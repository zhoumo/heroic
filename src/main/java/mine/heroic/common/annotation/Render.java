package mine.heroic.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Render {

	public static final String INPUT = "INPUT";

	public static final String SELECT = "SELECT";

	public static final String RADIO = "RADIO";

	public static final String SELECTOR = "SELECTOR";

	boolean table() default true;

	boolean dialog() default true;

	String key() default "";

	String type() default INPUT;

	String url() default "";

	String[] text() default "";

	String[] value() default "";
}
