package av1.grupa_1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Demo1 {

    public static void main(String[] args) throws IOException {
//        test();
//        listFiles("/Users/njofce/Documents/SchoolApps/os-demo-folder");
//
//        List<File> allTxtFiles = new ArrayList<>();
//
//        listFilesRecursive("/Users/njofce/Documents/SchoolApps/os-demo-folder", allTxtFiles);

//        createDirectory();
//        readFileContentsByteOriented();
//        readFileContentsCharOriented();
//        writeCharToFile();
//        writePrimitives();
//        readPrimitives();
//        bufferedWrite();
//        bufferedAppend();
//        redirectStandardOutput();
//        bufferedRead();
//        readFromStdIn();
        randomInverse();
    }

    public static void randomInverse() throws IOException {
        File inputFile = new File("file1.txt");
        RandomAccessFile randomInput = new RandomAccessFile(inputFile, "r");
        RandomAccessFile randomOutput = new RandomAccessFile("file_inverse.txt", "rw");

        long outPosition = 0;

        for(long i = inputFile.length() - 1; i >= 0; i--) {
            randomInput.seek(i);
            int read = randomInput.read();

            randomOutput.seek(outPosition);
            randomOutput.write(read);
            outPosition++;
        }
    }

    public static void readFromStdIn() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String s = reader.readLine();

        System.out.println("Read from keyboard:" + s);
    }

    public static void redirectStandardOutput() throws FileNotFoundException {
        PrintStream stream = new PrintStream(new FileOutputStream("out.txt"));
        System.setOut(stream);


    }

    public static void bufferedWrite() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("file1.txt"));

        try {

            writer.write("This is new string 1");
        }finally {
            writer.close();
        }
    }

    public static void bufferedAppend() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("file1.txt", true));

        try {
            writer.write("\n");
            writer.write("this is appended string");
        }finally {
            writer.close();
        }
    }

    public static void bufferedRead() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("file1.txt")));

        String read;

        while ((read = reader.readLine()) != null) {
            System.out.println(read);
        }
    }

    public static void writePrimitives() throws IOException {

        DataOutputStream outputStream = new DataOutputStream(new FileOutputStream("file1.txt"));

        try{
            outputStream.writeDouble(1.12);
            outputStream.writeFloat(1.0f);
        }finally {
            outputStream.close();
        }
    }

    public static void readPrimitives() throws IOException {
        DataInputStream inputStream = new DataInputStream(new FileInputStream("file1.txt"));

        try {

            double d = inputStream.readDouble();
            float f = inputStream.readFloat();

            System.out.println("Read double: " + d);
            System.out.println("Read float: " + f);
        }finally {
            inputStream.close();
        }
    }

    public static void test() throws IOException {
        File file1 = new File("test.txt");

        if(file1.exists())
            System.out.println(" Exists");
        else
            file1.createNewFile();

        System.out.println(file1.getAbsolutePath());
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
