package gr.aueb.cf.schoolpro;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gr.aueb.cf.schoolpro.util.DBUtil;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.awt.Toolkit;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AdminInsertTeachersForm extends JFrame {
	private static final long serialVersionUID = 123456;
	private JPanel contentPane;
	private JTextField firstnameTxt;
	private JLabel lastnameLbl;
	private JTextField lastnameTxt;
	private JLabel genderLbl;
	private JLabel ssnLbl;
	private JTextField ssnTxt;
	private JLabel cityLbl;
	private JComboBox<String> cityComboBox;
	private JComboBox<String> specialityComboBox;
	private JComboBox<String> usernameComboBox;
	private Map<String, Integer> cities;
	private Map<String, Integer> specialities;
	private Map<String, Integer> usernames;
	private DefaultComboBoxModel<String> citieModel;
	private DefaultComboBoxModel<String> specialitiesModel;
	private DefaultComboBoxModel<String> usernamesModel;
	private ButtonGroup buttonGroup = new ButtonGroup(); 
	private JRadioButton maleRdBtn;
	private JRadioButton femaleRdBtn;
	private JLabel usernameLbl;

	public AdminInsertTeachersForm() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				firstnameTxt.setText("");
				lastnameTxt.setText("");
				maleRdBtn.setSelected(true);
				ssnTxt.setText("");
				cityComboBox.setSelectedItem(null);
				specialityComboBox.setSelectedItem(null);
				usernameComboBox.setSelectedItem(null);
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(AdminInsertTeachersForm.class.getResource("/resources/eduv2.png")));
		setTitle("Εισαγωγή Εκπαιδευτή");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 536, 371);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel firstnameLbl = new JLabel("Όνομα");
		firstnameLbl.setForeground(new Color(128, 0, 0));
		firstnameLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		firstnameLbl.setBounds(68, 36, 56, 17);
		contentPane.add(firstnameLbl);
		
		firstnameTxt = new JTextField();
		firstnameTxt.setBounds(129, 34, 207, 20);
		contentPane.add(firstnameTxt);
		firstnameTxt.setColumns(10);
		
		lastnameLbl = new JLabel("Επώνυμο");
		lastnameLbl.setForeground(new Color(128, 0, 0));
		lastnameLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		lastnameLbl.setBounds(52, 96, 72, 17);
		contentPane.add(lastnameLbl);
		
		lastnameTxt = new JTextField();
		lastnameTxt.setColumns(10);
		lastnameTxt.setBounds(129, 94, 207, 20);
		contentPane.add(lastnameTxt);
		
		genderLbl = new JLabel("Φύλο");
		genderLbl.setForeground(new Color(128, 0, 0));
		genderLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		genderLbl.setBounds(80, 126, 44, 17);
		contentPane.add(genderLbl);
		
		ssnLbl = new JLabel("Αρ. Μητρώου");
		ssnLbl.setForeground(new Color(128, 0, 0));
		ssnLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		ssnLbl.setBounds(25, 66, 99, 17);
		contentPane.add(ssnLbl);
		
		ssnTxt = new JTextField();
		ssnTxt.setColumns(10);
		ssnTxt.setBounds(129, 64, 207, 20);
		contentPane.add(ssnTxt);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		JRadioButton maleRdbtn = new JRadioButton("Άνδρας");
		maleRdbtn.setBounds(129, 125, 81, 23);
		contentPane.add(maleRdbtn);
		
		JRadioButton femaleRdbtn = new JRadioButton("Γυναίκα");
		femaleRdbtn.setBounds(206, 125, 95, 23);
		contentPane.add(femaleRdbtn);
		
		buttonGroup.add(maleRdbtn);
		buttonGroup.add(femaleRdbtn);
		
		cityLbl = new JLabel("Πόλη");
		cityLbl.setForeground(new Color(128, 0, 0));
		cityLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		cityLbl.setBounds(80, 156, 44, 17);
		contentPane.add(cityLbl);
		
		cityComboBox = new JComboBox<>();
		
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
		cityComboBox.setBounds(129, 155, 207, 20);
		contentPane.add(cityComboBox);
		
		JButton insertBtn = new JButton("Εισαγωγή");
		insertBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (buttonGroup.getSelection() == null || cityComboBox.getSelectedItem() == null
						|| specialityComboBox.getSelectedItem() == null || usernameComboBox.getSelectedItem() == null) {
					JOptionPane.showMessageDialog(null, "Please select gender / city / speciality / username", "Gender", JOptionPane.ERROR_MESSAGE);
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
				
				String sql = "INSERT INTO TEACHERS (FIRSTNAME, LASTNAME, SSN, GENDER, SPECIALITY_ID, USER_ID, CITY_ID) VALUES(?, ?, ?, ?, ?, ?, ? )";
				try (Connection connection = DBUtil.getConnection();
						PreparedStatement ps = connection.prepareStatement(sql)){
					ps.setString(1, firstname);
					ps.setString(2,  lastname);
					ps.setInt(3, ssn);
					ps.setString(4, gender);
					ps.setInt(5, specialityId);
					ps.setInt(6, usernameId);
					ps.setInt(7, cityId);
					ps.executeUpdate();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				
			}
		});
		insertBtn.setForeground(Color.BLUE);
		insertBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		insertBtn.setBounds(267, 262, 108, 58);
		contentPane.add(insertBtn);
		
		JButton closeBtn = new JButton("Close");
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getTeachersMenu().setEnabled(true);
				Main.getAdminInsertTeacehrsForm().setVisible(false);
			}
		});
		closeBtn.setForeground(Color.BLUE);
		closeBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		closeBtn.setBounds(404, 262, 108, 58);
		contentPane.add(closeBtn);
		
		JLabel specialityLbl = new JLabel("Ειδικότητα");
		specialityLbl.setForeground(new Color(128, 0, 0));
		specialityLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		specialityLbl.setBounds(45, 192, 75, 17);
		contentPane.add(specialityLbl);
		
		
		specialityComboBox = new JComboBox<String>();
		
		specialityComboBox.addFocusListener(new FocusAdapter() {
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
		specialityComboBox.setBounds(129, 192, 207, 20);
		contentPane.add(specialityComboBox);
		
		usernameLbl = new JLabel("Username");
		usernameLbl.setForeground(new Color(128, 0, 0));
		usernameLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		usernameLbl.setBounds(45, 231, 75, 17);
		contentPane.add(usernameLbl);
		
		usernameComboBox = new JComboBox<String>();
		usernameComboBox.addFocusListener(new FocusAdapter() {
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
			    	usernameComboBox.setModel(usernamesModel);
			    	usernameComboBox.setMaximumRowCount(5);
			    	
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		usernameComboBox.setBounds(129, 231, 207, 20);
		contentPane.add(usernameComboBox);
		
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