/*
 * Initial version copyright 2008 Lockheed Martin Corporation, except  
 * as stated in the file entitled Licensing-Information. 
 * 
 * All modifications copyright 2009-2017 Data Access Technologies, Inc.
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fUML.Semantics.Classes.Kernel;

import fUML.Syntax.Classes.Kernel.Class_;
import fUML.Syntax.Classes.Kernel.NamedElement;
import fUML.Syntax.Classes.Kernel.NamedElementList;
import fUML.Syntax.Classes.Kernel.Operation;

public class RedefinitionBasedDispatchStrategy extends
		fUML.Semantics.Classes.Kernel.DispatchStrategy {

	public fUML.Syntax.Classes.Kernel.Operation getActualOperation(
			fUML.Semantics.Classes.Kernel.Object_ object,
			fUML.Syntax.Classes.Kernel.Operation operation) {
		// Get the redefinition, if any, of the given operation for the given
		// object.
		// [If the object has more than one type with the given operation or
		// a redefinition for it, then the first one is arbitrarily chosen.]

		Operation actualOperation = null;
		int i = 1;
		while (actualOperation == null & i <= object.types.size()) {
			Class_ type = object.types.getValue(i - 1);
			NamedElementList members = type.member;
			int j = 1;
			while (actualOperation == null & j <= members.size()) {
				NamedElement member = members.getValue(j - 1);
				if (member instanceof Operation) {
					Operation memberOperation = (Operation) member;
					if (this.operationsMatch(memberOperation, operation)) {
						actualOperation = memberOperation;
					}
				}
				j = j + 1;
			}
			i = i + 1;
		}

		return actualOperation;
	} // getMethod

	public boolean operationsMatch(
			fUML.Syntax.Classes.Kernel.Operation ownedOperation,
			fUML.Syntax.Classes.Kernel.Operation baseOperation) {
		// Check if the owned operation is equal to or a redefinition (directly
		// or indirectly) of the base operation.

		boolean matches = false;
		if (ownedOperation == baseOperation) {
			matches = true;
		} else {
			int i = 1;
			while (!matches & i <= ownedOperation.redefinedOperation.size()) {
				matches = this.operationsMatch(
						ownedOperation.redefinedOperation.getValue(i - 1),
						baseOperation);
				i = i + 1;
			}
		}

		return matches;
	} // operationsMatch

} // RedefinitionBasedDispatchStrategy
