package genericCheckpointing.util;

public class MyAllTypesFirst extends SerializableObject {
    private int myInt;
    private String myString;
    public int getMyInt() {
        return myInt;
    }

    public void setMyInt(int myInt) {
        this.myInt = myInt;
    }

    public String getMyString() {
        return myString;
    }

    public void setMyString(String myString) {
        this.myString = myString;
    }

    @Override
    public String toString() {
        return "MyAllTypesFirst{" +
                "myInt=" + myInt +
                '}';
    }
}
