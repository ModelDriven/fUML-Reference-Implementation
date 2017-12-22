/*
 * Copyright 2009-2015 Data Access Technologies, Inc., except
 * as stated in the file entitled Licensing-Information.
 * Licensed under the Academic Free License version 3.0
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated
 * in the file entitled Licensing-Information.
 */

package org.modeldriven.fuml.environment;

import java.util.Iterator;

import org.modeldriven.fuml.assembly.ElementStubAssembler;

import fuml.Debug;
import fuml.semantics.commonbehavior.ExecutionQueue;
import fuml.syntax.activities.Activity;
import fuml.syntax.commonstructure.Comment;

public class ActivityExecution extends fuml.semantics.activities.ActivityExecution {
	
    public void execute() {
        Activity activity = (Activity) (this.getTypes().getValue(0));
        if (activity.ownedComment != null && activity.ownedComment.size() > 0
                && ElementStubAssembler.STUB_TOKEN.equals(activity.ownedComment.get(0).body)) {
            Debug.println("[execute] invalid activity encountered: " + activity.name
                    + " - see below error(s)");
            Iterator<Comment> comments = activity.ownedComment.iterator();
            comments.next(); // skip the "stub" comment
            while (comments.hasNext())
                Debug.println("[execute] error: " + comments.next().body);
            throw new InvalidExecutionTargetException("cannot execute invalid activity '" + activity.name
                    + "' - see above errors");
        }
        if (ExecutionQueue.notStarted()) {
        	ExecutionQueue.start(this);
        } else {
        	super.execute();
        }
    } // execute

} // ActivityExecution
