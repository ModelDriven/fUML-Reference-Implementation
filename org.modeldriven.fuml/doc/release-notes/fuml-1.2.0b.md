### fUML Reference Implementation
### v1.2.0b Release Notes

This is a production maintenance release of the fUML Reference Implementation.

### fUML Execution Engine

The current base release for the fUML Execution Engine is v1.2.0, conforming to v1.2.1 of the [fUML Specification](http://www.omg.org/spec/FUML/1.2.1). The v1.2.0b maintenance release builds on the v1.2.0a maintenance release (see the [v1.2.0a Release Notes](./fuml-1.2.0a.md)).

This maintenance release resolves the issues listed below, as tracked in our issue management system on GitHub (see https://github.com/ModelDriven/fUML-Reference-Implementation/issues). These are changes to the fUML execution engine, but there are no corresponding OMG issues, because the issues being resolved are specific to the Reference Implementation.

This release also includes the resolution of the following OMG issue, which was part of fUML 1.2, but was not properly implemented in v1.2.0 of the Execution Engine.

[FUML 12-11](http://solitaire.omg.org/issues/task-force/FUML12#issue-16436) (18362) Problem with ActivityExecution::terminate

The full list of open fUML issues is available at http://solitaire.omg.org/issues/task-force/FUML13.

### XMI Loader/Infrastructure

No changes to the XMI Loader/Infrastructure have been made in this maintenance release.

#### Bug Fixes

[Issue #28] - Problem starting the behaviors of a multiply classified active object

#### Improvements

[Issue #29] - fUML.Library and fUML.Test packages are only used for builtin tests
