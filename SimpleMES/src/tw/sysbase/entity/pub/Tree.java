package tw.sysbase.entity.pub;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public class Tree<T> {
	/**
	 * 节点ID
	 */
	private String id;
	private String text;
	/**
	 * 菜单URL
	 */
	private String url;
	/**
	 * 节点状态，open closed
	 */
	private String isOpen = "false";
	private String icon;
	private String targetType;
	/**
	 * 节点是否被选中 true false
	 */
//	private boolean checked = false;
	/**
	 * 节点属性
	 */
//	private List<Map<String, Object>> attributes;
	/**
	 * 节点的子节点
	 */
	private List<Tree<T>> children = new ArrayList<Tree<T>>();

	/**
	 * 父ID
	 */
	private String parentId;
//	private String parentName;
	/**
	 * 是否有父节点
	 */
	private boolean isParent = false;
	/**
	 * 是否有子节点
	 */
	private boolean isChildren = false;
	private String rootId;

	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getTargetType() {
		return targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

	public List<Tree<T>> getChildren() {
		return children;
	}

	public void setChildren(List<Tree<T>> children) {
		this.children = children;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
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

	public String getRootId() {
		return rootId;
	}

	public void setRootId(String rootId) {
		this.rootId = rootId;
	}

	public Tree(String id, String text, String url, String isOpen, String icon,
			String targetType, List<Tree<T>> children,String parentId,
			boolean isParent, boolean isChildren,String rootId) {
		super();
		this.id = id;
		this.text = text;
		this.url = url;
		this.isOpen = isOpen;
		this.icon = icon;
		this.children = children;
		this.parentId = parentId;
		this.isParent = isParent;
		this.isChildren = isChildren;
		this.rootId = rootId;
	}

	public Tree() {
		super();
	}

	@Override
	public String toString() {

		return JSON.toJSONString(this);
	}
}
