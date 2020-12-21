
/*
* COP 3530: Project 1 – Array Searches and Sorts
* <p>
* This class will include the main and sorting methods 
* as well as the menu method. In these methods, users
* are prompter to repeatedly put integer inputs between
* 1-5 or 6 to quit that will search, sort and print the 
* country array.  
*
* @author <Emanuel Tesfa>
* @version <September 13, 2020>
*/

import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Project1 {

	static Country[] c = new Country[153];

	/*
	 * The main method throws 2 exceptions and initializes all variables and the
	 * Scanner type objects. After this the the Country array object is initialized
	 * in the for loop. Then the CSV file is parsed and the user interactive portion
	 * used. Once prompted, the user enters a number 1-6, in which a else if
	 * statements are utilized to call the sorting, search and printing methods
	 * depending on the users input.
	 * 
	 * @param parameter
	 * 
	 * @return void
	 */

	public static void main(String[] args) throws NumberFormatException, IOException {

		String name = null, capitol = null, searchKey = null, fileName = null;
		double gdp = 0, population = 0, cases = 0, deaths = 0;
		int i = 0, userNum = 0;

		Scanner input = new Scanner(System.in);
		System.out.println("Please enter in name of file");
		fileName = input.next(); // Countries1.csv
		System.out.printf("\nThere were " + c.length + " countries read \n");

		Scanner read = new Scanner(new File(fileName));

		read.useDelimiter(",|\\n");

		for (int j = 0; j < 153; j++)
			c[j] = new Country();

		while (read.hasNext()) {
			read.nextLine();

			name = read.next();
			capitol = read.next();
			population = read.nextDouble();
			gdp = Double.parseDouble(read.next());
			cases = read.nextDouble();
			deaths = Double.parseDouble(read.next());

			c[i].setName(name);
			c[i].setCapitol(capitol);
			c[i].setPopulation(population);
			c[i].setGDP(gdp);
			c[i].setCases(cases);
			c[i].setDeaths(deaths);

			i++;
		}

		while (userNum != 6) {
			menu();
			try {
				userNum = input.nextInt();

			} catch (InputMismatchException | NumberFormatException ex) {

				input.next();
			}

			if (userNum == 1) {
				System.out.printf("\t\t\t     Name\t Capitol     Population\t\tGDP\t\tCases\t  Deaths\n\n"
						+ "----------------------------------------------------------------------------------------------------------");
				for (i = 0; i < c.length; i++) {

					System.out.printf("\n %33s %14s %12.0f \t%15.0f \t%7.0f\t %7.0f  ", c[i].getName(),
							c[i].getCapitol(), c[i].getPopulation(), c[i].getGDP(), c[i].getCases(), c[i].getDeaths());

				}

			} else if (userNum == 2)
				insertionSort();
			else if (userNum == 3)
				selectionSort();
			else if (userNum == 4)
				bubbleSort();
			else if (userNum == 5) {
				int indexFound;
				System.out.println("Enter in a Country");
				searchKey = input.next();

				if (c[0].getName().compareToIgnoreCase("Afghanistan") == 0) {

					indexFound = findBinary(searchKey);

				} else {

					indexFound = findSeq(searchKey);
				}

				if (indexFound != c.length) {
					System.out.print("Name: " + c[indexFound].getName() + "\nCapitol: " + c[indexFound].getCapitol()
							+ "\nPopulation: " + c[indexFound].getPopulation() + "\nGDP: " + c[indexFound].getGDP()
							+ "\nCases: " + c[indexFound].getCases() + "\nDeaths: " + c[indexFound].getDeaths()
							+ "\n\n");
				} else
					System.out.println("Country not found\n\n");

			} else if (userNum == 6) {
				System.out.println("Thank you, please come again later");
				break;
			} else {
				System.out.println("Invalid choice please enter a valid value");

			}

		}

	}

	/*
	 * This method is made to sort the Country array based on name. Takes no
	 * parameters and does not return any values as it is type void.
	 *
	 * @param parameter
	 * 
	 * @return
	 */

	public static void insertionSort() {

		int in, out;

		for (out = 1; out < c.length; out++) {
			Country temp = c[out];
			in = out;

			while (in > 0 && c[in - 1].getName().compareTo(temp.getName()) > 0) {
				c[in] = c[in - 1];
				--in;
			}

			c[in] = temp;
		}
		System.out.println("Countries have been sorted by Country's name\n");
	}

	/*
	 * This method is made to sort the Country array based on CFR by Selection sort.
	 * Uses getCFR() method to compare at the given index. Takes no parameters and
	 * does not return any values as it is type void.
	 *
	 * @param parameter
	 * 
	 * @return
	 */

	public static void selectionSort() {

		int out, in, min;
		for (out = 0; out < c.length - 1; out++) {
			min = out;
			Country temp;

			for (in = out + 1; in < c.length; in++) {
				if (c[in].getCFR() < c[min].getCFR()) {
					min = in;
				}
			}

			temp = c[min];
			c[min] = c[out];
			c[out] = temp;
		}
		System.out.println("Countries have been sorted by Covid-19 Fatality Rate \n");

	}

	/*
	 * This method is made to sort the Country array based on GDP per Capita by
	 * Bubble sort. Uses getPerCapita() method to compare at the given index. Takes
	 * no parameters and does not return any values as it is type void.
	 *
	 * @param parameter
	 * 
	 * @return
	 */

	public static void bubbleSort() {
		for (int i = 0; i < c.length - 1; i++) {
			for (int j = 0; j < c.length - i - 1; j++) {
				if (c[j].getPerCapita() < c[j + 1].getPerCapita()) {

					Country temp = c[j];
					c[j] = c[j + 1];
					c[j + 1] = temp;

				}
			}
		}
		System.out.println("Countries have been sorted by GDP per Capita\n");

	}

	/*
	 * This method is made to search through and confirm the name of a Country
	 * (ignoring capitalization) in the Country array using sequential search. Using
	 * getName() method to compare at the given index. Takes a String as a
	 * parameter, named Search key and returns the index as an integer type, which
	 * is read in main as either as either true or false using a if/else statement
	 * and calls information from the getters at that returned index.
	 *
	 * @param parameter j ( the index found ) is returned to the main
	 * 
	 * @param parameter the integer 153 is returned as a way to notate that j has
	 * reached end of array
	 * 
	 * @return
	 */

	public static int findSeq(String searchKey) {
		int j = 0;
		while (j < c.length) {
			int a = c[j].getName().compareToIgnoreCase(searchKey);

			if (a == 0) {
				break;
			}
			j++;
		}

		if (j == c.length) {
			return c.length;
		} else
			return j;

	}

	/*
	 * This method is made to search through and confirm the name of a Country
	 * (ignoring capitalization) in the Country array using Binary search. Using
	 * getName() method to compare at the given index. Takes a String as a
	 * parameter, named Search key and returns the index as an integer type, which
	 * is read in main as either as either true or false using a if/else statement
	 * and calls information from the getters at that returned index.
	 *
	 * @param parameter j ( the index found ) is returned to the main
	 * 
	 * @param parameter the integer 153 is returned as a way to notate that j has
	 * reached end of array
	 * 
	 * @return
	 */
	public static int findBinary(String searchKey) {
		int lowerBound = 0;
		int upperBound = c.length - 1;
		int curIn = 0;

		while (upperBound >= lowerBound) {
			curIn = (lowerBound + upperBound) / 2;

			if (c[curIn].getName().compareToIgnoreCase(searchKey) == 0)
				return curIn;

			else if (lowerBound > upperBound)
				return c.length;
			else {
				if (c[curIn].getName().compareToIgnoreCase(searchKey) < 0)
					lowerBound = curIn + 1;
				else
					upperBound = curIn - 1;
			}

		}
		return curIn;

	}

	/*
	 * This method is made to print the recurring menu in main
	 *
	 * @param parameter
	 * 
	 * @return
	 */

	public static void menu() {
		System.out.println("\nEnter a number 1-5. \n" + "1) Print a countries report\n" + "2) Sort by Name \n"
				+ "3) Sort by COVID-19 CFR\n" + "4) Sort by GDP per capita\n"
				+ "5) Find and print a country for a given name\n" + "6) Quit");

	}

}
