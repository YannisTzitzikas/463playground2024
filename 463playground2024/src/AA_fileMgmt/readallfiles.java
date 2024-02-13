package AA_fileMgmt;

/*
 * It just scans (and prints the absolute path) 
 * of all files and subfolders of  a given selected folder
 * @author  Yannis Tzitzikas
 */
import java.io.File;

import javax.swing.JFileChooser;

public class readallfiles {
	static int numOfFiles=0;
	static int numOfFolders=0;
	
	/**
	 * Dialog for selecting a folder
	 * @return
	 */
	public static String selectAFolderDialog() {
		String filePath ="";
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		int result = fileChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
		    File selectedFile = fileChooser.getSelectedFile();
		    filePath= selectedFile.getAbsolutePath();
		    System.out.println("Selected folder: " +  filePath);
		}
		return filePath;
	}

    public static void main(String[] args) {
   
        //File folder = new File("C:\\dataset\\ clinic\\"); // the path of the folder     
    	File folder = new File( selectAFolderDialog());
    	listFilesForFolder(folder);
    	System.out.println("Number of folders:"+numOfFolders);
        System.out.println("Number of files:  "+numOfFiles);

    }

    public static void listFilesForFolder(File folder) {
        for (File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
            	numOfFolders++;
                listFilesForFolder(fileEntry);
            } else {
            	numOfFiles++;
                System.out.println(fileEntry.getAbsolutePath());
            }
        }
    }
}
