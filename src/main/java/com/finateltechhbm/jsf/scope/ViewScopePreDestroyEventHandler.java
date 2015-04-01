package com.finateltechhbm.jsf.scope;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.component.UIViewRoot;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.PreDestroyViewMapEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.ViewMapListener;

public class ViewScopePreDestroyEventHandler implements ViewMapListener {
  protected final Logger logger = LoggerFactory.getLogger(getClass());

  public boolean isListenerForSource(Object source) {
    return source instanceof UIViewRoot;
  }

  public void processEvent(SystemEvent event) throws AbortProcessingException {
    if (event instanceof PreDestroyViewMapEvent) {
      processPreDestroyEvent((PreDestroyViewMapEvent) event);
    }
    logger.warn("received unexpected event: {}", event);
  }

  private void processPreDestroyEvent(PreDestroyViewMapEvent event) {
    logger.debug("processing pre destroy event");
    UIViewRoot viewRoot = (UIViewRoot) event.getComponent();
  }
}