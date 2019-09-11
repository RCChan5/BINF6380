package lab;
import java.util.Scanner;  // Import the Scanner class
import java.util.Random;
public class Lab2
{
	
	public static String[] SHORT_NAMES = 
		{ "A","R", "N", "D", "C", "Q", "E", 
		"G",  "H", "I", "L", "K", "M", "F", 
		"P", "S", "T", "W", "Y", "V" };

	public static String[] FULL_NAMES = 
		{
		"alanine","arginine", "asparagine", 
		"aspartic acid", "cysteine",
		"glutamine",  "glutamic acid",
		"glycine" ,"histidine","isoleucine",
		"leucine",  "lysine", "methionine", 
		"phenylalanine", "proline", 
		"serine","threonine","tryptophan", 
		"tyrosine", "valine"};
	
	public static Boolean timer(long timeLimit, long started)
	{
		if (timeLimit+started <= System.currentTimeMillis())
		{
			System.out.println("Times up!");
			return false;
		}
		else
		{
			return true;
		}
		
	}
	public static long elapse(long started) 
	{
		long current = (System.currentTimeMillis()-started)/1000;
		return current;
	}
	
	public static void quiz(long timeLimit,int totalQuestions, int [] summary) 
	{
		Scanner input1 = new Scanner(System.in);  // Create a Scanner object
		Random random = new Random();
		int right=0;
		long started = System.currentTimeMillis();
		int questionCounter =0;
		
		
		while (timer(timeLimit,started) && questionCounter!=totalQuestions) 
		{
			int c1 =random.nextInt(19);
			questionCounter++;
			System.out.println("\n-------------Question"+questionCounter+"-----------------");
			System.out.println(FULL_NAMES[c1]);
			String ua =input1.next().toUpperCase();
			
			if (ua.equals("QUIT")) 
			{
				System.out.println("Good Bye!");
				break;
			}
			else if (ua.equals(SHORT_NAMES[c1]))
			{
				right++;
				System.out.println("Correct! Score: "+right+" Time Elapsed: "+elapse(started)+" out of "+timeLimit/1000 +" seconds");
				
			}
			else 
			{
				
				System.out.println("Wrong! Score: "+right+" Time Elapsed: "+elapse(started)+" out of "+timeLimit/1000+" seconds");
				summary[c1]++;
			}
		
			
		}
		input1.close();
		
		
		// End of game user summary
		System.out.println("----------------------------------------");
		System.out.println("You final score was "+right);
		System.out.println("You got these wrong:");
		for (int i=0; i<summary.length;i++)
		{
			if(summary[i] != 0) 
			{
				System.out.println(FULL_NAMES[i]+" wrong "+summary[i]+" time(s)");
			}
			
		}
				
	}
	public static void main(String[] args)
	{
		
		// getting user input
	    Scanner input = new Scanner(System.in);  // Create a Scanner object
	    System.out.println("How long do you want the game to run?(seconds) ");
	    Long timeLimit = (input.nextLong()*1000);// multiply by 1000 due to currentTimeMillis gets time in milliseconds
	    System.out.println("How many questions do you want? ");
	    Integer totalQuestions = input.nextInt();
	    
	    //array used to store wrong answers
	    int[] summary = new int[20];
	    
	    quiz(timeLimit,totalQuestions, summary);
	    input.close();
	    	
	}
	
	
}
