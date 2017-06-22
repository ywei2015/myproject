package sail.beans.service;

import java.lang.reflect.Method;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sail.beans.dao.GenericDao;
import sail.beans.entity.BatSilkReuse;
import sail.beans.entity.BatSpiceRemain;
import sail.beans.entity.BatSpiceTurn;
import sail.beans.entity.CarCode;

/**
 * 物质批次解码服务类
 * @author YWW
 * @Time  2017-05-18 
 * */
@Service
public class BatchResolveValue {
	@Autowired
	private GenericDao genericDao;  
	private String []methodName= new String[] {"getResolveValueByDetail","getResolveValueByDetailList","getResolveValueByOutput",
			"getResolveValueBySpicet","getResolveValueByRemian","getResolveValueBySilkReuse"};

	
	public CarCode getResolveValue(String match,String type){
		CarCode carCode = new CarCode();
		String []methodNamej=null;
		if("JM01".equals(type)){
			methodNamej= new String[]{methodName[0],methodName[1],methodName[5],methodName[2],methodName[3],methodName[4]};
		}else if("JM02".equals(type)){
			methodNamej= new String[]{methodName[2],methodName[0],methodName[4],methodName[1],methodName[3],methodName[5]};
		}else if("JM03".equals(type)){
			methodNamej= new String[]{methodName[2],methodName[3],methodName[4],methodName[0],methodName[1],methodName[5]};
		}else if("JM04".equals(type)){
			methodNamej= new String[]{methodName[1],methodName[2],methodName[0],methodName[3],methodName[4],methodName[5]};
		}else
			methodNamej= new String[]{methodName[0],methodName[1],methodName[2],methodName[3],methodName[4],methodName[5]};
		try {
			for (int j = 0; j < methodNamej.length; j++) {
				Method m = this.getClass().getDeclaredMethod(methodNamej[j], String.class);
				carCode=(CarCode) m.invoke(this, match);
				if(carCode.getMatcode()!=null){
					String sql="select F_BAT_GETBATCHSTATE('"+match+"') from dual";
					List<String> state=genericDao.getListWithNativeSql(sql);
					if(state!=null&&state.size()>0)
						carCode.setState(state.get(0));
					return carCode;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return carCode;
		}
	
	/**
	 * 从出入库表获取
	 * */
	public CarCode getResolveValueByDetail(String match){
		CarCode carCode = new CarCode();
		List<Object[]> carList=null;
		carList=genericDao.getListWithNativeSql("STORAGE.GET_RESOLVEVAlUE", new Object[]{match});
		if(carList!=null&&carList.size()>0){
			Object[] cararray=carList.get(0);
			String storecode=(String) (cararray[8]==null?"":cararray[8]);
			carCode.setStroecode(storecode);
			String unit=(String) (cararray[7]==null?"":cararray[7]);
			carCode.setUnit(unit);
			String quality= (cararray[6]==null?"":cararray[6]).toString();
			carCode.setAmount(Double.parseDouble(quality));
			String state=(String) (cararray[5]==null?"":cararray[5]);
			carCode.setState(state);
			String oldmatch=(String) (cararray[4]==null?"":cararray[4]);
			carCode.setOldbatch(oldmatch);
			String matname=(String) (cararray[3]==null?"":cararray[3]);
			carCode.setMatname(matname);
			String matcode=(String) (cararray[2]==null?"":cararray[2]);
			carCode.setMatcode(matcode);
			carCode.setValue2("1");
	}
		return carCode;
}
	/**
	 * 从出入库明细子表表获取
	 * */
	public CarCode getResolveValueByDetailList(String match){
		CarCode carCode = new CarCode();
		List<Object[]> carList=null;
		carList=genericDao.getListWithNativeSql("STORAGE.GET_RESOLVEVAlUE2", new Object[]{match});
		if(carList!=null&&carList.size()>0){
			Object[] cararray=carList.get(0);
			String storecode=(String) (cararray[8]==null?"":cararray[8]);
			carCode.setStroecode(storecode);
			String unit=(String) (cararray[7]==null?"":cararray[7]);
			carCode.setUnit(unit);
			String quality= (cararray[6]==null?"":cararray[6]).toString();
			carCode.setAmount(Double.parseDouble(quality));
			String state=(String) (cararray[5]==null?"":cararray[5]);
			carCode.setState(state);
			String oldmatch=(String) (cararray[9]==null?"":cararray[9]);
			carCode.setOldbatch(oldmatch);
			String matname=(String) (cararray[3]==null?"":cararray[3]);
			carCode.setMatname(matname);
			String matcode=(String) (cararray[2]==null?"":cararray[2]);
			carCode.setMatcode(matcode);
			carCode.setValue2("2");
		}
		return carCode;
}
	/**
	 * 从产出表获取
	 * */
	public CarCode getResolveValueByOutput(String match){
		CarCode carCode = new CarCode();
		List<Object[]> carList=null;
		carList=genericDao.getListWithNativeSql("STORAGE.GET_RESOLVEVAlUE3", new Object[]{match});
		if(carList!=null&&carList.size()>0){
			Object[] cararray=carList.get(0);
			String matname=(String) (cararray[3]==null?"":cararray[3]);
			carCode.setMatname(matname);
			String matcode=(String) (cararray[2]==null?"":cararray[2]);
			carCode.setMatcode(matcode);
			String quality= (cararray[0]==null?"":cararray[0]).toString();
			carCode.setAmount(Double.parseDouble(quality));
			String unit=(String) (cararray[1]==null?"":cararray[1]);
			carCode.setUnit(unit);
			carCode.setValue2("3");
		}
		return carCode;
}
	/**
	 * 从稀释液出库小批次转储表获取
	 * */
	public CarCode getResolveValueBySpicet(String match){
		CarCode carCode = new CarCode();
		List<Object[]> carList=null;
		List<BatSpiceTurn> batSpiceTurnList = genericDao.getListWithVariableParas("WORKORDER.T_BAT_SPICETURN.LIST", new Object[]{match,null});
		if(batSpiceTurnList!=null&&batSpiceTurnList.size()>0){
			BatSpiceTurn batSpiceTurn=batSpiceTurnList.get(0);
			carCode.setMatname(batSpiceTurn.getMatename());
			carCode.setMatcode(batSpiceTurn.getMatecode());
			carCode.setAmount(batSpiceTurn.getQuantity());
			carCode.setUnit(batSpiceTurn.getUnit());
			carCode.setValue2("4");
		}
		return carCode;
}
	
	/**
	 * 从余料退回表获取
	 * */
	public CarCode getResolveValueByRemian(String match){
		CarCode carCode = new CarCode();
		List<Object[]> carList=null;
		List<BatSpiceRemain> batSpiceRemainList = genericDao.getListWithVariableParas("SPICEREMAIN.T_BAT_SPICE_REMAIN.LIST", new Object[]{null,match});
		if(batSpiceRemainList!=null&&batSpiceRemainList.size()>0){
			BatSpiceRemain batSpiceRemain=batSpiceRemainList.get(0);
			carCode.setMatname(batSpiceRemain.getMatname());
			carCode.setMatcode(batSpiceRemain.getMatcode());
			carCode.setAmount(batSpiceRemain.getQuantity());
			carCode.setUnit(batSpiceRemain.getUnit());
			carCode.setValue2("5");
		}
		return carCode;
}
	
	/**
	 * 从残烟丝回用表获取
	 * */
	public CarCode getResolveValueBySilkReuse(String match){
		CarCode carCode = new CarCode();
		List<Object[]> carList=null;
		List<BatSilkReuse> batSilkReuseList = genericDao.getListWithVariableParas("STORAGE.GET_RESOLVEVAlUE.SILKREUSE", new Object[]{match});
		if(batSilkReuseList!=null&&batSilkReuseList.size()>0){
			BatSilkReuse batSilkReuse=batSilkReuseList.get(0);
			carCode.setMatname(batSilkReuse.getNewmatid());
			carCode.setMatcode(batSilkReuse.getNewmatname());
			carCode.setAmount(batSilkReuse.getQuantity());
			carCode.setUnit(batSilkReuse.getUnit());
			carCode.setValue2("6");
		}
		return carCode;
}
	
	
}	
