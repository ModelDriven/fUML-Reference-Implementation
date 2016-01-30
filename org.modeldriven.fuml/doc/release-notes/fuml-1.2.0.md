### fUML Reference Implementation
### v1.2.0 Release Notes

This is a base release of the fUML Reference Implementation, conforming to v1.2.1 of the [fUML Specification](http://www.omg.org/spec/FUML/1.2.1).

### fUML Execution Engine

This is a new base release of the fUML Execution Engine. It includes all implementation updates from [v1.1.4](./fuml-1.1.4.md) and previous
releases tracking the issues resolutions adopted by the fUML 1.2 Revision Task Force (RTF). The complete set of resolved issues implemented 
since the v1.1.0 base release are listed below. The full list of remaining open fUML issues is available at http://solitaire.omg.org/issues/task-force/FUML13.

The following issue was resolved as an "urgent issue" by the fUML 1.3 RTF, resulting in the formal release of fUML 1.2.1 rather
than fUML 1.2:

[FUML 13-12](http://issues.omg.org/issues/task-force/FUML13#issue-38461) Bad event accepter removed from the waitingEventAccepter list

The following issues were resolved by the fUML 1.2 RTF:

[FUML 12-7](http://solitaire.omg.org/issues/task-force/FUML12#issue-16432) (18279) ReclassifyObjectAction does not preserve structural feature values

[FUML 12-8](http://solitaire.omg.org/issues/task-force/FUML12#issue-16433) (18280) LoopNodeActivation does not currently handle the firing of a contained activity final node  

[FUML 12-9](http://solitaire.omg.org/issues/task-force/FUML12#issue-16434) (18282) The bodyOutputLists for a loop node need to be cleared when the node fires again  

[FUML 12-10](http://solitaire.omg.org/issues/task-force/FUML12#issue-16435) (18321) Certain Boolean flags are not properly initialized in some cases 

[FUML 12-11](http://solitaire.omg.org/issues/task-force/FUML12#issue-16436) (18362) Problem with ActivityExecution::terminate  

[FUML 12-12](http://solitaire.omg.org/issues/task-force/FUML12#issue-16437) (18364) RealValue::toString puts too many digits in the mantissa  

[FUML 12-13](http://solitaire.omg.org/issues/task-force/FUML12#issue-16438) (18365) Objects with cyclic references cannot be converted to string representations

[FUML 12-14](http://solitaire.omg.org/issues/task-force/FUML12#issue-16439) (18508) FoundationalModelLibrary, UnlimitedNaturalFunctions: inconsistencies between the spec and the normative xmi

[FUML 12-15](http://solitaire.omg.org/issues/task-force/FUML12#issue-16440) (18510) ListFunctions should have a ListConcat behavior

[FUML 12-16](http://solitaire.omg.org/issues/task-force/FUML12#issue-16441) (18511) The ReadLine::result parameter should have direction "return"

[FUML 12-17](http://solitaire.omg.org/issues/task-force/FUML12#issue-16442) (18512) FoundationalModelLibrary::Common::Notification should be public

[FUML 12-18](http://solitaire.omg.org/issues/task-force/FUML12#issue-16443) (18513) The Listener reception of Notification should have a name

[FUML 12-19](http://solitaire.omg.org/issues/task-force/FUML12#issue-16444) (18514) The types of the ReadLine::errorStatus and WriteLine::errorStatus parameters should be Status

[FUML 12-20](http://solitaire.omg.org/issues/task-force/FUML12#issue-16445) (18529) Feature values need to be created for private structural features of parent classifiers

[FUML 12-21](http://solitaire.omg.org/issues/task-force/FUML12#issue-16446) (18693) ReclassifyObjectAction handles removal of structural features incorrect

[FUML 12-22](http://solitaire.omg.org/issues/task-force/FUML12#issue-16447) (18714) RemoveStructuralFeatureValueActionActivation: Determination of position(s) of value to removed results in inf. loop

[FUML 12-23](http://solitaire.omg.org/issues/task-force/FUML12#issue-16448) (18721) RemoveStructuralFeatureValueAction: Removal of links with specified remove at value works incorrectly

[FUML 12-24](http://solitaire.omg.org/issues/task-force/FUML12#issue-16449) (18722) RemoveStructuralFeatureValueActionActivation: Removing links with specified remove at value works incorrectly

[FUML 12-31](http://solitaire.omg.org/issues/task-force/FUML12#issue-16456) (18800) Correction of method UnlimitedNaturalValue.equals

[FUML 12-33](http://solitaire.omg.org/issues/task-force/FUML12#issue-16458) (19008) Extensional values should have an unique identifier

[FUML 12-34](http://solitaire.omg.org/issues/task-force/FUML12#issue-16459) (19528) AcceptEventActionActivation::match should match instances of descendants of a trigger's signal

[FUML 12-35](http://solitaire.omg.org/issues/task-force/FUML12#issue-16460) (19679) Initial execution of an activity is not run-to-completion

[FUML 12-36](http://solitaire.omg.org/issues/task-force/FUML12#issue-17283) (19130) Problem with CallActionActivation: Possible infinite loop in removeCallExecution()

[FUML 12-57](http://solitaire.omg.org/issues/task-force/FUML12#issue-37808) List parameters of ListFunctions should be non-unique

### XMI Loader/Infrastructure

This base release includes all updates from [v1.1.4](./fuml-1.1.4.md) and previous maintenance releases.
There are no further changes to the XMI Loader in this release.

#### Bugs

None

#### Improvements

None  
