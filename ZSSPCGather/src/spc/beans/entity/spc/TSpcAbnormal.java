package spc.beans.entity.spc;

import spc.beans.base.DatetimeEx;
import spc.beans.buffer.PubConst;

public class TSpcAbnormal { 
     private String FPid; 
    private String FAbnormalType; 
    private String FRuleId; 
    private String FRuleName; 
    private String FBatch; 
    private String FWorkshop; 
    private String FWorkshopName; 
    private String FProductLine; 
    private String FProductLinename; 
    private String FWorksection; 
    private String FWorksectionName; 
    private String FProcess; 
    private String FProcessName; 
    private String FParamId; 
    private String FParamName; 
    private String FBrandId; 
    private String FBrandName; 
    private String FWorktime; 
    private String FWorkteam; 
    private String FDescription; 
    private String FDegree; 
    private String FReasonType; 
    private String FState; 
    private String FOccurStime; 
    private String FOccurEtime; 
    private String FRemark1; 
    private String FRemark2; 
    private String FRemark3; 
    private String FRemark4; 
    private String FRemark5; 
    private String FSysFlag; 
    private String FCreator; 
    private String FCreateTime; 
    private String FLastModifier; 
    private String FLastModifiedTime; 
    
    public TSpcAbnormal(){ 
		this.setFState(PubConst.ABNORMAL_STATE_INIT);
		this.setFSysFlag(PubConst.ZERO);
		this.setFCreateTime(DatetimeEx.curDT14());
    }
    
    
    public String getFPid()  { 
        return this.FPid; 
 } 
     public void setFPid(String FPid)  { 
        this.FPid = FPid; 
 } 
      public String getFAbnormalType()  { 
        return this.FAbnormalType; 
 } 
     public void setFAbnormalType(String FAbnormalType)  { 
        this.FAbnormalType = FAbnormalType; 
 } 
      public String getFRuleId()  { 
        return this.FRuleId; 
 } 
     public void setFRuleId(String FRuleId)  { 
        this.FRuleId = FRuleId; 
 } 
      public String getFRuleName()  { 
        return this.FRuleName; 
 } 
     public void setFRuleName(String FRuleName)  { 
        this.FRuleName = FRuleName; 
 } 
      public String getFBatch()  { 
        return this.FBatch; 
 } 
     public void setFBatch(String FBatch)  { 
        this.FBatch = FBatch; 
 } 
      public String getFWorkshop()  { 
        return this.FWorkshop; 
 } 
     public void setFWorkshop(String FWorkshop)  { 
        this.FWorkshop = FWorkshop; 
 } 
      public String getFWorkshopName()  { 
        return this.FWorkshopName; 
 } 
     public void setFWorkshopName(String FWorkshopName)  { 
        this.FWorkshopName = FWorkshopName; 
 } 
      public String getFProductLine()  { 
        return this.FProductLine; 
 } 
     public void setFProductLine(String FProductLine)  { 
        this.FProductLine = FProductLine; 
 } 
      public String getFProductLinename()  { 
        return this.FProductLinename; 
 } 
     public void setFProductLinename(String FProductLinename)  { 
        this.FProductLinename = FProductLinename; 
 } 
      public String getFWorksection()  { 
        return this.FWorksection; 
 } 
     public void setFWorksection(String FWorksection)  { 
        this.FWorksection = FWorksection; 
 } 
      public String getFWorksectionName()  { 
        return this.FWorksectionName; 
 } 
     public void setFWorksectionName(String FWorksectionName)  { 
        this.FWorksectionName = FWorksectionName; 
 } 
      public String getFProcess()  { 
        return this.FProcess; 
 } 
     public void setFProcess(String FProcess)  { 
        this.FProcess = FProcess; 
 } 
      public String getFProcessName()  { 
        return this.FProcessName; 
 } 
     public void setFProcessName(String FProcessName)  { 
        this.FProcessName = FProcessName; 
 } 
      public String getFParamId()  { 
        return this.FParamId; 
 } 
     public void setFParamId(String FParamId)  { 
        this.FParamId = FParamId; 
 } 
      public String getFParamName()  { 
        return this.FParamName; 
 } 
     public void setFParamName(String FParamName)  { 
        this.FParamName = FParamName; 
 } 
      public String getFBrandId()  { 
        return this.FBrandId; 
 } 
     public void setFBrandId(String FBrandId)  { 
        this.FBrandId = FBrandId; 
 } 
      public String getFBrandName()  { 
        return this.FBrandName; 
 } 
     public void setFBrandName(String FBrandName)  { 
        this.FBrandName = FBrandName; 
 } 
      public String getFWorktime()  { 
        return this.FWorktime; 
 } 
     public void setFWorktime(String FWorktime)  { 
        this.FWorktime = FWorktime; 
 } 
      public String getFWorkteam()  { 
        return this.FWorkteam; 
 } 
     public void setFWorkteam(String FWorkteam)  { 
        this.FWorkteam = FWorkteam; 
 } 
      public String getFDescription()  { 
        return this.FDescription; 
 } 
     public void setFDescription(String FDescription)  { 
        this.FDescription = FDescription; 
 } 
      public String getFDegree()  { 
        return this.FDegree; 
 } 
     public void setFDegree(String FDegree)  { 
        this.FDegree = FDegree; 
 } 
      public String getFReasonType()  { 
        return this.FReasonType; 
 } 
     public void setFReasonType(String FReasonType)  { 
        this.FReasonType = FReasonType; 
 } 
      public String getFState()  { 
        return this.FState; 
 } 
     public void setFState(String FState)  { 
        this.FState = FState; 
 } 
      public String getFOccurStime()  { 
        return this.FOccurStime; 
 } 
     public void setFOccurStime(String FOccurStime)  { 
        this.FOccurStime = FOccurStime; 
 } 
      public String getFOccurEtime()  { 
        return this.FOccurEtime; 
 } 
     public void setFOccurEtime(String FOccurEtime)  { 
        this.FOccurEtime = FOccurEtime; 
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
     @Override     
     public String toString()     {         return "FPid = "+this.FPid +"/r/n"       + "FAbnormalType = "+this.FAbnormalType +"/r/n"               + "FRuleId = "+this.FRuleId +"/r/n"               + "FRuleName = "+this.FRuleName +"/r/n"               + "FBatch = "+this.FBatch +"/r/n"               + "FWorkshop = "+this.FWorkshop +"/r/n"               + "FWorkshopName = "+this.FWorkshopName +"/r/n"               + "FProductLine = "+this.FProductLine +"/r/n"               + "FProductLinename = "+this.FProductLinename +"/r/n"               + "FWorksection = "+this.FWorksection +"/r/n"               + "FWorksectionName = "+this.FWorksectionName +"/r/n"               + "FProcess = "+this.FProcess +"/r/n"               + "FProcessName = "+this.FProcessName +"/r/n"               + "FParamId = "+this.FParamId +"/r/n"               + "FParamName = "+this.FParamName +"/r/n"               + "FBrandId = "+this.FBrandId +"/r/n"               + "FBrandName = "+this.FBrandName +"/r/n"               + "FWorktime = "+this.FWorktime +"/r/n"               + "FWorkteam = "+this.FWorkteam +"/r/n"               + "FDescription = "+this.FDescription +"/r/n"               + "FDegree = "+this.FDegree +"/r/n"               + "FReasonType = "+this.FReasonType +"/r/n"               + "FState = "+this.FState +"/r/n"               + "FOccurStime = "+this.FOccurStime +"/r/n"               + "FOccurEtime = "+this.FOccurEtime +"/r/n"               + "FRemark1 = "+this.FRemark1 +"/r/n"               + "FRemark2 = "+this.FRemark2 +"/r/n"               + "FRemark3 = "+this.FRemark3 +"/r/n"               + "FRemark4 = "+this.FRemark4 +"/r/n"               + "FRemark5 = "+this.FRemark5 +"/r/n"               + "FSysFlag = "+this.FSysFlag +"/r/n"               + "FCreator = "+this.FCreator +"/r/n"               + "FCreateTime = "+this.FCreateTime +"/r/n"               + "FLastModifier = "+this.FLastModifier +"/r/n"               + "FLastModifiedTime = "+this.FLastModifiedTime +"/r/n" ;  } }