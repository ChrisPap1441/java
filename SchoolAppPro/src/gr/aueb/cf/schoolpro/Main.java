package gr.aueb.cf.schoolpro;

import java.awt.EventQueue;

public class Main {
	
	private static Login loginForm;
	private static AdminMenu adminMenu;
	private static TeachersMenu teachersMenu;
	private static StudentsMenu studentsMenu;
	private static UsersMenu usersMenu;
	private static SpecialitiesMenu specialitiesMenu;
	private static CitiesMenu citiesMenu;
	private static AdminInsertStudentsForm adminInsertStudentsForm;
	private static AdminInsertTeachersForm adminInsertTeacehrsForm;
	private static AdminInsertUsersForm adminInsertUsersForm;
	private static AdminInsertSpecialitiesForm adminInsertSpecialitiesForm;
	private static AdminInsertCitiesForm adminInsertCitiesForm;
	private static AdminUpdateDeleteStudentsForm adminUpdateDeleteStudentsForm;
	private static AdminUpdateDeleteUsersForm adminUpdateDeleteUsersForm;
	private static AdminUpdateDeleteSpecialitiesForm adminUpdateDeleteSpecialitiesForm;
	private static AdminUpdateDeleteCitiesForm adminUpdateDeleteCitiesForm;
	private static AdminUpdateDeleteTeachersForm adminUpdateDeleteTeachersForm;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					loginForm = new Login();
					loginForm.setVisible(true);
					
					adminMenu = new AdminMenu();
					adminMenu.setVisible(false);
					
					teachersMenu = new TeachersMenu();
					teachersMenu.setVisible(false);
					
					studentsMenu = new StudentsMenu();
					studentsMenu.setVisible(false);
					
					usersMenu = new UsersMenu();
					usersMenu.setVisible(false);
					
					specialitiesMenu = new SpecialitiesMenu();
					specialitiesMenu.setVisible(false);
					
					citiesMenu = new CitiesMenu();
					citiesMenu.setVisible(false);
					
					adminInsertTeacehrsForm = new AdminInsertTeachersForm();
					adminInsertTeacehrsForm.setVisible(false);
					
					adminInsertStudentsForm = new AdminInsertStudentsForm();
					adminInsertStudentsForm.setVisible(false);
					
					adminInsertUsersForm = new AdminInsertUsersForm();
					adminInsertUsersForm.setVisible(false);
					
					adminInsertSpecialitiesForm = new AdminInsertSpecialitiesForm();
					adminInsertSpecialitiesForm.setVisible(false);
					
					adminInsertCitiesForm = new AdminInsertCitiesForm();
					adminInsertCitiesForm.setVisible(false);
					
					adminUpdateDeleteStudentsForm = new AdminUpdateDeleteStudentsForm();
					adminUpdateDeleteStudentsForm.setVisible(false);
					
					adminUpdateDeleteUsersForm = new AdminUpdateDeleteUsersForm();
					adminUpdateDeleteUsersForm.setVisible(false);
					
					adminUpdateDeleteSpecialitiesForm = new AdminUpdateDeleteSpecialitiesForm();
					adminUpdateDeleteSpecialitiesForm.setVisible(false);
					
					adminUpdateDeleteCitiesForm = new AdminUpdateDeleteCitiesForm();
					adminUpdateDeleteCitiesForm.setVisible(false);
					
					adminUpdateDeleteTeachersForm = new AdminUpdateDeleteTeachersForm();
					adminUpdateDeleteTeachersForm.setVisible(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static Login getLoginForm() {
		return loginForm;
	}

	public static AdminMenu getAdminMenu() {
		return adminMenu;
	}

	public static TeachersMenu getTeachersMenu() {
		return teachersMenu;
	}
	
	public static StudentsMenu getStudentsMenu() {
		return studentsMenu;
	}

	public static UsersMenu getUsersMenu() {
		return usersMenu;
	}

	public static SpecialitiesMenu getSpecialitiesMenu() {
		return specialitiesMenu;
	}

	public static CitiesMenu getCitiesMenu() {
		return citiesMenu;
	}

	public static AdminInsertStudentsForm getAdminInsertStudentsForm() {
		return adminInsertStudentsForm;
	}

	public static AdminInsertTeachersForm getAdminInsertTeacehrsForm() {
		return adminInsertTeacehrsForm;
	}

	public static AdminInsertUsersForm getAdminInsertUsersForm() {
		return adminInsertUsersForm;
	}

	public static AdminInsertSpecialitiesForm getAdminInsertSpecialitiesForm() {
		return adminInsertSpecialitiesForm;
	}

	public static AdminInsertCitiesForm getAdminInsertCitiesForm() {
		return adminInsertCitiesForm;
	}

	public static AdminUpdateDeleteStudentsForm getAdminUpdateDeleteStudentsForm() {
		return adminUpdateDeleteStudentsForm;
	}

	public static AdminUpdateDeleteUsersForm getAdminUpdateDeleteUsersForm() {
		return adminUpdateDeleteUsersForm;
	}

	public static AdminUpdateDeleteSpecialitiesForm getAdminUpdateDeleteSpecialitiesForm() {
		return adminUpdateDeleteSpecialitiesForm;
	}

	public static AdminUpdateDeleteCitiesForm getAdminUpdateDeleteCitiesForm() {
		return adminUpdateDeleteCitiesForm;
	}

	public static AdminUpdateDeleteTeachersForm getAdminUpdateDeleteTeachersForm() {
		return adminUpdateDeleteTeachersForm;
	}
	
}