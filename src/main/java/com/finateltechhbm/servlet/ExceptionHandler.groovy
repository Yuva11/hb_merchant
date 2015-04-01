package com.finateltechhbm.servlet;



import groovy.util.logging.Slf4j

import javax.faces.application.ViewExpiredException
import javax.servlet.ServletException
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Slf4j
public class ExceptionHandler extends HttpServlet {

    private static final long serialVersionUID = 8041268911605424292L;
    public ExceptionHandler() {
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse res)throws ServletException, IOException {
		log.error("Exception Handler Called");
        Enumeration<String> asd = request.getAttributeNames();
        boolean viewExpired = false
        while(asd.hasMoreElements()){
            def expkey = asd.nextElement();
            if(request.getAttribute(expkey) instanceof ViewExpiredException){
				log.error("View Expired Exception Occured",(Exception)request.getAttribute(expkey));
                viewExpired = true
            }

            if(request.getAttribute(expkey) instanceof Exception){
                log.error(" OOPS : Unexpected Error Occured",(Exception)request.getAttribute(expkey));
            }
        }
        if(viewExpired){
			log.info("view Expired in exception Handler:: "+getServletContext().getContextPath()+"/login.xhtml");
			res.sendRedirect(getServletContext().getContextPath()+"/login.xhtml");
        }
        else {
			log.info("OOPS in exception Handler:: "+ getServletContext().getContextPath()+"/login.xhtml");
			 res.sendRedirect(getServletContext().getContextPath()+"/exception.xhtml");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
        doPost(req,resp);
    }
}