package mine.heroic.aspect;

import mine.heroic.common.BaseClass;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceAspect extends BaseClass {

	private Long startTime, endTime;

	@Before("execution (* mine..service..*(..))")
	public void doBefore(JoinPoint joinPoint) {
		startTime = System.currentTimeMillis();
	}

	@After("execution (* mine..service..*(..))")
	public void doAfter(JoinPoint joinPoint) {
		endTime = System.currentTimeMillis();
		logger.debug(joinPoint.getSignature().toLongString() + " cost " + Long.toString(endTime - startTime) + "ms");
	}
}
