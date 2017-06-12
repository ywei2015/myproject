package sail.beans.service;

import java.util.ArrayList;
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
	/**
	 * 根据制丝工单投料记录为工单加上班次班组，实际开始结束时间
	 * */
/*	public BatWorkOrder TransfromWorkOrder(BatWorkOrder batWorkOrder){
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
	}*/
	
	public List<BatWorkOrder> TransfromWorkOrder(BatWorkOrder batWorkOrder){
		List<BatWorkOrder> batWorkOrderList=new ArrayList<BatWorkOrder>();
		try {
			List<BatWorkOrderInput> inputList = genericDao.getListWithVariableParas("transfrom.workorderteam.list", new Object[]{batWorkOrder.getPid()});
			if(inputList!=null&&inputList.size()>0){
				BatWorkOrderInput batWorkOrderInput =inputList.get(0);
				BatWorkOrderInput batWorkOrderInput1 =inputList.get(inputList.size()-1);
				batWorkOrder.setWorkteam(getWorkteam(batWorkOrderInput.getCreatetime()));
				batWorkOrder.setWorktime(getWorkClass(batWorkOrderInput.getCreatetime()));
				batWorkOrder.setActualstarttime(batWorkOrderInput.getCreatetime());
				batWorkOrder.setActualendtime(batWorkOrderInput1.getCreatetime());
				batWorkOrderList.add(batWorkOrder);
				//zp03与zp13相同的班次班组，由于zp13没有投料记录，所以处理zp03时同时处理zp13的工单
				if(batWorkOrder.getWorkordertype().equalsIgnoreCase("ZP03")){
					String workorder=batWorkOrder.getWorkordercode().replace("ZP03", "ZP13");
					List<BatWorkOrder> batWorkOrderList1 = genericDao.getListWithVariableParas("WORKORDER.T_BAT_WORKORDERLIST.LIST", new Object[]{workorder});
					if(batWorkOrderList1.size()>0){
						BatWorkOrder batWorkOrder1=batWorkOrderList1.get(0);
						batWorkOrder1.setWorkteam(getWorkteam(batWorkOrderInput.getCreatetime()));
						batWorkOrder1.setWorktime(getWorkClass(batWorkOrderInput.getCreatetime()));
						batWorkOrder1.setActualstarttime(batWorkOrderInput.getCreatetime());
						batWorkOrder1.setActualendtime(batWorkOrderInput1.getCreatetime());
						batWorkOrderList.add(batWorkOrder1);
					}
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return batWorkOrderList;
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
