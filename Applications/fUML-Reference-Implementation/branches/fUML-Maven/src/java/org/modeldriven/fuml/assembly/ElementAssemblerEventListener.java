package org.modeldriven.fuml.assembly;

import java.util.EventListener;

public interface ElementAssemblerEventListener extends EventListener {
    public void elementAssembled(ElementAssemblerEvent event);
    public void elementStubAssembled(ElementAssemblerResultsEvent event);
    public void elementGraphAssembled(ElementAssemblerResultsEvent event);
}
