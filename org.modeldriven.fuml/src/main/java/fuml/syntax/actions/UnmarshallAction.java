/*
 * Copyright 2019 Model Driven Solutions, Inc. 
 * 
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fuml.syntax.actions;

public class UnmarshallAction extends Action {
	
	public fuml.syntax.actions.InputPin object = null;
	public fuml.syntax.actions.OutputPinList result = new fuml.syntax.actions.OutputPinList();
	public fuml.syntax.classification.Classifier unmarshallType = null;
	
	public void setObject(fuml.syntax.actions.InputPin object) {
		super.addInput(object);
		this.object = object;
	}
	
	public void addResult(fuml.syntax.actions.OutputPin result) {
		super.addOutput(result);
		this.result.addValue(result);
	}

	public void setUnmarshallType(fuml.syntax.classification.Classifier unmarshallType) {
		this.unmarshallType = unmarshallType;
	}

}
