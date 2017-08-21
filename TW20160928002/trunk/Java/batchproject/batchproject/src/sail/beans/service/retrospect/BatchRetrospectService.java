package sail.beans.service.retrospect;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.apache.bcel.generic.NEW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysql.fabric.xmlrpc.base.Array;

import sail.beans.dao.GenericDao;
import sail.beans.dao.iml.Pager;
import sail.beans.entity.BatDepotIoDetail;
import sail.beans.entity.BatWorkOrder;
import sail.beans.entity.BatWorkOrderInput;
import sail.beans.entity.BatWorkOrderOutput;
import sail.beans.entity.BatchMeterialTracking;
import sail.beans.entity.BatchRetrospect;

@Service
public class BatchRetrospectService {
	@Autowired
	private GenericDao genericDao;

	public Pager batchRetrospectForJianYan(int pageSize, int pageNumber,String orderNum) {
		Pager page=null;
		try {
			int starnumber=pageSize*(pageNumber-1);
			List<BatchRetrospect> batchRetrospectList=new ArrayList();
		    page=genericDao.getListWithNativeSqlFY("retrospect.jianyan.list", new Object[]{orderNum,orderNum},new int[]{starnumber,pageSize});
			if(page!=null){
				List<Object[]> RetrospectList=(List<Object[]>) page.getList();
				if(RetrospectList!=null&&RetrospectList.size()>0){
					for (int i = 0; i < RetrospectList.size(); i++) {
						Object[] result=RetrospectList.get(i);
						BatchRetrospect batchRetrospect=new BatchRetrospect();
						batchRetrospect.setPid(result[0].toString());
						batchRetrospect.setCodetype(result[2]==null?"":result[2].toString());
						batchRetrospect.setCodetypename(result[3]==null?"":result[3].toString());
						batchRetrospect.setProducetime(result[4]==null?"":result[4].toString());
						batchRetrospect.setUcode(result[5]==null?"":result[5].toString());
						batchRetrospect.setLcode(result[6]==null?"":result[6].toString());
						batchRetrospect.setWorktime(result[8]==null?"":result[8].toString());
						batchRetrospect.setWorkteam(result[10]==null?"":result[10].toString());
						batchRetrospect.setBrandcode(result[11]==null?"":result[11].toString());
						batchRetrospect.setBatchname(result[12]==null?"":result[12].toString());
						batchRetrospect.setProcess(result[13]==null?"":result[13].toString());
						batchRetrospect.setOperator(result[14]==null?"":result[14].toString());
						batchRetrospectList.add(batchRetrospect);
					}
					page.setList(batchRetrospectList);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  page;
	}
	
	public Pager batchRetrospectForJBao(int pageSize, int pageNumber,String code) {
		Pager page=null;
		Object []shuzu=new Object[18];
		try {
			for (int i = 0; i < 18; i++) {
				shuzu[i]=code;
			}
			int starnumber=pageSize*(pageNumber-1);
			List<BatWorkOrder> batchRetrospectList=new ArrayList();
		    page=genericDao.getListWithNativeSqlFY("retrospect.juanbao.list", shuzu,new int[]{starnumber,pageSize});
			if(page!=null){
				List<Object[]> RetrospectList=(List<Object[]>) page.getList();
				if(RetrospectList!=null&&RetrospectList.size()>0){
					for (int i = 0; i < RetrospectList.size(); i++) {
						Object[] result=RetrospectList.get(i);
						BatWorkOrder batWorkOrder=new BatWorkOrder();
						batWorkOrder.setPid(result[0]==null?"":result[0].toString());
						batWorkOrder.setWorkordercode(result[1]==null?"":result[1].toString());
						batWorkOrder.setProcess(result[45]==null?"":result[45].toString());
						batWorkOrder.setProducedate(result[3]==null?"":result[3].toString());
						batWorkOrder.setMatcode(result[6]==null?"":result[6].toString());
						batWorkOrder.setMatname(result[7]==null?"":result[7].toString());
						batWorkOrder.setWorktime(result[43]==null?"":result[43].toString());
						batWorkOrder.setWorkteam(result[44]==null?"":result[44].toString());
						batWorkOrder.setActualstarttime(result[16]==null?"":result[16].toString());
						batWorkOrder.setActualendtime(result[17]==null?"":result[17].toString());
						batWorkOrder.setOpuserid(result[42]==null?"":result[42].toString());
						batchRetrospectList.add(batWorkOrder);
					}
					page.setList(batchRetrospectList);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  page;
	}

	public BatWorkOrder batchRetrospectForWorkOder(String code) {
		String workteam="";
		String worktime="";
		BatWorkOrder batWorkOrder=null;
		try {
			List<BatWorkOrder> batWorkOrderList=this.genericDao.getListWithVariableParas("WORKORDER.T_BAT_WORKORDERLIST.LIST", new Object[]{code});
			
			if(batWorkOrderList!=null&&batWorkOrderList.size()>0){
			    batWorkOrder=batWorkOrderList.get(0);
			    List<String> workname=this.genericDao.getListWithNativeSql("retrospect.get_workorder_name.list", new Object[]{batWorkOrder.getWorkordertype()});
				batWorkOrder.setRemark(workname.get(0).toString());
				List<Object> workteamList=this.genericDao.getListWithNativeSql("retrospect.workteam.list", new Object[]{batWorkOrder.getWorkteam()});
				if(workteamList.size()>0)
					 workteam=workteamList.get(0).toString();
				List<Object> worktimeList=this.genericDao.getListWithNativeSql("retrospect.worktime.list", new Object[]{batWorkOrder.getWorktime()});
				if(worktimeList.size()>0)
					 worktime=worktimeList.get(0).toString();
				batWorkOrder.setWorkteam(workteam);
				batWorkOrder.setWorktime(worktime);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return batWorkOrder;
	}

	public List<BatWorkOrder> retrospectInputByGroup(String code) {
		BatWorkOrder batWorkOrder=null;
		List<BatWorkOrder> batWorkOrderListj=new ArrayList<BatWorkOrder>();
		try {
			List<Object[]> batWorkOrderList=this.genericDao.getListWithNativeSql("retrospect.inputbygroup.list", new Object[]{code});
			if(batWorkOrderList.size()>0){
				for (int i = 0; i < batWorkOrderList.size(); i++) {
					batWorkOrder=new BatWorkOrder();
					Object[] result=batWorkOrderList.get(i);
					batWorkOrder.setMatcode(result[0]==null?"":result[0].toString());
					batWorkOrder.setMatname(result[1]==null?"":result[1].toString());
					batWorkOrder.setRemark(result[2]==null?"":result[2].toString());
					batWorkOrder.setRemark1(result[3]==null?"":result[3].toString());
					batWorkOrder.setUnit(result[4]==null?"":result[4].toString());
					batWorkOrderListj.add(batWorkOrder);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			}
		return batWorkOrderListj;
	}

	public Pager retrospectInput(String code,String matcode, int pageSize, int pageNumber) {
		int starnumber=pageSize*(pageNumber-1);
		Pager page=this.genericDao.getListWithVariableParasFY("retrospect.workorder_input.list", 
				new Object[]{code,matcode},new int[]{starnumber,pageSize});
		List<BatWorkOrderInput> workinputlist=(List<BatWorkOrderInput>) page.getList();
		if(workinputlist!=null&&workinputlist.size()>0){
			for (int i = 0; i < workinputlist.size(); i++) {
				BatWorkOrderInput batWorkOrderInput=workinputlist.get(i);
				//查询该批次是来源，大于1表示来源出库表，否则来源于出入库
				List<Object> countlist=this.genericDao.getListWithNativeSql("retrospect.getbatchsource.list", new Object[]{batWorkOrderInput.getMatbatch()});
				batWorkOrderInput.setRemark(countlist.get(0).toString());
			}
		}
		return page;
	}

	public String getWorkcodeBypara(String code, String batch) {
		String workcode=null;
		if(code!=null)
			workcode= code;
		if(batch!=null&&!batch.isEmpty()){
			List<BatWorkOrder>  batWorkOrderList=this.genericDao.getListWithVariableParas("retrospect.workorder_out.list", new Object[]{batch});
			BatWorkOrder batWorkOrder=batWorkOrderList.get(0);
			workcode=batWorkOrder.getWorkordercode();
		}
		return workcode;
	}

	public List<BatDepotIoDetail> retrospectInStorage(String batch) {
		List<BatDepotIoDetail> batDepotIoDetailList= new ArrayList<BatDepotIoDetail>();
		List<Object[]>  BatDepotIoDetailList=this.genericDao.getListWithNativeSql("retrospect.t_bat_depot_iobilldetail.out", new Object[]{batch,batch});
		if(BatDepotIoDetailList.size()>0){
			for (int i = 0; i < BatDepotIoDetailList.size(); i++) {
				BatDepotIoDetail batDepotIoDetail=new BatDepotIoDetail();
				Object[] detailObject=BatDepotIoDetailList.get(i);
				batDepotIoDetail.setRemark(detailObject[0]==null?"":detailObject[0].toString());//出入库类型
				batDepotIoDetail.setRemark1(detailObject[1]==null?"":detailObject[1].toString());//出入库类型
				batDepotIoDetail.setRemark2(detailObject[2]==null?"":detailObject[2].toString());//出入日期
				batDepotIoDetail.setCreator(detailObject[3]==null?"":detailObject[3].toString());//操作人
				batDepotIoDetail.setCreatetime(detailObject[4]==null?"":detailObject[4].toString());//操作人
				batDepotIoDetail.setMatname(detailObject[6]==null?"":detailObject[6].toString());
				batDepotIoDetail.setMatbatch(detailObject[7]==null?"":detailObject[7].toString());
				batDepotIoDetail.setStatus(detailObject[8]==null?"":detailObject[8].toString());
				batDepotIoDetail.setQuantity(detailObject[9]==null?0:Double.parseDouble(detailObject[9].toString()));
				batDepotIoDetail.setUnitname(detailObject[10]==null?"":detailObject[10].toString());
				batDepotIoDetail.setMatkl(detailObject[11]==null?"":detailObject[11].toString());
				batDepotIoDetailList.add(batDepotIoDetail);
			}
		}
		return batDepotIoDetailList;
	} 
	
}
