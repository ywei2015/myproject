package tw.sysbase.pub;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tw.sysbase.dao.PaginationSupport;


/**
* <p>类说明：Spring MVC Response Base Class</p> 
* 
* <p>Copyright: Copyright (c) 2017</p>
*    
* @author GuveXie
* 2017-08-24
*
* @version 1.0 
 * 
 */
public class ResponseBase implements Serializable {
	private static final long serialVersionUID = 1223013741741997981L;
	
	private ResponseStatus response = new ResponseStatus();

	@SuppressWarnings("rawtypes")
	private Map dataset = new HashMap(); 
	private long totalrecords = 0;

	@SuppressWarnings("unchecked")
	public ResponseBase(){
		if(dataset.containsKey("response"))
			dataset.remove("response");
		dataset.put("response",response);
		if(dataset.containsKey("totalrecords"))
			dataset.remove("totalrecords");
		dataset.put("totalrecords",totalrecords);
	}
	 
	public void setResponseStatus(boolean success, String content){
		this.response.setSuccess(success);
		this.response.setContent(content);
		
		if(dataset.containsKey("response"))
			dataset.remove("response");
		dataset.put("response",response);
	}
	@SuppressWarnings("unchecked")
	public void setResponseStatus(boolean success, String content, int specialflag){
		this.response.setSuccess(success);
		this.response.setContent(content);
		this.response.setSpecialflag(specialflag);  
		if(dataset.containsKey("response"))
			dataset.remove("response");
		dataset.put("response",response);
	}
	@SuppressWarnings("unchecked")
	public void setResponseSpecialflag(int specialflag){ 
		this.response.setSpecialflag(specialflag);  
		if(dataset.containsKey("response"))
			dataset.remove("response");
		dataset.put("response",response);
	}
	 
	@SuppressWarnings("rawtypes")
	public Map getDataset() {
		return dataset;
	}

	@SuppressWarnings("unchecked")
	public void setDataset(Object obj, String entityname) {
		if(obj==null) return;
		this.dataset.put(entityname, obj);
		this.setTotalRecords(1);
	}
	@SuppressWarnings("unchecked")
	public void setDataset(List _dataset, String entityname) {
		if(_dataset==null) return;
		this.dataset.put(entityname, _dataset);
		this.setTotalRecords(_dataset.size());
	}

	@SuppressWarnings("unchecked")
	public void setDataset(PaginationSupport ps, String entityname) {
		if(ps==null) return;
		this.dataset.put(entityname, ps.getItems());
		this.setTotalRecords(ps.getTotalCount());
	}

	@SuppressWarnings("unchecked")
	public void setTotalRecords(long _totalRecords) {
		this.totalrecords = _totalRecords;
		if(dataset.containsKey("totalrecords"))
			dataset.remove("totalrecords");
		dataset.put("totalrecords",totalrecords);
	}
 
	
}
