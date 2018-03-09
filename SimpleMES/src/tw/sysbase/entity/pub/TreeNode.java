package tw.sysbase.entity.pub;



public class TreeNode {
	private String id;
	private String name;
	private String pid;
	private String parentId;
	private String parentName;
	private String treeUrl;
	private String relationId;
	private int order;
	private String keyId;
	private String objRefPid;
	private String objPid;
	public TreeNode() {
	}
	public TreeNode(String id, String name, String pid, String parentName, int order, String treeUrl, String keyId, String objRefPid,String objPid) {
		super();
		this.id = id;
		this.name = name;
		this.pid = pid;
		this.parentName = parentName;
		this.order = order;
		this.treeUrl = treeUrl;
		this.keyId = keyId;
		this.objRefPid = objRefPid;
		this.objPid = objPid;
	}
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
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
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
	public String getTreeUrl() {
		return treeUrl;
	}
	public void setTreeUrl(String treeUrl) {
		this.treeUrl = treeUrl;
	}
	public String getRelationId() {
		return relationId;
	}
	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public String getKeyId() {
		return keyId;
	}
	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}
	public String getObjRefPid() {
		return objRefPid;
	}
	public void setObjRefPid(String objRefPid) {
		this.objRefPid = objRefPid;
	}
	public String getObjPid() {
		return objPid;
	}
	public void setObjPid(String objPid) {
		this.objPid = objPid;
	}
	
	
}
