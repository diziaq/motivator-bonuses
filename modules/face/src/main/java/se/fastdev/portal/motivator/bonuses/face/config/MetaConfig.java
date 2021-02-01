package se.fastdev.portal.motivator.bonuses.face.config;

import static se.fastdev.portal.motivator.bonuses.face.extras.errhandle.ParsedError.auto;
import static se.fastdev.portal.motivator.bonuses.face.extras.errhandle.ParsedError.badInputData;
import static se.fastdev.portal.motivator.bonuses.face.extras.errhandle.ParsedError.businessLogicFailure;
import static se.fastdev.portal.motivator.bonuses.face.extras.errhandle.ParsedError.simple;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebInputException;
import se.fastdev.portal.motivator.bonuses.core.BonusesException;
import se.fastdev.portal.motivator.bonuses.face.extras.errhandle.ErrorRecognition;
import se.fastdev.portal.motivator.bonuses.face.extras.errhandle.ParsedError;
import se.fastdev.portal.motivator.bonuses.toolbox.control.TypeAwareControl;

@Configuration
public class MetaConfig {

  @Bean
  public Logger sharedLogger() {
    return LoggerFactory.getLogger(MetaConfig.class.getPackage().getName() + ".shared-logger");
  }

  @Bean
  public ErrorRecognition errorRecognition() {
    return ErrorRecognition.fromControl(
        TypeAwareControl
            .perAssignable(Exception.class, ParsedError.class)
            .with(BonusesException.class,
                  e -> businessLogicFailure(e))
            .with(WebExchangeBindException.class,
                  e -> badInputData(e.getBindingResult().getAllErrors(),
                                    ObjectError::getDefaultMessage))
            .with(ConstraintViolationException.class,
                  e -> badInputData(e.getConstraintViolations(),
                                    ConstraintViolation::getMessage)
            )
            .with(ServerWebInputException.class,
                  e -> simple(HttpStatus.BAD_REQUEST,
                              "Unexpected format of input parameters",
                              e.getMessage())
            )
            .with(ResponseStatusException.class,
                  e -> simple(e.getStatus(), e.getMessage())
            )
            .fallback(e -> auto(e)));
  }
}
