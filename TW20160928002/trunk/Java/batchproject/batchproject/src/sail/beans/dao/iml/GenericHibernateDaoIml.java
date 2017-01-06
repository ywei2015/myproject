package sail.beans.dao.iml;

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
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.hql.internal.classic.QueryTranslatorImpl;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import sail.beans.dao.GenericDao;
import sail.beans.dao.iml.cacheQueryCount.CountCache;
import sail.beans.dao.iml.cacheQueryCount.SqlParams;
import sail.beans.exception.BusinessException;

@Repository("GenericDao")
public class GenericHibernateDaoIml implements GenericDao {
	
	
	private SessionFactory sessionFactory;
	
	



	

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * 从cache中得到记录总数
	 * @param queryString
	 * @param params
	 * @param values
	 * @return
	 */
	private Long getCount4Cache(String queryString, final String[] params,
			final Object[] values) {
		SqlParams sql = new SqlParams(queryString, params, values);
		Long l = CountCache.getInstance().get(sql);
		return l;
	}

	/**
	 * 把记录总数加入cache中
	 * @param queryString
	 * @param params
	 * @param values
	 * @param count
	 * @param beginExecTime
	 */
	private void setCount4Cache(String queryString, final String[] params,
			final Object[] values, int count, long beginExecTime) {
		SqlParams sql = new SqlParams(queryString, params, values);
		Long l = new Long(count);
		CountCache.getInstance().set(sql, l, beginExecTime);
	}


