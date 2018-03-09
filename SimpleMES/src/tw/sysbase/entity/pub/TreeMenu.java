package tw.sysbase.entity.pub;

public class TreeMenu {
	private String childId;
	private String childName;
	private String parentId;
	private String parentName;
	private String menuUrl;
	private String order;
	private String rootId;
	private String icon;
	
	public String getChildId() {
		return childId;
	}
	public void setChildId(String childId) {
		this.childId = childId;
	}
	public String getChildName() {
		return childName;
	}
	public void setChildName(String childName) {
		this.childName = childName;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getMenuUrl() {
		return menuUrl;
	}
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}
	
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	
	public String getRootId() {
		return rootId;
	}
	public void setRootId(String rootId) {
		this.rootId = rootId;
	}
	public TreeMenu(String parentId,String parentName,String order){
		super();
		this.parentId = parentId;
		this.parentName = parentName;
		this.order = order;
	}
	public TreeMenu(String childId,String childName,String parentId,String parentName){
		super();
		this.childId = childId;
		this.childName = childName;
		this.parentId = parentId;
		this.parentName = parentName;
	}
	public TreeMenu(String childId,String childName,String parentId,String parentName,String menuUrl){
		super();
		this.childId = childId;
		this.childName = childName;
		this.parentId = parentId;
		this.parentName = parentName;
		this.menuUrl = menuUrl;
	}
	public TreeMenu(String childId,String childName,String parentId,String parentName,String menuUrl,String rootId){
		super();
		this.childId = childId;
		this.childName = childName;
		this.parentId = parentId;
		this.parentName = parentName;
		this.menuUrl = menuUrl;
		this.rootId = rootId;
	}
	public TreeMenu(String childId,String childName,String parentId,String parentName,String menuUrl,String rootId,String vicon){
		super();
		this.childId = childId;
		this.childName = childName;
		this.parentId = parentId;
		this.parentName = parentName;
		this.menuUrl = menuUrl;
		this.rootId = rootId;
		this.icon = vicon;
	} 
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
}
