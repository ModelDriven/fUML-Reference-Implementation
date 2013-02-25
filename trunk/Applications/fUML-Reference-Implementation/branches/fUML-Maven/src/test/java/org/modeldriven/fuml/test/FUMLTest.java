package org.modeldriven.fuml.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;

import javax.management.MBeanServerConnection;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fUML.Debug;
import fUML.Semantics.Classes.Kernel.ExtensionalValueList;
import fUML.Syntax.Classes.Kernel.Classifier;
import fUML.Syntax.Classes.Kernel.Element;
import fUML.Test.InitTestEnvironment;

/**
  */
public abstract class FUMLTest extends TestCase {

    protected Log log = LogFactory.getLog(getClass().getName());

    public FUMLTest() {
    }

    public FUMLTest(String name) {
        super(name);
    }

    /** Get the MBeanServerConnection from JNDI */
    public MBeanServerConnection getServer() throws NamingException {
        throw new RuntimeException("not implemented");
    }

    /** True, if -Djbossws.integration.target=jboss?? */
    public boolean isTargetJBoss() {
        return true;
    }

    /** True, if -Djbossws.integration.target=tomcat */
    public boolean isTargetTomcat() {
        return false;
    }

    /**
     * Get the client's env context for a given name.
     */
    protected InitialContext getInitialContext(String clientName) throws NamingException {
        InitialContext iniCtx = new InitialContext();
        Hashtable env = iniCtx.getEnvironment();
        env.put(Context.URL_PKG_PREFIXES, "org.jboss.naming.client");
        env.put("j2ee.clientName", clientName);
        return new InitialContext(env);
    }

    /**
     * Get the client's env context
     */
    protected InitialContext getInitialContext() throws NamingException {
        return getInitialContext("jbossws-client");
    }

    public boolean hasJDK15() {
        try {
            Class.forName("java.lang.Enum");
            return true;
        } catch (ClassNotFoundException ex) {
            return false;
        }
    }

    public static String getServerHost() {
        String hostName = System.getProperty("jbosstest.host.name", "localhost");
        return hostName;
    }

    public static void assertEquals(Object exp, Object was) {
        if (exp instanceof Object[] && was instanceof Object[])
            assertEqualsArray((Object[]) exp, (Object[]) was);
        else if (exp instanceof byte[] && was instanceof byte[])
            assertEqualsArray((byte[]) exp, (byte[]) was);
        else if (exp instanceof boolean[] && was instanceof boolean[])
            assertEqualsArray((boolean[]) exp, (boolean[]) was);
        else if (exp instanceof short[] && was instanceof short[])
            assertEqualsArray((short[]) exp, (short[]) was);
        else if (exp instanceof int[] && was instanceof int[])
            assertEqualsArray((int[]) exp, (int[]) was);
        else if (exp instanceof long[] && was instanceof long[])
            assertEqualsArray((long[]) exp, (long[]) was);
        else if (exp instanceof float[] && was instanceof float[])
            assertEqualsArray((float[]) exp, (float[]) was);
        else if (exp instanceof double[] && was instanceof double[])
            assertEqualsArray((double[]) exp, (double[]) was);
        else
            TestCase.assertEquals(exp, was);
    }

    private static void assertEqualsArray(Object[] exp, Object[] was) {
        if (exp == null && was == null)
            return;

        if (exp != null && was != null) {
            if (exp.length != was.length) {
                fail("Expected <" + exp.length + "> array items, but was <" + was.length + ">");
            } else {
                for (int i = 0; i < exp.length; i++) {

                    Object compExp = exp[i];
                    Object compWas = was[i];
                    assertEquals(compExp, compWas);
                }
            }
        } else if (exp == null) {
            fail("Expected a null array, but was: " + Arrays.asList(was));
        } else if (was == null) {
            fail("Expected " + Arrays.asList(exp) + ", but was: null");
        }
    }

    private static void assertEqualsArray(byte[] exp, byte[] was) {
        assertTrue("Arrays don't match", Arrays.equals(exp, was));
    }

    private static void assertEqualsArray(boolean[] exp, boolean[] was) {
        assertTrue("Arrays don't match", Arrays.equals(exp, was));
    }

    private static void assertEqualsArray(short[] exp, short[] was) {
        assertTrue("Arrays don't match", Arrays.equals(exp, was));
    }

    private static void assertEqualsArray(int[] exp, int[] was) {
        assertTrue("Arrays don't match", Arrays.equals(exp, was));
    }

    private static void assertEqualsArray(long[] exp, long[] was) {
        assertTrue("Arrays don't match", Arrays.equals(exp, was));
    }

    private static void assertEqualsArray(float[] exp, float[] was) {
        assertTrue("Arrays don't match", Arrays.equals(exp, was));
    }

    private static void assertEqualsArray(double[] exp, double[] was) {
        assertTrue("Arrays don't match", Arrays.equals(exp, was));
    }

    public byte[] getContent(InputStream source) throws IOException {
        byte[] buf = new byte[4000];
        ByteArrayOutputStream os = new ByteArrayOutputStream(4000);
        try {
            int len = -1;
            while ((len = source.read(buf)) != -1)
                os.write(buf, 0, len);
        } finally {
            source.close();
            os.flush();
        }
        return os.toByteArray();
    }
}
