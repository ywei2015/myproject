package sail.beans.service.transfromdata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sail.beans.dao.GenericDao;
import sail.beans.entity.BatWorkOrder;
import sail.beans.entity.BatWorkOrderOutput;
import sail.beans.service.SpcQmsBatchDataService;
import sail.beans.support.DateBean;

@Service
public class TransformProduceDateService {
	@Autowired
	private GenericDao genericDao;
	@Autowired
	private SpcQmsBatchDataService spcQmsBatchDataService;
	
	private String taskday=DateBean.getBeforDay(DateBean.getSysdate(), 1);

	/**
	 * 卷包成型生产转储
	 * */
	@Transactional(rollbackFor=Exception.class) 
	public void transformJBProduceDate(String type) {
		String ztflag,billno,workpid;
		Map yzMess;
		try{
			List<Object[]> list_ZS=genericDao.getListWithNativeSql("transfrom.produceJBData.list",new Object[]{type,taskday,3});
			if(list_ZS!=null&&list_ZS.size()>0){
				for (int i = 0; i < list_ZS.size(); i++) {
					 Object[] jizu=list_ZS.get(i);
					 yzMess=getvltdetails(jizu);
					 if(!"1".equals(yzMess.get("flag").toString()))
						 continue;
					 BatWorkOrder batWorkOrder=(BatWorkOrder) yzMess.get("workorder");
					 BatWorkOrderOutput batWorkOrderOutput=new BatWorkOrderOutput();
					 batWorkOrderOutput.setWorkorderpid(batWorkOrder.getPid());
					 if(jizu[11]!=null)
						 batWorkOrderOutput.setQuantity(Double.parseDouble(jizu[11].toString()));
					 batWorkOrderOutput.setMatbatch(batWorkOrder.getWorkordercode());
					 if(batWorkOrder.getUnit()==null||batWorkOrder.getUnit().trim().length()==0)
						 batWorkOrderOutput.setUnit("PAN");
					 else 
						 batWorkOrderOutput.setUnit(batWorkOrder.getUnit());
					 batWorkOrderOutput.setDepot("HZN20");
					 batWorkOrderOutput.setSysflag("1");
					 batWorkOrderOutput.setOperateuserid(jizu[15]==null?"":jizu[15].toString());
					 batWorkOrderOutput.setCreator(jizu[15]==null?"":jizu[15].toString());
					 batWorkOrderOutput.setCreatetime(jizu[16]==null?"":jizu[16].toString());
					 batWorkOrderOutput.setLastmodifier(jizu[17]==null?"":jizu[17].toString());
					 batWorkOrderOutput.setLastmodifiedtime(jizu[18]==null?"":jizu[18].toString());
					
					 List<BatWorkOrderOutput> list_workorderout=genericDao.getListWithVariableParas("transfrom.workorderout.list",new Object[]{batWorkOrder.getWorkordercode()});
					 if(list_workorderout!=null&&list_workorderout.size()>0) {
						 BatWorkOrderOutput batWorkOrderOutput1=list_workorderout.get(0);
						 String pid=batWorkOrderOutput1.getPid();
						 BeanUtils.copyProperties(batWorkOrderOutput,batWorkOrderOutput1);
						 batWorkOrderOutput1.setPid(pid);
						 batWorkOrderOutput=batWorkOrderOutput1;
					 };
					 
					 this.genericDao.save(batWorkOrderOutput);
					 batWorkOrder.setWorkorderstate("20");
					 batWorkOrder.setLastmodifiedtime(DateBean.getSysdateTime());
					 this.genericDao.save(batWorkOrder);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
	} 
	
	/**
	 * 获取卷包成型转储验证状态
	 * */
	public Map getvltdetails(Object[] jizu){
		int orderflag = 0,planflag=0;
		Map map=new HashMap();
		BatWorkOrder batWorkOrder=null;
		String date=jizu[0]==null?"":jizu[0].toString();
		String banci=jizu[2]==null?"":jizu[2].toString();
		String jitaino=jizu[3]==null?"":jizu[3].toString();
		String paihao=jizu[1]==null?"":jizu[1].toString();
		//String paihao=paihao1.substring(8, 13);
		String billno=date+banci+jitaino+paihao+"ZP01";
		//以订单号为参数获取工单号
		List<BatWorkOrder> workOrderList=this.genericDao.getListWithVariableParas("WORKORDER.T_BAT_WORKORDERLIST.LIST", new Object[]{billno});
		if(workOrderList!=null&&workOrderList.size()>0){
			 batWorkOrder=workOrderList.get(0);
			if("10".equals(batWorkOrder.getWorkorderstate())){
				orderflag=1;
			}
		}
		String banciId=jizu[4]==null?"":jizu[4].toString();
		String workunitId=jizu[24]==null?"":jizu[24].toString();
		String matId=jizu[9]==null?"":jizu[9].toString();
		List <Object[]> planstatuslist=this.genericDao.getListWithNativeSql("transfrom.getworkplan.list", new Object[]{date,banciId,workunitId,null,matId});
		if(planstatuslist!=null&&planstatuslist.size()>0){
			Object[] planstatus=(Object[]) planstatuslist.get(0);
			if("ad5856e2-93f6-4ca0-9c36-67a465e2bc83".equals(planstatus[5])){
				planflag=1;
			}
			planflag=1;
		}
		if(orderflag+planflag>1){
			map.put("flag",1);
			map.put("workorder", batWorkOrder);
		}else{
			map.put("flag",0);
		}
		return map;
	}
	
	/**
	 * 制丝生产转储
	 * */
	@Transactional(rollbackFor=Exception.class) 
	public void transformZSProduceDate() {
		String billno,workpid;
		Map yzMess;
		try {
			List<Object[]> list_ZS=genericDao.getListWithNativeSql("transfrom.produceJBData.list",new Object[]{"TUB_DIC_2070",taskday,null});
			if(list_ZS!=null&&list_ZS.size()>0){
				for (int i = 0; i < list_ZS.size(); i++) {
					 Object[] jizu=list_ZS.get(i);
					 yzMess=getZSvltdetails(jizu,"ZP03");
					 if(!"1".equals(yzMess.get("flag").toString()))
						 continue;
					 String pici=jizu[19]==null?"":jizu[19].toString();
					 billno=pici+"ZP03";
					 BatWorkOrder batWorkOrder=(BatWorkOrder) yzMess.get("workorder");
					 BatWorkOrderOutput batWorkOrderOutput=new BatWorkOrderOutput();
					 batWorkOrderOutput.setWorkorderpid(batWorkOrder.getPid());
					 if(jizu[11]!=null)
						 batWorkOrderOutput.setQuantity(Double.parseDouble(jizu[11].toString()));
					 batWorkOrderOutput.setMatbatch(pici);
					 batWorkOrderOutput.setUnit(jizu[12]==null?"":jizu[12].toString());
					 batWorkOrderOutput.setDepot("HZN20");
					 batWorkOrderOutput.setSysflag("1");
					 batWorkOrderOutput.setUnit("KG");
					 batWorkOrderOutput.setOperateuserid(jizu[15]==null?"":jizu[15].toString());
					 batWorkOrderOutput.setCreator(jizu[15]==null?"":jizu[15].toString());
					 batWorkOrderOutput.setCreatetime(jizu[16]==null?"":jizu[16].toString());
					 batWorkOrderOutput.setLastmodifier(jizu[17]==null?"":jizu[17].toString());
					 batWorkOrderOutput.setLastmodifiedtime(jizu[18]==null?"":jizu[18].toString());
					 
					 List<BatWorkOrderOutput> list_workorderout=genericDao.getListWithVariableParas("transfrom.workorderout.list",new Object[]{pici});
					 if(list_workorderout!=null&&list_workorderout.size()>0) {
						 BatWorkOrderOutput batWorkOrderOutput1=list_workorderout.get(0);
						 String pid=batWorkOrderOutput1.getPid();
						 BeanUtils.copyProperties(batWorkOrderOutput,batWorkOrderOutput1);
						 batWorkOrderOutput1.setPid(pid);
						 batWorkOrderOutput=batWorkOrderOutput1;
					 };
					 
					 this.genericDao.save(batWorkOrderOutput);
					 batWorkOrder.setLastmodifiedtime(DateBean.getSysdateTime());
					 batWorkOrder.setWorkorderstate("20");
					 this.genericDao.save(batWorkOrder);
					 spcQmsBatchDataService.SaveSpcQmsBatchData(pici);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
	}
	
	/**
	 * 获取制丝转储验证状态
	 * */
	public Map getZSvltdetails(Object[] jizu,String type){
		int orderflag = 0,planflag=0;
		Map map=new HashMap();
		BatWorkOrder batWorkOrder=null;
		String date=jizu[0]==null?"":jizu[0].toString();
		String pici=jizu[19]==null?"":jizu[19].toString();
		String matId=jizu[9]==null?"":jizu[9].toString();
		String billno=pici+type;
		List<BatWorkOrder> workOrderList=this.genericDao.getListWithVariableParas("WORKORDER.T_BAT_WORKORDERLIST.LIST", new Object[]{billno});
		if(workOrderList!=null&&workOrderList.size()>0){
		    batWorkOrder=workOrderList.get(0);
			if("10".equals(batWorkOrder.getWorkorderstate())){
				orderflag=1;
			}
		}
		List<Object[]>planstatuslist=this.genericDao.getListWithNativeSql("transfrom.getworkplan.list", new Object[]{date,null,null,pici,matId});
		if(planstatuslist!=null&&planstatuslist.size()>0){
			Object[] planstatus=(Object[]) planstatuslist.get(0);
			if("ad5856e2-93f6-4ca0-9c36-67a465e2bc83".equals(planstatus[5])){
				planflag=1;
			}
			planflag=1;
		}
		if(orderflag+planflag>1){
			map.put("flag",1);
			map.put("workorder", batWorkOrder);
		}else{
			map.put("flag",0);
		}
		return map;
	}
	
	/**
	 * 梗丝生产转储
	 * */
	@Transactional(rollbackFor=Exception.class) 
	public void transformGSProduceDate() {
		String billno;
		Map yzMess;
		try {
			List<Object[]> list_ZS=genericDao.getListWithNativeSql("transfrom.produceJBData.list",new Object[]{"tub_dic_3001",taskday,null});
			if(list_ZS!=null&&list_ZS.size()>0){
				for (int i = 0; i < list_ZS.size(); i++) {
					 Object[] jizu=list_ZS.get(i);
					 yzMess=getZSvltdetails(jizu,"ZP05");
					 if(!"1".equals(yzMess.get("flag").toString()))
						 continue;
					 String pici=jizu[19]==null?"":jizu[19].toString();
					 billno=pici+"ZP05";
					 BatWorkOrderOutput batWorkOrderOutput=new BatWorkOrderOutput();
					 BatWorkOrder batWorkOrder=(BatWorkOrder) yzMess.get("workorder");
					batWorkOrderOutput.setWorkorderpid(batWorkOrder.getPid());
					 if(jizu[44]!=null)
						 batWorkOrderOutput.setQuantity(Double.parseDouble(jizu[44].toString()));
					 batWorkOrderOutput.setMatbatch(pici);
					 batWorkOrderOutput.setDepot("HZN20");
					 batWorkOrderOutput.setSysflag("1");
					 batWorkOrderOutput.setUnit("KG");
					 batWorkOrderOutput.setOperateuserid(jizu[15]==null?"":jizu[15].toString());
					 batWorkOrderOutput.setCreator(jizu[15]==null?"":jizu[15].toString());
					 batWorkOrderOutput.setCreatetime(jizu[16]==null?"":jizu[16].toString());
					 batWorkOrderOutput.setLastmodifier(jizu[17]==null?"":jizu[17].toString());
					 batWorkOrderOutput.setLastmodifiedtime(jizu[18]==null?"":jizu[18].toString());
					 //半段此条记录是否存在
					 List<BatWorkOrderOutput> list_workorderout=genericDao.getListWithVariableParas("transfrom.workorderout.list",new Object[]{billno});
					 if(list_workorderout!=null&&list_workorderout.size()>0) {
						 BatWorkOrderOutput batWorkOrderOutput1=list_workorderout.get(0);
						 String pid=batWorkOrderOutput1.getPid();
						 BeanUtils.copyProperties(batWorkOrderOutput,batWorkOrderOutput1);
						 batWorkOrderOutput1.setPid(pid);
						 batWorkOrderOutput=batWorkOrderOutput1;
					 };
					 this.genericDao.save(batWorkOrderOutput);
					 batWorkOrder.setLastmodifiedtime(DateBean.getSysdateTime());
					 batWorkOrder.setWorkorderstate("20");
					 this.genericDao.save(batWorkOrder);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
	
		
	}
}
