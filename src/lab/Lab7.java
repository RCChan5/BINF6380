package lab;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;



public class Lab7 extends JFrame 
{

	private static final long serialVersionUID = 1L;
	//basic frame
	private static JTextField answerTextField = new JTextField();
	//new button with name
	private static JLabel topPanel = new JLabel();

	private static JTextArea ta = new JTextArea();
	private static JScrollPane CenterPanel = new JScrollPane(ta);
	private static JButton startButton = new JButton("Find Prime Number");
	private static JButton cancelButton = new JButton("Cancel");
	private volatile static boolean Continue = true;
	private static Long number;
	
	
	private class startActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			// this panel gets user input
			new OptionPane();
			//log
			System.out.println("LOG: Start Button : " + Thread.currentThread().getName());
			System.out.println("LOG: Start Button: Clicked");
			
			Continue = true;

			if (number != null) 
			{
				new Thread(new primeFinder()).start();
				System.out.println("LOG: StartActionListener " + number);
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
	
	
	private class endGameListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e){
			//answerBox.setEditable(false);
			startButton.setEnabled(true);
			cancelButton.setEnabled(false);
			Continue = false;
			
			try
			{  
				primeFinder.interrupt();  
			}
			catch(Exception e1)
			{
				System.out.println("endGameListener: Exception "+e1);
			}  
		
		}
	}

	//Method to update center panel text
	private static void updateCenterPanel(String message) 
	{
		ta.append(message);
	}
	//Method to update top panel text
	private static void updateTopPanel(String message) 
	{
		topPanel.setText(message);
		updateScroll();
	}
	//Method to update jscrollpanel to lowest point (not needed)
	private static void updateScroll() 
	{
	JScrollBar sb = CenterPanel.getVerticalScrollBar();
	sb.setValue( sb.getMaximum() );
	
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

	private static class primeFinder implements Runnable
	{
		public void run()
		{
			//to do rework timer 
			try
			{	
				long startTime = System.nanoTime();
				int counter =1;
				int primeFound = 0;
				updateTopPanel("user input "+number);
				
				while(Continue && counter<number+1)
				{
					if (isPrime(counter)) {
						updateCenterPanel("Prime found: "+counter+"\n");
						primeFound++;
					}
					counter++;
					long endTime = System.nanoTime();
					long duration = (endTime - startTime)/1000000;  //divide by 1000000 to get milliseconds.
					
					updateTopPanel("Inputed Number: "+number+"     Time Elapsed: "+duration+" Miliseconds "+"     Primes Found: "+primeFound);
				}
				//updateTextField("You got: Right: "+right+" Wrong: "+wrong+" Click Begin Quiz to play again." );
				
				//end conditions for gui
				answerTextField.setEditable(false);
				startButton.setEnabled(true);
				cancelButton.setEnabled(false);
				Continue = false;
			}
			catch(Exception ex)
			{
				System.out.println("exception in primeFinder "+ex);
			}
		}

		public static void interrupt() {
			// TODO Auto-generated method stub
			System.out.println("primeFinder was interputed");
			updateCenterPanel("Canceled");
		}
	}

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
		    
		    String input=JOptionPane.showInputDialog(f,"Enter A Number Here: ");   	
		    System.out.println("LOG: OptionPane: "+input);
		    
		   
		    try 
		    {
		    	number = Long.parseLong(input);
		    	
		    }
		    catch(Exception e){
		    	System.out.println("LOG: OptionPane "+e);
		    }
		    
		}
	}
	
	public Lab7()
	{
		
		super("Prime Number Finder ");
		setLocationRelativeTo(null);
		setSize(800,400);
		//default behavior is to simply hide the JFrame when the user closes the window
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(topPanel, BorderLayout.NORTH);
		getContentPane().add(CenterPanel, BorderLayout.CENTER);
		topPanel.setText("Click the Find Prime number button and input a number.\n");
		
		//buttons
		getContentPane().add(getBottomPanel(), BorderLayout.SOUTH);
		

	
	setVisible(true);
		
	}

	public static void main(String[] args) 
	{
		new Lab7();
	}
   

}





