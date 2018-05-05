package genericCheckpointing.xmlStoreRestore;

import genericCheckpointing.util.FileProcessor;
import genericCheckpointing.util.SerializableObject;

import java.io.BufferedReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class XMLDeserialization implements SerStrategy {
    private BufferedReader reader;
    private FileProcessor fp;
    private SerializableObject obj = null;

    /**
     *
     * @param fp
     * @param reader
     */
    public XMLDeserialization(FileProcessor fp, BufferedReader reader) {
        this.fp = fp;
        this.reader = reader;
    }

    public SerializableObject getObj() {
        return obj;
    }

    private void setObj(SerializableObject obj) {
        this.obj = obj;
    }

    @Override
    public void processInput(SerializableObject sObject) {
        readXML();
    }

    private void readXML() {
        String line;
        line = fp.readLine(reader);
        Object object = null;
        Class<?> newClass = null;
        if (line != null && line.equalsIgnoreCase("<DPSerialization>")) {
            while ((line = fp.readLine(reader)) != null && !line.equalsIgnoreCase("</DPSerialization>")) {
//                System.out.println(line);
                if (line.startsWith(" <complexType")) {
                    String[] packageName = (line.split("="));
                    String className = packageName[1].substring(1, packageName[1].length() - 2);
//                    System.out.println(newClass);
                    try {
                        newClass = Class.forName(className);
                        object = newClass.newInstance();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        System.exit(-1);
                    }
                } else {
                    if (line.trim().startsWith("</complexType")) {
                        continue;
                    }
                    identifyFields(line, newClass, object);
                }
            }
        }
    }

    public void identifyFields(String line, Class<?> newClass, Object object) {
        String[] currLine = line.trim().split(" ");
        String fieldName = currLine[0].substring(1);
        Field field = null;
        try {
            field = newClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        String[] rest = currLine[1].split("xsd:");
        //Figure out the data type
        String type = rest[1].substring(0, rest[1].indexOf('"'));
//        System.out.println("type ==== >"+ type);
        //Start and end index of to figure out the value
        int startInd = line.indexOf('>');
        int endInd = line.indexOf('<', startInd);
        // Field value
        String val = line.substring(startInd + 1, endInd);
        if(field != null){
            matchCorres(type, field, object, newClass, val);
        }
        setObj((SerializableObject) object);
    }
    private void matchCorres(String type, Field field, Object object, Class<?> newClass, String val){
        String methodName = setMethodName(field.getName());
        Class[] argTypes;
        switch (type){
            case "int":
                argTypes = new Class[]{Integer.TYPE};
                try {
                    Method method = newClass.getDeclaredMethod(methodName, argTypes);
                    method.invoke(object, Integer.parseInt(val));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                break;
            case "string":
                argTypes = new Class[]{String.class};
                try {
                    Method method = newClass.getDeclaredMethod(methodName, argTypes);
                    method.invoke(object, val);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                break;
            case "double":
                argTypes = new Class[]{Double.TYPE};
                try {
                    Method method = newClass.getDeclaredMethod(methodName, argTypes);
                    double v =  Double.parseDouble(val);
                    method.invoke(object, v);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                break;
            case "long":
                argTypes = new Class[]{Long.TYPE};
                try {
                    Method method = newClass.getDeclaredMethod(methodName, argTypes);
                    method.invoke(object, Long.valueOf(val));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                break;
            case "boolean":
                argTypes = new Class[]{Boolean.TYPE};
                try {
                    Method method = newClass.getDeclaredMethod(methodName, argTypes);
                    method.invoke(object, Boolean.parseBoolean(val));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                break;
            case "float":
                argTypes = new Class[]{Float.TYPE};
                try {
                    Method method = newClass.getDeclaredMethod(methodName, argTypes);
                    method.invoke(object, Float.parseFloat(val));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                break;
            case "char":
                argTypes = new Class[]{Character.TYPE};
                try {
                    Method method = newClass.getDeclaredMethod(methodName, argTypes);
                    method.invoke(object, val.charAt(0));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                break;
            case "short":
                argTypes = new Class[]{Short.TYPE};
                try {
                    Method method = newClass.getDeclaredMethod(methodName, argTypes);
                    method.invoke(object, Short.parseShort(val));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                break;
                default:
                    break;
        }
    }
    private String setMethodName(String name) {
        return "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
    }
}
