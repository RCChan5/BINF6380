package lab;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

//import java.util.Scanner;

//import java.io.File;
public class Lab3
{

	public static void fileWriter(ArrayList<String[]> results, String location) throws Exception
	{
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(location)));
			
			
			writer.write("ID	numA	numT	numC	numG	sequence\n");
			for(String[] a: results) {
				writer.write(a[0]+"	"+a[1]+"	"+a[2]+"	"+a[3]+"	"+a[4]+"	"+a[5]+"\n");
			}
						
			writer.flush();
			writer.close();
	}
	public static ArrayList<String[]> fastaReader(String location) throws Exception 
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
			
			
			ArrayList<String []> results = new ArrayList<String[]>();
			
			/*using for-each loop for iteration over Map.entrySet() fills the other arrays needed 
			 * nucleotides are counted by getting the original length of the sequence and replace all targeted elements in the string 
			 * with nothing thus reducing the strings length Using this method of counting the bases there is no for loop iterating 
			 * through the string and no if else chain and doesnt use ==
			 * 
			 *
			 */
	        for (Map.Entry<String,String> entry : hash_map.entrySet()) 
	        {
	        	Integer sequenceLength=entry.getValue().toString().length();
	        	String counter = entry.getValue();
	        	String[] inner = new String[6];
	        	
	        	//complexity for the counting step should be O(n)??
	        	Integer aCounter = sequenceLength-counter.replaceAll("A", "").length();
	        	Integer tCounter = sequenceLength-counter.replaceAll("T", "").length();
	        	Integer cCounter = sequenceLength-counter.replaceAll("C", "").length();
	        	Integer gCounter = sequenceLength-counter.replaceAll("G", "").length();
	        	
	        	//filling inner array
	        	inner[0]=entry.getKey().toString();
	        	inner[1]= aCounter.toString();
	        	inner[2]= tCounter.toString();
	        	inner[3]= cCounter.toString();   
	        	inner[4]= gCounter.toString();
	            inner[5]=entry.getValue().toString();
	        	
	            results.add(inner);
	            
	        }
				reader.close();
				return results;
				
	
	}

	public static int counter(String input) {
		
		return 1;
	}

	/*returns an array of unique elements from the array 
	 * this method was originally planed to be used and allow for user to input both amino acid sequences and nucleic and find 
	 * unique elemnts to be use in avoiding magic/hardcoded variables.
	 * 
	 * The usage of this method was scraped bc it would be faster to specify elements of interest than to compare every element of
	 * an array to another to get its unique elemnts
	 * 
	 * this code is left here incase future assignments need it
	 */
//	public static String[] getUniqueElements(String[] input) {
//		
//		//String[] uniqueElements = new String[];
//		StringBuffer uniqueElements = new StringBuffer();
//		
//		//iterate throuh array
//		for(int i=0;i<input.length;i++) {
//			//iterate through char in each element
//			
//			
//			
//		}
//		
//		for(int i=0;i<input.length;i++){
//            boolean isDistinct = false;
//            for(int j=0;j<i;j++){
//                if(input[i] == input[j]){
//                    isDistinct = true;
//                    break;
//                }
//            }
//            if(!isDistinct){
//            	uniqueElements.append(input[i])
//                System.out.print(input[i]+" ");
//            }
//        }
//		return null;
//	}
	
	public static void main(String[] args) throws Exception
	{
		String fasta = "/home/rosh/Desktop/example.fasta";
		String output = "/home/rosh/Desktop/output.fasta";
		
		fileWriter(fastaReader(fasta),output);
		
	}
	
}
