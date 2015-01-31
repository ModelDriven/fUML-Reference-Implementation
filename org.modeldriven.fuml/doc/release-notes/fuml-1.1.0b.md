### fUML Reference Implementation
### v1.1.0b Release Notes

This is a production maintenance release of the fUML Reference Implementation.

### fUML Execution Engine

The current base release for the fUML Execution Engine is v1.1.0, conforming to v1.1 of the fUML Specification. The v1.1.0b maintenance release includes implementation of resolutions to the following OMG issues, currently under consideration by the fUML 1.2 Revision Task Force. However, the task force has not yet voted to adopt these resolutions, so they are subject to change in future releases of the Reference Implementation.

The full list of open fUML issues is available at http://solitaire.omg.org/issues/task-force/FUML12.

[FUML 12-8](http://solitaire.omg.org/issues/task-force/FUML12#issue-16433) (18280) LoopNodeActivation does not currently handle the firing of a contained activity final node
[FUML 12-9](http://solitaire.omg.org/issues/task-force/FUML12#issue-16434) (18282) The bodyOutputLists for a loop node need to be cleared when the node fires again
[FUML 12-10](http://solitaire.omg.org/issues/task-force/FUML12#issue-16435) (18321) Certain Boolean flags are not properly initialized in some cases [partial fix]
[FUML 12-11](http://solitaire.omg.org/issues/task-force/FUML12#issue-16436) (18362) Problem with ActivityExecution::terminate
[FUML 12-12](http://solitaire.omg.org/issues/task-force/FUML12#issue-16437) (18364) RealValue::toString puts too many digits in the mantissa
[FUML 12-13](http://solitaire.omg.org/issues/task-force/FUML12#issue-16438) (18365) Objects with cyclic references cannot be converted to string representations

### XMI Loader/Infrastructure

This maintenance release resolves the following issues related to the XMI Loader and other Reference Implementation infrastructure capabilities, as tracked in our issue management system on GitHub (see https://github.com/ModelDriven/fUML-Reference-Implementation/issues).

#### Improvements

[Issue #9] - Add Support For Papyrus Models
[Issue #22] - Add Support for Papyrus Libraries

