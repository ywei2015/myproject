package sail.beans.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sail.beans.dao.GenericDao;
import sail.beans.entity.BatWipMark;
import sail.beans.entity.BatWipMarkDetail;
import sail.beans.support.DateBean;

@Service
public class WipMarkService {
	@Autowired
	private GenericDao genericDao;
	
	@Transactional
	public BatWipMarkDetail saveWipMark(String billno,String batchno, String refbatchno, String userId) {
		BatWipMarkDetail batWipMarkDetail =null;
		BatWipMark batWorkMark=null;
		String batchnoj=null;//一号工程码
		String refbatchnoj=null;//陕西中烟质量追溯码下段码
		String f_ucode=null;//浙江中烟质量追溯码上段码
		String f_lcode=null;//浙江中烟质量追溯码下段码
		String f_remark4=null;//陕西中烟质量追溯码上段码
		try{
			//验证一号工程码
			List<String> batchnoList=valiteWipMarkNo(batchno);
			//验证质量追溯码
			List<String> refbatchnoList=valiteWipMarkNo(refbatchno);
			if(batchnoList==null||batchnoList.size()==0){
				batWipMarkDetail=new BatWipMarkDetail();
				batWipMarkDetail.setRemark5("01");//一号工程码信息不存在
				return batWipMarkDetail;
			}
			if(refbatchnoList==null||refbatchnoList.size()==0){
				batWipMarkDetail=new BatWipMarkDetail();
				batWipMarkDetail.setRemark5("02");//质量追溯码信息不存在
				return batWipMarkDetail;
			}
			if("00".equals(batchnoList.get(0))){
				batchnoj=batchnoList.get(2);
			}else if("01".equals(batchnoList.get(0))){
				refbatchnoj=batchnoList.get(4);
				f_ucode=batchnoList.get(1);
				f_lcode=batchnoList.get(2);
				f_remark4=batchnoList.get(3);
			}
			if("00".equals(refbatchnoList.get(0))){
				batchnoj=refbatchnoList.get(2);
			}else if("01".equals(refbatchnoList.get(0))){
				refbatchnoj=refbatchnoList.get(4);
				f_ucode=refbatchnoList.get(1);
				f_lcode=refbatchnoList.get(2);
				f_remark4=refbatchnoList.get(3);
			}
			if(batchnoj==null){
				batWipMarkDetail=new BatWipMarkDetail();
				batWipMarkDetail.setRemark5("01");//一号工程码信息不存在
				return batWipMarkDetail;
			}
			if(refbatchnoj==null){
				batWipMarkDetail=new BatWipMarkDetail();
				batWipMarkDetail.setRemark5("02");//质量追溯码信息不存在
				return batWipMarkDetail;
			}
			List<BatWipMarkDetail> batWipMarkDetailList=this.genericDao.getListWithVariableParas("WIPMARK.WIPMARKDATAILS.BYLIST", new Object[]{null,refbatchnoj});
			if(batWipMarkDetailList!=null&&batWipMarkDetailList.size()>0){
				batWipMarkDetail=batWipMarkDetailList.get(0);
				batWipMarkDetail.setIsrepeat("1");
				return batWipMarkDetail;
			}else{
				List<BatWipMark> batWorkMarkList=this.genericDao.getListWithVariableParas("WIPMARK.WIPMARKLIST.LIST", new Object[]{DateBean.getSysdate()});
				if (batWorkMarkList != null && batWorkMarkList.size() > 0){
					batWorkMark=batWorkMarkList.get(0);
				}else{
					batWorkMark= new BatWipMark();
					batWorkMark.setDate(DateBean.getSysdate());
					batWorkMark.setCreatetime(DateBean.getSysdateTime());
					batWorkMark.setCreator(userId);
					batWorkMark.setSysflag("1");
					batWorkMark.setTransferbill(DateBean.getSysdate());
					batWorkMark.setFactory("2200");
					batWorkMark.setLgort("HZ30");
					this.genericDao.save(batWorkMark);
				}
				batWipMarkDetail=new BatWipMarkDetail();
				batWipMarkDetail.setBatchno(batchnoj);
				batWipMarkDetail.setRefbatchno(f_lcode);
				batWipMarkDetail.setRemark3(f_ucode);
				batWipMarkDetail.setRemark4(f_remark4);
				batWipMarkDetail.setRemark5(refbatchnoj);
				batWipMarkDetail.setCreatetime(DateBean.getSysdateTime());
				batWipMarkDetail.setCreator(userId);
				batWipMarkDetail.setOpertime(DateBean.getSysdateTime());
				batWipMarkDetail.setRemark("PDA");
				batWipMarkDetail.setSysflag("1");
				batWipMarkDetail.setLastmodifiedtime(DateBean.getSysdateTime());
				batWipMarkDetail.setLastmodifier(userId);
				batWipMarkDetail.setTransferpid(batWorkMark.getPid());
				this.genericDao.save(batWipMarkDetail);
		}	
		}catch (Exception e) {
			e.printStackTrace();
		}
		return batWipMarkDetail;
	}

	public List<BatWipMarkDetail> getWipMark(String batchno) {
		String date=DateBean.getSysdate();
		List<BatWipMarkDetail> detailList = genericDao.getListWithVariableParas("WIPMARK.WIPMARKDETAILS.LIST", new Object[]{batchno,date});
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
	
	/**
	 * 获取扫码信息
	 * */
	public List<String> valiteWipMarkNo(String no){
		List<String> list=new ArrayList();
		String sql="select  F_CODE_TYPE,F_UCODE,F_LCODE,F_REMARK4,F_REMARK5 " +
				"FROM T_BAT_PACKBOX_CODE tt where tt.f_sys_flag='2' and tt.F_LCODE='"+no+"'";
		String sql2="select  F_CODE_TYPE,F_UCODE,F_LCODE,F_REMARK4,F_REMARK5 " +
				"FROM T_BAT_PACKBOX_CODE tt where tt.f_sys_flag='1' and tt.f_remark5='"+no+"'";
		List<Object[]> worklist=this.genericDao.getListWithNativeSql(sql);
		List<Object[]> worklist2=this.genericDao.getListWithNativeSql(sql2);
		if(worklist!=null&&worklist.size()>0){
			Object[] lisObject=worklist.get(0);
			list.add(lisObject[0]==null?"":lisObject[0].toString());
			list.add(lisObject[1]==null?"":lisObject[1].toString());
			list.add(lisObject[2]==null?"":lisObject[2].toString());
			list.add(lisObject[3]==null?"":lisObject[3].toString());
			list.add(lisObject[4]==null?"":lisObject[4].toString());
		}else if(worklist2!=null&&worklist2.size()>0){
			Object[] lisObject=worklist2.get(0);
			list.add(lisObject[0]==null?"":lisObject[0].toString());
			list.add(lisObject[1]==null?"":lisObject[1].toString());
			list.add(lisObject[2]==null?"":lisObject[2].toString());
			list.add(lisObject[3]==null?"":lisObject[3].toString());
			list.add(lisObject[4]==null?"":lisObject[4].toString());
		}
		return list;
	}
}
