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

1. encourage tool vendors to implement this standard in their tools

2. provide a reference that can assist in evaluating vendor implementations
conformance with the specification

3. encourage evolution of the reference implementation to support further
enhancements to its functionality such as a model debugger, or animation of the
execution trace

4. support evaluation and evolution of the the specification to support UML
execution semantics and the execution semantics of its profiles suchas SysML.

###Licensing

For licensing information, please see the file Licensing-Information.txt and the
associated files Common-Public-License-1.0.txt and Apache-License-2.0.txt.

###Building

The implementation build requires the following to be installed:

* Oracle Java Version 6 or above - see http://java.oracle.com/
* Apache Maven Version 2.2 or above - see http://maven.apache.org/

To build from the command line:

1. In a Windows/DOS command window, navigate to the 'root' reference
implementation directory.
This directory is where the Apache Maven 'pom.xml' file can be found.

2. Use the following command:
   
        mvn -DskipTests install

Several targets will be executed, and the message 'BUILD SUCCESSFUL' should
be displayed. Generated and compiled code can be found under the 'target'
directory.

To build using Eclipse:
    The implementation build requires Eclipse Kepler or above with the M2E plugin and dependencies:

1. Start Eclipse

2. Import the Maven project into Eclipse
 * File->Import->Existing Maven Projects
 * Select Next and for the "root directory" select the parent directory for the pom.xml file.
 * The project should be imported successfully.

3. Run the tests
 * Right click the project root (fuml)
 * Run As->Maven test

4. Install
 * Right click the project root (fuml)
 * Run As->Maven install

###Testing

1. In a Windows/DOS command window, navigate to the 'root' reference
implementation directory. This directory is where the Apache Ant 'build.xml'
file can be found.

2. Use the following command:

        mvn test

The test output can be found in the 'target/surefire-reports'
directory. 


Testing individual JUnit tests in Eclipse: 

1. Right-click on any file under the 'src/test/java' folder with a filename ending in
'TestCase' or 'Test'
2. Run As->JUnit Test.
3. The test should execute and display output.

###Deploying

1. In a Windows/DOS command window, navigate to the 'root' reference
implementation directory. This directory is where the Apache Ant 'build.xml' 
file can be found.

2. Use the following command:

        mvn install

A binary deployment 'zip' file will be created and expanded onto the current
drive's root directory. The deployment directory will be called: 
'fuml-[version string]-distribution' 

###Running

1. In a Windows/DOS command window, navigate to the 'bin' directory for the
DEPLOYED implementation. The implementation can only be run from a DEPLOYED
binary or source distribution (see above).

2. To load a UML model file (XMI 2.1 format) and execute one or more behaviors
(activities), use the following command:

        fuml <model-file> [<behavior-name> <behavior-name> <behavior-name> <...>]

   Where:

   * `<model-file>` is UML model file (XMI 2.1 format)
     
   * `<behavior-name>` is a named behavior within the model-file

   If no behavior name is given, then there should be only a single behavior in the
top-level namespace of the model, and this is what is executed.

3. The execution trace will print to the console. This may be redirected to a
file if desired (e.g., by appending '> trace.txt' to the command above).

