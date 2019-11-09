package lab;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.*;



public class Lab6 extends JFrame 
{

	private static final long serialVersionUID = 1L;
	
	//basic frame
	private static JTextField answerTextField = new JTextField();
	//new button with name
	private static JLabel timerTextField = new JLabel();
	//private static JTextField timerTextField = new JTextField();
	private static JLabel questionText = new JLabel();
	private static JLabel scoreBoard = new JLabel();
	private static JButton startButton = new JButton("Begin Quiz");
	private static JButton cancelButton = new JButton("Cancel");
	private volatile static boolean Continue = true;
	private static final Random random = new Random();
	private static int right=0;
	private static int wrong=0;
	private String aminoAcidName;
	private String aminoCode;
	
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
			System.out.println("Start Button : " + Thread.currentThread().getName());
			startButton.setEnabled(false);
			cancelButton.setEnabled(true);
			questionText.setEnabled(true);
			answerTextField.setEditable(true);
			System.out.println("Start Button: Game Started");
			right=0;
			wrong=0;
			Continue = true;

			getQuestion();
			updateTextField("");
			updateScoreBoard(right, wrong);
			new Thread(new timer2()).start();
		}
	}
	
	private class answerListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e){
			String input = answerTextField.getText().toUpperCase();
			System.out.println("USER INPUT: "+input);
			if (Continue == true) {
			if (input.equals(aminoCode)) 				
			{
				right++;
				System.out.println("anwerListener: Correct");
			}
			else 
			{
				System.out.println("anwerListener: Wrong");
				wrong++;
			} 
			answerTextField.setText("");
			updateScoreBoard(right, wrong);
			getQuestion();
			
			}
		}
		}
	
	private class endGameListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e){
			//answerBox.setEditable(false);
			startButton.setEnabled(true);
			cancelButton.setEnabled(false);
			updateTextField("You got: Right: "+ right+ " Wrong: "+wrong+" Click Begin Quiz to play again.");
			questionText.setText("Your Quetion will appear here:");
			//updateStartGame("Start the game");
			answerTextField.setEditable(false);
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

	
	private static void updateTextField(String message)
	{
		answerTextField.setText(message);
	}
	
	private static void updateTimer(String message) 
	{
		timerTextField.setText(message);
	}
	
	private static void updateQuestion(String message) 
	{
		questionText.setText(message);
	}
	
	private static void updateScoreBoard(int right, int wrong) 
	{
		scoreBoard.setText("Right: "+right+" Wrong: "+wrong);
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
					updateTimer("Time Left: "+numTimes);
					Thread.sleep(1000);
				}
				updateTextField("You got: Right: "+right+" Wrong: "+wrong+" Click Begin Quiz to play again." );
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
	
	
	public void getQuestion()
	{
		if (Continue == true){
			int amino = random.nextInt(20);
		
			aminoAcidName = FULL_NAMES[amino];
			
			aminoCode = SHORT_NAMES[amino];
			
			
			updateQuestion("What is " + aminoAcidName + "?");
			System.out.println("getQuestion: "+aminoAcidName);
			System.out.println("getQuestion: "+aminoCode);
			}else {
				System.out.println("getQuestion: The random int has gone over array length this is imposible");
			}
	}
	
	public Lab6()
	{
		
		super("Amino Acid Quiz");
		setLocationRelativeTo(null);
		setSize(800,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(timerTextField, BorderLayout.NORTH);
		timerTextField.setText("You have 15 Seconds to solve as many questions as you can.");
		
		//score board
		getContentPane().add(scoreBoard, BorderLayout.WEST);
		
		//question box (right)
		getContentPane().add(questionText, BorderLayout.EAST);
		questionText.setText("Your Quetion will appear here:");
		
		//answer input box
		getContentPane().add(answerTextField, BorderLayout.CENTER);
		answerTextField.setText("Type Your Answer in here.");
		answerTextField.setEditable(false);
		answerTextField.addActionListener(new answerListener());
		
		//buttons
		getContentPane().add(getBottomPanel(), BorderLayout.SOUTH);
		

	
	setVisible(true);
		
	}
	
	
	public static void main(String[] args) 
	{
		new Lab6();
	}
   

}


