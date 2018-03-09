package tw.sysbase.entity.pub;


public class TreeLeaf<T> {
	private String id;
	private String name;
	private String treeUrl;
	private String state = "open";
	private boolean isParent = false;
	private boolean isChildren = true;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTreeUrl() {
		return treeUrl;
	}
	public void setTreeUrl(String treeUrl) {
		this.treeUrl = treeUrl;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public boolean isParent() {
		return isParent;
	}
	public void setParent(boolean isParent) {
		this.isParent = isParent;
	}
	public boolean isChildren() {
		return isChildren;
	}
	public void setChildren(boolean isChildren) {
		this.isChildren = isChildren;
	}
	
	
}
