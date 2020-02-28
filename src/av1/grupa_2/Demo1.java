package av1.grupa_2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Demo1 {

    // Find all .txt files in all-files directory,
    // with only write permission
    // and count their total size

    public static void main(String[] args) throws IOException {
//        createNewFile();
//        checkFileInDocuments();
//        createDirectory();

//        listFilesFirstLevel();
        listAllFilesWithRecursion();

//        readFromFile();
//        readFromFileCharLevel();
    }

    public static void readFromFile() throws IOException {
        String filePath = "all-files/file1.txt";

        File file;
        InputStream fileInputStream = new FileInputStream(new File(filePath));

        while(true) {
            int read = fileInputStream.read();

            if(read == -1)
                break;

            System.out.println("Read byte:" + read);
        }

        fileInputStream.close();
    }

    public static void readFromFileCharLevel() throws IOException {
        String filePath = "all-files/file1.txt";

        InputStream fileInputStream = new FileInputStream(new File(filePath));
        InputStreamReader fileInputStreamReader = new InputStreamReader(fileInputStream);

        char[] firstTwoChars = new char[10];

        fileInputStreamReader.read(firstTwoChars);

        for(char c: firstTwoChars) {
            System.out.println(c);
        }

        fileInputStreamReader.close();
    }

    public static void listAllFilesWithRecursion() throws IOException {

        List<File> allTxtFilesInAllLevels = new ArrayList<>();
        listFilesRecursive("all-files", allTxtFilesInAllLevels);

        System.out.println("Total files in all dirs: " + allTxtFilesInAllLevels.size());

        long totalSize = 0;

        for(File f: allTxtFilesInAllLevels) {
            totalSize += f.length();
        }

        System.out.println("Total size of all files in all directories: "+ totalSize);
        writeTotalSizeToFile(totalSize);
    }

    public static void writeTotalSizeToFile(long totalSize) throws IOException {

        OutputStream outputStream = new FileOutputStream(new File("totalsize.txt"));
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

        dataOutputStream.writeLong(totalSize);

        dataOutputStream.close();
    }

    public static void listFilesFirstLevel() {
        File allFilesDirectory = new File("all-files");

        if(!allFilesDirectory.exists())
            return;

        File[] files = allFilesDirectory.listFiles();
        System.out.println("All files: " + files.length);

        File[] txtFiles = allFilesDirectory.listFiles(new TxtFilter());
        System.out.println("Txt files: " + txtFiles.length);

        List<File> filesWithWritePermission = new ArrayList<>();
        long totalSize = 0;

        for(File f: txtFiles) {
            if(f.canWrite())  {
                filesWithWritePermission.add(f);
                totalSize += f.length();
            }
        }

        System.out.println("Txt files with write permission: " + filesWithWritePermission.size());
        System.out.println("Total size in bytes for txt with write permission: " + totalSize);
    }

    public static void listFilesRecursive(String currentPath, List<File> allTxtFilesInAllLevels) {
        File allFilesDirectory = new File(currentPath);

        if(!allFilesDirectory.exists())
            return;

        File[] txtFiles = allFilesDirectory.listFiles(new TxtWritePermissionFilter());

        for(File f: txtFiles) {
            allTxtFilesInAllLevels.add(f);
        }

        for(File f: allFilesDirectory.listFiles()) {
            if(f.isDirectory()) {
                listFilesRecursive(f.getAbsolutePath(), allTxtFilesInAllLevels);
            }
        }
    }

    public static void createNewFile() throws IOException {
        File file1 = new File("file1.txt");

        if(file1.exists())
            System.out.println(file1.getAbsolutePath());
        else
            file1.createNewFile();

    }

    public static void createDirectory() {
        File directory = new File("dir1/dir2/dir3");

        if(directory.exists())
            System.out.println("Directory exists");
        else {
            boolean isCreated = directory.mkdirs();
            System.out.println(isCreated);
        }
    }

    public static void checkFileInDocuments() {
        String absolutePath = "/Users/njofce/Documents/file2.txt";
        String relativePath = "../../file2.txt";

        File fileInDocuments = new File(relativePath);

        if(fileInDocuments.exists())
            System.out.println("File in documents exists");

    }

}
