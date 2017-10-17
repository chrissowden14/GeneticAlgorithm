public class SimulatedAnnealing {

    // Calculate the acceptance probability
    public static double acceptanceProbability(double currentEnergy, double neighbourEnergy, double temperature) {
        // If the new solution is better, accept it
        if (neighbourEnergy < currentEnergy) {
            return 1.0;
        }
        // If the new solution is worse, calculate an acceptance probability
        return Math.exp((currentEnergy - neighbourEnergy) / temperature);
    }

    public static void main(String[] args) {
        // Create and add our cities
        City city = new City(0.372090608,0.432608199);
        TourManager.addCity(city);
        City city2 = new City(0.38029396,0.713361671);
        TourManager.addCity(city2);
        City city3 = new City(0.898119767, 0.772681874);
        TourManager.addCity(city3);
        City city4 = new City(0.167702257,0.99219988);
        TourManager.addCity(city4);
        City city5 = new City(0.686992927,0.93838682);
        TourManager.addCity(city5);
        City city6 = new City(0.274830532,0.127452799);
        TourManager.addCity(city6);
        City city7 = new City(0.424618695,0.817378304);
        TourManager.addCity(city7);
        City city8 = new City(0.478824774,0.093485707);
        TourManager.addCity(city8);
        City city9 = new City(0.656087536,0.875909204);
        TourManager.addCity(city9);
        City city10 = new City(0.240264048,0.324621796);
        TourManager.addCity(city10);
        City city11 = new City(0.830124281,0.076594979);
        TourManager.addCity(city11);
        City city12 = new City(0.72887909,0.622051319);
        TourManager.addCity(city12);
        City city13 = new City(0.442833825,0.387846749);
        TourManager.addCity(city13);
        City city14 = new City(0.088127924,0.547910343);
        TourManager.addCity(city14);
        

        // Set initial temp
        double temp = 10000;

        // Cooling rate
        double coolingRate = 0.001;

        // Initialize intial solution
        Tour currentSolution = new Tour();
        currentSolution.generateIndividual();
        
        System.out.println("Initial solution distance: " + currentSolution.getDistance());

        // Set as current best
        Tour best = new Tour(currentSolution.getTour());
        
        // Loop until system has cooled
        while (temp > 1) {
            // Create new neighbour tour
            Tour newSolution = new Tour(currentSolution.getTour());

            // Get a random positions in the tour
            int tourPos1 = (int) (newSolution.tourSize() * Math.random());
            int tourPos2 = (int) (newSolution.tourSize() * Math.random());

            // Get the cities at selected positions in the tour
            City citySwap1 = newSolution.getCity(tourPos1);
            City citySwap2 = newSolution.getCity(tourPos2);

            // Swap them
            newSolution.setCity(tourPos2, citySwap1);
            newSolution.setCity(tourPos1, citySwap2);
            
            // Get energy of solutions
            double currentEnergy = currentSolution.getDistance();
            double neighbourEnergy = newSolution.getDistance();

            // Decide if we should accept the neighbour
            if (acceptanceProbability(currentEnergy, neighbourEnergy, temp) > Math.random()) {
                currentSolution = new Tour(newSolution.getTour());
            }

            // Keep track of the best solution found
            if (currentSolution.getDistance() < best.getDistance()) {
                best = new Tour(currentSolution.getTour());
            }
            
            // Cool system
            temp *= 1-coolingRate;
        }

        System.out.println("Final solution distance: " + best.getDistance());
        System.out.println("Tour: " + best);
    }
}