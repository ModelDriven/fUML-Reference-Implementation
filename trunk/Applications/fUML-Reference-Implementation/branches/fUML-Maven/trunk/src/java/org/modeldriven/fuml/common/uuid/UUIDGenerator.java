package org.modeldriven.fuml.common.uuid;

/**
 *
 **/
public class UUIDGenerator
{
    static private UUIDGenerator instance = null;
    static private ShutdownHook shutdownHook = null;
    
    private UUIDGenerator()
    {
        if (shutdownHook == null){
            // Add a shutdown hook to save the uuid seed
            shutdownHook = new ShutdownHook();
            Runtime.getRuntime().addShutdownHook(shutdownHook);
        }
    }

    public static UUIDGenerator instance()
    {
        if (instance == null)
            initializeInstance();
        return instance;
    }

    private static synchronized void initializeInstance()
    {
        if (instance == null){
            instance = new UUIDGenerator();
        }
    }

    public synchronized UUID getId()
    {
        return new UUID();
    }
    
    public synchronized String getIdString36()
    {
        return (new UUID()).toString();
    }

    public synchronized String getIdString22()
    {
        return (new UUID()).to64String();
    }
    
    /**
     * Java JDK shutdown hook to save uuid seed
     */
    class ShutdownHook extends Thread{
        UUID uuid;
        
        ShutdownHook(){
            uuid = new UUID();
        }
        public void run(){
            uuid.saveSeedData();
        }
    }
}
