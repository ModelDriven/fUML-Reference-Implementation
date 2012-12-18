package org.modeldriven.fuml.io;

import java.util.EventListener;

public interface ElementReaderEventListener extends EventListener {
    public void streamCompleted(ElementReaderEvent event);
}
