//Rosh Chan
package lab;
/**
 * Write code to print to the console 1,000 randomly generated 
	DNA 3 mers (e.g. “ACA”, “TCG” )
	where the frequency of A,C,G and T is 25% and is uniformly sampled.

	Have your code track how often it prints out the 3 mer (“AAA”) 
	How often would you expect to see this 3mer by chance?  Is Java’s number
	close to the number that you would expect?
 * 
 * 
 * Extra Credit:
 * Modify your code so that the frequency of A,C,G and T is

		p(A) = 0.12
		p(C) = 0.38
		p(G) = 0.39
		p(T) = 0.11

	What is the expected frequency now of “AAA”?  Does Java produce “AAA” 
	at close to the expected frequency?
	
		Hint the code: 
		Random random = new Random();
		float f = random.nextFloat();
	
	       produces a random number uniformly between 0 and 1.
*/

import java.util.Random;

public class Lab1 
{
	public static void main(String[] args)
	{
		Random random = new Random();
		int count =0;
		for(int i =0; i < 1000 ;i++) {
			int x1 = random.nextInt(4);
			int x2 = random.nextInt(4);
			int x3 = random.nextInt(4);
			String mer = (x1+""+x2+""+x3);
			mer=mer.replace("0","A").replace("1","T").replace("2","C").replace("3","G");
			System.out.println(mer);
			if (mer.equals("AAA")) 
			{
				count++;
			}
		}
		System.out.println("Results:\nThere are "+count+" AAA with 25% likelyhood for each nucleotide the chance of getting AAA is .0150 \nor about 15 hits out of 1000 runs.");
		
		System.out.println("\nExtra Credit 1 Results:\np(A) = 0.12\n" + 
				"p(C) = 0.38\n" + 
				"p(G) = 0.39\n" + 
				"p(T) = 0.11\n"+
				"This is how many times AAA apears in 1000 runs "+extraCredit1());
		System.out.println(".12^3 = .0017 * 1000 = 1.728 which is the expected requency");
	}
	
	public static int extraCredit1() 
	{
		int count =0;
		for(int i =0; i < 1000 ;i++) 
		{
			String mer = frequency()+frequency()+frequency();
			//System.out.println(mer);
			if (mer.equals("AAA")) 
			{
				count++;
			}
			
		}
		frequency();
		return count;
		
	}
	
	public static String frequency()
	{
		Double x = Math.random();
		if (0 <= x && x <= .12) 
		{
			return "A";
		}
		else if (.12 < x && x <= .50) 
		{
			return "C";
		}
		else if (.50 < x && x <= .89) 
		{
			return "G";
		}
		else if (.89 < x && x <= 1) 
		{
			return "T";
		}
		else 
		{
			return "THIS IS WRONG";
		}
		
	}

}







