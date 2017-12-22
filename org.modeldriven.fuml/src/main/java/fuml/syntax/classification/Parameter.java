
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

public class Parameter extends fuml.syntax.commonstructure.TypedElement {

	public fuml.syntax.commonstructure.MultiplicityElement multiplicityElement = new fuml.syntax.commonstructure.MultiplicityElement();
	public fuml.syntax.classification.ParameterDirectionKind direction = fuml.syntax.classification.ParameterDirectionKind.in;
	public fuml.syntax.classification.Operation operation = null;

	public void setDirection(
			fuml.syntax.classification.ParameterDirectionKind direction) {
		this.direction = direction;
	} // setDirection

	public void setIsOrdered(boolean isOrdered) {
		this.multiplicityElement.setIsOrdered(isOrdered);
	} // setIsOrdered

	public void setIsUnique(boolean isUnique) {
		this.multiplicityElement.setIsUnique(isUnique);
	} // setIsUnique

	public void setUpperValue(
			fuml.syntax.values.ValueSpecification upperValue) {
		this.multiplicityElement.setUpperValue(upperValue);
	} // setUpperValue

	public void setLowerValue(
			fuml.syntax.values.ValueSpecification lowerValue) {
		this.multiplicityElement.setLowerValue(lowerValue);
	} // setLowerValue

	public void setUpper(int upper) {
		// Note: This is a convenience operation that may be used _instead_ of
		// setUpperValue, not in addition to it.

		this.multiplicityElement.setUpper(upper);
	} // setUpper

	public void setLower(int lower) {
		// Note: This is a convenience operation that may be used _instead_ of
		// setLowerValue, not in addition to it.

		this.multiplicityElement.setLower(lower);

	} // setLower

	public void _setOperation(fuml.syntax.classification.Operation operation) {
		this.operation = operation;
	} // _setOperation

} // Parameter
