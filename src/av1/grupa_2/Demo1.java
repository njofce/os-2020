package av1.grupa_2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.RandomAccess;

public class Demo1 {

    // 1. Read/Write primitives
    // 2. BufferedReader/Writer & append
    // 3. Read from standard input
    // 4. Redirect standard IO
    // 5. Random Access File

    public static void main(String[] args) throws IOException {
//        createNewFile();
//        checkFileInDocuments();
//        createDirectory();
//
//        listFilesFirstLevel();
//        listAllFilesWithRecursion();
//
//        readFromFile();
//        readFromFileCharLevel();

//        writePrimitiveTypes();
//        readPrimitive();

//        writeBuffered();
//        readBuffered();
//        appendBuffered();
//        redirectStandardOutput();
//        System.out.println("This should be redirected to file!");
//        readFromStdInput();
        randomAccess();
    }

    public static void randomAccess() throws IOException {
        File input = new File("file1.txt");
        RandomAccessFile inputFile = new RandomAccessFile(input, "r");
        RandomAccessFile outputFile = new RandomAccessFile("file-rev.txt", "rw");

        long startPositionForOutput = 0;
        for(long i = input.length() - 1; i >=0; i--) {
            inputFile.seek(i);
            int readByte = inputFile.read();

            outputFile.seek(startPositionForOutput);
            outputFile.write(readByte);
            startPositionForOutput++;
        }

//        System.out.println(read);
    }

    public static void readFromStdInput() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String s = reader.readLine();

        System.out.println("Read from std input: " + s);

        reader.close();
    }

    public static void redirectStandardOutput() throws FileNotFoundException {
        PrintStream output = new PrintStream(new FileOutputStream("std-out.txt"));
        System.setOut(output);
    }

    public static void readBuffered() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("file-buff.txt")));

        String line;

        while( (line = reader.readLine()) != null) {
            System.out.println(line);
        }

        reader.close();
    }

    public static void writeBuffered() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("file-buff.txt"));

        writer.write("This is a new line");

        writer.close();
    }

    public static void appendBuffered() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("file-buff.txt", true));

        writer.write("\nThis is appended line");

        writer.close();
    }

    public static void readPrimitive() throws IOException {
        DataInputStream inputStream = new DataInputStream(new FileInputStream("file1.txt"));

        double d = inputStream.readDouble();
        float f = inputStream.readFloat();
        System.out.println("Read from file: " + d);
        System.out.println("Read from file: " + f);
        inputStream.close();
    }

    public static void writePrimitiveTypes() throws IOException {
        DataOutputStream outputStream = new DataOutputStream(new FileOutputStream("file1.txt"));

        try{

            outputStream.writeDouble(1.12);
            outputStream.writeFloat(0.75f);

        }finally {
            outputStream.close();
        }
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
