
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

package fUML.Semantics.Actions.CompleteActions;

import fUML.Debug;
import UMLPrimitiveTypes.*;

import fUML.Syntax.*;
import fUML.Syntax.Classes.Kernel.*;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.*;
import fUML.Syntax.CommonBehaviors.Communications.*;
import fUML.Syntax.Activities.IntermediateActivities.*;
import fUML.Syntax.Actions.BasicActions.*;
import fUML.Syntax.Actions.IntermediateActions.*;
import fUML.Syntax.Actions.CompleteActions.*;

import fUML.Semantics.*;
import fUML.Semantics.Classes.Kernel.*;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.*;
import fUML.Semantics.Activities.IntermediateActivities.*;
import fUML.Semantics.Actions.BasicActions.*;
import fUML.Semantics.Actions.IntermediateActions.*;
import fUML.Semantics.Loci.*;

public class ReclassifyObjectActionActivation extends
		fUML.Semantics.Actions.BasicActions.ActionActivation {

	public void doAction() {
		// Get the value of the object input pin. If it is not a reference, then
		// do nothing. Otherwise, do the following.
		// Remove all types from the referent object that are in the set of old
		// classifiers but not the set of new classifiers (or just all types
		// that are not new classifiers, if isReplaceAll is true).
		// Remove the feature values from the referent object for all
		// classifiers that are removed.
		// Add all new classifiers as types of the referent object that are not
		// already types.
		// Add (empty) feature values to the referent object for the structural
		// features of all added classifiers.

		ReclassifyObjectAction action = (ReclassifyObjectAction) (this.node);
		ClassifierList newClassifiers = action.newClassifier;
		ClassifierList oldClassifiers = action.oldClassifier;

		Value input = this.takeTokens(action.object).getValue(0);

		if (input instanceof Reference) {
			Object_ object = ((Reference) input).referent;

			int i = 1;
			while (i <= object.types.size()) {
				Class_ type = object.types.getValue(i - 1);

				boolean toBeRemoved = true;
				int j = 1;
				while (toBeRemoved & j <= newClassifiers.size()) {
					toBeRemoved = (type != newClassifiers.getValue(j - 1));
					j = j + 1;
				}

				if (toBeRemoved & !action.isReplaceAll) {
					boolean notInOld = true;
					int k = 1;
					while (notInOld & k <= oldClassifiers.size()) {
						notInOld = (type != oldClassifiers.getValue(k - 1));
						k = k + 1;
					}
					toBeRemoved = !notInOld;
				}

				if (toBeRemoved) {
					object.types.removeValue(i - 1);
					object.removeFeatureValues(type);
				} else {
					i = i + 1;
				}
			}

			for (int n = 0; n < newClassifiers.size(); n++) {
				Classifier classifier = newClassifiers.getValue(n);

				boolean toBeAdded = true;
				int j = 1;
				while (toBeAdded & j <= object.types.size()) {
					toBeAdded = (classifier != object.types.getValue(j - 1));
					j = j + 1;
				}

				if (toBeAdded) {
					object.types.addValue((Class_) classifier);
					NamedElementList members = classifier.member;
					for (int k = 0; k < members.size(); k++) {
						NamedElement member = members.getValue(k);
						if (member instanceof StructuralFeature) {
							object.setFeatureValue((StructuralFeature) member,
									new ValueList(), 0);
						}
					}
				}
			}
		}
	} // doAction

} // ReclassifyObjectActionActivation
