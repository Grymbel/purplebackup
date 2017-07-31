package login;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class LoginDAO {
	private static File dataFile;

	public LoginDAO() {
		Path dPath = FileSystems.getDefault().getPath("src/login/Login.txt");
		dataFile = new File(dPath.toString()); 
	}
	
	public ArrayList<LoginModel> getAllData() {
		Scanner in;
		String record = null;
		String[] fields;
		ArrayList<LoginModel> dataList = new ArrayList<LoginModel>();
		try {
			in = new Scanner(dataFile);
			while (in.hasNextLine()) {
				record = in.nextLine();
				fields = record.split(";");
				String username = fields[0];
				String password = fields[1];
				String salt = fields[2];
				LoginModel loginModel = new LoginModel(username, password, salt);
				dataList.add(loginModel);
			}
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("No record found!");
			//e.printStackTrace();
		}
		return dataList;
	}
	
	public LoginModel getAdmin(String username) {
		ArrayList<LoginModel> dataList = new ArrayList<LoginModel>();
		LoginModel loginModel = null;
		for (LoginModel l : dataList) {
			if (l.getUsername().equals(username)) {
				loginModel = l;
				break;
			}
		}
		return loginModel;
	}
	
	private void synToFile(ArrayList<LoginModel> dataList) {
		if (dataList == null) 
			return;
			
		try {
			FileWriter out = new FileWriter(dataFile);
			for (LoginModel loginModel : dataList) {
				out.append(loginModel.toString() + "\n");
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void updateAdmin(LoginModel loginModel) {
		ArrayList<LoginModel> dataList = new ArrayList<LoginModel>();
		for (int i = 0; i < dataList.size(); i++) {
			LoginModel l = dataList.get(i);
			if (l.getUsername().equals(loginModel.getUsername())) {
				dataList.set(i, loginModel);
			}
		}
		synToFile(dataList);
	}
	
	public static void main(String[] args) {
		LoginDAO loginDAO = new LoginDAO();
		ArrayList<LoginModel> dataList = loginDAO.getAllData();
		for (LoginModel loginModel : dataList) {
			System.out.println("Username: " + loginModel.getUsername());
			System.out.println("Password: " + loginModel.getPassword());
			System.out.println("Salt: " + loginModel.getSalt());
		}
	}
}
