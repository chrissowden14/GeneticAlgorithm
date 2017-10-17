import java.util.ArrayList;
import java.util.Random;

public class TSP1
{
    private static final int START_SIZE = 50;                    // Population size at start.
    private static final int MAX_EPOCHS = 50;                  // Arbitrary number of test cycles.
    private static final double MATING_PROBABILITY = 0.7;        // Probability of two chromosomes mating. Range: 0.0 < MATING_PROBABILITY < 1.0
    private static final double MUTATION_RATE = 0.001;           // Mutation Rate. Range: 0.0 < MUTATION_RATE < 1.0
    private static final int MIN_SELECT = 30;                    // Minimum parents allowed for selection.
    private static final int MAX_SELECT = 75;                    // Maximum parents allowed for selection. Range: MIN_SELECT < MAX_SELECT < START_SIZE
    private static final int OFFSPRING_PER_GENERATION = 40;      // New offspring created per generation. Range: 0 < OFFSPRING_PER_GENERATION < MAX_SELECT.
    private static final int MINIMUM_SHUFFLES = 16;               // For randomizing starting chromosomes
    private static final int MAXIMUM_SHUFFLES = 20;

    private static final boolean SHOW_VERBOSE_RESULTS = false;

    private static int epoch = 0;
    private static int childCount = 0;
    private static int nextMutation = 0;                         // For scheduling mutations.
    private static int mutations = 0;

    private static ArrayList<Chromosome> population = new ArrayList<Chromosome>();
    private static ArrayList<cCity> map = new ArrayList<cCity>();
    private static final int CITY_COUNT = 8;
    private static final double TARGET = 86.6299;                // Number for algorithm to find.
    private static double XLocs[] = new double[] {0.372090608,
    		0.38029396,
    		0.898119767,
    		0.167702257,
    		0.686992927,
    		0.274830532,
    		0.424618695,
    		0.478824774,
    		0.656087536,
    		0.240264048,
    		0.830124281,
    		0.72887909,
    		0.442833825,
    		0.088127924};
    private static double YLocs[] = new double[] {0.432608199,
    		0.713361671,
    		0.772681874,
    		0.99219988,
    		0.93838682,
    		0.127452799,
    		0.817378304,
    		0.093485707,
    		0.875909204,
    		0.324621796,
    		0.076594979,
    		0.622051319,
    		0.387846749,
    		0.547910343};

    private static void geneticAlgorithm()
    {
        int popSize = 0;
        Chromosome thisChromo = null;
        boolean done = false;

        initializeChromosomes();
        mutations = 0;
        nextMutation = getRandomNumber((int)Math.round(1.0 / MUTATION_RATE));

        while(!done)
        {
            popSize = population.size();
            for(int i = 0; i < popSize; i++)
            {
                thisChromo = population.get(i);
                if((Math.abs(thisChromo.total() - TARGET) <= 1.0) || epoch == MAX_EPOCHS){
                    done = true;
                }
            }

            getFitness();

            if(SHOW_VERBOSE_RESULTS == true){
                popSize = population.size();
                for(int i = 0; i < popSize; i++)
                {
                    thisChromo = population.get(i);
                    for(int j = 0; j < CITY_COUNT; j++)
                    {
                       // System.out.print(thisChromo.data(j));
                    } // j
                    System.out.print(" = " + thisChromo.total());
                            System.out.print("\t" + thisChromo.fitness() + "%\n");
                } // i
                System.out.print("\n");
            }
            
            rouletteSelection();

            mating();

            prepNextEpoch();

            epoch++;
            // This is here simply to show the runtime status.
            System.out.println("Epoch: " + epoch);
        }
        //printBestFromPopulation();
        System.out.println(printBestFromPopulation());

        System.out.println("done.");
        
        if(epoch != MAX_EPOCHS){
            popSize = population.size();
            for(int i = 0; i < popSize; i++)
            {
                thisChromo = population.get(i);
                if(Math.abs(thisChromo.total() - TARGET) <= 0.001){
                    // Print the chromosome.
                    for(int j = 0; j < CITY_COUNT; j++)
                    {
                        System.out.print(thisChromo.data(j) + ", ");
                    } // j
                    System.out.print("\n");
                }
            } // i
        }
        System.out.println("Completed " + epoch + " epochs.");
        System.out.println("Encountered " + mutations + " mutations in " + childCount + " offspring.");

        return;
    }

    private static void initializeMap()
    {
        cCity city = null;

        for(int i = 0; i < CITY_COUNT; i++)
        {
            city = new cCity();
            city.x(XLocs[i]);
            city.y(YLocs[i]);
            map.add(city);
        }
        return;
    }

