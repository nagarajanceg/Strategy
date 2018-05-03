package genericCheckpointing.xmlStoreRestore;

import genericCheckpointing.util.FileProcessor;

import java.io.BufferedReader;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class StoreRestoreHandler implements InvocationHandler {
    private FileProcessor fp ;
    private BufferedReader reader;
    public StoreRestoreHandler() {
        this.fp = new FileProcessor();
    }

    public void setInputFile(String name){
        System.out.println(name);
        reader = fp.readerDesc(name);
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
            XMLSerialization serialization = new XMLSerialization();

        }
        return null;
    }
    private void deserialize( SerStrategy strategy){
        strategy.processInput(null);
    }
}
