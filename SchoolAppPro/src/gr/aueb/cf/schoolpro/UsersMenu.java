package gr.aueb.cf.schoolpro;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class UsersMenu extends JFrame {
	private static final long serialVersionUID = 123456;
	private JPanel contentPane;
	private JTextField usernameTxt;
	private String username = "";

	public UsersMenu() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(UsersMenu.class.getResource("/resources/eduv2.png")));
		setTitle("Μενού Χρηστών");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 449, 481);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel searchPanel = new JPanel();
		searchPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		searchPanel.setBounds(32, 23, 355, 170);
		contentPane.add(searchPanel);
		searchPanel.setLayout(null);
		
		JLabel usernameLbl = new JLabel("Username");
		usernameLbl.setForeground(new Color(128, 0, 0));
		usernameLbl.setFont(new Font("Tahoma", Font.BOLD, 15));
		usernameLbl.setBounds(134, 11, 80, 35);
		searchPanel.add(usernameLbl);
		
		usernameTxt = new JTextField();
		usernameTxt.setBounds(66, 46, 216, 35);
		searchPanel.add(usernameTxt);
		usernameTxt.setColumns(10);
		
		JButton searchBtn = new JButton("Αναζήτηση");
		searchBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getAdminUpdateDeleteUsersForm().setVisible(true);
				Main.getUsersMenu().setEnabled(false);
				username = usernameTxt.getText();
			}
		});
		searchBtn.setForeground(new Color(0, 0, 255));
		searchBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		searchBtn.setBounds(113, 98, 122, 47);
		searchPanel.add(searchBtn);
		
		JPanel insertPanel = new JPanel();
		insertPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		insertPanel.setBounds(32, 204, 355, 145);
		contentPane.add(insertPanel);
		insertPanel.setLayout(null);
		
		JButton insertbtn = new JButton("Εισαγωγή");
		insertbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getAdminInsertUsersForm().setVisible(true);
				Main.getUsersMenu().setEnabled(false);
			}
		});
		insertbtn.setForeground(new Color(0, 0, 255));
		insertbtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		insertbtn.setBounds(113, 54, 122, 47);
		insertPanel.add(insertbtn);
		
		JButton closeBtn = new JButton("Close");
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getAdminMenu().setEnabled(true);
				Main.getUsersMenu().setVisible(false);
			}
		});
		closeBtn.setForeground(new Color(0, 0, 255));
		closeBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		closeBtn.setBounds(280, 384, 108, 49);
		contentPane.add(closeBtn);
	}

	public String getUsername() {
		return username;
	}

	
}
