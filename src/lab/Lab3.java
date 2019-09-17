package lab;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
//import java.util.ArrayList;
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
			StringBuffer inner = new StringBuffer();
			for(String nextLine =reader.readLine(); nextLine != null;nextLine=reader.readLine()) 
			{	
				
				if (nextLine.startsWith(">")) 
				{	
					inner.append(nextLine+"   ");	
				}
				else
				{
					inner.append(nextLine);
				}
			}	
				
				String holder =(inner.toString());
				System.out.println(holder);
				String x1[] = holder.split(">");
				reader.close();
				return x1;
				
	
	}
	public static String[] getUniqueElements(String[] input) {
		return null;
	}
	
	public static void main(String[] args) throws Exception
	{
		String location1 = "/home/rosh/Desktop/example.fasta";
		String[] holder = fastaReader(location1);
		
		for(String a: holder) {
			System.out.println("hello");
			System.out.println(a);
		}
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
