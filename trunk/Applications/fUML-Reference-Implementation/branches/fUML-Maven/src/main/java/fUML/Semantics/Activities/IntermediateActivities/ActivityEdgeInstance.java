
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

public class ActivityEdgeInstance extends org.modeldriven.fuml.FumlObject {

	public fUML.Syntax.Activities.IntermediateActivities.ActivityEdge edge = null;
	public fUML.Semantics.Activities.IntermediateActivities.ActivityNodeActivationGroup group = null;
	public fUML.Semantics.Activities.IntermediateActivities.ActivityNodeActivation source = null;
	public fUML.Semantics.Activities.IntermediateActivities.ActivityNodeActivation target = null;
	public fUML.Semantics.Activities.IntermediateActivities.OfferList offers = new fUML.Semantics.Activities.IntermediateActivities.OfferList();

	public void sendOffer(
			fUML.Semantics.Activities.IntermediateActivities.TokenList tokens) {
		// Send an offer from the source to the target.
		// Keep the offered tokens until taken by the target.
		// (Note that any one edge should only be handling either all object
		// tokens or all control tokens.)

		Offer offer = new Offer();

		for (int i = 0; i < tokens.size(); i++) {
			Token token = tokens.getValue(i);
			// Debug.println("[sendOffer] token value = " + token.getValue());
			offer.offeredTokens.addValue(token);
		}

		this.offers.addValue(offer);

		this.target.receiveOffer();

	} // sendOffer

	public int countOfferedValues() {
		// Return the number of values being offered in object tokens.

		int count = 0;
		OfferList offers = this.offers;
		for (int i = 0; i < offers.size(); i++) {
			count = count + offers.getValue(i).countOfferedValues();
		}

		return count;
	} // countOfferedValues

	public fUML.Semantics.Activities.IntermediateActivities.TokenList takeOfferedTokens() {
		// Take all the offered tokens and return them.

		TokenList tokens = new TokenList();

		while (this.offers.size() > 0) {
			TokenList offeredTokens = this.offers.getValue(0)
					.getOfferedTokens();
			for (int i = 0; i < offeredTokens.size(); i++) {
				tokens.addValue(offeredTokens.getValue(i));
			}
			this.offers.removeValue(0);
		}

		return tokens;
	} // takeOfferedTokens

	public fUML.Semantics.Activities.IntermediateActivities.TokenList takeOfferedTokens(
			int maxCount) {
		// Take all the offered tokens, up to the given maximum count of
		// non-null object tokens, and return them.

		TokenList tokens = new TokenList();
		int remainingCount = maxCount;

		while (this.offers.size() > 0 & remainingCount > 0) {
			Offer offer = this.offers.getValue(0);
			TokenList offeredTokens = offer.getOfferedTokens();
			int count = offer.countOfferedValues();
			if (count <= remainingCount) {
				for (int i = 0; i < offeredTokens.size(); i++) {
					tokens.addValue(offeredTokens.getValue(i));
				}
				remainingCount = remainingCount - count;
				this.offers.removeValue(0);
			} else {
				for (int i = 0; i < remainingCount; i++) {
					Token token = offeredTokens.getValue(i);
					if (token.getValue() != null) {
						tokens.addValue(token);
					}
				}
				offer.removeOfferedValues(remainingCount);
				remainingCount = 0;
			}
		}

		return tokens;
	} // takeOfferedTokens

	public fUML.Semantics.Activities.IntermediateActivities.TokenList getOfferedTokens() {
		// Get the offered tokens (after which the tokens will still be
		// offered).

		TokenList tokens = new TokenList();
		OfferList offers = this.offers;

		for (int i = 0; i < offers.size(); i++) {
			TokenList offeredTokens = offers.getValue(i).getOfferedTokens();
			for (int j = 0; j < offeredTokens.size(); j++) {
				tokens.addValue(offeredTokens.getValue(j));
			}
		}

		return tokens;
	} // getOfferedTokens

	public boolean hasOffer() {
		// Return true if there are any pending offers.

		boolean hasTokens = false;
		int i = 1;
		while (!hasTokens & i <= this.offers.size()) {
			hasTokens = this.offers.getValue(i - 1).hasTokens();
			i = i + 1;
		}

		return hasTokens;
	} // hasOffer

} // ActivityEdgeInstance
