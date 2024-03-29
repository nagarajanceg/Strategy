package genericCheckpointing.util;

import java.io.*;

public class FileProcessor {
    /**
     * @param name
     * @return
     */
    public BufferedReader readerDesc(String name) {
        FileInputStream fp;
        BufferedReader reader = null;
        try {
            fp = new FileInputStream(name);
            reader = new BufferedReader(new InputStreamReader(fp));
        } catch (FileNotFoundException e) {
            Logger.writeMessage("File Not found, please make sure the input file available", Logger.getDebugValue());
            e.printStackTrace();
            System.exit(0);
        } finally {

        }
        return reader;
    }

    public BufferedWriter writerDesc(String name){
        FileOutputStream fp;
        BufferedWriter writer = null;
        try {
            fp = new FileOutputStream(name);
            writer =new BufferedWriter(new OutputStreamWriter(fp));
//            writer.write('e');
//            writer.close();
//            writeLine(writer, "test content");
        } catch (IOException e) {
            Logger.writeMessage("File Not found", Logger.getDebugValue());
//            System.out.println("File not found");
            e.printStackTrace();
        }finally {

        }
        return writer;
    }

    /**
     * @param reader
     * @return
     */
    public String readLine(BufferedReader reader) {
        if (reader == null) {
            return null;
        }
        String line = null;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            Logger.writeMessage("Read a line error", Logger.getDebugValue());
            System.exit(0);
        } finally {

        }
        return line;
    }
    public void writeLine(BufferedWriter writer, String content){
        try {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

        }
    }
    public void close(BufferedWriter writer){
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

        }
    }
    public void close(BufferedReader reader){
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

        }
    }
    @Override
    public String toString() {
        return "FileProcessor{}";
    }
}