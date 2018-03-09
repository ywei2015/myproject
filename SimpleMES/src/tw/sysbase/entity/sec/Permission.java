/**
 * 
 */
package tw.sysbase.entity.sec;


/**
 * @author 刘威
 * 
 */
public interface Permission {

	String getId();

	Party getParty();

	Resources getResource();
	
	long getType();

	void setId(String id);

	void setParty(Party party);

	void setResource(Resources resource);
	
	void setType(long type);
	
	boolean hasRight(long type);
}
