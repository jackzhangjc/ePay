/**
 * Add a new product to the selling list.
 * Contains name,price,quantity,picture ready to be edited; save and cancel button
 * 
 * @author Jack Zhang
 * @version 2010/1/16
 */
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JOptionPane;
import javax.imageio.*;
import java.io.*;
import java.io.FileFilter.*;
import java.awt.Image;
import java.awt.Graphics.*;
import java.sql.*;
import java.awt.image.BufferedImage;
import java.applet.*; 
import java.awt.Container.*;

public class Sell extends JFrame   
implements MouseListener,ActionListener,ItemListener
{
    private Font font=new Font("Serif",Font.BOLD,20);
    private JTextField name=new JTextField("",10);
    private TextArea dis=new TextArea("");
    private JTextField quan=new JTextField("",10);
    private JTextField price=new JTextField("",10);    
    private JLabel n,q,d,p,pb,pic;
    private Button save= new Button("Save");
    private Button bro= new Button("Browse");    
    private String nameText,disText;
    private int quanNum; 
    private double priceNum;
    private JFileChooser fc;
    private JEditorPane jep = new JEditorPane();
    private String address="";
    private String picture;
    private ImageIcon icon;
    private int flag;
    private File f;
    private ResultSetMetaData rsmd;
    private ResultSet rs;
    
    
    /**
     * The constructor of the class
     * Set up all the graphic user interface
     */
    public Sell(){
        super("Selling Page");
        setLayout(null);
        
        font=new Font("Serif",Font.BOLD,20);    
        
        n=new JLabel("Name:");
        n.setFont(font);
        add(n);
        n.setBounds(10,10,70,20);
        
        pic=new JLabel();
        pic.setBounds(500,0,300,300);
        add(pic);
        
        add(name);
        name.setBounds(120,10,100,20);
        
        q=new JLabel("Quantity:");
        q.setFont(font);
        add(q);
        q.setBounds(10,80,100,20);
        
        add(quan);        
        quan.setBounds(120,80,50,20);
        
        p=new JLabel("Price:");
        p.setFont(font);
        add(p);
        p.setBounds(10,150,80,20);
        
        add(price);
        price.setBounds(120,150,50,20);
        
        d=new JLabel("Description:");
        d.setFont(font);
        add(d);
        d.setBounds(10,220,110,20);
                       
        dis = new TextArea("", 300, 80, TextArea.SCROLLBARS_VERTICAL_ONLY);
        add(dis);
        dis.setBounds(120,220,300,80);        
        
        pb=new JLabel("Picture:");
        pb.setFont(font);
        pb.setBounds(230,10,80,20);    
        add(pb);
        
        font=new Font("Serif",Font.PLAIN,20);    
        add(bro);
        bro.setFont(font);
        bro.setBounds(330,10,80,20);
        bro.addMouseListener(this);        
       
        add(save);
        save.setFont(font);
        save.setBounds(200,350,80,20);
        save.addMouseListener(this);        
        
        
        this.setBounds(100,100,800,500);
        this.setVisible(true);
    }    
    
    
    /**
     * Save all the information of this product when the save button is clicked
     */
    public void mouseClicked(MouseEvent e) 
    {
        if (e.getSource()==save){
            nameText=name.getText();         
            disText=dis.getText();
            int quanNum=Integer.parseInt(quan.getText());
            double p=Double.parseDouble(price.getText());
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
                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
                st.executeUpdate("insert into product (name,price,description,rating,owner,picture,quantity) values('"+nameText+"','"+p+"','"+disText+"','0','"+myLogIn.id+"','"+picture+"','"+quanNum+"')");
                rs = st.executeQuery("select ID,name,price,rating,quantity,owner from product where owner='"+myLogIn.id+"'");
                rsmd=rs.getMetaData();
                int columns=rsmd.getColumnCount();
                rs.last();
                int rows=rs.getRow();
                String[] colNames = {""};
                colNames=new String[columns];
                Object[][] cells={{""},{""}};
                cells=new Object[rows][columns];
                rs.first();
                for(int i=0;i<columns;i++)
                {      
                    colNames[i]=rsmd.getColumnLabel(i+1);                
                }            
                rs.beforeFirst();      
                for(int i=0;i<rows;i++){      
                    if(rs.next()){                        
                        for(int j=0;j<columns;j++){
                            cells[i][j]=rs.getString(j+1);                  
                        }
                    }
                }
                sellPage.model.setDataVector(cells,colNames);
            }
            catch (SQLException f) 
            {f.printStackTrace();}
            this.setVisible(false);
        }
        if (e.getSource()==bro){
            openPic();
        }
    }
    
    /** 
     * The method that make open file dialog appear
     * Set the picture of label to the picture user selects
     */
    public void openPic(){
        fc=new JFileChooser();
        fc.setDialogTitle("Open File");    
            try{     
                flag=fc.showOpenDialog(this);     
            }    
            catch(HeadlessException head){     
                System.out.println("Open File Dialog ERROR!");    
            }    
            if(flag==JFileChooser.APPROVE_OPTION)    
            {    
                f=fc.getSelectedFile();  
                picture=fc.getName(f);
                icon=new ImageIcon(picture);
                icon.setImage(icon.getImage().getScaledInstance(icon.getIconWidth()*150/icon.getIconHeight(), 150,Image.SCALE_DEFAULT));
                pic.setIcon(icon);
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
   
    public void actionPerformed(ActionEvent e)
    {
    } 
    public static void main(String[] args){
        Sell items=new Sell();
        items.addWindowListener(new MyWindowListener());
    }
    public void itemStateChanged(ItemEvent e){}
}
