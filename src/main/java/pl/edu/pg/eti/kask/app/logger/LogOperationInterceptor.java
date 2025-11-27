package pl.edu.pg.eti.kask.app.logger;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.security.enterprise.SecurityContext;
import pl.edu.pg.eti.kask.app.logger.binding.LogOperation;

import java.util.logging.Logger;

@Interceptor
@LogOperation
@Priority(10)
public class LogOperationInterceptor {

    private final SecurityContext securityContext;

    private static final Logger logger = Logger.getLogger(LogOperationInterceptor.class.getName());

    @Inject
    public LogOperationInterceptor(SecurityContext securityContext) {
        this.securityContext = securityContext;
    }

    @AroundInvoke
    public Object invoke(InvocationContext context) throws Exception {
        LogOperation op = context.getMethod().getAnnotation(LogOperation.class);

        if (op == null) {
            op = context.getTarget().getClass().getAnnotation(LogOperation.class);
        }

        String annotation = (op != null) ? op.value() : "";

        String username = securityContext.getCallerPrincipal() != null
                ? securityContext.getCallerPrincipal().getName()
                : "anonymous";

        Object resourceId = context.getParameters().length > 0
                ? context.getParameters()[0]
                : "none";

        logger.info(() -> String.format(
                "User='%s' Operation='%s' ResourceId='%s'",
                username, annotation, resourceId
        ));

        return context.proceed();
    }

}
