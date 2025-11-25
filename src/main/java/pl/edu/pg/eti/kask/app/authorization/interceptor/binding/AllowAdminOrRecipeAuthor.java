package pl.edu.pg.eti.kask.app.authorization.interceptor.binding;

import jakarta.interceptor.InterceptorBinding;

import java.lang.annotation.*;

@InterceptorBinding
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface AllowAdminOrRecipeAuthor {

}
