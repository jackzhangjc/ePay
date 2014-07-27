
/**
 * The comment page without the rating system
 * Contains two TextArea and a submit button.
 * 
 * @author Daniel Wang
 * @version 2010/1/16
 */
import java.io.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Container.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.sql.*;

public class commentSell extends JFrame   
implements MouseListener,ActionListener
{
    private Font font=new Font("Serif",Font.BOLD,20);
    private JLabel r,r1;
    private String productid;
    private TextArea com,myCom;
    private Button submit= new Button("Submit");
    private static String words,words2;
    private Connection con;
    private Statement st;
    private ResultSet rs;
    private ResultSetMetaData rsmd;
    
    
    /**
     * The constructor of the class
     * Set up all the graphic user interface
     * @param id is the ID of the product
     */
    public commentSell(String id){
        super("Comment");        
        
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
            rs = st.executeQuery("select comment from product where ID="+id+"");
            if (rs.next()){
                words=rs.getString(1);
                words2=words;
            }
        }
        catch (SQLException f) 
        {f.printStackTrace();}
        
        if (words2==null){
            words2="";
        }
        
        com = new TextArea("", 500, 280, TextArea.SCROLLBARS_VERTICAL_ONLY);   
        // Read the comment from database
        // Seperate the comments according to #
        while (words!=null && words.length()>0){
            String temp=words.substring(0,words.indexOf("#")-1);
            com.append("\n"+temp);
            com.append("\n"+" ");
            com.append("\n"+"-----------------------------");
            com.append("\n"+" ");
            words=words.substring(words.indexOf("#")+1,words.length());
        }
        add(com);
        com.setEditable(false);
        com.setBounds(0,0,500,280);
        
        myCom = new TextArea("", 300, 100, TextArea.SCROLLBARS_VERTICAL_ONLY);        
        add(myCom);
        myCom.setBounds(0,300,300,100);
        
        add(submit);
        submit.setBounds(0,400,50,20);
        submit.addMouseListener(this);
                 
        this.setSize(502, 450);
        this.setResizable(false);
        this.setVisible(true);
    }    
    
    /**
     * Save the comment when submit button is clicked
     */
    public void mouseClicked(MouseEvent e) 
    {
        if (e.getSource()==submit){
            String s=myCom.getText();
            if (s.compareTo("")!=0){
                com.append("\n"+s);
                com.append("\n"+" ");
                com.append("\n"+"-----------------------------");
                com.append("\n"+" ");
                myCom.setText("");
                words2=words2+s+"#"; // Use a "#" to seperate different comments
                try{
                    st.executeUpdate("update product set comment='"+words2+"' where ID="+productid); 
                }
                catch (SQLException f) 
                {f.printStackTrace();}
            }
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
}