package com.finateltechhbm.servlet;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.finateltechhbm.validation.Faces;

public class LoginErrorPhaseListener implements PhaseListener {
    private static final long serialVersionUID = -1216620620302322995L;

    @SuppressWarnings("deprecation")
    @Override
    public void beforePhase(final PhaseEvent arg0) {

        Exception e = (Exception) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
                .get(AbstractAuthenticationProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY);

        if (e instanceof BadCredentialsException) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
                    .put(AbstractAuthenticationProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY, null);
           
                Faces.addError("Please Enter Valid Username and Password");
        }
    }

    @Override
    public void afterPhase(final PhaseEvent arg0) {}

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RENDER_RESPONSE;
    }

}
