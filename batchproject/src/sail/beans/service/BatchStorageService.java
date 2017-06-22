package sail.beans.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sail.beans.dao.GenericDao;
import sail.beans.entity.BatBatAdjust;
import sail.beans.entity.BatBatAdjustDetail;
import sail.beans.entity.BatDepotIoBill;
import sail.beans.entity.BatDepotIoDetail;
import sail.beans.entity.BatDepotIoDetailList;
import sail.beans.entity.CarCode;
import sail.beans.support.DateBean;

@Service
public class BatchStorageService {
	@Autowired
	private GenericDao genericDao;  
	
	@Resource
	private UserService userService; 
	
	@Autowired
	private BatchResolveValue batchResolveValue;
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
				return batDepotIoDetail;
			}else if(docType.equals("ZI101")){
				batDepotIoDetail=saveBatchInStorageForSiShu(matBatch,userId);
				return batDepotIoDetail;
			}
			//检查数据库是否存在原始数据，批次是否已经存在
			List<BatDepotIoDetail> detailList = genericDao.getListWithVariableParas("STORAGE.T_BAT_DEPOT_IOBILLDETAIL.LIST", new Object[]{null,docType,null,matBatch});
			if (detailList != null && detailList.size() > 0){
				batDepotIoDetail = detailList.get(0);
				if("1".equals(batDepotIoDetail.getIsEnter())){
					batDepotIoDetail.setRepeated("1");
					return batDepotIoDetail;
				}
				batDepotIoDetail.setLastmodifiedtime(DateBean.getSysdateTime());
				batDepotIoDetail.setLastmodifier(userId);
				batDepotIoDetail.setRemark5("1");
				batDepotIoDetail.setIsEnter("1");
				batDepotIoDetail.setShkzg("S");
				batDepotIoDetail.setStatus("A");
				batDepotIoDetail.setInventorytype("0");
				this.genericDao.save(batDepotIoDetail);
				//更新大件对应小件
				List<BatDepotIoDetailList> billDetailList = genericDao.getListWithVariableParas("BATCHDATA_DEPOT_IODETAILLIST_BYPID", new Object[]{batDepotIoDetail.getPid()});
				if(billDetailList!=null&&billDetailList.size()>0){
					for (int i = 0; i < billDetailList.size(); i++) {
						batDepotIoDetailList=billDetailList.get(i);
						batDepotIoDetailList.setRemark5("1");
						batDepotIoDetailList.setLastmodifiedtime(DateBean.getSysdateTime());
						batDepotIoDetailList.setLastmodifier(userId);
						this.genericDao.save(batDepotIoDetailList);
					}
				}
				//更新单据表
				batDepotIoBill=(BatDepotIoBill) genericDao.getById(BatDepotIoBill.class, batDepotIoDetail.getBillpid());
				if(billNo!=null&&!billNo.equals(""))
					batDepotIoBill.setBillno(billNo);
				batDepotIoBill.setIsEnter("1");
				Map map=this.getBatDepotStorageTotal(batDepotIoBill.getBillno(), docType);
				if(map.get("ruku").toString().equals(map.get("toatal").toString())){
					batDepotIoBill.setIsEnter("1");
				}else{
					batDepotIoBill.setIsEnter("0");
				}
				batDepotIoBill.setOperatetime(DateBean.getSysdateTime());
				batDepotIoBill.setOperateuserid(userId);
				batDepotIoBill.setDate(DateBean.getSysdate());
				batDepotIoBill.setBiztype(busType);
				batDepotIoBill.setBilltype("11");
				batDepotIoBill.setDoctype(docType);
				batDepotIoBill.setLastmodifiedtime(DateBean.getSysdateTime());
				batDepotIoBill.setLastmodifier(userId);
				this.genericDao.save(batDepotIoBill);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
		return batDepotIoDetail;
		
	}
	
	/**
	 * 丝束入库
	 * */
	private BatDepotIoDetail saveBatchInStorageForSiShu(String matBatch,String userId) {
		double quanlity;
		BatDepotIoBill batDepotIoBill = null;
		BatDepotIoDetail batDepotIoDetail=null;
		String bill="SS303200"+DateBean.getSysdate("YYYYMMDDHH");
		List<BatDepotIoDetail> detailList = genericDao.getListWithVariableParas("STORAGE.T_BAT_DEPOT_IOBILLDETAIL.LIST", new Object[]{null,"ZI20",null,matBatch});
		if (detailList != null && detailList.size() > 0){
			batDepotIoDetail = detailList.get(0);
			batDepotIoDetail.setRepeated("1");
			return batDepotIoDetail;
		}else{
			List<BatDepotIoBill> batDepotIoBillList = genericDao.getListWithVariableParas("STORAGE.T_BAT_DEPOT_IOBILLLIST.LIST", new Object[]{bill,"ZI20"});
			if(batDepotIoBillList!=null&&batDepotIoBillList.size()>0){
				batDepotIoBill=batDepotIoBillList.get(0);
			}else{
				batDepotIoBill=new BatDepotIoBill();
				batDepotIoBill.setBillno(bill);
				batDepotIoBill.setBiztype("MM2141");
				batDepotIoBill.setBilltype("11");
				batDepotIoBill.setDoctype("ZI20");
				batDepotIoBill.setDepot("HZ10");
				batDepotIoBill.setFactory("2200");
				batDepotIoBill.setIsEnter("1");
				batDepotIoBill.setSysflag("1");
				batDepotIoBill.setWorkshop("2220");
				batDepotIoBill.setCreator(userId);
				batDepotIoBill.setCreatetime(DateBean.getSysdateTime());
				batDepotIoBill.setDate(DateBean.getSysdate());
				batDepotIoBill.setOperatetime(DateBean.getSysdateTime());
				batDepotIoBill.setOperateuserid(userId);
				batDepotIoBill.setLastmodifiedtime(DateBean.getSysdateTime());
				batDepotIoBill.setLastmodifier(userId);
			}	
			this.genericDao.save(batDepotIoBill);
			batDepotIoDetail=new BatDepotIoDetail();
			batDepotIoDetail.setBillpid(batDepotIoBill.getPid());
			batDepotIoDetail.setLastmodifiedtime(DateBean.getSysdateTime());
			batDepotIoDetail.setLastmodifier(userId);
			batDepotIoDetail.setCreator(userId);
			batDepotIoDetail.setCreatetime(DateBean.getSysdateTime());
			batDepotIoDetail.setRemark5("1");
			batDepotIoDetail.setIsEnter("1");
			batDepotIoDetail.setSysflag("1");
			batDepotIoDetail.setMatbatch(matBatch);
			batDepotIoDetail.setMatcode("201010002");
			batDepotIoDetail.setMatkl("2001");
			batDepotIoDetail.setShkzg("S");
			batDepotIoDetail.setStatus("A");
			batDepotIoDetail.setInventorytype("0");
			batDepotIoDetail.setSuppliersortcode("XA");
			batDepotIoDetail.setMatname("二醋酸纤维丝束(3.0Y/32000)");
		//	batDepotIoDetail.setQuantity(quantity);
			try {
				quanlity=Double.parseDouble(matBatch.substring(matBatch.length()-4, matBatch.length()))/10;
			} catch (Exception e) {
				quanlity=320;
			}
			batDepotIoDetail.setUnit("KG");
			batDepotIoDetail.setQuantity(quanlity);
			this.genericDao.save(batDepotIoDetail);	
			
		}	
		
		return batDepotIoDetail;
	}
	/**
	 * 生产领域物资退回入库
	 * */
	private BatDepotIoDetail saveBatchInStorageTH(String billNo, String matBatch,String userId) {
		BatDepotIoBill batDepotIoBill = null;
		BatDepotIoDetail batDepotIoDetail=null;
		BatDepotIoDetailList batDepotIoDetailList=null;
		String bill=DateBean.getSysdate()+"ZI30";
		try {
			List<BatDepotIoDetail> detailList = genericDao.getListWithVariableParas("STORAGE.T_BAT_DEPOT_IOBILLDETAIL.LIST", new Object[]{null,"ZI30",null,matBatch});
			if (detailList != null && detailList.size() > 0){
				batDepotIoDetail = detailList.get(0);
				if("1".equals(batDepotIoDetail.getIsEnter())){
					batDepotIoDetail.setRepeated("1");
					return batDepotIoDetail;
				}
			}
			List<BatDepotIoBill> batDepotIoBillList = genericDao.getListWithVariableParas("STORAGE.T_BAT_DEPOT_IOBILLLIST.LIST", new Object[]{bill,"ZI30"});
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
				batDepotIoBill.setCreator(userId);
				batDepotIoBill.setCreatetime(DateBean.getSysdateTime());
				batDepotIoBill.setDate(DateBean.getSysdate());
				batDepotIoBill.setOperatetime(DateBean.getSysdateTime());
				batDepotIoBill.setOperateuserid(userId);
				batDepotIoBill.setLastmodifiedtime(DateBean.getSysdateTime());
				batDepotIoBill.setLastmodifier(userId);
				this.genericDao.save(batDepotIoBill);
			}
			//根据批次号到出入库表获取批次信息
			List<BatDepotIoDetail> BatDepotIoDetailList1=this.genericDao.getListWithVariableParas("STORAGE.T_BAT_DEPOT_IOBILLDETAIL.LIST", new Object[]{null,null,null,matBatch});
			if(BatDepotIoDetailList1!=null&&BatDepotIoDetailList1.size()>0){
				batDepotIoDetail=saveBatDepotIoDetail(BatDepotIoDetailList1,batDepotIoBill,matBatch,userId);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		return batDepotIoDetail;
	}

	
	
	
	/**
	 * 根据大件批次号从入库表获取大小件信息，进行一次退回入库的操作
	 * @param billNo
	 * @param docType
	 * @return
	 */
	public BatDepotIoDetail saveBatDepotIoDetail(List<BatDepotIoDetail> BatDepotIoDetailList,BatDepotIoBill batDepotIoBill,String matBatch,String userId){
		BatDepotIoDetail batDepotIoDetail=null;
		BatDepotIoDetailList batDepotIoDetailList=null;
		BatDepotIoDetail batDepotIoDetail1=BatDepotIoDetailList.get(0);
		batDepotIoDetail=new BatDepotIoDetail();
		batDepotIoDetail.setBillpid(batDepotIoBill.getPid());
		batDepotIoDetail.setLastmodifiedtime(DateBean.getSysdateTime());
		batDepotIoDetail.setRemark5("1");
		batDepotIoDetail.setIsEnter("1");
		batDepotIoDetail.setSysflag("1");
		batDepotIoDetail.setShkzg("S");
		batDepotIoDetail.setStatus("A");
		batDepotIoDetail.setInventorytype("0");
		batDepotIoDetail.setBillpid(batDepotIoBill.getPid());
		batDepotIoDetail.setMatbatch(matBatch);
		batDepotIoDetail.setMatkl(batDepotIoDetail1.getMatkl());
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
				batDepotIoDetailList.setLastmodifiedtime(DateBean.getSysdateTime());
				batDepotIoDetailList.setLastmodifier(userId);
				batDepotIoDetailList.setRemark5("1");
				this.genericDao.save(batDepotIoDetailList);
			}
		}
		return batDepotIoDetail;
	
	}

	
	/**
	 * 根据单号和类型查询出明细数据
	 * @param billNo
	 * @param docType
	 * @return
	 */
	public List<BatDepotIoDetail> getBatDepotIoDetailList(String billNo,String docType,String batch,String remark){
		List<BatDepotIoDetail> detailList=null;
		detailList = genericDao.getListWithVariableParas("STORAGE.T_BAT_DEPOT_IOBILLDETAIL2.LIST", new Object[]{billNo,docType,remark,batch});
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
			if(Long.parseLong((map.get("ruku").toString()))<Long.parseLong((map.get("toatal").toString()))){
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
			List<BatDepotIoDetailList> batDepotIoDetailList = genericDao.getListWithVariableParas("BATCHDATA_DEPOT_IODETAILLIST_BYPID", new Object[]{batDepotIoDetail.getPid()});
			if(batDepotIoDetailList!=null&&batDepotIoDetailList.size()>0){
				for (int i = 0; i < batDepotIoDetailList.size(); i++) {
					BatDepotIoDetailList batDepotIoDetailLists=batDepotIoDetailList.get(i);
					batDepotIoDetailLists.setSysflag("0");
					batDepotIoDetailLists.setLastmodifiedtime(DateBean.getSysdateTime());
					batDepotIoDetailLists.setLastmodifier(operuser);
					this.genericDao.save(batDepotIoDetailLists);
				}
			}
			falg = true;
		}
		
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}finally{
			return falg;
		}
		
	}
	
	
	/**
	 * 条形码解析
	 * @param type 
	 * @param matCode
	 */
	public CarCode getResolveValue(String match, String type){
		CarCode carCode=null;
		carCode=batchResolveValue.getResolveValue(match, type);
		return carCode;
	}
		
	
	/**
	 * 物资批次大小件关系调整
	 * @param masterbatch
	 * @param slavebatch
	 * @param operuser
	 * @return
	 */
	public BatBatAdjustDetail saveBatchAdjustment(final String masterbatch,final String slavebatch,final String operuser){
		BatBatAdjustDetail batBatAdjustDetail = null;
		final CarCode carcode=this.getResolveValue(slavebatch,"JM01");
		try {
			List<BatBatAdjustDetail> masterList = genericDao.getListWithVariableParas("STORAGE.T_BAT_BATADJUST_DETAILSLAVE.LIST", new Object[]{null,masterbatch});
			//判断是否已经存在改批次的数据
			if (masterList != null && masterList.size() > 0){
				List<BatBatAdjustDetail> BatBatAdjustDetailList = genericDao.getListWithVariableParas("STORAGE.T_BAT_BATADJUST_DETAILSLAVE.LIST", new Object[]{slavebatch,null});
				//判断该小件批次的数据是否已经扫描过
				if (BatBatAdjustDetailList != null && BatBatAdjustDetailList.size() > 0){
					batBatAdjustDetail = BatBatAdjustDetailList.get(0);
					batBatAdjustDetail.setIsrepeat("1");
				}else{
					batBatAdjustDetail = new BatBatAdjustDetail();
					batBatAdjustDetail = this.saveDetail(masterList.get(0).getAdjustpid(), operuser, slavebatch, masterbatch);
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
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return batBatAdjustDetail;
	}
	
	/**
	 * 组装明细数据
	 * @param adjustpid
	 * @param operuser
	 * @param slavebatch
	 * @param masterbatch
	 * @return
	 */
	public BatBatAdjustDetail saveDetail(String adjustpid,final String operuser,final String slavebatch,final String masterbatch){
		BatBatAdjustDetail batBatAdjustDetail = new BatBatAdjustDetail();
		batBatAdjustDetail.setAdjustpid(adjustpid);
		final CarCode carcode=this.getResolveValue(slavebatch,"JM01");
		batBatAdjustDetail.setMatcode(carcode.getMatcode());
		batBatAdjustDetail.setMatname(carcode.getMatname());
		batBatAdjustDetail.setOldmasterbatch(carcode.getOldbatch());
		if(carcode.getStroecode()!=null&&!carcode.getStroecode().equals(""))
			batBatAdjustDetail.setSuppliersortcode(carcode.getStroecode());
		else
			batBatAdjustDetail.setSuppliersortcode("1");
		batBatAdjustDetail.setSlavebatch(slavebatch);
		batBatAdjustDetail.setNewmasterbatch(masterbatch);
		batBatAdjustDetail.setSysflag("1");
		batBatAdjustDetail.setCreator(operuser);
		batBatAdjustDetail.setCreatetime(DateBean.getSysdateTime());
		genericDao.save(batBatAdjustDetail);
		/*ExecutorService service= Executors.newFixedThreadPool(1);
	    Runnable run = new Runnable() {
             public void run() {
            	 saveBatchInStorageForAdjust(operuser, slavebatch, masterbatch, carcode);
             }
         };
         service.execute(run);*/
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
				List<BatDepotIoDetail> detailList = genericDao.getListWithVariableParas("STORAGE.T_BAT_DEPOT_IOBILLDETAIL.LIST", new Object[]{null,null,null,f_mat_batch});
				if (detailList != null && detailList.size() > 0){
					BatDepotIoDetail batDepotIoDetail1 = detailList.get(0);
					String pid=batDepotIoDetail1.getPid();
					if("2".equals(batDepotIoDetail1.getRemark5())){
						batDepotIoDetail=new BatDepotIoDetail();
						batDepotIoDetail.setRepeated("1");
						return batDepotIoDetail;
					}
					List<BatDepotIoBill> batDepotIoBillList = genericDao.getListWithVariableParas("STORAGE.T_BAT_DEPOT_IOBILLLIST.LIST", new Object[]{f_bill_no,f_doc_type});
					if (batDepotIoBillList != null && batDepotIoBillList.size() > 0)
						batDepotIoBill=batDepotIoBillList.get(0);
					else{
						batDepotIoBill=new BatDepotIoBill();	
						BatDepotIoBill batDepotIoBill1=(BatDepotIoBill) genericDao.getById(BatDepotIoBill.class, batDepotIoDetail1.getBillpid());
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
						batDepotIoBill.setLastmodifiedtime(DateBean.getSysdateTime());
						batDepotIoBill.setLastmodifier(userId);
						this.genericDao.save(batDepotIoBill);
					}
				   batDepotIoDetail=new BatDepotIoDetail();
					//List<BatDepotIoBill> billList = genericDao.getListWithVariableParas("STORAGE.T_BAT_DEPOT_IOBILLLIST.LIST", new Object[]{f_bill_no,f_doc_type});
					String p_id=batDepotIoBill.getPid();
					batDepotIoDetail.setMatbatch(batDepotIoDetail1.getMatbatch());
					batDepotIoDetail.setMatcode(batDepotIoDetail1.getMatcode());
					batDepotIoDetail.setMatname(batDepotIoDetail1.getMatname());
					batDepotIoDetail.setMatcode(batDepotIoDetail1.getMatcode());
					batDepotIoDetail.setQuantity(batDepotIoDetail1.getQuantity());
					batDepotIoDetail.setMatkl(batDepotIoDetail1.getMatkl());
					batDepotIoDetail.setStatus(batDepotIoDetail1.getStatus());
					batDepotIoDetail.setUnit(batDepotIoDetail1.getUnit());
					batDepotIoDetail.setSuppliersortcode(batDepotIoDetail1.getSuppliersortcode());
					batDepotIoDetail.setTrayno(batDepotIoDetail1.getTrayno());
					batDepotIoDetail.setIsEnter("1");
					batDepotIoDetail.setRemark5("2");
					batDepotIoDetail.setBillpid(p_id);
					batDepotIoDetail.setShkzg("H");
					batDepotIoDetail.setStatus("A");
					batDepotIoDetail.setInventorytype("0");
					batDepotIoDetail.setCreatetime(DateBean.getSysdateTime());
					batDepotIoDetail.setLastmodifiedtime(DateBean.getSysdateTime());
					batDepotIoDetail.setLastmodifier(userId);
					batDepotIoDetail.setCreator(userId);
					batDepotIoDetail.setSysflag("1");
					this.genericDao.save(batDepotIoDetail);
					
					List<BatDepotIoDetailList> billDetailList = genericDao.getListWithVariableParas("BATCHDATA_DEPOT_IODETAILLIST_BYPID", new Object[]{pid});
					if(billDetailList!=null&&billDetailList.size()>0){
						for (int i = 0; i < billDetailList.size(); i++) {
							batDepotIoDetailList=billDetailList.get(i);
							BatDepotIoDetailList batDepotIoDetailList1=new BatDepotIoDetailList();
							batDepotIoDetailList1.setSlavebatch(batDepotIoDetailList.getSlavebatch());
							batDepotIoDetailList1.setQuantity(batDepotIoDetailList.getQuantity());
							batDepotIoDetailList1.setUnit(batDepotIoDetailList.getUnit());
							batDepotIoDetailList1.setSysflag("1");
							batDepotIoDetailList1.setBilldetailpid(batDepotIoDetail.getPid());
							batDepotIoDetailList1.setCreatetime(DateBean.getSysdateTime());
							batDepotIoDetailList1.setCreator(userId);
							batDepotIoDetailList1.setRemark5("2");
							batDepotIoDetailList1.setLastmodifiedtime(DateBean.getSysdateTime());
							batDepotIoDetailList1.setLastmodifier(userId);
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
	
	
/**
 * 根据单号获取此单号未完成批次列表
 * */
	public List<BatDepotIoDetail> getBatDepotValidate(String f_bill_no,String f_doc_type){
			List<BatDepotIoDetail> detailList = genericDao.getListWithVariableParas("STORAGE.T_BAT_DEPOT_IOBILLDETAIL.LIST", new Object[]{f_bill_no,f_doc_type,'0',null});
			return detailList;
	}
/**
 * 入库根据单号获取单号批次总量
 * */
	public Map getBatDepotStorageTotal(String f_bill_no,String f_doc_type) {
		int ruku=0;
		int total=0;
		Map map=new HashMap();
		try {
			List<BatDepotIoDetail> detailList = genericDao.getListWithVariableParas("STORAGE.T_BAT_DEPOT_IOBILLDETAIL.LIST", new Object[]{f_bill_no,f_doc_type,null,null});
			if(detailList!=null&&detailList.size()>0){
				total=detailList.size();
				for(int i=0;i<total;i++){
				BatDepotIoDetail batDepotIoDetail=detailList.get(i);
				if("1".equals(batDepotIoDetail.getIsEnter())){
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

public List<String> getBillNoList(String userId,String userCode) {
	List<String> billList= new ArrayList<String>();
	int todayNumber,yesNumber;
	String today=DateBean.getSysdate();
	String yestoday=DateBean.getBeforDay(today, 1);
	//String userCode=userService.getEsbCodeById(userId);
	String sql="select count(1) from t_bat_depot_iobill u where u.F_SYS_FLAG='1' and " +
			"substr(u.F_CREATE_TIME,0,8)=to_char(sysdate,'YYYYMMDD') and u.F_CREATOR='"+userId+"'";
	String sql1="select count(1) from t_bat_depot_iobill u where u.F_SYS_FLAG='1'and " +
			"substr(u.F_CREATE_TIME,0,8)=to_char(sysdate-1,'YYYYMMDD') and u.F_CREATOR='"+userId+"'";
	List<Object> billNoT=this.genericDao.getListWithNativeSql(sql);
	List<Object> billNoY=this.genericDao.getListWithNativeSql(sql1);
	if(billNoY.size()>0){
		yesNumber=((BigDecimal)billNoY.get(0)).intValue();
		if(yesNumber>0){
			billList.add(yestoday+userCode+"0"+yesNumber);
		}
	}
	if(billNoT!=null&&billNoT.size()>0){
		todayNumber=((BigDecimal) billNoT.get(0)).intValue();
		for (int i = 0; i < todayNumber+1; i++) {
			String billno=today+userCode+"0"+(i+1);
			billList.add(billno);
		}
	}
	return billList;
}

}
