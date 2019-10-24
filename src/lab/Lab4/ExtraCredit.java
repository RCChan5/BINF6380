package lab.Lab4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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


/*
 * since FileReader can read one line at a time and basically keep its location while iterating through a text file.
 * I use that to and make it so that an object stores the FileReader object. Due to how fasta files are, i need a way to check 
 * when the sequence ends and while using FileReader which just iterates through so i wont be able to get the next seqID unless i 
 * move within the position of the text file hence why I used a variable to store seq ID's 
 * 
 * by doing this I can add methods like getCurrentSequence()
 * 
 * 2 objects are being used in this program the first is an object of the extracredit class and this instance holds the iterator for the fasta file
 * and the brains for knowing when to stop or not the other is the fastasequnce instance which holds the header and seq which is returned when getNextFasta
 * is called
 */
public class ExtraCredit
{
	//object/instance variables
	private BufferedReader reader;
	private String nextID = null;
	private String seq="";
	private FastaSequence instance;
	//constructor
public ExtraCredit(String location) throws Exception
	{
		this.reader = new BufferedReader(new FileReader(new File(location)));
		
	
	}

public FastaSequence getNextFastaSequence() throws Exception 
{	
	BufferedWriter writer = new BufferedWriter(new FileWriter(new File("/home/rosh/Desktop/output.fasta")));
	boolean stop = false;
	this.seq="";
	this.instance = new FastaSequence(nextID, seq);
	

	//while loop that stops when readlines hits a new > and stores the new id into memory for next get()
	while(stop == false) 
	{
		
		//System.out.println("loop start");
		String currentLine = reader.readLine();
		//System.out.println(currentLine);
		//ENDING last sequence 
		if (currentLine==null && nextID != null) {
			stop = true;
			//writer.append(nextID+"\n");
			//writer.append(seq);
		
			instance.setHeader(this.nextID);
			instance.setSequence(this.seq);
			this.nextID = null;
			//return null
		}
		else if(currentLine==null && nextID == null) 
		{
			System.out.println("There are no more sequences in the fasta file");
			break;
		}
		//first sequence
		else if (currentLine.startsWith(">") && nextID ==null) 
		{	
			this.nextID=currentLine;
		}
		
		else if (currentLine.startsWith(">") && nextID !=null) 
		{
			stop = true;
			instance.setHeader(nextID);
			instance.setSequence(seq);
			this.nextID = currentLine;			
		}
		//fasta body else
		else 
		{
			this.seq+=currentLine;
		}
	
	
	}	
	writer.flush();
	writer.close();
	
	return instance;
}
	
public static void main(String[] args) throws Exception
{
	
	
	ExtraCredit x1 = new ExtraCredit("/home/rosh/Desktop/example.fasta");

	
	FastaSequence hold=x1.getNextFastaSequence();
	System.out.println("1st fasta sequence");
	System.out.println(hold.getHeader());
	System.out.println(hold.getSequence());
	
	System.out.println("2nd fasta sequence");
	hold=x1.getNextFastaSequence();
	System.out.println(hold.getHeader());
	System.out.println(hold.getSequence());
	
	System.out.println("3nd time no sequence here");
	x1.getNextFastaSequence();
	System.out.println("done");
}

}