    private static void initializeChromosomes()
    {
        int shuffles = 0;
        Chromosome newChromo = null;
        int chromoIndex = 0;

        for(int i = 0; i < START_SIZE; i++)
        {
            newChromo = new Chromosome();

            for(int j = 0; j < CITY_COUNT; j++)
            {
                newChromo.data(j, j);
            } // j
            population.add(newChromo);
            chromoIndex = population.indexOf(newChromo);

            // Randomly choose the number of shuffles to perform.
            shuffles = getRandomNumber(MINIMUM_SHUFFLES, MAXIMUM_SHUFFLES);

            exchangeMutation(chromoIndex, shuffles);

            getTotalDistance(chromoIndex);

        } // i
        return;
    }

    private static void getFitness()
    {
        // Lowest errors = 100%, Highest errors = 0%
        int popSize = population.size();
        Chromosome thisChromo = null;
        double bestScore = 0;
        double worstScore = 0;

        // The worst score would be the one furthest from the TARGET, best would be closest.
        thisChromo = population.get(maximum());
        worstScore = Math.abs(TARGET - thisChromo.total());

        // Convert to a weighted percentage.
        thisChromo = population.get(minimum());
        bestScore = worstScore - Math.abs(TARGET - thisChromo.total());

        for(int i = 0; i < popSize; i++)
        {
            thisChromo = population.get(i);
            thisChromo.fitness((worstScore - (Math.abs(TARGET - thisChromo.total()))) * 100.0 / bestScore);
        }
        
        return;
    }

    private static void rouletteSelection()
    {
        int j = 0;
        int popSize = population.size();
        double genTotal = 0.0;
        double selTotal = 0.0;
        int maximumToSelect = getRandomNumber(MIN_SELECT, MAX_SELECT);
        double rouletteSpin = 0.0;
        Chromosome thisChromo = null;
        Chromosome thatChromo = null;
        boolean done = false;

        for(int i = 0; i < popSize; i++)
        {
            thisChromo = population.get(i);
            genTotal += thisChromo.fitness();
        } // i

        genTotal *= 0.01;

        for(int i = 0; i < popSize; i++)
        {
            thisChromo = population.get(i);
            thisChromo.selectionProbability(thisChromo.fitness() / genTotal);
        } // i

        for(int i = 0; i < maximumToSelect; i++)
        {
            rouletteSpin = getRandomNumber(99);
            j = 0;
            selTotal = 0;
            done = false;
            while(!done)
            {
                thisChromo = population.get(j);
                selTotal += thisChromo.selectionProbability();
                if(selTotal >= rouletteSpin){
                    if(j == 0){
                        thatChromo = population.get(j);
                    }else if(j >= popSize - 1){
                        thatChromo = population.get(popSize - 1);
                    }else{
                        thatChromo = population.get(j - 1);
                    }
                    thatChromo.selected(true);
                    done = true;
                }else{
                    j++;
                }
            }
        }
        return;
    }

    private static void mating()
    {
        int getRand = 0;
        int parentA = 0;
        int parentB = 0;
        int newChildIndex = 0;
        Chromosome newChromo = null;

        for(int i = 0; i < OFFSPRING_PER_GENERATION; i++)
        {
            parentA = chooseParent();
            // Test probability of mating.
            getRand = getRandomNumber(100);
            if(getRand <= MATING_PROBABILITY * 100){
                parentB = chooseParent(parentA);
                newChromo = new Chromosome();
                population.add(newChromo);
                newChildIndex = population.indexOf(newChromo);
                partiallyMappedCrossover(parentA, parentB, newChildIndex);
                if(childCount == nextMutation){
                    getRand = getRandomNumber(CITY_COUNT - 1);
                    exchangeMutation(newChildIndex, 1);
                }

                getTotalDistance(newChildIndex);

                childCount++;

                // Schedule next mutation.
                if(childCount % (int)(1.0 / MUTATION_RATE) == 0){
                    nextMutation = childCount + getRandomNumber((int)Math.round(1.0 / MUTATION_RATE));
                }
            }
        }
        return;
    }

    private static int chooseParent()
    {
        // Overloaded function, see also "chooseparent(ByVal parentA As Integer)".
        int parent = 0;
        Chromosome thisChromo = null;
        boolean done = false;

        while(!done)
        {
            // Randomly choose an eligible parent.
            parent = getRandomNumber(population.size() - 1);
            thisChromo = population.get(parent);
            if(thisChromo.selected() == true){
                done = true;
            }
        }

        return parent;
    }

