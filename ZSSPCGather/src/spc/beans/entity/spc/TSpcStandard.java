package spc.beans.entity.spc; 

import java.io.Serializable;
 /** 
* @ClassName: TSpcStandard 
* @Description: TODO(参数规格标准) 
* @author xieshihe 
* @date 2017年9月25日 上午11:31:08 
*  
*/
public class TSpcStandard  implements Serializable{ 
     private String FPid; 
    private String FKBrandid; 
    private String FKParameterid; 
    private String FName; 
    private String FExternalFk; 
    private String FQualityType; 
    private Integer FControlModel; 
    private Double FTarget; 
    private Double FUsl; 
    private Double FLsl; 
    private String FIncludeUSL;
	private String FIncludeLSL;
    private Double FGatherUplimit; 
    private Double FGatherLowlimit; 
    private String FIsstatistics; 
    private Double FModulus; 
    private String FOrderby; 
    private Double FGreenMin; 
    private Double FGreenMax; 
    private Double FYellowMin1; 
    private Double FYellowMax1; 
    private Double FYellowMin2; 
    private Double FYellowMax2; 
    private Double FRedMin1; 
    private Double FRedMax1; 
    private Double FRedMin2; 
    private Double FRedMax2; 
    private String FRemark1; 
    private String FRemark2; 
    private String FRemark3; 
    private String FRemark4; 
    private String FRemark5; 
    private String FVersion; 
    private String FEnableTime; 
    private String FDisableTime; 
    private String FEnableFlag; 
    private String FParstdJson; 
    private String FSysFlag; 
    private String FCreator; 
    private String FCreateTime; 
    private String FLastModifier; 
    private String FLastModifiedTime;  
 
