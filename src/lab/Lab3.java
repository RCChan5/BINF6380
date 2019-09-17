package lab;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
//import java.util.Scanner;

//import java.io.File;
public class Lab3
{

	public static void fileWriter(String location) throws Exception
	{
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(location)));
			
			writer.flush();
			writer.close();
	}
	public static String[] fastaReader(String location) throws Exception 
	{
			BufferedReader reader = new BufferedReader(new FileReader(new File(location)));
			StringBuffer nameStringBuf = new StringBuffer();
			StringBuffer seqStringBuf = new StringBuffer();
			// for loop to parse out name and seq into a stringbuffer
			for(String nextLine =reader.readLine(); nextLine != null;nextLine=reader.readLine()) 
			{	
				
				if (nextLine.startsWith(">")) 
				{	
					nameStringBuf.append(nextLine+"   ");	
				}
				else
				{
					seqStringBuf.append(nextLine+"   ");
				}
			}	
				
				//convert stringbuffer to string
				String seqArray[] = (seqStringBuf.toString()).split("   ");
				String nameArray[] = (nameStringBuf.toString()).split("   ");
				//ArrayList<String []> result = new ArrayList<String[]>();
				//result.add(innerArray);
				
				///////////////////////building each inner array//////////////////////
				//for loop iterates through string buffer array and splits it into 
				for(int i = 0; i < nameArray.length; i++) 
				{
					System.out.println(nameArray[i]);
					System.out.println(seqArray[i].trim());
					
				}
			
				System.out.println("end");
				
				reader.close();
				return nameArray;
				
	
	}
	public static String[] getUniqueElements(String[] input) {
		return null;
	}
	
	public static void main(String[] args) throws Exception
	{
		String location1 = "/home/rosh/Desktop/example.fasta";
		String[] holder = fastaReader(location1);
		
//		for(String a: holder) {
//			System.out.println("hello");
//			System.out.println(a);
//		}
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
