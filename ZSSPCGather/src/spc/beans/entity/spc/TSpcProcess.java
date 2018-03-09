package spc.beans.entity.spc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TSpcProcess { 
     private String FPid; 
    private String FCode; 
    private String FName; 
    private String FSectionId; 
    private String FProductlineId; 
    private String FWorkshopId; 
    private String FTag; 
    private Long FInterval; 
    private String FBatchTag; 
    private String FPhTag; 
    private String FRemark1; 
    private String FRemark2; 
    private String FRemark3; 
    private String FRemark4; 
    private String FRemark5; 
    private String FEnableFlag; 
    private String FSysFlag; 
    private String FCreator; 
    private String FCreateTime; 
    private String FLastModifier; 
    private String FLastModifiedTime;  
    
	public Map<String,TSpcParameter> ParameterMap = new HashMap<String, TSpcParameter>(); 
	
	/** 
	* @Title: getParamTagList 
	* @Description: TODO(根据工序获取参数Tag的List)  
	* @return List<String>   参数Tag的List，第一个为主秤参数 
	* @throws 
	* 2017年9月11日 上午11:10:33 最后修改人 GuveXie 
	*/
	public List<String> getParamTagList(){
		if(ParameterMap.isEmpty()) return null;
		List<String> list = new ArrayList<String>();
		Set<String> keyset =  ParameterMap.keySet();  
		for(String keyname:keyset){
			if(ParameterMap.get(keyname).getFMaster().equals("1"))
				list.add(0,keyname);
			else
				list.add(keyname);
		}
		return list;
	}

	/** 
	* @Title: getMasterParamTag 
	* @Description: TODO(根据工序获取主参数Tag)  
	* @return String   主秤参数Tag 
	* @throws 
	* 2017年9月11日 上午11:10:33 最后修改人 GuveXie 
	*/
	public String getMasterParamTag(){
		if(ParameterMap.isEmpty()) return null; 
		Set<String> keyset =  ParameterMap.keySet();  
		for(String keyname:keyset){
			if(ParameterMap.get(keyname).getFMaster().equals("1"))
				return keyname;
		}
		return null;
	}
	
     
     public String getFPid()  { 
        return this.FPid; 
 } 
     public void setFPid(String FPid)  { 
        this.FPid = FPid; 
 } 
      public String getFCode()  { 
        return this.FCode; 
 } 
     public void setFCode(String FCode)  { 
        this.FCode = FCode; 
 } 
      public String getFName()  { 
        return this.FName; 
 } 
     public void setFName(String FName)  { 
        this.FName = FName; 
 } 
      public String getFSectionId()  { 
        return this.FSectionId; 
 } 
     public void setFSectionId(String FSectionId)  { 
        this.FSectionId = FSectionId; 
 } 
      public String getFProductlineId()  { 
        return this.FProductlineId; 
 } 
     public void setFProductlineId(String FProductlineId)  { 
        this.FProductlineId = FProductlineId; 
 } 
      public String getFWorkshopId()  { 
        return this.FWorkshopId; 
 } 
     public void setFWorkshopId(String FWorkshopId)  { 
        this.FWorkshopId = FWorkshopId; 
 } 
      public String getFTag()  { 
        return this.FTag; 
 } 
     public void setFTag(String FTag)  { 
        this.FTag = FTag; 
 } 
      public Long getFInterval()  { 
        return this.FInterval; 
 } 
     public void setFInterval(Long FInterval)  { 
        this.FInterval = FInterval; 
 } 
      public String getFBatchTag()  { 
        return this.FBatchTag; 
 } 
     public void setFBatchTag(String FBatchTag)  { 
        this.FBatchTag = FBatchTag; 
 } 
      public String getFPhTag()  { 
        return this.FPhTag; 
 } 
     public void setFPhTag(String FPhTag)  { 
        this.FPhTag = FPhTag; 
 } 
      public String getFRemark1()  { 
        return this.FRemark1; 
 } 
     public void setFRemark1(String FRemark1)  { 
        this.FRemark1 = FRemark1; 
 } 
      public String getFRemark2()  { 
        return this.FRemark2; 
 } 
     public void setFRemark2(String FRemark2)  { 
        this.FRemark2 = FRemark2; 
 } 
      public String getFRemark3()  { 
        return this.FRemark3; 
 } 
     public void setFRemark3(String FRemark3)  { 
        this.FRemark3 = FRemark3; 
 } 
      public String getFRemark4()  { 
        return this.FRemark4; 
 } 
     public void setFRemark4(String FRemark4)  { 
        this.FRemark4 = FRemark4; 
 } 
      public String getFRemark5()  { 
        return this.FRemark5; 
 } 
     public void setFRemark5(String FRemark5)  { 
        this.FRemark5 = FRemark5; 
 } 
      public String getFEnableFlag()  { 
        return this.FEnableFlag; 
 } 
     public void setFEnableFlag(String FEnableFlag)  { 
        this.FEnableFlag = FEnableFlag; 
 } 
      public String getFSysFlag()  { 
        return this.FSysFlag; 
 } 
     public void setFSysFlag(String FSysFlag)  { 
        this.FSysFlag = FSysFlag; 
 } 
      public String getFCreator()  { 
        return this.FCreator; 
 } 
     public void setFCreator(String FCreator)  { 
        this.FCreator = FCreator; 
 } 
      public String getFCreateTime()  { 
        return this.FCreateTime; 
 } 
     public void setFCreateTime(String FCreateTime)  { 
        this.FCreateTime = FCreateTime; 
 } 
      public String getFLastModifier()  { 
        return this.FLastModifier; 
 } 
     public void setFLastModifier(String FLastModifier)  { 
        this.FLastModifier = FLastModifier; 
 } 
      public String getFLastModifiedTime()  { 
        return this.FLastModifiedTime; 
 } 
     public void setFLastModifiedTime(String FLastModifiedTime)  { 
        this.FLastModifiedTime = FLastModifiedTime; 
 } 
     @Override     public String toString()     {         return "FPid = "+this.FPid +"/r/n"               + "FCode = "+this.FCode +"/r/n"               + "FName = "+this.FName +"/r/n"               + "FSectionId = "+this.FSectionId +"/r/n"               + "FProductlineId = "+this.FProductlineId +"/r/n"               + "FWorkshopId = "+this.FWorkshopId +"/r/n"               + "FTag = "+this.FTag +"/r/n"               + "FInterval = "+this.FInterval +"/r/n"               + "FBatchTag = "+this.FBatchTag +"/r/n"               + "FPhTag = "+this.FPhTag +"/r/n"               + "FRemark1 = "+this.FRemark1 +"/r/n"               + "FRemark2 = "+this.FRemark2 +"/r/n"               + "FRemark3 = "+this.FRemark3 +"/r/n"               + "FRemark4 = "+this.FRemark4 +"/r/n"               + "FRemark5 = "+this.FRemark5 +"/r/n"               + "FEnableFlag = "+this.FEnableFlag +"/r/n"               + "FSysFlag = "+this.FSysFlag +"/r/n"               + "FCreator = "+this.FCreator +"/r/n"               + "FCreateTime = "+this.FCreateTime +"/r/n"               + "FLastModifier = "+this.FLastModifier +"/r/n"               + "FLastModifiedTime = "+this.FLastModifiedTime +"/r/n" ;  } }