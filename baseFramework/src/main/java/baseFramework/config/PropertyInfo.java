package baseFramework.config;

import java.lang.reflect.Field;

/**
 * @author chao.li
 * @date 2016年12月15日
 */
class PropertyInfo {

	private String path;
	private boolean required;

	private boolean isJson;

	private Field field;
	private boolean isstatic;

	private Class<?> clazz;
	private Object obj;

	private String localValue;
	private String remoteValue;

	/**
	 * @param path
	 * @param required
	 * @param isJson
	 * @param jsonType
	 * @param field
	 * @param isstatic
	 * @param clazz
	 * @param obj
	 */
	public PropertyInfo(String path, boolean required, boolean isJson, Field field, boolean isstatic, Class<?> clazz,
			Object obj) {
		super();
		this.path = path;
		this.required = required;
		this.isJson = isJson;
		this.field = field;
		this.isstatic = isstatic;
		this.clazz = clazz;
		this.obj = obj;
	}

	public String getPath() {
		return path;
	}

	public boolean isRequired() {
		return required;
	}

	public boolean isJson() {
		return isJson;
	}

	public Field getField() {
		return field;
	}

	public boolean isIsstatic() {
		return isstatic;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public Object getObj() {
		return obj;
	}

	public String getLocalValue() {
		return localValue;
	}

	public void setLocalValue(String localValue) {
		this.localValue = localValue;
	}

	public String getRemoteValue() {
		return remoteValue;
	}

	public void setRemoteValue(String remoteValue) {
		this.remoteValue = remoteValue;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clazz == null) ? 0 : clazz.hashCode());
		result = prime * result + ((field == null) ? 0 : field.hashCode());
		result = prime * result + (isJson ? 1231 : 1237);
		result = prime * result + (isstatic ? 1231 : 1237);
		result = prime * result + ((obj == null) ? 0 : obj.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result + (required ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PropertyInfo other = (PropertyInfo) obj;
		if (clazz == null) {
			if (other.clazz != null)
				return false;
		} else if (!clazz.equals(other.clazz))
			return false;
		if (field == null) {
			if (other.field != null)
				return false;
		} else if (!field.equals(other.field))
			return false;
		if (isJson != other.isJson)
			return false;
		if (isstatic != other.isstatic)
			return false;
		if (this.obj == null) {
			if (other.obj != null)
				return false;
		} else if (!this.obj.equals(other.obj))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		if (required != other.required)
			return false;
		return true;
	}

}
