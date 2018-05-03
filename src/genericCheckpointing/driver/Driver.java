package genericCheckpointing.driver;

import genericCheckpointing.server.RestoreI;
import genericCheckpointing.server.StoreI;
import genericCheckpointing.server.StoreRestoreI;
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
        String mode = "deser";

        if(mode.equalsIgnoreCase("deser")){
            for (int j=0; j<3; j++) {
                myRecordRet = ((RestoreI) cpointRef).readObj("XML");
                System.out.println(myRecordRet);
                myRecordList.add(myRecordRet);
            }
        }else{

        }

    }

}
