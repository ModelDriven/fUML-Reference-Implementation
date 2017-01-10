
/*
 * Initial version copyright 2008 Lockheed Martin Corporation, except  
 * as stated in the file entitled Licensing-Information. 
 * 
 * All modifications copyright 2009-2017 Data Access Technologies, Inc.
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fUML.Semantics.CommonBehaviors.Communications;

import fUML.Debug;
import UMLPrimitiveTypes.*;

import java.util.Iterator;

import fUML.Syntax.Classes.Kernel.*;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.*;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.*;
import fUML.Semantics.Loci.LociL1.*;

public class ObjectActivation extends org.modeldriven.fuml.FumlObject {

	public fUML.Semantics.CommonBehaviors.Communications.ClassifierBehaviorInvocationEventAccepterList classifierBehaviorInvocations = new fUML.Semantics.CommonBehaviors.Communications.ClassifierBehaviorInvocationEventAccepterList();
	public fUML.Semantics.CommonBehaviors.Communications.EventAccepterList waitingEventAccepters = new fUML.Semantics.CommonBehaviors.Communications.EventAccepterList();
	public fUML.Semantics.CommonBehaviors.Communications.EventOccurrenceList eventPool = new fUML.Semantics.CommonBehaviors.Communications.EventOccurrenceList();
	public fUML.Semantics.Classes.Kernel.Object_ object = null;

	public void stop() {
		// Stop this object activation by terminating all classifier behavior
		// executions.

		ClassifierBehaviorInvocationEventAccepterList classifierBehaviorInvocations = this.classifierBehaviorInvocations;
		for (int i = 0; i < classifierBehaviorInvocations.size(); i++) {
			ClassifierBehaviorInvocationEventAccepter classifierBehaviorInvocation = classifierBehaviorInvocations
					.getValue(i);
			classifierBehaviorInvocation.terminate();
		}

	} // stop

	public void register(
			fUML.Semantics.CommonBehaviors.Communications.EventAccepter accepter) {
		// Register the given event accepter to wait for a dispatched signal
		// event.

		Debug.println("[register] object = " + this.object);
		Debug.println("[register] accepter = " + accepter);

		this.waitingEventAccepters.addValue(accepter);
	} // register

	public void unregister(
			fUML.Semantics.CommonBehaviors.Communications.EventAccepter accepter) {
		// Remove the given event accepter for the list of waiting event
		// accepters.

		Debug.println("[unregister] object = " + this.object);
		Debug.println("[unregister] accepter = " + accepter);

		boolean notFound = true;
		int i = 1;
		while (notFound & i <= this.waitingEventAccepters.size()) {
			if (this.waitingEventAccepters.getValue(i - 1) == accepter) {
				this.waitingEventAccepters.remove(i - 1);
				notFound = false;
			}
			i = i + 1;
		}

	} // unregister

	public void dispatchNextEvent() {
		// Get the next event occurrence out of the event pool.
		// If there are one or more waiting event accepters with triggers that
		// match the event occurrence, then dispatch it to exactly one of those
		// waiting accepters.

		if (this.eventPool.size() > 0) {
			EventOccurrence eventOccurrence = this.getNextEvent();

			Debug.println("[dispatchNextEvent] eventOccurrence = " + eventOccurrence);

			intList matchingEventAccepterIndexes = new intList();
			EventAccepterList waitingEventAccepters = this.waitingEventAccepters;
			for (int i = 0; i < waitingEventAccepters.size(); i++) {
				EventAccepter eventAccepter = waitingEventAccepters.getValue(i);
				if (eventAccepter.match(eventOccurrence)) {
					matchingEventAccepterIndexes.addValue(i);
				}
			}

			if (matchingEventAccepterIndexes.size() > 0) {
				// *** Choose one matching event accepter non-deterministically. ***
				int j = ((ChoiceStrategy) this.object.locus.factory
						.getStrategy("choice"))
						.choose(matchingEventAccepterIndexes.size());
				int k = matchingEventAccepterIndexes.getValue(j - 1);
				EventAccepter selectedEventAccepter = this.waitingEventAccepters
						.getValue(k);
				this.waitingEventAccepters.removeValue(k);
				selectedEventAccepter.accept(eventOccurrence);
			}
		}
	} // dispatchNextEvent

	public fUML.Semantics.CommonBehaviors.Communications.EventOccurrence getNextEvent() {
		// Get the next event from the event pool, using a get next event
		// strategy.

		return ((GetNextEventStrategy) this.object.locus.factory
				.getStrategy("getNextEvent")).getNextEvent(this);
	} // getNextEvent

	public void send(
			fUML.Semantics.CommonBehaviors.Communications.EventOccurrence eventOccurrence) {
		// Add an event occurrence to the event pool and signal that a
		// new event occurrence has arrived.

		this.eventPool.addValue(eventOccurrence);
		_send(new ArrivalSignal());
	} // send

	public void startBehavior(
			fUML.Syntax.Classes.Kernel.Class_ classifier,
			fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList inputs) {
		// Start the event dispatch loop for this object activation (if it has
		// not already been started).
		// If a classifier is given that is a type of the object of this object
		// activation and there is not already a classifier behavior invocation
		// for it,
		// then create a classifier behavior invocation for it and add an invocation
		// event occurrence to the event pool.
		// Otherwise, create a classifier behavior invocation for each of the
		// types of the object of this object activation which has a classifier
		// behavior or which is a behavior itself
		// and for which there is not currently a classifier behavior invocation.

		// Start EventDispatchLoop
		_startObjectBehavior();

		if (classifier == null) {
			Debug.println("[startBehavior] Starting behavior for all classifiers...");
			// *** Start all classifier behaviors concurrently. ***
			Class_List types = this.object.types;
			for (Iterator i = types.iterator(); i.hasNext();) {
				Class_ type = (Class_) i.next();
				if (type instanceof Behavior | type.classifierBehavior != null) {
					this.startBehavior(type, new ParameterValueList());
				}
			}
		} else {
			Debug.println("[startBehavior] Starting behavior for " + classifier.name + "...");

			_beginIsolation();
			boolean notYetStarted = true;
			int i = 1;
			while (notYetStarted
					& i <= this.classifierBehaviorInvocations.size()) {
				notYetStarted = (this.classifierBehaviorInvocations
						.getValue(i - 1).classifier != classifier);
				i = i + 1;
			}

			if (notYetStarted) {
				ClassifierBehaviorInvocationEventAccepter newInvocation = new ClassifierBehaviorInvocationEventAccepter();
				newInvocation.objectActivation = this;
				this.classifierBehaviorInvocations.addValue(newInvocation);
				newInvocation.invokeBehavior(classifier, inputs);
				InvocationEventOccurrence eventOccurrence = new InvocationEventOccurrence();
				eventOccurrence.execution = newInvocation.execution;
				this.eventPool.addValue(eventOccurrence);
				_send(new ArrivalSignal());
			}
			_endIsolation();
		}
	} // startBehavior

	private ObjectActivation_Behavior behavior = new ObjectActivation_Behavior(
			this);

	public void _send(ArrivalSignal signal) {
		this.behavior._send(signal);
	}

	public void _startObjectBehavior() {
		this.behavior._startObjectBehavior();
	}
	
	public static void _endIsolation() {
		Debug.println("[_endIsolation] End isolation.");
	} // _endIsolation

	public static void _beginIsolation() {
		Debug.println("[_beginIsolation] Begin isolation.");
	} // _beginIsolation

} // ObjectActivation
