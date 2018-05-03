package genericCheckpointing.xmlStoreRestore;

import genericCheckpointing.util.FileProcessor;
import genericCheckpointing.util.SerializableObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public class StoreRestoreHandler implements InvocationHandler {
    private FileProcessor fp ;
    private BufferedReader reader;
    private BufferedWriter writer;
    private SerializableObject sObject;

    public StoreRestoreHandler() {
        this.fp = new FileProcessor();
    }

    public void setInputFile(String name){
        System.out.println(name);
        reader = fp.readerDesc(name);
        writer = fp.writerDesc(name);
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
//        System.out.println("Method Name == "+methodName);
        if(methodName.equals("readObj")){
            XMLDeserialization deserialization = new XMLDeserialization(fp, reader);
            deserialize(deserialization);
            return deserialization.getObj();
        }else{
            System.out.println(Arrays.toString(args));
            XMLSerialization serialization = new XMLSerialization(reader, writer, fp);
            serialize((SerializableObject) args[0], serialization);
            System.out.println("Reached hear");
            fp.writeLine(writer, "hello");
        }
        return null;
    }
    private void deserialize( SerStrategy strategy){
        strategy.processInput(null);
    }
    private void serialize(SerializableObject sObject, SerStrategy strategy){
        strategy.processInput(sObject);
    }
    public void close(){
        fp.close(writer);
        fp.close(reader);
    }
}
