package sail.beans.service.retrospect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sail.beans.dao.GenericDao;
import sail.beans.dao.iml.Pager;
import sail.beans.entity.BatchRetrospect;
import sail.beans.entity.FeedingStatistic;
import sail.beans.entity.PersonalFeedingStatistic;
import sail.beans.entity.ProductionWorkorder;
import sail.beans.entity.TopDressFlavor;
import sail.beans.entity.UnacceptMaterial;
import sail.beans.entity.UnacceptMaterialDetail;

@Service
public class MaterialUnacceptService {
	@Autowired
	private GenericDao genericDao;
	
	/**
	 * 物资确认情况
	 * @param pageSize
	 * @param pageNumber
	 * @param mat_code
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Pager materialUnacceptForBase(int pageSize, int pageNumber,String mat_code,String startDate,String endDate) {
		Pager page=null;
		
		try {
			int starnumber=pageSize*(pageNumber-1);
			List<UnacceptMaterial> unacceptMateriallist=new ArrayList<>();
		    page=genericDao.getListWithNativeSqlFY("unaccept.base.list", new Object[]{mat_code,startDate,endDate},new int[]{starnumber,pageSize});
		    //测试
		   /* for (Object obj : page.getList()) {
		    	Object[] objj = (Object[]) obj;
				System.out.println(Arrays.toString(objj));
			}*/
		    if(page!=null){
				List<Object[]> RetrospectList=(List<Object[]>) page.getList();
				if(RetrospectList!=null&&RetrospectList.size()>0){
					for (int i = 0; i < RetrospectList.size(); i++) {
						Object[] result=RetrospectList.get(i);
//						System.out.println(Arrays.toString(result));
						UnacceptMaterial batchRetrospect=new UnacceptMaterial();
						batchRetrospect.setBillGiveDate(result[0].toString());
						batchRetrospect.setMatCode(result[1]==null?"":result[1].toString());
						batchRetrospect.setMatName(result[2]==null?"":result[2].toString());
						batchRetrospect.setBatchCountTotal(result[3]==null?"":result[3].toString());
						batchRetrospect.setBatchCountY(result[4]==null?"":result[4].toString());
						batchRetrospect.setBatchCountN(result[5]==null?"":result[5].toString());
						batchRetrospect.setQuantity(result[6]==null?"":result[6].toString());
						unacceptMateriallist.add(batchRetrospect);
					}
					page.setList(unacceptMateriallist);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return page;
	}
	
	/**
	 * 未确认物资明细
	 * @param pageSize
	 * @param pageNumber
	 * @param mat_code
	 * @param date
	 * @return
	 */
	public Pager materialUnacceptForDetail(int pageSize, int pageNumber,String mat_code,String date) {
		Pager page=null;

		try {
			int starnumber=pageSize*(pageNumber-1);
			List<UnacceptMaterialDetail> unacceptMaterialDetaillist=new ArrayList<>();
		    page=genericDao.getListWithNativeSqlFY("unaccept.detail.list", new Object[]{mat_code,date},new int[]{starnumber,pageSize});
		    //测试
		    for (Object obj : page.getList()) {
		    	Object[] objj = (Object[]) obj;
				System.out.println(Arrays.toString(objj));
		     }
		    if(page!=null){
				List<Object[]> RetrospectList=(List<Object[]>) page.getList();
				if(RetrospectList!=null&&RetrospectList.size()>0){
					for (int i = 0; i < RetrospectList.size(); i++) {
						Object[] result=RetrospectList.get(i);
						System.out.println(Arrays.toString(result));
						UnacceptMaterialDetail batchRetrospect=new UnacceptMaterialDetail();
						batchRetrospect.setBillGiveDate(result[0]==null?"":result[0].toString());
						batchRetrospect.setMatCode(result[1]==null?"":result[1].toString());
						batchRetrospect.setMatName(result[2]==null?"":result[2].toString());
						batchRetrospect.setMatBatch(result[3]==null?"":result[3].toString());
						batchRetrospect.setQuantity(result[4]==null?"":result[4].toString());
						unacceptMaterialDetaillist.add(batchRetrospect);
					}
					page.setList(unacceptMaterialDetaillist);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return page;
	}
	/**
	 * 表香余重
	 * @param pageSize
	 * @param pageNumber
	 * @param mat_code
	 * @param date
	 * @return
	 */
	//参数可能改变
	public Pager topDressFlavor(int pageSize, int pageNumber,String matBatch,String startDate,String endDate) {
		Pager page=null;

		try {
			int starnumber=pageSize*(pageNumber-1);
			List<TopDressFlavor> topDressFlavorlist=new ArrayList<>();
		    page=genericDao.getListWithNativeSqlFY("unaccept.topdressflavor.list", new Object[]{matBatch,startDate,endDate},new int[]{starnumber,pageSize});
		    //测试
		    for (Object obj : page.getList()) {
		    	Object[] objj = (Object[]) obj;
				System.out.println(Arrays.toString(objj));
		     }
		    if(page!=null){
				List<Object[]> RetrospectList=(List<Object[]>) page.getList();
				if(RetrospectList!=null&&RetrospectList.size()>0){
					for (int i = 0; i < RetrospectList.size(); i++) {
						Object[] result=RetrospectList.get(i);
						//测试
						System.out.println(Arrays.toString(result));
						TopDressFlavor batchRetrospect=new TopDressFlavor();
						batchRetrospect.setMatCode(result[0]==null?"":result[0].toString());
						batchRetrospect.setMatName(result[1]==null?"":result[1].toString());
						batchRetrospect.setMatBatch(result[2]==null?"":result[2].toString());
						batchRetrospect.setQuantity(result[3]==null?"":result[3].toString());
						batchRetrospect.setUnit(result[4]==null?"":result[4].toString());
						batchRetrospect.setCreateTime(result[5]==null?"":result[5].toString());
						topDressFlavorlist.add(batchRetrospect);
					}
					page.setList(topDressFlavorlist);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return page;
	}
	/**
	 * 生产工单
	 * @param pageSize
	 * @param pageNumber
	 * @param mat_code
	 * @param date
	 * @return
	 */
	public Pager productWorkOrder(int pageSize, int pageNumber,String workorderCode,String startDate,String endDate) {
		Pager page=null;

		try {
			int starnumber=pageSize*(pageNumber-1);
			List<ProductionWorkorder> productionWorkorderlist=new ArrayList<>();
		    page=genericDao.getListWithNativeSqlFY("unaccept.productworkorder.list", new Object[]{workorderCode,startDate,endDate},new int[]{starnumber,pageSize});
		    //测试
		    for (Object obj : page.getList()) {
		    	Object[] objj = (Object[]) obj;
				System.out.println(Arrays.toString(objj));
		     }
		    if(page!=null){
				List<Object[]> RetrospectList=(List<Object[]>) page.getList();
				if(RetrospectList!=null&&RetrospectList.size()>0){
					for (int i = 0; i < RetrospectList.size(); i++) {
						Object[] result=RetrospectList.get(i);
						System.out.println(Arrays.toString(result));
						ProductionWorkorder batchRetrospect=new ProductionWorkorder();
						batchRetrospect.setWorkorderCode(result[0]==null?"":result[0].toString());
						batchRetrospect.setProduceDate(result[1]==null?"":result[1].toString());
						batchRetrospect.setWorkorderState(result[2]==null?"":result[2].toString());
						batchRetrospect.setBatchsPutIn(result[3]==null?"":result[3].toString());
						batchRetrospect.setActualQuantity(result[4]==null?"":result[4].toString());
						batchRetrospect.setRlsStatus(result[5]==null?"":result[5].toString());
						productionWorkorderlist.add(batchRetrospect);
					}
					page.setList(productionWorkorderlist);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return page;
	}
	
	/**
	 * 投料查询
	 * @param pageSize
	 * @param pageNumber
	 * @param mat_code
	 * @param date
	 * @return
	 */
	public Pager feedstatistic(int pageSize, int pageNumber,String code,String workorderType,String startDate,String endDate) {
		Pager page=null;

		try {
			int starnumber=pageSize*(pageNumber-1);
			List<FeedingStatistic> feedingStatisticlist=new ArrayList<>();
		    page=genericDao.getListWithNativeSqlFY("unaccept.feedstatistic.list", new Object[]{code,workorderType,startDate,endDate},new int[]{starnumber,pageSize});
		    //测试
		    for (Object obj : page.getList()) {
		    	Object[] objj = (Object[]) obj;
				System.out.println(Arrays.toString(objj));
		     }
		    if(page!=null){
				List<Object[]> RetrospectList=(List<Object[]>) page.getList();
				if(RetrospectList!=null&&RetrospectList.size()>0){
					for (int i = 0; i < RetrospectList.size(); i++) {
						Object[] result=RetrospectList.get(i);
						System.out.println(Arrays.toString(result));
						FeedingStatistic batchRetrospect=new FeedingStatistic();
						batchRetrospect.setWorkorderCode(result[0]==null?"":result[0].toString());
						batchRetrospect.setProduceDate(result[1]==null?"":result[1].toString());
						batchRetrospect.setMatName(result[2]==null?"":result[2].toString());
						batchRetrospect.setMatBatch(result[3]==null?"":result[3].toString());
						batchRetrospect.setOperateTime(result[4]==null?"":result[4].toString());
						batchRetrospect.setName(result[5]==null?"":result[5].toString());
						feedingStatisticlist.add(batchRetrospect);
					}
					page.setList(feedingStatisticlist);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return page;
	}
	/**
	 * 人员投料统计
	 * @param pageSize
	 * @param pageNumber
	 * @param mat_code
	 * @param date
	 * @return
	 */
	public Pager personFeedStatistic(int pageSize, int pageNumber,String code,String startDate,String endDate) {
		Pager page=null;

		try {
			int starnumber=pageSize*(pageNumber-1);
			List<PersonalFeedingStatistic> personalFeedingStatisticlist=new ArrayList<>();
		    page=genericDao.getListWithNativeSqlFY("unaccept.personfeedstatistic.list", new Object[]{code,startDate,endDate},new int[]{starnumber,pageSize});
		    //测试
		    for (Object obj : page.getList()) {
		    	Object[] objj = (Object[]) obj;
				System.out.println(Arrays.toString(objj));
		     }
		    if(page!=null){
				List<Object[]> RetrospectList=(List<Object[]>) page.getList();
				if(RetrospectList!=null&&RetrospectList.size()>0){
					for (int i = 0; i < RetrospectList.size(); i++) {
						Object[] result=RetrospectList.get(i);
						System.out.println(Arrays.toString(result));
						PersonalFeedingStatistic batchRetrospect=new PersonalFeedingStatistic();
						batchRetrospect.setName(result[0]==null?"":result[0].toString());
						batchRetrospect.setBatchsPutIn(result[1]==null?"":result[1].toString());
						batchRetrospect.setWorkorderCount(result[2]==null?"":result[2].toString());
						personalFeedingStatisticlist.add(batchRetrospect);
					}
					page.setList(personalFeedingStatisticlist);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return page;
	}
	
/**
 * 投料查询，下拉框
 * @return
 */
	public List<String> feedWorkorderType(){
		List<String> workorders = null;
		try {
			workorders = genericDao.getListDefault("unaccept.feedworkordertype.list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return workorders;
	}

	
	
}