	/**
	 * 
	 * 方法说明： 增加或者修改数据库中记录。
	 * 
	 * @param vo_obj
	 *            void
	 */
	public void save(Object obj) {
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(obj);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new BusinessException("保存出错。");
		}
	}
	
	/**
	 * 调用存储过程
	 * @param storageName 存储过程名称
	 * @param params 存储过程变量数组
	 * @author Xu Xinwei
	 * @Data 20091124
	 */	
	public void executeStorage(final String storageName,final String[]params){
		//参数判断
		if(StringUtils.isBlank(storageName)||null==params){
			throw new BusinessException("传入参数错误");
		}
		//参数格式化
		final String param=paramInit(params);
		sessionFactory.getCurrentSession().doWork(new Work() {
			public void execute(Connection connection) {                  
                String sql = "{call "+storageName+"("+param+")}"; 
                CallableStatement stmt;
				try {
					stmt = connection.prepareCall(sql);
					for(int i=1;i<=params.length;i++){
		               stmt.setString(i, params[i-1]);
		            }
					stmt.execute();    
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
               
			}  
		});
		
	}
	

	

	/**
	 * 调用存储过程
	 * @param storageName 存储过程名称
	 * @param 不带参数
	 * @author lhb
	 * @Data 20110721
	 */	
	public void executeStorage(final String storageName){
		sessionFactory.getCurrentSession().doWork(new Work() {
			public void execute(Connection connection) {
				String sql = "{call "+storageName+"()}"; 
		        CallableStatement stmt;
				try {
					stmt = connection.prepareCall(sql);
					stmt.execute();   
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}  
		});
	}
	/**
	 * @param params
	 */
	private String paramInit(String[] params) {
		StringBuffer pa=new StringBuffer();
		for(int i=0;i<params.length;i++){
			pa.append("?,");
		}
		if(pa.length()>0){
			pa=pa.deleteCharAt(pa.length()-1);
		}
		return pa.toString();
	}

	public void clear() {
		try {
			sessionFactory.getCurrentSession().clear();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new BusinessException("保存出错。");
		}
	}

	public void evict(Object obj) {
		try {
			sessionFactory.getCurrentSession().evict(obj);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new BusinessException("保存出错。");
		}
	}
	
	public void flush() {
		try {
			sessionFactory.getCurrentSession().flush();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new BusinessException("保存出错。");
		}
	}	
	/**
	 * 
	 * 方法说明： 删除数据库中记录。
	 * 
	 * @param vo_obj
	 *            void
	 */

	public void delete(Object obj) {
		sessionFactory.getCurrentSession().delete(obj);
	}

	public void deleteById(final Class entity, Serializable id) {
		sessionFactory.getCurrentSession().get(entity, id);
	}

	/**
	 * 方法说明： 将给定的对象的状态复制到具有相同标识的持久化对象上。
	 * 
	 * @param entityName
	 * @param entity
	 * @return
	 */
	public Object merge(String entityName, Object entity) {
		return sessionFactory.getCurrentSession().merge(entityName, entity);
	}

	/**
	 * 方法说明： 将给定的对象的状态复制到具有相同标识的持久化对象上。
	 * 
	 * @param entity
	 * @return
	 */
	public Object merge(Object entity) {
		return sessionFactory.getCurrentSession().merge(entity);
	}

	/**
	 * 
	 * 方法说明： 根据命名查询获取结果对象集。
	 * 
	 * @param queryName
	 * @return List: 返回所有符合条件的POJO对象集合
	 */
	public List getListDefault(String queryName) {
		try {
			//return getHibernateTemplate().findByNamedQuery(queryName);
			return sessionFactory.getCurrentSession().getNamedQuery(queryName).list();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new BusinessException("查询出错。");
		}

	}

	/**
	 * 执行原生的sql语句
	 * 
	 * @param sql
	 */
	public void executeNativeSql(final String sql) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.executeUpdate();
	}
	
	/**
	 * 
	 * 方法说明： 根据带参数的命名查询获取结果对象集。
	 * 
	 * @param queryName
	 * @param vo_obj
	 * @return List 返回所有符合条件的POJO对象集合
	 */
	public List getList(String queryName, Object obj) {
		
		final String sql = filterContion(queryName, new Object[]{obj});
		final Object[] objects = new Object[]{obj};
		Query queryObject = sessionFactory.getCurrentSession().createQuery(sql);
		if (objects != null) {
			for (int i = 0; i < objects.length; i++) {
				queryObject.setParameter(i, objects[i]);
			}
		}
		List list = queryObject.list();
		return list;

	}

	/**
	 * 
	 * 方法说明： 根据参数数组获取命名查询的结果对象集。
	 * 
	 * @param queryName
	 * @param objs
	 * @return List 返回所有符合条件的POJO对象集合
	 */
	public List getList(String queryName, Object[] objs) {
		final String sql = filterContion(queryName, objs);
		final Object[] objects = filterNull(objs);

		Query queryObject = sessionFactory.getCurrentSession().createQuery(sql);

		if (objs != null) {
			for (int i = 0; i < objects.length; i++) {
				queryObject.setParameter(i, objects[i]);
			}
		}
		List list = queryObject.list();
		return list;
	}

	/**
	 * 
	 * 方法说明： 对于查询参数中含有In语句的HQL，请调用本方法进行查询。 获取命名查询的结果对象集。
	 * 
	 * @param queryName
	 * @param paramName:参数名
	 * @param value:参数值。In中的所有可能的参数值设置为一个List对象传入即可。
	 * @return List 返回所有符合条件的POJO对象集合
	 */

	public List getListWithIn(final String queryName, final String paramName,
			final List value) {
		Session session = sessionFactory.getCurrentSession();
		String sql = sessionFactory.getCurrentSession().getNamedQuery(queryName).getQueryString();
		Query queryObject = session.createQuery(sql);
		queryObject.setParameterList(paramName, value);
		List list = queryObject.list();
		return list;
	}

	/**
	 * 
	 * 方法说明： 根据组合条件进行数据库查询，并返回结果集。 其中，objs中可能有NULL值，应将对应的HQL中的条件过滤掉。
	 * 
	 * @param queryName
	 * @param objs
	 * @return List 返回所有符合条件的POJO对象集合
	 */
	public List getListWithVariableParas(final String queryName,
			final Object[] objs) {
		final String sql = filterContion(queryName, objs);
		final Object[] objects = filterNull(objs);

		Query queryObject = sessionFactory.getCurrentSession().createQuery(sql);

		if (objs != null) {
			for (int i = 0; i < objects.length; i++) {
				queryObject.setParameter(i, objects[i]);
			}
		}
	return queryObject.list();

	}
	
	public List getListWithNativeSql(final String nativeSql) {
		Query queryObject = sessionFactory.getCurrentSession().createSQLQuery(nativeSql);
		return queryObject.list();

	}	
	
	
	
	
	
	
	/**
	 * 
	 * 方法说明： 过滤掉HQL中的无效条件，主要指objs中可能有NULL值， 应将对应的HQL中的条件过滤掉。
	 * 
	 * @param queryName
	 * @param objs
	 * @return String
	 */
	private String filterContion(final String queryName, final Object[] objs, final String tableName) {

		String sql = sessionFactory.getCurrentSession().getNamedQuery(queryName).getQueryString();
		if (operator.size() < 2) {
			initMap();
		}

		return process(preSql(sql), objs).replace("table_name", tableName);
	}

	/**
	 * 
	 * 方法说明： 根据组合条件进行数据库查询，并返回结果集。 其中，objs中可能有NULL值，应将对应的HQL中的条件过滤掉。
	 * 
	 * @param queryName
	 * @param objs
	 * @return List 返回所有符合条件的POJO对象集合
	 */

	public List getListWithNativeSql(final String queryName, final Object[] objs) {
		final String sql = filterContion(queryName, objs);
		final Object[] objects = filterNull(objs);
		String sql_=filterSql(sql,objs);
		Query queryObject = sessionFactory.getCurrentSession().createSQLQuery(sql_);

		if (objs != null) {
			for (int i = 0; i < objects.length; i++) {
				queryObject.setParameter(i, objects[i]);
			}
		}
		return queryObject.list();

	}
	
	public String filterSql(String sql,Object[] objs){
		if(objs[0]==null){
			String sqlj=sql.substring(sql.indexOf("where")+5, sql.indexOf("?")+5);
			sql=sql.replace(sqlj,"");
		}
			
		return sql;
	}

	public List getListWithNativeSql(final String queryName,
			final Object[] objs, final String[] clazzNames) {
		final String sql = filterContion(queryName, objs);
		final Object[] objects = filterNull(objs);

		Query queryObject = sessionFactory.getCurrentSession().createSQLQuery(sql);

		if (clazzNames != null && clazzNames.length > 0) {
			for (int i = 0; i < clazzNames.length; i++) {
				((SQLQuery) queryObject).addEntity(clazzNames[i]);
			}
		}

		if (objs != null) {
			for (int i = 0; i < objects.length; i++) {
				queryObject.setParameter(i, objects[i]);
			}
		}

		return queryObject.list();

	}

	/**
	 * 
	 * 方法说明： 过滤掉HQL中的无效条件，主要指objs中可能有NULL值， 应将对应的HQL中的条件过滤掉。
	 * 
	 * @param queryName
	 * @param objs
	 * @return String
	 */
	private String filterContion(final String queryName, final Object[] objs) {

		String sql = sessionFactory.getCurrentSession().getNamedQuery(queryName).getQueryString();
		if (operator.size() < 2) {
			initMap();
		}

		return process(preSql(sql), objs);
	}
	
	private String filterContionForHql(final String hql, final Object[] objs) {
		if (operator.size() < 2) {
			initMap();
		}
		return process(preSql(hql), objs);
	}

//	/**
//	 * 
//	 * 方法说明： 获取数据库实体对象。
//	 * 
//	 * @param clazz
//	 * @return List 返回数据库中所有该类的对象集合。
//	 */
//	public List getAll(Class clazz) {
//		return getHibernateTemplate().loadAll(clazz);
//	}
//
//	/**
//	 * 
//	 * 方法说明： 根据组合查询条件获取结果集。
//	 * 
//	 * @param obj
//	 * @return List
//	 */
//	public List findByCriteria(DetachedCriteria obj) {
//		return getHibernateTemplate().findByCriteria(obj);
//	}

	public void execute(final String hql){
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.executeUpdate();
	}
	/**
	 * 
	 * 方法说明： 执行原生的UPDATE和DELETE等SQL命令。
	 * 
	 * @param queryString
	 * @param value
	 *            void
	 */
	public void execute(final String queryName, final Object[] values) {
		Query query = sessionFactory.getCurrentSession().getNamedQuery(queryName);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		query.executeUpdate();
	}

	/**
	 * 
	 * 方法说明： 执行原生的UPDATE和DELETE等SQL命令。
	 * 
	 * @param queryString
	 * @param value
	 *            void
	 */
	public void execute(final String queryName, final Object value) {
		execute(queryName, new Object[] { value });
	}

	/**
	 * 
	 * 方法说明： 根据对象ID获取对象。
	 * 
	 * @param clazz
	 * @param object
	 * @return Object
	 */
	public Object getById(Class clazz, Serializable id) {
		return sessionFactory.getCurrentSession().get(clazz, id);

	}

	/**
	 * =========================================================================
	 * 根据命名查询中的参数名和参数值，取得一页数据
	 * 
	 * =========================================================================
	 */

	


	
	

	

	

	
	@SuppressWarnings("unchecked")
	public List getListByHql(String hql) {
		try {
			return sessionFactory.getCurrentSession().createQuery(hql).list();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new BusinessException("查询出错。");
		}
	}
	
	

	@SuppressWarnings("unchecked")
	public List getListWithVariableParasByHQL(final String hql, final Object[] values){
		final String sql = filterContionForHql(hql, values);
		final Object[] objects = filterNull(values);

		Query queryObject = sessionFactory.getCurrentSession().createQuery(sql);

		if (values != null) {
			for (int i = 0; i < objects.length; i++) {
				queryObject.setParameter(i, objects[i]);
			}
		}
		return queryObject.list();
	}
	
	@SuppressWarnings("unchecked")
	public List getListWithNamedParams(final String queryName, final Map<String, String> params){
		Session session = sessionFactory.getCurrentSession();
		String sql = session.getNamedQuery(queryName).getQueryString();
		Query queryObject = null;
		if(sql.contains("*+*")){
			queryObject=session.createSQLQuery(sql);
		}else{
			queryObject=session.createQuery(sql);
		}
		
		Iterator<String> iterator = params.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			String param = params.get(key);
			if(param.indexOf(':') > 0){
				queryObject.setParameterList(key, param.split(":"));
			}
			else{
				queryObject.setParameter(key, param);
			}
		}
		
		List list = queryObject.list();
		return list;
	}
	/**
	 * 方法说明： 生成查询记录集条数的查询。
	 * 
	 * @param hql
	 * @param sessionFactory
	 * @return
	 */
	private String getCountSql(String hql,
			org.hibernate.SessionFactory sessionFactory) {
		if (hql.indexOf("*") == -1) {
			QueryTranslatorImpl queryTranslator = new QueryTranslatorImpl(
					hql,
					hql,
					Collections.EMPTY_MAP,
					(SessionFactoryImplementor) sessionFactory);

			queryTranslator.compile(Collections.EMPTY_MAP, false);

			return "select count(1) from (" + queryTranslator.getSQLString()
					+ ") tmp_count_t";
		} else {
			System.out
					.println("select count(1) from (" + hql + ") tmp_count_t");
			return "select count(1) from (" + hql + ") tmp_count_t";
		}
	}

	private String getSql(String hql,
			org.hibernate.SessionFactory sessionFactory) {
		QueryTranslatorImpl queryTranslator = new QueryTranslatorImpl(hql, hql,
				Collections.EMPTY_MAP,
				(SessionFactoryImplementor) sessionFactory);

		queryTranslator.compile(Collections.EMPTY_MAP, false);

		return queryTranslator.getSQLString();
	}

	/**
	 * 方法说明： 根据原始查询，获取该查询所取得的记录总数。
	 * 
	 * @param session
	 * @param queryString
	 * @param params
	 * @param values
	 * @return
	 */
	private int getCount(Session session, String queryString,
			final String[] params, final Object[] values) {
		int ret = 0;
		//从cache中得到记录总数
		Long lCountCache = getCount4Cache(queryString,params,values); 
		long beginTime = System.currentTimeMillis();
		if(lCountCache != null ){
			return lCountCache.intValue();
		}
		

		String sql = getCountSql(queryString, session.getSessionFactory());


		Query countQuery = session.createSQLQuery(sql);

		if (values != null) {
			for (int j = 0; j < values.length; j++) {
				countQuery.setParameter(params[j], values[j]);
			}
		}

		ret = (int) ((BigDecimal) countQuery.list().iterator().next())
				.doubleValue();

		//把记录总数加入cache中
		setCount4Cache(queryString, params, values, ret, beginTime);
		return ret;
	}

	private int getCount(Session session, String queryString,
			final Object[] values) {
		int ret = 0;
		//从cache中得到记录总数
		Long lCountCache = getCount4Cache(queryString, null, values);
		long beginTime = System.currentTimeMillis();
		if(lCountCache != null ){
			return lCountCache.intValue();
		}
		

		Object[] objects = filterNull(values);
		String sql = getCountSql(queryString, session.getSessionFactory());


		Query countQuery = session.createSQLQuery(sql);

		if (objects != null) {
			for (int j = 0; j < objects.length; j++) {
				System.out.println("=============================" + j);
				countQuery.setParameter(j, objects[j]);
			}
		}

		ret = (int) ((BigDecimal) countQuery.list().iterator().next())
				.doubleValue();

		//把记录总数加入cache中
		setCount4Cache(queryString, null, values, ret, beginTime);
		return ret;
	}

	private List getResultList(Session session, String queryString,
			final Object[] values) {

		List ret;
		Object[] objects = filterNull(values);
		String sql = getSql(queryString, session.getSessionFactory());


		Query countQuery = session.createSQLQuery(sql);

		if (objects != null) {
			for (int j = 0; j < objects.length; j++) {
				System.out.println("=============================" + j);
				countQuery.setParameter(j, objects[j]);
			}
		}

		ret = countQuery.list();

		return ret;
	}

	private static int count(String sql) {
		int result = 0;
		for (int i = 0; i < sql.length(); i++) {
			if ((sql.charAt(i) + "").equals("?")) {
				result++;
			}
		}

		return result;
	}

	private static String process(String sourceSql, Object[] objects) {
		String source = sourceSql;
		String middle = sourceSql;
		String result = "";
		ArrayList ar = new ArrayList();
		ArrayList ar1 = new ArrayList();

		while (middle.indexOf("[") != -1) {
			String sql = middle.substring(middle.indexOf("[") + 1, middle
					.indexOf("]"));

			String sql2 = middle.substring(middle.indexOf("]") + 1, middle
					.indexOf("[", middle.indexOf("[") + 1) > -1 ? middle
					.indexOf("[", middle.indexOf("[") + 1) : middle.length());
			ar1.add(sql2);
			StringBuffer bf = new StringBuffer(sql);
			ar.add(bf);
			middle = middle.substring(middle.indexOf("]") + 1);
		}

		int count1 = 0;
		for (int i = 0; i < ar.size(); i++) {
			String sql = ar.get(i).toString();

			int count = count(sql);

			for (int j = count1; j < count + count1; j++) {
				
				if (objects[j] == null || "".equals(objects[j])) {
					sql = "1=1";
				}

				if (sql.toLowerCase().indexOf(" in ") != -1) {
					sql = sql.replaceAll("\\?", objects[j] + "");

					objects[j] = null;
				}
			}

			count1 += count;
			result += sql + ar1.get(i);
		}
		String s1 = null;
		if (source.indexOf("[") > -1) {
			s1 = source.substring(0, source.indexOf("["));
		} else {
			s1 = source;
		}
		return s1 + result;

	}

	private static Object[] filterNull(Object[] objects) {

		if (objects == null) {
			return null;
		}

		ArrayList ar = new ArrayList();
		for (int i = 0; i < objects.length; i++) {
			if (objects[i] != null && !"".equals(objects[i])) {
				ar.add(objects[i]);
			}
		}

		return ar.toArray();

	}

	private static HashMap operator = new HashMap();

	private static String preSql(String sql) {
		String where = sql.substring(sql.toLowerCase().indexOf("where"));
		String allSql = sql.substring(0, sql.toLowerCase().indexOf("where"));
		String[] wheres = where.split(" ");

		for (int i = wheres.length - 1; i > 0; i--) {
			if (wheres[i].trim().equals("?")) {
				Object temp1 = operator.get(wheres[i - 1].toLowerCase());
				int[] temp;
				if (temp1 == null) {
					temp1 = operator.get(wheres[i - 2].toLowerCase());
				}

				if (temp1 != null) {
					temp = (int[]) temp1;
					wheres[i + temp[0]] = wheres[i + temp[0]] + "]";
					wheres[i - temp[1]] = "[" + wheres[i - temp[1]];

				}
			}

		}

		allSql = allSql + getString(wheres);

		return allSql;

	}

	public static String getString(String[] args) {
		String result = "";
		if (args == null) {
			return result;
		}
		for (int i = 0; i < args.length; i++) {
			result += args[i] + " ";
		}

		return result;
	}

	private static void initMap() {
		operator.put("=", new int[] { 0, 2 });
		operator.put("<>", new int[] { 0, 2 });
		operator.put("!=", new int[] { 0, 2 });
		operator.put(">", new int[] { 0, 2 });
		operator.put(">=", new int[] { 0, 2 });
		operator.put("<", new int[] { 0, 2 });
		operator.put("<=", new int[] { 0, 2 });
		operator.put("like", new int[] { 0, 2 });
		operator.put("between", new int[] { 2, 2 });
		operator.put("in", new int[] { 1, 3 });
	}

	@Override
	public List getAll(Class entity) {
		// TODO Auto-generated method stub
		return null;
	}


}
