package spc.beans.buffer;

/** 
* @ClassName: PubConst 
* @Description: TODO(常量定义) 
* @author xieshihe 
* @date 2017年9月11日 上午8:48:04 
*  
*/
public final class PubConst {
	
	public final static String ZERO = "0"; 
	public final static String NONZERO = "1";
	public static boolean toBool(String str){
		if(str==null||str.equals("")) return false;
		if(ZERO.equals(str)) return false;
		if(NONZERO.equals(str)) return true;
		 return false;
	}
	public static boolean toBool(Integer str){
		if(str==null) return false;
		if(str==0) return false;
		if(str==1) return true;
		 return false;
	}
	
	//OPC TAG Field Name 
	public final static String GX = "GX";
	public final static String DT = "DT";
	public final static String BATCH = "BATCH";
	public final static String PH = "PH";
	public final static String GXID = "GXID";
	
	//F_QUALITY_TYPE 参数类型(1-计量,2-计数)
	public final static String QUALITY_TYPE_METERING="1"; //计量
	public final static String QUALITY_TYPE_COUNTING="2"; //计数
	
	//F_STATE数据剔除状态(0-正常,1-非稳态,2-不在有效区间,3-超整组剔除)
	/** 
	* @Fields ELIMINATE_STATE_NORMAL : 正常数据
	*/ 
	public final static int ELIMINATE_STATE_NORMAL = 0;
	/** 
	* @Fields ELIMINATE_STATE_BLANK : 非稳态(断流剔除)
	*/ 
	public final static int ELIMINATE_STATE_BLANK = 1;
	/** 
	* @Fields ELIMINATE_STATE_INVALIDREGION : 不在有效区间
	*/ 
	public final static int ELIMINATE_STATE_INVALIDREGION  = 2;
	/** 
	* @Fields ELIMINATE_STATE_OVERGROUP : 超整组剔除
	*/ 
	public final static int ELIMINATE_STATE_OVERGROUP  = 3;
	/** 
	* @Fields ELIMINATE_STATE_OTHER : 剔除程序异常未判断出
	*/ 
	public final static int ELIMINATE_STATE_PRGERROR  = 9;
	
	/** 
	* @Fields ELIMINATE_STATE_OTHER : 该批次牌号未找到标准
	*/ 
	public final static int ELIMINATE_STATE_NOSTANDARD  = 8;
	
	//F_CONTROL_MODEL  规格控制方式(1-双边,2-只有上限,3-只有下限) 
	/** 
	* @Fields SPEC_CONTROL_BILATERAL : 1-双边
	*/ 
	public final static int SPEC_CONTROL_BILATERAL  = 1;
	/** 
	* @Fields SPEC_CONTROL_UNILATERAL_UPPER : 2-只有上限
	*/ 
	public final static int SPEC_CONTROL_UNILATERAL_UPPER  = 2;
	/** 
	* @Fields SPEC_CONTROL_UNILATERAL_LOWER : 3-只有下限
	*/ 
	public final static int SPEC_CONTROL_UNILATERAL_LOWER  = 3;
	
	
	//F_CONTROL_MODE 管控分类(0-否,1-百分比偏差控制,2-非百分比偏差控,3-允差控制)
	
	///F_TOOL_ID 默认监控工具

	///T_SPC_STATISTIC_RESULT.F_STATE 统计状态
	/** 
	* @Fields SPCRESULT_STATE_INIT : 0-初始
	*/ 
	public final static String SPCRESULT_STATE_INIT  = "0";
	/** 
	* @Fields SPCRESULT_STATE_RUN : 10-进行中
	*/ 
	public final static String SPCRESULT_STATE_RUN  = "10";
	/** 
	* @Fields SPCRESULT_STATE_END : 20-结束
	*/ 
	public final static String SPCRESULT_STATE_END  = "20";
	

	/** 
	* @Fields ABNORMAL_TYPE_PREALARM : 10-预警
	*/ 
	public final static String ABNORMAL_TYPE_PREALARM  = "10";
	/** 
	* @Fields ABNORMAL_TYPE_ALARM : 20-报警
	*/ 
	public final static String ABNORMAL_TYPE_ALARM  = "20";
	
	//异常状态(0:待操作工处理,10-已开始处理中,20-处理结束待验证,30-验证结束)
	/** 
	* @Fields ABNORMAL_STATE_INIT : 0-初始(待操作工处理)
	*/ 
	public final static String ABNORMAL_STATE_INIT  = "0";
	/** 
	* @Fields ABNORMAL_STATE_DEALING : 10-已开始处理中
	*/ 
	public final static String ABNORMAL_STATE_DEALING  = "10";
	/** 
	* @Fields ABNORMAL_STATE_WAITCHECK : 20-处理结束待验证
	*/ 
	public final static String ABNORMAL_STATE_WAITCHECK  = "20";
	/** 
	* @Fields ABNORMAL_STATE_CHECKDONE : 30-验证结束
	*/ 
	public final static String ABNORMAL_STATE_CHECKDONE  = "30";
}
