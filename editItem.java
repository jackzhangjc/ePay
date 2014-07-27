
/**
 * The editing page of the product that the user is selling.
 * Contains name,price,quantity,picture ready to be edited and saved.
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

public class editItem extends JFrame   
implements MouseListener,ActionListener,ItemListener
{
    private Font font=new Font("Serif",Font.BOLD,20);
    private JTextField name=new JTextField("",10);
    private TextArea dis=new TextArea("");
    private JTextField quan=new JTextField("",10);
    private JTextField price=new JTextField("",10);    
    private JLabel n,q,d,p,pb;
    private Button save= new Button("Save");
    private Button bro= new Button("Browse");    
    private Button cancel= new Button("Cancel");    
    private int quanNum; 
    private double priceNum;
    private JEditorPane jep = new JEditorPane();
    private String address="epay.gif";
    private JLabel addP;
    private static ImageIcon ii,newii;
    private static Image img;
    private String nameText,disText,picture,productid;
    private Connection con;
    private Statement st;
    private ResultSet rs;
    private ResultSetMetaData rsmd;
    private File f;    
    private JFileChooser fc;   
    private int flag; 
    private ImageIcon icon;
    
    /**
     * The constructor of the class
     * variable id is the ID of the product that is going to be edited
     * Set up the graphic user interface
     * @param id is the ID of the product
     */
    public editItem(String id){
        super("Edit Product");
        setLayout(null);
        productid=id;
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
                con = DriverManager.getConnection(database,"","");
                st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
                rs = st.executeQuery("select name,price,description,picture,quantity from product where ID="+id);
                if (rs.next()){
                    nameText=rs.getString(1);
                    priceNum=rs.getDouble(2);
                    disText=rs.getString(3);
                    picture=rs.getString(4);
                    quanNum=rs.getInt(5);
                }
             }
            catch (SQLException f) 
            {f.printStackTrace();}
            
        n=new JLabel("Name:");
        add(n);
        n.setBounds(10,10,50,20);
        
        name.setText(nameText);
        add(name);
        name.setBounds(100,10,100,20);
        
        q=new JLabel("Quantity:");
        add(q);
        q.setBounds(10,80,100,20);
        
        quan.setText(Integer.toString(quanNum));
        add(quan);        
        quan.setBounds(100,80,50,20);
        
        p=new JLabel("Price:");
        add(p);
        p.setBounds(10,150,60,20);
        
        price.setText(Double.toString(priceNum));
        add(price);
        price.setBounds(100,150,50,20);
        
        d=new JLabel("Description:");
        add(d);
        d.setBounds(10,220,80,20);
                       
        dis = new TextArea("", 300, 80, TextArea.SCROLLBARS_VERTICAL_ONLY);
        dis.setText(disText);
        add(dis);
        dis.setBounds(100,220,300,80);        
        
        pb=new JLabel("Picture:");
        add(pb);
        pb.setBounds(230,10,50,20);        
        
        add(bro);
        bro.setBounds(300,10,50,20);
        bro.addMouseListener(this);        
       
        add(save);
        save.setBounds(70,310,50,20);
        save.addMouseListener(this);     
        
        add(cancel);
        cancel.setBounds(270,310,50,20);
        cancel.addMouseListener(this);
        
        if (picture!=null){
            ii=new ImageIcon(picture);
            img = ii.getImage().getScaledInstance(150, 150,  java.awt.Image.SCALE_SMOOTH);                       
            newii = new ImageIcon(img); 
            addP=new JLabel(newii);
            add(addP);
            addP.setBounds(230,40,150,150);
        } else {
            addP=new JLabel();
            add(addP);
            addP.setBounds(230,40,150,150);
        }
            
        this.setSize(430,380);
        this.setResizable(false);
        this.setVisible(true);
    }    
    
    /**
     * Update all the information in the database when the save button is clicked
     */
    public void mouseClicked(MouseEvent e) 
    {
        if (e.getSource()==save){
            nameText=name.getText();         
            disText=dis.getText();
            quanNum=Integer.parseInt(quan.getText());
            priceNum=Double.parseDouble(price.getText());
            try
            {    
                st.executeUpdate("update product set name='"+nameText+"' where ID="+productid);
                st.executeUpdate("update product set price="+priceNum+" where ID="+productid);
                st.executeUpdate("update product set description='"+disText+"' where ID="+productid);
                st.executeUpdate("update product set picture='"+picture+"' where ID="+productid);
                st.executeUpdate("update product set quantity="+quanNum+" where ID="+productid);
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
            
        } else if (e.getSource()==bro){
            openPic();
        } else if(e.getSource()==cancel){
            this.setVisible(false);
        }
    }
    
    /**
     * The method that makes open file dialog appear
     */
    public void openPic(){
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
                addP.setIcon(icon);
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


