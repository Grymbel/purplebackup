package auditLog;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class AuditLogDAO {
	private static File dataFile;
	
	public AuditLogDAO() {
		Path dPath = FileSystems.getDefault().getPath("");
		dataFile = new File(dPath.toString()); 
	}
	
	public ArrayList<AuditLogModel> getAllData() {
		Scanner in;
		String record = null;
		String [] fields;
		ArrayList<AuditLogModel> dataList = new ArrayList<AuditLogModel>();
		try {
			in = new Scanner(dataFile);
			while (in.hasNextLine()) {
				record = in.nextLine();
				fields = record.split(";");
				String dateTime = fields[0];
				String ipAddress = fields[1];
				String username = fields[2];
				String activity = fields[3];
				AuditLogModel aLM = new AuditLogModel(dateTime, ipAddress, username, activity);
				dataList.add(aLM);
			}
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("No record found!");
			//e.printStackTrace();
		}
		return dataList;
	}
	
	public AuditLogModel getData(String dateTime) {
		ArrayList<AuditLogModel> dataList = new ArrayList<AuditLogModel>();
		AuditLogModel aLM = null;
		for (AuditLogModel a : dataList) {
			if (a.getDateTime().equals(dateTime)) {
				aLM = a;
				break;
			}
		}
		return aLM;
	}
}
