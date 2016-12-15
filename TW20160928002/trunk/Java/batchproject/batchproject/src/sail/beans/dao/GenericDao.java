package sail.beans.dao;

/**
 * <p>类说明：</p>
 *      
 *    
 * <p>Copyright: Copyright (c) 2008</p>
 *    
 * @author mark
 * 2007-12-12
 *
 * @version 1.0
 */

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import sail.beans.dao.iml.PaginationSupport;

public interface GenericDao {
	/**
	 * 
	 * 功能说明： 增加或者修改数据库中记录。
	 * 
	 * @param obj
	 *            void
	 */
	public void save(Object obj);

	/**
	 * 
	 * 功能说明： 删除数据库中记录。
	 * 
	 * @param obj
	 *            void
	 */
	public void delete(Object obj);

	public void deleteById(final Class entity, Serializable id);

	public Object merge(Object entity);

	public void clear();
	public void flush();
	public void evict(Object obj);	
	/**
	 * 方法说明： 将给定的对象的状态复制到具有相同标识的持久化对象上。
	 * 
	 * @param entityName
	 * @param entity
	 * @return
	 */
	public Object merge(String entityName, Object entity);

	/**
	 * 
	 * 功能说明： 根据命名查询获取结果对象集。
	 * 
	 * @param queryName
	 * @return List
	 */
	public List getListDefault(String queryName);

	/**
	 * 
	 * 功能说明： 根据带参数的命名查询获取结果对象集。
	 * 
	 * @param queryName
	 * @param obj
	 * @return List
	 */
	public List getList(String queryName, Object obj);

	/**
	 * 
	 * 功能说明： 根据参数数组获取命名查询的结果对象集。
	 * 
	 * @param queryName
	 * @param objs
	 * @return List
	 */
	public List getList(String queryName, Object[] objs);

	public List getListWithIn(String queryName, String paramName, List value);
	
	/**
	 * 执行原生的sql语句
	 * @param sql
	 */
	public void executeNativeSql(final String sql);

	/**
	 * 
	 * 功能说明： 根据组合条件进行数据库查询，并返回结果集。 其中，objs中可能有NULL值，应将对应的HQL中的条件过滤掉。
	 * 
	 * @param queryName
	 * @param objs
	 * @return List
	 */
	public List getListWithVariableParas(String queryName, Object[] objs);
	
	public List getListWithNativeSql(final String nativeSql);

	public List getListWithNativeSql(String queryName, Object[] objs);
	
	
	
	public List getListWithNativeSql(String queryName, Object[] objs, String[] clazzNames);

	/**
	 * 
	 * 功能说明： 获取数据库实体对象。
	 * 
	 * @param entity
	 * @return List
	 */
	public List getAll(Class entity);

	/**
	 * 
	 * 功能说明： 根据对象ID获取对象。
	 * 
	 * @param entity
	 * @param object
	 * @return Object
	 */
	public Object getById(Class entity, Serializable id);


	
	/**
	 * 如果不存在queryName的命名查询,使用传入的hql查询
	 * @param queryName
	 * @param hql
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getListByHql(final String hql);
	
	/**
	 * 调用存储过程
	 * @param storageName 存储过程名称
	 * @param params 存储过程变量数组
	 * @author Xu Xinwei
	 * @Data 20091124
	 */	
	public void executeStorage(final String storageName,final String[]params);
	//调用无参数的存储过程add by lhb 20110721
	public void executeStorage(final String storageName);
	
	
	@SuppressWarnings("unchecked")
	public List getListWithVariableParasByHQL(final String hql, final Object[] values);
	
	@SuppressWarnings("unchecked")
	public List getListWithNamedParams(final String queryName, final Map<String, String> params);
	
	/**
	 * 获取Entity对象的主键名.
	 */
	// String getIdName(Class cls);
	/**
	 * 
	 * 功能说明： 根据组合查询条件获取结果集。
	 * 
	 * @param obj
	 * @return List
	 */
	// public List findByCriteria(DetachedCriteria obj);
	public void execute(final String hql);
	/**
	 * 
	 * 功能说明： 执行原生的UPDATE和DELETE等SQL命令。
	 * 
	 * @param queryName:命名查询的名字。该命名查询可以包含UPDATE和DELETE等SQL命令。
	 * @param values
	 *            void
	 */
	 public void execute(final String queryName, final Object[] values);
	/**
	 * 
	 * 功能说明： 执行原生的UPDATE和DELETE等SQL命令。
	 * 
	 * @param queryName:命名查询的名字。该命名查询可以包含UPDATE和DELETE等SQL命令。
	 * @param value
	 *            void
	 */
	 public void execute(final String queryName, final Object value);
	/**
	 * 
	 * 功能说明： 根据对象ID获取对象。
	 * 
	 * @param entity
	 * @param object
	 * @return Object
	 */
	// public Object loadById(final Class entity, Serializable obj);
}
