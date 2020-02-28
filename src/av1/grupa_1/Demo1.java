package av1.grupa_1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Demo1 {

    public static void main(String[] args) throws IOException {
//        File file1 = new File("test.txt");

//        if(file1.exists())
//            System.out.println(" Exists");
//        else
//            file1.createNewFile();

//        System.out.println(file1.getAbsolutePath());
//
//        listFiles("/Users/njofce/Documents/SchoolApps/os-demo-folder");
//
//        List<File> allTxtFiles = new ArrayList<>();
//
//        listFilesRecursive("/Users/njofce/Documents/SchoolApps/os-demo-folder", allTxtFiles);

//        createDirectory();
//        readFileContentsByteOriented();
//        readFileContentsCharOriented();

        writeCharToFile();
    }

    public static void readFileContentsByteOriented() throws IOException {
        File file1 = new File("file1.txt");

        InputStream inputStream = new FileInputStream(file1);

        while(true) {
            int read = inputStream.read();

            if(read == -1)
                break;

            System.out.println(read);
        }


        inputStream.close();
    }

    public static void readFileContentsCharOriented() throws IOException {
        File file1 = new File("file1.txt");

        InputStream inputStream = new FileInputStream(file1);
        InputStreamReader reader = new InputStreamReader(inputStream);

        char[] buffer = new char[2];
        reader.read(buffer);

        System.out.println(buffer[0]);
        System.out.println(buffer[1]);

        inputStream.close();
    }

    public static void writeCharToFile() throws IOException {
        File file2 = new File("file2.txt");
        file2.createNewFile();

        OutputStream out = new FileOutputStream(file2);
        OutputStreamWriter writer = new OutputStreamWriter(out);

        writer.write('c');
        writer.write('c');
        writer.write('c');

        writer.flush();
        writer.close();
    }

    public static void createDirectory() {
        File directory1 = new File("dir1/dir2/dir3");

        if(directory1.exists())
            System.out.println("Exists");
        else {
            boolean isCreated = directory1.mkdirs();
            System.out.println(isCreated);
        }

    }

    public static void listFilesRecursive(String directoryPath, List<File> allTxtFiles) {
        File directory = new File(directoryPath);

        if(!directory.exists())
            return;

        FilenameFilter txtFilter = new TxtFilter();
        File[] txtFiles = directory.listFiles(txtFilter);

        for(File f: txtFiles) {
            allTxtFiles.add(f);
        }

        for(File f: directory.listFiles(txtFilter)){
            if(f.isDirectory())
                listFilesRecursive(f.getAbsolutePath(), allTxtFiles);
        }
    }

    public static void listFiles(String directoryPath) {
        File directory = new File(directoryPath);

        if(!directory.exists())
            return;

        FilenameFilter txtFilter = new TxtFilter();
        File[] allFiles = directory.listFiles();
        File[] txtFiles = directory.listFiles(txtFilter);

        System.out.println("Printing all files in directory");
        for(File f: allFiles) {
            System.out.println(f.getAbsolutePath());
        }

        System.out.println("Printing txt files in directory");
        for(File f: txtFiles) {
            System.out.println(f.getAbsolutePath());
        }

        List<File> writePermissionFiles = new ArrayList<>();

        for(File txtFile: txtFiles) {
            if(txtFile.canWrite())
                writePermissionFiles.add(txtFile);
        }

        System.out.println("Printing txt files with write permission in directory");
        long golemina = 0;
        for(File f: writePermissionFiles) {
            golemina += f.length();
            System.out.println(f.getAbsolutePath());
        }

        System.out.println("Vkupna golemina: " + golemina);
    }
}
