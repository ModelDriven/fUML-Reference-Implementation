### fUML Reference Implementation
### v1.2.0b Release Notes

This is a production maintenance release of the fUML Reference Implementation.

### fUML Execution Engine

The current base release for the fUML Execution Engine is v1.2.0, conforming to v1.2.1 of the [fUML Specification](http://www.omg.org/spec/FUML/1.2.1). The v1.2.0b maintenance release builds on the v1.2.0a maintenance release (see the [v1.2.0a Release Notes](./fuml-1.2.0a.md)).

This maintenance release resolves the following issue related to the implementation of the event-handling behavior of an object activation, as tracked in our issue management system on GitHub (see https://github.com/ModelDriven/fUML-Reference-Implementation/issues). This is a change to the fUML execution engine, but there is no corresponding OMG issue, because the bug being fixed is specific to the Reference Implementation.

The full list of open fUML issues is available at http://solitaire.omg.org/issues/task-force/FUML13.

### XMI Loader/Infrastructure

No changes to the XMI Loader/Infrastructure have been made in this maintenance release.

#### Bug Fixes

[Issue #28] - Problem starting the behaviors of a multiply classified active object
