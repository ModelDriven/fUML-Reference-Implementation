### fUML Reference Implementation
### v1.3.0 Release Notes

This is a base release of the fUML Reference Implementation, conforming to v1.3 of the [fUML Specification](http://www.omg.org/spec/FUML/1.3).

### fUML Execution Engine

This is a new base release of the fUML Execution Engine. It includes all implementation updates from [v1.2.3](./fuml-1.2.3.md) and previous
releases tracking the issue resolutions adopted by the fUML 1.3 Revision Task Force (RTF). The complete set of resolved issues implemented 
since the previous [v1.2.0](./fuml-1.2.0.md) base release are listed below. The list of remaining open fUML issues is available at 
http://solitaire.omg.org/issues/task-force/FUML14.

The following issues were resolved by the fUML 1.3 RTF:

[FUML 13-1](http://issues.omg.org/issues/task-force/FUML13#issue-38293) SendSignalAction completion semantics

[FUML 13-4](http://issues.omg.org/issues/task-force/FUML13#issue-38296) The fUML subset should include central buffer nodes and data stores

[FUML 13-16](http://issues.omg.org/issues/task-force/FUML13#issue-38600) Introduce CallEvent, AcceptCallAction and ReplyAction to keep consistency with PSSM

[FUML 13-17](http://issues.omg.org/issues/task-force/FUML13#issue-38717) Stand-alone owned behavior of BehavioredClassifier shall be possible

[FUML 13-20](http://issues.omg.org/issues/task-force/FUML13#issue-39953) 
Error in RealValue::toString

[FUML 13-21](http://issues.omg.org/issues/task-force/FUML13#issue-39984) BasicInputOutput operations for reading and writing reals are missing

[FUML 13-23](http://issues.omg.org/issues/task-force/FUML13#issue-40625) EventOccurrence should be used instead of SignalInstance in execution model operation parameters

[FUML 13-25](http://issues.omg.org/issues/task-force/FUML13#issue-40627) Refactor EventOccurrence to provide useful operations

[FUML 13-26](http://issues.omg.org/issues/task-force/FUML13#issue-40628) Reference::equal should use Object::equals

[FUML 13-27](http://issues.omg.org/issues/task-force/FUML13#issue-40665)
Input parameter activity nodes should fire first

[FUML 13-29](http://issues.omg.org/issues/task-force/FUML13#issue-40667)
"Debug" statements should be removed

[FUML 13-49](http://issues.omg.org/issues/task-force/FUML13#issue-40992) Correction to the resolution to issue [FUML 13-25](http://issues.omg.org/issues/task-force/FUML13#issue-40627)

[FUML 13-60](http://issues.omg.org/issues/task-force/FUML13#issue-41143) Simplification to the resolution to issue [FUML 13-1](http://issues.omg.org/issues/task-force/FUML13#issue-38293)

This release also includes the resolution of the following OMG issue, which was part of fUML 1.2, but was not properly implemented in [v1.2.0](./fuml-1.2.0.md) of the
Execution Engine.

[FUML 12-11](http://solitaire.omg.org/issues/task-force/FUML12#issue-16436) (18362) Problem with ActivityExecution::terminate

### XMI Loader/Infrastructure

This base release includes all updates from maintenance releases [v1.2.0a](./fuml-1.2.0a.md) and [v1.2.0b](./fuml-1.2.0b.md), resolving the following issues, 
as tracked in our issue management system on GitHub (see https://github.com/ModelDriven/fUML-Reference-Implementation/issues).

#### Bugs

[Issue #28] - Problem starting the behaviors of a multiply classified active object

#### Improvements

[Issue #26] - Problems with SAXParser when building with Java 8 JDK

[Issue #29] - fUML.Library and fUML.Test packages are only used for builtin tests
