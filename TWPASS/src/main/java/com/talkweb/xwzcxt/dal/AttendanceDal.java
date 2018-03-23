package com.talkweb.xwzcxt.dal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.talkweb.twdpe.dal.batis.spring.SessionDaoSupport;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.pojo.AttendancePojo;

public class AttendanceDal extends SessionDaoSupport {
	public List<AttendancePojo> getAttendanceData(Map attendancePojo, Pagination page){
		return this.getSession().selectList("attendance.getAttendanceData",attendancePojo,page);
	}
   public int  getAttendanceTimes(Map map){
	   return (Integer) this.getSession().selectOne("attendance.getAttendanceTimes",map);
   }
  public String getdepName(Map map) {
	return (String) this.getSession().selectOne("attendance.getdepName",map);
  }

  public  List<AttendancePojo> getAttendanceDetails(Map map,Pagination page){
	return (List<AttendancePojo>)this.getSession().selectList("attendance.getAttendanceDetails",map,page);
  }
public List<AttendancePojo> exportAttendance(Map map) {
	return this.getSession().selectList("attendance.exportAttendance",map);
}
public Pagination getAttenForMobile(Map params, Pagination pagination) {
	List li = this.getSession().selectList("attendance.getAttenForMobile", params, pagination);
	if (li != null)
		pagination.setList(li);
	return pagination;
}
public void doKaoqin(Map user) {
	// TODO Auto-generated method stub
    this.getSession().insert("attendance.doKaoqin",user);
}
public HashMap getKaoPB(Map map) {
	// TODO Auto-generated method stub
	return (HashMap)this.getSession().selectOne("attendance.getKaoPB", map);
}
//获取移动端考勤ip
public ArrayList<String> getAddr() {
	// TODO Auto-generated method stub
	return (ArrayList)this.getSession().selectList("getAddr");
}
//获取web端mac地址
public List<String> getKaoMac() {
	// TODO Auto-generated method stub
	List list= this.getSession().selectList("getKaoMac");
	  return  list;
}
public List<Map> getAttendanceForMap(Map map) {
	return (List)this.getSession().selectList("attendance.getAttendanceForMap",map);
}
public List<AttendancePojo> getAttendanceForMapGroup(Map map){
	return (List)this.getSession().selectList("attendance.getAttendanceForMapGroup",map);
}
  
}


