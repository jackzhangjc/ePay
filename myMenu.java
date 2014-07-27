/** 
 * User's menu.
 * Contains user's name,profile picture; buy,sell,
 * 
 * @author Jack Zhang
 * @version 2010/1/16
 */
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
public class myMenu extends JFrame
implements MouseListener
{
    private JButton buy,sell,editProfile,owned,logOut;
    private JPanel pan;
    private JLabel name,line,money,background,pic;
    private ImageIcon icon;
    private Font font;
    
    /** 
     * The constructer of the class
     * Set up all the graphic interface and read the name,picture from data base
     */
    public myMenu(){
        super("User's Main Menu");
        
        this.setLayout(null);
        pan=new JPanel();
        pan.setLayout(null);
        
        font=new Font("Serif",Font.BOLD,20);        
        if (myLogIn.picture!=null){
            icon=new ImageIcon(myLogIn.picture);
            icon.setImage(icon.getImage().getScaledInstance(icon.getIconWidth()*300/icon.getIconHeight(), 300,Image.SCALE_DEFAULT));
            pic=new JLabel(icon);
            pic.setBounds(800,40,icon.getIconWidth(), icon.getIconHeight());
            pan.add(pic);
        }
        
        icon=new ImageIcon("buy.gif");
        buy=new JButton("Buy",icon);
        buy.setBounds(50,400,120,50);
        buy.setFont(font);
        buy.addMouseListener(this);
                
        icon=new ImageIcon("sell.gif");
        sell=new JButton("Sell",icon);
        sell.setBounds(240,400,120,50);
        sell.setFont(font);
        sell.addMouseListener(this);
        
        icon=new ImageIcon("edit.png");
        editProfile=new JButton("Edit Profile",icon);
        editProfile.setFont(font);
        editProfile.setBounds(800,400,200,50);
        editProfile.addMouseListener(this);
        
        icon=new ImageIcon("products.png");
        owned=new JButton("Shopping Cart",icon);
        owned.setFont(font);
        owned.setBounds(450,400,250,50);
        owned.addMouseListener(this);
        
        icon=new ImageIcon("logout.png");
        logOut=new JButton("Log Out",icon);
        logOut.setFont(font);
        logOut.setBounds(1100,400,170,50);
        logOut.addMouseListener(this);
        
        icon=new ImageIcon("line.gif");
        line=new JLabel(icon);
        line.setBounds(50,150,680,20);
        
        name=new JLabel("Welcome, "+myLogIn.name);
        font=new Font("Lucida Handwriting",Font.BOLD,40);
        name.setFont(font);
        name.setBounds(50,50,1200,100);
        
        icon=new ImageIcon("background.jpg");
        background=new JLabel(icon);
        background.setBounds(0, 0,icon.getIconWidth(), icon.getIconHeight());
        
        pan.add(name);
        pan.add(line);
        pan.add(buy);
        pan.add(sell);
        pan.add(owned);
        pan.add(editProfile);
        pan.add(logOut);
        
        this.getLayeredPane().setLayout(null);
        this.getLayeredPane().add(background, new Integer(Integer.MIN_VALUE));
        this.setContentPane(pan);
        pan.setOpaque(false);
        this.setVisible(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
 }


    /** 
     * The main method of the class.
     */
    public static void main(String[] args){
        myMenu app=new myMenu();
        app.addWindowListener(new MyWindowListener());
    }
    
    /** 
     * Go to the corresponding page when user clicks one of the five buttons
     */
    public void mouseClicked(MouseEvent e) 
    {
        if (e.getSource()==buy){
            Buy app=new Buy();
        } else if (e.getSource()==sell){
            sellPage app=new sellPage();
        } else if (e.getSource()==editProfile){
            editProfile app=new editProfile();
            app.addWindowListener(new MyWindowListener());
            this.setVisible(false);
        } else if (e.getSource()==owned){
            ownedProduct app=new ownedProduct();
        } else if (e.getSource()==logOut){
            myLogIn app=new myLogIn();
            this.setVisible(false);
        }
    }

    
    public void mouseExited(MouseEvent e) 
    {
        
    }
 
    public void mouseEntered(MouseEvent e) 
    {
       
    }  
    public void mouseReleased(MouseEvent e) {} 
    public void mousePressed(MouseEvent e) {} 
}
