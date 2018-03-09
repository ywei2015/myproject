package tw.business.entity.pub;

/**
 * 对应视图中四个字段，实体映射
 * @author lenovo
 *
 */
public class DicView {
	private String pid;
	private String code;
	private String name;
	private Integer index;
	@Override
	public String toString() {
		return "DicView [pid=" + pid + ", code=" + code + ", name=" + name + ", index=" + index + "]";
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	
	
}
