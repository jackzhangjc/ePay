
/** 
 * The starting page of the program.
 * Contains name,version,company logo,teacher's name
 * 
 * @author (Jonathan Lai) 
 * @version (January 18th 2011)
 */
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.io.*;
import java.applet.*;
import java.applet.Applet;
import java.net.URL;
public class start extends JFrame
implements ActionListener
{
        javax.swing.Timer timer; 
        private static JProgressBar progressBar;
        private static JLabel percent,background,logo,name,project,version;
        private AudioClip audio;
        private JLayeredPane layeredPane;
        private ImageIcon icon;
        private Font font=new Font("Serif",Font.BOLD,20);
        private JPanel pan;
    
        /**
         * The constructor of the class
         */
        public start()
        {
            this.setLayout(null);
            pan=new JPanel();
            pan.setLayout(null);
            icon=new ImageIcon("background3.jpg");
            icon.setImage(icon.getImage().getScaledInstance(800,500,Image.SCALE_DEFAULT));
            background=new JLabel(icon);
            background.setBounds(0, 0,icon.getIconWidth(), icon.getIconHeight());
            
            name=new JLabel("By: Jack and Daniel      Teacher: Mr.Chan");
            name.setFont(font);
            name.setBounds(200,350,450,50);
            
            project=new JLabel("Final Project: ePay Online Shopping System");
            project.setFont(font);
            project.setBounds(200,300,400,50);
            
            version=new JLabel("Version 2.3 2010/01/16");
            version.setFont(font);
            version.setBounds(300,400,250,50);
            
            progressBar=new JProgressBar(JProgressBar.HORIZONTAL,0,300);
            progressBar.setBounds(250,250,250,25);
            percent=new JLabel("0.0%");
            percent.setFont(font);
            percent.setBounds(500,243,80,40);
            
            icon=new ImageIcon("epay.jpg");
            logo=new JLabel(icon);
            logo.setBounds(250,50,300,200);
            
            pan.add(progressBar);
            pan.add(percent);
            pan.add(name);
            pan.add(project);
            pan.add(version);
            pan.add(logo);
            progressBar.setStringPainted(true);
            progressBar.setString("Loadig....");
        
            try{           
                String filename="file:\\"+System.getProperty("user.dir")+"\\Book of days.mid";                
                URL url = new URL(filename);
                audio=Applet.newAudioClip(url);
            }catch( Exception ef){
                javax.swing.JOptionPane.showMessageDialog(null, "fall");
            } 
            this.getLayeredPane().setLayout(null);
            this.getLayeredPane().add(background, new Integer(Integer.MIN_VALUE));
            this.setContentPane(pan);
            pan.setOpaque(false);
            this.setBounds(250,50,800,500);
            this.setVisible(true);
            timer=new javax.swing.Timer(800,this); //Set up the timer
            timer.start();
            audio.play();
            setVisible(true);
        }
        
        /** 
         * The main method the class
         */
        public static void main(String[] args){
            start app=new start();
        }
        
        /**
         * Runs automatically with the timer.
         * @param e is the action
         */
        public void actionPerformed(ActionEvent e)
        {
            int cur=progressBar.getValue();
            progressBar.setValue(cur+30);
            percent.setText(progressBar.getPercentComplete()*100+"%"); // Change the value as time changes
            progressBar.repaint();
            if (cur==300)
            {
                timer.stop();
                audio.stop();
                setVisible(false);
                myLogIn app=new myLogIn();
             }
            
        }
}