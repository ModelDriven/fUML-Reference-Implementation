package org.modeldriven.fuml.repository.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.repository.RepositoryArtifact;
import org.modeldriven.fuml.xmi.stream.StreamNode;


public class OpaqueBehavior extends NamedElement
    implements org.modeldriven.fuml.repository.OpaqueBehavior {

    private static Log log = LogFactory.getLog(OpaqueBehavior.class);
	private fUML.Syntax.CommonBehaviors.BasicBehaviors.OpaqueBehavior behavior;
	
	public OpaqueBehavior(fUML.Syntax.CommonBehaviors.BasicBehaviors.OpaqueBehavior behavior,
			RepositoryArtifact artifact) {
		super(behavior, artifact);
		this.behavior = behavior;
	}
	
	public String getBody() {
		if (this.behavior.body != null) {
			if (this.behavior.body.size() == 1)
				return this.behavior.body.get(0);
			else if (this.behavior.body.size() > 0) {
				log.warn("ignoring all but first behavior body");
				return this.behavior.body.get(0);
			}
		}
		return "";
	}

	public String getLanguage() {
		if (this.behavior.language != null) {
			if (this.behavior.language.size() == 1)
				return this.behavior.language.get(0);
			else if (this.behavior.language.size() > 0) {
				log.warn("ignoring all but first behavior language");
				return this.behavior.language.get(0);
			}
		}
		return "";
	}



}
