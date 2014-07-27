
/**
 * The display of the detailed information of product that user owns
 * Contains name,price,quantity,picture and cancel button.
 * 
 * @Daniel
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

public class productDisOwn extends JFrame   
implements MouseListener,ActionListener,ItemListener
{
    private Font font=new Font("Serif",Font.BOLD,20);
    private JLabel n,n1,q,q1,p,p1,d,pb,pb1,r,r1;
    private TextArea dis=new TextArea("");
    private Button cancel= new Button("Cancel");     
    private String name,description,owner,picture;
    private double price,rating;
    private int quantity;
    private ImageIcon icon;
    private Connection con;
    private Statement st;
    private ResultSet rs;
    private ResultSetMetaData rsmd;
    private String productid;
    
    /**
     * The constructor of the class
     * @param id is the ID of the product
     */
    public productDisOwn(String id){
        super("Product Page");
        setLayout(null);
        
        productid=id;
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
            con = DriverManager.getConnection(database,"","");            
            st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            rs = st.executeQuery("select name,price,description,rating,quantity,owner,picture from product where ID="+id+"");
            if (rs.next()){
                name=rs.getString(1);
                price=rs.getDouble(2);
                description=rs.getString(3);
                rating=rs.getDouble(4);
                quantity=rs.getInt(5);
                owner=rs.getString(6);
                picture=rs.getString(7);
            }
        }
        catch (SQLException f) 
        {f.printStackTrace();}
            
        n=new JLabel("Name:");
        add(n);
        n.setBounds(10,10,50,20);
        
        n1=new JLabel(name);
        add(n1);
        n1.setBounds(100,10,100,20);
        
        q=new JLabel("Quantity:");
        add(q);
        q.setBounds(10,80,100,20);
        
        q1=new JLabel(Integer.toString(quantity));
        add(q1);
        q1.setBounds(100,80,50,20);
        
        p=new JLabel("Price:");
        add(p);
        p.setBounds(10,150,60,20);
        
        p1=new JLabel(Double.toString(price));
        add(p1);
        p1.setBounds(100,150,50,20);
        
        d=new JLabel("Description:");
        add(d);
        d.setBounds(10,220,80,20);                    
 
        
        pb=new JLabel("Picture:");
        add(pb);
        pb.setBounds(230,10,50,20);        
        
        if (picture!=null){
            icon=new ImageIcon(picture);
            icon.setImage(icon.getImage().getScaledInstance(170, 170,  java.awt.Image.SCALE_SMOOTH));      
            pb1 = new JLabel(icon);
            add(pb1);
            pb1.setBounds(230,40,200,150);
        }
        
        dis = new TextArea(description, 300, 80, TextArea.SCROLLBARS_VERTICAL_ONLY);
        dis.setEditable(false);
        add(dis);
        dis.setBounds(100,220,300,80);   
        
        r=new JLabel("Rating:");
        add(r);
        r.setBounds(10,310,50,20);
        
        r1=new JLabel(rating+"/5");
        add(r1);
        r1.setBounds(60,310,50,20);
        
        add(cancel);
        cancel.addMouseListener(this);
        cancel.setBounds(340,310,50,20);
      
        this.setSize(600,380);
        this.setVisible(true);
    }    
    
    /**
     * Make this page disappear
     */
    public void mouseClicked(MouseEvent e) 
    {
        if (e.getSource()==cancel){
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
   
    public void actionPerformed(ActionEvent e)
    {
    } 
    public static void main(String[] args){
    }
    public void itemStateChanged(ItemEvent e){}
}
