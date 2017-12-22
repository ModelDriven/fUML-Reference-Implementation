
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

package fuml.semantics.activities;

public class InitialNodeActivation extends
		fuml.semantics.activities.ControlNodeActivation {

	public void fire(
			fuml.semantics.activities.TokenList incomingTokens) {
		// Create a single token and send offers for it.

		TokenList tokens = new TokenList();
		tokens.addValue(new ControlToken());
		this.addTokens(tokens);

		this.sendOffers(tokens);
	} // fire

} // InitialNodeActivation