    private static int chooseParent(final int parentA)
    {
        // Overloaded function, see also "chooseparent()".
        int parent = 0;
        Chromosome thisChromo = null;
        boolean done = false;

        while(!done)
        {
            // Randomly choose an eligible parent.
            parent = getRandomNumber(population.size() - 1);
            if(parent != parentA){
                thisChromo = population.get(parent);
                if(thisChromo.selected() == true){
                    done = true;
                }
            }
        }

        return parent;
    }

    private static void partiallyMappedCrossover(final int chromA, final int chromB, final int childIndex)
    {
        int j = 0;
        int crossPoint1 = 0;
        int crossPoint2 = 0;
        int item1 = 0;
        int item2 = 0;
        int pos1 = 0;
        int pos2 = 0;
        Chromosome thisChromo = population.get(chromA);
        Chromosome thatChromo = population.get(chromB);
        Chromosome newChromo = population.get(childIndex);

        crossPoint1 = getRandomNumber(CITY_COUNT - 1);
        crossPoint2 = getExclusiveRandomNumber(CITY_COUNT - 1, crossPoint1);
        if(crossPoint2 < crossPoint1){
            j = crossPoint1;
            crossPoint1 = crossPoint2;
            crossPoint2 = j;
        }

        // Copy parentA genes to offspring.
        for(int i = 0; i < CITY_COUNT; i++)
        {
            newChromo.data(i, thisChromo.data(i));
        }

        for(int i = crossPoint1; i <= crossPoint2; i++)
        {
            // Get the two items to swap.
            item1 = thisChromo.data(i);
            item2 = thatChromo.data(i);

            // Get the items' positions in the offspring.
            for(int k = 0; k < CITY_COUNT; k++)
            {
                if(newChromo.data(k) == item1){
                    pos1 = k;
                }else if(newChromo.data(k) == item2){
                    pos2 = k;
                }
            } // k

            // Swap them.
            if(item1 != item2){
                newChromo.data(pos1, item2);
                newChromo.data(pos2, item1);
            }

        } // i
        return;
    }

    private static void exchangeMutation(final int index, final int exchanges)
    {
        int i =0;
        int tempData = 0;
        Chromosome thisChromo = null;
        int gene1 = 0;
        int gene2 = 0;
        boolean done = false;
        
        thisChromo = population.get(index);

        while(!done)
        {
            gene1 = getRandomNumber(CITY_COUNT - 1);
            gene2 = getExclusiveRandomNumber(CITY_COUNT - 1, gene1);

            // Exchange the chosen genes.
            tempData = thisChromo.data(gene1);
            thisChromo.data(gene1, thisChromo.data(gene2));
            thisChromo.data(gene2, tempData);

            if(i == exchanges){
                done = true;
            }
            i++;
        }
        mutations++;
        return;
    }

    private static void prepNextEpoch()
    {
        int popSize = 0;
        Chromosome thisChromo = null;

        // Reset flags for selected individuals.
        popSize = population.size();
        for(int i = 0; i < popSize; i++)
        {
            thisChromo = population.get(i);
            thisChromo.selected(false);
        }
        return;
    }

    private static String printBestFromPopulation()
    {
    	double far = 0;
    	int count = 0;
        int popSize = 0;
        StringBuffer tempString = new StringBuffer();
        Chromosome thisChromo = null;

        popSize = population.size();
        for(int i = 0; i < popSize; i++)
        {
            thisChromo = population.get(i);
            if(thisChromo.fitness() > 80.0){
                for(int j = 0; j < CITY_COUNT; j++)
                {
                    //tempString.append(thisChromo.data(j));
                } // j

                tempString.append( + thisChromo.total() +   "\n");
                far= far+( (thisChromo.total()-5.12585032063536)* (thisChromo.total()-5.12585032063536));
                count ++;
              
            }
        } // i
        System.out.println (Math.sqrt(far/count));
        return tempString.toString();
       
    }
//    private static void InitialDistance(final int chromoIndex){
// // Initialize population
//    Population2 pop = new Population2();
//    System.out.println("Initial distance: " + ((Object) pop.getFittest()).getDistance());
//    }


    private static void getTotalDistance(final int chromoIndex)
    {
        Chromosome thisChromo = null;
        thisChromo = population.get(chromoIndex);

        for(int i = 0; i < CITY_COUNT; i++)
        {
            if(i == CITY_COUNT - 1){
                thisChromo.total(thisChromo.total() + getDistance(thisChromo.data(CITY_COUNT - 1), thisChromo.data(0))); // Complete trip.
            }else{
                thisChromo.total(thisChromo.total() + getDistance(thisChromo.data(i), thisChromo.data(i + 1)));
            }
        }
        return;
    }

