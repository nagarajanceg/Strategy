package genericCheckpointing.server;

import genericCheckpointing.utill.SerializableObject;

public interface RestoreI extends StoreRestoreI {
    SerializableObject readObj(String wireFormat);
}
