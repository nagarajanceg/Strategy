package genericCheckpointing.driver;

import genericCheckpointing.server.RestoreI;
import genericCheckpointing.server.StoreI;
import genericCheckpointing.server.StoreRestoreI;
import genericCheckpointing.util.MyAllTypesFirst;
import genericCheckpointing.util.MyAllTypesSecond;
import genericCheckpointing.util.ProxyCreator;
import genericCheckpointing.util.SerializableObject;
import genericCheckpointing.xmlStoreRestore.StoreRestoreHandler;

import java.util.ArrayList;
import java.util.List;

public class Driver {

    public static void main(String[] args){
        ProxyCreator pc = new ProxyCreator();
        StoreRestoreHandler storeHandler = new StoreRestoreHandler();
        // create a proxy
        StoreRestoreI cpointRef;
        cpointRef = (StoreRestoreI) pc.createProxy(
                new Class[] {
                        StoreI.class, RestoreI.class
                },
                storeHandler
        );
        List<SerializableObject> myRecordList = new ArrayList<>();
        SerializableObject myRecordRet;
        String mode = "serdeser";
//        String mode = "deser";
        int NUM_OF_OBJECTS = 1;
        if(mode.equalsIgnoreCase("deser")){
            storeHandler.setInputFile("checkpoint.txt");
            for (int j=0; j< 2 * NUM_OF_OBJECTS; j++) {
                myRecordRet = ((RestoreI) cpointRef).readObj("XML");
                if (myRecordRet != null){
                    myRecordList.add(myRecordRet);
                    System.out.println(myRecordRet);
                }
            }

        }else{
            storeHandler.setOutputFile("checkpoint.txt");
            storeHandler.setInputFile("checkpoint.txt");
            for (int i=0; i<NUM_OF_OBJECTS; i++) {

                // FIXME: create these object instances correctly using an explicit value constructor
                // use the index variable of this loop to change the values of the arguments to these constructors
                MyAllTypesFirst myFirst = new MyAllTypesFirst();
                MyAllTypesSecond mySecond = new MyAllTypesSecond();
                // FIXME: store myFirst and mySecond in the data structure
                ((StoreI) cpointRef).writeObj(myFirst,1234, "XML");
                ((StoreI) cpointRef).writeObj(mySecond, 1235, "XML");
            }
            for (SerializableObject obj : storeHandler.getBeforeSerialize()){
                System.out.println("element ==>");
                System.out.println(obj);
            }
            storeHandler.close();
            int misMatch = 0;
            for (int i=0; i<2*NUM_OF_OBJECTS; i++){
                System.out.println("Deserialize ==============================");

                myRecordRet = ((RestoreI) cpointRef).readObj("XML");
                if (myRecordRet != null){
                    myRecordList.add(myRecordRet);
                    System.out.println(myRecordRet);
                }
            }

            for (int i=0; i<2*NUM_OF_OBJECTS; i++){
                if(!storeHandler.getBeforeSerialize().get(i).equals(myRecordList.get(i))){
                    misMatch += 1;
                }
            }
            System.out.println("misMatch == "+misMatch);
        }
    }

}
