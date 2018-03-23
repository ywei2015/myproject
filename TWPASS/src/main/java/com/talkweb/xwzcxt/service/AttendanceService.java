package com.talkweb.xwzcxt.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.pojo.AttendancePojo;

public interface AttendanceService {
	public List<AttendancePojo> getAttendanceData(Map attendancePojo, Pagination page);
	public int getAttendanceTimes(Map map);
	public String getdepName(Map map);
	public List<AttendancePojo> getAttendanceDetails(Map map,
			Pagination pagination);
	public List<AttendancePojo> exportAttendance(Map map);
	public Pagination getAttenForMobile(Map params, Pagination pagination);
	public void doKaoqin(Map user);
	public HashMap getKaoPB(Map map);
	public ArrayList<String> getAddr();
	public List<String> getKaoMac();
	public List<Map> getAttendanceForMap(Map map);
	public List<AttendancePojo> getAttendanceForMapGroup(Map map);
}