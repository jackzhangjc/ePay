
/**
 * The display of the detailed information of a product that the user is going to buy
 * Contains name,quantity,picture,price,comment button and cancel button.
 * 
 * @author Daniel Wang
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

public class productDis extends JFrame   
implements MouseListener,ActionListener,ItemListener
{
    private Font font=new Font("Serif",Font.BOLD,20);
    private JLabel n,n1,q,q1,p,p1,d,pb,pb1,r;
    public static JLabel r1;
    private TextArea dis=new TextArea("");
    private Button comment= new Button("Comments");
    private Button cancel= new Button("Cancel");    
    private Button addTo= new Button("Add to my cart");    
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
     * Set up all the graphic user interface
     * @param id is the ID of the product that is going to be disaplayed
     */
    public productDis(String id){
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
        
        add(comment);
        comment.addMouseListener(this);
        comment.setBounds(120,310,70,20);
        
        add(addTo);
        addTo.addMouseListener(this);
        addTo.setBounds(220,310,90,20);
        
        add(cancel);
        cancel.addMouseListener(this);
        cancel.setBounds(340,310,50,20);
      
        this.setSize(600,380);
        this.setVisible(true);
    }    
    
    /**
     * Go to the comment page if comment button is clicked
     * Store the product ID in the ownedproduts table and quantity-1, if buy button is clicked
     */
    public void mouseClicked(MouseEvent e) 
    {
        if (e.getSource()==comment){
            commentP app=new commentP(productid);
        } else if (e.getSource()==addTo){
            if (quantity>0){
            try
            {    
                st.executeUpdate("insert into owned values('"+productid+"','"+myLogIn.id+"')");
                st.executeUpdate("update product set quantity=quantity-1 where ID="+productid);
                rs = st.executeQuery("select ID,name,price,rating,quantity,owner from product where owner<>'"+myLogIn.id+"'");
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
                    cells[0][i]=rsmd.getColumnLabel(i+1);                  
                }            
                rs.beforeFirst();      
                for(int i=0;i<rows;i++){      
                    if(rs.next()){                        
                        for(int j=0;j<columns;j++){
                            cells[i][j]=rs.getString(j+1);                  
                        }
                    }
                }
                Buy.model.setDataVector(cells,colNames);
                JOptionPane.showMessageDialog(null,
                name+" is added to your cart!",
                "Message",
                JOptionPane.PLAIN_MESSAGE);
                quantity=quantity-1;
                q1.setText(Integer.toString(quantity));
            }
            catch (SQLException f) 
            {f.printStackTrace();}
        } else {
            JOptionPane.showMessageDialog(null,
            "This product is out of stock!",
            "Sorry",
            JOptionPane.PLAIN_MESSAGE);
        }
        } else if (e.getSource()==cancel){
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
