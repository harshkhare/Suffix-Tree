package test;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SuffixTreeGUI extends JFrame{
   
    
   public SuffixTreeGUI()
   {
        setTitle("SuffixTree GUI"); 
        setSize(400,150);
        this.setLocation(200, 200);
        setResizable(false);
        
        Container cp = this.getContentPane();
        JPanel basePanel = new JPanel();
        basePanel.setLayout(new BorderLayout());
        
        JPanel ioPanel = new JPanel();
        JLabel ipLabel = new JLabel("Input string : ");
        final JTextField ipString = new JTextField(20);
        ioPanel.add(ipLabel);
        ioPanel.add(ipString);
        
        JPanel opPanel = new JPanel();
        JLabel fileLabel = new JLabel("Output file : ");
        final JTextField opFile = new JTextField(20);
        JButton writeButton = new JButton("write to file");
        JButton viewButton = new JButton("view");
        opPanel.add(fileLabel);
        opPanel.add(opFile);
        opPanel.add(writeButton);
        opPanel.add(viewButton);
        
        
//        browseButton.addActionListener(new ActionListener() 
//        {
//            public void actionPerformed(ActionEvent e)
            {
//                String filePath;
//              JFileChooser fileDialog = new JFileChooser();
//                fileDialog.setDialogType(fileDialog.OPEN_DIALOG);
//                filePath = fileDialog.getSelectedFile().getPath();
//                System.out.println("**" + filePath);
//            }
//        });
        
        viewButton.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
                    try 
                    {
                        Process p = Runtime.getRuntime().exec("F:\\Program Files\\Rod Page\\TreeView\\treev32.exe " + opFile.getText());
                    }
                    catch (IOException ex) 
                    {
                        Logger.getLogger(SuffixTreeGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
         });
        writeButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e)
            {
                SuffixTree t = new SuffixTree();
                try
                {
                    t.getNewickFormat(t.build(ipString.getText()));
                    t.writeTreeToFile(opFile.getText());
                    
                }
                catch(Exception ex)
                {
                    System.out.println(ex+"\nRequired fields can not be kept blank.");
                }
            }
        });
               
        basePanel.add(ioPanel, BorderLayout.NORTH);
        basePanel.add(opPanel, BorderLayout.CENTER);
        cp.add(basePanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }
}  
   public static void main(String args[])
   {
       new SuffixTreeGUI().setVisible(true);
   }
   
}

