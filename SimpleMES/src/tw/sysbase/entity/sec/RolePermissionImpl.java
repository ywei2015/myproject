package tw.sysbase.entity.sec;

import tw.sysbase.entity.sec.Party;
import tw.sysbase.entity.sec.PermissionType;
import tw.sysbase.entity.sec.Resources;

public class RolePermissionImpl extends AbstractPermission {
	
	public RolePermissionImpl() {

	}
	
	public RolePermissionImpl(Party party, Resources resource) {
		this(party,resource,PermissionType.PERMISSION_GENERAL);
	}
	
	public RolePermissionImpl(Party party, Resources resource,long type) {
		this.party = party;
		this.resource = resource;
		this.type=type;
	}
	
}
