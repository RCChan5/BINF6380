package lab;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.*;



public class Lab7 extends JFrame 
{

	private static final long serialVersionUID = 1L;
	
	//basic frame
	private static JTextField answerTextField = new JTextField();
	//new button with name
	private static JLabel timerTextField = new JLabel();
	
	private static JButton startButton = new JButton("Find Factorial");
	private static JButton cancelButton = new JButton("Cancel");
	private volatile static boolean Continue = true;
	private static Long number;

	
	public static String[] SHORT_NAMES = 
	{
		"A","R", "N", "D", "C", "Q", "E", 
		"G",  "H", "I", "L", "K", "M", "F", 
		"P", "S", "T", "W", "Y", "V" 
	};

	public static String[] FULL_NAMES = 
	{
		"alanine","arginine", "asparagine", 
		"aspartic acid", "cysteine",
		"glutamine",  "glutamic acid",
		"glycine" ,"histidine","isoleucine",
		"leucine",  "lysine", "methionine", 
		"phenylalanine", "proline", 
		"serine","threonine","tryptophan", 
		"tyrosine", "valine"
	};
	
	
	private class startActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			new OptionPane();
			System.out.println("Start Button : " + Thread.currentThread().getName());
			
			System.out.println("Start Button: Started");
		
			Continue = true;

			if (number != null) 
			{
				new Thread(new timer2()).start();
				System.out.println("StartActionListener " + number);
				startButton.setEnabled(false);
				cancelButton.setEnabled(true);
			}
			else {
				startButton.setEnabled(true);
				cancelButton.setEnabled(false);
				
			}
		}
	}
	
	
	private class endGameListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e){
			//answerBox.setEditable(false);
			startButton.setEnabled(true);
			cancelButton.setEnabled(false);
			Continue = false;
			
			try
			{  
				timer2.interrupt();  
			}
			catch(Exception e1)
			{
				System.out.println("endGameListener: Exception "+e1);
			}  
		
		}
	}


	private static void updateResults(String message) 
	{
		timerTextField.setText(message);
	}
	

	private JPanel getBottomPanel()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
		panel.add(startButton);
		startButton.addActionListener(new startActionListener());
		panel.add(cancelButton);
		cancelButton.setEnabled(false);
		cancelButton.addActionListener(new endGameListener());
		
		return panel;
	}

	private static class timer2 implements Runnable
	{
		public void run()
		{
			try
			{
				int numTimes =16;
				
				while(Continue && numTimes>0)
				{
					numTimes--;
					updateResults("Time Left: "+numTimes);
					Thread.sleep(1000);
				}
				//updateTextField("You got: Right: "+right+" Wrong: "+wrong+" Click Begin Quiz to play again." );
				answerTextField.setEditable(false);
				startButton.setEnabled(true);
				cancelButton.setEnabled(false);
				Continue = false;
			}
			catch(Exception ex)
			{
				System.out.println("exception in timer2 "+ex);
			}
		}

		public static void interrupt() {
			// TODO Auto-generated method stub
			System.out.println("timer was interputed");
		}
	}
	
	private static class factorial implements Runnable
	{
		public void run()
		{
			try
			{
				int numTimes =16;
				
				while(Continue && numTimes>0)
				{
					numTimes--;
					updateResults("Time Left: "+numTimes);
					Thread.sleep(1000);
				}
			
				answerTextField.setEditable(false);
				startButton.setEnabled(true);
				cancelButton.setEnabled(false);
				Continue = false;
			}
			catch(Exception ex)
			{
				System.out.println("exception in timer2 "+ex);
			}
		}

		public static void interrupt() {
			// TODO Auto-generated method stub
			System.out.println("timer was interputed");
		}
	}
	
	
	
	public static class OptionPane {  
		JFrame f;  
		OptionPane()
		{  
		    f=new JFrame();   
		    
		    String input=JOptionPane.showInputDialog(f,"Enter A Number Here: ");   	
		    System.out.println("OptionPane: "+input);
		    
		   
		    try 
		    {
		    	number = Long.parseLong(input);
		    	
		    }
		    catch(Exception e){
		    	System.out.println("OptionPane "+e);
		    }
		    
		}
	}
	
	public Lab7()
	{
		
		super("Amino Acid Quiz");
		setLocationRelativeTo(null);
		setSize(800,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(timerTextField, BorderLayout.CENTER);
		timerTextField.setText("Your working Factorial will apear here.");
		  
		//buttons
		getContentPane().add(getBottomPanel(), BorderLayout.SOUTH);
		

	
	setVisible(true);
		
	}
	
	public static void main(String[] args) 
	{
		new Lab7();
	}
   

}


