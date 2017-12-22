
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

package fuml.syntax.commonbehavior;

public abstract class BehavioredClassifier extends
		fuml.syntax.classification.Classifier {

	public fuml.syntax.commonbehavior.BehaviorList ownedBehavior = new fuml.syntax.commonbehavior.BehaviorList();
	public fuml.syntax.commonbehavior.Behavior classifierBehavior = null;

	public void addOwnedBehavior(
			fuml.syntax.commonbehavior.Behavior ownedBehavior) {
		super.addOwnedMember(ownedBehavior);

		this.ownedBehavior.addValue(ownedBehavior);

		if (!(this instanceof Behavior) || ((Behavior) this).context == null) {
			ownedBehavior._setContext(this);
		} else {
			ownedBehavior._setContext(((Behavior) this).context);
		}

	} // addOwnedBehavior

	public void setClassifierBehavior(
			fuml.syntax.commonbehavior.Behavior classifierBehavior) {
		// Note: The classifier behavior must also be added as an owned behavior
		// using addOwnedBehavior.

		this.classifierBehavior = classifierBehavior;
	} // setClassifierBehavior

} // BehavioredClassifier
