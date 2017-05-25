package sail.beans.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sail.beans.dao.GenericDao;
import sail.beans.entity.BatWorkOrder;
import sail.beans.entity.BatWorkOrderInput;

@Service
public class TransfromWorkCodeDataService {
	@Autowired
	private GenericDao genericDao;  
	
	public BatWorkOrder TransfromWorkOrder(BatWorkOrder batWorkOrder){
		try {
			List<BatWorkOrderInput> inputList = genericDao.getListWithVariableParas("transfrom.workorderteam.list", new Object[]{batWorkOrder.getPid()});
			if(inputList!=null&&inputList.size()>0){
				BatWorkOrderInput batWorkOrderInput =inputList.get(0);
				BatWorkOrderInput batWorkOrderInput1 =inputList.get(inputList.size()-1);
				batWorkOrder.setWorkteam(getWorkteam(batWorkOrderInput.getCreatetime()));
				batWorkOrder.setWorktime(getWorkClass(batWorkOrderInput.getCreatetime()));
				batWorkOrder.setActualstarttime(batWorkOrderInput.getCreatetime());
				batWorkOrder.setActualendtime(batWorkOrderInput1.getCreatetime());
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return batWorkOrder;
	}
	
	public String getWorkteam(String time){
		String workteam="";
		String sql="select  mestest.f_bat_getesbcode_byschedule('"+time+"','ZS',2) as ZSBZ from dual";
		List workteamList=this.genericDao.getListWithNativeSql(sql);
		if(workteamList!=null&&workteamList.size()>0){
			workteam=workteamList.get(0).toString();
		}
		return workteam;
	}
	
	public String getWorkClass(String time){
		String workClass="";
		String sql="select  mestest.f_bat_getesbcode_byschedule('"+time+"','ZS',1) as ZSBC from dual";
		List workteamList=this.genericDao.getListWithNativeSql(sql);
		if(workteamList!=null&&workteamList.size()>0){
			workClass=workteamList.get(0).toString();
		}
		return workClass;
	}
}
