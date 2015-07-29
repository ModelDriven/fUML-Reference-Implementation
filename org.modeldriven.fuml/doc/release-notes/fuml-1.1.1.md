### fUML Reference Implementation
### v1.1.1 Release Notes

This is a production release of the fUML Reference Implementation tracking the issue resolutions approved in Ballot #1 of the fUML 1.2 Revision Task Force.

### fUML Execution Engine

The current base release for the fUML Execution Engine is v1.1.0, conforming to v1.1 of the [fUML Specification](http://www.omg.org/spec/FUML/1.1). 
The v1.1.1 tracking release includes implementation of resolutions to the following OMG issues, which have been approved by the
fUML Revision Task Force for fUML 1.2 but have not yet been formally adopted.

The full list of open fUML issues is available at http://solitaire.omg.org/issues/task-force/FUML12.

[FUML 12-7](http://solitaire.omg.org/issues/task-force/FUML12#issue-16432) (18279) ReclassifyObjectAction does not preserve structural feature values

[FUML 12-8](http://solitaire.omg.org/issues/task-force/FUML12#issue-16433) (18280) LoopNodeActivation does not currently handle the firing of a contained activity final node  

[FUML 12-9](http://solitaire.omg.org/issues/task-force/FUML12#issue-16434) (18282) The bodyOutputLists for a loop node need to be cleared when the node fires again  

[FUML 12-10](http://solitaire.omg.org/issues/task-force/FUML12#issue-16435) (18321) Certain Boolean flags are not properly initialized in some cases 

[FUML 12-11](http://solitaire.omg.org/issues/task-force/FUML12#issue-16436) (18362) Problem with ActivityExecution::terminate  

[FUML 12-12](http://solitaire.omg.org/issues/task-force/FUML12#issue-16437) (18364) RealValue::toString puts too many digits in the mantissa  

[FUML 12-13](http://solitaire.omg.org/issues/task-force/FUML12#issue-16438) (18365) Objects with cyclic references cannot be converted to string representations

[FUML 12-20](http://solitaire.omg.org/issues/task-force/FUML12#issue-16445) (18529) Feature values need to be created for private structural features of parent classifiers

[FUML 12-21](http://solitaire.omg.org/issues/task-force/FUML12#issue-16446) (18693) ReclassifyObjectAction handles removal of structural features incorrect

[FUML 12-22](http://solitaire.omg.org/issues/task-force/FUML12#issue-16447) (18714) RemoveStructuralFeatureValueActionActivation: Determination of position(s) of value to removed results in inf. loop

[FUML 12-23](http://solitaire.omg.org/issues/task-force/FUML12#issue-16448) (18721) RemoveStructuralFeatureValueAction: Removal of links with specified remove at value works incorrectly

[FUML 12-24](http://solitaire.omg.org/issues/task-force/FUML12#issue-16449) (18722) RemoveStructuralFeatureValueActionActivation: Removing links with specified remove at value works incorrectly

[FUML 12-31](http://solitaire.omg.org/issues/task-force/FUML12#issue-16456) (18800) Correction of method UnlimitedNaturalValue.equals

[FUML 12-36](http://solitaire.omg.org/issues/task-force/FUML12#issue-17283) (19130) Problem with CallActionActivation: Possible infinite loop in removeCallExecution()

### XMI Loader/Infrastructure

This tracking release includes all resolutions from [v1.1.0c](./fuml-1.1.0c.md) and previous maintenance releases.
In addition, it includes a provisional fix for the following issue related to the XMI Loader, 
as tracked in our issue management system on GitHub (see https://github.com/ModelDriven/fUML-Reference-Implementation/issues).

#### Bugs

[Issue #25] - Problem assembling XMI internal references for collection features (provisional fix)

#### Improvements

None  
