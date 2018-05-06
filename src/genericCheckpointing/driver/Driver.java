package genericCheckpointing.driver;

import genericCheckpointing.server.RestoreI;
import genericCheckpointing.server.StoreI;
import genericCheckpointing.server.StoreRestoreI;
import genericCheckpointing.util.*;
import genericCheckpointing.xmlStoreRestore.StoreRestoreHandler;

import java.util.ArrayList;
import java.util.List;

public class Driver {

    public static void main(String[] args){
        if (args.length != 4 || args[0].equals("${arg0}") || args[1].equals("${arg1}") || args[2].equals("${arg2}") || args[3].equals("${arg3}") ) {
            System.err.println("Error: Incorrect number of arguments. Program accepts 4 arguments.");
            System.exit(0);
        }
        int debugLevel = Integer.parseInt(args[3]);
        ProxyCreator pc = new ProxyCreator();
        StoreRestoreHandler storeHandler = new StoreRestoreHandler(debugLevel);
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
        String mode = args[0];
        String fileName = args[1];
        int NUM_OF_OBJECTS = Integer.parseInt(args[2]);
        Logger.setDebugValue(debugLevel);

        if(mode.equalsIgnoreCase("deser")){
            storeHandler.setInputFile(fileName);
            for (int j=0; j< 2 * NUM_OF_OBJECTS; j++) {
                myRecordRet = ((RestoreI) cpointRef).readObj("XML");
                if (myRecordRet != null){
                    myRecordList.add(myRecordRet);
                    Logger.writeMessage(myRecordRet.toString(), Logger.getDebugValue());
//                    System.out.println(myRecordRet);
                }
            }

        }else{
            storeHandler.setOutputFile(fileName);
            storeHandler.setInputFile(fileName);
            for (int i=0; i<NUM_OF_OBJECTS; i++) {

                // FIXME: create these object instances correctly using an explicit value constructor
                // use the index variable of this loop to change the values of the arguments to these constructors
                MyAllTypesFirst myFirst = new MyAllTypesFirst();
                MyAllTypesSecond mySecond = new MyAllTypesSecond();
                // FIXME: store myFirst and mySecond in the data structure
                ((StoreI) cpointRef).writeObj(myFirst,1234, "XML");
                ((StoreI) cpointRef).writeObj(mySecond, 1235, "XML");
            }
            Logger.writeMessage(" -------------------- Before Serialize  --------------------", Logger.getDebugValue());
            for (SerializableObject obj : storeHandler.getBeforeSerialize()){
                Logger.writeMessage(obj.toString(), Logger.getDebugValue());
            }
            storeHandler.close();
            int misMatch = 0;
            Logger.writeMessage(" -------------------- After Serialize ----------------------", Logger.getDebugValue());
            for (int i=0; i<2*NUM_OF_OBJECTS; i++){
                myRecordRet = ((RestoreI) cpointRef).readObj("XML");
                if (myRecordRet != null){
                    myRecordList.add(myRecordRet);
                    Logger.writeMessage(myRecordRet.toString(), Logger.getDebugValue());
                }
            }

            for (int i=0; i<2*NUM_OF_OBJECTS; i++){
                if(!storeHandler.getBeforeSerialize().get(i).equals(myRecordList.get(i))){
                    misMatch += 1;
                }
            }
            Logger.writeMessage("misMatch Count ==> "+misMatch, Logger.getDebugValue());
        }
    }

}
