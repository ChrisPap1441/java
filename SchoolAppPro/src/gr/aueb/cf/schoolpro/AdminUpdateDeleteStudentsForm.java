package gr.aueb.cf.schoolpro;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import gr.aueb.cf.schoolpro.util.DBUtil;
import gr.aueb.cf.schoolpro.util.DateUtil;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class AdminUpdateDeleteStudentsForm extends JFrame {
	private static final long serialVersionUID = 123456;
	private JPanel contentPane;
	private JTextField idTxt;
	private JTextField firstnameTxt;
	private JTextField lastnameTxt;
	private JTextField birthDateTxt;
	
	private JComboBox<String> cityComboBox = new JComboBox<>();
	private JComboBox<String> userComboBox = new JComboBox<>();
	private JComboBox<String> specialityComboBox;
	private JComboBox<String> usernameComboBox;
	private Map<String, Integer> cities;
	private Map<String, Integer> specialities;
	private Map<String, Integer> usernames;
	private DefaultComboBoxModel<String> citieModel;
	private DefaultComboBoxModel<String> specialitiesModel;
	private DefaultComboBoxModel<String> usernamesModel;
	private DefaultComboBoxModel<String> citiesModel;
	private ButtonGroup buttonGroup = new ButtonGroup(); 
	private ResultSet rs = null;
	private PreparedStatement ps = null;
	private JRadioButton maleRdBtn;
	private JRadioButton femaleRdBtn;
	private JButton firstBtn;
	private JButton prevBtn;
	private JButton nextBtn;
	private JButton lastBtn;
	Connection conn = null;
	private JButton updateBtn;
	private JButton deleteBtn;
	private JButton closeBtn;

	public AdminUpdateDeleteStudentsForm() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AdminUpdateDeleteStudentsForm.class.getResource("/resources/eduv2.png")));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				String sql = "SELECT * FROM STUDENTS WHERE LASTNAME LIKE ?";
				// Connection conn = Login.getConnection();
				//Connection conn = null;
				try {
					conn = DBUtil.getConnection();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try {
					//DBUtil.getConnection();
					//System.out.println("Search name" + Main.getStudentsMenu().getLastname().trim())
					ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
					ps.setString(1, Main.getStudentsMenu().getLastname() + "%");
					rs = ps.executeQuery();
					
					if (!rs.next()) {
						JOptionPane.showMessageDialog(null, "Empty", "Result", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				} 
					
					
				
				PreparedStatement psCities;
				ResultSet rsCities;
			    try {
			    	String sqlCities = "SELECT * FROM CITIES";
			    	psCities = conn.prepareStatement(sqlCities);
		    		rsCities = psCities.executeQuery();
			    	cities = new HashMap<>();
			    	citiesModel = new DefaultComboBoxModel<>();
			    	
			    	while (rsCities.next()) {
			    		String city = rsCities.getString("CITY");
			    		int id = rsCities.getInt("ID");
			    		cities.put(city, id);
			    		citiesModel.addElement(city);
			    	}
			    	cityComboBox.setModel(citiesModel);
			    	cityComboBox.setMaximumRowCount(5);
			    	
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			    
			  
			    PreparedStatement psUsers;
			    ResultSet rsUsers;
			    try {
			    	    String sqlUsers = "SELECT ID, USERNAME FROM USERS";
					    psUsers = conn.prepareStatement(sqlUsers);
			    		rsUsers = psUsers.executeQuery(sqlUsers);
			    	usernames = new HashMap<>();
			    	usernamesModel = new DefaultComboBoxModel<>();
			    	
			    	while (rsUsers.next()) {
			    		String username = rsUsers.getString("USERNAME");
			    		int id = rsUsers.getInt("ID");
			    		usernames.put(username, id);
			    		usernamesModel.addElement(username);
			    	}
			    	userComboBox.setModel(usernamesModel);
			    	userComboBox.setMaximumRowCount(5);
			    	
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			    
			    try {
					idTxt.setText(Integer.toString(rs.getInt("ID")));
					firstnameTxt.setText(rs.getString("FIRSTNAME"));
					lastnameTxt.setText(rs.getString("LASTNAME"));
					if (rs.getString("GENDER").equals("M")) {
						maleRdBtn.setSelected(true);
					} else {
						femaleRdBtn.setSelected(true);
					}
					birthDateTxt.setText(DateUtil.toSQLDateString(rs.getDate("BIRTH_DATE")));
					cityComboBox.setSelectedItem(cities.get(rs.getInt("CITY_ID")));
					userComboBox.setSelectedItem(usernames.get(rs.getInt("USER_ID")));
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		setTitle("Ενημέρωση / Διαγραφή Εκπαιδευόμενων");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 469, 545);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel idLbl = new JLabel("ID");
		idLbl.setForeground(new Color(128, 0, 0));
		idLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		idLbl.setBounds(92, 27, 17, 17);
		contentPane.add(idLbl);
		
		idTxt = new JTextField();
		idTxt.setBackground(new Color(252, 252, 205));
		idTxt.setEditable(false);
		idTxt.setBounds(124, 25, 59, 20);
		contentPane.add(idTxt);
		idTxt.setColumns(10);
		
		JLabel firstnameLbl = new JLabel("Όνομα");
		firstnameLbl.setForeground(new Color(128, 0, 0));
		firstnameLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		firstnameLbl.setBounds(60, 71, 49, 17);
		contentPane.add(firstnameLbl);
		
		firstnameTxt = new JTextField();
		firstnameTxt.setBounds(124, 69, 203, 19);
		contentPane.add(firstnameTxt);
		firstnameTxt.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Επώνυμο");
		lblNewLabel.setForeground(new Color(128, 0, 0));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(45, 115, 64, 14);
		contentPane.add(lblNewLabel);
		
		lastnameTxt = new JTextField();
		lastnameTxt.setBounds(124, 112, 203, 20);
		contentPane.add(lastnameTxt);
		lastnameTxt.setColumns(10);
		
		JLabel genderLbl = new JLabel("Φύλο");
		genderLbl.setForeground(new Color(128, 0, 0));
		genderLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		genderLbl.setBounds(72, 156, 37, 14);
		contentPane.add(genderLbl);
		
		maleRdBtn = new JRadioButton("Άνδρας");
		maleRdBtn.setBounds(124, 152, 71, 23);
		contentPane.add(maleRdBtn);
		
		femaleRdBtn = new JRadioButton("Γυναίκα");
		femaleRdBtn.setBounds(208, 154, 71, 23);
		contentPane.add(femaleRdBtn);
		
		buttonGroup.add(maleRdBtn);
		buttonGroup.add(femaleRdBtn);
		
		JLabel birthDateLbl = new JLabel("Ημ. Γέννησης");
		birthDateLbl.setForeground(new Color(128, 0, 0));
		birthDateLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		birthDateLbl.setBounds(10, 197, 99, 14);
		contentPane.add(birthDateLbl);
		
		birthDateTxt = new JTextField();
		birthDateTxt.setBounds(124, 194, 133, 20);
		contentPane.add(birthDateTxt);
		birthDateTxt.setColumns(10);
		
		JLabel cityLbl = new JLabel("Πόλη");
		cityLbl.setForeground(new Color(128, 0, 0));
		cityLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		cityLbl.setBounds(67, 238, 42, 14);
		contentPane.add(cityLbl);
		cityComboBox.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				//comboBox.removeAll();
				String sql = "SELECT * FROM CITIES";
				//connection = Login.getConnection();
				
			    try (Connection connection = DBUtil.getConnection();
			    		PreparedStatement ps = connection.prepareStatement(sql);
			    		ResultSet rs = ps.executeQuery()) {
			    	cities = new HashMap<>();
			    	citieModel = new DefaultComboBoxModel<>();
			    	
			    	while (rs.next()) {
			    		String city = rs.getString("CITY");
			    		int id = rs.getInt("ID");
			    		cities.put(city, id);
			    		//comboBox.addItem(city);
			    		citieModel.addElement(city);
			    	}
			    	cityComboBox.setModel(citieModel);
			    	cityComboBox.setMaximumRowCount(5);
			    	
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		

		cityComboBox.setBounds(124, 235, 173, 22);
		contentPane.add(cityComboBox);
		
		JLabel usernameLbl = new JLabel("Username");
		usernameLbl.setForeground(new Color(128, 0, 0));
		usernameLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		usernameLbl.setBounds(38, 279, 71, 14);
		contentPane.add(usernameLbl);
		userComboBox.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				String sql = "SELECT ID, USERNAME FROM USERS";
				//connection = Login.getConnection();
				
			    try (Connection connection = DBUtil.getConnection();
			    		PreparedStatement ps = connection.prepareStatement(sql);
			    		ResultSet rs = ps.executeQuery(sql)) {
			    	usernames = new HashMap<>();
			    	usernamesModel = new DefaultComboBoxModel<>();
			    	
			    	while (rs.next()) {
			    		String username = rs.getString("USERNAME");
			    		int id = rs.getInt("ID");
			    		usernames.put(username, id);
			    		usernamesModel.addElement(username);
			    	}
			    	userComboBox.setModel(usernamesModel);
			    	userComboBox.setMaximumRowCount(5);
			    	
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		
	
		userComboBox.setBounds(124, 275, 173, 22);
		contentPane.add(userComboBox);
		
		firstBtn = new JButton("");
		firstBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (rs.first()) {
						idTxt.setText(Integer.toString(rs.getInt("ID")));
						firstnameTxt.setText(rs.getString("FIRSTNAME"));
						lastnameTxt.setText(rs.getString("LASTNAME"));
						if (rs.getString("GENDER").equals("M")) {
							maleRdBtn.setSelected(true);
						} else {
							femaleRdBtn.setSelected(true);
						}
						birthDateTxt.setText(DateUtil.toSQLDateString(rs.getDate("BIRTH_DATE")));
						cityComboBox.setSelectedItem(cities.get(rs.getInt("CITY_ID")));
						userComboBox.setSelectedItem(usernames.get(rs.getInt("USER_ID")));
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		firstBtn.setIcon(new ImageIcon(AdminUpdateDeleteStudentsForm.class.getResource("/resources/First record.png")));
		firstBtn.setBounds(87, 350, 49, 23);
		contentPane.add(firstBtn);
		
		prevBtn = new JButton("");
		prevBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (rs.previous()) {
						idTxt.setText(Integer.toString(rs.getInt("ID")));
						firstnameTxt.setText(rs.getString("FIRSTNAME"));
						lastnameTxt.setText(rs.getString("LASTNAME"));
						if (rs.getString("GENDER").equals("M")) {
							maleRdBtn.setSelected(true);
						} else {
							femaleRdBtn.setSelected(true);
						}
						birthDateTxt.setText(DateUtil.toSQLDateString(rs.getDate("BIRTH_DATE")));
						cityComboBox.setSelectedItem(cities.get(rs.getInt("CITY_ID")));
						userComboBox.setSelectedItem(usernames.get(rs.getInt("USER_ID")));
					} else {
						rs.first();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		prevBtn.setIcon(new ImageIcon(AdminUpdateDeleteStudentsForm.class.getResource("/resources/Previous_record.png")));
		prevBtn.setBounds(134, 350, 49, 23);
		contentPane.add(prevBtn);
		
		nextBtn = new JButton("");
		nextBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (rs.next()) {
						idTxt.setText(Integer.toString(rs.getInt("ID")));
						firstnameTxt.setText(rs.getString("FIRSTNAME"));
						lastnameTxt.setText(rs.getString("LASTNAME"));
						if (rs.getString("GENDER").equals("M")) {
							maleRdBtn.setSelected(true);
						} else {
							femaleRdBtn.setSelected(true);
						}
						birthDateTxt.setText(DateUtil.toSQLDateString(rs.getDate("BIRTH_DATE")));
						cityComboBox.setSelectedItem(cities.get(rs.getInt("CITY_ID")));
						userComboBox.setSelectedItem(usernames.get(rs.getInt("USER_ID")));
					} else {
						rs.last();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		nextBtn.setIcon(new ImageIcon(AdminUpdateDeleteStudentsForm.class.getResource("/resources/Next_track.png")));
		nextBtn.setBounds(179, 350, 49, 23);
		contentPane.add(nextBtn);
		
		lastBtn = new JButton("");
		lastBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (rs.last()) {
						idTxt.setText(Integer.toString(rs.getInt("ID")));
						firstnameTxt.setText(rs.getString("FIRSTNAME"));
						lastnameTxt.setText(rs.getString("LASTNAME"));
						if (rs.getString("GENDER").equals("M")) {
							maleRdBtn.setSelected(true);
						} else {
							femaleRdBtn.setSelected(true);
						}
						birthDateTxt.setText(DateUtil.toSQLDateString(rs.getDate("BIRTH_DATE")));
						cityComboBox.setSelectedItem(cities.get(rs.getInt("CITY_ID")));
						userComboBox.setSelectedItem(usernames.get(rs.getInt("USER_ID")));
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		lastBtn.setIcon(new ImageIcon(AdminUpdateDeleteStudentsForm.class.getResource("/resources/Last_Record.png")));
		lastBtn.setBounds(227, 350, 49, 23);
		contentPane.add(lastBtn);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(24, 324, 303, 2);
		contentPane.add(separator);
		
		updateBtn = new JButton("Update");
		updateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (buttonGroup.getSelection() == null || cityComboBox.getSelectedItem() == null
						|| userComboBox.getSelectedItem() == null) {
					JOptionPane.showMessageDialog(null, "Please select gender / city / username", "Gender", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				String firstname = firstnameTxt.getText().trim();
				String lastname = lastnameTxt.getText().trim();
				String gender = buttonGroup.getSelection().getActionCommand();
				String city = (String) cityComboBox.getSelectedItem();
				String username = (String) userComboBox.getSelectedItem();
				int cityId = cities.get(city);
				int usernameId = usernames.get(username);
				
				java.sql.Date sqlBirthDate;
				try {
					sqlBirthDate = DateUtil.toSQLDate(DateUtil.toDate(birthDateTxt.getText()));
				} catch (ParseException e1) {
					JOptionPane.showMessageDialog(null, "Please insert a valid date (dd-MM-yyyy)", "Date", JOptionPane.ERROR_MESSAGE);
					//e1.printStackTrace();
					return;
				}
				
				if (firstname == "" || lastname == "") {
					JOptionPane.showMessageDialog(null, "Please fill firstname / lastname", "Basic info", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				PreparedStatement ps = null;
				try (Connection conn = DBUtil.getConnection();) {
					String sql = "INSERT INTO STUDENTS (FIRSTNAME, LASTNAME, GENDER, BIRTH_DATE, CITY_ID, USER_ID) " +
								"VALUES(?, ?, ?, ?, ?, ?)";
					
					ps = conn.prepareStatement(sql);
					ps.setString(1, firstname);
					ps.setString(2,  lastname);
					ps.setString(3, gender);
					ps.setDate(4, sqlBirthDate);
					ps.setInt(5, cityId);
					ps.setInt(6, usernameId);
					ps.executeUpdate();
					JOptionPane.showMessageDialog(null, "Η εγγραφή έγινε επιτυχώς", "Insert", JOptionPane.INFORMATION_MESSAGE);			
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		updateBtn.setForeground(Color.BLUE);
		updateBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		updateBtn.setBounds(137, 434, 100, 46);
		contentPane.add(updateBtn);
		
		deleteBtn = new JButton("Delete");
		deleteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					String sql = "DELETE FROM STUDENTS WHERE ID = ?";
				
				try {
					Connection connection = DBUtil.getConnection();
					PreparedStatement ps = connection.prepareStatement(sql);
					ps.setInt(1, Integer.parseInt(idTxt.getText()));
					
					
					int response = JOptionPane.showConfirmDialog(null, "Είστε σίγουρος;", "Warning", JOptionPane.YES_NO_OPTION);
					if (response == JOptionPane.YES_OPTION) {
						int n = ps.executeUpdate();
						JOptionPane.showMessageDialog(null, n + " rows affected", "Delete", JOptionPane.INFORMATION_MESSAGE);
						if (rs.next()) {
							idTxt.setText(Integer.toString(rs.getInt("ID")));
							firstnameTxt.setText(rs.getString("FIRSTNAME"));
							lastnameTxt.setText(rs.getString("LASTNAME"));
							if (rs.getString("GENDER").equals("M")) {
								maleRdBtn.setSelected(true);
							} else {
								femaleRdBtn.setSelected(true);
							}
							birthDateTxt.setText(DateUtil.toSQLDateString(rs.getDate("BIRTH_DATE")));
							cityComboBox.setSelectedItem(cities.get(rs.getInt("CITY_ID")));
							userComboBox.setSelectedItem(usernames.get(rs.getInt("USER_ID")));
						} else {
							firstnameTxt.setText("");
							lastnameTxt.setText("");
							maleRdBtn.setSelected(true);
							birthDateTxt.setText("");
							cityComboBox.setSelectedItem(null);
							userComboBox.setSelectedItem(null);
						}
					} else {
						return;
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		deleteBtn.setForeground(Color.BLUE);
		deleteBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		deleteBtn.setBounds(240, 434, 100, 46);
		contentPane.add(deleteBtn);
		
		closeBtn = new JButton("Close");
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getStudentsMenu().setEnabled(true);
				Main.getAdminUpdateDeleteStudentsForm().setVisible(false);
			}
		});
		closeBtn.setForeground(Color.BLUE);
		closeBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		closeBtn.setBounds(343, 434, 100, 46);
		contentPane.add(closeBtn);
	}
}