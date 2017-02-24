package baseFramework.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author chao.li
 * @date 2016年12月15日
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)

public @interface ConfigProperty {

	/**
	 * zk path
	 * 
	 */
	String path();

	boolean required() default true;

	/**
	 * 是否是json
	 */
	boolean json() default false;

}
