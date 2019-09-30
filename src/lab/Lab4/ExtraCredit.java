package lab.Lab4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/*
 * For extra-credit:

Create a class that allows a user to parse an arbitrarily large Fasta file
one sequence at a time but does not hold the sequences in memory.

This class should have a function called something like

	public FastaSequence getNextSequence()

that returns the next FastaSequence in the file or null if there are
no more sequences…

 */


public class ExtraCredit
{
	//object needs to store position and changes it every time the next() method is called 
	//constructor
	public ExtraCredit(String location) throws Exception
	{
		BufferedReader reader = new BufferedReader(new FileReader(new File(location)));

		//using linked hash map just in case order of fasta file matter
		LinkedHashMap<String, String> hash_map = new LinkedHashMap<String, String>();
		String key = null;
		
		//
		//*****instead of sing this for loop keep it pointng at the start till nextseq  
		//i dont know bufferedREader as well ask later
		for(String nextLine =reader.readLine(); nextLine != null;nextLine=reader.readLine()) 
		{	

			if (nextLine.startsWith(">")) 
			{	
				key = nextLine;
				//System.out.println(key);
			}
			else
			{
				//if else is for multi line fasta files so null value is not appended into value
				if(hash_map.get(key)==null) {
					hash_map.put(key,nextLine);
				}
				else {
					hash_map.put(key,hash_map.get(key)+nextLine);
				}
			}
			
		}	
		System.out.println("test");
	
	}
/*
 * method to just print sequence to text file
 */
public static void fileWriter(ArrayList<String> results, String location) throws Exception
{
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(location)));
		
					
		writer.flush();
		writer.close();
}

public static String getNextFastaSequence() 
{	
	BufferedReader reader = new BufferedReader(new FileReader(new File(location)));
	
	//using linked hash map just in case order of fasta file matter
	LinkedHashMap<String, String> hash_map = new LinkedHashMap<String, String>();
	String key = null;
	
	//parses fasta file into linked hashmap
	for(String nextLine =reader.readLine(); nextLine != null;nextLine=reader.readLine()) 
	{	

		if (nextLine.startsWith(">")) 
		{	
			key = nextLine;
			//System.out.println(key);
		}
		else
		{
			//if else is for multi line fasta files so null value is not appended into value
			if(hash_map.get(key)==null) {
				hash_map.put(key,nextLine);
			}
			else {
				hash_map.put(key,hash_map.get(key)+nextLine);
			}
		}
		
	}	
	
	
	return null;	
}
	
public static void main(String[] args)
{
	//while(getNextFastaSequence()!=null) 
	
	ExtraCredit x1 = new ExtraCredit();
		
	
}

}
