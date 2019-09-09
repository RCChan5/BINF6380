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
	public static int quiz(long timeLimit,int totalQuestions) 
	{
		
		Random random = new Random();
		int right=0;
		int wrong=0;
		long started = System.currentTimeMillis();
		while (timer(timeLimit,started)) 
		{
			int c1 =random.nextInt(19);
			System.out.println(FULL_NAMES[c1]);
			Scanner input1 = new Scanner(System.in);  // Create a Scanner object

			String ua =input1.next();
			
			ua=ua.toUpperCase();
			if (ua.equals(SHORT_NAMES))
			{
				System.out.println("Correct!");
				right++;
			}
			else 
			{
				wrong++;
			}
			//input1.close();
			
		}
		
		return 1;
	}
	public static void main(String[] args)
	{
	    Scanner input = new Scanner(System.in);  // Create a Scanner object
	    System.out.println("How long do you want the game to run?(seconds) ");
	    Long timeLimit = (input.nextLong()*1000);
	    System.out.println("How many questions do you want? ");
	    Integer totalQuestions = input.nextInt();
	    //input.close();
	    quiz(timeLimit,totalQuestions);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
