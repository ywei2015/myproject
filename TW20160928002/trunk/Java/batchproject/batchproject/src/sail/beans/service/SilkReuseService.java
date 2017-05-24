package sail.beans.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sail.beans.dao.GenericDao;
import sail.beans.entity.BatWorkOrder;
import sail.beans.entity.BatWorkOrderInput;
import sail.beans.entity.CarCode;
import sail.beans.support.DateBean;

@Service
public class SilkReuseService {
	@Autowired
	private GenericDao genericDao;
	
	/**
	 * 残丝回用烟丝 余梗投料投料保存
	 */
	public BatWorkOrderInput saveSilkReuseInput(String workOrderCode,String matBatch, String quantity, String remark,String location, String operuser) {
		BatWorkOrderInput batWorkOrderInput = null;
		try{
		List<BatWorkOrder> batWorkList = genericDao.getListWithVariableParas("WORKORDER.T_BAT_WORKORDERLIST.LIST", new Object[]{workOrderCode});
		if (batWorkList != null && batWorkList.size() > 0){
			BatWorkOrder BatWorkOrder = batWorkList.get(0);
				CarCode carCode = this.getSilkReuseValue(matBatch);
				if(carCode==null) {
					return batWorkOrderInput;
				}
				batWorkOrderInput = new BatWorkOrderInput();
				batWorkOrderInput.setWorkorderpid(BatWorkOrder.getPid());
				batWorkOrderInput.setMatbatch(matBatch);
				batWorkOrderInput.setMatcode(carCode.getMatcode());
				batWorkOrderInput.setMatname(carCode.getMatname());
				if (location != null && !"".equals(location)){
					batWorkOrderInput.setLocation(location);
				}
				if("w".equalsIgnoreCase(carCode.getState())){
					batWorkOrderInput.setRemark5("w");
				}else if("2".equalsIgnoreCase(carCode.getState())){
					batWorkOrderInput.setRemark5("2");
					return batWorkOrderInput;
				}else if("e".equalsIgnoreCase(carCode.getState())){
					batWorkOrderInput.setRemark5("e");
					return batWorkOrderInput;
				}
				batWorkOrderInput.setTltype("0");
				batWorkOrderInput.setRemark3(remark);
				batWorkOrderInput.setMatname(carCode.getMatname());
				batWorkOrderInput.setQuantity(Double.parseDouble(quantity));
				batWorkOrderInput.setUnit(carCode.getUnit());
				batWorkOrderInput.setStarttime(DateBean.getSysdateTime());
				batWorkOrderInput.setEndtime(DateBean.getSysdateTime());
				batWorkOrderInput.setOperatetime(DateBean.getSysdateTime());
				batWorkOrderInput.setOperateuserid(operuser);
				batWorkOrderInput.setSysflag("1");
				batWorkOrderInput.setCreator(operuser);
				batWorkOrderInput.setCreatetime(DateBean.getSysdateTime());
				genericDao.save(batWorkOrderInput);
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return batWorkOrderInput;
	}

	/**
	 * 获取可回用烟丝批次号
	 */
	public List<String> getSilkReuseBatch(String type,String workcode) {
		String sql=null;
		if(type.equals("cansi")){
			sql="select t.F_NEW_MATBATCH from T_BAT_SILK_REUSE t where t.F_BIZ_ID='PP21410'"+ 
			        "and  t.f_sys_flag='1' order by F_CREATE_TIME desc";
		}else{
			sql="select t1.F_MAT_BATCH||'-'||d.f_mat_name from t_bat_workorder_output t1,t_bat_workorder d where "+
            "t1.F_WORKORDER_PID=d.f_pid and d.f_workorder_type='ZP05'and d.f_end_brand in(select t2.f_end_brand from t_bat_workorder t2 where " +
            "t2.f_workorder_code ='"+workcode+"') and t1.f_sys_flag='1' and d.f_sys_flag='1' and t1.f_create_time>to_char(sysdate-30,'YYYYMMDD')"+
            "order by t1.F_CREATE_TIME desc";
		}
		List<String> workData=genericDao.getListWithNativeSql(sql);
		return workData;
		
	}
	
	/**
	 * 残丝回用烟丝批次解码
	 */
	public CarCode getSilkReuseValue(String match) {
		CarCode carCode=new CarCode();
		List<Object[]>carList=genericDao.getListWithNativeSql("STORAGE.GET_SILKREUSE_VALUE", new Object[]{match});
		if(carList!=null&&carList.size()>0){
			Object[] cararray=carList.get(0);
			String unit=(String) (cararray[11]==null?"":cararray[11]);
			carCode.setUnit(unit);
			String quality= (cararray[10]==null?"":cararray[10]).toString();
			carCode.setAmount(Double.parseDouble(quality));
			String oldmatch=(String) (cararray[14]==null?"":cararray[14]);
			carCode.setOldbatch(oldmatch);
			String matname=(String) (cararray[6]==null?"":cararray[6]);
			carCode.setMatname(matname);
			String matcode=(String) (cararray[5]==null?"":cararray[5]);
			carCode.setMatcode(matcode);
		}
		return carCode;
	}
	
	/**
	 * 残丝回用烟丝是否足量
	 */
	public boolean isEnough(String matBatch, String quantity,String type) {
		double xiaol=0;
		String sql1="select  sum(t.F_QUANTITY) from T_BAT_SILK_REUSE t where t.F_SYS_FLAG='1' and t.f_new_matbatch='"+matBatch+"'";
		if(type.equals("gengsi")){
			 sql1="select  sum(t.F_QUANTITY) from T_BAT_WORKORDER_OUTPUT t where t.F_SYS_FLAG='1' and t.F_MAT_BATCH='"+matBatch+"'";
		}
		String sql2="select sum(d.F_QUANTITY) from T_BAT_WORKORDER_INPUT d where d.F_SYS_FLAG='1' and d.f_mat_batch='"+matBatch+"'";
		List<java.math.BigDecimal> list1=this.genericDao.getListWithNativeSql(sql1);
		List<java.math.BigDecimal> list2=this.genericDao.getListWithNativeSql(sql2);
		if(list2!=null&&list2.get(0)!=null){
			 xiaol=list2.get(0).doubleValue();
		}
		if(list1==null||list1.get(0)==null)
			return false;
		double zongl=list1.get(0).doubleValue();
		double xiaol1=Double.valueOf(quantity.toString());
		if((zongl-xiaol-xiaol1)>=0){
			return true;
		}
			
		return false;
	}

	
}
