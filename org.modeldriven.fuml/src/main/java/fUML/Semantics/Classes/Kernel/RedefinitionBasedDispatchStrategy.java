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

import fUML.Syntax.Classes.Kernel.*;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.*;

public class RedefinitionBasedDispatchStrategy extends
		fUML.Semantics.Classes.Kernel.DispatchStrategy {

	public fUML.Syntax.CommonBehaviors.BasicBehaviors.Behavior getMethod(
			fUML.Semantics.Classes.Kernel.Object_ object,
			fUML.Syntax.Classes.Kernel.Operation operation) {
// Find the member operation of a type of the given object that
// is the same as or a redefinition of the given operation. Then
// return the method of that operation, if it has one, otherwise
// return a CallEventBehavior as the effective method for the
// matching operation.
// [If there is more than one type with a matching operation, then
// the first one is arbitrarily chosen.]

Behavior method = null;
int i = 1;
while (method == null & i <= object.types.size()) {
	Class_ type = object.types.getValue(i - 1);
	NamedElementList members = type.member;
	int j = 1;
	while (method == null & j <= members.size()) {
		NamedElement member = members.getValue(j - 1);
		if (member instanceof Operation) {
			Operation memberOperation = (Operation) member;
			if (this.operationsMatch(memberOperation, operation)) {
				if (memberOperation.method.size() == 0) {
					method = super.getMethod(object, memberOperation);
				} else {
					method = memberOperation.method.getValue(0);
				}
			}
		}
		j = j + 1;
	}
	i = i + 1;
}

return method;
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
