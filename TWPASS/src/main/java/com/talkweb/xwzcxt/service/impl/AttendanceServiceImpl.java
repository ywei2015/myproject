package com.talkweb.xwzcxt.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.dal.AttendanceDal;
import com.talkweb.xwzcxt.pojo.AttendancePojo;
import com.talkweb.xwzcxt.service.AttendanceService;

public class AttendanceServiceImpl implements AttendanceService {
	@Autowired
	private AttendanceDal attendanceDal;

	@Override
	public List <AttendancePojo> getAttendanceData(Map map, Pagination page) {
		// TODO Auto-generated method stub
		return attendanceDal.getAttendanceData( map,page);

	}

	@Override
	public int getAttendanceTimes(Map map) {
		return attendanceDal.getAttendanceTimes(map);
	}

	@Override
	public String getdepName(Map map) {
		return attendanceDal.getdepName(map);
	}
         
	@Override
	public List<AttendancePojo> getAttendanceDetails(Map map,
			Pagination pagination) {
		return attendanceDal.getAttendanceDetails(map,pagination);
	}

	@Override
	public List<AttendancePojo> exportAttendance(Map map) {
		return attendanceDal.exportAttendance(map);
	}

	@Override
	public Pagination getAttenForMobile(Map params, Pagination pagination) {
		return attendanceDal.getAttenForMobile(params,pagination);
	}

	@Override
	public void doKaoqin(Map user) {
		// TODO Auto-generated method stub
		attendanceDal.doKaoqin(user);
	}

	@Override
	public HashMap getKaoPB(Map map) {
		// TODO Auto-generated method stub
		return attendanceDal.getKaoPB(map);
	}

	@Override
	public ArrayList<String> getAddr() {
		// TODO Auto-generated method stub
		return attendanceDal.getAddr();
	}

	@Override
	public List<String> getKaoMac() {
		// TODO Auto-generated method stub
		return attendanceDal.getKaoMac();
	}

	@Override
	public List<Map> getAttendanceForMap(Map map) {
		return attendanceDal.getAttendanceForMap(map);
	}

	@Override
	public List<AttendancePojo> getAttendanceForMapGroup(Map map) {
		return attendanceDal.getAttendanceForMapGroup(map);
	}

}