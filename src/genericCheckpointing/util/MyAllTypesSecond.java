package genericCheckpointing.util;

public class MyAllTypesSecond extends SerializableObject {
    private int myInt;
    private String myString;
    private boolean myBool;
    private Long myLong;
    private Long myOtherLong;
    private int myOtherInt;
    private double myDoubleT;
    private double myOtherDoubleT;
    private float myFloatT;
    private short myShortT;
    private char myCharT;

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

    public boolean isMyBool() {
        return myBool;
    }

    public void setMyBool(boolean myBool) {
        this.myBool = myBool;
    }

    public Long getMyLong() {
        return myLong;
    }

    public void setMyLong(Long myLong) {
        this.myLong = myLong;
    }

    public Long getMyOtherLong() {
        return myOtherLong;
    }

    public void setMyOtherLong(Long myOtherLong) {
        this.myOtherLong = myOtherLong;
    }

    public int getMyOtherInt() {
        return myOtherInt;
    }

    public void setMyOtherInt(int myOtherInt) {
        this.myOtherInt = myOtherInt;
    }

    public double getMyDoubleT() {
        return myDoubleT;
    }

    public void setMyDoubleT(double myDoubleT) {
        this.myDoubleT = myDoubleT;
    }

    public double getMyOtherDoubleT() {
        return myOtherDoubleT;
    }

    public void setMyOtherDoubleT(double myOtherDoubleT) {
        this.myOtherDoubleT = myOtherDoubleT;
    }

    public float getMyFloatT() {
        return myFloatT;
    }

    public void setMyFloatT(float myFloatT) {
        this.myFloatT = myFloatT;
    }

    public short getMyShortT() {
        return myShortT;
    }

    public void setMyShortT(short myShortT) {
        this.myShortT = myShortT;
    }

    public char getMyCharT() {
        return myCharT;
    }

    public void setMyCharT(char myCharT) {
        this.myCharT = myCharT;
    }

    @Override
    public String toString() {
        return "MyAllTypesFirst{" +
                "myInt=" + myInt +
                ",\n myString='" + myString + '\'' +
                ",\n myBool=" + myBool +
                ",\n myLong=" + myLong +
                ",\n myOtherLong=" + myOtherLong +
                ",\n myOtherInt=" + myOtherInt +
                ",\n myDoubleT=" + myDoubleT +
                ",\n myOtherDoubleT=" + myOtherDoubleT +
                ",\n myFloatT=" + myFloatT +
                ",\n myShortT=" + myShortT +
                ",\n myCharT=" + myCharT +
                '}';
    }
}