    private String ParamTag; ;  
	public String getParamTag() {
		return ParamTag;
	}
	public void setParamTag(String fpTag) {
		ParamTag = fpTag;
	} 
     public String getFPid()  { 
        return this.FPid; 
 } 
     public void setFPid(String FPid)  { 
        this.FPid = FPid; 
 } 
      public String getFKBrandid()  { 
        return this.FKBrandid; 
 } 
     public void setFKBrandid(String FKBrandid)  { 
        this.FKBrandid = FKBrandid; 
 } 
      public String getFKParameterid()  { 
        return this.FKParameterid; 
 } 
     public void setFKParameterid(String FKParameterid)  { 
        this.FKParameterid = FKParameterid; 
 } 
      public String getFName()  { 
        return this.FName; 
 } 
     public void setFName(String FName)  { 
        this.FName = FName; 
 } 
      public String getFExternalFk()  { 
        return this.FExternalFk; 
 } 
     public void setFExternalFk(String FExternalFk)  { 
        this.FExternalFk = FExternalFk; 
 } 
      public String getFQualityType()  { 
        return this.FQualityType; 
 } 
     public void setFQualityType(String FQualityType)  { 
        this.FQualityType = FQualityType; 
 } 
      public Integer getFControlModel()  { 
        return this.FControlModel; 
 } 
     public void setFControlModel(Integer FControlModel)  { 
        this.FControlModel = FControlModel; 
 } 
      public Double getFTarget()  { 
        return this.FTarget; 
 } 
     public void setFTarget(Double FTarget)  { 
        this.FTarget = FTarget; 
 } 
      public Double getFUsl()  { 
        return this.FUsl; 
 } 
     public void setFUsl(Double FUsl)  { 
        this.FUsl = FUsl; 
 } 
      public Double getFLsl()  { 
        return this.FLsl; 
 } 
     public void setFLsl(Double FLsl)  { 
        this.FLsl = FLsl; 
 } 
      public Double getFGatherUplimit()  { 
        return this.FGatherUplimit; 
 } 
     public void setFGatherUplimit(Double FGatherUplimit)  { 
        this.FGatherUplimit = FGatherUplimit; 
 } 
      public Double getFGatherLowlimit()  { 
        return this.FGatherLowlimit; 
 } 
     public void setFGatherLowlimit(Double FGatherLowlimit)  { 
        this.FGatherLowlimit = FGatherLowlimit; 
 } 
      public String getFIsstatistics()  { 
        return this.FIsstatistics; 
 } 
     public void setFIsstatistics(String FIsstatistics)  { 
        this.FIsstatistics = FIsstatistics; 
 } 
      public Double getFModulus()  { 
        return this.FModulus; 
 } 
     public void setFModulus(Double FModulus)  { 
        this.FModulus = FModulus; 
 } 
      public String getFOrderby()  { 
        return this.FOrderby; 
 } 
     public void setFOrderby(String FOrderby)  { 
        this.FOrderby = FOrderby; 
 } 
      public Double getFGreenMin()  { 
        return this.FGreenMin; 
 } 
     public void setFGreenMin(Double FGreenMin)  { 
        this.FGreenMin = FGreenMin; 
 } 
      public Double getFGreenMax()  { 
        return this.FGreenMax; 
 } 
     public void setFGreenMax(Double FGreenMax)  { 
        this.FGreenMax = FGreenMax; 
 } 
      public Double getFYellowMin1()  { 
        return this.FYellowMin1; 
 } 
     public void setFYellowMin1(Double FYellowMin1)  { 
        this.FYellowMin1 = FYellowMin1; 
 } 
      public Double getFYellowMax1()  { 
        return this.FYellowMax1; 
 } 
     public void setFYellowMax1(Double FYellowMax1)  { 
        this.FYellowMax1 = FYellowMax1; 
 } 
      public Double getFYellowMin2()  { 
        return this.FYellowMin2; 
 } 
     public void setFYellowMin2(Double FYellowMin2)  { 
        this.FYellowMin2 = FYellowMin2; 
 } 
      public Double getFYellowMax2()  { 
        return this.FYellowMax2; 
 } 
     public void setFYellowMax2(Double FYellowMax2)  { 
        this.FYellowMax2 = FYellowMax2; 
 } 
      public Double getFRedMin1()  { 
        return this.FRedMin1; 
 } 
     public void setFRedMin1(Double FRedMin1)  { 
        this.FRedMin1 = FRedMin1; 
 } 
      public Double getFRedMax1()  { 
        return this.FRedMax1; 
 } 
     public void setFRedMax1(Double FRedMax1)  { 
        this.FRedMax1 = FRedMax1; 
 } 
      public Double getFRedMin2()  { 
        return this.FRedMin2; 
 } 
     public void setFRedMin2(Double FRedMin2)  { 
        this.FRedMin2 = FRedMin2; 
 } 
      public Double getFRedMax2()  { 
        return this.FRedMax2; 
 } 
     public void setFRedMax2(Double FRedMax2)  { 
        this.FRedMax2 = FRedMax2; 
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
      public String getFVersion()  { 
        return this.FVersion; 
 } 
     public void setFVersion(String FVersion)  { 
        this.FVersion = FVersion; 
 } 
      public String getFEnableTime()  { 
        return this.FEnableTime; 
 } 
     public void setFEnableTime(String FEnableTime)  { 
        this.FEnableTime = FEnableTime; 
 } 
      public String getFDisableTime()  { 
        return this.FDisableTime; 
 } 
     public void setFDisableTime(String FDisableTime)  { 
        this.FDisableTime = FDisableTime; 
 } 
      public String getFEnableFlag()  { 
        return this.FEnableFlag; 
 } 
     public void setFEnableFlag(String FEnableFlag)  { 
        this.FEnableFlag = FEnableFlag; 
 } 
      public String getFParstdJson()  { 
        return this.FParstdJson; 
 } 
     public void setFParstdJson(String FParstdJson)  { 
        this.FParstdJson = FParstdJson; 
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

     public String getFIncludeUSL() {
 		return FIncludeUSL;
 	}
 	public void setFIncludeUSL(String fIncludeUSL) {
 		FIncludeUSL = fIncludeUSL;
 	}
 	public String getFIncludeLSL() {
 		return FIncludeLSL;
 	}
 	public void setFIncludeLSL(String fIncludeLSL) {
 		FIncludeLSL = fIncludeLSL;
 	}
 	
    @Override     public String toString()     {return "FPid = "+this.FPid +"/r/n"               + "FKBrandid = "+this.FKBrandid +"/r/n"               + "FKParameterid = "+this.FKParameterid +"/r/n"               + "FName = "+this.FName +"/r/n"               + "FExternalFk = "+this.FExternalFk +"/r/n"               + "FQualityType = "+this.FQualityType +"/r/n"               + "FControlModel = "+this.FControlModel +"/r/n"               + "FTarget = "+this.FTarget +"/r/n"               + "FUsl = "+this.FUsl +"/r/n"               + "FLsl = "+this.FLsl +"/r/n"               + "FGatherUplimit = "+this.FGatherUplimit +"/r/n"               + "FGatherLowlimit = "+this.FGatherLowlimit +"/r/n"               + "FIsstatistics = "+this.FIsstatistics +"/r/n"               + "FModulus = "+this.FModulus +"/r/n"               + "FOrderby = "+this.FOrderby +"/r/n"               + "FGreenMin = "+this.FGreenMin +"/r/n"               + "FGreenMax = "+this.FGreenMax +"/r/n"               + "FYellowMin1 = "+this.FYellowMin1 +"/r/n"               + "FYellowMax1 = "+this.FYellowMax1 +"/r/n"               + "FYellowMin2 = "+this.FYellowMin2 +"/r/n"               + "FYellowMax2 = "+this.FYellowMax2 +"/r/n"               + "FRedMin1 = "+this.FRedMin1 +"/r/n"               + "FRedMax1 = "+this.FRedMax1 +"/r/n"               + "FRedMin2 = "+this.FRedMin2 +"/r/n"               + "FRedMax2 = "+this.FRedMax2 +"/r/n"               + "FRemark1 = "+this.FRemark1 +"/r/n"               + "FRemark2 = "+this.FRemark2 +"/r/n"               + "FRemark3 = "+this.FRemark3 +"/r/n"               + "FRemark4 = "+this.FRemark4 +"/r/n"               + "FRemark5 = "+this.FRemark5 +"/r/n"               + "FVersion = "+this.FVersion +"/r/n"               + "FEnableTime = "+this.FEnableTime +"/r/n"               + "FDisableTime = "+this.FDisableTime +"/r/n"               + "FEnableFlag = "+this.FEnableFlag +"/r/n"               + "FParstdJson = "+this.FParstdJson +"/r/n"               + "FSysFlag = "+this.FSysFlag +"/r/n"               + "FCreator = "+this.FCreator +"/r/n"               + "FCreateTime = "+this.FCreateTime +"/r/n"               + "FLastModifier = "+this.FLastModifier +"/r/n"               + "FLastModifiedTime = "+this.FLastModifiedTime +"/r/n" ;  }
 
	}