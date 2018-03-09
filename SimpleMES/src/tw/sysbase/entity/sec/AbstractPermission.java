package tw.sysbase.entity.sec;

import tw.sysbase.entity.sec.Party;
import tw.sysbase.entity.sec.Permission;
import tw.sysbase.entity.sec.Resources;

public abstract class AbstractPermission implements Permission {

	protected String id;
	protected Party party;
	protected Resources resource;
	protected long type;

	public void setType(long type) {
		this.type = type;
	}

	public boolean hasRight(long type) {
		return (this.type & type) != 0;

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Party getParty() {
		return party;
	}

	public void setParty(Party party) {
		this.party = party;
	}

	public Resources getResource() {
		return resource;
	}

	public void setResource(Resources resource) {
		this.resource = resource;
	}

	public long getType() {
		return type;
	}

}
