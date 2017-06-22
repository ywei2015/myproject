package sail.beans.service.transfromdata;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sail.beans.dao.GenericDao;
import sail.beans.entity.BatBatAdjustDetail;
import sail.beans.entity.BatDepotIoBill;
import sail.beans.entity.BatDepotIoDetail;
import sail.beans.entity.BatDepotIoDetailList;
import sail.beans.entity.BatFiltertipPut;
import sail.beans.entity.BatWorkOrder;
import sail.beans.entity.BatWorkOrderInput;
import sail.beans.entity.CarCode;
import sail.beans.service.BatchResolveValue;
import sail.beans.service.MatBomService;
import sail.beans.support.DateBean;
import sqlj.runtime.error.RuntimeErrors;

@Service
public class TransformInputDateService {
	
	@Autowired
	private GenericDao genericDao;  
	@Autowired
	private BatchResolveValue batchResolveValue;
	@Autowired
	private MatBomService matBomService; 
	
	/**
	 * 大小件关系类转储为生产领域退回入库
	 * */
	@Transactional
	public void TransformAdjustData() {
		// TODO Auto-generated method stub
		BatDepotIoBill batDepotIoBill = null;
		BatDepotIoDetail batDepotIoDetail=null;
		BatDepotIoDetailList batDepotIoDetailList=null;
		BatBatAdjustDetail batBatAdjustDetail=null;
		double quality=0;
		String bill=DateBean.getSysdate()+"ZI30";
		try {
			List<BatBatAdjustDetail> adjustDetailList=genericDao.getListWithVariableParas("TRANSFORM_INPUT.ADJUST.LIST",new Object[]{null});
			if (adjustDetailList != null && adjustDetailList.size() > 0){
				for (int i = 0; i < adjustDetailList.size(); i++) {
					batBatAdjustDetail=adjustDetailList.get(i);
					List<BatDepotIoBill> batDepotIoBillList = genericDao.getListWithVariableParas("STORAGE.T_BAT_DEPOT_IOBILLLIST.LIST", new Object[]{bill,"ZI30"});
					CarCode caecode=batchResolveValue.getResolveValue(batBatAdjustDetail.getSlavebatch(), "JM01");
					if(caecode.getMatcode()==null)
						continue;
					if(batDepotIoBillList!=null&&batDepotIoBillList.size()>0){
						batDepotIoBill=batDepotIoBillList.get(0);
					}else{
						batDepotIoBill=new BatDepotIoBill();
						batDepotIoBill.setBillno(bill);
						batDepotIoBill.setBiztype("MM2143");
						batDepotIoBill.setBilltype("11");
						batDepotIoBill.setDoctype("ZI30");
						batDepotIoBill.setDepot("HZ10");
						batDepotIoBill.setFactory("2200");
						batDepotIoBill.setIsEnter("1");
						batDepotIoBill.setSysflag("1");
						batDepotIoBill.setCreator(batBatAdjustDetail.getCreator());
						batDepotIoBill.setCreatetime(batBatAdjustDetail.getCreatetime());
						batDepotIoBill.setDate(DateBean.getSysdate());
						batDepotIoBill.setOperatetime(batBatAdjustDetail.getCreatetime());
						batDepotIoBill.setOperateuserid(batBatAdjustDetail.getCreator());
						batDepotIoBill.setLastmodifiedtime(batBatAdjustDetail.getCreatetime());
						batDepotIoBill.setLastmodifier(batBatAdjustDetail.getCreator());
						this.genericDao.save(batDepotIoBill);
					}
					//入库表是否已经存在这个大件批次的入库记录，有则只需要累加质量
					List<BatDepotIoDetail> detailList = genericDao.getListWithVariableParas("STORAGE.T_BAT_DEPOT_IOBILLDETAIL.LIST", new Object[]{null,"ZI30",null,batBatAdjustDetail.getNewmasterbatch()});
					if (detailList != null && detailList.size() > 0){
						batDepotIoDetail = detailList.get(0);
						batDepotIoDetail.setQuantity(batDepotIoDetail.getQuantity()+caecode.getAmount());
					}else{
						batDepotIoDetail=new BatDepotIoDetail();
						batDepotIoDetail.setBillpid(batDepotIoBill.getPid());
						batDepotIoDetail.setLastmodifiedtime(batBatAdjustDetail.getCreatetime());
						batDepotIoDetail.setLastmodifier(batBatAdjustDetail.getCreator());
						batDepotIoDetail.setCreator(batBatAdjustDetail.getCreator());
						batDepotIoDetail.setCreatetime(batBatAdjustDetail.getCreatetime());
						batDepotIoDetail.setRemark5("1");
						batDepotIoDetail.setIsEnter("1");
						batDepotIoDetail.setSysflag("1");
						batDepotIoDetail.setMatbatch(batBatAdjustDetail.getNewmasterbatch());
						batDepotIoDetail.setMatcode(batBatAdjustDetail.getMatcode());
						batDepotIoDetail.setMatname(batBatAdjustDetail.getMatname());
						batDepotIoDetail.setQuantity(caecode.getAmount());
						batDepotIoDetail.setUnit(caecode.getUnit());
					}	
					this.genericDao.save(batDepotIoDetail);
					//从入库明细子表复制记录为ZI30
					List <BatDepotIoDetailList> batDepotIoDetailList_=this.genericDao.getListWithVariableParas("storage.getbatDepotIoDetailList.bybatch", new Object[]{batBatAdjustDetail.getSlavebatch(),batDepotIoDetail.getPid()});
					if(batDepotIoDetailList_==null||batDepotIoDetailList_.size()==0){
						batDepotIoDetailList=new BatDepotIoDetailList();
					//	batDepotIoDetailList=batDepotIoDetailList_.get(0);
						batDepotIoDetailList.setBilldetailpid(batDepotIoDetail.getPid());
						quality=quality+batDepotIoDetailList.getQuantity();
						batDepotIoDetailList.setSysflag("1");
						batDepotIoDetailList.setCreator(batBatAdjustDetail.getCreator());
						batDepotIoDetailList.setCreatetime(batBatAdjustDetail.getCreatetime());
						batDepotIoDetailList.setLastmodifiedtime(batBatAdjustDetail.getCreatetime());
						batDepotIoDetailList.setLastmodifier(batBatAdjustDetail.getCreator());
						batDepotIoDetailList.setRemark5("1");
						batDepotIoDetailList.setQuantity(caecode.getAmount());
						batDepotIoDetailList.setUnit(caecode.getUnit());
						batDepotIoDetailList.setSlavebatch(batBatAdjustDetail.getSlavebatch());
						this.genericDao.save(batDepotIoDetailList);
					}
					batBatAdjustDetail.setRemark5("1");
					this.genericDao.save(batBatAdjustDetail);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	/**
	 * 嘴棒投料转储
	 * */
	@Transactional
	public void TransformFiltertipPut() {
		BatFiltertipPut batFiltertipPut=null;
		BatWorkOrder batWorkOrder=null;
		try {
			List<BatFiltertipPut> batFiltertipPutList = genericDao.getListWithVariableParas("TRANSFORM_INPUT.FILTERTIPPUT.LIST", new Object[]{null});
			BatWorkOrderInput batWorkOrderInput = null;
			if (batFiltertipPutList != null && batFiltertipPutList.size() > 0){
				for (int i = 0; i < batFiltertipPutList.size(); i++) {
					batFiltertipPut = batFiltertipPutList.get(i);
					String date=batFiltertipPut.getOperatetime().substring(0, 8);
					//根据发射机和日期匹配投料工单
					List<BatWorkOrder> batWorkOrderList=getBatWorkOrderList(date,batFiltertipPut.getTransmitter(),batFiltertipPut.getCreatetime());
					if (batWorkOrderList != null && batWorkOrderList.size() > 0){
						for (int j = 0; j < batWorkOrderList.size(); j++) {
							 batWorkOrder=batWorkOrderList.get(j);
							 List<BatWorkOrderInput> inputList = genericDao.getListWithVariableParas("WORKORDER.T_BAT_WORKORDER_INPUTLIST.LIST", new Object[]{batFiltertipPut.getMatbatch(),null,null});
							 if (inputList.size() ==0){
									batWorkOrderInput = new BatWorkOrderInput();
									batWorkOrderInput.setWorkorderpid(batWorkOrder.getPid());
									batWorkOrderInput.setTltype("3");
									batWorkOrderInput.setMatbatch(batFiltertipPut.getMatbatch());
									batWorkOrderInput.setMatcode(batFiltertipPut.getMatcode());
									batWorkOrderInput.setMatname(batFiltertipPut.getMatname());
									batWorkOrderInput.setUnit(batFiltertipPut.getUnit());
									batWorkOrderInput.setQuantity(batFiltertipPut.getQuantity());
									batWorkOrderInput.setStarttime(batFiltertipPut.getOperatetime());
									batWorkOrderInput.setEndtime(batFiltertipPut.getOperatetime());
									batWorkOrderInput.setOperatetime(batFiltertipPut.getOperatetime());
									batWorkOrderInput.setOperateuserid(batFiltertipPut.getOperateuserid());
									batWorkOrderInput.setSysflag("1");
									batWorkOrderInput.setCreator(batFiltertipPut.getOperateuserid());
									batWorkOrderInput.setCreatetime(DateBean.getSysdateTime()); //为转储时间
									List matList=matBomService.getBomByWorkOrder(batWorkOrder.getWorkordercode(),null,batFiltertipPut.getMatcode());
									if(matList.size()==0)
										batWorkOrderInput.setRemark4("0");
									genericDao.save(batWorkOrderInput);
								}	
						}
						batFiltertipPut.setIsdeal("1");
						genericDao.save(batFiltertipPut);
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	/**
	 * 根据发射机台号和日期获取投料工单
	 * */
	private List<BatWorkOrder> getBatWorkOrderList(String date,String transmitter,String createTime) {
		List<BatWorkOrder> workOrderList=null;
		//获取发射机对应的卷接机台
		List<String> jjtaino=genericDao.getListWithNativeSql("TRANSFORM_INPUT.WORKORDERJITAI.LIST", new Object[]{transmitter,createTime,createTime});
		if(jjtaino!=null&&jjtaino.size()>0){
			for (int i = 0; i < jjtaino.size(); i++) {
				String jitnoEsb=jjtaino.get(i).toString();
				//根据卷接机台获取卷接机组
				String sql="select t.F_GROUP_NAME,t.F_GROUP_ESBCODE from v_bat_equ t where  t.F_PROCESS_CODE='"+jitnoEsb+"'";
				List<String> groupNoList=genericDao.getListWithNativeSql(sql);
				if(groupNoList!=null&&groupNoList.size()>0){
					String groupNo=groupNoList.get(0).toString();
					if(groupNo!=null){
						 List<BatWorkOrder> batWorkOrderList = genericDao.getListWithVariableParas("TRANSFORM_INPUT.WORKORDERCODE.LIST", new Object[]{date,"ZP01",groupNo});
						 if(batWorkOrderList!=null&&batWorkOrderList.size()>0){
							 if(getWorkClass(createTime).equals(batWorkOrderList.get(0)))
								 workOrderList.add(batWorkOrderList.get(0));
						 }
					}
				}
			}
		}
		return workOrderList;
	}
	
	public String getWorkClass(String time){
		String workClass="";
		String sql="select  mestest.f_bat_getesbcode_byschedule('"+time+"','JB',1) as ZSBC from dual";
		List workteamList=this.genericDao.getListWithNativeSql(sql);
		if(workteamList!=null&&workteamList.size()>0){
			workClass=workteamList.get(0).toString();
		}
		return workClass;
	}
}
