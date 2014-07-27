
/**
 * The comment and the rating system of the products.
 * Contains JTextArea,JLabels,submit button and cancel button.
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

public class commentP extends JFrame   
implements MouseListener,ActionListener,ItemListener
{
    private Font font=new Font("Serif",Font.BOLD,20);
    private JLabel r,r1;
    private String productid;
    private TextArea com,myCom;
    private Button submit= new Button("Submit");
    private static String words,words2;
    private static JButton rate1,rate2,rate3,rate4,rate5;
    private static double rateT;
    private Connection con;
    private Statement st;
    private ResultSet rs;
    private ResultSetMetaData rsmd;
    
    /**
     * The constructor of the class
     * Set up all the graphic user interface of this page
     * @param id is the ID of the product
     */
    public commentP(String id){
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
            rs = st.executeQuery("select rating,comment from product where ID="+id+"");
            if (rs.next()){
                rateT=rs.getDouble(1);
                words=rs.getString(2);
                words2=words;
            }
        }
        catch (SQLException f) 
        {f.printStackTrace();}
        
        if (words2==null){
            words2="";
        }
        com = new TextArea("", 500, 280, TextArea.SCROLLBARS_VERTICAL_ONLY);   
        while (words!=null && words.length()>0){
            String temp=words.substring(0,words.indexOf("#"));
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
                
        ImageIcon icon=new ImageIcon("rateb.png");
        Image img = icon.getImage().getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH);   
        icon = new ImageIcon(img);
        
        rate1=new JButton(icon);
        rate2=new JButton(icon);
        rate3=new JButton(icon);
        rate4=new JButton(icon);
        rate5=new JButton(icon);
        
        add(rate1); 
        rate1.setBounds(320,350,30,30);
        rate1.addMouseListener(this);
        
        add(rate2); 
        rate2.setBounds(350,350,30,30);        
        rate2.addMouseListener(this);
        
        add(rate3); 
        rate3.setBounds(380,350,30,30);       
        rate3.addMouseListener(this);
        
        add(rate4); 
        rate4.setBounds(410,350,30,30);
        rate4.addMouseListener(this);
        
        add(rate5); 
        rate5.setBounds(440,350,30,30);
        rate5.addMouseListener(this);       
        
        r=new JLabel("Rate:");
        add(r);
        r.setBounds(350,300,50,50);
        
        r1=new JLabel(Double.toString(rateT)+"/5");
        add(r1);
        r1.setBounds(400,300,50,50);
        
        this.setSize(502, 450);
        this.setResizable(false);
        this.setVisible(true);
    }    
    
    /**
     * Save the comment if the submit butttons is clicked
     * Save the rating of the product if one of the five buttons is clicked
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
                try{
                    words2=words2+s+"#";
                    st.executeUpdate("update product set comment='"+words2+"' where ID="+productid);
                }
                catch (SQLException f) 
                {f.printStackTrace();}
            }
        } else {
            if (e.getSource()==rate1){
                rateT=calRate(1);
                rateT=roundTwoDecimals(rateT);
                try{
                    st.executeUpdate("update product set rating='"+rateT+"' where ID="+productid);
                }
                catch (SQLException f) 
                {f.printStackTrace();}
            }
            if (e.getSource()==rate2){
                rateT=calRate(2);
                rateT=roundTwoDecimals(rateT);
                try{
                    st.executeUpdate("update product set rating='"+rateT+"' where ID="+productid);
                }
                catch (SQLException f) 
                {f.printStackTrace();}
            }
            if (e.getSource()==rate3){
                rateT=calRate(3);
                rateT=roundTwoDecimals(rateT);
                try{
                    st.executeUpdate("update product set rating='"+rateT+"' where ID="+productid);
                }
                catch (SQLException f) 
                {f.printStackTrace();}
            }
            if (e.getSource()==rate4){
                rateT=calRate(4);
                rateT=roundTwoDecimals(rateT);
                try{
                    st.executeUpdate("update product set rating='"+rateT+"' where ID="+productid);
                }
                catch (SQLException f) 
                {f.printStackTrace();}
            }      
            if (e.getSource()==rate5){
                rateT=calRate(5);
                rateT=roundTwoDecimals(rateT);
                try{
                    st.executeUpdate("update product set rating='"+rateT+"' where ID="+productid);
                }
                catch (SQLException f) 
                {f.printStackTrace();}
            }
            this.r1.setText(Double.toString(roundTwoDecimals(rateT))+"/5");
            productDis.r1.setText(Double.toString(roundTwoDecimals(rateT))+"/5");
            try{
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
            }
            catch (SQLException f) 
            {f.printStackTrace();}
        }
    }
    
    /**
     * Change the picture on rating buttons if mouse passes by
     */
    public void mouseExited(MouseEvent e) 
    {
        ImageIcon icon=new ImageIcon("rateb.png");
        Image img = icon.getImage().getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH);   
        icon = new ImageIcon(img);
        if (e.getSource()==rate1||e.getSource()==rate2||e.getSource()==rate3||e.getSource()==rate4||e.getSource()==rate5){
            rate1.setIcon(icon);
            rate2.setIcon(icon);
            rate3.setIcon(icon);            
            rate4.setIcon(icon);
            rate5.setIcon(icon);            
        }
    }
 
    /**
     * Change the picture on rating buttons if mouse passes by
     */
    public void mouseEntered(MouseEvent e) 
    {
        ImageIcon icon=new ImageIcon("rate.png");
        Image img = icon.getImage().getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH);   
        icon = new ImageIcon(img);
        if (e.getSource()==rate1){
            rate1.setIcon(icon);
        }
        if (e.getSource()==rate2){
            rate1.setIcon(icon);
            rate2.setIcon(icon);
        }
        if (e.getSource()==rate3){
            rate1.setIcon(icon);
            rate2.setIcon(icon);
            rate3.setIcon(icon);
        }
        if (e.getSource()==rate4){
            rate1.setIcon(icon);
            rate2.setIcon(icon);
            rate3.setIcon(icon);
            rate4.setIcon(icon);
        }
        if (e.getSource()==rate5){
            rate1.setIcon(icon);
            rate2.setIcon(icon);
            rate3.setIcon(icon);
            rate4.setIcon(icon);
            rate5.setIcon(icon);           
        }
    }  
    public void mouseReleased(MouseEvent e) {} 
    public void mousePressed(MouseEvent e) {} 
   
    public void actionPerformed(ActionEvent e)
    {
    } 
    public static void main(String[] args){        
    }
    
    /**
     * The method that calculates the average rating
     * @param i is the rating that the user just added
     */
    public static double calRate(int i){
        double dbl=(rateT+i)/2;
        return(dbl);            
    }
    
    /**
     * The method that round a double to two decimal places
     * @param d is the number that is rounded to two decimal places
     */
    public static double roundTwoDecimals(double d) {
            DecimalFormat twoDForm = new DecimalFormat("#.##");
            return Double.valueOf(twoDForm.format(d));
        }
    public void itemStateChanged(ItemEvent e){}
}
