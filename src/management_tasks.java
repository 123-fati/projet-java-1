import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.desktop.SystemEventListener;

import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.ResultSetMetaData;


import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.jar.Attributes.Name;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;

public class management_tasks {

	private JFrame frame;
	private JPanel contentPane;
	private JTextField textField_name;
	private JTextField textField_description;
	private JTextField day;
	private JTable table;
	private JTable table_1;
	private JTextField textField_name_1;
	private JTextField textField_description_1;
	private JTextField day_1;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					management_tasks window = new management_tasks();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws SQLException 
	 */
	public management_tasks() throws SQLException {
		initialize();
		connect();
		fetch();
	}
	
	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	PreparedStatement pst1;
	ResultSet rs1;
	private JTable table_categories;

	private JTable table_2;
	private JTextField textField;

	private JTextField cat_name;
	private JComboBox id_task;

	
	public void connect() throws SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/management_tasks","root","");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	
	public void fetch() throws SQLException {
		
        
        try {
        	int q;
            pst = con.prepareStatement("SELECT * FROM tasks WHERE is_archived = 0");
            rs = pst.executeQuery();
            ResultSetMetaData rssData = (ResultSetMetaData) rs.getMetaData();
            q = rssData.getColumnCount();
            DefaultTableModel df = (DefaultTableModel)table.getModel(); 
            df.setRowCount(0);
            
            while(rs.next()) {
            	Vector v2 = new Vector();
            	for(int i=1; i<q; i++) {
            		v2.add(rs.getString("id"));
            		v2.add(rs.getString("name"));
            		v2.add(rs.getString("description"));
            		v2.add(rs.getString("day"));
            		v2.add(rs.getString("categorie"));

            		
            		
            		
            		
            		

            		if( rs.getInt("statut") == 1) {
            		 v2.add("done");
            		}else {
            			v2.add("not yet");
            		}

            	}
            	
            	df.addRow(v2);
            }
		} catch (Exception e) {
			// TODO: handle exception
		}
        
	}
	

private void fetch_category() throws SQLException {
		
        
        try {
        	int q1;
            pst1 = con.prepareStatement("SELECT * FROM categories");
            rs1 = pst1.executeQuery();
            ResultSetMetaData rssData1 = (ResultSetMetaData) rs1.getMetaData();
            q1 = rssData1.getColumnCount();
            DefaultTableModel df1 = (DefaultTableModel)table_2.getModel(); 
            df1.setRowCount(0);
            
            while(rs1.next()) {
            	Vector v = new Vector();
            	for(int i=1; i<q1; i++) {
            		v.add(rs1.getString("id_cat"));
            		v.add(rs1.getString("name_cat"));
            	}
            	
            	df1.addRow(v);
            }
		} catch (Exception e) {
			// TODO: handle exception
		} 
        
	}


