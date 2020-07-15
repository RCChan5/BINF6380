package lab;
//Questions
//Is storing all output as a string acceptable? what would be a more elegant way of doing it?
//
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


// to do (for lab 8
// move timer to its own thread? or keep it on the awt thread depends on if i time each thread or not?


public class Lab7 extends JFrame 
{

	private static final long serialVersionUID = 1L;
	//basic frame
	private static JTextField answerTextField = new JTextField();
	//new button with name
	private static JLabel topPanel = new JLabel();
	private static JTextArea ta = new JTextArea();
	private static JScrollPane CenterPanel = new JScrollPane(ta);
	private static JLabel rightPanel = new JLabel();
	private static JButton startButton = new JButton("Find Prime Numbers");
	private static JButton cancelButton = new JButton("Cancel");
	private volatile static boolean keepWorking = true;
	private volatile static Long userInput;
	private volatile static String totalFoundPrimes="===============================================\n";
	
	private class StartActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			// this panel gets user input
			new OptionPane();
			//log for visualizing what is happening
			System.out.println("LOG: Start Button : " + Thread.currentThread().getName());
			System.out.println("LOG: Start Button: Clicked");
			keepWorking = true;

			if (userInput != null) 
			{
				new Thread(new PrimeFinder()).start();
				System.out.println("LOG: StartActionListener " + userInput);
				startButton.setEnabled(false);
				cancelButton.setEnabled(true);
			}
			else {
				System.out.println("LOG: StartActionListener invalid input");
				startButton.setEnabled(true);
				cancelButton.setEnabled(false);
				
			}
		}
	}
	
	//Cancel button
	private class EndGameListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e){
			startButton.setEnabled(true);
			cancelButton.setEnabled(false);
			keepWorking = false;
			totalFoundPrimes+="Canceled\n";
			SwingUtilities.invokeLater(updateCenterPanel(totalFoundPrimes));
		}
	}

	//Method to update center panel text
	private static Runnable updateCenterPanel(String message)
	{
		return new Runnable() 
		{
			public void run() 
			{
				ta.append(message);
				
			}
		};
	}
	//for lab 8 adjust output message to take input from a synchronized array to view all threads progress
	private static Runnable updateRightPanel(String message)
	{
		return new Runnable() 
		{
			public void run() 
			{
				rightPanel.setText(message);
			}
		};
	}

	//Method to update top panel text
	private static Runnable updateTopPanel(String message) 
	{
		return new Runnable() 
		{
			public void run() 
			{
				//System.out.println("LOG: Update top panel : " + Thread.currentThread().getName());
				topPanel.setText(message);
				SwingUtilities.invokeLater(updateScroll());
			}
		};
	}
	//Method to update jscrollpanel to lowest point (not needed)
	private static Runnable updateScroll() 
	{
	return new Runnable() 
	{
		public void run() 
		{
			JScrollBar sb = CenterPanel.getVerticalScrollBar();
			sb.setValue( sb.getMaximum() );
			
		}
	};
	}
	//resets gui at the end of running it will also reset variables used
	private static Runnable endUpdate() 
	{
		return new Runnable() 
		{
			public void run() 
			{
				answerTextField.setEditable(false);
				startButton.setEnabled(true);
				cancelButton.setEnabled(false);
				keepWorking = false;
				userInput = null;
				totalFoundPrimes="===============================================\n";
				
			}
		};
	}
	
	private JPanel getBottomPanel()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
		panel.add(startButton);
		startButton.addActionListener(new StartActionListener());
		panel.add(cancelButton);
		cancelButton.setEnabled(false);
		cancelButton.addActionListener(new EndGameListener());
		
		return panel;
	}

	private static class PrimeFinder implements Runnable
	{
		public void run()
		{
			try
			{	
				long startTime = System.nanoTime();
				int counter =1;
				int primeFound = 0;
				while(keepWorking && counter<userInput)
				{
					if (isPrime(counter)) 
					{
						totalFoundPrimes+=counter+"\n";
						primeFound++;
						
					}
					counter++;
					//timer stuff
					long endTime = System.nanoTime();
					long duration = (endTime - startTime)/1000000;  //divide by 1000000 to get milliseconds.
					//updates the GUI every 1 second 
					if(duration%1000==0) {
						SwingUtilities.invokeLater(updateTopPanel("Inputed Number: "+userInput+"     Time Elapsed: "+duration/1000+" Seconds "+"     Primes Found: "+primeFound));
						SwingUtilities.invokeLater(updateRightPanel("{"+Thread.currentThread().getName()+"} Total primes found: "+primeFound+"\n"));
					}
				}
				long endTime = System.nanoTime();
				long duration = (endTime - startTime)/1000000;  //divide by 1000000 to get milliseconds.
				SwingUtilities.invokeLater(updateTopPanel("Inputed Number: "+userInput+"     Time Elapsed: "+duration/1000+" Seconds "+"     Primes Found: "+primeFound));
				SwingUtilities.invokeLater(updateCenterPanel(totalFoundPrimes));
				SwingUtilities.invokeLater(updateRightPanel("{"+Thread.currentThread().getName()+"} Total primes found: "+primeFound+"\n"));
				SwingUtilities.invokeLater(endUpdate());
				
				
			}
			catch(Exception ex)
			{
				System.out.println("Exception in PrimeFinder Method"+ex);
			}
		}

	}
	
	//method that returns true or false if inputed userInput is prime or not
	public static boolean isPrime(int num) {
		   if (num <= 1) {
		       return false;
		   }
		   for (int i = 2; i <= Math.sqrt(num); i++) {
		       if (num % i == 0) {
		           return false;
		       }
		   }
		   return true;
	}
	
	public static class OptionPane {  
		JFrame f;  
		OptionPane()
		{  
		    f=new JFrame();   
		    // this is to reset any prior user inputs
		    String input = null;
		    input =JOptionPane.showInputDialog(f,"Enter A Number Here: ");   	
		    System.out.println("LOG: OptionPane: "+input);
		    try 
		    {
		    	userInput = Long.parseLong(input);
		    	
		    }
		    catch(Exception e){
		    	System.out.println("LOG: OptionPane "+e);
		    }
		    
		}
	}
	
	public Lab7()
	{
		
		super("Prime Number Finder ");
		
		setSize(900,400);
		//default behavior is to simply hide the JFrame when the user closes the window
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//GUI layout
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(topPanel, BorderLayout.NORTH);
		getContentPane().add(CenterPanel, BorderLayout.CENTER);
		getContentPane().add(rightPanel, BorderLayout.EAST);
		topPanel.setText("Please Click [Find Prime Numbers] and input a value to search for all Prime numbers.\n");
		//puts gui to center of screen
		setLocationRelativeTo(null);
		//buttons
		getContentPane().add(getBottomPanel(), BorderLayout.SOUTH);

	setVisible(true);
		
	}

	public static void main(String[] args) 
	{
		new Lab7();
	}
   

}