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

public class AdminInsertSpecialitiesForm extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField specialityInsTxt;
	

	/**
	 * Create the frame.
	 */
	public AdminInsertSpecialitiesForm() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				specialityInsTxt.setText("");
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(AdminInsertSpecialitiesForm.class.getResource("/resources/eduv2.png")));
		setTitle("Εισαγωγή Ειδικότητας");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 484, 255);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel specialityLbl = new JLabel("Ειδικότητα");
		specialityLbl.setForeground(new Color(128, 0, 0));
		specialityLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		specialityLbl.setBounds(47, 57, 73, 19);
		contentPane.add(specialityLbl);
		
		specialityInsTxt = new JTextField();
		specialityInsTxt.setBounds(130, 58, 156, 20);
		contentPane.add(specialityInsTxt);
		specialityInsTxt.setColumns(10);
		
		JButton closeBtn = new JButton("Close");
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getSpecialitiesMenu().setEnabled(true);
				Main.getAdminInsertSpecialitiesForm().setVisible(false);
			}
		});
		closeBtn.setForeground(Color.BLUE);
		closeBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		closeBtn.setBounds(348, 131, 108, 58);
		contentPane.add(closeBtn);
		
		JButton insertBtn = new JButton("Εισαγωγή");
		insertBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String speciality = specialityInsTxt.getText();
				
				
				if(speciality == "") {
					JOptionPane.showMessageDialog(null, "Please fill Speciality", "Basic info", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if (specialityExists(speciality)) {
					JOptionPane.showMessageDialog(null, "Speciality already exists", "Basic info", JOptionPane.ERROR_MESSAGE);
					specialityInsTxt.setText("");
					return;
				}
				
				
				String sql = "INSERT INTO SPECIALITIES (SPECIALITY) VALUES(?)";
				try (Connection connection = DBUtil.getConnection();
						PreparedStatement ps = connection.prepareStatement(sql)) {		 
					ps.setString(1, speciality);
					ps.executeUpdate();
					JOptionPane.showMessageDialog(null, "Η εισαγωγή έγινε επιτυχώς", "Insert", JOptionPane.INFORMATION_MESSAGE);
					specialityInsTxt.setText("");
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
	
	private static boolean specialityExists(String speciality) {
		String sql = "SELECT COUNT(*) FROM SPECIALITIES WHERE SPECIALITY = ?";
		try (Connection connection = DBUtil.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, speciality);
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
