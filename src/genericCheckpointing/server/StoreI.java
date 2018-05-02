package genericCheckpointing.server;

import genericCheckpointing.utill.MyAllTypesFirst;
import genericCheckpointing.utill.MyAllTypesSecond;

public interface StoreI extends StoreRestoreI {
    void writeObj(MyAllTypesFirst aRecord, int authID, String wireFormat);
    void writeObj(MyAllTypesSecond bRecord, int authID, String wireFormat);
}
