
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

package fUML.Syntax.Classes.Kernel;

import fUML.Debug;
import UMLPrimitiveTypes.*;

public abstract class BehavioralFeature extends
		fUML.Syntax.Classes.Kernel.Feature {

	public fUML.Syntax.Classes.Kernel.ParameterList ownedParameter = new fUML.Syntax.Classes.Kernel.ParameterList();
	public boolean isAbstract = false;
	public fUML.Syntax.CommonBehaviors.BasicBehaviors.BehaviorList method = new fUML.Syntax.CommonBehaviors.BasicBehaviors.BehaviorList();
	public fUML.Syntax.CommonBehaviors.BasicBehaviors.CallConcurrencyKind concurrency = fUML.Syntax.CommonBehaviors.BasicBehaviors.CallConcurrencyKind.sequential;

	public void setIsAbstract(boolean isAbstract) {
		this.isAbstract = isAbstract;
	} // setIsAbstract

	public void addOwnedParameter(
			fUML.Syntax.Classes.Kernel.Parameter ownedParameter) {
		// this.addOwnedMember(ownedParameter); [Note: BehavioralFeature is not
		// a Namespace in fUML, to avoid multiple inheritance.]

		this.ownedParameter.addValue(ownedParameter);
	} // addOwnedParameter

	public void addMethod(
			fUML.Syntax.CommonBehaviors.BasicBehaviors.Behavior method) {
		method._setSpecification(this);
		this.method.addValue(method);
	} // addMethod

} // BehavioralFeature
