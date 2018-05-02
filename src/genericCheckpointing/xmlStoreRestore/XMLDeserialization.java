package genericCheckpointing.xmlStoreRestore;

import genericCheckpointing.utill.FileProcessor;
import genericCheckpointing.utill.SerializableObject;

import java.io.BufferedReader;

public class XMLDeserialization implements SerStrategy {
    private BufferedReader reader;
    private FileProcessor fp;
    public SerializableObject obj = null;
    public XMLDeserialization(FileProcessor fp, BufferedReader reader) {
        this.fp = fp;
        this.reader = reader;
    }

    public SerializableObject getObj() {
        return obj;
    }

    public void setObj(SerializableObject obj) {
        this.obj = obj;
    }

    @Override
    public void processInput(SerializableObject sObject) {
        readXML();
        System.out.println("Processing");
    }
    private void readXML(){
        String line;
        while ((line = fp.readLine(reader)) != null) {
            if(line.equalsIgnoreCase("<DPSerialization>"))
                System.out.println(line);
        }
    }
}
