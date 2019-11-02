package lab;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class test extends JFrame {

    private JPanel contentPane;
    private JTextField DisplayOne;
    

    int count;
    int count1;
    int count2;




    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() 
            {
                  try {
                    Lab5 frame = new Lab5();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }



    public test() 
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(1000, 1000, 200, 200);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.CENTER);

        DisplayOne = new JTextField();
        panel.add(DisplayOne);
        DisplayOne.setColumns(10);


        JButton btnCountOne = new JButton("Count");
        count1=0;
        btnCountOne.addActionListener(new ActionListener() {                
                public void actionPerformed(ActionEvent arg0) 
                {   
                    DisplayOne.setText(Integer.toString(count1++));
                }
        });
        panel.add(btnCountOne);
    }

}