package baseFramework.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

/**
 * @author chao.li
 * @date 2017年3月21日
 */
public class BeanUtils {

	public static <T> T mapToObject(Map<String, Object> map, Class<T> beanClass) throws Exception {
		if (map == null || map.size() <= 0)
			return null;

		T obj = beanClass.newInstance();

		List<Field> fields = getAllField(obj.getClass());
		for (Field field : fields) {
			field.setAccessible(true);
			field.set(obj, map.get(field.getName()));
		}

		return obj;
	}

	public static Map<String, Object> objectToMap(Object obj) throws Exception {
		if (obj == null) {
			return null;
		}

		Map<String, Object> map = new HashMap<String, Object>();

		List<Field> fields = getAllField(obj.getClass());
		for (Field field : fields) {
			field.setAccessible(true);
			map.put(field.getName(), field.get(obj));
		}

		return map;
	}

	public static List<Field> getAllField(Class<?> clazz) {

		List<Field> fields = Lists.newArrayList();

		// 获取关联的所有类，本类以及所有父类
		while (true) {

			if (clazz == null || clazz == Object.class)
				break;

			Field[] declaredFields = clazz.getDeclaredFields();
			for (Field field : declaredFields) {
				int mod = field.getModifiers();
				// 过滤 static 和 final 类型
				if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
					continue;
				}
				fields.add(field);
			}

			clazz = clazz.getSuperclass();
		}

		return fields;
	}
}
