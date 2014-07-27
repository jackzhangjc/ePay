/** 
 * The shopping cart.
 * Contains a JTable that displays all the products the user has bought.
 * 
 * @author Daniel Wang
 * @version 2010/1/16
 */
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.io.*;
import java.sql.*;
public class ownedProduct extends JFrame
implements MouseListener,ItemListener
{
    private JButton search;
    private int sortIndex;
    private JLabel s,background,sort;
    private static Icon icon;
    private Font font;
    private String choice[]={"Price","Rating"};
    private JComboBox comboBox=new JComboBox(choice);
    private JTextField sea=new JTextField("",100);
    private JLayeredPane layeredPane;
    private JPanel pan;
    private JTable table;
    private String database,filename;
    private Connection con;
    private Statement st;
    private ResultSet rs;
    private ResultSetMetaData rsmd;
    private static DefaultTableModel model;
    
    /**
     * The constructor of the class
     */
    public ownedProduct(){
        super("ePay Online Shopping searching");
        
        this.setLayout(null);
        pan=new JPanel();
        pan.setLayout(null);
        
        
        try 
        {Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");} 
        catch (ClassNotFoundException f) {
            f.printStackTrace();
        } 
        try
        {    
            filename=System.getProperty("user.dir")+"/db1.mdb";
                    
            database = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=";
            database+=filename.trim()+ ";DriverID=22;READONLY=true}";   
            con = DriverManager.getConnection(database,"","");            
            st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            
            // Read from database and set up the JTable 
            rs = st.executeQuery("select owned.product,product.name,product.price,product.rating from owned,product where owned.buyer='"+myLogIn.id+"' and owned.product=product.id");
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
          font=new Font("Serif",Font.PLAIN,25);
          model = new DefaultTableModel(cells,colNames);
          table = new JTable(model){
              public boolean isCellEditable(int row,int column)
              {
                  return false;
                }
            };
          table.setFont(font);
          table.addMouseListener(this);
          table.setRowHeight(30);
          JScrollPane jp=new JScrollPane(table);
          font=new Font("Serif",Font.BOLD,25);
          table.getTableHeader().setFont(font);
          jp.setBounds(100,100,1200,600);
          pan.add(jp);
        }
        
        catch (SQLException f) 
        {f.printStackTrace();}
        
        font=new Font("Serif",Font.BOLD,25);
        icon=new ImageIcon("background.jpg");
        background=new JLabel(icon);
        background.setBounds(0, 0,icon.getIconWidth(), icon.getIconHeight());
        
        sort=new JLabel("Sort by:");
        sort.setFont(font);
        sort.setBounds(370,40,90,30);
        
        icon=new ImageIcon("search2.png");
        search=new JButton(icon);
        search.setBounds(600,35,icon.getIconWidth(), icon.getIconHeight());
        search.addMouseListener(this);
        
        s=new JLabel("Search:");
        s.setBounds(100,40,100,40);
        s.setFont(font);
        
        comboBox.setBounds(485,40,100,30);
        comboBox.addItemListener(this);
        
        sea.setBounds(220,44,120,30);
        
        pan.add(s);
        pan.add(sea);
        pan.add(search);
        pan.add(comboBox);
        pan.add(sort);
        
        this.getLayeredPane().setLayout(null);
        this.getLayeredPane().add(background, new Integer(Integer.MIN_VALUE));
        this.setContentPane(pan);
        pan.setOpaque(false);
        this.setVisible(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    
    /** 
     * The main method of the class
     */
    public static void main(String[] args){
        Buy app=new Buy();
        app.addWindowListener(new MyWindowListener());
    }
  
    /**
     * Display the search result
     * Show the product display page if the user double clicks a product
     */
    public void mouseClicked(MouseEvent e) 
    {
        if (e.getSource()==search){
            try
            {    
                String n=sea.getText();
                if (sortIndex==0){
                    rs=st.executeQuery("select owned.product,product.name,product.price,product.rating from owned,product where owned.buyer='"+myLogIn.id+"' and owned.product=product.id and name like'%"+n+"%' order by price");
                } else if (sortIndex==1){
                    rs=st.executeQuery("select owned.product,product.name,product.price,product.rating from owned,product where owned.buyer='"+myLogIn.id+"' and owned.product=product.id and name like'%"+n+"%' order by rating");
                }
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
                model.setDataVector(cells,colNames);
            }
            catch (SQLException f) 
            {f.printStackTrace();}
            } else{
            if (e.getButton()==MouseEvent.BUTTON1){
                if (e.getClickCount()==2){
                    int n=table.getSelectedRow();
                    String productid=table.getModel().getValueAt(n,0).toString();
                    productDisOwn app=new productDisOwn(productid);
                }
            }
        }
    }

    /**
     * Change the picture on the search botton
     */
    public void mouseExited(MouseEvent e) 
    {
        if (e.getSource()==search){
            icon=new ImageIcon("search2.png");
            search.setIcon(icon);
        }
     }
     
    /**
     * Change the picture on the search botton
     */
    public void mouseEntered(MouseEvent e) 
    {
        if (e.getSource()==search){
            icon=new ImageIcon("search1.png");
            search.setIcon(icon);
        }
    }  
    public void mouseReleased(MouseEvent e) {} 
    public void mousePressed(MouseEvent e) {} 
    
    /**
     * Change the value of sortIndex if the user change the JList
     */
    public void itemStateChanged(ItemEvent e){
        sortIndex=comboBox.getSelectedIndex();
    }
   
}