    private static double getDistance(final int FirstCity, final int SecondCity)
    {
        cCity CityA = null;
        cCity CityB = null;
        double A2 = 0;
        double B2 = 0;
        CityA = map.get(FirstCity);
        CityB = map.get(SecondCity);
        A2 = Math.pow(Math.abs(CityA.x() - CityB.x()), 2);
        B2 = Math.pow(Math.abs(CityA.y() - CityB.y()), 2);

        return Math.sqrt(A2 + B2);
    }
        
    private static int getRandomNumber(final int high)
    {
        return new Random().nextInt(high);
    }
    
    private static int getRandomNumber(final int low, final int high)
    {
        return (int)Math.round((high - low) * new Random().nextDouble() + low);
    }

    private static int getExclusiveRandomNumber(final int high, final int except)
    {
        boolean done = false;
        int getRand = 0;

        while(!done)
        {
            getRand = new Random().nextInt(high);
            if(getRand != except){
                done = true;
            }
        }

        return getRand;
    }

    private static int minimum()
    {
        // Returns an array index.
        int popSize = 0;
        Chromosome thisChromo = null;
        Chromosome thatChromo = null;
        int winner = 0;
        boolean foundNewWinner = false;
        boolean done = false;

        while(!done)
        {
            foundNewWinner = false;
            popSize = population.size();
            for(int i = 0; i < popSize; i++)
            {
                if(i != winner){             // Avoid self-comparison.
                    thisChromo = population.get(i);
                    thatChromo = population.get(winner);
                    // The minimum has to be in relation to the TARGET.
                    if(Math.abs(TARGET - thisChromo.total()) < Math.abs(TARGET - thatChromo.total())){
                        winner = i;
                        foundNewWinner = true;
                    }
                }
            }
            if(foundNewWinner == false){
                done = true;
            }
        }
        return winner;
    }

    private static int maximum()
    {
        // Returns an array index.
        int popSize = 0;
        Chromosome thisChromo = null;
        Chromosome thatChromo = null;
        int winner = 0;
        boolean foundNewWinner = false;
        boolean done = false;

        while(!done)
        {
            foundNewWinner = false;
            popSize = population.size();
            for(int i = 0; i < popSize; i++)
            {
                if(i != winner){             // Avoid self-comparison.
                    thisChromo = population.get(i);
                    thatChromo = population.get(winner);
                    // The minimum has to be in relation to the TARGET.
                    if(Math.abs(TARGET - thisChromo.total()) > Math.abs(TARGET - thatChromo.total())){
                        winner = i;
                        foundNewWinner = true;
                    }
                }
            }
            if(foundNewWinner == false){
                done = true;
            }
        }
        return winner;
    }

    private static class Chromosome
    {
        private int mData[] = new int[CITY_COUNT];
        private int mRegion = 0;
        private double mTotal = 0.0;
        private double mFitness = 0.0;
        private boolean mSelected = false;
        private int mAge = 0;
        private double mSelectionProbability = 0.0;
    
        public Chromosome()
        {
            mTotal = 0.0;
            return;
        }
    
        public double selectionProbability()
        {
            return mSelectionProbability;
        }
        
        public void selectionProbability(final double SelProb)
        {
            mSelectionProbability = SelProb;
            return;
        }
    
        public int age()
        {
            return mAge;
        }
        
        public void age(final int epochs)
        {
            mAge = epochs;
            return;
        }
    
        public boolean selected()
        {
            return mSelected;
        }
        
        public void selected(final boolean sValue)
        {
            mSelected = sValue;
            return;
        }
    
        public double fitness()
        {
            return mFitness;
        }
        
        public void fitness(final double score)
        {
            mFitness = score;
            return;
        }
    
        public double total()
        {
            return mTotal;
        }
        
        public void total(final double Value)
        {
            mTotal = Value;
            return;
        }
    
        public int region()
        {
            return mRegion;
        }
        
        public void region(final int regionNumber)
        {
            mRegion = regionNumber;
            return;
        }
    
        public int data(final int index)
        {
            return mData[index];
        }
        
        public void data(final int index, final int value)
        {
            mData[index] = value;
            return;
        }
    } // Chromosome

    private static class cCity
    {
        private double mX = 0;
        private double mY = 0;
    
        public double x()
        {
            return mX;
        }
        
        public void x(final double xLocs)
        {
            mX = xLocs;
            return;
        }
    
        public double y()
        {
            return mY;
        }
        
        public void y(final double yLocs)
        {
            mY = yLocs;
            return;
        }
    } // cCity

    public static void main(String[] args)
    {
        initializeMap();
        geneticAlgorithm();
        return;
    }

}