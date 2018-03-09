package tw.business.service.workcalendar;

import java.math.BigInteger;

import javax.annotation.Resource;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.sysbase.dao.GenericDao;

/**
 * 工作日历
 * @author XSH 
 */
@Service
@Transactional
public class WorkCalendarService {

	@Resource
	private GenericDao genericDao;
	
	/**
	 * 判断某日期是否为工件 日 
	 * @param calendaytype 日历类别0-通用
	 * @param thedate 日期
	 * @return 
	 */
	public boolean isWorkDay(String calendaytype, String thedate){
		if(calendaytype==null||"".equals(calendaytype)) calendaytype = "0";
		String sql = "select count(1) as N from t_calendar t where t.F_DATE='"+thedate+"' and t.F_CALENDAR_TYPE='"+calendaytype+"' and t.F_FACT_ISWORKDAY=1";
		SQLQuery query =  genericDao.getSession().createSQLQuery(sql);
		BigInteger n = (BigInteger) query.uniqueResult();  
		return n.intValue()>0?true:false;
	}
}
