package org.modeldriven.fuml.common.uuid;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.GregorianCalendar;
import java.security.SecureRandom;


public class UUID implements Serializable, Comparable {
    static private byte[] node = null;
    static private long lastTime = 0;
    static private long gregorianChange =
        (new GregorianCalendar()).getGregorianChange().getTime();
    static private SecureRandom random = null;
    static private int count = 0;
    
    static private final int SEED_SIZE = 24;    // 24 bytes seed

    static private void initNode()
    {
        if (node == null){
            File file = null;

            node = new byte[6];

            try {
                file = new File(System.getProperty("java.home"), "uuid.node");
            }
            catch (SecurityException e) {}

            if (file != null)
                try {
                    FileInputStream in = new FileInputStream(file);

                    if (in.read(node) == node.length){
                        random = new SecureRandom(node);
                        
                        // Re-seed the secure random generator
                        FileInputStream seed_in = null;
                        try{
                            byte[] seed = new byte[SEED_SIZE];
                            File seed_file = new File(System.getProperty("java.home"), "uuid.seed");
                            seed_in = new FileInputStream(seed_file);
                            if (in.read(seed) == seed.length)
                                random.setSeed(seed);
                            else
                                random.setSeed(System.currentTimeMillis());
                        }
                        catch (Exception ex){
                            // This is not very good for randomness
                            // but the best thing to increase entropy
                            random.setSeed(System.currentTimeMillis());
                        }
                    }

                    in.close();
                } catch (Throwable t) {}

            if (random == null){
                random = new SecureRandom();
                // Warning: this takes time but should not happen more than
                // once.
                random.nextInt();

                random.nextBytes(node);
                node[0] |= (byte) 0x80;

                if (file != null)
                    try {
                        FileOutputStream out = new FileOutputStream(file);

                        out.write(node);
                        out.close();
                    }
                    catch (Throwable t) {}
            }
        }
    }

    static private final char[] digits = {
        '0', '1', '2', '3', '4', '5', '6', '7',
        '8', '9', 'a', 'b', 'c', 'd', 'e', 'f',
        'g', 'h' ,'i', 'j', 'k', 'l', 'm', 'n',
        'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
        'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',
        'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 
        'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
        'U', 'V', 'W', 'X', 'Y', 'Z', '_', '$'
    };

    static private void get16Bytes(byte[] src, char[] buf,
                                   int dst, int from, int to)
    {
        for (int i = from; i < to; ++i, dst += 2) {
            int b = (int) src[i];

            for (int j = 0, charPos = dst + 1; j < 2; j++, charPos--) {
                buf[charPos] = digits[b & 15];
                b >>>= 4;
            }
        }
    }

    static private void get64Bytes(char[] buf, int dst, long n)
    {
        for (int i = 0, charPos = dst + 10; i < 11; i++, charPos--, n >>>= 6)
            buf[charPos] = digits[(int) (n & 63)];
    }

    static public String toString(byte[] uuid)
    {
        char[] buf = new char[36];

        get16Bytes(uuid, buf, 0, 0, 4);
        buf[8] = '-';
        get16Bytes(uuid, buf, 9, 4, 6);
        buf[13] = '-';
        get16Bytes(uuid, buf, 14, 6, 8);
        buf[18] = '-';
        get16Bytes(uuid, buf, 19, 8, 10);
        buf[23] = '-';
        get16Bytes(uuid, buf, 24, 10, 16);

        return new String(buf);
    }

    static public String to64String(byte[] uuid)
    {
        char[] buf = new char[22];
        long l;

        l = ( ((long) uuid[0] << 56) |
             (((long) uuid[1] << 56) >>> 8) |
             (((long) uuid[2] << 56) >>> 16) |
             (((long) uuid[3] << 56) >>> 24) |
             (((long) uuid[4] << 56) >>> 32) |
             (((long) uuid[5] << 56) >>> 40) |
             (((long) uuid[6] << 56) >>> 48) |
             (((long) uuid[7] << 56) >>> 56));
        get64Bytes(buf, 0, l);

        l = ( ((long) uuid[ 8] << 56) |
             (((long) uuid[ 9] << 56) >>> 8) |
             (((long) uuid[10] << 56) >>> 16) |
             (((long) uuid[11] << 56) >>> 24) |
             (((long) uuid[12] << 56) >>> 32) |
             (((long) uuid[13] << 56) >>> 40) |
             (((long) uuid[14] << 56) >>> 48) |
             (((long) uuid[15] << 56) >>> 56));
        get64Bytes(buf, 11, l);

        return new String(buf);
    }

