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
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AdminInsertCitiesForm extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField cityTxt;
	

	/**
	 * Create the frame.
	 */
	public AdminInsertCitiesForm() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				cityTxt.setText("");
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(AdminInsertCitiesForm.class.getResource("/resources/eduv2.png")));
		setTitle("Εισαγωγή Πόλης");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 484, 255);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel cityLbl = new JLabel("Πόλη");
		cityLbl.setForeground(new Color(128, 0, 0));
		cityLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		cityLbl.setBounds(83, 57, 37, 19);
		contentPane.add(cityLbl);
		
		cityTxt = new JTextField();
		cityTxt.setBounds(130, 58, 156, 20);
		contentPane.add(cityTxt);
		cityTxt.setColumns(10);
		
		JButton closeBtn = new JButton("Close");
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getCitiesMenu().setEnabled(true);
				Main.getAdminInsertCitiesForm().setVisible(false);
			}
		});
		closeBtn.setForeground(Color.BLUE);
		closeBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		closeBtn.setBounds(348, 131, 108, 58);
		contentPane.add(closeBtn);
		
		JButton insertBtn = new JButton("Εισαγωγή");
		insertBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String city = cityTxt.getText();
				
				
				if(city == "") {
					JOptionPane.showMessageDialog(null, "Please fill City", "Basic info", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if (cityExists(city)) {
					JOptionPane.showMessageDialog(null, "City already exists", "Basic info", JOptionPane.ERROR_MESSAGE);
					cityTxt.setText("");
					return;
				}
				
				
				String sql = "INSERT INTO CITIES (CITY) VALUES(?)";
				try (Connection connection = DBUtil.getConnection();
						PreparedStatement ps = connection.prepareStatement(sql)) {		 
					ps.setString(1, city);
					ps.executeUpdate();
					JOptionPane.showMessageDialog(null, "Η εισαγωγή έγινε επιτυχώς", "Insert", JOptionPane.INFORMATION_MESSAGE);
					cityTxt.setText("");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		insertBtn.setForeground(Color.BLUE);
		insertBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		insertBtn.setBounds(211, 131, 108, 58);
		contentPane.add(insertBtn);
		
	}
	
	private static boolean cityExists(String city) {
		String sql = "SELECT COUNT(*) FROM CITIES WHERE CITY = ?";
		try (Connection connection = DBUtil.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, city);
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