/** 
 * The class that allows the user to log in.
 * Contains log in button, register button, id textfeld, passwrod textfield and logo
 * 
 * @author Jack Zhang
 * @version 2010/1/16
 */
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.sql.*;
public class myLogIn extends JFrame
implements MouseListener
{
    private JButton log,register,test;
    private JLabel logo,login,account,password,background;
    private Icon icon;
    private Font font=new Font("Serif",Font.BOLD,20);
    private JTextField acc=new JTextField("",10); 
    private JPasswordField pwd=new JPasswordField("",10);
    private Box box1,box2,box3,box4,box5,box6;
    private JLayeredPane layeredPane;
    private JPanel pan;
    public static String id,name,pass,gender,email,picture;
        
    /** 
     * The constructor the class
     * Set up the graphic interface
     */
    public myLogIn(){
        super("ePay Online Shopping");

        pan  = new JPanel();
        icon=new ImageIcon("background.jpg");
        background=new JLabel(icon);
        background.setBounds(0, 0,icon.getIconWidth(), icon.getIconHeight());
        
        icon=new ImageIcon("epay.jpg");
        logo=new JLabel(icon);
        
        login=new JLabel("Log In");
        login.setFont(font);
        background.add(login);
        account=new JLabel("Account:");
        account.setFont(font);
        
        password=new JLabel("Password:");
        password.setSize(200,200);
        password.setFont(font);
       
        icon=new ImageIcon("login1.gif");
        log=new JButton(icon);
        log.addMouseListener(this);
        log.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        
        icon=new ImageIcon("register1.gif");
        register=new JButton(icon);
        register.addMouseListener(this);
        register.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        
        box1=Box.createVerticalBox();
        box5=Box.createVerticalBox();
        box6=Box.createVerticalBox();
        box5.add(account);
        box5.add(Box.createVerticalStrut(10));
        box5.add(password);
        box6.add(acc);
        box6.add(Box.createVerticalStrut(10));
        box6.add(pwd);
        
        box2=Box.createHorizontalBox();
        box2.add(box5);
        box2.add(Box.createHorizontalStrut(10));
        box2.add(box6);
 
             
        box4=Box.createHorizontalBox();
        box4.add(log);
        box4.add(Box.createHorizontalStrut(200));
        box4.add(register);
        
        box1.add(Box.createVerticalStrut(200));
        box1.add(logo);
        box1.add(Box.createVerticalStrut(50));
        box1.add(box2);
        box1.add(Box.createVerticalStrut(30));
        box1.add(box4);
        pan.add(box1);
        this.getLayeredPane().setLayout(null); 
        this.getLayeredPane().add(background, new Integer(Integer.MIN_VALUE));
        this.setContentPane(pan); // Make the words appear on top of the background picture
        pan.setOpaque(false);
        setVisible(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    
    /** 
     * The main method of the class
     */
    public static void main(String[] args){
        myLogIn app=new myLogIn();
        app.addWindowListener(new MyWindowListener());
    }
    
    /** 
     * Go to next page if the user type in the correct account and password
     */
    public void mouseClicked(MouseEvent e) 
    {
        if (e.getSource()==log){
            id=acc.getText();
            String p=new String(pwd.getPassword());
            
            // sql languages
            // connect to Microsoft Acess 
            try 
            {Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");} 
            catch (ClassNotFoundException f) {
                f.printStackTrace();
            } 
            try
            {    
                String filename=System.getProperty("user.dir")+"/db1.mdb";
                
                String database = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=";
                database+=filename.trim()+ ";DriverID=22;READONLY=true}";   
                Connection con = DriverManager.getConnection(database,"","");
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("select * from user where ID='"+id+"'");
                if (rs.next()){
                    pass=rs.getString(2);
                    if (pass.compareTo(p)==0){
                            id=rs.getString(1);
                            name=rs.getString(3);
                            gender=rs.getString(4);
                            email=rs.getString(5);
                            picture=rs.getString(6);
                            myMenu app=new myMenu();
                            this.setVisible(false);
                        } else {
                            JOptionPane.showMessageDialog(null,
                            "Wrong Password!",
                            "Message",
                            JOptionPane.PLAIN_MESSAGE);
                        }
                    } else {
                    JOptionPane.showMessageDialog(null,
                        "Wrong Account!",
                        "Message",
                        JOptionPane.PLAIN_MESSAGE);
                    } 
                rs.close();
                st.close();
                con.close();
            }
             
            catch (SQLException f) 
            {f.printStackTrace();}
             
        } else {
            if (e.getSource()==register){
                myRegister app1=new myRegister();
            }
        }
    }

    /** 
     * Change the picture on the login and register buttons when mouse exited
     */
    public void mouseExited(MouseEvent e) 
    {
        if (e.getSource()==log){
            icon=new ImageIcon("Login1.gif");
            log.setIcon(icon);
        }
        if (e.getSource()==register){
            icon=new ImageIcon("register1.gif");
            register.setIcon(icon);
        }
    }
 
    /** 
     * Change the picture on the login and register buttons when mouse entered
     */
    public void mouseEntered(MouseEvent e) 
    {
        if (e.getSource()==log){
            icon=new ImageIcon("Login2.gif");
            log.setIcon(icon);
        }
        if (e.getSource()==register){
            icon=new ImageIcon("register2.gif");
            register.setIcon(icon);
        }
    }  
    public void mouseReleased(MouseEvent e) {} 
    public void mousePressed(MouseEvent e) {} 
   
}