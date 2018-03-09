package tw.business.entity.pub;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class BasicData {
	//表格分页数据
	@JsonIgnore
	private int page;
	@JsonIgnore
	private int limit=10;
	private String funcUrl;
	private String funcName;
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	/**
	 * 
	 * @Description: 初始化值为10
	 * @author: zw
	 */
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public String getFuncUrl() {
		return funcUrl;
	}
	public void setFuncUrl(String funcUrl) {
		this.funcUrl = funcUrl;
	}
	public String getFuncName() {
		return funcName;
	}
	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}
	/**
	 *
	 * @Description:  获取开始的点。
	 * @author: zw
	 */
	public  int getStart(){
		if(page==0){
			return page;
		}
		return (page-1)*this.getLimit();
	}
	   @Override
	    protected Object clone() throws CloneNotSupportedException {
	        return super.clone();
	    }

}
