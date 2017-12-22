
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

package fuml.syntax.actions;

public abstract class StructuralFeatureAction extends
		fuml.syntax.actions.Action {

	public fuml.syntax.classification.StructuralFeature structuralFeature = null;
	public fuml.syntax.actions.InputPin object = null;

	public void setStructuralFeature(
			fuml.syntax.classification.StructuralFeature structuralFeature) {
		this.structuralFeature = structuralFeature;
	} // setStructuralFeature

	public void setObject(fuml.syntax.actions.InputPin object) {
		super.addInput(object);
		this.object = object;
	} // setObject

} // StructuralFeatureAction
