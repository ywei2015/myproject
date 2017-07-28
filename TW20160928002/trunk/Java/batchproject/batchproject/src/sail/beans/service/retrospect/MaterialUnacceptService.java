package sail.beans.service.retrospect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sail.beans.dao.GenericDao;
import sail.beans.dao.iml.Pager;
import sail.beans.entity.BatchRetrospect;
import sail.beans.entity.UnacceptMaterial;
import sail.beans.entity.UnacceptMaterialDetail;

@Service
public class MaterialUnacceptService {
	@Autowired
	private GenericDao genericDao;
	
	
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
	
	
	
}




