package sail.beans.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sail.beans.dao.GenericDao;
import sail.beans.entity.BatFiltertipPut;
import sail.beans.entity.BatSpiceTurn;
import sail.beans.entity.BatWorkOrder;
import sail.beans.entity.BatWorkOrderInput;
import sail.beans.entity.CarCode;
import sail.beans.support.DateBean;
import sqlj.runtime.error.RuntimeErrors;

@Service
public class NozzlebarAddService {
	@Autowired
	private GenericDao genericDao;
	@Autowired
	private BatchStorageService batchStorageService;
	
	public  BatFiltertipPut saveNozzlebaradd(String f_jitai_id,String esb,String f_mat_batch, String userId) {
		BatFiltertipPut batFiltertipPut=null;
		try {
			List<BatFiltertipPut> batFiltertipPutList = genericDao.getListWithVariableParas("WORKORDER.T_BAT_FILTERTIPPUT.LIST", new Object[]{f_jitai_id,f_mat_batch});
			if(batFiltertipPutList!=null&&batFiltertipPutList.size()>0){
				batFiltertipPut.setIsrepair("1");
			}else{
				CarCode carcode=batchStorageService.getResolveValue(f_mat_batch, "JM02");
				if(carcode.getMatcode()!=null){
					batFiltertipPut=new BatFiltertipPut();
					batFiltertipPut.setTransmitter(esb);
					batFiltertipPut.setSysflag("1");
					batFiltertipPut.setCreatetime(DateBean.getSysdateTime());
					batFiltertipPut.setCreator(userId);
					batFiltertipPut.setLastmodifiedtime(DateBean.getSysdateTime());
					batFiltertipPut.setLastmodifier(userId);
					batFiltertipPut.setMatbatch(f_mat_batch);
					batFiltertipPut.setMatcode(carcode.getMatcode());
					batFiltertipPut.setMatname(carcode.getMatname());
					batFiltertipPut.setQuantity(carcode.getAmount());
					batFiltertipPut.setUnit(carcode.getUnit());
					batFiltertipPut.setOperateuserid(userId);
					batFiltertipPut.setOperatetime(DateBean.getSysdateTime());
					batFiltertipPut.setIsdeal("0");
					this.genericDao.save(batFiltertipPut);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException();
		}
		return batFiltertipPut;
	}

	public List<Map> getJiTaiMes() {
		List<Map> list=new ArrayList<>();
		String nativeSql="select t.f_dic_name,t.F_ESB_CODE from " +
				"v_bat_equ t where  t.F_PEOCESS_CODE='HZ-CXD-702'";
		List<Object[]> jiaiList=this.genericDao.getListWithNativeSql(nativeSql);
		if(jiaiList!=null&&jiaiList.size()>0){
			for (Object[] objects : jiaiList) {
				Map map=new HashMap();
				map.put("name", objects[0]);
				map.put("value", objects[1]);
				list.add(map);
			}
		}
		return list;
	}

	public List<BatFiltertipPut> getBatFiltertipPut(String f_jitai_id) {
		List<BatFiltertipPut> inputList=null;
		inputList = genericDao.getListWithVariableParas("WORKORDER.T_BAT_FILTERTIPPUT.LIST", new Object[]{f_jitai_id,null});
		return inputList;
	}

	public boolean deleteBatFiltertipPut(String pid, String userId) {
		boolean flag=false;
		BatFiltertipPut batFiltertipPut = (BatFiltertipPut)genericDao.getById(BatFiltertipPut.class,pid);
		try {
			if(batFiltertipPut!=null){
				batFiltertipPut.setSysflag("0");
				batFiltertipPut.setLastmodifier(userId);
				batFiltertipPut.setLastmodifiedtime(DateBean.getSysdateTime());
				this.genericDao.save(batFiltertipPut);
				flag=true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException();
		}
		return flag;
	}

	
}
