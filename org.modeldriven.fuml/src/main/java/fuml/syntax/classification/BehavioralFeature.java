
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

package fuml.syntax.classification;

public abstract class BehavioralFeature extends
		fuml.syntax.classification.Feature {

	public fuml.syntax.classification.ParameterList ownedParameter = new fuml.syntax.classification.ParameterList();
	public boolean isAbstract = false;
	public fuml.syntax.commonbehavior.BehaviorList method = new fuml.syntax.commonbehavior.BehaviorList();
	public fuml.syntax.commonbehavior.CallConcurrencyKind concurrency = fuml.syntax.commonbehavior.CallConcurrencyKind.sequential;

	public void setIsAbstract(boolean isAbstract) {
		this.isAbstract = isAbstract;
	} // setIsAbstract

	public void addOwnedParameter(
			fuml.syntax.classification.Parameter ownedParameter) {
		// this.addOwnedMember(ownedParameter); [Note: BehavioralFeature is not
		// a Namespace in fUML, to avoid multiple inheritance.]

		this.ownedParameter.addValue(ownedParameter);
	} // addOwnedParameter

	public void addMethod(
			fuml.syntax.commonbehavior.Behavior method) {
		method._setSpecification(this);
		this.method.addValue(method);
	} // addMethod

} // BehavioralFeature
