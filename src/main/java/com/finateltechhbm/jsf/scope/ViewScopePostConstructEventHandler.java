package com.finateltechhbm.jsf.scope;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.component.UIViewRoot;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.PostConstructViewMapEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.ViewMapListener;

public class ViewScopePostConstructEventHandler implements ViewMapListener {
  protected final Logger logger = LoggerFactory.getLogger(getClass());

  public boolean isListenerForSource(Object source) {
    return source instanceof UIViewRoot;
  }

  public void processEvent(SystemEvent event) throws AbortProcessingException {
    if (event instanceof PostConstructViewMapEvent) {
      processPostConstructEvent((PostConstructViewMapEvent) event);
      return;
    }
    logger.warn("received unexpected event: {}", event);
  }

  private void processPostConstructEvent(PostConstructViewMapEvent event) {
    logger.debug("processing post construct event");
    UIViewRoot viewRoot = (UIViewRoot) event.getComponent();
  }
}