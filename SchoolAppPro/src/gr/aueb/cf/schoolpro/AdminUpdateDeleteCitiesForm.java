package gr.aueb.cf.schoolpro;

import java.awt.Color;
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
import java.awt.Toolkit;
public class AdminUpdateDeleteCitiesForm extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private PreparedStatement ps = null;
	Connection conn = null;
	private ResultSet rs = null;
	private JTextField cityTxt;
	private JTextField idTxt;

	public AdminUpdateDeleteCitiesForm() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AdminUpdateDeleteCitiesForm.class.getResource("/resources/eduv2.png")));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				String sql = "SELECT * FROM CITIES WHERE CITY LIKE ?";
				
				try {
					conn = DBUtil.getConnection();
					ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
					ps.setString(1, Main.getCitiesMenu().getCity() + "%");
					rs = ps.executeQuery();
					
					if(rs.next()) {
						idTxt.setText(Integer.toString(rs.getInt("ID")));
						cityTxt.setText(rs.getString("CITY"));
					} else {
						JOptionPane.showMessageDialog(null, "No Cities found", "Specialities", JOptionPane.ERROR_MESSAGE);
						Main.getAdminUpdateDeleteCitiesForm().setVisible(false);
						Main.getCitiesMenu().setEnabled(true);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 459, 352);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(10, 11, 349, 112);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel cityLbl = new JLabel("Πόλη");
		cityLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		cityLbl.setBounds(99, 57, 37, 14);
		cityLbl.setForeground(new Color(128, 0, 0));
		panel.add(cityLbl);

		cityTxt = new JTextField();
		cityTxt.setBackground(new Color(255, 255, 255));
		cityTxt.setBounds(146, 56, 178, 20);
		panel.add(cityTxt);
		cityTxt.setColumns(10);
		
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
		firstBtn.setSelectedIcon(new ImageIcon(AdminUpdateDeleteCitiesForm.class.getResource("/resources/First record.png")));
		firstBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(rs.first()) {
						idTxt.setText(Integer.toString(rs.getInt("ID")));
						cityTxt.setText(rs.getString("CITY"));
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		firstBtn.setIcon(new ImageIcon(AdminUpdateDeleteCitiesForm.class.getResource("/resources/First record.png")));
		firstBtn.setBounds(61, 157, 49, 36);
		contentPane.add(firstBtn);
		
		JButton previousBtn = new JButton("");
		previousBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(rs.previous()) {
						idTxt.setText(Integer.toString(rs.getInt("ID")));
						cityTxt.setText(rs.getString("CITY"));
					} else {
						rs.first();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		previousBtn.setIcon(new ImageIcon(AdminUpdateDeleteCitiesForm.class.getResource("/resources/Previous_record.png")));
		previousBtn.setBounds(111, 157, 49, 36);
		contentPane.add(previousBtn);
		
		JButton nextBtn = new JButton("");
		nextBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(rs.next()) {
						idTxt.setText(Integer.toString(rs.getInt("ID")));
						cityTxt.setText(rs.getString("CITY"));
					} else {
						rs.last();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		nextBtn.setIcon(new ImageIcon(AdminUpdateDeleteCitiesForm.class.getResource("/resources/Next_track.png")));
		nextBtn.setBounds(161, 157, 49, 36);
		contentPane.add(nextBtn);
		
		JButton lastBtn = new JButton("");
		lastBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(rs.last()) {
						idTxt.setText(Integer.toString(rs.getInt("ID")));
						cityTxt.setText(rs.getString("CITY"));
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		lastBtn.setIcon(new ImageIcon(AdminUpdateDeleteCitiesForm.class.getResource("/resources/Last_Record.png")));
		lastBtn.setBounds(211, 157, 49, 36);
		contentPane.add(lastBtn);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(new Color(0, 128, 255));
		separator.setBounds(23, 220, 390, 1);
		contentPane.add(separator);
		
		JButton updateBtn = new JButton("Update");
		updateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sql = "UPDATE CITIES SET CITY = ? WHERE ID = ?";
				
				try {
					Connection connection = DBUtil.getConnection();
					PreparedStatement ps = connection.prepareStatement(sql);
					
					String speciality = cityTxt.getText().trim();

					
					if (speciality.equals("")) {
						JOptionPane.showMessageDialog(null, "Empty city", "Input Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					if (cityExists(speciality)) {
						JOptionPane.showMessageDialog(null, "City already exists", "Input Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					ps.setString(1, speciality);
					ps.setString(2, idTxt.getText());
					
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
		updateBtn.setBounds(107, 248, 100, 46);
		contentPane.add(updateBtn);
		
		JButton deleteBtn = new JButton("Delete");
		deleteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sql = "DELETE FROM CITIES WHERE CITY = ?";
				
				try {
					Connection connection = DBUtil.getConnection();
					PreparedStatement ps = connection.prepareStatement(sql);
					ps.setString(1, cityTxt.getText());
						
					int response = JOptionPane.showConfirmDialog(null, "Είστε σίγουρος;", "Warning", JOptionPane.YES_NO_OPTION);
					if (response == JOptionPane.YES_OPTION) {
						int n = ps.executeUpdate();
						JOptionPane.showMessageDialog(null, n + " rows affected", "Delete", JOptionPane.INFORMATION_MESSAGE);
						if (rs.next()) {
	                        // If there are remaining specialities, update the form with the details of the next speciality
	                        idTxt.setText(Integer.toString(rs.getInt("ID")));
	                        cityTxt.setText(rs.getString("CITY"));
	                    } else {
	                        // If there are no remaining specialities, clear the form
	                        idTxt.setText("");
	                        cityTxt.setText("");
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
		deleteBtn.setBounds(210, 248, 100, 46);
		contentPane.add(deleteBtn);
		
		JButton closeBtn = new JButton("Close");
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getSpecialitiesMenu().setEnabled(true);
				Main.getAdminUpdateDeleteSpecialitiesForm().setVisible(false);
			}
		});
		closeBtn.setForeground(Color.BLUE);
		closeBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		closeBtn.setBounds(313, 248, 100, 46);
		contentPane.add(closeBtn);
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
