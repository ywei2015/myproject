/**
 * 
 */
package tw.sysbase.pub;

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
 * 
 */
public class QueryCorrelation {
	private int start; // 每页记录的开始位置

	private int limit; // 每页记录数

	private String sort; // 排序字段

	private String dir; // 升降序

	private String operator; // 操作员ID

	private String operatorType; // 操作员类别(高级用户/普通用户)
	
	private String[][] ParametId;
	
	public String[][] getParametId() {
		return ParametId;
	}

	public void setParametId(String[][] parametId) {
		ParametId = parametId;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(String operatorType) {
		this.operatorType = operatorType;
	}

}
