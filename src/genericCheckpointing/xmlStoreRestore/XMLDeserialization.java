package genericCheckpointing.xmlStoreRestore;

import genericCheckpointing.util.FileProcessor;
import genericCheckpointing.util.SerializableObject;

import java.io.BufferedReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class XMLDeserialization implements SerStrategy {
    private BufferedReader reader;
    private FileProcessor fp;
    private SerializableObject obj = null;
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
    }
    private void readXML(){
        String line;
        line = fp.readLine(reader);
        Object object = null;
        Class<?> newClass = null;
        if(line != null && line.equalsIgnoreCase("<DPSerialization>")){
                while ((line = fp.readLine(reader)) != null && !line.equalsIgnoreCase("</DPSerialization>")) {
                    System.out.println(line);
                    if(line.startsWith(" <complexType")){
                        String[] packageName = (line.split("="));
//                        (line.split("="))[1].split("\\.");
                        System.out.println(Arrays.toString(packageName));
                        System.out.println(packageName[1].substring(1, packageName[1].length() -2));
//                        String className = packageName[packageName.length-1].replace("\">","");
                        String className = packageName[1].substring(1, packageName[1].length() -2);
                        System.out.println("Myclass Name"+className);
                        try {
                            newClass = Class.forName(className);
                            System.out.println(newClass);
                            try {
                                try {
                                    object = newClass.newInstance();
                                } catch (InstantiationException e) {
                                    e.printStackTrace();
                                }
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                            System.exit(-1);
                        }
                    }else{
                        if(line.trim().startsWith("</complexType")){
                            continue;
                        }
                       identifyFields(line, newClass, object);
                    }
                }
        }
    }

    public void identifyFields(String line, Class<?> newClass, Object object){
        String [] currLine = line.trim().split(" ");
        String fieldName = currLine[0].substring(1);
        Field field = null;
        try {
            field = newClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        String[] rest = currLine[1].split("xsd:");
        String type = rest[1].substring(0,rest[1].indexOf('"'));
        //Start and end index of to figure out the value
        int startInd = line.indexOf('>');
        int endInd = line.indexOf('<',startInd);
        String val = line.substring(startInd+1, endInd );
        System.out.println(type);
        System.out.println(val);
        if(field != null && field.getType() == int.class){
            String fName = field.getName();
            String methodName = "set" + fName.substring(0,1).toUpperCase()+fName.substring(1);
            Class[] argTypes = new Class[] { int.class };
            Method method = null;
            try {
                method = newClass.getDeclaredMethod(methodName, argTypes);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            try {
                method.invoke(object, Integer.parseInt(val));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        }
        setObj((SerializableObject) object);
    }

}
