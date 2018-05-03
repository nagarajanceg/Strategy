package genericCheckpointing.xmlStoreRestore;

import genericCheckpointing.util.FileProcessor;
import genericCheckpointing.util.SerializableObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

public class XMLSerialization implements SerStrategy {

    private BufferedReader reader;
    private BufferedWriter writer;
    private FileProcessor fp;

    public XMLSerialization(BufferedReader reader, BufferedWriter writer, FileProcessor fp) {
        this.reader = reader;
        this.writer = writer;
        this.fp = fp;
    }

    @Override
    public void processInput(SerializableObject sObject) {
        System.out.println("next call");
        fp.writeLine(writer,"Hello from here");
        setRandomValues(sObject);
        populateXML(sObject);
    }

    private void setRandomValues(SerializableObject sObject){
        Field[] fields = sObject.getClass().getDeclaredFields();

        for (Field field: fields){

            setAppropriateType(sObject, field);

        }
    }
    private void setAppropriateType(SerializableObject sObject, Field field){
        String methodName = setMethodName(field.getName());
        Method method = null;
        String type = field.getType().toString();
        Random random = new Random();
//        System.out.println("Mytype === "+ type);
        switch (type){
            case "int":
//                System.out.println("My switch break int");
                try {
                    method = sObject.getClass().getDeclaredMethod(methodName, int.class);
                    try {
                        int num = random.nextInt(2000)+1;
                        method.invoke(sObject, num);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    System.out.println(method);
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
                    method = sObject.getClass().getDeclaredMethod(methodName, Boolean.class);
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
                    method = sObject.getClass().getDeclaredMethod(methodName, Long.class);
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
                    method = sObject.getClass().getDeclaredMethod(methodName, Short.class);
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
                    method = sObject.getClass().getDeclaredMethod(methodName, Character.class);
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
                    method = sObject.getClass().getDeclaredMethod(methodName, Double.class);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                try {
                    method.invoke(sObject, random.nextDouble());

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                break;
            case "float":
                try {
                    method = sObject.getClass().getDeclaredMethod(methodName, Float.class);
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
//        System.out.println(type);
//        System.out.println(method);
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
    //Reference -- http://www.baeldung.com/java-generate-random-long-float-integer-double
    private long generateRandomLong() {
        long leftLimit = 1L;
        long rightLimit = 10000L;
        long generatedLong = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
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
            formatter.append("\n <"+field.getName() +" xsi:type=\"xsd:"+field.getType()+"\">"+value+"</"+field.getName()+">");
        }
        formatter.append("\n </complexType>\n" + "</DPSerialization>");
        System.out.println(formatter);
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
