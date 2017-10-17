
import java.util.Scanner;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.JOptionPane;

 
public class Population2 {
	
	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);							// Create Scanner object
		NumberFormat df = DecimalFormat.getInstance();					// Decimal formatting
	    df.setMaximumFractionDigits(2);
		int days;														// Store the number of days to multiple the population
		double organisms = 0;										    // Store number of organism		
		double increaseRate = 0;									    // Daily population increase percentage
		
		
		System.out.print("Number of days to multiply: ");
		days  = input.nextInt();										// Prompt user for the number of days
		
		while(days < 1){
			System.out.print("Invalid entry. Re-enter a value greater than 0: ");
			days  = input.nextInt();
		}
		
		System.out.print("Number of organisms: ");
		organisms = input.nextDouble();										// Prompt user for the amount of organisms
		
		while( organisms < 2){												// Input validation
			System.out.print("Ivalid entry. Re-enter a value greater than 1: ");
			organisms = input.nextDouble();	
		}
		
		System.out.print("Percentage of increase: ");
		increaseRate = input.nextDouble()/100;								// Prompt user for the rate of increase in percentage
		
		while( increaseRate < 0){											// Input validation
			System.out.print("Ivalid entry. Re-enter a non-negative value: ");
			increaseRate = input.nextDouble();	
		}
		
		System.out.println(""); // Newline
		
		System.out.println("Day" +"       Population" + "       Percent of Increase");	
		System.out.println("_______________________________________________");
		System.out.println("  1           " + df.format(organisms));
		for(int i=2; i<days + 1; i++){
			organisms += (organisms *= increaseRate);
			System.out.println("  " +i + "           " + df.format(organisms) + "                " + (increaseRate*100)+"%");
		}
	}

	public Object getFittest() {
		// TODO Auto-generated method stub
		return null;
	}
}