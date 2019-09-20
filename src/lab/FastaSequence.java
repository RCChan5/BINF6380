package lab;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.*;
import java.util.*;




/*
 * Make a static factory method that parses a Fasta file and returns a list of FastaSequence objects
 */
public class FastaSequence
{
	//class variables
	private final String header;
	private final String sequence;
	
	
	//constructor
	public FastaSequence(String header, String sequence)
	{
		this.header=header;
		this.sequence=sequence;
	
	}

	
	public static List<FastaSequence> readFastaFile(String filepath) throws Exception
	{
		BufferedReader reader = new BufferedReader(new FileReader(new File(filepath)));
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
		
		//declare list of object thats going to be returned
		ArrayList<FastaSequence> results = new ArrayList<FastaSequence>();
		//iterate through hash map containing parse fasta seq
		for (Map.Entry<String,String> entry : hash_map.entrySet()) 
        {
			//call object constructor with key value
			FastaSequence obj = new FastaSequence(entry.getKey(), entry.getValue());
			//add created object to a list
			results.add(obj);
        }
		
		reader.close();
		return results;
	}
	
	/*
	 * writes each unique sequence to the output file with the # of times each sequence was 
	 * Seen in the input file as the header (sorted with the sequence seen the fewest times the first)
	 */
	public static void writeUnique(File inFile, File outFile ) throws Exception
	{
	
		
		
		
	}
	
	// returns the header of this sequence without the “>”
	public String getHeader()
	{
		return header;	
	}
	
	// returns the Dna sequence of this FastaSequence
	public String getSequence() 
	{
		return sequence;
		
	}
	
	// returns the number of G’s and C’s divided by the length of this sequence
	public float getGCRatio()
	{
		float totalLength=sequence.length();
		float gcTotal=sequence.replace("A", "").replace("T", "").length();
		return gcTotal/totalLength;
	}
	
	
	
	
	
	public static void main(String[] args) throws Exception
	{
		List<FastaSequence> fastaList = FastaSequence.readFastaFile("/home/rosh/Desktop/example.fasta");
		
		for( FastaSequence fs : fastaList)
		{
			System.out.println(fs.getHeader());
			System.out.println(fs.getSequence());
			System.out.println(fs.getGCRatio());
		}
	}
	
	
	
}
