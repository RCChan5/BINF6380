package lab;
//Questions
//Is storing all output as a string acceptable? what would be a more elegant way of doing it?
//
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicReference; 

// to do (for lab 8)
// should i limit how many theads one can use?
// fix output to a thread safe
// fix 

public class Lab8 extends JFrame 
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
	private static JButton threadButton = new JButton("Number of Threads");
	private volatile static boolean keepWorking = true;
	private volatile static Long userInput;
	private volatile static Integer threadInput = 1; 
	private static AtomicInteger counterTotal;
	private static AtomicIntegerArray outputStatus;
	
	
	private class StartActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			// this panel gets user input on what primes to find
			new OptionPane();			
			//log for visualizing what is happening
			System.out.println("LOG: Start Button : " + Thread.currentThread().getName());
			System.out.println("LOG: Start Button: Clicked");
			counterTotal = new AtomicInteger(0);
			//totalFoundPrimes = new AtomicReference("===============================================\n");
			keepWorking = true;
			if (userInput != null) 
			{
				// start timer 
//				Thread timer = new Thread(new Timer(), "Timer");
//				timer.start();
				outputStatus = new AtomicIntegerArray(threadInput);
				new Thread(new Timer(),"Timer").start();
				// this will be used to spawn multiple threads
				for (int i = 0; i < threadInput; i++) {
					  System.out.println(i);
					  new Thread(new PrimeFinder(i)).start();
					}
				//new Thread(new PrimeFinder()).start();// move this into loop
				System.out.println("LOG: StartActionListener " + userInput);
				startButton.setEnabled(false);
				threadButton.setEnabled(false);
				cancelButton.setEnabled(true);
			}
			else {
				System.out.println("LOG: StartActionListener invalid input");
				startButton.setEnabled(true);
				threadButton.setEnabled(true);
				cancelButton.setEnabled(false);
				
			}
		}
	}
	
	private class ThreadButtonActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			// this panel gets user input
			new OptionPane2();
			//log for visualizing what is happening
			//keepWorking = true;
			System.out.println("LOG USER INPUTED THREADS "+threadInput);

		
		}
	}
	
	//Cancel button
	private class EndGameListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e){
			startButton.setEnabled(true);
			cancelButton.setEnabled(false);
			keepWorking = false;
			String totalFoundPrimes="Canceled\n";
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
	private static Runnable updateRightPanel()
	{
		return new Runnable() 
		{
			public void run() 
			{
				
				String output="";
				for (int i = 0; i < threadInput; i++) 
				{
					  output=output+"<br/ Thread "+i+" primes found "+outputStatus.get(i);
					  
				}
				output="<html>"+output+"</html>";
				rightPanel.setText(output);
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
				threadButton.setEnabled(true);
				cancelButton.setEnabled(false);
				keepWorking = false;
				userInput = null;
				
				
			}
		};
	}
	
	private JPanel getBottomPanel()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,3));
		
		
		panel.add(threadButton);
		threadButton.addActionListener(new ThreadButtonActionListener());
		panel.add(startButton);
		startButton.addActionListener(new StartActionListener());
		panel.add(cancelButton);
		cancelButton.setEnabled(false);
		cancelButton.addActionListener(new EndGameListener());
		
		return panel;
	}

	
	private static class PrimeFinder implements Runnable
	{
		
		int number;
		public PrimeFinder(int num) {
			number = num;	
		}
		
		
		public void run()
		{
			try
			{	
				
				int counter = counterTotal.getAndAdd(1);
				String totalFoundPrimes="";
				while(keepWorking && counter<userInput)
				{
					if (isPrime(counter)) 
					{
						totalFoundPrimes+="Thread "+number+" "+counter+"\n";
						outputStatus.getAndIncrement(number);
					
					}
					counter = counterTotal.getAndAdd(1);
					
				}
				
				SwingUtilities.invokeLater(updateCenterPanel(totalFoundPrimes));
				SwingUtilities.invokeLater(updateRightPanel());
				SwingUtilities.invokeLater(endUpdate());
					
				
				
			}
			catch(Exception ex)
			{
				System.out.println("Exception in PrimeFinder Method "+ex);
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
	
	//Timer is moved to its own thread to avoid over loading the AWT thread
	private static class Timer extends Thread
	{
		public void run()
		{
		
			long startTime = System.nanoTime();
			while(keepWorking == true) {
				
				
				long endTime = System.nanoTime();
				long duration = (endTime - startTime)/1000000;  //divide by 1000000 to get milliseconds.
				if (duration%1000==0) {
					SwingUtilities.invokeLater(updateTopPanel("Inputed Number: "+userInput+"     Time Elapsed: "+duration+"mili Seconds "+"     Threads Used: "+threadInput));
					//System.out.println(outputStatus);
					//System.out.println("THIS IS NEW STUFF HERE "+Thread.currentThread().getName());
					//TODO update top panel here to return time 
				}
			}
			
			
		}
		
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
	public static class OptionPane2 {  
		JFrame f2;  
		OptionPane2()
		{  
		    f2=new JFrame();   
		    // this is to reset any prior user inputs
		    String input = null;
		    input =JOptionPane.showInputDialog(f2,"Enter the number of threads you want to use: ");   	
		    try 
		    {
		    	threadInput = Integer.parseInt(input);
		    	SwingUtilities.invokeLater(updateTopPanel("Inputed Number: "+userInput+"     Time Elapsed: 0 mili Seconds "+"     Threads Used: "+threadInput));
		    	
		    }
		    catch(Exception e){
		    	System.out.println("LOG: OptionPane "+e);
		    }
		    
		}
	}
	public Lab8()
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
		new Lab8();
	}
   

}