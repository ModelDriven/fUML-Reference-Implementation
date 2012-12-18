Foundational UML Reference Implementation
-----------------------------------------

This open source software is a reference implementation, consisting of software
and related files, for the OMG specification called the Semantics of a
Foundational Subset for Executable UML Models (fUML). The reference
implementation is intended to implement the execution semantics of UML activity
models, accepting an XMI file from a conformant UML model as its input and
providing an execution trace of the selected activity model(s) as its output.

The reference implementation was developed as part of a Lockheed Martin
Corporation funded project with Model Driven Solutions (a division of Data
Access Technologies) in 2008. The objectives for making this reference
implementation open source are to:

a) encourage tool vendors to implement this standard in their tools

b) provide a reference that can assist in evaluating vendor implementations
conformance with the specification

c) encourage evolution of the reference implementation to support further
enhancements to its functionality such as a model debugger, or animation of the
execution trace

d) support evaluation and evolution of the the specification to support UML
execution semantics and the execution semantics of its profiles suchas SysML.

Licensing
---------

For licensing information, please see the file Licensing-Information.txt and the
associated files Common-Public-License-1.0.txt and Apache-License-2.0.txt.

Building
--------

The implementation build requires the following to be installed:

    Oracle Java Version 6 or above - see http://java.oracle.com/
    Apache Ant Version 1.7.0 or above - see http://ant.apache.org/

To build from the command line:

1. In a Windows/DOS command window, navigate to the 'root' reference
implementation directory.
This directory is where the Apache Ant 'build.xml' file can be found.

2. Use the following command:

    ant

Several targets will be executed, and the message 'BUILD SUCCESSFUL' should
be displayed. Generated and compiled code can be found under the 'target'
directory.

To build using Eclipse:

1. Checkout the source from SVN or download and unzip the source '.zip' file
into a local directory called 'fUML-Reference-Implementation'. This name reflects
the current SVN folder name. 

	Note: For Eclipse with an integrated Ant build certain restrictions
	require some folder name to be baked into the Eclipse project external
	build configuration, hence we baked in the current SVN folder name for
	the RI.

2. Launch Eclipse and Create a Workspace. In the "Select a Workspace" prompt,
browse to the parent folder of the 'fUML-Reference-Implementation' folder above.

3. Import the fUML RI Project Into the Workspace. Select File->Import->
General->Existing Projects Into Workspace. Select 'Next>>'. Select the 'Select
Root Directory' radio-button, browse to the 'fUML-Reference-Implementation'
folder above, and select it. Then select 'Finish'. The project should begin
building automatically.


Testing
-------

1. In a Windows/DOS command window, navigate to the 'root' reference
implementation directory. This directory is where the Apache Ant 'build.xml'
file can be found.

2. Use the following command:

    ant test

The formatted HTML test output can be found in the 'target/fuml/testoutput'
directory. Load the 'target/fuml/testoutput/index.html' page into a browser to
view the test results.

The below tests can be run individual using the following commands:

    ant [test-name]
    ant run-test-reports

where 'test-name' is one of the following:

test-model
test-library
test-validate
test-execution
test-magic-draw-execution
test-external-reference
test-function-execution
test-incremental-validation


Testing individual JUnit tests in Eclipse: 

1. Right-click on any file under the 'test' folder with a filename ending in
'TestCase' or 'Test' and select Run As->JUnit Test. The test should execute and
display output.


Deploying
---------

1. In a Windows/DOS command window, navigate to the 'root' reference
implementation directory. This directory is where the Apache Ant 'build.xml' 
file can be found.

2. Use the following command:

    ant deploy

A binary deployment 'zip' file will be created and expanded onto the current
drive's root directory. The deployment directory will be called: 
'fuml-[version string]' 

Note: Pre-packaged deployment archives can be found under the
'dist' directory. These can be manually un-archived to the desired location.


Running
-------

1. In a Windows/DOS command window, navigate to the 'bin' directory for the
DEPLOYED implementation. The implementation can only be run from a DEPLOYED
binary or source distribution (see above).

2. To load a UML model file (XMI 2.1 format) and execute one or more behaviors
(activities), use the following command:

    fuml <model-file> [<behavior-name> <behavior-name> <behavior-name> \<...>]

Where:
     <model-file> is UML model file (XMI 2.1 format)
     <behavior-name> is a named behavior within the model-file

If no behavior name is given, then there should be only a single behavior in the
top-level namespace of the model, and this is what is executed.

3. The execution trace will print to the console. This may be redirected to a
file if desired (e.g., by appending '> trace.txt' to the command above).
