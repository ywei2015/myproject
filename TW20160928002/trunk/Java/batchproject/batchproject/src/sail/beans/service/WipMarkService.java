package sail.beans.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sail.beans.dao.GenericDao;
import sail.beans.entity.BatWipMark;
import sail.beans.entity.BatWipMarkDetail;
import sail.beans.support.DateBean;

@Service
public class WipMarkService {
	@Autowired
	private GenericDao genericDao;
	
	public BatWipMarkDetail saveWipMark(String billno,String batchno, String refbatchno, String userId) {
		BatWipMarkDetail batWipMarkDetail =null;
		try{
			List<BatWipMark> batWipMarkList=this.genericDao.getListWithVariableParas("WIPMARK.WIPMARKLIST.LIST", new Object[]{billno});
			if (batWipMarkList != null && batWipMarkList.size() > 0){
				List<BatWipMarkDetail> batWipMarkDetailList=this.genericDao.getListWithVariableParas("WIPMARK.WIPMARKDATAILS.BYLIST", new Object[]{batchno,refbatchno});
				if(batWipMarkDetailList!=null&&batWipMarkDetailList.size()>0){
					batWipMarkDetail=batWipMarkDetailList.get(0);
					batWipMarkDetail.setIsrepeat("1");
				}else{
					BatWipMark BatWorkMark= batWipMarkList.get(0);
					batWipMarkDetail=new BatWipMarkDetail();
					batWipMarkDetail.setBatchno(batchno);
					batWipMarkDetail.setRefbatchno(refbatchno);
					batWipMarkDetail.setCreatetime(DateBean.getSysdateTime());
					batWipMarkDetail.setCreator(userId);
					batWipMarkDetail.setOpertime(DateBean.getSysdateTime());
					batWipMarkDetail.setRefbatchno(refbatchno);
					batWipMarkDetail.setSysflag("1");
					batWipMarkDetail.setTransferpid(BatWorkMark.getPid());
					this.genericDao.save(batWipMarkDetail);
				}
				
			}
				
		}catch (Exception e) {
			e.printStackTrace();
		}
		return batWipMarkDetail;
	}

	public List<BatWipMarkDetail> getWipMark(String batchno) {
		List<BatWipMarkDetail> detailList = genericDao.getListWithVariableParas("WIPMARK.WIPMARKDETAILS.LIST", new Object[]{batchno});
		return detailList;
	}

	public boolean deleteWipMark(String pid,String operuser) {
		boolean falg = false;
		BatWipMarkDetail batWipMarkDetail = (BatWipMarkDetail)genericDao.getById(BatWipMarkDetail.class, pid);
		batWipMarkDetail.setSysflag("0");
		batWipMarkDetail.setLastmodifier(operuser);
		batWipMarkDetail.setLastmodifiedtime(DateBean.getSysdateTime());
		genericDao.save(batWipMarkDetail);
		falg = true;
		return falg;
	}

	public List<String> getWipMarkBillNo() {
		String sql="select t.F_TRANSFER_BILL from T_BAT_WIP_MARK t where t.F_SYS_FLAG='1'";
		List<String> worklist=this.genericDao.getListWithNativeSql(sql);
		return worklist;
	}

}
