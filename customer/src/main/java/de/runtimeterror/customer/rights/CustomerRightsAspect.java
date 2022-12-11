package de.runtimeterror.customer.rights;

import de.runtimeterror.customer.Customer;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class CustomerRightsAspect {

    @Around("execution(public * *(..)) && @annotation(secured)")
    public void checkModuleAccess(ProceedingJoinPoint call, de.runtimeterror.customer.rights.Secured secured) throws Throwable {
        Object[] args = call.getArgs();
        for (var arg : args) {
            if (arg instanceof Customer customer) {
                CustomerRights[] modules = secured.value();
                for (var module : modules) {
                    var customerRights = customer.getRights();
                    for (var customerRight : customerRights) {
                        if (customerRight == module) {
                            call.proceed();
                            log.info("User has access to module");
                            return;
                        }
                    }
                }
                throw new CustomerNotAllowedException(customer.getId(), modules);
            }
        }
    }
}
