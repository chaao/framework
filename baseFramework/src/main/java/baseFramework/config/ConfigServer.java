package baseFramework.config;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Preconditions;

import baseFramework.exception.ServerException;
import baseFramework.exception.TypeMismatchException;
import baseFramework.jackson.JsonUtils;
import baseFramework.qconf.QconfClient;
import baseFramework.utils.StringTools;

/**
 * @author chao.li
 * @date 2016年12月15日
 */
public class ConfigServer {

	private static class SingletonHolder {
		/**
		 * 单例对象实例
		 */
		static final ConfigServer INSTANCE = new ConfigServer();
	}

	public static ConfigServer getInstance() {
		return SingletonHolder.INSTANCE;
	}

	private static Logger logger = LoggerFactory.getLogger(ConfigServer.class);
	private static Set<PropertyInfo> propertyInfos = null;
	private static ScheduledExecutorService executor = null;

	private ConfigServer() {
		propertyInfos = new CopyOnWriteArraySet<PropertyInfo>();
		executor = Executors.newSingleThreadScheduledExecutor();
		executor.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				try {
					checkPropertyIsChanged();
				} catch (Exception e) {
					logger.error("", e);
				}
			}
		}, 10, 5, TimeUnit.SECONDS);
	}

	private void checkPropertyIsChanged() {
		for (PropertyInfo propertyInfo : propertyInfos) {
			String value = null;
			try {
				value = getValue(propertyInfo.getPath());
				String remoteValue = propertyInfo.getRemoteValue();
				if (StringUtils.equals(value, remoteValue)) {
					continue;
				}
				this.setValue(value, propertyInfo);
				this.reloadOnPropertyChange(propertyInfo);

			} catch (Exception e) {
				logger.error(StringTools.format("change property error! {}.{}, path:{}, value:{}, local:{}, remote:{}",
						propertyInfo.getClazz().getSimpleName(), propertyInfo.getField().getName(),
						propertyInfo.getPath(), value, propertyInfo.getLocalValue(), propertyInfo.getRemoteValue()), e);
			}
		}
	}

	private void reloadOnPropertyChange(PropertyInfo propertyInfo) throws Exception {
		if (!propertyInfo.isIsstatic() && propertyInfo.getObj() instanceof ReloadProperty) {
			try {
				((ReloadProperty) propertyInfo.getObj()).reloadOnPropertyChange();
				logger.info("reload object({}) success!", propertyInfo.getClazz().getSimpleName());
			} catch (Exception e) {
				throw new ServerException(e, "reload object({}) error!", propertyInfo.getClazz().getSimpleName());
			}
		}
	}

	private String getValue(String path) {
		return QconfClient.getString(path);
	}

	private boolean isStaticField(Field field) {
		return Modifier.isStatic(field.getModifiers());
	}

	public void initStaticClass(String prefix, Class<?> clazz) {
		Preconditions.checkNotNull(prefix, "config prefix must not be null!");
		Preconditions.checkNotNull(clazz, "config class must not be null!");
		logger.info("init static class({}) start!", clazz.getSimpleName());

		if (!prefix.startsWith("/")) {
			prefix = "/" + prefix;
		}
		if (prefix.length() > 1 && !prefix.endsWith("/")) {
			prefix = prefix + "/";
		}

		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			ConfigProperty configProperty = field.getAnnotation(ConfigProperty.class);
			if (configProperty == null || !isStaticField(field)) {
				continue;
			}
			Preconditions.checkArgument(StringUtils.isNotBlank(configProperty.path()), "%s.%s.path must be required!",
					clazz.getSimpleName(), field.getName());

			String path = configProperty.path();
			if (path.startsWith("/")) {
				path = StringUtils.substringAfter(path, "/");
			}
			path = prefix + path;

			PropertyInfo propertyInfo = new PropertyInfo(path, configProperty.required(), configProperty.json(), field,
					true, clazz, null);
			initPropertyInfo(propertyInfo);
			propertyInfos.add(propertyInfo);
		}
		logger.info("init static class({}) end!", clazz.getSimpleName());
	}

	public void initObject(String prefix, Object object) {
		Preconditions.checkNotNull(prefix, "config prefix must not be null!");
		Preconditions.checkNotNull(object, "config object must not be null!");
		Class<?> clazz = object.getClass();
		logger.info("init object({}) start!", clazz.getSimpleName());

		if (!prefix.startsWith("/")) {
			prefix = "/" + prefix;
		}
		if (prefix.length() > 1 && !prefix.endsWith("/")) {
			prefix = prefix + "/";
		}

		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			ConfigProperty configProperty = field.getAnnotation(ConfigProperty.class);
			if (configProperty == null || isStaticField(field)) {
				continue;
			}
			Preconditions.checkArgument(StringUtils.isNotBlank(configProperty.path()), "%s.%s.path must be required!",
					clazz.getSimpleName(), field.getName());

			String path = configProperty.path();
			if (path.startsWith("/")) {
				path = StringUtils.substringAfter(path, "/");
			}
			path = prefix + path;

			PropertyInfo propertyInfo = new PropertyInfo(path, configProperty.required(), configProperty.json(), field,
					false, clazz, object);
			initPropertyInfo(propertyInfo);
			propertyInfos.add(propertyInfo);
		}

		if (object instanceof ReloadProperty) {
			try {
				((ReloadProperty) object).reloadOnPropertyChange();
				logger.info("reload object({}) success!", clazz.getSimpleName());
			} catch (Exception e) {
				throw new ServerException(e, "reload object({}) error!", clazz.getSimpleName());
			}
		}
		logger.info("init object({}) end!", clazz.getSimpleName());
	}

	private void initPropertyInfo(PropertyInfo propertyInfo) {

		String value = null;
		try {
			value = getValue(propertyInfo.getPath());

			if (propertyInfo.isRequired()) {
				Preconditions.checkArgument(StringUtils.isNotBlank(value),
						"init property error! %s.%s is required! the %s has no value",
						propertyInfo.getClazz().getSimpleName(), propertyInfo.getField().getName(),
						propertyInfo.getPath());
			}

			setValue(value, propertyInfo);
		} catch (Exception e) {
			throw new ServerException(e, "init property error! {}.{} path:{}, value:{}",
					propertyInfo.getClazz().getSimpleName(), propertyInfo.getField().getName(), propertyInfo.getPath(),
					value);
		}
	}

	private void setValue(String strValue, PropertyInfo propertyInfo) throws Exception {
		Object result = null;
		Field field = propertyInfo.getField();
		Class<?> type = field.getType();

		propertyInfo.setRemoteValue(strValue);

		if (StringUtils.isNotBlank(strValue)) {

			strValue = StringTools.trimAll(strValue);
			if (propertyInfo.isJson()) {
				result = JsonUtils.fromJson(strValue, type);
				// result = JsonUtils.fromJson(strValue, new
				// TypeReference4Reflect<>(type));
			} else {
				if (type == Integer.class || type == int.class) {
					result = Integer.parseInt(strValue);
				} else if (type == String.class) {
					result = strValue;
				} else if (type == Long.class || type == long.class) {
					result = Long.parseLong(strValue);
				} else if (type == Double.class || type == double.class) {
					result = Double.parseDouble(strValue);
				} else if (type == Boolean.class || type == boolean.class) {
					result = Boolean.parseBoolean(strValue);
				} else if (type == Float.class || type == float.class) {
					result = Float.parseFloat(strValue);
				} else {
					throw new TypeMismatchException("not support type:{}", type);
				}
			}
		} else {
			if (propertyInfo.isJson()) {
				result = null;
			} else {
				if (type == String.class) {
					result = "";
				} else {
					throw new TypeMismatchException("not support type:{}", type);
				}
			}
		}

		field.setAccessible(true);
		if (propertyInfo.isIsstatic()) {
			Object oldValue = field.get(propertyInfo.getClazz());
			field.set(propertyInfo.getClazz(), result);
			logger.info("change static property success {}.{}={}, old:{}, path:{}",
					propertyInfo.getClazz().getSimpleName(), propertyInfo.getField().getName(),
					field.get(propertyInfo.getClazz()), oldValue, propertyInfo.getPath());
		} else {
			Object oldValue = field.get(propertyInfo.getObj());
			field.set(propertyInfo.getObj(), result);
			logger.info("change object property success {}.{}={}, old:{}, path:{}",
					propertyInfo.getClazz().getSimpleName(), propertyInfo.getField().getName(),
					field.get(propertyInfo.getObj()), oldValue, propertyInfo.getPath());
		}
		propertyInfo.setLocalValue(strValue);
	}

	private class TypeReference4Reflect<T> extends TypeReference<T> {
		TypeReference4Reflect(T t)
				throws IllegalArgumentException, IllegalAccessException, SecurityException, NoSuchFieldException {
			Class<?> cla = TypeReference.class;
			Field field = cla.getDeclaredField("_type");
			field.setAccessible(true);
			field.set(this, t);
		}
	}

}
