
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

public abstract class Feature extends
		fUML.Syntax.Classes.Kernel.RedefinableElement {

	public boolean isStatic = false;
	public fUML.Syntax.Classes.Kernel.ClassifierList featuringClassifier = new fUML.Syntax.Classes.Kernel.ClassifierList();

	public void _addFeaturingClassifier(
			fUML.Syntax.Classes.Kernel.Classifier featuringClassifier) {
		this.featuringClassifier.add(featuringClassifier);
	} // _addFeaturingClassifier

} // Feature
