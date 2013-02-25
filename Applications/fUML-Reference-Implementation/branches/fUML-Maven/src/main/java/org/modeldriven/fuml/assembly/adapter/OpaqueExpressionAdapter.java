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
package org.modeldriven.fuml.assembly.adapter;

import javax.xml.namespace.QName;
import javax.xml.stream.events.Attribute;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.assembly.AssemblyAdapter;
import org.modeldriven.fuml.repository.Classifier;
import org.modeldriven.fuml.xmi.XmiNode;
import org.modeldriven.fuml.xmi.stream.StreamNode;

import UMLPrimitiveTypes.UnlimitedNatural;
import fUML.Syntax.Classes.Kernel.Element;
import fUML.Syntax.Classes.Kernel.LiteralBoolean;
import fUML.Syntax.Classes.Kernel.LiteralInteger;
import fUML.Syntax.Classes.Kernel.LiteralString;
import fUML.Syntax.Classes.Kernel.LiteralUnlimitedNatural;

/**
 * OpaqueExpression is not supported in fUML and this class adapts 
 * away an OpaqueExpression into an fUML Element depending on the expression content.
 * While the below logic is questionable, the class is intended
 * to support conversion of these often found. For instance
 * RSA and Eclipse and other tools generate them as the ObjectFlow guard attribute (below).
 * <p>
 * <edge xmi:type="uml:ObjectFlow" xmi:id="_F4BJME_lEduAoLr6GBiiFQ" name="guard_0" source="_8bSJAE_kEduAoLr6GBiiFQ" target="_BxxFgE_lEduAoLr6GBiiFQ">
 *    <guard xmi:type="uml:OpaqueExpression" xmi:id="_F4BJMU_lEduAoLr6GBiiFQ">
 *        <body>1</body>
 *    </guard>
 *    <weight xmi:type="uml:LiteralInteger" xmi:id="_F4BJMk_lEduAoLr6GBiiFQ" value="1"/>
 * </edge> 
 * </p>
 * The next reasonable type in fUML to accommodate the expression is a LiteralInteger and
 * the below logic in general swaps OpaqueExpression for some type
 * of ValueSpecification. 
 * 
 */
public class OpaqueExpressionAdapter implements AssemblyAdapter {
    
    private static Log log = LogFactory.getLog(OpaqueExpressionAdapter.class);

    public Element assembleElement(StreamNode opaqueExpressionNode) {
        LiteralString literal = new LiteralString();
        
        XmiNode bodyNode = opaqueExpressionNode.findChildByName("body");
        
        String bodyContent = null;
        
        if (bodyNode != null)
        {    
            bodyContent = bodyNode.getData();
        }
        else
        {
            Attribute bodyAttrib = opaqueExpressionNode.getAttribute("body");
            if (bodyAttrib != null)
                bodyContent = bodyAttrib.getValue();
        }
        if (bodyContent != null)
        {    
            bodyContent = bodyContent.trim();
            if (bodyContent.startsWith("\"") && bodyContent.endsWith("\""))
            {
                bodyContent = bodyContent.substring(1, bodyContent.length()-1); 
                literal.setValue(bodyContent);
                return literal; // must be a string, no need to continue. 
            }
        }    
        literal.setValue(bodyContent);
        
        if ("true".equalsIgnoreCase(bodyContent) || "false".equalsIgnoreCase(bodyContent))
        {
            LiteralBoolean literalBoolean = new LiteralBoolean();
            literalBoolean.setValue(Boolean.valueOf(bodyContent).booleanValue()); 
            return literalBoolean;            
        }
        else if ("*".equals(bodyContent))
        {
            LiteralUnlimitedNatural literalUnlimitedNatural = new LiteralUnlimitedNatural();
            UnlimitedNatural value = new UnlimitedNatural();
            value.naturalValue = -1;
            literalUnlimitedNatural.setValue(value); 
            return literalUnlimitedNatural;            
        }
        else
        {    
            String type = findResultPinType(opaqueExpressionNode);            
            if ("UnlimitedNatural".equals(type))
            {
                int intValue = 0;
                try {
                    intValue = Integer.valueOf(bodyContent).intValue();
                }
                catch (NumberFormatException e) {
                    log.warn("could not parse content as unlimited-natural integer '"
                            + bodyContent + "'");
                }
                LiteralUnlimitedNatural literalUnlimitedNatural = new LiteralUnlimitedNatural();
                UnlimitedNatural value = new UnlimitedNatural();
                value.naturalValue = intValue;
                literalUnlimitedNatural.setValue(value); 
                return literalUnlimitedNatural;            
            }
            try {
                if (log.isDebugEnabled())
                    log.debug("parsing expression: '" + bodyContent + "'");
                LiteralInteger literalInt = new LiteralInteger();
                literalInt.setValue(Integer.valueOf(bodyContent).intValue()); 
                return literalInt;
            }
            catch (NumberFormatException e) {
                if (log.isDebugEnabled())
                    log.debug("invalid number format: " + e.getMessage());
                literal.setValue(bodyContent);
            }
        }
        
        return literal;
    }

    private String findResultPinType(StreamNode opaqueExpressionNode)
    {
        String result = null;
        StreamNode parent = (StreamNode)opaqueExpressionNode.getParent();
        if (parent == null)
        {
            log.warn("expected parent node");
            return null;
        }
        
        StreamNode resultNode = (StreamNode)parent.findChildByName("result");
        if (resultNode != null)
        {
            StreamNode typeNode = (StreamNode)resultNode.findChildByName("type");
            if (typeNode != null)
            {
                QName name = new QName("href");
                String href = typeNode.getAttributeValue(name);
                if (href != null)
                {    
                    // FIXME; gotta be a better way! URI resolver?? SOMETHING!
                    if (href.endsWith("Integer"))
                        result = "Integer";
                    else if (href.endsWith("String"))
                        result = "String";
                    else if (href.endsWith("Boolean"))
                        result = "Boolean";
                    else if (href.endsWith("UnlimitedNatural"))
                        result = "UnlimitedNatural";
                }
            }
        }
        return result;
    }

	public Classifier getClassifier(StreamNode node) {
		throw new IllegalStateException("OpaqueExpression should have no classifier");
	}
}
