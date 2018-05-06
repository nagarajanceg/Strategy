package genericCheckpointing.xmlStoreRestore;

import genericCheckpointing.util.FileProcessor;
import genericCheckpointing.util.SerializableObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class XMLSerialization implements SerStrategy {

    private BufferedReader reader;
    private BufferedWriter writer;
    private FileProcessor fp;
    private List<SerializableObject> beforeSerialize = null;
    public XMLSerialization(BufferedReader reader, BufferedWriter writer, FileProcessor fp) {
        this.reader = reader;
        this.writer = writer;
        this.fp = fp;
        this.beforeSerialize = new ArrayList<>();
    }

    public List<SerializableObject> getBeforeSerialize() {
        return beforeSerialize;
    }

    @Override
    public void processInput(SerializableObject sObject) {
        setRandomValues(sObject);
        populateXML(sObject);
    }

    private void setRandomValues(SerializableObject sObject){
        Field[] fields = sObject.getClass().getDeclaredFields();

        for (Field field: fields){
            setAppropriateType(sObject, field);
        }
        this.beforeSerialize.add(sObject);
    }
    private void setAppropriateType(SerializableObject sObject, Field field){
        String methodName = setMethodName(field.getName());
        Method method = null;
        String type = field.getType().toString();
        Random random = new Random();
        switch (type){
            case "int":
                try {
                    method = sObject.getClass().getDeclaredMethod(methodName, Integer.TYPE);
                    try {
                        int num = random.nextInt(80)+1;
                        method.invoke(sObject, num);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                break;
            case "class java.lang.String":
                try {
                    method = sObject.getClass().getDeclaredMethod(methodName, String.class);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                try {
                    method.invoke(sObject,randString());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                break;
            case "boolean":
                boolean[] br = {true, false};
                try {
                    method = sObject.getClass().getDeclaredMethod(methodName, Boolean.TYPE);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                try {
                    method.invoke(sObject, br[random.nextInt(2)]);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                break;
            case "long":
                try {
                    method = sObject.getClass().getDeclaredMethod(methodName, Long.TYPE);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                try {
                    method.invoke(sObject,generateRandomLong());

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                break;
            case "short":
                try {
                    method = sObject.getClass().getDeclaredMethod(methodName, Short.TYPE);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                try {
                    method.invoke(sObject, generateRandomShort(random));

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                break;
            case "char":
                try {
                    method = sObject.getClass().getDeclaredMethod(methodName, Character.TYPE);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                try {
                    method.invoke(sObject, generateRandomChar(random));

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                break;
            case "double":
                try {
                    method = sObject.getClass().getDeclaredMethod(methodName, Double.TYPE);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                try {
                    method.invoke(sObject, generateRandomDouble(random));

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                break;
            case "float":
                try {
                    method = sObject.getClass().getDeclaredMethod(methodName, Float.TYPE);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                try {
                    method.invoke(sObject, random.nextFloat());

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                break;

            default:
                break;
        }
    }
    //Reference -- http://www.baeldung.com/java-random-string
    private String randString(){
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();
        return generatedString;
    }
    private double generateRandomDouble(Random random){
        double randomValue = 3.0 + ((55.0 - 3.0) * random.nextDouble());
        return randomValue;
    }
    //Reference -- http://www.baeldung.com/java-generate-random-long-float-integer-double
    private long generateRandomLong() {
        long leftLimit = 1L;
        long rightLimit = 10000L;
        long generatedLong;
        generatedLong = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
        return generatedLong;
    }

    private short generateRandomShort(Random random){
        return (short) random.nextInt(Short.MAX_VALUE + 1);
    }
    //http://programming.guide/java/generate-random-character.html
    private char generateRandomChar(Random random){
        return (char) (random.nextInt(26)+'a');
    }

    private void populateXML(SerializableObject sObject){
        StringBuffer formatter = new StringBuffer();
        formatter.append("<DPSerialization>\n <complexType xsi:type=\"");
        formatter.append(sObject.getClass().getName()+"\">");
        Field[] fields = sObject.getClass().getDeclaredFields();
        Method method = null;
        for (Field field : fields){
//            String name = field.getName();
            try {
                method = sObject.getClass().getMethod(getMethodName(field.getName()));
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                System.exit(1);
            }
            String value = "";
            if(method != null){
                try {
                    value = method.invoke(sObject).toString();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            if(field.getType().toString().equals("class java.lang.String")){
                formatter.append("\n  <"+field.getName() +" xsi:type=\"xsd:"+"string"+"\">"+value+"</"+field.getName()+">");
            }else{
                if(checkLimitValue(value, field.getType().toString())){
                    continue;
                }
                formatter.append("\n  <"+field.getName() +" xsi:type=\"xsd:"+field.getType()+"\">"+value+"</"+field.getName()+">");
            }
        }
        formatter.append("\n </complexType>\n" + "</DPSerialization>\n");
        fp.writeLine(writer, formatter.toString());
    }
    private boolean checkLimitValue(String value, String type){
        if (type.equalsIgnoreCase("int")){
            if(Integer.parseInt(value) < 10){
                return true;
            }
        }else if(type.equalsIgnoreCase("double")){
            if(Double.parseDouble(value) < 10){
                return true;
            }
        }else if (type.equalsIgnoreCase("long")){
            if(Long.parseLong(value) < 10){
                return true;
            }
        }
        return false;
    }
    private String getMethodName(String name) {
        if(name.equalsIgnoreCase("myBool")){
            return "is" + name.substring(0, 1).toUpperCase() + name.substring(1);
        }
        return "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
    }
    private String setMethodName(String name) {
        return "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
    }
}
