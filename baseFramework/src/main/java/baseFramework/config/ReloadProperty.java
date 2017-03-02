package baseFramework.config;

/**
 * @author chao.li
 * @date 2016年12月16日
 */
public interface ReloadProperty {

	/**
	 * 有错误请抛出来<br>
	 * 程序结构：<br>
	 * 1.加载zk数据并转换<br>
	 * 2.赋值到temp<br>
	 * 3.批量替换原有的属性<br>
	 * <br>
	 * 不要首先就修改原有属性，否则一旦zk数据不规范抛出错误后，会影响现有系统。<br>
	 * 
	 */
	public void reloadOnPropertyChange() throws Exception;
}
