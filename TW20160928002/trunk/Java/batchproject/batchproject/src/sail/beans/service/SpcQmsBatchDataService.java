package sail.beans.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
//	@Transactional(propagation=Propagation.REQUIRED)
	public boolean SaveSpcQmsBatchData(String batch){
		boolean flag = false;
		Map<String, String> map = new HashMap<String, String>();
		try{
			//查询用于插入主表的单据
			List batchDataList = genericDao.getListWithNativeSql("GET.SPC.QMS.BATCH.DATA.MAIN", new Object[]{batch});
			for(int i = 0;i < batchDataList.size(); i++){
				Object[] obj = (Object[]) batchDataList.get(i);
				BatWorkOrder wo = this.getWorkorderByBatch("17053306ZP03"/*obj[1].toString()*/);
				List<BatZsQaSample> list = genericDao.getListWithVariableParas("CHECK.SPC.QMS.BATCH.DATA", new Object[]{obj[1].toString(),obj[2].toString()
						,obj[4].toString(),wo.getMatcode()});
				if(list.size() == 0){
					BatZsQaSample sample = new BatZsQaSample();
					BatWorkOrder workOrder = this.getWorkorderByBatch("17053306ZP03"/*obj[1].toString()*/);
					if(!StingUtil.isEmpty(workOrder)){
						if(!map.values().contains(obj[1].toString()) && !map.values().contains(obj[4].toString())){
							sample.setFactory(Constants.FACTORY);
							sample.setWorkarea(workOrder.getWorkarea());
							sample.setWorktime(workOrder.getWorktime());
							sample.setWorkteam(workOrder.getWorkteam());
							sample.setProduceTime(workOrder.getProducedate());
							sample.setMatCode(workOrder.getMatcode());
							sample.setMatName(workOrder.getMatname());
							sample.setSectionCode(obj[2]==null?"":obj[2].toString());
							sample.setSectionName(obj[3]==null?"":obj[3].toString());
							sample.setProcessCode(obj[4]==null?"":obj[4].toString());
							sample.setProcessName(obj[5]==null?"":obj[5].toString());
							sample.setWorkorderPid(workOrder.getPid());
							sample.setBatch(obj[0]==null?"":obj[0].toString());
							sample.setSurveyBill(obj[1]==null?"":obj[1].toString());
							sample.setSurveyor(workOrder.getOpuserid());
							sample.setCheckTime(workOrder.getActualendtime());
							sample.setSysFlag(Constants.SYS_FLAG_USEING);
							sample.setCreator(Constants.USERID);
							sample.setCreateTime(DateBean.getSysdateTime());
							this.genericDao.save(sample);
							
							List batchDataSecList = genericDao.getListWithNativeSql("GET.SPC.QMS.BATCH.DATA.SEC", 
									new Object[]{sample.getSurveyBill(),sample.getProcessCode()});
							for(int j = 0;j < batchDataSecList.size(); j++){
								BatZsQaParamResult result = new BatZsQaParamResult();
								Object[] objs = (Object[]) batchDataSecList.get(j);
								result.setQasamplePid(sample);
								result.setParamId(objs[6]==null?"":objs[6].toString());
								result.setParamName(objs[7]==null?"":objs[7].toString());
								result.setNormalValue(objs[13]==null?0:Double.parseDouble(objs[13].toString()));
								result.setUsl(objs[11]==null?0:Double.parseDouble(objs[11].toString()));
								result.setLsl(objs[12]==null?0:Double.parseDouble(objs[12].toString()));
								result.setMax(objs[17]==null?0:Double.parseDouble(objs[17].toString()));
								result.setMin(objs[18]==null?0:Double.parseDouble(objs[18].toString()));
								result.setAverage(objs[16]==null?0:Double.parseDouble(objs[16].toString()));
								result.setSd(objs[15]==null?0:Double.parseDouble(objs[15].toString()));
								result.setCpk(objs[14]==null?0:Double.parseDouble(objs[14].toString()));
								result.setOverCount(objs[19]==null?0:Double.parseDouble(objs[19].toString()));
								result.setPassPercent(objs[20]==null?0:Double.parseDouble(objs[20].toString()));
								result.setUnit(objs[8]==null?"":objs[8].toString());
								result.setSysFlag(Constants.SYS_FLAG_USEING);
								result.setCreator(Constants.USERID);
								result.setCreateTime(DateBean.getSysdateTime());
								this.genericDao.save(result);
							}
							map.put(sample.getSurveyBill(), sample.getSurveyBill());
						    map.put(sample.getProcessCode(), sample.getProcessCode());
						}
					}
				}
			}
			flag = true;
			map.clear();
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
		return flag;
	}
}
