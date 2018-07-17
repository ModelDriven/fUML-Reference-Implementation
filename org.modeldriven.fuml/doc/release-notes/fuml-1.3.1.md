### fUML Reference Implementation
### v1.3.1 Release Notes

This is a production release of the fUML Reference Implementation, tracking the issue resolutions approved by 
the fUML 1.4 Revision Task Force (RTF).

**IMPORTANT NOTE**

The fUML 1.4 RTF was unusual, in that it's primary task was to migrate fUML to UML 2.5.1. Therefore, fUML 1.4 
will have no functional differences from fUML 1.3, but the structure of the fUML syntax and semantics has been
significantly re-organized to follow the new abstract syntax package structure introduced in UML 2.5.
The Java package hierarchy for the syntax and semantics implementation classes has been correspondingly updated
in the Reference Implementation. In addition, while the Java package names are still based on the names of the 
corresponding UML packages, they are now rendered entirely in lower case, per usual Java convention (e.g., 
`fuml.semantics.actions` rather than `fUML.Semantics.Actions`).

### fUML Execution Engine

The current base release of the fUML Execution Engine is v1.3.0 (see the [v1.3.0 Release Notes](./fuml-1.3.0.md)), 
conforming to v1.3.0 of the [fUML Specification](http://www.omg.org/spec/FUML/1.3.0).
The v1.3.1 tracking release includes implementation of resolutions to the following OMG issues, which have
been approved by the fUML Revision Task Force for fUML 1.4 but have not yet been
formally adopted.

The full list of open fUML issues is available at http://issues.omg.org/issues/task-force/FUML14.

The following issues were resolved by the fUML 1.4 RTF:

[FUML 14-9](http://issues.omg.org/issues/task-force/FUML14#issue-42353) Migrate fUML to UML 2.5.1

[FUML 14-10](http://issues.omg.org/issues/task-force/FUML14#issue-42655) The ExecutionFactory class descriptions need 
to be updated in the specification for CentralBufferNode and DataStoreNode

[FUML 14-21](http://issues.omg.org/issues/task-force/FUML14#issue-42985) Correction to the resolution to issue 14-10

### XMI Loader/Infrastructure

In order to implement the migration to UML 2.5.1, as required by the resolution to OMG Issue FUML 14-9, 
this tracking release also updates the XMI Loader/Infrastructure for the new structure of the UML 2.5.1 metamodel.

#### Bug Fixes

None

#### Improvements

[Issue #31](https://github.com/ModelDriven/fUML-Reference-Implementation/issues/31) Update the XMI Loader implementation for the new structure of UML 2.5.1

