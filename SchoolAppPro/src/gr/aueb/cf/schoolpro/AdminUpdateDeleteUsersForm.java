package gr.aueb.cf.schoolpro;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import gr.aueb.cf.schoolpro.util.DBUtil;
import javax.swing.JPasswordField;
import java.awt.Toolkit;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class AdminUpdateDeleteUsersForm extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private PreparedStatement ps = null;
	Connection conn = null;
	private ResultSet rs = null;
	private JTextField usernameTxt;
	private JPasswordField passwordTxt;
	private JPasswordField confirmPasswordTxt;
	private JComboBox<String> roleComboBox;
	private JTextField idTxt;

	public AdminUpdateDeleteUsersForm() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AdminUpdateDeleteUsersForm.class.getResource("/resources/eduv2.png")));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				String sql = "SELECT * FROM USERS WHERE USERNAME LIKE ?";
				
				try {
					conn = DBUtil.getConnection();
					ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
					ps.setString(1, Main.getUsersMenu().getUsername() + "%");
					rs = ps.executeQuery();
					
					roleComboBox = new JComboBox<>();
					roleComboBox.removeAllItems();
					
					for (RoleType role : RoleType.values()) {
				        roleComboBox.addItem(role.name());
				    }
					
					if(rs.next()) {
						idTxt.setText(Integer.toString(rs.getInt("ID")));
						String userRole = rs.getString("ROLE");
				        roleComboBox.setSelectedItem(userRole);
						usernameTxt.setText(rs.getString("USERNAME"));
						passwordTxt.setText(rs.getString("PASSWORD"));
					} else {
						JOptionPane.showMessageDialog(null, "No Users found", "Users", JOptionPane.ERROR_MESSAGE);
						Main.getAdminUpdateDeleteUsersForm().setVisible(false);
						Main.getUsersMenu().setEnabled(true);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 457, 436);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(10, 11, 348, 228);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel usernameLbl = new JLabel("Username");
		usernameLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		usernameLbl.setBounds(67, 87, 69, 14);
		usernameLbl.setForeground(new Color(128, 0, 0));
		panel.add(usernameLbl);
		
		JLabel passwordLbl = new JLabel("Password");
		passwordLbl.setForeground(new Color(128, 0, 0));
		passwordLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		passwordLbl.setBounds(67, 126, 69, 14);
		panel.add(passwordLbl);

		JLabel confirmPasswordLbl = new JLabel("Confirm Password");
		confirmPasswordLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		confirmPasswordLbl.setForeground(new Color(128, 0, 0));
		confirmPasswordLbl.setBounds(10, 165, 126, 14);
		panel.add(confirmPasswordLbl);

		usernameTxt = new JTextField();
		usernameTxt.setEditable(false);
		usernameTxt.setBackground(new Color(255, 255, 128));
		usernameTxt.setBounds(146, 86, 178, 20);
		panel.add(usernameTxt);
		usernameTxt.setColumns(10);
		
		passwordTxt = new JPasswordField();
		passwordTxt.setBounds(146, 125, 178, 20);
		panel.add(passwordTxt);
		
		confirmPasswordTxt = new JPasswordField();
		confirmPasswordTxt.setBounds(146, 164, 178, 20);
		panel.add(confirmPasswordTxt);
		confirmPasswordTxt.setColumns(10);
		
		JLabel roleLbl = new JLabel("Role");
		roleLbl.setForeground(new Color(128, 0, 0));
		roleLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		roleLbl.setBounds(106, 49, 30, 27);
		panel.add(roleLbl);
		
		JComboBox roleComboBox = new JComboBox();
		roleComboBox.setModel(new DefaultComboBoxModel(RoleType.values()));
		roleComboBox.setBounds(146, 53, 178, 22);
		panel.add(roleComboBox);
		
		JLabel idLbl = new JLabel("ID");
		idLbl.setForeground(new Color(128, 0, 0));
		idLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		idLbl.setBounds(119, 24, 17, 14);
		panel.add(idLbl);
		
		idTxt = new JTextField();
		idTxt.setEditable(false);
		idTxt.setBackground(new Color(255, 255, 128));
		idTxt.setBounds(146, 23, 59, 20);
		panel.add(idTxt);
		idTxt.setColumns(10);
		
		JButton firstBtn = new JButton("");
		firstBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(rs.first()) {
						idTxt.setText(Integer.toString(rs.getInt("ID")));
						String userRole = rs.getString("ROLE");
				        roleComboBox.setSelectedItem(userRole);
						usernameTxt.setText(rs.getString("USERNAME"));
						passwordTxt.setText(rs.getString("PASSWORD"));
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		firstBtn.setIcon(new ImageIcon(AdminUpdateDeleteUsersForm.class.getResource("/resources/First record.png")));
		firstBtn.setBounds(64, 258, 49, 36);
		contentPane.add(firstBtn);
		
		JButton previousBtn = new JButton("");
		previousBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(rs.previous()) {
						idTxt.setText(Integer.toString(rs.getInt("ID")));
						String userRole = rs.getString("ROLE");
				        roleComboBox.setSelectedItem(userRole);
						usernameTxt.setText(rs.getString("USERNAME"));
						passwordTxt.setText(rs.getString("PASSWORD"));
					} else {
						rs.first();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		previousBtn.setIcon(new ImageIcon(AdminUpdateDeleteUsersForm.class.getResource("/resources/Previous_record.png")));
		previousBtn.setBounds(114, 258, 49, 36);
		contentPane.add(previousBtn);
		
		JButton nextBtn = new JButton("");
		nextBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(rs.next()) {
						idTxt.setText(Integer.toString(rs.getInt("ID")));
						String userRole = rs.getString("ROLE");
				        roleComboBox.setSelectedItem(userRole);
						usernameTxt.setText(rs.getString("USERNAME"));
						passwordTxt.setText(rs.getString("PASSWORD"));
					} else {
						rs.last();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		nextBtn.setIcon(new ImageIcon(AdminUpdateDeleteUsersForm.class.getResource("/resources/Next_track.png")));
		nextBtn.setBounds(164, 258, 49, 36);
		contentPane.add(nextBtn);
		
		JButton lastBtn = new JButton("");
		lastBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(rs.last()) {
						idTxt.setText(Integer.toString(rs.getInt("ID")));
						String userRole = rs.getString("ROLE");
				        roleComboBox.setSelectedItem(userRole);
						usernameTxt.setText(rs.getString("USERNAME"));
						passwordTxt.setText(rs.getString("PASSWORD"));
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		lastBtn.setIcon(new ImageIcon(AdminUpdateDeleteUsersForm.class.getResource("/resources/Last_Record.png")));
		lastBtn.setBounds(214, 258, 49, 36);
		contentPane.add(lastBtn);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(new Color(0, 128, 255));
		separator.setBounds(23, 317, 390, 1);
		contentPane.add(separator);
		
		JButton updateBtn = new JButton("Update");
		updateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sql = "UPDATE USERS SET PASSWORD = ? ROLE = ? WHERE USERNAME = ?";
				
				try {
					Connection connection = DBUtil.getConnection();
					PreparedStatement ps = connection.prepareStatement(sql);
					String userRole = rs.getString("ROLE");
					roleComboBox.setSelectedItem(userRole);
					String role = roleComboBox.toString();
					String username = usernameTxt.getText().trim();
					String password = passwordTxt.toString();
					String confirmPassword = confirmPasswordTxt.toString();

					
					if (password.equals("") || confirmPassword.equals("")) {
						JOptionPane.showMessageDialog(null, "Empty password or password confirmation", "Input Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					if (!password.equals(confirmPassword)) {
						JOptionPane.showMessageDialog(null, "Incorrect password", "Input Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					ps.setString(1, password);
					ps.setString(2, role);
					ps.setString(3, username);
					
					int n = ps.executeUpdate();
					
					if (n > 0) {
						JOptionPane.showMessageDialog(null, "Successful Update", "Update", JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "Update Error", "Update", JOptionPane.ERROR_MESSAGE);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		updateBtn.setForeground(Color.BLUE);
		updateBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		updateBtn.setBounds(107, 340, 100, 46);
		contentPane.add(updateBtn);
		
		JButton deleteBtn = new JButton("Delete");
		deleteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sql = "DELETE FROM USERS WHERE USERNAME = ?";
				
				try {
					Connection connection = DBUtil.getConnection();
					PreparedStatement ps = connection.prepareStatement(sql);
					ps.setString(1, usernameTxt.getText());
					
					
					int response = JOptionPane.showConfirmDialog(null, "Είστε σίγουρος;", "Warning", JOptionPane.YES_NO_OPTION);
					if (response == JOptionPane.YES_OPTION) {
						int n = ps.executeUpdate();
						JOptionPane.showMessageDialog(null, n + " rows affected", "Delete", JOptionPane.INFORMATION_MESSAGE);
						if (rs.next()) {
	                        // If there are remaining users, update the form with the details of the next user
	                        idTxt.setText(Integer.toString(rs.getInt("ID")));
	                        String userRole = rs.getString("ROLE");
	                        roleComboBox.setSelectedItem(userRole);
	                        usernameTxt.setText(rs.getString("USERNAME"));
	                        passwordTxt.setText(rs.getString("PASSWORD"));
	                    } else {
	                        // If there are no remaining users, clear the form
	                        idTxt.setText("");
	                        roleComboBox.setSelectedIndex(0); // Assuming the first item is a reasonable default
	                        usernameTxt.setText("");
	                        passwordTxt.setText("");
	                        confirmPasswordTxt.setText("");
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
		deleteBtn.setBounds(210, 340, 100, 46);
		contentPane.add(deleteBtn);
		
		JButton closeBtn = new JButton("Close");
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getUsersMenu().setEnabled(true);
				Main.getAdminUpdateDeleteUsersForm().setVisible(false);
			}
		});
		closeBtn.setForeground(Color.BLUE);
		closeBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		closeBtn.setBounds(313, 340, 100, 46);
		contentPane.add(closeBtn);
	}
}
