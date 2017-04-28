package sail.beans.service.batinterface;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sail.beans.dao.GenericDao;
import sail.beans.entity.BatWorkOrder;
import sail.beans.entity.Matdic;
import sail.beans.entity.User;
import sail.beans.support.StingUtil;

@Service
public class CommonService {
	@Autowired
	private GenericDao genericDao;  
	
	/**
	 * 通过批次号查找
	 * @param matBatch
	 * @return
	 */
	public BatWorkOrder getWorkorderByBatch(String matBatch)
	{
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("FROM sail.beans.entity.BatWorkOrder T ");
		strBuffer.append("Where substr(T.workordercode,0,8) = '");
		strBuffer.append(matBatch);
		strBuffer.append("'");
		strBuffer.append(" AND T.sysFlag = '1'");
		
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
}
