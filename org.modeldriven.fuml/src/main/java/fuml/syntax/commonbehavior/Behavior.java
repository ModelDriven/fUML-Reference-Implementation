
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

public abstract class Behavior extends fuml.syntax.structuredclassifiers.Class_ {

	public boolean isReentrant = true;
	public fuml.syntax.classification.BehavioralFeature specification = null;
	public fuml.syntax.classification.ParameterList ownedParameter = new fuml.syntax.classification.ParameterList();
	public fuml.syntax.commonbehavior.BehavioredClassifier context = null;

	public void addOwnedParameter(
			fuml.syntax.classification.Parameter ownedParameter) {
		super.addOwnedMember(ownedParameter);
		this.ownedParameter.addValue(ownedParameter);
	} // addOwnedParameter

	public void _setContext(
			fuml.syntax.commonbehavior.BehavioredClassifier context) {
		// Note: This is a helper operation intended to be called by certain
		// operations outside the Behavior class in order to allow the setting
		// of the context to be overriden in subclasses.
		// It should _not_ be called otherwise in order to set the context
		// directly, rather than via these other operations.

		// Debug.println("[_setContext] behavior = " + this.name +
		// ", context = " + context.name);

		this.context = context;
	} // _setContext

	public void _setSpecification(
			fuml.syntax.classification.BehavioralFeature specification) {
		this.specification = specification;
	} // _setSpecification

} // Behavior
