package gr.aueb.cf.schoolpro;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gr.aueb.cf.schoolpro.security.SecUtil;
import gr.aueb.cf.schoolpro.util.DBUtil;

import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.Toolkit;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField usernameTxt;
	private JPasswordField passwordTxt;
	private JTextField usernameRegTxt;
	private JPasswordField passwordRegTxt;
	private JPasswordField passwordConfirmRegTxt;
	private static Connection connection;

	/**
	 * Create the frame.
	 */
	public Login() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/resources/eduv2.png")));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				String username = "schoolpro";
				String password = System.getenv("SCHOOL_DB_USER_PASSWD");
				String url = "jdbc:mysql://localhost:3306/schooldbpro?serverTimezone = UTC";
				
				try {
					//Class.forName("com.mysql.cj.jdbc.Driver");
					connection = DriverManager.getConnection(url, username, password);
					System.out.println("Successfully connect to DB" + connection);
				} catch (SQLException e1) {
					e1.printStackTrace();
				} 
//				catch (ClassNotFoundException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
			}
		});
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 436, 252);
		contentPane.add(tabbedPane);
		
		JPanel login = new JPanel();
		tabbedPane.addTab("Login", null, login, null);
		login.setLayout(null);
		
		JLabel usernameLbl = new JLabel("Username");
		usernameLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		usernameLbl.setBounds(91, 46, 97, 25);
		login.add(usernameLbl);
		
		usernameTxt = new JTextField();
		usernameTxt.setBounds(198, 46, 129, 20);
		login.add(usernameTxt);
		usernameTxt.setColumns(10);
		
		JLabel passwordLbl = new JLabel("Password");
		passwordLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		passwordLbl.setBounds(91, 105, 85, 14);
		login.add(passwordLbl);
		
		JButton loginBtn = new JButton("Login");
		loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String inputPassword = String.valueOf(passwordTxt.getPassword()).trim();
				String inputUsername = usernameTxt.getText();
				if (inputUsername.matches("[aA]dmin")) {
				//if (usernameTxt.getText().matches(Role.Admin.name())) {
					if (inputPassword.equals(System.getenv("SCHOOL_APP_ADMIN_PASSWD"))) {
						Main.getAdminMenu().setVisible(true);
						Main.getLoginForm().setEnabled(false);
					} else {
						JOptionPane.showMessageDialog(null, "Password Error" , "Error" , JOptionPane.INFORMATION_MESSAGE);
					}
				}
				else {
					String role = getUserRole(inputUsername, inputPassword);
					if (role == null) {
						JOptionPane.showMessageDialog(null, "Username or Password Error" , "Error" , JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					if (role.equals("Student")) {
						Main.getStudentsMenu().setVisible(true);
						Main.getLoginForm().setEnabled(false);
					} else if (role.equals("Teacher")) {
						Main.getTeachersMenu().setVisible(true);
						Main.getLoginForm().setEnabled(false);
					}
				}	
			}
		});
		loginBtn.setBounds(230, 143, 97, 39);
		login.add(loginBtn);
		
		passwordTxt = new JPasswordField();
		passwordTxt.setBounds(198, 102, 129, 20);
		login.add(passwordTxt);
		
		JPanel register = new JPanel();
		tabbedPane.addTab("Register", null, register, null);
		register.setLayout(null);
		
		JLabel roleLbl = new JLabel("Role");
		roleLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		roleLbl.setBounds(150, 25, 30, 27);
		register.add(roleLbl);
		
		JComboBox roleComboBox = new JComboBox();
		roleComboBox.setModel(new DefaultComboBoxModel(RoleType.values()));
		roleComboBox.setBounds(202, 29, 129, 22);
		register.add(roleComboBox);
		
		JLabel usernameRegLbl = new JLabel("Username");
		usernameRegLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		usernameRegLbl.setBounds(107, 68, 73, 25);
		register.add(usernameRegLbl);
		
		usernameRegTxt = new JTextField();
		usernameRegTxt.setColumns(10);
		usernameRegTxt.setBounds(202, 68, 129, 20);
		register.add(usernameRegTxt);
		
		JLabel passwordRegLbl = new JLabel("Password");
		passwordRegLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		passwordRegLbl.setBounds(107, 107, 73, 14);
		register.add(passwordRegLbl);
		
		passwordRegTxt = new JPasswordField();
		passwordRegTxt.setBounds(202, 104, 129, 20);
		register.add(passwordRegTxt);
		
		JLabel passwordConfirmRegLbl = new JLabel("Confirm Password");
		passwordConfirmRegLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		passwordConfirmRegLbl.setBounds(51, 141, 129, 14);
		register.add(passwordConfirmRegLbl);
		
		passwordConfirmRegTxt = new JPasswordField();
		passwordConfirmRegTxt.setBounds(202, 138, 129, 20);
		register.add(passwordConfirmRegTxt);
		
		JButton registerBtn = new JButton("Register");
		registerBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = usernameRegTxt.getText().trim();
				
				//char[] password1chars = passwordRegTxt.getPassword();
				String password1 = String.valueOf(passwordRegTxt.getPassword()).trim();
				
				//char[] password2chars = passwordConfirmRegTxt.getPassword();
				String password2 = String.valueOf(passwordConfirmRegTxt.getPassword()).trim();
				String role = roleComboBox.getSelectedItem().toString();
				
				if (username == "" || password1 == "" || password2 == "") {
					JOptionPane.showMessageDialog(null, "Please fill username / password", "Basic info", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if (usernameExists(username)) {
					JOptionPane.showMessageDialog(null, "Username already exists", "Basic info", JOptionPane.ERROR_MESSAGE);
					passwordRegTxt.setText("");
					passwordConfirmRegTxt.setText("");
					return;
				}
				
//				if (username.length() <= 2 || password2.length() <= 8) {
//					JOptionPane.showMessageDialog(null, "Please fill username / password", "Basic info", JOptionPane.ERROR_MESSAGE);
//					return;
//				}
				
				if (!password1.equals(password2)) {
					JOptionPane.showMessageDialog(null, "Confirmation password not the same", "Basic info", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				
				String sql = "INSERT INTO USERS (USERNAME, PASSWORD, ROLE) VALUES(?, ?, ? )";
				try (Connection connection = DBUtil.getConnection();
						PreparedStatement ps = connection.prepareStatement(sql)) {		 
					ps.setString(1, username);
					ps.setString(2, SecUtil.hashPassword(password1));
					ps.setString(3, role);
					ps.executeUpdate();
					JOptionPane.showMessageDialog(null, "User registered successfuly", "Basic info", JOptionPane.INFORMATION_MESSAGE);
					usernameRegTxt.setText("");
					passwordRegTxt.setText("");
					passwordConfirmRegTxt.setText("");
				} catch (SQLException e1) {
					e1.printStackTrace();
				} 
			}
		});
		registerBtn.setForeground(new Color(0, 0, 255));
		registerBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		registerBtn.setBounds(235, 179, 96, 34);
		register.add(registerBtn);
	}
	
	private String getUserRole(String inputUsername, String inputPassword) {
		PreparedStatement ps = null;
		String role = null;
		
		try (Connection conn = DBUtil.getConnection()) {
			String sql = "SELECT * FROM USERS WHERE USERNAME = ?";
			
				ps = conn.prepareStatement(sql);
				ps.setString(1,  inputUsername);
				ResultSet rs = ps.executeQuery();
				if (!rs.next()) return null;
				String password = rs.getString("PASSWORD");
				role = rs.getString("ROLE");
				if (!SecUtil.checkPassword(inputPassword, password)) {
					return null;
				}
		} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		return role;
	}
	
	private static boolean usernameExists(String username) {
		String sql = "SELECT COUNT(*) FROM USERS WHERE USERNAME = ?";
		try (Connection connection = DBUtil.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, username);
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
