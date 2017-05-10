package sail.beans.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sail.beans.dao.GenericDao;
import sail.beans.entity.BatBatAdjust;
import sail.beans.entity.BatBatAdjustDetail;
import sail.beans.entity.BatDepotIoBill;
import sail.beans.entity.BatDepotIoDetail;
import sail.beans.entity.BatDepotIoDetailList;
import sail.beans.entity.BatWorkOrder;
import sail.beans.entity.CarCode;
import sail.beans.entity.CarCodeRule;
import sail.beans.support.DateBean;

@Service
public class BatchStorageService {
	@Autowired
	private GenericDao genericDao;  
	
	/**
	 * 入库后台服务
	 * @param billNo
	 * @param docType
	 * @param matBatch
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class) 
	public BatDepotIoDetail saveBatchInStorage(String billNo,String docType,String matBatch,String busType,String userId){
		BatDepotIoBill batDepotIoBill = null;
		BatDepotIoDetail batDepotIoDetail=null;
		BatDepotIoDetailList batDepotIoDetailList=null;
		try{
			if(docType.equals("ZI30")){
				batDepotIoDetail=saveBatchInStorageTH(billNo,matBatch,userId);
				//batDepotIoDetail.setRemark4(matBatch);
			}else{
				List<BatDepotIoDetail> detailList = genericDao.getListWithVariableParas("BATCHDATA_BAT_DEPOT_IODETAIL", new Object[]{matBatch});
				if (detailList != null && detailList.size() > 0){
					batDepotIoDetail = detailList.get(0);
					if("1".equals(batDepotIoDetail.getRemark5())){
						batDepotIoDetail.setRepeated("1");
						return batDepotIoDetail;
					}
					batDepotIoDetail.setCreatetime(DateBean.getSysdateTime());
					batDepotIoDetail.setCreator(userId);
					batDepotIoDetail.setRemark5("1");
					batDepotIoDetail.setIsEnter("1");
					this.genericDao.save(batDepotIoDetail);
					//batDepotIoDetail.setRemark4(matBatch);
					
					batDepotIoBill=(BatDepotIoBill) genericDao.getById(BatDepotIoBill.class, batDepotIoDetail.getBillpid());
					if(billNo!=null&&!billNo.equals(""))
						batDepotIoBill.setBillno(billNo);
					Map map=this.getBatDepotStorageTotal(batDepotIoBill.getBillno(), docType);
					if(map.get("ruku").toString().equals(map.get("toatal").toString())){
						batDepotIoBill.setIsEnter("1");
					}else{
						batDepotIoBill.setIsEnter("0");
					}
					batDepotIoBill.setBiztype(busType);
					batDepotIoBill.setBilltype("11");
					batDepotIoBill.setDoctype(docType);
					batDepotIoBill.setCreatetime(DateBean.getSysdateTime());
					batDepotIoBill.setCreator(userId);
					//batDepotIoBill.setBillno(billNo); 待确认
					this.genericDao.save(batDepotIoBill);
					
					
					List<BatDepotIoDetailList> billDetailList = genericDao.getListWithVariableParas("BATCHDATA_DEPOT_IODETAILLIST_BYPID", new Object[]{batDepotIoDetail.getPid()});
					if(billDetailList!=null&&billDetailList.size()>0){
						for (int i = 0; i < billDetailList.size(); i++) {
							batDepotIoDetailList=billDetailList.get(i);
							batDepotIoDetailList.setRemark5("1");
							batDepotIoDetailList.setCreatetime(DateBean.getSysdateTime());
							batDepotIoDetailList.setCreator(userId);
							this.genericDao.save(batDepotIoDetailList);
						}
					}
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
		return batDepotIoDetail;
		
	}
	/**
	 * 生产领域物资退回入库
	 * */
	private BatDepotIoDetail saveBatchInStorageTH(String billNo, String matBatch,String userId) {
		double quality=0;
		String uint="";
		BatDepotIoBill batDepotIoBill = null;
		BatDepotIoDetail batDepotIoDetail=null;
		BatDepotIoDetailList batDepotIoDetailList=null;
		try {
			batDepotIoBill=new BatDepotIoBill();
			String bill=DateBean.getSysdate()+"ZI30";
			batDepotIoBill.setBillno(bill);
			batDepotIoBill.setBiztype("MM2143");
			batDepotIoBill.setBilltype("11");
			batDepotIoBill.setDoctype("ZI30");
			batDepotIoBill.setDepot("HZ10");
			batDepotIoBill.setFactory("2200");
			batDepotIoBill.setIsEnter("1");
			batDepotIoBill.setSysflag("1");
			batDepotIoBill.setDate(DateBean.getSysdate());
			batDepotIoBill.setCreatetime(DateBean.getSysdateTime());
			batDepotIoBill.setCreator(userId);
			this.genericDao.save(batDepotIoBill);
			//根据批次号到大小件表获取批次信息
			List<BatBatAdjustDetail> batBatAdjustDetailList=this.genericDao.getListWithVariableParas("STORAGE.T_BAT_BATADJUST_DETAILSLAVE.LIST", new Object[]{null,matBatch});
			if(batBatAdjustDetailList!=null&&batBatAdjustDetailList.size()>0){
				batDepotIoDetail=new BatDepotIoDetail();
				batDepotIoDetail.setBillpid(batDepotIoBill.getPid());
				batDepotIoDetail.setCreatetime(DateBean.getSysdateTime());
				batDepotIoDetail.setCreator(userId);
				batDepotIoDetail.setRemark5("1");
				batDepotIoDetail.setIsEnter("1");
				batDepotIoDetail.setSysflag("1");
				batDepotIoDetail.setMatbatch(matBatch);
				batDepotIoDetail.setMatcode(batBatAdjustDetailList.get(0).getMatcode());
				batDepotIoDetail.setMatname(batBatAdjustDetailList.get(0).getMatname());
				this.genericDao.save(batDepotIoDetail);
				for (int i = 0; i < batBatAdjustDetailList.size(); i++) {
					BatBatAdjustDetail batBatAdjustDetail=batBatAdjustDetailList.get(i);
					String slavbatch=batBatAdjustDetail.getSlavebatch();
					List <BatDepotIoDetailList> batDepotIoDetailList_=this.genericDao.getListWithVariableParas("storage.getbatDepotIoDetailList.bybatch", new Object[]{slavbatch});
					if(batDepotIoDetailList_!=null&&batDepotIoDetailList_.size()>0){
						batDepotIoDetailList=new BatDepotIoDetailList();
					//	batDepotIoDetailList=batDepotIoDetailList_.get(0);
						batDepotIoDetailList.setBilldetailpid(batDepotIoDetail.getPid());
						quality=quality+batDepotIoDetailList.getQuantity();
						batDepotIoDetailList.setCreatetime(DateBean.getSysdateTime());
						batDepotIoDetailList.setCreator(userId);
						batDepotIoDetailList.setSysflag("1");
						batDepotIoDetailList.setLastmodifiedtime(null);
						batDepotIoDetailList.setLastmodifiedtime(null);
						batDepotIoDetailList.setRemark5("1");
						uint=batDepotIoDetailList.getUnit();
						this.genericDao.save(batDepotIoDetailList);
					}
					
				}
				batDepotIoDetail.setQuantity(quality);
				batDepotIoDetail.setUnit(uint);
				this.genericDao.save(batDepotIoDetail);
			}else{
				List<BatDepotIoDetail> BatDepotIoDetailList1=this.genericDao.getListWithVariableParas("BATCHDATA_BAT_DEPOT_IODETAIL", new Object[]{matBatch});
				if(BatDepotIoDetailList1!=null&&BatDepotIoDetailList1.size()>0){
					BatDepotIoDetail batDepotIoDetail1=BatDepotIoDetailList1.get(0);
					batDepotIoDetail=new BatDepotIoDetail();
					batDepotIoDetail.setCreatetime(DateBean.getSysdateTime());
					batDepotIoDetail.setCreator(userId);
					batDepotIoDetail.setRemark5("1");
					batDepotIoDetail.setIsEnter("1");
					batDepotIoDetail.setSysflag("1");
					batDepotIoDetail.setBillpid(batDepotIoBill.getPid());
					batDepotIoDetail.setMatbatch(matBatch);
					batDepotIoDetail.setMatcode(batDepotIoDetail1.getMatcode());
					batDepotIoDetail.setMatname(batDepotIoDetail1.getMatname());
					batDepotIoDetail.setQuantity(batDepotIoDetail1.getQuantity());
					batDepotIoDetail.setUnit(batDepotIoDetail1.getUnit());
					this.genericDao.save(batDepotIoDetail);
					//根据PID
					List<BatDepotIoDetailList> billDetailList = genericDao.getListWithVariableParas("BATCHDATA_DEPOT_IODETAILLIST_BYPID", new Object[]{batDepotIoDetail1.getPid()});
					if(billDetailList!=null&&billDetailList.size()>0){
						for (int i = 0; i < billDetailList.size(); i++) {
							BatDepotIoDetailList batDepotIoDetailList1=billDetailList.get(i);
						    batDepotIoDetailList=new BatDepotIoDetailList();
							batDepotIoDetailList.setSlavebatch(batDepotIoDetailList1.getSlavebatch());
							batDepotIoDetailList.setQuantity(batDepotIoDetailList1.getQuantity());
							batDepotIoDetailList.setUnit(batDepotIoDetailList1.getUnit());
							batDepotIoDetailList.setSysflag("1");
							batDepotIoDetailList.setBilldetailpid(batDepotIoDetail.getPid());
							batDepotIoDetailList.setCreatetime(DateBean.getSysdateTime());
							batDepotIoDetailList.setCreator(userId);
							batDepotIoDetailList.setRemark5("1");
							this.genericDao.save(batDepotIoDetailList);
						}
					}
				}

			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		return batDepotIoDetail;
	}

	/**
	 * 根据单号和类型查询出明细数据
	 * @param billNo
	 * @param docType
	 * @return
	 */
	public List<BatDepotIoDetail> getBatDepotIoDetailList(String billNo,String docType,String remark5,String batch){
		List<BatDepotIoDetail> detailList = genericDao.getListWithVariableParas("STORAGE.T_BAT_DEPOT_IOBILLDETAIL.LIST", new Object[]{null,docType,remark5,batch});
		return detailList;
	}
	
	/**
	 * 删除出库入库对应明细数据
	 * @param detailpid
	 */
	@SuppressWarnings("finally")
	@Transactional(rollbackFor=Exception.class) 
	public boolean deleteBatDepotIoDetail(String detailpid,String operuser){
		boolean falg = false;
		try{
		BatDepotIoDetail batDepotIoDetail = (BatDepotIoDetail)genericDao.getById(BatDepotIoDetail.class,detailpid);
		if (batDepotIoDetail == null){
			return falg;
		}
		//入库信息回撤
		if("1".equals(batDepotIoDetail.getRemark5())){
			batDepotIoDetail.setIsEnter("0");
			batDepotIoDetail.setRemark5("");
			batDepotIoDetail.setLastmodifiedtime(DateBean.getSysdateTime());
			batDepotIoDetail.setLastmodifier(operuser);
			this.genericDao.save(batDepotIoDetail);
			//是否全部逻辑删除
			BatDepotIoBill batDepotIoBill=(BatDepotIoBill) genericDao.getById(BatDepotIoBill.class,batDepotIoDetail.getBillpid());
			Map map=getBatDepotStorageTotal(batDepotIoBill.getBillno(),batDepotIoBill.getDoctype());
			if((map.get("ruku").toString()).equals("0")){
				batDepotIoBill.setIsEnter("0");
				batDepotIoBill.setLastmodifiedtime(DateBean.getSysdateTime());
				batDepotIoBill.setLastmodifier(operuser);
				this.genericDao.save(batDepotIoBill);
			}
			List<BatDepotIoDetailList> batDepotIoDetailList = genericDao.getListWithVariableParas("BATCHDATA_DEPOT_IODETAILLIST_BYPID", new Object[]{batDepotIoDetail.getPid()});
			if(batDepotIoDetailList!=null&&batDepotIoDetailList.size()>0){
				for (int i = 0; i < batDepotIoDetailList.size(); i++) {
					BatDepotIoDetailList batDepotIoDetailLists=batDepotIoDetailList.get(i);
					batDepotIoDetailLists.setRemark5("");
					batDepotIoDetailLists.setLastmodifiedtime(DateBean.getSysdateTime());
					batDepotIoDetailLists.setLastmodifier(operuser);
					this.genericDao.save(batDepotIoDetailLists);
				}
			}
			falg = true;
			//出库信息删除
		}else if("2".equals(batDepotIoDetail.getRemark5())){
			batDepotIoDetail.setSysflag("0");
			batDepotIoDetail.setLastmodifiedtime(DateBean.getSysdateTime());
			batDepotIoDetail.setLastmodifier(operuser);
			genericDao.save(batDepotIoDetail);
			falg = true;
		}
		
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}finally{
			return falg;
		}
		
	}
	
	/*public boolean isAllBack(BatDepotIoDetail batDepotIoDetail){
		String billno=batDepotIoDetail.getBillpid();
		try{
			List<Object[]> ruku  = (List<Object[]>)genericDao.getListWithNativeSql("STORAGE.GET_STROEXINIX", new Object[]{billno});
			if(ruku!=null&&ruku.size()>0){
				Object[] rukulist=ruku.get(0);
				if("1".equals(rukulist[1].toString())){
					return true;
				}else{
					return false;
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			
		}
		return false;
	}*/
	/**
	 * 条形码解析
	 * @param matCode
	 */
	public CarCode getResolveValue(String match){
		CarCode carCode = new CarCode();
		List<Object[]> carList=null;
		carList=genericDao.getListWithNativeSql("STORAGE.GET_RESOLVEVAlUE", new Object[]{match});
		if(carList!=null&&carList.size()>0){
			Object[] cararray=carList.get(0);
			String storecode=(String) (cararray[8]==null?"":cararray[8]);
			carCode.setStroecode(storecode);
			String unit=(String) (cararray[7]==null?"":cararray[7]);
			carCode.setUnit(unit);
			String quality= (cararray[6]==null?"":cararray[6]).toString();
			carCode.setAmount(quality);
			String state=(String) (cararray[5]==null?"":cararray[5]);
			carCode.setState(state);
			String oldmatch=(String) (cararray[4]==null?"":cararray[4]);
			carCode.setOldbatch(oldmatch);
			String matname=(String) (cararray[3]==null?"":cararray[3]);
			carCode.setMatname(matname);
			String matcode=(String) (cararray[2]==null?"":cararray[2]);
			carCode.setMatcode(matcode);
			carCode.setValue2("1");
		}else if(carCode.getMatcode()==null){
			carList=genericDao.getListWithNativeSql("STORAGE.GET_RESOLVEVAlUE2", new Object[]{match});
			if(carList!=null&&carList.size()>0){
				Object[] cararray=carList.get(0);
				String storecode=(String) (cararray[8]==null?"":cararray[8]);
				carCode.setStroecode(storecode);
				String unit=(String) (cararray[7]==null?"":cararray[7]);
				carCode.setUnit(unit);
				String quality= (cararray[6]==null?"":cararray[6]).toString();
				carCode.setAmount(quality);
				String state=(String) (cararray[5]==null?"":cararray[5]);
				carCode.setState(state);
				String oldmatch=(String) (cararray[4]==null?"":cararray[4]);
				carCode.setOldbatch(oldmatch);
				String matname=(String) (cararray[3]==null?"":cararray[3]);
				carCode.setMatname(matname);
				String matcode=(String) (cararray[2]==null?"":cararray[2]);
				carCode.setMatcode(matcode);
				carCode.setValue2("2");
			}
		}
		if(carCode.getMatcode()==null){
			carList=genericDao.getListWithNativeSql("STORAGE.GET_RESOLVEVAlUE3", new Object[]{match});
			if(carList!=null&&carList.size()>0){
				Object[] cararray=carList.get(0);
				String matname=(String) (cararray[3]==null?"":cararray[3]);
				carCode.setMatname(matname);
				String matcode=(String) (cararray[2]==null?"":cararray[2]);
				carCode.setMatcode(matcode);
				String quality= (cararray[0]==null?"":cararray[0]).toString();
				carCode.setAmount(quality);
				String unit=(String) (cararray[1]==null?"":cararray[1]);
				carCode.setUnit(unit);
			//	carCode.setValue2("2");
		}
		}
		
		return carCode;
		}
		
	
	/**
	 * 物资批次大小件关系调整
	 * @param masterbatch
	 * @param slavebatch
	 * @param operuser
	 * @return
	 */
	
	
	public BatBatAdjustDetail saveBatchAdjustment(String masterbatch,String slavebatch,String operuser){
		BatBatAdjustDetail batBatAdjustDetail = null;
		List<BatBatAdjustDetail> masterList = genericDao.getListWithVariableParas("STORAGE.T_BAT_BATADJUST_DETAILSLAVE.LIST", new Object[]{null,masterbatch});
		//判断是否已经存在改批次的数据
		if (masterList != null && masterList.size() > 0){
			List<BatBatAdjustDetail> BatBatAdjustDetailList = genericDao.getListWithVariableParas("STORAGE.T_BAT_BATADJUST_DETAILSLAVE.LIST", new Object[]{slavebatch,null});
			//判断该小件批次的数据是否已经扫描过
			if (BatBatAdjustDetailList != null && BatBatAdjustDetailList.size() > 0){
				batBatAdjustDetail = BatBatAdjustDetailList.get(0);
				batBatAdjustDetail.setIsrepeat("1");
				return batBatAdjustDetail;
			}else{
				batBatAdjustDetail = new BatBatAdjustDetail();
				batBatAdjustDetail = this.saveDetail(masterList.get(0).getAdjustpid(), operuser, slavebatch, masterbatch);
				return batBatAdjustDetail;
			}
		}else{
			BatBatAdjust batBatAdjust = new BatBatAdjust();
			batBatAdjust.setAdjustno(DateBean.getSysdate()+""+masterbatch);
			batBatAdjust.setActflag("1");
			batBatAdjust.setFactory("2200 ");
			batBatAdjust.setDepot("HZ10");
			batBatAdjust.setOperatetime(DateBean.getSysdateTime());
			batBatAdjust.setOperateuserid(operuser);
			batBatAdjust.setSysflag("1");
			batBatAdjust.setCreator(operuser);
			batBatAdjust.setCreatetime(DateBean.getSysdateTime());
			genericDao.save(batBatAdjust);
			batBatAdjustDetail = this.saveDetail(batBatAdjust.getPid(), operuser, slavebatch, masterbatch);
			return batBatAdjustDetail;
		}
	}
	
	/**
	 * 组装明细数据
	 * @param adjustpid
	 * @param operuser
	 * @param slavebatch
	 * @param masterbatch
	 * @return
	 */
	public BatBatAdjustDetail saveDetail(String adjustpid,String operuser,String slavebatch,String masterbatch){
		BatBatAdjustDetail batBatAdjustDetail = new BatBatAdjustDetail();
		batBatAdjustDetail.setAdjustpid(adjustpid);
		CarCode carcode=this.getResolveValue(slavebatch);
		batBatAdjustDetail.setMatcode(carcode.getMatcode());
		batBatAdjustDetail.setMatname(carcode.getMatname());
		batBatAdjustDetail.setOldmasterbatch(carcode.getOldbatch());
		if(carcode.getStroecode()!=null&&!carcode.getStroecode().equals(""))
			batBatAdjustDetail.setSuppliersortcode(carcode.getStroecode());
		else
			batBatAdjustDetail.setSuppliersortcode("1");
		batBatAdjustDetail.setSlavebatch(slavebatch);
		batBatAdjustDetail.setNewmasterbatch(masterbatch+carcode.getMatcode());
		batBatAdjustDetail.setSysflag("1");
		batBatAdjustDetail.setCreator(operuser);
		batBatAdjustDetail.setCreatetime(DateBean.getSysdateTime());
		genericDao.save(batBatAdjustDetail);
		return batBatAdjustDetail;
	}
	
	/**
	 * 根据大件批次获取小件批次的数据
	 * @param masterbatch
	 * @return
	 */
	public List<BatBatAdjustDetail> getBatBatAdjustDetail(String masterbatch){
		List<BatBatAdjustDetail> BatBatAdjustDetailList = genericDao.getListWithVariableParas("STORAGE.T_BAT_BATADJUST_DETAILSLAVE.LIST", new Object[]{null,masterbatch});
		return BatBatAdjustDetailList;
	}
	
	/**
	 * 删除对应的小批次数据
	 * @param pid
	 * @param operuser
	 * @return
	 */
	public boolean deleteBatBatAdjustDetail(String pid,String operuser){
		boolean falg = false;
		BatBatAdjustDetail batBatAdjustDetail = (BatBatAdjustDetail)genericDao.getById(BatBatAdjustDetail.class, pid);
		batBatAdjustDetail.setSysflag("0");
		batBatAdjustDetail.setLastmodifier(operuser);
		batBatAdjustDetail.setLastmodifiedtime(DateBean.getSysdateTime());
		genericDao.save(batBatAdjustDetail);
		falg = true;
		return falg;
	}
	/**
	 * 出库保存接口
	 * @param 
	 * @param 
	 * @return
	 */
	 @Transactional(rollbackFor=Exception.class) 
	public BatDepotIoDetail saveBatchStorageOut(String f_bill_no,String f_doc_type,String f_bus_type,String f_mat_batch,String userId) {
		 BatDepotIoBill batDepotIoBill = null;
		 BatDepotIoDetail batDepotIoDetail=null;
		 BatDepotIoDetailList batDepotIoDetailList=null;
		 
		 try{
				List<BatDepotIoDetail> detailList = genericDao.getListWithVariableParas("BATCHDATA_BAT_DEPOT_IODETAIL", new Object[]{f_mat_batch});
				if (detailList != null && detailList.size() > 0){
					batDepotIoDetail = detailList.get(0);
					String p_id1=batDepotIoDetail.getPid();
					if("2".equals(batDepotIoDetail.getRemark5())){
						batDepotIoDetail.setRepeated("1");
						return batDepotIoDetail;
					}
					
					batDepotIoBill=new BatDepotIoBill();
					BatDepotIoBill batDepotIoBill1=(BatDepotIoBill) genericDao.getById(BatDepotIoBill.class, batDepotIoDetail.getBillpid());
					batDepotIoBill.setDepot(batDepotIoBill1.getDepot());
					batDepotIoBill.setFactory(batDepotIoBill1.getFactory());
					batDepotIoBill.setSysflag("1");
					batDepotIoBill.setIsEnter("1");
					batDepotIoBill.setDate(DateBean.getSysdate());
					batDepotIoBill.setBillno(f_bill_no);
					batDepotIoBill.setBiztype(f_bus_type);
					batDepotIoBill.setBilltype("12");
					batDepotIoBill.setDoctype(f_doc_type);
					batDepotIoBill.setCreatetime(DateBean.getSysdateTime());
					batDepotIoBill.setCreator(userId);
					batDepotIoBill.setOperateuserid(userId);
					batDepotIoBill.setOperatetime(DateBean.getSysdateTime());
					this.genericDao.save(batDepotIoBill);
					
					BatDepotIoDetail batDepotIoDetail1=new BatDepotIoDetail();
					//List<BatDepotIoBill> billList = genericDao.getListWithVariableParas("STORAGE.T_BAT_DEPOT_IOBILLLIST.LIST", new Object[]{f_bill_no,f_doc_type});
					String p_id=batDepotIoBill.getPid();
					batDepotIoDetail1.setMatbatch(batDepotIoDetail.getMatbatch());
					batDepotIoDetail1.setMatcode(batDepotIoDetail.getMatcode());
					batDepotIoDetail1.setMatname(batDepotIoDetail.getMatname());
					batDepotIoDetail1.setMatcode(batDepotIoDetail.getMatcode());
					batDepotIoDetail1.setQuantity(batDepotIoDetail.getQuantity());
					batDepotIoDetail1.setStatus(batDepotIoDetail.getStatus());
					batDepotIoDetail1.setUnit(batDepotIoDetail.getUnit());
					batDepotIoDetail1.setSuppliersortcode(batDepotIoDetail.getSuppliersortcode());
					batDepotIoDetail1.setTrayno(batDepotIoDetail.getTrayno());
					batDepotIoDetail1.setIsEnter("1");
					batDepotIoDetail1.setRemark5("2");
					batDepotIoDetail1.setBillpid(p_id);
					batDepotIoDetail1.setCreatetime(DateBean.getSysdateTime());
					batDepotIoDetail1.setCreator(userId);
					batDepotIoDetail1.setSysflag("1");
					this.genericDao.save(batDepotIoDetail1);
					//List<BatDepotIoDetail> detailList1 = genericDao.getListWithVariableParas("BATCHDATA_BAT_DEPOT_IODETAIL", new Object[]{f_mat_batch});
					
					List<BatDepotIoDetailList> billDetailList = genericDao.getListWithVariableParas("BATCHDATA_DEPOT_IODETAILLIST_BYPID", new Object[]{p_id1});
					if(billDetailList!=null&&billDetailList.size()>0){
						for (int i = 0; i < billDetailList.size(); i++) {
							batDepotIoDetailList=billDetailList.get(i);
							BatDepotIoDetailList batDepotIoDetailList1=new BatDepotIoDetailList();
							batDepotIoDetailList1.setSlavebatch(batDepotIoDetailList.getSlavebatch());
							batDepotIoDetailList1.setQuantity(batDepotIoDetailList.getQuantity());
							batDepotIoDetailList1.setUnit(batDepotIoDetailList.getUnit());
							batDepotIoDetailList1.setSysflag("1");
							batDepotIoDetailList1.setBilldetailpid(batDepotIoDetail1.getPid());
							batDepotIoDetailList1.setCreatetime(DateBean.getSysdateTime());
							batDepotIoDetailList1.setCreator(userId);
							batDepotIoDetailList1.setRemark5("2");
							this.genericDao.save(batDepotIoDetailList1);
						}
					}
				}
		 }catch(Exception e){
			 e.printStackTrace();
			 throw new RuntimeException();
		 }
		return batDepotIoDetail;
	}
	
	
	public List<BatDepotIoDetail> getBatDepotIoDetailListj(String f_match) {
		List<BatDepotIoDetail> detailList = genericDao.getListWithVariableParas("STORAGE.T_BAT_DEPOT_IOBILLDETAILJ.LIST", new Object[]{f_match});
		return detailList;
	}
/**
 * 根据单号获取此单号未完成批次列表
 * */
	public List<BatDepotIoDetail> getBatDepotValidate(String f_bill_no,String f_doc_type){
			List<BatDepotIoDetail> detailList = genericDao.getListWithVariableParas("STORAGE.T_BAT_STROAGVALIDATE_.LIST", new Object[]{f_bill_no,f_doc_type,null,"0"});
			return detailList;
	}
/**
 * 根据单号获取单号批次总量
 * */
	public Map getBatDepotStorageTotal(String f_bill_no,String f_doc_type) {
		int ruku=0;
		int total=0;
		Map map=new HashMap();
		try {
			List<BatDepotIoDetail> detailList = genericDao.getListWithVariableParas("STORAGE.T_BAT_STROAGVALIDATE_.LIST", new Object[]{f_bill_no,f_doc_type,null,null});
			if(detailList!=null&&detailList.size()>0){
				total=detailList.size();
				for(int i=0;i<total;i++){
				BatDepotIoDetail batDepotIoDetail=detailList.get(i);
				if("1".equals(batDepotIoDetail.getRemark5())){
					ruku++;
				}
				}
			}
			map.put("ruku",ruku);
			map.put("toatal",total);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return map;
	}

}
