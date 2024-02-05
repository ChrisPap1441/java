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

public class AdminUpdateDeleteTeachersForm extends JFrame {
	private static final long serialVersionUID = 123456;
	private JPanel contentPane;
	private JTextField idTxt;
	private JTextField firstnameTxt;
	private JTextField lastnameTxt;
	private JTextField ssnTxt;
	
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
	private JLabel specialityLbl;
	private JComboBox<String> specialityComboBox_1;

	public AdminUpdateDeleteTeachersForm() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AdminUpdateDeleteTeachersForm.class.getResource("/resources/eduv2.png")));
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
					ps.setString(1, Main.getTeachersMenu().getLastname() + "%");
					rs = ps.executeQuery();
					
					if (!rs.next()) {
						JOptionPane.showMessageDialog(null, "Empty", "Result", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				} 
					
					
				PreparedStatement psSpecialities;
				ResultSet rsSpecialities;
			    try {
			    	String sqlSpecialities = "SELECT * FROM SPECIALTIES";
			    	psSpecialities = conn.prepareStatement(sqlSpecialities);
			    	rsSpecialities = psSpecialities.executeQuery();
			    	specialities = new HashMap<>();
			    	specialitiesModel = new DefaultComboBoxModel<>();
			    	
			    	while (rsSpecialities.next()) {
			    		String speciality = rsSpecialities.getString("SPECIALITY");
			    		int id = rsSpecialities.getInt("ID");
			    		specialities.put(speciality, id);
			    		specialitiesModel.addElement(speciality);
			    	}
			    	cityComboBox.setModel(citiesModel);
			    	cityComboBox.setMaximumRowCount(5);
			    	
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
					ssnTxt.setText(Integer.toString(rs.getInt("SSN")));
					cityComboBox.setSelectedItem(cities.get(rs.getInt("CITY_ID")));
					userComboBox.setSelectedItem(usernames.get(rs.getInt("USER_ID")));
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		setTitle("Ενημέρωση / Διαγραφή Εκπαιδευτικών");
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
		lblNewLabel.setBounds(45, 142, 64, 14);
		contentPane.add(lblNewLabel);
		
		lastnameTxt = new JTextField();
		lastnameTxt.setBounds(124, 139, 203, 20);
		contentPane.add(lastnameTxt);
		lastnameTxt.setColumns(10);
		
		JLabel genderLbl = new JLabel("Φύλο");
		genderLbl.setForeground(new Color(128, 0, 0));
		genderLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		genderLbl.setBounds(69, 170, 37, 14);
		contentPane.add(genderLbl);
		
		maleRdBtn = new JRadioButton("Άνδρας");
		maleRdBtn.setBounds(121, 166, 71, 23);
		contentPane.add(maleRdBtn);
		
		femaleRdBtn = new JRadioButton("Γυναίκα");
		femaleRdBtn.setBounds(205, 168, 71, 23);
		contentPane.add(femaleRdBtn);
		
		buttonGroup.add(maleRdBtn);
		buttonGroup.add(femaleRdBtn);
		
		JLabel birthDateLbl = new JLabel("Αρ. Μητρώου");
		birthDateLbl.setForeground(new Color(128, 0, 0));
		birthDateLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		birthDateLbl.setBounds(16, 102, 93, 14);
		contentPane.add(birthDateLbl);
		
		ssnTxt = new JTextField();
		ssnTxt.setBounds(124, 99, 203, 20);
		contentPane.add(ssnTxt);
		ssnTxt.setColumns(10);
		
		JLabel cityLbl = new JLabel("Πόλη");
		cityLbl.setForeground(new Color(128, 0, 0));
		cityLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		cityLbl.setBounds(72, 209, 37, 14);
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
		

		cityComboBox.setBounds(124, 206, 173, 22);
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
						ssnTxt.setText(DateUtil.toSQLDateString(rs.getDate("BIRTH_DATE")));
						cityComboBox.setSelectedItem(cities.get(rs.getInt("CITY_ID")));
						userComboBox.setSelectedItem(usernames.get(rs.getInt("USER_ID")));
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		firstBtn.setIcon(new ImageIcon(AdminUpdateDeleteTeachersForm.class.getResource("/resources/First record.png")));
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
						ssnTxt.setText(DateUtil.toSQLDateString(rs.getDate("BIRTH_DATE")));
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
		prevBtn.setIcon(new ImageIcon(AdminUpdateDeleteTeachersForm.class.getResource("/resources/Previous_record.png")));
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
						ssnTxt.setText(DateUtil.toSQLDateString(rs.getDate("BIRTH_DATE")));
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
		nextBtn.setIcon(new ImageIcon(AdminUpdateDeleteTeachersForm.class.getResource("/resources/Next_track.png")));
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
						ssnTxt.setText(DateUtil.toSQLDateString(rs.getDate("BIRTH_DATE")));
						cityComboBox.setSelectedItem(cities.get(rs.getInt("CITY_ID")));
						userComboBox.setSelectedItem(usernames.get(rs.getInt("USER_ID")));
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		lastBtn.setIcon(new ImageIcon(AdminUpdateDeleteTeachersForm.class.getResource("/resources/Last_Record.png")));
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
				int ssn = Integer.parseInt(ssnTxt.getText());
				String gender = buttonGroup.getSelection().getActionCommand();
				String city = (String) cityComboBox.getSelectedItem();
				String username = (String) usernameComboBox.getSelectedItem();
				String speciality = (String) specialityComboBox.getSelectedItem();
				int cityId = cities.get(city);
				int usernameId = usernames.get(username);
				int specialityId = specialities.get(speciality);
				
				
				if (firstname == "" || lastname == "") {
					JOptionPane.showMessageDialog(null, "Please fill firstname / lastname", "Basic info", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if (ssnExists(ssn)) {
					JOptionPane.showMessageDialog(null, "Ssn already exists.", "Basic info", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				PreparedStatement ps = null;
				try (Connection conn = DBUtil.getConnection();) {
					String sql = "INSERT INTO TEACHERS (FIRSTNAME, LASTNAME, SSN, GENDER, SPECIALITY_ID, USER_ID, CITY_ID) VALUES(?, ?, ?, ?, ?, ?, ? )";
					
					ps = conn.prepareStatement(sql);
					ps.setString(1, firstname);
					ps.setString(2,  lastname);
					ps.setInt(3, ssn);
					ps.setString(4, gender);
					ps.setInt(5, specialityId);
					ps.setInt(6, usernameId);
					ps.setInt(7, cityId);
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
					String sql = "DELETE FROM TEACHERS WHERE ID = ?";
				
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
							ssnTxt.setText(DateUtil.toSQLDateString(rs.getDate("SSN")));
							cityComboBox.setSelectedItem(cities.get(rs.getInt("CITY_ID")));
							userComboBox.setSelectedItem(usernames.get(rs.getInt("USER_ID")));
							specialityComboBox.setSelectedItem(specialities.get(rs.getInt("ID")));
						} else {
							firstnameTxt.setText("");
							lastnameTxt.setText("");
							maleRdBtn.setSelected(true);
							ssnTxt.setText("");
							cityComboBox.setSelectedItem(null);
							userComboBox.setSelectedItem(null);
							specialityComboBox.setSelectedItem(null);
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
				Main.getTeachersMenu().setEnabled(true);
				Main.getAdminUpdateDeleteTeachersForm().setVisible(false);
			}
		});
		closeBtn.setForeground(Color.BLUE);
		closeBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		closeBtn.setBounds(343, 434, 100, 46);
		contentPane.add(closeBtn);
		
		specialityLbl = new JLabel("Ειδικότητα");
		specialityLbl.setForeground(new Color(128, 0, 0));
		specialityLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		specialityLbl.setBounds(34, 239, 75, 17);
		contentPane.add(specialityLbl);
		
		specialityComboBox_1 = new JComboBox<String>();
		specialityComboBox_1.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				//comboBox.removeAll();
				String sql = "SELECT * FROM SPECIALITIES";
				//connection = Login.getConnection();
				
			    try (Connection connection = DBUtil.getConnection();
			    		PreparedStatement ps = connection.prepareStatement(sql);
			    		ResultSet rs = ps.executeQuery()) {
			    	specialities = new HashMap<>();
			    	specialitiesModel = new DefaultComboBoxModel<>();
			    	
			    	while (rs.next()) {
			    		String speciality = rs.getString("SPECIALITY");
			    		int id = rs.getInt("ID");
			    		specialities.put(speciality, id);
			    		//comboBox.addItem(speciality);
			    		specialitiesModel.addElement(speciality);
			    	}
			    	specialityComboBox.setModel(specialitiesModel);
			    	specialityComboBox.setMaximumRowCount(5);
			    	
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		specialityComboBox_1.setBounds(124, 239, 173, 20);
		contentPane.add(specialityComboBox_1);
	}
	
	private static boolean ssnExists(int ssn) {
		String sql = "SELECT COUNT(*) FROM TEACHERS WHERE SSN = ?";
		try (Connection connection = DBUtil.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, ssn);
			try(ResultSet rs = ps.executeQuery()) {
				if(rs.next()) {
					int count = rs.getInt(1);
					return count > 0;
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return false;
	}
}
