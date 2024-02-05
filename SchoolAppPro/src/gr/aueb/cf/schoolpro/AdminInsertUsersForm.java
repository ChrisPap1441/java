package gr.aueb.cf.schoolpro;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gr.aueb.cf.schoolpro.security.SecUtil;
import gr.aueb.cf.schoolpro.util.DBUtil;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AdminInsertUsersForm extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField usernameInsTxt;
	private JPasswordField passwordInsTxt;
	private JPasswordField confirmPasswordInsTxt;
	

	/**
	 * Create the frame.
	 */
	public AdminInsertUsersForm() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				usernameInsTxt.setText("");
				passwordInsTxt.setText("");
				confirmPasswordInsTxt.setText("");
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(AdminInsertUsersForm.class.getResource("/resources/eduv2.png")));
		setTitle("Εισαγωγή Χρήστη");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 482, 337);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel usernameInsLbl = new JLabel("Username");
		usernameInsLbl.setForeground(new Color(128, 0, 0));
		usernameInsLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		usernameInsLbl.setBounds(129, 96, 69, 19);
		contentPane.add(usernameInsLbl);
		
		usernameInsTxt = new JTextField();
		usernameInsTxt.setBounds(208, 97, 156, 20);
		contentPane.add(usernameInsTxt);
		usernameInsTxt.setColumns(10);
		
		JLabel passwordInsLbl = new JLabel("Password");
		passwordInsLbl.setForeground(new Color(128, 0, 0));
		passwordInsLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		passwordInsLbl.setBounds(129, 127, 69, 19);
		contentPane.add(passwordInsLbl);
		
		passwordInsTxt = new JPasswordField();
		passwordInsTxt.setBounds(208, 128, 156, 20);
		contentPane.add(passwordInsTxt);
		
		JLabel confirmPasswordInsLbl = new JLabel("Confirm Password");
		confirmPasswordInsLbl.setForeground(new Color(128, 0, 0));
		confirmPasswordInsLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		confirmPasswordInsLbl.setBounds(72, 157, 126, 19);
		contentPane.add(confirmPasswordInsLbl);
		
		confirmPasswordInsTxt = new JPasswordField();
		confirmPasswordInsTxt.setBounds(208, 158, 156, 20);
		contentPane.add(confirmPasswordInsTxt);
		
		JButton closeBtn = new JButton("Close");
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getUsersMenu().setEnabled(true);
				Main.getAdminInsertUsersForm().setVisible(false);
			}
		});
		closeBtn.setForeground(Color.BLUE);
		closeBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		closeBtn.setBounds(344, 222, 108, 58);
		contentPane.add(closeBtn);
		
		JComboBox roleComboBox = new JComboBox();
		roleComboBox.setModel(new DefaultComboBoxModel(RoleType.values()));
		roleComboBox.setBounds(207, 62, 156, 22);
		contentPane.add(roleComboBox);
		
		JButton insertBtn = new JButton("Εισαγωγή");
		insertBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = usernameInsTxt.getText();
				String password = String.valueOf(passwordInsTxt.getPassword());
				String confirmPassword = String.valueOf(confirmPasswordInsTxt.getPassword());
				String role = roleComboBox.getSelectedItem().toString();
				
				
				if(username == "" || password == "" || confirmPassword == "") {
					JOptionPane.showMessageDialog(null, "Please fill Username / Password", "Basic info", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if (usernameExists(username)) {
					JOptionPane.showMessageDialog(null, "Username already exists", "Basic info", JOptionPane.ERROR_MESSAGE);
					usernameInsTxt.setText("");
					passwordInsTxt.setText("");
					return;
				}
				
				if (!username.equals(username.trim()) && !password.equals(password.trim()) &&
						!confirmPassword.equals(confirmPassword.trim()) && !password.equals(confirmPassword)) {
					JOptionPane.showMessageDialog(null, "Please insert correct Username/Password values.(ex. No spaces)", "Basic info", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				String sql = "INSERT INTO USERS (USERNAME, PASSWORD, ROLE) VALUES(?, ?, ? )";
				try (Connection connection = DBUtil.getConnection();
						PreparedStatement ps = connection.prepareStatement(sql)) {		 
					ps.setString(1, username);
					ps.setString(2, SecUtil.hashPassword(password));
					ps.setString(3, role);
					ps.executeUpdate();
					JOptionPane.showMessageDialog(null, "Η εισαγωγή έγινε επιτυχώς", "Insert", JOptionPane.INFORMATION_MESSAGE);
					usernameInsTxt.setText("");
					passwordInsTxt.setText("");
					confirmPasswordInsTxt.setText("");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		insertBtn.setForeground(Color.BLUE);
		insertBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		insertBtn.setBounds(207, 222, 108, 58);
		contentPane.add(insertBtn);
		
		JLabel roleLbl = new JLabel("Role");
		roleLbl.setForeground(new Color(128, 0, 0));
		roleLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		roleLbl.setBounds(167, 58, 30, 27);
		contentPane.add(roleLbl);
		
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
