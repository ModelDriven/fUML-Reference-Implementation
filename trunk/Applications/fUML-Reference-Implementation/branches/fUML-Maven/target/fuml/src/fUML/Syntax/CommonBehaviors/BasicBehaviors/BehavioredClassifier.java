
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

package fUML.Syntax.CommonBehaviors.BasicBehaviors;

import fUML.Debug;
import UMLPrimitiveTypes.*;

public abstract class BehavioredClassifier extends
		fUML.Syntax.Classes.Kernel.Classifier {

	public fUML.Syntax.CommonBehaviors.BasicBehaviors.BehaviorList ownedBehavior = new fUML.Syntax.CommonBehaviors.BasicBehaviors.BehaviorList();
	public fUML.Syntax.CommonBehaviors.BasicBehaviors.Behavior classifierBehavior = null;

	public void addOwnedBehavior(
			fUML.Syntax.CommonBehaviors.BasicBehaviors.Behavior ownedBehavior) {
		super.addOwnedMember(ownedBehavior);

		this.ownedBehavior.addValue(ownedBehavior);

		if (!(this instanceof Behavior) || ((Behavior) this).context == null) {
			ownedBehavior._setContext(this);
		} else {
			ownedBehavior._setContext(((Behavior) this).context);
		}

	} // addOwnedBehavior

	public void setClassifierBehavior(
			fUML.Syntax.CommonBehaviors.BasicBehaviors.Behavior classifierBehavior) {
		// Note: The classifier behavior must also be added as an owned behavior
		// using addOwnedBehavior.

		this.classifierBehavior = classifierBehavior;
	} // setClassifierBehavior

} // BehavioredClassifier
