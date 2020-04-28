
/*
 * Initial version copyright 2008 Lockheed Martin Corporation, except  
 * as stated in the file entitled Licensing-Information. 
 * 
 * Modifications:
 * Copyright 2009-2012 Data Access Technologies, Inc.
 * Copyright 2019-2020 Model Driven Solutions, Inc.
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fuml.semantics.activities;

import fuml.Debug;
import fuml.semantics.commonbehavior.ParameterValue;
import fuml.semantics.commonbehavior.StreamingParameterValue;
import fuml.semantics.values.Value;
import fuml.semantics.values.ValueList;
import fuml.syntax.activities.ActivityParameterNode;
import fuml.syntax.classification.Parameter;

public class ActivityParameterNodeActivation extends fuml.semantics.activities.ObjectNodeActivation {
	
	public void run() {
		// If this activation is for an input activity parameter node for a
		// stream parameter, then register a listener for this activation
		// with the streaming parameter value corresponding to the parameter.
		
		super.run();
		
		Parameter parameter = ((ActivityParameterNode) (this.node)).parameter;
		ParameterValue parameterValue = this.getActivityExecution().
				getParameterValue(parameter);
		if (this.node.incoming.size() == 0 &
				parameterValue instanceof StreamingParameterValue) {
			ActivityParameterNodeStreamingParameterListener listener = 
					new ActivityParameterNodeStreamingParameterListener();
			listener.nodeActivation = this;
			((StreamingParameterValue)parameterValue).register(listener);
			Debug.println("[run] Registering for streaming parameter " + parameter.name + ".");
		}
	}

	public void fire(fuml.semantics.activities.TokenList incomingTokens) {
		// If there are no incoming edges, this is an activation of an input
		// activity parameter node.
		// Get the values from the input parameter indicated by the activity
		// parameter node and offer those values as object tokens.
		// If there are one or more incoming edges, this is an activation of an
		// output activity parameter node.
		// If the output parameter is not streaming, take the tokens offered on 
		// incoming edges and add them to the set of tokens being offered.
		// If the output parameter is streaming, post the values from the
		// the tokens offered on incoming edges to the parameter value.
		// (Note that an output activity parameter node may fire multiple times,
		// accumulating tokens offered to it.)

		Parameter parameter = ((ActivityParameterNode) (this.node)).parameter;
		ParameterValue parameterValue = this.getActivityExecution()
				.getParameterValue(parameter);
		// Debug.println("[fire] parameter = " + parameter.name);

		if (this.node.incoming.size() == 0) {
			Debug.println("[fire] Input activity parameter node " + this.node.name + "...");
			if (parameterValue != null) {
				Debug.println("[fire] Parameter has " + parameterValue.values.size() + " value(s).");
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

		else {
			Debug.println("[fire] Output activity parameter node " + this.node.name + "...");
			
			this.addTokens(incomingTokens);				
			
			if (parameterValue instanceof StreamingParameterValue) {
				ValueList values = new ValueList();
				for (int i = 0; i < incomingTokens.size(); i++) {
					Token token = incomingTokens.getValue(i);
					Value value = token.getValue();					
					if (value != null) {
						values.addValue(value);
						Debug.println("[event] Post activity=" + this.getActivityExecution().getBehavior().name
								+ " parameter=" + parameterValue.parameter.name
								+ " value=" + value);
					}
				}
				((StreamingParameterValue)parameterValue).post(values);
				super.clearTokens();
			}
		}

	} // fire

	public void clearTokens() {
		// Clear all held tokens only if this is an input parameter node.

		if (this.node.incoming.size() == 0) {
			super.clearTokens();
		}
	} // clearTokens

} // ActivityParameterNodeActivation
