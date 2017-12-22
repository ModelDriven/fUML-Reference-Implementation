
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

public class ReclassifyObjectAction extends
		fuml.syntax.actions.Action {

	public boolean isReplaceAll = false;
	public fuml.syntax.classification.ClassifierList oldClassifier = new fuml.syntax.classification.ClassifierList();
	public fuml.syntax.actions.InputPin object = null;
	public fuml.syntax.classification.ClassifierList newClassifier = new fuml.syntax.classification.ClassifierList();

	public void setIsReplaceAll(boolean isReplaceAll) {
		this.isReplaceAll = isReplaceAll;
	} // setIsReplaceAll

	public void addOldClassifier(
			fuml.syntax.classification.Classifier oldClassifier) {
		this.oldClassifier.addValue(oldClassifier);
	} // addOldClassifier

	public void addNewClassifier(
			fuml.syntax.classification.Classifier newClassifier) {
		this.newClassifier.addValue(newClassifier);
	} // addNewClassifier

	public void setObject(fuml.syntax.actions.InputPin object) {
		super.addInput(object);
		this.object = object;

	} // setObject

} // ReclassifyObjectAction
