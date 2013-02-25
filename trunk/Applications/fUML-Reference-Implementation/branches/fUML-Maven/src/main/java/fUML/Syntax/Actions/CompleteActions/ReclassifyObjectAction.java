
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

package fUML.Syntax.Actions.CompleteActions;

import fUML.Debug;
import UMLPrimitiveTypes.*;

public class ReclassifyObjectAction extends
		fUML.Syntax.Actions.BasicActions.Action {

	public boolean isReplaceAll = false;
	public fUML.Syntax.Classes.Kernel.ClassifierList oldClassifier = new fUML.Syntax.Classes.Kernel.ClassifierList();
	public fUML.Syntax.Actions.BasicActions.InputPin object = null;
	public fUML.Syntax.Classes.Kernel.ClassifierList newClassifier = new fUML.Syntax.Classes.Kernel.ClassifierList();

	public void setIsReplaceAll(boolean isReplaceAll) {
		this.isReplaceAll = isReplaceAll;
	} // setIsReplaceAll

	public void addOldClassifier(
			fUML.Syntax.Classes.Kernel.Classifier oldClassifier) {
		this.oldClassifier.addValue(oldClassifier);
	} // addOldClassifier

	public void addNewClassifier(
			fUML.Syntax.Classes.Kernel.Classifier newClassifier) {
		this.newClassifier.addValue(newClassifier);
	} // addNewClassifier

	public void setObject(fUML.Syntax.Actions.BasicActions.InputPin object) {
		super.addInput(object);
		this.object = object;

	} // setObject

} // ReclassifyObjectAction
