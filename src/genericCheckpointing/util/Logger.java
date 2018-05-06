package genericCheckpointing.util;

public class Logger {

    public enum DebugLevel { NONE, SHOW };

    private static DebugLevel debugLevel;



    public static void setDebugValue (int levelIn) {
        switch (levelIn) {
            case 1: debugLevel = DebugLevel.SHOW; break;
            case 0: debugLevel = DebugLevel.NONE; break;
            default: debugLevel = DebugLevel.NONE; break;
        }
    }

    public static DebugLevel getDebugValue()
    {
        return debugLevel;
    }
    public static void setDebugValue (DebugLevel levelIn) {
        debugLevel = levelIn;
    }

    public static void writeMessage (String     message  ,
                                     DebugLevel levelIn ) {
        if (levelIn == DebugLevel.SHOW)
            System.out.println(message);
    }

    public String toString() {
        return "The debug level has been set to the following " + debugLevel;
    }

}