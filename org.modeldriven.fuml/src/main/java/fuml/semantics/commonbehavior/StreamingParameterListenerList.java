package fuml.semantics.commonbehavior;

import java.util.ArrayList;

public class StreamingParameterListenerList extends ArrayList<StreamingParameterListener> {

	public StreamingParameterListenerList() {
		super();
	}

	public StreamingParameterListener getValue(int i) {
		return get(i);
	}

	public void addValue(StreamingParameterListener v) {
		add(v);
	}

	public void addValue(int i, StreamingParameterListener v) {
		add(i, v);
	}

	public void setValue(int i, StreamingParameterListener v) {
		set(i, v);
	}

	public void removeValue(int i) {
		remove(i);
	}

}
