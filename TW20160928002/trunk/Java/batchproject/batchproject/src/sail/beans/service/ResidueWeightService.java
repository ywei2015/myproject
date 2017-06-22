package sail.beans.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sail.beans.dao.GenericDao;
import sail.beans.entity.BatDepotIoBill;
import sail.beans.entity.BatDepotIoDetail;
import sail.beans.support.DateBean;
import sqlj.runtime.error.RuntimeErrors;

/**
 * 表香余重
 * */
@Service
public class ResidueWeightService {
	@Autowired
	private GenericDao genericDao;  
	
	/**
	 * 表香余重保存方法,根据此批次的余重，在物资明细表保存为CJYL的入库记录，
	 * 以便以后的批次解码获取到该批次最新的余重
	 * */
	@Transactional
	public BatDepotIoDetail saveResidueWeight(String batch, String weight,String userId) {
		BatDepotIoDetail batDepotIoDetail=null;
		BatDepotIoBill batDepotIoBill=null;
		String billno=DateBean.getSysdate().substring(0, 6)+"CJYL";	
		BatDepotIoDetail batDepotIoDetailj=null;
		try {
			//首先在物资出入明细表中查找是否已经存在
			List<BatDepotIoDetail> detailList = genericDao.getListWithVariableParas("STORAGE.T_BAT_DEPOT_IOBILLDETAIL.LIST", new Object[]{null,"YL01",null,batch});
			if (detailList != null && detailList.size() > 0){
				batDepotIoDetail = detailList.get(0);
				batDepotIoDetail.setRepeated("1");
				return batDepotIoDetail;
			}else{
				//单据记录是否存在
				List<BatDepotIoBill> batDepotIoBillList = genericDao.getListWithVariableParas("STORAGE.T_BAT_DEPOT_IOBILLLIST.LIST", new Object[]{billno,"YL01"});
				if(batDepotIoBillList!=null&&batDepotIoBillList.size()>0){
					batDepotIoBill=batDepotIoBillList.get(0);
				}else{
					batDepotIoBill=new BatDepotIoBill();
					batDepotIoBill.setBillno(billno);
					batDepotIoBill.setBiztype("MM000Y");
					batDepotIoBill.setBilltype("11");
					batDepotIoBill.setDoctype("YL01");
					batDepotIoBill.setDepot("HZ10");
					batDepotIoBill.setFactory("2200");
					batDepotIoBill.setIsEnter("1");
					batDepotIoBill.setSysflag("1");
					batDepotIoBill.setCreator(userId);
					batDepotIoBill.setCreatetime(DateBean.getSysdateTime());
					batDepotIoBill.setDate(DateBean.getSysdate());
					batDepotIoBill.setOperatetime(DateBean.getSysdateTime());
					batDepotIoBill.setOperateuserid(userId);
					batDepotIoBill.setLastmodifiedtime(DateBean.getSysdateTime());
					batDepotIoBill.setLastmodifier(userId);
					this.genericDao.save(batDepotIoBill);
				}
				//在物资入库明细表中获取该批次相关信息
				List<BatDepotIoDetail> detailListj = genericDao.getListWithVariableParas("STORAGE.T_BAT_DEPOT_IOBILLDETAIL.LIST", new Object[]{null,null,null,batch});
				if (detailListj != null && detailListj.size() > 0){
				    batDepotIoDetailj=detailListj.get(0);
					batDepotIoDetail=new BatDepotIoDetail();
					batDepotIoDetail.setBillpid(batDepotIoBill.getPid());
					batDepotIoDetail.setLastmodifiedtime(DateBean.getSysdateTime());
					batDepotIoDetail.setLastmodifier(userId);
					batDepotIoDetail.setCreator(userId);
					batDepotIoDetail.setCreatetime(DateBean.getSysdateTime());
					batDepotIoDetail.setRemark5("1");
					batDepotIoDetail.setIsEnter("1");
					batDepotIoDetail.setSysflag("1");
					batDepotIoDetail.setReason("生产剩余物料记录");
					batDepotIoDetail.setMatbatch(batch);
					batDepotIoDetail.setShkzg(batDepotIoDetailj.getShkzg());
					batDepotIoDetail.setStatus(batDepotIoDetailj.getStatus());
					batDepotIoDetail.setUnit(batDepotIoDetailj.getUnit());
					Double quantity=Double.parseDouble(weight);
					batDepotIoDetail.setQuantity(quantity);
					batDepotIoDetail.setMatcode(batDepotIoDetailj.getMatcode());
					batDepotIoDetail.setMatname(batDepotIoDetailj.getMatname());
					batDepotIoDetail.setInventorytype(batDepotIoDetailj.getInventorytype());
					this.genericDao.save(batDepotIoDetail);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return batDepotIoDetail;
	}

	public List<BatDepotIoDetail> getBatDepotIoDetailList(String docType){
		List<BatDepotIoDetail> detailList=null;
		detailList = genericDao.getListWithVariableParas("STORAGE.T_BAT_DEPOT_IOBILLDETAIL2.LIST", new Object[]{null,"YL01",null,null});
		return detailList;
	}

	public boolean deleteBatDepotIoDetail(String f_pid, String userId) {
		boolean falg=false;
		BatDepotIoDetail batDepotIoDetail = (BatDepotIoDetail)genericDao.getById(BatDepotIoDetail.class,f_pid);
		if (batDepotIoDetail != null){
			batDepotIoDetail.setSysflag("0");
			batDepotIoDetail.setLastmodifiedtime(DateBean.getSysdateTime());
			falg=true;
		}
		return falg;
	}

}
