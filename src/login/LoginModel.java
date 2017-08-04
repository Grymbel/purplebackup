package login;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class LoginModel {
	private String username;
	private String salt;
	private String password;
	
	public LoginModel() {
		super();
	}

	public LoginModel(String username, String salt, String password) {
		super();
		this.username = username;
		this.salt = salt;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}
	
	@Override
	public String toString() {
		return username + ";" + salt + ";" + password;
	}
	
	public static ArrayList<LoginModel> getAllData() {
		LoginDAO loginDAO = new LoginDAO();
		return loginDAO.getAllData();
	}
	
	public void getData() {
		LoginDAO loginDAO = new LoginDAO();
		LoginModel loginModel = loginDAO.getAdmin(username);
		setSalt(loginModel.getSalt());
		setPassword(loginModel.getPassword());
	}
	
	public void updateAdmin() {
		LoginDAO loginDAO = new LoginDAO();
		loginDAO.updateAdmin(this);
	}
	
	public LoginModel getCurrentAdmin() throws FileNotFoundException {
		File file = new File("src/login/Login.txt");
		Scanner sc = new Scanner(file) ;
		String n = sc.nextLine();
		sc.close();
		
		LoginDAO loginDAO = new LoginDAO();
		LoginModel loginModel = new LoginModel();
		loginModel = loginDAO.getAdmin(n);
		
		return loginModel;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		LoginModel model = new LoginModel();
		ArrayList<LoginModel> list = LoginModel.getAllData();
		for (LoginModel m : list) {
			model = m;
		}
		model.setPassword("Something");
		model.updateAdmin();
	}

}
