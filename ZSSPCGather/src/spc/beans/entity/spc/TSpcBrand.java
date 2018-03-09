package spc.beans.entity.spc;

import java.util.HashMap;
import java.util.Map;

public class TSpcBrand { 
     private String FPid; 
    private String FCode; 
    private String FName; 
    private String FOpcCode; 
    private String FDescription; 
    private String FOrderby; 
    private String FRemark1; 
    private String FRemark2; 
    private String FRemark3; 
    private String FRemark4; 
    private String FRemark5; 
    private String FEnableFlag; 
    private String FExternalFk; 
    private String FSysFlag; 
    private String FCreator; 
    private String FCreateTime; 
    private String FLastModifier; 
    private String FLastModifiedTime; 

	/** 
	* @Fields ParamStdMap : TODO(参数规格标准) 
	* Key-->参数Tag
	*/ 
	public Map<String,TSpcStandard> ParamStdMap = new HashMap<String, TSpcStandard>();
    
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
      public String getFOpcCode()  { 
        return this.FOpcCode; 
 } 
     public void setFOpcCode(String FOpcCode)  { 
        this.FOpcCode = FOpcCode; 
 } 
      public String getFDescription()  { 
        return this.FDescription; 
 } 
     public void setFDescription(String FDescription)  { 
        this.FDescription = FDescription; 
 } 
      public String getFOrderby()  { 
        return this.FOrderby; 
 } 
     public void setFOrderby(String FOrderby)  { 
        this.FOrderby = FOrderby; 
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
      public String getFExternalFk()  { 
        return this.FExternalFk; 
 } 
     public void setFExternalFk(String FExternalFk)  { 
        this.FExternalFk = FExternalFk; 
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
     @Override     public String toString()     {         return "FPid = "+this.FPid +"/r/n"               + "FCode = "+this.FCode +"/r/n"               + "FName = "+this.FName +"/r/n"               + "FOpcCode = "+this.FOpcCode +"/r/n"               + "FDescription = "+this.FDescription +"/r/n"               + "FOrderby = "+this.FOrderby +"/r/n"               + "FRemark1 = "+this.FRemark1 +"/r/n"               + "FRemark2 = "+this.FRemark2 +"/r/n"               + "FRemark3 = "+this.FRemark3 +"/r/n"               + "FRemark4 = "+this.FRemark4 +"/r/n"               + "FRemark5 = "+this.FRemark5 +"/r/n"               + "FEnableFlag = "+this.FEnableFlag +"/r/n"               + "FExternalFk = "+this.FExternalFk +"/r/n"               + "FSysFlag = "+this.FSysFlag +"/r/n"               + "FCreator = "+this.FCreator +"/r/n"               + "FCreateTime = "+this.FCreateTime +"/r/n"               + "FLastModifier = "+this.FLastModifier +"/r/n"               + "FLastModifiedTime = "+this.FLastModifiedTime +"/r/n" ;  } }