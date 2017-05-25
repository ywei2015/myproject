package sail.beans.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sail.beans.dao.GenericDao;
import sail.beans.entity.BatFiltertipPut;
import sail.beans.entity.BatPalletReturn;
import sail.beans.entity.BatPalletReturnDetails;
import sail.beans.entity.BatSpiceTurn;
import sail.beans.entity.BatWorkOrder;
import sail.beans.entity.BatWorkOrderInput;
import sail.beans.entity.CarCode;
import sail.beans.support.DateBean;
import sqlj.runtime.error.RuntimeErrors;

@Service
public class PalletReturnService {
	@Autowired
	private GenericDao genericDao;
	@Autowired
	private BatchStorageService batchStorageService;

	public BatPalletReturnDetails savePalletReturn(String f_date, String f_pallet_no,String f_mat_batch, String userId) {
		BatPalletReturn batPalletReturn=null;
		BatPalletReturnDetails batPalletReturnDetails=null;
		String billno=f_pallet_no+f_date;
		try {
			List<BatPalletReturnDetails> batPalletReturnDetailsList = genericDao.getListWithVariableParas("WORKORDER.T_BAT_PALLETRETURNDETAIL.LIST", new Object[]{f_mat_batch,billno});
			if(batPalletReturnDetailsList!=null&&batPalletReturnDetailsList.size()>0){
				batPalletReturnDetails=batPalletReturnDetailsList.get(0);
				batPalletReturnDetails.setIsrepeat("1");
			}else{
				CarCode carcode=batchStorageService.getResolveValue(f_mat_batch,"JM04");
				if(carcode.getMatcode()==null)
					return batPalletReturnDetails;
				List<BatPalletReturn> batPalletReturnList = genericDao.getListWithVariableParas("WORKORDER.T_BAT_PALLETRETURN.LIST", new Object[]{billno});
				if(batPalletReturnList!=null&&batPalletReturnList.size()>0){
					batPalletReturn=batPalletReturnList.get(0);
				}else{
					batPalletReturn=new BatPalletReturn();
					batPalletReturn.setAdjustno(billno);
					batPalletReturn.setFactory("2200");
					batPalletReturn.setDepot("HZ01");
					batPalletReturn.setOperateuserid(userId);
					batPalletReturn.setOperatetime(DateBean.getSysdateTime());
					batPalletReturn.setCreatetime(DateBean.getSysdateTime());
					batPalletReturn.setCreator(userId);
					batPalletReturn.setState("0");
					batPalletReturn.setSysflag("1");
					this.genericDao.save(batPalletReturn);
				}
					batPalletReturnDetails=new BatPalletReturnDetails();
					batPalletReturnDetails.setAdjustpid(batPalletReturn.getPid());
					batPalletReturnDetails.setSysflag("1");
					batPalletReturnDetails.setCreatetime(DateBean.getSysdateTime());
					batPalletReturnDetails.setCreator(userId);
					batPalletReturnDetails.setLastmodifiedtime(DateBean.getSysdateTime());
					batPalletReturnDetails.setLastmodifier(userId);
					batPalletReturnDetails.setSlavebatch(f_mat_batch);
					batPalletReturnDetails.setMatcode(carcode.getMatcode());
					batPalletReturnDetails.setMatname(carcode.getMatname());
					batPalletReturnDetails.setOldmasterbatch(carcode.getOldbatch());
					batPalletReturnDetails.setRemark1(carcode.getAmount().toString());
					batPalletReturnDetails.setRemark2(carcode.getUnit());
					this.genericDao.save(batPalletReturnDetails);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException();
		}
		return batPalletReturnDetails;
	}

	public List<BatPalletReturnDetails> getPalletReturn(String f_jitai_id) {
		List<BatPalletReturnDetails> batPalletReturnDetailsList = genericDao.getListWithVariableParas("WORKORDER.T_BAT_PALLETRETURNDETAIL.LIST", new Object[]{null,f_jitai_id});
		return batPalletReturnDetailsList;
	}

	public boolean deletePalletReturn(String pid, String userId) {
		boolean flag=false;
		BatPalletReturnDetails batPalletReturnDetails = (BatPalletReturnDetails)genericDao.getById(BatFiltertipPut.class,pid);
		try {
			if(batPalletReturnDetails!=null){
				batPalletReturnDetails.setSysflag("0");
				batPalletReturnDetails.setLastmodifier(userId);
				batPalletReturnDetails.setLastmodifiedtime(DateBean.getSysdateTime());
				this.genericDao.save(batPalletReturnDetails);
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
