package genericCheckpointing.util;

public class MyAllTypesSecond extends SerializableObject {
    private int myInt;
    private String myString;
    private boolean myBool;
    private long myLong;
    private long myOtherLong;
    private int myOtherInt;
    private double myDoubleT;
    private double myOtherDoubleT;
    private float myFloatT;
    private short myShortT;
    private char myCharT  ;

    public MyAllTypesSecond() {
    }

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

    public long getMyLong() {
        return myLong;
    }

    public void setMyLong(long myLong) {
        this.myLong = myLong;
    }

    public long getMyOtherLong() {
        return myOtherLong;
    }

    public void setMyOtherLong(long myOtherLong) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MyAllTypesSecond that = (MyAllTypesSecond) o;

        if (myInt != that.myInt) return false;
        if (myBool != that.myBool) return false;
        if (myLong != that.myLong) return false;
        if (myOtherLong != that.myOtherLong) return false;
        if (myOtherInt != that.myOtherInt) return false;
        if (Double.compare(that.myDoubleT, myDoubleT) != 0) return false;
        if (Double.compare(that.myOtherDoubleT, myOtherDoubleT) != 0) return false;
        if (Float.compare(that.myFloatT, myFloatT) != 0) return false;
        if (myShortT != that.myShortT) return false;
        if (myCharT != that.myCharT) return false;
        return myString.equals(that.myString);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = myInt;
        result = 31 * result + myString.hashCode();
        result = 31 * result + (myBool ? 1 : 0);
        result = 31 * result + (int) (myLong ^ (myLong >>> 32));
        result = 31 * result + (int) (myOtherLong ^ (myOtherLong >>> 32));
        result = 31 * result + myOtherInt;
        temp = Double.doubleToLongBits(myDoubleT);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(myOtherDoubleT);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (myFloatT != +0.0f ? Float.floatToIntBits(myFloatT) : 0);
        result = 31 * result + (int) myShortT;
        result = 31 * result + (int) myCharT;
        return result;
    }

    @Override
    public String toString() {
        return "MyAllTypesSecond{" +
                "\n myInt=" + myInt +
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
                "\n }";
    }
}
