package lab;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.*;


public class Lab5 extends JFrame 
{

	private static final long serialVersionUID = 1L;
	//basic frame
	private JTextField aTextField = new JTextField();
	//new button with name
	private JButton doubleButton = new JButton("JButton");
	private JButton sad = new JButton("sad");
	private JButton das = new JButton("das");
	private int numDollars=1;
	private Random random = new Random();
	
	private class DoubleActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			if(random.nextFloat() < .5) 
			{
				numDollars = numDollars*2;
			}
			else 
			{
				numDollars= 0;
			}
			updateTextField();
		}
	}
	
	private void updateTextField()
	{
		aTextField.setText("part 2 You have $"+numDollars);
	}
	
	private JPanel getBottomPanel()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,3));
		panel.add(doubleButton);
		panel.add(sad);
		panel.add(das);
		
		doubleButton.addActionListener(new DoubleActionListener());
		return panel;
	}
	public Lab5()
	{
		super("Double Your Money!");
		setLocationRelativeTo(null);
		setSize(200,200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(getBottomPanel(), BorderLayout.SOUTH);
		//connects button to its function
		doubleButton.addActionListener(new DoubleActionListener());
		
		getContentPane().add(aTextField, BorderLayout.CENTER);
		aTextField.setText("You have $"+numDollars);
		setVisible(true);
	}
	public static void main(String[] args) 
	{
		new Lab5();
	}
   

}