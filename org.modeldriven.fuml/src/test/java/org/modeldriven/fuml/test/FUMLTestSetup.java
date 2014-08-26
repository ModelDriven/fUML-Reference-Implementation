package org.modeldriven.fuml.test;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import junit.extensions.TestSetup;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 */
public class FUMLTestSetup extends TestSetup
{
   public static FUMLTestSetup newTestSetup(Class testClass)
   {
      return new FUMLTestSetup(testClass);
   }
   
   protected FUMLTestSetup(Class testClass)
   {
      super(new TestSuite(testClass));
   }   
}
