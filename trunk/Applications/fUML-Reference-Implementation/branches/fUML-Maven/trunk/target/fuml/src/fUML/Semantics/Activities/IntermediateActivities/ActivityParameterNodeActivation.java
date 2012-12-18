
/*
 * Initial version copyright 2008 Lockheed Martin Corporation, except  
 * as stated in the file entitled Licensing-Information. 
 * 
 * All modifications copyright 2009-2012 Data Access Technologies, Inc.
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fUML.Semantics.Activities.IntermediateActivities;

import fUML.Debug;
import UMLPrimitiveTypes.*;

import fUML.Syntax.*;
import fUML.Syntax.Classes.Kernel.*;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.*;
import fUML.Syntax.CommonBehaviors.Communications.*;
import fUML.Syntax.Activities.IntermediateActivities.*;
import fUML.Syntax.Actions.BasicActions.*;

import fUML.Semantics.*;
import fUML.Semantics.Classes.Kernel.*;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.*;
import fUML.Semantics.Actions.BasicActions.*;
import fUML.Semantics.Loci.*;

public class ActivityParameterNodeActivation extends
		fUML.Semantics.Activities.IntermediateActivities.ObjectNodeActivation {

	public void fire(
			fUML.Semantics.Activities.IntermediateActivities.TokenList incomingTokens) {
		// If there are no incoming edges, this is an activation of an input
		// activity parameter node.
		// Get the values from the input parameter indicated by the activity
		// parameter node and offer those values as object tokens.

		if (this.node.incoming.size() == 0) {
			Debug.println("[fire] Input activity parameter node "
					+ this.node.name + "...");
			Parameter parameter = ((ActivityParameterNode) (this.node)).parameter;
			ParameterValue parameterValue = this.getActivityExecution()
					.getParameterValue(parameter);
			// Debug.println("[fire] parameter = " + parameter.name);
			if (parameterValue != null) {
				Debug.println("[fire] Parameter has "
						+ parameterValue.values.size() + " value(s).");
				TokenList tokens = new TokenList();
				ValueList values = parameterValue.values;
				for (int i = 0; i < values.size(); i++) {
					Value value = values.getValue(i);
					ObjectToken token = new ObjectToken();
					token.value = value;
					this.addToken(token);
				}
				this.sendUnofferedTokens();
			}
		}

		// If there are one or more incoming edges, this is an activation of an
		// output activity parameter node.
		// Take the tokens offered on incoming edges and add them to the set of
		// tokens being offered.
		// [Note that an output activity parameter node may fire multiple times,
		// accumulating tokens offered to it.]

		else {
			Debug.println("[fire] Output activity parameter node "
					+ this.node.name + "...");
			this.addTokens(incomingTokens);
		}

	} // fire

	public void clearTokens() {
		// Clear all held tokens only if this is an input parameter node.

		if (this.node.incoming.size() == 0) {
			super.clearTokens();
		}
	} // clearTokens

} // ActivityParameterNodeActivation
