package mine.heroic.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Cache {

	boolean useMemcached() default false;

	boolean useRedis() default false;

	boolean useMongodb() default false;
}
