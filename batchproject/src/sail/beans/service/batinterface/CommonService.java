package sail.beans.service.batinterface;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sail.beans.dao.GenericDao;
import sail.beans.entity.BatWorkOrder;
import sail.beans.entity.Matdic;
import sail.beans.entity.UBatTransBlendingOrder;
import sail.beans.entity.User;
import sail.beans.support.StingUtil;

@Service
public class CommonService {
	@Autowired
	private GenericDao genericDao;  
	
	/**
	 * 通过批次号+工单类型查找
	 * @param matBatch
	 * @return
	 */
	public BatWorkOrder getWorkorderByBatch(String matBatch)
	{
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("FROM sail.beans.entity.BatWorkOrder T ");
		strBuffer.append("Where T.workordercode = '");
		strBuffer.append(matBatch);
		strBuffer.append("'");
		strBuffer.append(" AND T.sysflag = '1'");
		
		String hql = strBuffer.toString();
		List<BatWorkOrder> conList = this.genericDao.getListByHql(hql);
		if (conList == null || conList.size() == 0)
		{
			return null;
		}
		return conList.get(0);
	}
	/**
	 * 通过编码查找名称
	 * @param matcode
	 * @return
	 */
	public String getNameByCode(String matcode)
	{
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("FROM sail.beans.entity.Matdic T ");
		strBuffer.append("Where T.code = '");
		strBuffer.append(matcode);
		strBuffer.append("'");
		strBuffer.append(" AND T.sysFlag = '1'");
		
		String hql = strBuffer.toString();
		List<Matdic> conList = this.genericDao.getListByHql(hql);
		if (conList == null || conList.size() == 0)
		{
			return null;
		}
		return conList.get(0).getName().toString();
	}
	
	/**
	 * 通过usercode查找
	 * @param usercode
	 * @return
	 */
	public String getUserIdByUserCode(String usercode)
	{
		if(StingUtil.isEmpty(usercode)){
			return null;
		}
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("FROM sail.beans.entity.User T ");
		strBuffer.append("Where T.code = '");
		strBuffer.append(usercode);
		strBuffer.append("'");
		strBuffer.append(" AND T.sysflag = '1'");
		
		String hql = strBuffer.toString();
		List<User> conList = this.genericDao.getListByHql(hql);
		if (conList == null || conList.size() == 0)
		{
			return null;
		}
		return conList.get(0).getPid().toString();
	}
	
	/**
	 * 通过code查找单位ID
	 * @param usercode
	 * @return
	 */
	public String getIdByUnitCode(String unit)
	{
		if(StingUtil.isEmpty(unit)){
			return null;
		}
		String sql = "select f_pid from t_pub_dic where f_dic_code='"+unit+"' and f_sys_flag='1' "
				+ "and f_obj_pid='69522464-a7d6-4cb4-990c-9fef3795f04c' ";
		List conList = this.genericDao.getListWithNativeSql(sql);
		if (conList == null || conList.size() == 0)
		{
			return null;
		}
		return conList.get(0).toString();
	}
	
	/**
	 * 产出数量需要取五丝掺配接口表中blendingType=1（即烟丝掺配）该批次的投料产量
	 * @param batch
	 * @return
	 */
	public UBatTransBlendingOrder getQuantityByBatch(String batch)
	{
		if(StingUtil.isEmpty(batch)){
			return null;
		}
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("FROM sail.beans.entity.UBatTransBlendingOrder T ");
		strBuffer.append("Where T.matBatch = '");
		strBuffer.append(batch);
		strBuffer.append("'");
		strBuffer.append(" AND T.blendingType = '1'");
		
		String hql = strBuffer.toString();
		List<UBatTransBlendingOrder> conList = this.genericDao.getListByHql(hql);
		if (conList == null || conList.size() == 0)
		{
			return null;
		}
		return conList.get(0);
	}
	
	/**
	 * 根据烟丝ESB编码查找叶片编码和叶片名称
	 * @param ysEsbCode
	 * @return
	 */
	public String getBatYpName(String ypEsbCode)
	{
		if(StingUtil.isEmpty(ypEsbCode)){
			return null;
		}
		String sql = "SELECT F_YP_NAME from V_BAT_ZSMAT_BCP WHERE F_YP_ESBCODE ='"+ypEsbCode+"'";
		List conList = this.genericDao.getListWithNativeSql(sql);
		if (conList == null || conList.size() == 0)
		{
			return null;
		}
		return conList.get(0).toString();
	}
	
	/**
	 * 根据烟丝ESB编码查找叶丝编码和叶丝名称
	 * @param ypEsbCode
	 * @return
	 */
	public List getBatYsName(String ysEsbCode)
	{
		if(StingUtil.isEmpty(ysEsbCode)){
			return null;
		}
		String sql = "SELECT F_YS_ESBCODE,F_YS_NAME from V_BAT_ZSMAT_BCP WHERE F_ESB_CODE ='"+ysEsbCode+"'";
		List conList = this.genericDao.getListWithNativeSql(sql);
		if (conList == null || conList.size() == 0)
		{
			return null;
		}
		return conList;
	}
	
}
