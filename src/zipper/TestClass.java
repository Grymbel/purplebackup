package zipper;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.*;

public class TestClass {
	public static void main (String []args){
		try {
			FileUtils.copyDirectoryToDirectory(new File("src/output/"),new File("src/output2/"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
