package tw.business.service;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.business.entity.equ.EquRepairform;
import tw.business.entity.equ.EquWbtask;
import tw.sysbase.dao.GenericDao;

@Service
@Transactional
public class ATestService {

	private static SimpleDateFormat dateformat19=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	@Autowired
	private GenericDao genericDao;	
	/**
	 * getlist
	 * @param none
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<EquWbtask> getlist() { 
		//SELECT T FROM tw.business.entity.equ.EquWbtaskDefine T WHERE T.sysFlag='1' and 
		String hql ="SELECT T FROM tw.business.entity.equ.EquWbtask T WHERE T.sysFlag='1' and T.planBegintime>'2018-01-17 00:01:02'"; 
		return genericDao.getListByHql(hql); 
	}
 
	public List<EquRepairform> getrepairlist(){
		String hql ="SELECT T FROM tw.business.entity.equ.EquRepairform T WHERE 1=1 "+
				"AND T.sysFlag='1' and T.status =? "; //and T.receiveTime>? and T.receiveTime<?"; // 
				//"AND T.sysFlag='1' and T.receiveTime>? and T.receiveTime<? and T.equName like '%?%' and T.status =? ";
		Object [] values = new Object[2];
		String st = "2018-01-16 00:00:00";
//		values[0] = st; // dateformat19.parse(st);
		String et = "2018-01-30 23:59:59";
//		values[1] = et; //dateformat19.parse(et);
		//values[2] = "1"; //null; //
		//values[3] = "40";//null; //
/*
		values[0] = "1"; //null; //
		values[1] = "40";//null; //
		return genericDao.getObjectsWithSql(hql, values); */
		Query query = genericDao.getSession().createQuery(hql);
		//query.setDate(0, dateformat19.parse(st));
		//query.setString(0, "1");
		query.setString(0, "40");
		return query.list();
	}
}