	/**
	 * Initialize the contents of the frame.
	 * @throws SQLException 
	 */
	private void initialize() throws SQLException {
		frame = new JFrame();
		frame.setBounds(100, 100, 811, 469);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel frameTitle = new JLabel("Task's Management");
		frameTitle.setBounds(126, 11, 275, 31);
		frameTitle.setFont(new Font("Tahoma", Font.BOLD, 25));
		frame.getContentPane().add(frameTitle);
		
		JLabel task_name = new JLabel("Name");
		task_name.setFont(new Font("Tahoma", Font.BOLD, 12));
		task_name.setBounds(31, 69, 74, 22);
		frame.getContentPane().add(task_name);
		
		JPanel panel_buttons = new JPanel();
		panel_buttons.setLayout(null);
		panel_buttons.setBounds(31, 184, 322, 42);
		frame.getContentPane().add(panel_buttons);
		
		JLabel task_categorie = new JLabel("Categorie");
		task_categorie.setFont(new Font("Tahoma", Font.BOLD, 12));
		task_categorie.setBounds(302, 128, 74, 22);
		frame.getContentPane().add(task_categorie);
		
		JComboBox categorie = new JComboBox();
		categorie.setModel(new DefaultComboBoxModel(new String[] {"categorie 1", "categorie 2", "categorie 3", "categorie 4"}));
		categorie.setBounds(386, 127, 165, 22);
		frame.getContentPane().add(categorie);
		
		JButton btn_add = new JButton("ADD");
		btn_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Task added Successfuly !!");
				String tName = textField_name_1.getText();
				String tDescription = textField_description_1.getText();
				String tDay = day_1.getText();
				String tCategorie = categorie.getSelectedItem().toString();
				
				try {
					pst = con.prepareStatement("INSERT INTO tasks(name,description,day,categorie) VALUES (?,?,?,?)");
					pst.setString(1, tName);
					pst.setString(2, tDescription);
					pst.setString(3, tDay);
					pst.setString(4, tCategorie);
					
					
					int result = pst.executeUpdate();
					if(result == 1) {
						JOptionPane.showMessageDialog(btn_add, "Task added Successfuly !!");
						textField_name_1.setText("");
						textField_description_1.setText("");
						day_1.setText("");
						categorie.setSelectedItem("");
						fetch();
					}else {
						JOptionPane.showMessageDialog(btn_add, "Failed to save!!");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btn_add.setBounds(10, 11, 89, 23);
		panel_buttons.add(btn_add);
		
		JComboBox id_task = new JComboBox();
		id_task.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4","5","6"}));
		id_task.setBounds(386, 160, 74, 22);
		frame.getContentPane().add(id_task);
		
		JButton btn_update = new JButton("UPDATE");
		btn_update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tname = textField_name_1.getName();
				String tdescription = textField_description_1.getName();
				String tday = day_1.getName();
				String tcategory = categorie.getSelectedItem().toString();
				String tid = id_task.getSelectedItem().toString();
				
				try {
					pst = con.prepareStatement("UPDATE tasks SET name =?,description = ?,day = ?,categorie =? WHERE id= ?");
					pst.setString(1, tname);
					pst.setString(2, tdescription);
					pst.setString(3, tday);
					pst.setString(4, tcategory);
					pst.setString(5, tid);
					
					
					int result = pst.executeUpdate();
					if(result == 1) {
						JOptionPane.showMessageDialog(btn_add, "Task update Successfuly !!");
						fetch();
					}else {
						JOptionPane.showMessageDialog(btn_add, "Failed to update!!");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				
			}
			}
		});
		btn_update.setBounds(121, 11, 89, 23);
		panel_buttons.add(btn_update);
		
		JButton btn_delete = new JButton("DELETE");
		btn_delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tid = id_task.getSelectedItem().toString();
				
				try {
					pst = con.prepareStatement("UPDATE tasks SET is_archived =1 WHERE id= ?");
					
					pst.setString(1, tid);
					
					
					int result = pst.executeUpdate();
					if(result == 1) {
						JOptionPane.showMessageDialog(btn_add, "Task Deleted Successfuly !!");
						fetch();
					}else {
						JOptionPane.showMessageDialog(btn_add, "Failed to delete!!");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				
			}
			}
		});
		btn_delete.setBounds(220, 11, 89, 23);
		panel_buttons.add(btn_delete);
		
		JPanel panel_table = new JPanel();
		panel_table.setBounds(0, 237, 551, 192);
		frame.getContentPane().add(panel_table);
		panel_table.setLayout(null);
		
		//headers for the table
        String[] columns = new String[] {

        		"id","Name", "Description", "Day", "Categorie", "Statut"


        };
         
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columns);
        
        table = new JTable();
        table.setModel(model);
        
        
       
         
        //add the table to the frame
        table.setBounds(30, 11, 511, 174);
		panel_table.add(table);
		
		textField_name_1 = new JTextField();
		textField_name_1.setColumns(10);
		textField_name_1.setBounds(69, 71, 135, 20);
		frame.getContentPane().add(textField_name_1);
		
		JLabel task_description = new JLabel("Description");
		task_description.setFont(new Font("Tahoma", Font.BOLD, 12));
		task_description.setBounds(302, 69, 74, 22);
		frame.getContentPane().add(task_description);
		
		textField_description_1 = new JTextField();
		textField_description_1.setColumns(10);
		textField_description_1.setBounds(386, 71, 165, 20);
		frame.getContentPane().add(textField_description_1);
		
		day_1 = new JTextField();
		day_1.setColumns(10);
		day_1.setBounds(69, 128, 135, 20);
		frame.getContentPane().add(day_1);
		
		JLabel task_day = new JLabel("Day");
		task_day.setFont(new Font("Tahoma", Font.BOLD, 12));
		task_day.setBounds(31, 128, 74, 22);
		frame.getContentPane().add(task_day);
		

		JPanel panel = new JPanel();
		panel.setBounds(561, 11, 224, 408);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lbl_categories = new JLabel("Categories");
		lbl_categories.setBounds(77, 136, 70, 16);
		lbl_categories.setFont(new Font("Tahoma", Font.BOLD, 13));
		panel.add(lbl_categories);
		
		
		String[] columns1 = new String[] {
        		"id","Name"
        };
         
         
        DefaultTableModel model1 = new DefaultTableModel();
        model1.setColumnIdentifiers(columns1);
        
        table_categories = new JTable();
        
        
       
         
        //add the table to the frame
        table_categories.setBounds(28, 241, 159, -194);
		panel.add(table_categories);

		
		
		
		JButton btn_search = new JButton("Search");
		btn_search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String Tid = id_task.getSelectedItem().toString();
				
				try {
		            pst = con.prepareStatement("SELECT * FROM tasks WHERE id = ?");
		            pst.setString(1, Tid);
		            rs = pst.executeQuery();
		            
		            if(rs.next() == true) {
		            	textField_name_1.setText(rs.getString(2));
						textField_description_1.setText(rs.getString(3));
						day_1.setText(rs.getString(4));
		            }else {
						JOptionPane.showMessageDialog(btn_add, "No Record Found!!");
					}
		            
				} catch (Exception e1) {
					// TODO: handle exception
				}
				
			}
		});
		btn_search.setBounds(470, 160, 81, 23);
		frame.getContentPane().add(btn_search);

		
		table_2 = new JTable();
		table_2.setBounds(0, 242, 204, 156);
		panel.add(table_2);
        table_2.setModel(model1);

		
		JButton btn_delete_1 = new JButton("DELETE");
		btn_delete_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Categorie Deleted Successfuly !!");
				String id = table_2.getValueAt(table_2.getSelectedRow(), 0).toString();

				try {
					pst = con.prepareStatement("delete from categories where id_cat=?;");
					pst.setString(1, id);

					int result = pst.executeUpdate();
					fetch_category();
				
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btn_delete_1.setBounds(113, 208, 89, 23);
		panel.add(btn_delete_1);
		
		JButton btn_update_1 = new JButton("UPDATE");
		btn_update_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Categorie Updated Successfuly !!");
				String categorie = table_2.getValueAt(table_2.getSelectedRow(), 1).toString();
				String id = table_2.getValueAt(table_2.getSelectedRow(), 0).toString();

				try {
					pst = con.prepareStatement("update categories set name_cat=? where id_cat=?;");
					pst.setString(1, categorie);
					pst.setString(2, id);
					System.out.print(categorie);

					int result = pst.executeUpdate();
					fetch_category();
				
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btn_update_1.setBounds(14, 208, 89, 23);
		panel.add(btn_update_1);
		
		JButton btn_add_1 = new JButton("ADD");
		btn_add_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Categorie added Successfuly !!");
				String tName = textField.getText();
			
				
				try {
					pst = con.prepareStatement("INSERT INTO categories(name_cat) VALUES (?)");
					pst.setString(1, tName);
					int result = pst.executeUpdate();
					if(result == 1) {
						JOptionPane.showMessageDialog(btn_add_1, "Categorie added Successfuly !!");
						textField_name_1.setText("");
						fetch_category();
					}else {
						JOptionPane.showMessageDialog(btn_add, "Failed to save!!");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btn_add_1.setBounds(144, 173, 70, 23);
		panel.add(btn_add_1);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(14, 175, 120, 20);
		panel.add(textField);
		
		JButton btn_delete_2 = new JButton("Done");
		btn_delete_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
					
				try {
					pst = con.prepareStatement("update tasks set statut=? where id=?;");
				} catch (SQLException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
	
				try {
					pst.setInt(1, 1);
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
		
				String id = table.getValueAt(table.getSelectedRow(), 0).toString();
				try {
					pst.setString(2, id);
					int result = pst.executeUpdate();
					fetch();

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		btn_delete_2.setBounds(431, 194, 89, 23);
		frame.getContentPane().add(btn_delete_2);
		
		
		
		
	}
}