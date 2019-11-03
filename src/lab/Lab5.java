package lab;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javax.swing.*;


public class Lab5 extends JFrame 
{

	private static final long serialVersionUID = 1L;
	//basic frame
	private JTextField aTextField = new JTextField();
	//new button with name
	private JButton increaseButton = new JButton("Click Here");
	private int score=0;
		
	private class increaseActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			score++;
			updateTextField();
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
	
	private void updateTextField()
	{
		if(score == 100000)
		{
		aTextField.setText("!!SUPRISE!! Keep Going for another Surprise at 2,147,483,647" + score);
		}
		else
		{
		aTextField.setText("Current Clicks: " + score);
		}
	}
	
	private JPanel getBottomPanel()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,1));
		panel.add(increaseButton);
		//panel.add(sad);
		//panel.add(das);
		
		increaseButton.addActionListener(new increaseActionListener());
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
			writer.close();
		}
		
		catch(Exception ex)
		{
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Could not write file", JOptionPane.ERROR_MESSAGE);
		}
		
		
	}
	
	public Lab5()
	{
		super("The Boring Game");
		setLocationRelativeTo(null);
		setSize(400,300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(getBottomPanel(), BorderLayout.SOUTH);
		getContentPane().add(aTextField, BorderLayout.CENTER);
		aTextField.setText("Click the Button 1,000,000 times for a surprize.");
		setJMenuBar(getMyMenuBar());
		setVisible(true);
	}
	
	
	public static void main(String[] args) 
	{
		new Lab5();
	}
   

}

