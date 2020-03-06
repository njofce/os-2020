package av1.grupa_en;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Demo1 {

    // 1. Read/Write primitives
    // 2. BufferedReader/Writer & append
    // 3. Read from standard input
    // 4. Redirect standard IO
    // 5. Random Access File

    public static void main(String[] args) throws IOException {
        randomInverse();
    }

    public static void randomInverse() throws IOException {
        File source = new File("file1.txt");
        RandomAccessFile sourceAccess = new RandomAccessFile(source, "r");
        RandomAccessFile destAccess = new RandomAccessFile("file2.txt", "rw");

        long destinationStart = 0;
        for(long i = source.length() - 1; i>=0; i--) {
            sourceAccess.seek(i);

            int readByte = sourceAccess.read();

            destAccess.seek(destinationStart);
            destAccess.write(readByte);
            destinationStart++;
        }

    }

    public static void readFromStdInput() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String s = reader.readLine();
        System.out.println("Read from keyboard: " + s);

        reader.readLine();
    }

    public static void redirectStdOutput() throws FileNotFoundException {
        System.setOut(new PrintStream(new FileOutputStream("std-out.txt")));
    }

    public static void writeBuffered() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("file1.txt", true));

        writer.write("writing a line to file");
        writer.write("\n");
        writer.write("Line 2");

        writer.close();

    }

    public static void readBuffered() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("file1.txt")));

        String s;

        while( (s = reader.readLine()) != null) {
            System.out.println(s);
        }
    }

    public static void readPrimitive() throws IOException {
        DataInputStream dataInputStream = new DataInputStream(new FileInputStream("file1.txt"));

        double d = dataInputStream.readDouble();
        float f = dataInputStream.readFloat();

        System.out.println("Double: " + d);
        System.out.println("Float: " + f);
    }

    public static void writePrimitive() throws IOException {
        DataOutputStream outputStream = new DataOutputStream(new FileOutputStream("file1.txt"));

        outputStream.writeDouble(1.13);
        outputStream.writeFloat(0.15f);

        outputStream.close();
    }

    public static void readInputStream() throws IOException {
        InputStream stream = new FileInputStream("file1.txt");
        OutputStream outputStream = new FileOutputStream("file2.txt");

        try{
            int read1 = stream.read();
            int read2 = stream.read();


            outputStream.write(read1);
            outputStream.write(read2);

            System.out.println(read1);
            System.out.println(read2);
        }finally {
            stream.close();
            outputStream.close();
        }
    }

    public static void listFiles() {
        List<File> allTxtFiles = new ArrayList<>();
        findAllTextFilesRecursively("all-files",allTxtFiles);

        long totalSize = 0;
        for(File f: allTxtFiles) {
            System.out.println(f.getName());
            totalSize+=f.length();
        }

        System.out.println(totalSize);
    }

    public static void findAllTextFilesRecursively(String path, List<File> allTxtFiles) {
        File directory = new File(path);

        File[] files = directory.listFiles(new TxtFilter());

        for(File txtFile: files){
            allTxtFiles.add(txtFile);
        }

        for(File f: directory.listFiles()) {
            if(f.isDirectory())
                findAllTextFilesRecursively(f.getAbsolutePath(), allTxtFiles);
        }
    }

}
