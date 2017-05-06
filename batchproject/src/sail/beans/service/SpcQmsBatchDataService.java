package sail.beans.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sail.beans.Constants;
import sail.beans.dao.GenericDao;
import sail.beans.entity.BatWorkOrder;
import sail.beans.entity.BatZsQaParamResult;
import sail.beans.entity.BatZsQaSample;
import sail.beans.service.batinterface.CommonService;
import sail.beans.support.DateBean;
import sail.beans.support.StingUtil;

@Service
public class SpcQmsBatchDataService extends CommonService{

	@Autowired
	private GenericDao genericDao;  
	
	/**
	 * 传入批次号查找制丝生产工艺质量检验样本信息
	 * @param batch
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean SaveSpcQmsBatchData(String batch){
		boolean flag = false;
		BatZsQaSample sample = null;
		BatZsQaParamResult result = null;
		try{
			List batchDataList = genericDao.getListWithNativeSql("GET.SPC.QMS.BATCH.DATA", new Object[]{batch});
			for(int i = 0;i < batchDataList.size(); i++){
				Object[] obj = (Object[]) batchDataList.get(i);
				sample = new BatZsQaSample();
				result = new BatZsQaParamResult();
				BatWorkOrder workOrder = this.getWorkorderByBatch(obj[2].toString());
				if(!StingUtil.isEmpty(workOrder)){
					BatZsQaSample samples = (BatZsQaSample) this.genericDao.getById(BatZsQaSample.class, obj[0].toString());
					if(StingUtil.isEmpty(samples)){
						sample.setPid(obj[0]==null?"":obj[0].toString());
						sample.setFactory(Constants.FACTORY);
						sample.setWorkarea(workOrder.getWorkarea());
						sample.setWorktime(workOrder.getWorktime());
						sample.setWorkteam(workOrder.getWorkteam());
						sample.setProduceTime(workOrder.getProducedate());
						sample.setMatCode(workOrder.getMatcode());
						sample.setMatName(workOrder.getMatname());
						sample.setSectionCode(obj[3]==null?"":obj[3].toString());
						sample.setSectionName(obj[4]==null?"":obj[4].toString());
						sample.setProcessCode(obj[5]==null?"":obj[5].toString());
						sample.setProcessName(obj[6]==null?"":obj[6].toString());
						sample.setWorkorderPid(workOrder.getPid());
						sample.setBatch(obj[1]==null?"":obj[1].toString());
						sample.setSurveyBill(obj[2]==null?"":obj[2].toString());
						sample.setSurveyor(workOrder.getOpuserid());
						sample.setCheckTime(workOrder.getActualendtime());
						sample.setSysFlag(Constants.SYS_FLAG_USEING);
						sample.setCreator(Constants.USERID);
						sample.setCreateTime(DateBean.getSysdateTime());
						this.genericDao.save(sample);
						
						if(!StingUtil.isEmpty(sample)){
							result.setQasamplePid(sample);
							result.setParamId(obj[10]==null?"":obj[10].toString());
							result.setParamName(obj[11]==null?"":obj[11].toString());
							result.setNormalValue(obj[17]==null?0:Double.parseDouble(obj[17].toString()));
							result.setUsl(obj[15]==null?0:Double.parseDouble(obj[15].toString()));
							result.setLsl(obj[16]==null?0:Double.parseDouble(obj[16].toString()));
							result.setMax(obj[21]==null?0:Double.parseDouble(obj[21].toString()));
							result.setMin(obj[22]==null?0:Double.parseDouble(obj[22].toString()));
							result.setAverage(obj[20]==null?0:Double.parseDouble(obj[20].toString()));
							result.setSd(obj[19]==null?0:Double.parseDouble(obj[19].toString()));
							result.setCpk(obj[18]==null?0:Double.parseDouble(obj[18].toString()));
							result.setOverCount(obj[23]==null?0:Double.parseDouble(obj[23].toString()));
							result.setPassPercent(obj[24]==null?0:Double.parseDouble(obj[24].toString()));
							result.setUnit(obj[12]==null?"":obj[12].toString());
							result.setSysFlag(Constants.SYS_FLAG_USEING);
							result.setCreator(Constants.USERID);
							result.setCreateTime(DateBean.getSysdateTime());
							this.genericDao.save(result);
						}
						flag = true;
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
		return flag;
	}
}
