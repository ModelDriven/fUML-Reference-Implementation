/*
 * Copyright 2008 Lockheed Martin Corporation, except as stated in the file 
 * entitled Licensing-Information. All modifications copyright 2009 Data Access Technologies, Inc. Licensed under the Academic Free License 
 * version 3.0 (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 *
 * Contributors:
 *   MDS - initial API and implementation
 *
 */
package org.modeldriven.fuml.common.lang;

import java.util.HashMap;
import java.util.Map;

public class JavaKeyWords {

    private static Map<String, String> words = new HashMap<String, String>();
    private static JavaKeyWords instance;
        
    private JavaKeyWords()
    {
        words.put("abstract", "abstract");
        words.put("continue", "continue");
        words.put("switch", "switch");
        words.put("new", "new");
        words.put("assert", "assert");
        words.put("default", "default");
        words.put("goto", "goto");
        words.put("package", "package");
        words.put("synchronized", "synchronized");
        words.put("boolean", "boolean");
        words.put("do", "do");
        words.put("if", "if");
        words.put("private", "private");
        words.put("this", "this");
        words.put("break", "break");
        words.put("double", "double");
        words.put("implements", "implements");
        words.put("protected", "protected");
        words.put("throw", "throw");
        words.put("byte", "byte");
        words.put("else", "else");
        words.put("import", "import");
        words.put("public", "public");
        words.put("throws", "throws");
        words.put("case", "case");
        words.put("enum", "enum");
        words.put("instanceof", "instanceof");
        words.put("return", "return");
        words.put("transient", "transient");
        words.put("catch", "catch");
        words.put("extends", "extends");
        words.put("int", "int");
        words.put("short", "short");
        words.put("try", "try");
        words.put("char", "char");
        words.put("final", "final");
        words.put("interface", "interface");
        words.put("static", "static");
        words.put("void", "void");
        words.put("class", "class");
        words.put("finally", "finally");
        words.put("long", "long");
        words.put("strictfp", "strictfp");
        words.put("volatile", "volatile");
        words.put("const", "const");
        words.put("float", "float");
        words.put("native", "native");
        words.put("super", "super");
        words.put("while", "while");
    }

    public static JavaKeyWords getInstance()
    {
        if (instance == null)
            initializeInstance();
        return instance;
    }
    
    private static synchronized void initializeInstance()
    {
        if (instance == null)
            instance = new JavaKeyWords();
    } 
    
    public boolean isKeyWord(String word)
    {
        return words.get(word) != null;       
    }
    
}
