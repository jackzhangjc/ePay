/** 
 * The page that allows users to edit their profile information
 * Contains user's name,account,password,email,gender and profile picture ready to be edited and saved.
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
public class editProfile extends JFrame
implements MouseListener,ActionListener,ItemListener
{
    private JLabel account,password,name,email,gender,upload,pic,background,acc;
    private JButton register,choose,clear;
    private static ImageIcon icon;
    private Font font=new Font("Serif",Font.BOLD,20);
    private JTextField pwd,n,mail;
    private String str1,str2,str3,str4,str5,str6;
    private JRadioButton male,female;
    private ButtonGroup buttonGroup;
    private Box box1,box2,box3,box4,box5,box6,box7,box8,box9,box10;
    private String gen,picture;
    private File f;    
    private JFileChooser fc;   
    private int flag; 
    private JLayeredPane layeredPane;
    private JPanel pan; 
    
    /**
     * The constructor of the class
     * Read all the informaation of the user from database
     */
    public editProfile(){
        super("ePay Online Shopping Editing Profile");

        fc=new JFileChooser(); 
        
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
                ResultSet rs = st.executeQuery("select * from user where ID='"+myLogIn.id+"'");
                if (rs.next()){
                    str1=rs.getString(1);
                    str2=rs.getString(2);
                    str3=rs.getString(3);
                    str4=rs.getString(4);
                    str5=rs.getString(5);
                    str6=rs.getString(6);
                }
             }
            catch (SQLException f) 
            {f.printStackTrace();}
            
        pan  = new JPanel();
        icon=new ImageIcon("background2.jpg");
        icon.setImage(icon.getImage().getScaledInstance(1750,1100,Image.SCALE_DEFAULT));
        background=new JLabel(icon);
        background.setBounds(0, 0,1360,700);
        
        pic=new JLabel();
        if (str6!=null){
            icon=new ImageIcon(str6);
            icon.setImage(icon.getImage().getScaledInstance(icon.getIconWidth()*300/icon.getIconHeight(), 300,Image.SCALE_DEFAULT)); 
            pic.setIcon(icon);
        }
        
        acc=new JLabel(str1);
        acc.setFont(font);
        pwd=new JTextField(str2,10);
        n=new JTextField(str3,10);
        mail=new JTextField(str5,10);
        
        account=new JLabel("Account:");
        account.setFont(font);
        
        password=new JLabel("Password:");
        password.setFont(font);
        
        name=new JLabel("Name:");
        name.setFont(font);
        
        email=new JLabel("E-mail:");
        email.setFont(font);
        
        gender=new JLabel("Gender:");
        password.setFont(font);
        
        upload=new JLabel("Upload Profile Picture:");
        upload.setFont(font);
        
        icon=new ImageIcon("upload1.png");
        choose=new JButton(icon);
        choose.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()+5));
        choose.addMouseListener(this);
        
        icon=new ImageIcon("save1.png");
        register=new JButton(icon);
        register.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        register.addMouseListener(this);
        
        icon=new ImageIcon("clear1.png");
        clear=new JButton("Clear",icon);
        register.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        clear.setFont(font);
        clear.addMouseListener(this);
        
        box1=Box.createVerticalBox(); // Use boxes to set the layout of page
        box2=Box.createHorizontalBox();
        box3=Box.createHorizontalBox();
        box4=Box.createHorizontalBox();
        box5=Box.createHorizontalBox();
        box6=Box.createHorizontalBox();
        box7=Box.createHorizontalBox();
        box8=Box.createHorizontalBox();
        box9=Box.createHorizontalBox();
        box10=Box.createHorizontalBox();
        
        box2.add(account);
        box2.add(Box.createHorizontalStrut(10));
        box2.add(acc);
        
        box3.add(password);
        box3.add(Box.createHorizontalStrut(10));
        box3.add(pwd);
        
        box4.add(name);
        box4.add(Box.createHorizontalStrut(10));
        box4.add(n);

        box5.add(email);
        box5.add(Box.createHorizontalStrut(10));
        box5.add(mail);
        
        if (str4.compareTo("Male")==0){
            male=new JRadioButton("Male",true);
            female=new JRadioButton("Female",false);
        } else if (str4.compareTo("Female")==0){
            female=new JRadioButton("Female",true);
            male=new JRadioButton("Male",false);
        } else {
            female=new JRadioButton("Female");
            male=new JRadioButton("Male");
        }
        female.setFont(font);
        male.setFont(font);
        male.addItemListener(this);
        female.addItemListener(this);
        buttonGroup=new ButtonGroup();
        buttonGroup.add(male);
        buttonGroup.add(female);
        box6.add(male);
        box6.add(Box.createHorizontalStrut(80));
        box6.add(female);
        
        box7.add(upload);
        box7.add(Box.createHorizontalStrut(70));
        box7.add(choose);
        
        box8.add(pic);
        
        box10.add(clear);
        box10.add(Box.createHorizontalStrut(50));
        box10.add(register);
        
        box1.add(Box.createVerticalStrut(100));
        box1.add(box2);
        box1.add(Box.createVerticalStrut(10));
        box1.add(box3);
        box1.add(Box.createVerticalStrut(10));
        box1.add(box4);
        box1.add(Box.createVerticalStrut(10));
        box1.add(box5);
        box1.add(Box.createVerticalStrut(10));
        box1.add(box6);
        box1.add(Box.createVerticalStrut(10));
        box1.add(box7);
        box1.add(Box.createVerticalStrut(10));
        box1.add(box10);
        box9.add(box1);
        box9.add(Box.createHorizontalStrut(20));
        box9.add(box8);
        pan.add(box9);
        this.getLayeredPane().setLayout(null);
        this.getLayeredPane().add(background, new Integer(Integer.MIN_VALUE));
        this.setContentPane(pan);
        pan.setOpaque(false);
        //setSize(1000,1000);
        setVisible(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
    }
    
    /**
     * Modify the information in database that has been changed.
     */
    public void mouseClicked(MouseEvent e)
    {
        if (e.getSource()==register){
            String s2,s3,s4;
            
            s2=pwd.getText();
            if (s2.compareTo("")!=0){
            s3=n.getText();
            s4=mail.getText();
            //User u=new User(str1,str2,str3,gen,str4);
            //myLogIn.users.add(u);
            
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
                myLogIn.pass=s2;
                st.executeUpdate("update user set password='"+s2+"' where ID='"+myLogIn.id+"'");
                myLogIn.name=s3;
                st.executeUpdate("update user set name='"+s3+"' where ID='"+myLogIn.id+"'");
                if (gen==null){
                    gen=str4;
                }
                myLogIn.gender=gen;
                st.executeUpdate("update user set gender='"+gen+"' where ID='"+myLogIn.id+"'");
                myLogIn.email=s4;
                st.executeUpdate("update user set email='"+s4+"' where ID='"+myLogIn.id+"'");
                if (picture==null){
                    picture=str6;
                }
                myLogIn.picture=this.picture;
                st.executeUpdate("update user set picture='"+picture+"' where ID='"+myLogIn.id+"'");
                JOptionPane.showMessageDialog(null,
                "Information Saved!",
                "Message",
                JOptionPane.PLAIN_MESSAGE);
                this.setVisible(false);
                 //rs.close();
                st.close();
                con.close();
                myMenu app=new myMenu();
             }
            catch (SQLException f) 
            {f.printStackTrace();}
        } else {
            JOptionPane.showMessageDialog(null,
                    "Information not completed!",
                    "Message",
                    JOptionPane.PLAIN_MESSAGE);
                }
        } else if (e.getSource()==choose){
            fc.setDialogTitle("Open File");    
            try{     
                flag=fc.showOpenDialog(box1);     
            }    
            catch(HeadlessException head){     
                System.out.println("Open File Dialog ERROR!");    
            }    
            if(flag==JFileChooser.APPROVE_OPTION)    
            {    
                f=fc.getSelectedFile();  
                picture=fc.getName(f);
                icon=new ImageIcon(picture);
                icon.setImage(icon.getImage().getScaledInstance(icon.getIconWidth()*300/icon.getIconHeight(), 300,Image.SCALE_DEFAULT));
                pic.setIcon(icon);
            }
        } else if (e.getSource()==clear){
            pwd.setText(""); 
            n.setText(""); 
            mail.setText("");  
            female.setSelected(false);
            male.setSelected(true);
            icon=new ImageIcon();
            pic.setIcon(icon);
            picture="";
            gen="";
        }
    }

    /**
     * Change the picture of choose,register,clear button
     */
    public void mouseExited(MouseEvent e) 
    {
        if (e.getSource()==choose){
            icon=new ImageIcon("upload1.png");
            choose.setIcon(icon);
        } else if (e.getSource()==register){
            icon=new ImageIcon("save1.png");
            register.setIcon(icon);
        } else if(e.getSource()==clear){
            icon=new ImageIcon("clear1.png");
            clear.setIcon(icon);
        }
    }
    
    /**
     * Change the picture of choose,register,clear button
     */
    public void mouseEntered(MouseEvent e) 
    {
        if (e.getSource()==choose){
            icon=new ImageIcon("upload2.gif");
            choose.setIcon(icon);
        } else if (e.getSource()==register){
            icon=new ImageIcon("save2.png");
            register.setIcon(icon);
        } else if(e.getSource()==clear){
            icon=new ImageIcon("clear2.png");
            clear.setIcon(icon);
        }
    }  
    public void mouseReleased(MouseEvent e) {} 
    public void mousePressed(MouseEvent e) {} 
   
    public void actionPerformed(ActionEvent e)
    {
    } 
    
    /**
     * Change the value of gender when user change the radio button
     */
    public void itemStateChanged(ItemEvent e){
        if (e.getSource()==male) gen="Male"; 
          else if (e.getSource()==female) gen="Female";
    }
}