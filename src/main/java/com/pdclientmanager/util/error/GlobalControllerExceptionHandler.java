package com.pdclientmanager.util.error;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalControllerExceptionHandler {

    public static final String DEFAULT_ERROR_VIEW = "error";
    private static Logger warningLogger = 
            LogManager.getLogger("warningLogger");
    private static final Logger debugLogger =
            LogManager.getLogger("debugLogger");
    private static final Logger infoLogger =
            LogManager.getLogger("infoLogger");
    
    @ExceptionHandler(value = EntityNotFoundException.class)
    public ModelAndView handleEntityNotFound(HttpServletRequest req, EntityNotFoundException ex) {
        System.out.println(warningLogger.getName() + " = " + warningLogger.getLevel() + " - " + warningLogger.isInfoEnabled());
        System.out.println(debugLogger.getName() + " = " + debugLogger.getLevel() + " - " + debugLogger.isInfoEnabled());
        System.out.println(infoLogger.getName() + " = " + infoLogger.getLevel() + " - " + infoLogger.isWarnEnabled() + " - " + infoLogger.isDebugEnabled());
        debugLogger.warn("This better not show up");
        debugLogger.info("This better not show up");
        debugLogger.debug("Better be there");
        debugLogger.trace("This is a trace");
        infoLogger.warn("This better not show up");
        infoLogger.info("Better be there");
        infoLogger.debug("This better not show up");
        warningLogger.warn("Entity could not be found");
        ApiError error = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Oops! The entity you are searching for could not be found", ex);
        return createDefaultErrorModelAndView(error, req);
    }
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ModelAndView handleMethodArgumentNotValid(HttpServletRequest req, MethodArgumentNotValidException ex) {
        warningLogger.warn("There was an invalid request");
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST, "Oops! There was an invalid request.", ex);
        return createDefaultErrorModelAndView(error, req);
    }
    
    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultExceptionHandler(HttpServletRequest req, Exception ex) throws Exception {
        if(AnnotationUtils.findAnnotation(
                ex.getClass(), ResponseStatus.class) != null) {
                    throw ex;
                }
        ApiError error = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Oops! The program has encountered an unexpected error.", ex);
        warningLogger.error("There was an unexpected error");
        return createDefaultErrorModelAndView(error, req);
    }
    
    private ModelAndView createDefaultErrorModelAndView(ApiError error, HttpServletRequest req) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("error", error);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName(DEFAULT_ERROR_VIEW);
        return mav;
    }
}
