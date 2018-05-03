package genericCheckpointing.xmlStoreRestore;

import genericCheckpointing.util.FileProcessor;
import genericCheckpointing.util.SerializableObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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

    }

    private void populateXML(SerializableObject sObject){
        StringBuffer formater = new StringBuffer();
        formater.append("<DPSerialization>\n <complexType xsi:type=\"");
        formater.append(sObject.getClass().getName()+"\">");
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
            formater.append("\n <"+field.getName() +" xsi:type=\"xsd:"+field.getType()+"\">"+value+"</"+field.getName()+">");
        }
        formater.append("\n </complexType>\n" + "</DPSerialization>");
        System.out.println(formater);
    }
    private String getMethodName(String name) {
        if(name.equalsIgnoreCase("myBool")){
            return "is" + name.substring(0, 1).toUpperCase() + name.substring(1);
        }
        return "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
    }
}
