package tw.sysbase.entity.pub;

import java.util.ArrayList;
import java.util.List;

import tw.business.entity.pub.BasicData;

/**
 * Dic 
 */
public class Dic extends BasicData implements Cloneable {

	private String id;
	private String type;
	private String code;
	private String name;
	private String fullName;//全称
	private String remark; //备注
	private String creator; //创建人ID
	private String creatorName; //创建人名称
	private String createTime; //创建时间
	private String lastModifier; //修改人ID
	private String lastModifierName; //修改人名称
	private String lastModifiedTime; //修改时间
	private Integer sysFlag;
	private String sysFlagName;
	
	//扩展属性
	private String value2;
	private String value3;
	private String value4;
	private String value5;
	private String value6;
	private String value7;
	private String value8;
	private String value9;
	private String value10;
	private String otherId1;
	private String otherId2;
	private String otherId3;
	private String otherId4;
	private String otherId5;
	private Integer flag1;
	private Integer flag2;
	private Integer flag3;
	private Integer flag4;
	private Integer flag5;
	private Integer num1;
	private Integer num2;
	private Integer num3;
	private Integer num4;
	private Integer num5;
	private Double double1;
	private Double double2;
	private Double double3;
	private Double double4;
	private Double double5;
	private String typeName;//扩展
	//
	private String otherName1;
	private String otherName2;
	private String otherName3;
	private String otherName4;
	private String otherName5;
	//业务属性
	private boolean hasChild = false;
	private String parentId;
	private Integer trdFlag;
	private Integer trdOrder;
	private String objEntityRefId;
	private String typeRefDicId;
	private String typeChild;  //子节点类型
	private String objRefPid;
	private List<Dic> children = new ArrayList<Dic>();
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public Integer getSysFlag() {
		return sysFlag;
	}
	public void setSysFlag(Integer sysFlag) {
		this.sysFlag = sysFlag;
	}
	public String getSysFlagName() {
		return sysFlagName;
	}
	public void setSysFlagName(String sysFlagName) {
		this.sysFlagName = sysFlagName;
	}
	public boolean getHasChild() {
		return hasChild;
	}
	public void setHasChild(boolean hasChild) {
		this.hasChild = hasChild;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public Integer getTrdFlag() {
		return trdFlag;
	}
	public void setTrdFlag(Integer trdFlag) {
		this.trdFlag = trdFlag;
	}
	public Integer getTrdOrder() {
		return trdOrder;
	}
	public void setTrdOrder(Integer trdOrder) {
		this.trdOrder = trdOrder;
	}
	public String getObjEntityRefId() {
		return objEntityRefId;
	}
	public void setObjEntityRefId(String objEntityRefId) {
		this.objEntityRefId = objEntityRefId;
	}
	public List<Dic> getChildren() {
		return children;
	}
	
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getCreatorName() {
		return creatorName;
	}
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getLastModifier() {
		return lastModifier;
	}
	public void setLastModifier(String lastModifier) {
		this.lastModifier = lastModifier;
	}
	public String getLastModifierName() {
		return lastModifierName;
	}
	public void setLastModifierName(String lastModifierName) {
		this.lastModifierName = lastModifierName;
	}
	public String getLastModifiedTime() {
		return lastModifiedTime;
	}
	public void setLastModifiedTime(String lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValue2() {
		return value2;
	}
	public void setValue2(String value2) {
		this.value2 = value2;
	}
	public String getValue3() {
		return value3;
	}
	public void setValue3(String value3) {
		this.value3 = value3;
	}
	public String getValue4() {
		return value4;
	}
	public void setValue4(String value4) {
		this.value4 = value4;
	}
	public String getValue5() {
		return value5;
	}
	public void setValue5(String value5) {
		this.value5 = value5;
	}
	public String getValue6() {
		return value6;
	}
	public void setValue6(String value6) {
		this.value6 = value6;
	}
	public String getValue7() {
		return value7;
	}
	public void setValue7(String value7) {
		this.value7 = value7;
	}
	public String getValue8() {
		return value8;
	}
	public void setValue8(String value8) {
		this.value8 = value8;
	}
	public String getValue9() {
		return value9;
	}
	public void setValue9(String value9) {
		this.value9 = value9;
	}
	public String getValue10() {
		return value10;
	}
	public void setValue10(String value10) {
		this.value10 = value10;
	}
	public String getOtherId1() {
		return otherId1;
	}
	public void setOtherId1(String otherId1) {
		this.otherId1 = otherId1;
	}
	public String getOtherId2() {
		return otherId2;
	}
	public void setOtherId2(String otherId2) {
		this.otherId2 = otherId2;
	}
	public String getOtherId3() {
		return otherId3;
	}
	public void setOtherId3(String otherId3) {
		this.otherId3 = otherId3;
	}
	public String getOtherId4() {
		return otherId4;
	}
	public void setOtherId4(String otherId4) {
		this.otherId4 = otherId4;
	}
	public String getOtherId5() {
		return otherId5;
	}
	public void setOtherId5(String otherId5) {
		this.otherId5 = otherId5;
	}
	public Integer getFlag1() {
		return flag1;
	}
	public void setFlag1(Integer flag1) {
		this.flag1 = flag1;
	}
	public Integer getFlag2() {
		return flag2;
	}
	public void setFlag2(Integer flag2) {
		this.flag2 = flag2;
	}
	public Integer getFlag3() {
		return flag3;
	}
	public void setFlag3(Integer flag3) {
		this.flag3 = flag3;
	}
	public Integer getFlag4() {
		return flag4;
	}
	public void setFlag4(Integer flag4) {
		this.flag4 = flag4;
	}
	public Integer getFlag5() {
		return flag5;
	}
	public void setFlag5(Integer flag5) {
		this.flag5 = flag5;
	}
	public Integer getNum2() {
		return num2;
	}
	public void setNum2(Integer num2) {
		this.num2 = num2;
	}
	public Integer getNum3() {
		return num3;
	}
	public void setNum3(Integer num3) {
		this.num3 = num3;
	}
	public Integer getNum4() {
		return num4;
	}
	public void setNum4(Integer num4) {
		this.num4 = num4;
	}
	public Integer getNum5() {
		return num5;
	}
	public void setNum5(Integer num5) {
		this.num5 = num5;
	}
	public Double getDouble1() {
		return double1;
	}
	public void setDouble1(Double double1) {
		this.double1 = double1;
	}
	public Double getDouble2() {
		return double2;
	}
	public void setDouble2(Double double2) {
		this.double2 = double2;
	}
	public Double getDouble3() {
		return double3;
	}
	public void setDouble3(Double double3) {
		this.double3 = double3;
	}
	public Double getDouble4() {
		return double4;
	}
	public void setDouble4(Double double4) {
		this.double4 = double4;
	}
	public Double getDouble5() {
		return double5;
	}
	public void setDouble5(Double double5) {
		this.double5 = double5;
	}
	public Integer getNum1() {
		return num1;
	}
	public void setNum1(Integer num1) {
		this.num1 = num1;
	}
	public String getTypeRefDicId() {
		return typeRefDicId;
	}
	public void setTypeRefDicId(String typeRefDicId) {
		this.typeRefDicId = typeRefDicId;
	}
	public String getOtherName1() {
		return otherName1;
	}
	public void setOtherName1(String otherName1) {
		this.otherName1 = otherName1;
	}
	public String getOtherName2() {
		return otherName2;
	}
	public void setOtherName2(String otherName2) {
		this.otherName2 = otherName2;
	}
	public String getOtherName3() {
		return otherName3;
	}
	public void setOtherName3(String otherName3) {
		this.otherName3 = otherName3;
	}
	public String getOtherName4() {
		return otherName4;
	}
	public void setOtherName4(String otherName4) {
		this.otherName4 = otherName4;
	}
	public String getOtherName5() {
		return otherName5;
	}
	public void setOtherName5(String otherName5) {
		this.otherName5 = otherName5;
	}
	public String gettypeName() {
		return typeName;
	}
	public void settypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getTypeChild() {
		return typeChild;
	}
	public void setTypeChild(String typeChild) {
		this.typeChild = typeChild;
	}
	public String getObjRefPid() {
		return objRefPid;
	}
	public void setObjRefPid(String objRefPid) {
		this.objRefPid = objRefPid;
	}
	public  Dic clone() {
		Dic deepCopy = new Dic();
		deepCopy.setId(getId());
		deepCopy.setCode(getCode());
		deepCopy.setName(getName());
		deepCopy.setSysFlag(getSysFlag());
		deepCopy.setHasChild(getHasChild());
		deepCopy.setParentId(getParentId());
		deepCopy.setTrdFlag(getTrdFlag());
		deepCopy.setTrdOrder(getTrdOrder());
		deepCopy.setObjEntityRefId(getObjEntityRefId());
		deepCopy.setFullName(getFullName());
		deepCopy.setRemark(getRemark());
		deepCopy.setCreator(getCreator());
		deepCopy.setCreatorName(getCreatorName());
		deepCopy.setCreateTime(getCreateTime());
		deepCopy.setLastModifier(getLastModifier());
		deepCopy.setLastModifierName(getLastModifierName());
		deepCopy.setLastModifiedTime(getLastModifiedTime());
		deepCopy.setType(getType());
		deepCopy.setValue2(getValue2());
		deepCopy.setValue3(getValue3());
		deepCopy.setValue4(getValue4());
		deepCopy.setValue5(getValue5());
		deepCopy.setValue6(getValue6());
		deepCopy.setValue7(getValue7());
		deepCopy.setValue8(getValue8());
		deepCopy.setValue9(getValue9());
		deepCopy.setValue10(getValue10());
		deepCopy.setOtherId1(getOtherId1());
		deepCopy.setOtherId2(getOtherId2());
		deepCopy.setOtherId3(getOtherId3());
		deepCopy.setOtherId4(getOtherId4());
		deepCopy.setOtherId5(getOtherId5());
		deepCopy.setFlag1(getFlag1());
		deepCopy.setFlag2(getFlag2());
		deepCopy.setFlag3(getFlag3());
		deepCopy.setFlag4(getFlag4());
		deepCopy.setFlag5(getFlag5());
		deepCopy.setNum1(getNum1());
		deepCopy.setNum2(getNum2());
		deepCopy.setNum3(getNum3());
		deepCopy.setNum4(getNum4());
		deepCopy.setNum5(getNum5());
		deepCopy.setDouble1(getDouble1());
		deepCopy.setDouble2(getDouble2());
		deepCopy.setDouble3(getDouble3());
		deepCopy.setDouble4(getDouble4());
		deepCopy.setDouble5(getDouble5());
		deepCopy.settypeName(gettypeName());
		deepCopy.setTypeChild(getTypeChild());
		deepCopy.setObjRefPid(getObjRefPid());
		return deepCopy;
	}
	
	public Dic(String fullName){
		this.fullName = fullName;
	}
	public Dic() {
		
	}
	
	private String structId;

	public String getStructId() {
		return structId;
	}
	public void setStructId(String structId) {
		this.structId = structId;
	}
	
}
