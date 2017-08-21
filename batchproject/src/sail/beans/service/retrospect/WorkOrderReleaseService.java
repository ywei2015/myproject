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
import sail.beans.entity.WorkOrderRelease;

@Service
public class WorkOrderReleaseService {
	@Autowired
	private GenericDao genericDao;

	/**
	 * 工单投料查询
	 * @param pageSize
	 * @param pageNumber
	 * @param startDate
	 * @param endDate
	 * @param worktime
	 * @param workorderType
	 * @return Pager
	 */
	public Pager workorderRel(int pageSize, int pageNumber,String startDate,String endDate,String worktime,String workorderType) {
		Pager page=null;
		try {
			int starnumber=pageSize*(pageNumber-1);
			List<WorkOrderRelease> workOrderReleaseList=new ArrayList();
		    page=genericDao.getListWithNativeSqlFY("BATCH.WORKORDER.SELECT", new Object[]{worktime,startDate,endDate,workorderType},new int[]{starnumber,pageSize});
		  
		    if(page!=null){
				List<Object[]> retrospectList=(List<Object[]>) page.getList();
				if(retrospectList!=null&&retrospectList.size()>0){
					for (int i = 0; i < retrospectList.size(); i++) {
						WorkOrderRelease workOrderRelease=new WorkOrderRelease();
						Object[] result=retrospectList.get(i);
						workOrderRelease.setOrderCode(result[0]==null?"":result[0].toString());
						workOrderRelease.setProductMachine(result[1]==null?"":result[1].toString());
						workOrderRelease.setCardCode(result[2]==null?"":result[2].toString());
						workOrderRelease.setWorktime(result[3]==null?"":result[3].toString());
						workOrderRelease.setWorkteam(result[4]==null?"":result[4].toString());
						workOrderReleaseList.add(workOrderRelease);
					}
					page.setList(workOrderReleaseList);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  page;
	}
	
}
