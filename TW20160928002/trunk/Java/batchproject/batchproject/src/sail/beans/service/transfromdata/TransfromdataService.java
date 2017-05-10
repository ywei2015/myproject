package sail.beans.service.transfromdata;

import java.util.List;

import org.hibernate.metamodel.relational.Database;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sail.beans.dao.GenericDao;
import sail.beans.entity.BatWorkOrder;
import sail.beans.support.DateBean;

@Service
public class TransfromdataService {
	@Autowired
	private GenericDao genericDao;  
	

	public void transformDataJiZu() {
		try{
			//String taskday=DateBean.getAfterDay(DateBean.getSysdate(), 1);
			String taskday=DateBean.getSysdate();
			List<Object[]> list_jizu=genericDao.getListWithNativeSql("transfrom.produceData.list",new Object[]{'2',null});
			if(list_jizu!=null&&list_jizu.size()>0){
				for (int i = 0; i < list_jizu.size(); i++) {
					BatWorkOrder batWorkOrder=new BatWorkOrder();
					Object[] jizu=list_jizu.get(i);
					String billno=getBrandNo(jizu,"juanbao")+"ZP01";
					List<BatWorkOrder> list_billno=genericDao.getListWithVariableParas("transfrom.batWorkOrder.list",new Object[]{billno});
					if(list_billno==null||list_billno.size()==0){
						batWorkOrder=getBatWorkOrder(jizu,batWorkOrder);
					}else{
						batWorkOrder=getBatWorkOrder(jizu,batWorkOrder);
						BatWorkOrder batWorkOrder1=list_billno.get(0);
						String pid=batWorkOrder1.getPid();
						BeanUtils.copyProperties(batWorkOrder,batWorkOrder1);
						batWorkOrder1.setPid(pid);
						batWorkOrder=batWorkOrder1;
					}
					batWorkOrder.setWorkordercode(billno);
					batWorkOrder.setWorkordertype("ZP01");
					batWorkOrder.setProcess(jizu[49]==null?"":jizu[49].toString());
					//batWorkOrder.setEndbrand(jizu[51]==null?"":jizu[51].toString());
					this.genericDao.save(batWorkOrder);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	@Transactional(rollbackFor=Exception.class) 
	public void transformDataSilk() {
		try{
			//String taskday=DateBean.getAfterDay(DateBean.getSysdate(), 1);
			String taskday=DateBean.getSysdate();
			List<Object[]> list_jizu=genericDao.getListWithNativeSql("transfrom.produceDataZS.list",new Object[]{'4',"261564e6-50e3-4eb6-81be-0ee171603cd4"});
			if(list_jizu!=null&&list_jizu.size()>0){
				String []bill_type={"ZP12"};
				for (int i = 0; i < list_jizu.size(); i++) {
					Object[] jizu=list_jizu.get(i);
					int t=0;
					BatWorkOrder batWorkOrder=new BatWorkOrder();
					String billno=jizu[21]+bill_type[t];
					List<BatWorkOrder> list_billno=genericDao.getListWithVariableParas("transfrom.batWorkOrder.list",new Object[]{billno});
					if(list_billno==null||list_billno.size()==0){
						batWorkOrder=getBatWorkOrder(jizu,batWorkOrder);
					}else{
						batWorkOrder=getBatWorkOrder(jizu,batWorkOrder);
						BatWorkOrder batWorkOrder1=list_billno.get(0);
						String pid=batWorkOrder1.getPid();
						BeanUtils.copyProperties(batWorkOrder,batWorkOrder1);
						batWorkOrder1.setPid(pid);
						batWorkOrder=batWorkOrder1;
					}
					batWorkOrder.setWorkordercode(billno);
					batWorkOrder.setSession(jizu[50]==null?"":jizu[50].toString());
					batWorkOrder.setWorkordertype(bill_type[t]);
					//batWorkOrder.setEndbrand(jizu[52]==null?"":jizu[52].toString());
					//batWorkOrder.setProducedate(taskday);
					batWorkOrder.setUnit("KG");
					this.genericDao.save(batWorkOrder);
					t++;
				
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	@Transactional(rollbackFor=Exception.class) 
	public void transformDataSilk2() {
		try{
			//String taskday=DateBean.getAfterDay(DateBean.getSysdate(), 1);
			String taskday=DateBean.getSysdate();
			List<Object[]> list_jizu=genericDao.getListWithNativeSql("transfrom.produceDataZS.list",new Object[]{'4',"5fa03ea1-180c-4b17-b0bc-773c86e98226"});
			if(list_jizu!=null&&list_jizu.size()>0){
				String []bill_type={"ZP13","ZP03"};
				for (int i = 0; i < list_jizu.size(); i++) {
					Object[] jizu=list_jizu.get(i);
					int t=0;
					while(t<2){
						BatWorkOrder batWorkOrder=new BatWorkOrder();
						String billno=jizu[21]+bill_type[t];
						List<BatWorkOrder> list_billno=genericDao.getListWithVariableParas("transfrom.batWorkOrder.list",new Object[]{billno});
						if(list_billno==null||list_billno.size()==0){
							batWorkOrder=getBatWorkOrder(jizu,batWorkOrder);
						}else{
							batWorkOrder=getBatWorkOrder(jizu,batWorkOrder);
							BatWorkOrder batWorkOrder1=list_billno.get(0);
							String pid=batWorkOrder1.getPid();
							BeanUtils.copyProperties(batWorkOrder,batWorkOrder1);
							batWorkOrder1.setPid(pid);
							batWorkOrder=batWorkOrder1;
						}
						batWorkOrder.setWorkordercode(billno);
						batWorkOrder.setSession(jizu[50]==null?"":jizu[50].toString());
						batWorkOrder.setWorkordertype(bill_type[t]);
					//	batWorkOrder.setProducedate(taskday);
						batWorkOrder.setUnit("KG");
						this.genericDao.save(batWorkOrder);
						t++;
					}
				
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	/**
	 *梗丝任务下发 
	 **/
	
	public void transformDataStalkSilk() {
		try{
			//String taskday=DateBean.getAfterDay(DateBean.getSysdate(), 1);
			String taskday=DateBean.getSysdate();
			List<Object[]> list_jizu=genericDao.getListWithNativeSql("transfrom.produceDataGS.list",new Object[]{'4',"774a5d02-c268-45d4-9cf4-6be92a97133f"});
			if(list_jizu!=null&&list_jizu.size()>0){
				for (int i = 0; i < list_jizu.size(); i++) {
					BatWorkOrder batWorkOrder=new BatWorkOrder();
					Object[] jizu=list_jizu.get(i);
					String billno=jizu[21]+"ZP05";
					List<BatWorkOrder> list_billno=genericDao.getListWithVariableParas("transfrom.batWorkOrder.list",new Object[]{billno});
					if(list_billno==null||list_billno.size()==0){
						batWorkOrder=getBatWorkOrder(jizu,batWorkOrder);
					}else{
						batWorkOrder=getBatWorkOrder(jizu,batWorkOrder);
						BatWorkOrder batWorkOrder1=list_billno.get(0);
						String pid=batWorkOrder1.getPid();
						BeanUtils.copyProperties(batWorkOrder,batWorkOrder1);
						batWorkOrder1.setPid(pid);
						batWorkOrder=batWorkOrder1;
					}
					batWorkOrder.setWorkordercode(billno);
					batWorkOrder.setWorkordertype("ZP05");
					batWorkOrder.setSession(jizu[50]==null?"":jizu[50].toString());
					//batWorkOrder.setProducedate(taskday);
					batWorkOrder.setUnit("KG");
					this.genericDao.save(batWorkOrder);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	
	public String getBrandNo(Object[]jizu, String type){
		String billno=null;
		if(type.equals("juanbao")){
			String banCi=jizu[46]==null?"":jizu[46].toString();
			String jiTaino=jizu[47]==null?"":jizu[47].toString();
			String jitai="";
			if(jiTaino!=null&&jiTaino.length()>2)
				 jitai=jiTaino.substring(jiTaino.length()-2, jiTaino.length());
			String brandno=jizu[48]==null?"":jizu[48].toString();
		    billno=jizu[8].toString()+banCi+jiTaino+brandno;//日期8位+班次+机台号码+牌号ESB短码+工单类型
		}
		return billno;
	}
	
	public BatWorkOrder getBatWorkOrder(Object[]jizu,BatWorkOrder batWorkOrder){
		String taskday=DateBean.getAfterDay(DateBean.getSysdate(), 1);
		batWorkOrder.setCreatetime(DateBean.getSysdateTime());
		batWorkOrder.setWorkordertype(jizu[4]==null?"":jizu[4].toString());//ZP01
		batWorkOrder.setProducedate(jizu[8]==null?"":jizu[8].toString());
		batWorkOrder.setFactory("2200");
		batWorkOrder.setWorkarea("HZ10");
		batWorkOrder.setMatcode(jizu[42]==null?"":jizu[42].toString());
		batWorkOrder.setMatname(jizu[41]==null?"":jizu[41].toString());
		batWorkOrder.setEndbrand(jizu[51]==null?"":jizu[51].toString());
		batWorkOrder.setRecipeever(jizu[32]==null?"":jizu[32].toString());
		batWorkOrder.setCraftver(jizu[33]==null?"":jizu[33].toString());
		batWorkOrder.setPlanstarttime(jizu[14]==null?"":jizu[14].toString());
		batWorkOrder.setPlanendtime(jizu[15]==null?"":jizu[15].toString());
		batWorkOrder.setActualstarttime(jizu[16]==null?"":jizu[16].toString());
		batWorkOrder.setActualendtime(jizu[17]==null?"":jizu[17].toString());
		batWorkOrder.setWorktime(jizu[39]==null?"":jizu[39].toString());
		if(jizu[11]!=null)
			batWorkOrder.setPlanquantity(Double.parseDouble(jizu[11].toString()));
		batWorkOrder.setUnit(jizu[43]==null?"":jizu[43].toString());
		//batWorkOrder.setProcess(jizu[7]==null?"":jizu[7].toString());
		batWorkOrder.setOpuserid(jizu[44]==null?"":jizu[44].toString());
		batWorkOrder.setWorkorderstate("10");
		batWorkOrder.setRemark(jizu[25]==null?"":jizu[25].toString());
		batWorkOrder.setSysflag(jizu[26].toString());
		batWorkOrder.setCreator(jizu[27]==null?"":jizu[27].toString());
		batWorkOrder.setCreatetime(jizu[28]==null?"":jizu[28].toString());
		batWorkOrder.setLastmodifier(jizu[29]==null?"":jizu[29].toString());
		batWorkOrder.setLastmodifiedtime(jizu[30]==null?"":jizu[30].toString());
		return batWorkOrder;
	}


}
