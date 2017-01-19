package org.modeldriven.fuml.test;

import junit.extensions.TestSetup;
import junit.framework.TestSuite;

public class FUMLTestSetup extends TestSetup
{
   public static FUMLTestSetup newTestSetup(Class<?> testClass)
   {
      return new FUMLTestSetup(testClass);
   }
   
   protected FUMLTestSetup(Class<?> testClass)
   {
      super(new TestSuite(testClass));
   }   
}
