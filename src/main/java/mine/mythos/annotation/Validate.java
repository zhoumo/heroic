package mine.mythos.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Validate {

	boolean required() default false;

	boolean unique() default false;

	int minLength() default 0;

	int maxLength() default 0;
}
