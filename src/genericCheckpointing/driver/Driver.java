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
        storeHandler.setInputFile("input.txt");
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
        String mode = "ser";
//        String mode = "deser";
        int NUM_OF_OBJECTS = 3;
        if(mode.equalsIgnoreCase("deser")){
            for (int j=0; j< 2 * NUM_OF_OBJECTS; j++) {
                myRecordRet = ((RestoreI) cpointRef).readObj("XML");
                System.out.println(myRecordRet);
                myRecordList.add(myRecordRet);
            }
        }else{
            for (int i=0; i<1; i++) {

                // FIXME: create these object instances correctly using an explicit value constructor
                // use the index variable of this loop to change the values of the arguments to these constructors
               MyAllTypesFirst myFirst = new MyAllTypesFirst(25, "Serialize object", 56.789,78.26f);
//                MyAllTypesFirst myFirst = new MyAllTypesFirst();
                MyAllTypesSecond mySecond = new MyAllTypesSecond(45, "Serialize second object", 86.789,98.26f);

                // FIXME: store myFirst and mySecond in the data structure
                ((StoreI) cpointRef).writeObj(myFirst,1234, "XML");
                ((StoreI) cpointRef).writeObj(mySecond, 1235, "XML");

            }
        }
        storeHandler.close();
    }

}
