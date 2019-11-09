package lab;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Random;

import javax.swing.*;





public class Lab6 extends JFrame 
{

	private static final long serialVersionUID = 1L;
	
	//basic frame
	private JTextField answerTextField = new JTextField();
	//new button with name
	private JTextField timerTextField = new JTextField();
	//private static JTextField timerTextField = new JTextField();
	private JLabel questionText = new JLabel();
	private JLabel scoreBoard = new JLabel();
	private JButton startButton = new JButton("Begin Quiz");
	private JButton cancelButton = new JButton("Cancel");
	private volatile static boolean Continue = true;
	private static final Random random = new Random();
	private static final int SECONDS = 15;
	private int score=0;
	private int right=0;
	private int wrong=0;
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
	
	//currently working
	private class startActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
		//updateTextField(;
			
			startButton.setEnabled(false);
			//questionText.setEnabled(true);
			cancelButton.setEnabled(true);
			questionText.setEnabled(true);
			answerTextField.setEditable(true);
			System.out.println("Game Started");
			right=0;
			score=0;
			Continue = true;
			getQuestion();
			updateTextField("");
		}
	}
	//ideal
	private class StartGameListener implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			(new Thread(new Timer())).start();
//			startGame.setEnabled(false);
//			answerBox.setEnabled(true);
//			cancel.setEnabled(true);
			Continue = true;
			right = 0;		
			score = 0;
			getQuestion();
		}
	}
	
	
	private class answerListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e){
			String input = answerTextField.getText().toUpperCase();
			System.out.println("RIGHT HERE:"+input);
			if (Continue == true) {
			if (input.equals(aminoAcidName)) 				
			{
				right++;
				System.out.println("correct");
				//updateStartGame("Correct! " + right);
			}
			else 
			{
				System.out.println("Wrong");
				//updateStartGame("Incorrect! It's " + aminoAcidName + ".");
				wrong++;
			} 
			//count += 1;
			
			answerTextField.setText("");
			
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
			updateTextField("You got ".concat(Integer.valueOf(score).toString().concat(" correct! Click Begin to play again!")));
			questionText.setText("Your Quetion will appear here:");
			//updateStartGame("Start the game");
			Continue = false;
//			try{  
//			Timer.interrupt();  
//			}catch(Exception e1){System.out.println("Exception handled "+e1);}  
		
		}
	}
	
	private JMenuBar getMyMenuBar()
	{
		JMenuBar jmenuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		jmenuBar.add(fileMenu);
		
		JMenuItem saveItem = new JMenuItem("Save");
		fileMenu.add(saveItem);
		saveItem.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				saveToFile();
			}
		});
		return jmenuBar;
		
	}
	
	private void updateTextField(String message)
	{
		answerTextField.setText(message);
	}
	
	private void updateTimer(String message) 
	{
		timerTextField.setText(message);
	}
	
	private void updateQuestion(String message) 
	{
		questionText.setText(message);
	}
	
	private JPanel getBottomPanel()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
		panel.add(startButton);
		startButton.addActionListener(new startActionListener());
		panel.add(cancelButton);
		cancelButton.setEnabled(false);
		//add action listener to button to cancel
		return panel;
	}

	private void saveToFile()
	{
		JFileChooser jfc = new JFileChooser();
		
		if( jfc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION)
		{
			return;
		}
		if( jfc.getSelectedFile() == null)
		{
			return;
		}
		
		File chosenFile = jfc.getSelectedFile();
		
		if( jfc.getSelectedFile().exists()) 
		{
			String message = "File "+jfc.getSelectedFile().getName() + " exists. Overwrite?";
			if( JOptionPane.showConfirmDialog(this, message) != JOptionPane.YES_OPTION)
			{
				return;
			}
		}
		
		try
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter(chosenFile));
			writer.write(""+this.score+"\n");
			writer.flush();
//			writer.close();
		}
		
		catch(Exception ex)
		{
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Could not write file", JOptionPane.ERROR_MESSAGE);
		}
		
		
	}
	
	private class Timer implements Runnable
	{	
		@Override
		public void run() {
		try{		
			for (int i = SECONDS; i > 0; i--) {
				System.out.println("sleeping");
				Thread.sleep(1000);
				updateTimer(Integer.valueOf(i).toString());
			}
			updateTextField("Time's up! You correctly got ".concat(Integer.valueOf(right).toString().concat(" correct!")));
//			answerBox.setEditable(false);
//			startGame.setEnabled(true);
//			cancel.setEnabled(false);
//			Continue = false;
//			updateStartGame("Start the game");
			}catch(InterruptedException e){  
			throw new RuntimeException("Thread interrupted..."+e);  
			}
			
		}

		public void interrupt() {
			System.out.println("Stoped");
			
		}  
	}

	
	public void getQuestion()
	{
		if (Continue == true){
			int amino = random.nextInt(20);
			System.out.println(amino);
			aminoAcidName = FULL_NAMES[amino];
			System.out.println(amino);
			aminoCode = SHORT_NAMES[amino];
			System.out.println(right);
			
			updateQuestion("What is " + aminoAcidName + "?");
			System.out.println(aminoAcidName);
			}else {
				System.out.println("The random int has gone over array length this is imposible");
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
		
		getContentPane().add(scoreBoard, BorderLayout.WEST);
		
		
		getContentPane().add(questionText, BorderLayout.EAST);
		questionText.setText("Your Quetion will appear here:");
		getContentPane().add(answerTextField, BorderLayout.CENTER);
		answerTextField.setText("Type Your Answer in here.");
		
		answerTextField.setEditable(false);
		answerTextField.addActionListener(new answerListener());
		
		setJMenuBar(getMyMenuBar());
		getContentPane().add(getBottomPanel(), BorderLayout.SOUTH);
		cancelButton.addActionListener(new endGameListener());

	
	setVisible(true);
		
	}
	
	
	public static void main(String[] args) 
	{
		new Lab6();
	}
   

}
