
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

public abstract class Behavior extends fUML.Syntax.Classes.Kernel.Class_ {

	public boolean isReentrant = true;
	public fUML.Syntax.Classes.Kernel.BehavioralFeature specification = null;
	public fUML.Syntax.Classes.Kernel.ParameterList ownedParameter = new fUML.Syntax.Classes.Kernel.ParameterList();
	public fUML.Syntax.CommonBehaviors.BasicBehaviors.BehavioredClassifier context = null;

	public void addOwnedParameter(
			fUML.Syntax.Classes.Kernel.Parameter ownedParameter) {
		super.addOwnedMember(ownedParameter);
		this.ownedParameter.addValue(ownedParameter);
	} // addOwnedParameter

	public void _setContext(
			fUML.Syntax.CommonBehaviors.BasicBehaviors.BehavioredClassifier context) {
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
			fUML.Syntax.Classes.Kernel.BehavioralFeature specification) {
		this.specification = specification;
	} // _setSpecification

} // Behavior
