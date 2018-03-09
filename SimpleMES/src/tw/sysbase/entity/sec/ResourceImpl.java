/**
 * 
 */
package tw.sysbase.entity.sec;

import tw.sysbase.entity.sec.Resources;

/**
 * @author 刘威
 * 
 */
public class ResourceImpl implements Resources {

	private String id;

	private String targetId;

	private String type;
	
	public ResourceImpl() {

	}
	
	public ResourceImpl(String targetId,String type) {
		this.targetId=targetId;
		this.type=type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
