
/**
 * The page that user can see what he/she is selling.
 * Contains a search box,search button,sorting list,JTable,add new item button,edit button,delete button.
 * 
 * @author Jack Zhang
 * @version 2010/1/16
 */
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.io.*;
import java.sql.*;
public class sellPage extends JFrame
implements MouseListener,ItemListener
{
    private JButton search,addNew,edit,delete;
    private int sortIndex;
    private JLabel s,background,sort;
    private static Icon icon;
    private Font font;
    private String choice[]={"Price","Rating","Quantity"};
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
    public static DefaultTableModel model;
    
    /**
     * The constructor of the class
     */
    public sellPage(){
        super("ePay Online Owned Products");
        
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
          table.setBounds(100,100,1200,400);
          font=new Font("Serif",Font.BOLD,25);
          table.getTableHeader().setFont(font);
          jp.setBounds(100,100,1200,400);
          pan.add(jp);
        }
        
        catch (SQLException f) 
        {f.printStackTrace();}
        
        font=new Font("Serif",Font.BOLD,25);
        icon=new ImageIcon("background.jpg");
        background=new JLabel(icon);
        background.setBounds(0, 0,icon.getIconWidth(), icon.getIconHeight());
        
        icon=new ImageIcon("add.png");
        addNew=new JButton("Add New Product",icon);
        addNew.setFont(font);
        addNew.setBounds(100,600,280,50);
        addNew.addMouseListener(this);
        
        icon=new ImageIcon("edit.png");
        edit=new JButton("Edit Product",icon);
        edit.setFont(font);
        edit.setBounds(440,600,250,50);
        edit.addMouseListener(this);
        
        icon=new ImageIcon("delete.png");
        delete=new JButton("Remove Product",icon);
        delete.setFont(font);
        delete.setBounds(720,600,270,50);
        delete.addMouseListener(this);
        
        
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
        pan.add(addNew);
        pan.add(edit);
        pan.add(delete);
        
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
  
    public void mouseClicked(MouseEvent e) 
    {
        if (e.getSource()==search){
            try
            {    
                String n=sea.getText();
                if (sortIndex==0){
                    rs = st.executeQuery("select ID,name,price,rating,quantity,owner from product where owner='"+myLogIn.id+"'and name like'%"+n+"%' order by price");
                } else if (sortIndex==1){
                    rs = st.executeQuery("select ID,name,price,rating,quantity,owner from product where owner='"+myLogIn.id+"'and name like'%"+n+"%' order by rating");
                } else if (sortIndex==2){
                    rs = st.executeQuery("select ID,name,price,rating,quantity,owner from product where owner='"+myLogIn.id+"'and name like'%"+n+"%' order by quantity");
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
            } else if (e.getSource()==addNew){
                Sell app=new Sell();
            } else if(e.getSource()==edit){
                int n=table.getSelectedRow();
                if (n>=0){
                    String productid=table.getModel().getValueAt(n,0).toString();
                    editItem app=new editItem(productid);
                }
            } else if(e.getSource()==delete){
                int s=JOptionPane.showConfirmDialog(null, "Are You Sure?","Message",JOptionPane.YES_NO_OPTION);
                if (s==JOptionPane.YES_OPTION){
                try{
                    int n=table.getSelectedRow();
                    String productid=table.getModel().getValueAt(n,0).toString();
                    st.executeUpdate("delete * from product where ID="+productid);
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
                    model.setDataVector(cells,colNames);
                }
            catch (SQLException f) 
            {f.printStackTrace();}
            } 
                
            } else {
                if (e.getButton()==MouseEvent.BUTTON1){
                if (e.getClickCount()==2){
                    int n=table.getSelectedRow();
                    String productid=table.getModel().getValueAt(n,0).toString();
                    productDisSell app=new productDisSell(productid);
                } 
            }
            }
    }

    
    public void mouseExited(MouseEvent e) 
    {
        if (e.getSource()==search){
            icon=new ImageIcon("search2.png");
            search.setIcon(icon);
        }
     }
 
    public void mouseEntered(MouseEvent e) 
    {
        if (e.getSource()==search){
            icon=new ImageIcon("search1.png");
            search.setIcon(icon);
        }
    }  
    public void mouseReleased(MouseEvent e) {} 
    public void mousePressed(MouseEvent e) {} 
    
    public void itemStateChanged(ItemEvent e){
        sortIndex=comboBox.getSelectedIndex();
    }
   
}