    static public long getCreationTime(byte[] uuid)
        throws MalformedUUIDException
    {
        if (uuid.length != 16)
            throw new MalformedUUIDException("data is not 16 bytes long");
        else
        {
            long creationTime =
                ((long) (uuid[0] < 0 ? uuid[0] + 256 : uuid[0]) << 24 |
                 (long) (uuid[1] < 0 ? uuid[1] + 256 : uuid[1]) << 16 |
                 (long) (uuid[2] < 0 ? uuid[2] + 256 : uuid[2]) << 8  |
                 (long) (uuid[3] < 0 ? uuid[3] + 256 : uuid[3])       |
                 (long) (uuid[4] < 0 ? uuid[4] + 256 : uuid[4]) << 40 |
                 (long) (uuid[5] < 0 ? uuid[5] + 256 : uuid[5]) << 32 |
                 (long) (uuid[6] & 0x0f)                        << 56 |
                 (long) (uuid[7] < 0 ? uuid[7] + 256 : uuid[7]) << 48);

            creationTime /= 10000L;
            creationTime += gregorianChange;

            return creationTime;
        }
    }

    private byte[] uuid;

    /**
     * Generate a new UUID.
     */

    public UUID(){
        byte[] clockSequence = new byte[2];
        long currentTime = 0;
        short timeMid, timeHi;
        int timeLow;

        synchronized (this.getClass()) {
            if (node == null)
                initNode();
        }
        currentTime = System.currentTimeMillis();
        currentTime -= gregorianChange;
        currentTime *= 10000L;
        currentTime += count++;

        random.nextBytes(clockSequence);

        timeLow = (int) (currentTime & 0xffffffffL);
        timeMid = (short) ((currentTime >>> 32) & 0xffffL);
        timeHi = (short) ((currentTime >>> 48) & 0xffffL);

        uuid = new byte[16];

        uuid[0] = (byte) ((timeLow >>> 24) & 0xff);
        uuid[1] = (byte) ((timeLow >>> 16) & 0xff);
        uuid[2] = (byte) ((timeLow >>> 8) & 0xff);
        uuid[3] = (byte) (timeLow & 0xff);

        uuid[4] = (byte) ((timeMid >>> 8) & 0xff);
        uuid[5] = (byte) (timeMid & 0xff);

        uuid[6] = (byte) ((timeHi >>> 8) & 0xff);
        uuid[7] = (byte) (timeHi & 0xff);

        uuid[8] = clockSequence[0];
        uuid[9] = clockSequence[1];

        uuid[10] = node[0];
        uuid[11] = node[1];
        uuid[12] = node[2];
        uuid[13] = node[3];
        uuid[14] = node[4];
        uuid[15] = node[5];

        uuid[6] = (byte) ((byte) 0x10 | (uuid[6] & (byte) 0x0f));
        uuid[8] |= (byte) 0x80;
    }


    /**
     * Reconstruct a UUID from its intrinsic 16 bytes
     * @param data    a 16 byte array to implement the UUID with.
     */

    public UUID(byte[] data)
        throws MalformedUUIDException
    {
        if (data.length != 16)
            throw new MalformedUUIDException("data is not 16 bytes long");

        uuid = new byte[16];
        System.arraycopy(data, 0, uuid, 0, 16);
    }


    /**
     * Reconstruct a UUID from its various string representations.
     * @param data    xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx  (36 bytes)
     *                where x is a hexadecimal digit. 
     * @param data    xxxxxxxxxxxxxxxxxxxxxx                (22 bytes)
     *                where x is a base 64 digit.
     */

