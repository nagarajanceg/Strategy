package genericCheckpointing.xmlStoreRestore;

import genericCheckpointing.utill.SerializableObject;

public interface SerStrategy {
    void processInput(SerializableObject sObject);
}
