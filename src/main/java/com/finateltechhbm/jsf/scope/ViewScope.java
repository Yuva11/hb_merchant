package com.finateltechhbm.jsf.scope;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import javax.faces.context.FacesContext;
import java.util.Map;

public class ViewScope implements Scope {
  protected final Logger logger = LoggerFactory.getLogger(getClass());

  public Object get(String name, ObjectFactory objectFactory) {
    Map<String, Object> viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
    if (viewMap.containsKey(name)) {
      logger.debug("get '{}' used from viewMap", name);
      return viewMap.get(name);
    } else {
      Object object = objectFactory.getObject();
      viewMap.put(name, object);
      logger.debug("get '{}' created and put into viewMap", name);
      return object;
    }
  }

  public Object remove(String name) {
    logger.debug("remove '{}'", name);
    return FacesContext.getCurrentInstance().getViewRoot().getViewMap().remove(name);
  }

  public String getConversationId() {
    return null;
  }

  public void registerDestructionCallback(String name, Runnable callback) {
    //Not supported
  }

  public Object resolveContextualObject(String key) {
    return null;
  }
}