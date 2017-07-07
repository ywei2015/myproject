package sail.beans.service;

import org.springframework.stereotype.Service;

@Service
public class BarCodeLimit {
	
	public boolean barCodeLimitByType(String type,String match){
		if("ZI20".equals(type)){
			if(!match.startsWith("A"))
				return false;
			if(match.trim().length()!=30)
				return false;
		}
		return true;
	}
}
