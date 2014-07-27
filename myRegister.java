/** 
 * The register page of the program.
 * Contains account,password,name,email textfield; gender radio buttons; picture and register buttons
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
public class myRegister extends JFrame
implements MouseListener,ActionListener,ItemListener
{
    private JLabel account,password,name,email,gender,upload,pic,background;
    private JButton register,choose,clear;
    private static ImageIcon icon;
    private Font font=new Font("Serif",Font.BOLD,20);
    private JTextField pwd=new JTextField("",10); 
    private JTextField n=new JTextField("",10); 
    private JTextField mail=new JTextField("",10); 
    private JTextField acc=new JTextField("",10); 
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
     * Set up all the graphic interface
     */
    public myRegister(){
        super("ePay Online Shopping Register");

        fc=new JFileChooser(); 
        
        pan  = new JPanel();
        icon=new ImageIcon("background2.jpg");
        icon.setImage(icon.getImage().getScaledInstance(1750,1100,Image.SCALE_DEFAULT));
        background=new JLabel(icon);
        background.setBounds(0, 0,1360,700);
        
        pic=new JLabel();
        
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
        
        icon=new ImageIcon("registernow.png");
        register=new JButton(icon);
        register.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        register.addMouseListener(this);
        
        icon=new ImageIcon("clear1.png");
        clear=new JButton("Clear",icon);
        clear.setFont(font);
        clear.addMouseListener(this);
        
        box1=Box.createVerticalBox();
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
        
        male=new JRadioButton("Male",true);
        male.setFont(font);
        female=new JRadioButton("Female");
        female.setFont(font);
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
     * Save the information when the button is clicked
     */
    public void mouseClicked(MouseEvent e)
    {
        if (e.getSource()==register){
            String str1,str2,str3,str4;
            
            str1=acc.getText();
            str2=pwd.getText();
            if (str1.compareTo("")!=0 && str2.compareTo("")!=0){
            str3=n.getText();
            str4=mail.getText();
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
                ResultSet rs = st.executeQuery("select name from user where ID='"+str1+"'");
                if (!rs.next()){
                    st.executeUpdate("insert into user values('"+str1+"','"+str2+"','"+str3+"','"+gen+"','"+str4+"','"+picture+"')");
                    JOptionPane.showMessageDialog(null,
                    "Congratulation!",
                    "Message",
                    JOptionPane.PLAIN_MESSAGE);
                    this.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null,
                    "Account has already been used!",
                    "Message",
                    JOptionPane.PLAIN_MESSAGE);
                }
                //rs.close();
                st.close();
                con.close();
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
            fc.setDialogTitle("Open File");    // Pop out the file viewer
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
            acc.setText(""); 
            female.setSelected(false);
            male.setSelected(true);
            icon=new ImageIcon();
            pic.setIcon(icon);
            picture="";
            gen="";
        }
    }

    /** 
     * Change the picture on upload and clear botton when mouse exits
     */
    public void mouseExited(MouseEvent e) 
    {
        if (e.getSource()==choose){
            icon=new ImageIcon("upload1.png");
            choose.setIcon(icon);
        } else if(e.getSource()==clear){
            icon=new ImageIcon("clear1.png");
            clear.setIcon(icon);
        }
    }
    
    /** 
     * Change the picture on upload and clear botton when mouse enters
     */
    public void mouseEntered(MouseEvent e) 
    {
        if (e.getSource()==choose){
            icon=new ImageIcon("upload2.gif");
            choose.setIcon(icon);
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
     * Change the value of gender if the radio button is changed
     */
    public void itemStateChanged(ItemEvent e){
        if (e.getSource()==male) gen="Male"; 
          else if (e.getSource()==female) gen="Female";
    }
}