    public UUID(String id)
        throws MalformedUUIDException
    {
        int len = id.length();

        switch (len) {
          case 22:
            {
                char[] data = id.toCharArray();

                uuid = new byte[16];

                read64Bytes(data, 0, uuid, 0);
                read64Bytes(data, 11, uuid, 8);
            }
            break;

          case 36:
            {
                char[] data = id.toCharArray();

                verifyDash(data, 8);
                verifyDash(data, 13);
                verifyDash(data, 18);
                verifyDash(data, 23);

                uuid = new byte[16];

                read16Bytes(data, 0, uuid, 0, 4);
                read16Bytes(data, 9, uuid, 4, 2);
                read16Bytes(data, 14, uuid, 6, 2);
                read16Bytes(data, 19, uuid, 8, 2);
                read16Bytes(data, 24, uuid, 10, 6);
            }
            break;

          default:
            throw new MalformedUUIDException("'" + id +
                                             "' is not 36 or 22 bytes long");
        }
    }

    
    /**
     * Save the seed data for the next restart
     */
    public void saveSeedData(){
        FileOutputStream seed_out = null;
        try{
            if (random == null)
                return;
                
            byte[] seed = new byte[SEED_SIZE];
            random.nextBytes(seed);
            File seed_file = new File(System.getProperty("java.home"),"uuid.seed");
            seed_out = new FileOutputStream(seed_file);
            seed_out.write(seed);
            seed_out.close();
        }
        catch (Exception ex) {/*ignored*/}
    }

    
    /**
     * Return the standard string representation of this UUID.
     * Format is xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx,
     * where x is a hexadecimal digit.
     */

    public String toString()
    {
        return toString(uuid);
    }

    public String to64String()
    {
        return to64String(uuid);
    }


    /**
     * Compare two UUIDs. Different UUID objects are equal when their intrisic
     * bytes match.
     */

    public boolean equals(Object o)
    {
        if (o == this)
            return true;

        if (o instanceof UUID)
        {
            UUID u = (UUID) o;

            for (int i = 0; i < uuid.length; i++)
                if (uuid[i] != u.uuid[i])
                    return false;

            return true;
        }

        return false;
    }


    /* Comparable */

    public int compareTo(Object o)
    {
        byte[] uuid = ((UUID) o).getData();

        for (int i = 0; i < 16; i++) {
            byte ub = uuid[i];
            byte b = this.uuid[i];

            if (b < ub)
                return -1;
            if (b > ub)
                return 1;
        }

        return 0;
    }


    /**
     * Return the 16 byte byte array implementing this UUID.
     */

    public byte[] getData()
    {
        return uuid;
    }


    private void read16Bytes(char[] data, int from,
                             byte[] bytes, int to, int length)
        throws MalformedUUIDException
    {
        for (int i = 0; i < length; i++) {
            int c1 = c16Value(data[from++]);
            int c2 = c16Value(data[from++]);

            bytes[to++] = (byte) ((c1 << 4) + c2);
        }
    }

    private int c16Value(char c)
        throws MalformedUUIDException
    {
        if (c >= '0' && c <= '9')
            return c - '0';

        if (c >= 'a' && c <= 'f')
            return c - 'a' + 10;

        if (c >= 'A' && c <= 'F')
            return c - 'A' + 10;

        throw new MalformedUUIDException("'" + c + "' is not a base 16 digit");
    }

    private void read64Bytes(char[] data, int from, byte[] bytes, int to)
        throws MalformedUUIDException
    {
        long l = 0L;

        for (int i = 0; i < 11; i++)
            l = (l << 6) + c64Value(data[from++]);

        for (int i = 8; i > 0; l >>>= 8)
            bytes[to + --i] = (byte) (l & 0xff);
    }

    private long c64Value(char c)
        throws MalformedUUIDException
    {
        if (c >= '0' && c <= '9')
            return c - '0';

        if (c >= 'a' && c <= 'z')
            return c - 'a' + 10;

        if (c >= 'A' && c <= 'Z')
            return c - 'A' + 10 + 26;

        if (c == '_')
            return 62;

        if (c == '$')
            return 63;

        throw new MalformedUUIDException("'" + c + "' is not a base 64 digit");
    }

    private void verifyDash(char[] data, int index)
        throws MalformedUUIDException
    {
        if (data[index] != '-')
            throw new MalformedUUIDException("no '-' at position " + index);
    }

    /*
    static public void main(String[] argv)
        throws MalformedUUIDException
    {
        UUID id;

        if (argv.length > 0)
            id = new UUID(argv[0]);
        else
            id = new UUID();

        Debug.println(id + " " + id.to64String());
    }
     */
}
