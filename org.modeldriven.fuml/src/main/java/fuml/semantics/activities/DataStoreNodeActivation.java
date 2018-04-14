/*
 * Copyright 2017 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fuml.semantics.activities;

import fuml.semantics.values.Value;

public class DataStoreNodeActivation extends CentralBufferNodeActivation {

	@Override
	public void addToken(Token token) {
		// Add the given token to the data store only if it is unique,
		// that is, if its value is not the same as the value of
		// another token already held in the data store.
		
		Value value = token.getValue();
		
		boolean isUnique = true;
		if (value != null) {
			TokenList heldTokens = this.getTokens();
			int i = 1;
			while (isUnique & i <= heldTokens.size()) {
				isUnique = !heldTokens.getValue(i-1).getValue().equals(value);
				i = i + 1;
			}
		}
		
		if (isUnique) {
			super.addToken(token);
		}
		
	}
	
	@Override
	public int removeToken(Token token) {
		// Remove the given token from the data store, but then immediately 
		// add a copy back into the data store and offer it (unless the
		// node activation has already been terminated).
		
		int i = super.removeToken(token);
		
		if (this.isRunning()) {
			super.addToken(token.copy());
			this.sendUnofferedTokens();
		}
		
		return i;
	}

}
