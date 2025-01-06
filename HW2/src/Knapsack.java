/** Will Pond CSC 364
 * In this program it asks for 3 things the number people to work on each project and two files. One is an input file and the other
 *  is an output file. It uses Dynamic programing to create a knapsack method to solve the best possible profit from the amount people you entered
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;


public class Knapsack {

    public static Scanner input;

    public static PrintWriter outputFile;

    public static int available;

    public static int count;


    // Method for calculating the Knapsack
     static void Knapsack(ArrayList<Project> projects, int available) {

         ArrayList<Project> scheme = new ArrayList<Project>();

         int rows = projects.size() + 1;
         int columns = available + 1;
         int[][] Solution = new int[rows][columns];

         int counter = 0;
         int i, j;
         for(i = 0; i <= projects.size(); i++){
             for(j = 0; j <= available; j++){
                 if(i == 0 || j == 0)
                 {
                     Solution[i][j] = 0;
                 }
                 else if (projects.get(i-1).getWorkers() <= j)
                 {
                  Solution[i][j] = Math.max(projects.get(i-1).getProfit() + Solution[i-1][j-projects.get(i-1).getWorkers()], Solution[i-1][j]);
                 }
                 else
                 {
                     Solution[i][j] = Solution[i-1][j];
                 }

             }
             }
         int rest = Solution[projects.size()][available];
         int total = rest;


         j = available;
         for (i = projects.size(); i > 0 && rest > 0; i--)
         {
             if(rest == Solution[i-1][j])
                continue;
             else
             {
                 counter++;

                 scheme.add(projects.get(i-1));

                 rest = rest - projects.get(i-1).getProfit();
                 j = j - projects.get(i-1).getWorkers();
             }
         }
         outputFile.println("Number of projects chosen: " + counter);
         outputFile.println("Total profit: " + total);
         for(int t = scheme.size()-1; t >= 0; t--)
         {
            outputFile.println(scheme.get(t));
         }

     }
    public static void main(String[] args) throws FileNotFoundException {

        input = new Scanner(System.in);

        System.out.println("Enter the number of available employee work weeks:");
         available = Integer.parseInt(input.next());

        System.out.println("Enter the name of input file:");

         String input_file = input.next();
         String inputFileName = "C:\\Users\\wpond\\NKU\\CSC364\\Assignments\\HW2\\HW2\\src\\"+input_file;


        Scanner dataFile = new Scanner(new File(inputFileName));
        ArrayList<Project> projects = new ArrayList<>();
        while (dataFile.hasNextLine())
        {
            String line = dataFile.nextLine();
            String[] split = line.split(" ");
            projects.add(new Project(split[0], Integer.parseInt(split[1]), Integer.parseInt(split[2])));
            count++;
        }
        dataFile.close();
        System.out.println("Enter the name of output file:");
        String output_file = input.next();
         String outputFileName = "C:\\Users\\wpond\\NKU\\CSC364\\Assignments\\HW2\\HW2\\src\\"+output_file;

        // Here is code for writing to a text file
        // You will need to declare and initialize outputFileName (String).
        outputFile = new PrintWriter(outputFileName);
        outputFile.println("Number of projects available: "+count);
        outputFile.println("Available employee work weeks: "+available);
        Knapsack(projects,available);

        outputFile.close();
    }
    private static class Project {
          String name;
          int workers;
          int profit;

        public Project(String name, int workers, int profit) {

            this.name = name;

            this.workers = workers;

            this.profit = profit;

        }

        public int getWorkers(){return workers;}

        public int getProfit(){return profit;}


        public String toString(){return name+" "+workers+" "+profit;}

    }

}
