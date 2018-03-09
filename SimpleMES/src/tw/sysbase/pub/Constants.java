package tw.sysbase.pub;

 
 /**
 * <p>类说明：</p>
 *      
 *    
 * <p>Copyright: Copyright (c) 2008</p>
 *    
 * @author mark
 * 2007-12-12
 *
 * @version 1.0
 */ 
 
public class Constants {
	/**
	 * 根目录
	 */
	public static final String MENU_ROOT_ID = "9999";
	/**
	 * 菜单资源类型
	 */
	public static final String RES_MENU_TYPE = "10000";
	/**
	 * 动作资源类型
	 */
	public final static String RES_ACTION_TYPE = "10001";
	/**
	 * 超级管理员用户名
	 */
	public static final String ADMIN = "admin";
	/**
	 * 是否DEBUG
	 */
	public static final boolean DEBUG = true;
	public static final String PROJPACKAGE = "sail.business";
	
//	public static final boolean SUCCESS = true;					//定义服务调用返回值的文档类型
//	public static final boolean FAILURE = false;
	
	/**
	 * 返回成功
	 */
	public static final String SUCCESS_MSG = "操作成功。";
	/**
	 * 返回失败
	 */
	public static final String FAILURE_MSG = "操作失败。";
	
	/**
	 * 系统标志-记录正在使用，有效
	 */
	public final static String SYS_FLAG_USEING = "1";

	/**
	 * 系统标志-记录已无效
	 */
	public final static String SYS_FLAG_DELETED = "0";
	/**
	 * 系统标志-记录已失效
	 */
	public final static String INVALID = "失效";
	
	/**
	 * 系统标志-记录已删除
	 */
	public final static String SYS_FLAG_OVER = "2";
	
	/**
	 * 逗号分割
	 */
	public static final String SEPARATOR_COMMA = ",";
	
	/**
	 * 冒号分割
	 */
	public static final String SEPARATOR_COLON = ":";
	
	/**
	 * 状态
	 */
	public static final String STATUS = "status";
	/**
	 * 
	 */
	public static final String MESSAGE = "message";
	/**
	 * 返回成功状态
	 */
	public static final int SUCCESS = 200;
	/**
	 * 返回失败状态
	 */
	public static final int ERROR = 500;
	
	///以下为存放Session中的Key名常量 
	/** 
	* @Fields SESSION_KEY_OPERATOR : TODO(用户ID) 
	*/ 
	public static final String SESSION_KEY_OPERATOR = "operator"; //用户ID
	/** 
	* @Fields SESSION_KEY_OPERATOR : TODO(用户名) 
	*/ 
	public static final String SESSION_KEY_OPERNAME = "opername"; //用户名
	/** 
	* @Fields SESSION_KEY_OPERATOR : TODO(部门ID) 
	*/ 
	public static final String SESSION_KEY_OPERORGID = "operorgid"; //部门ID
	/** 
	* @Fields SESSION_KEY_OPERATOR : TODO(用户工号) 
	*/ 
	public static final String SESSION_KEY_JOBNO = "jobNo"; //用户工号
	/** 
	* @Fields SESSION_KEY_OPERATOR : TODO(是否登录标志) 
	*/  
	public static final String SESSION_KEY_ISLOGIN = "isLogin"; //是否登录标志 
	
	/**
	 *@tables 物料bom类型视图名
	 */
	public static final String MATERIAL_V_MAT_BOMTYPE = "MATERIAL_V_MAT_BOMTYPE" ;
	
	
	/**
     *@tables 物料子类视图
     */
    public static final String MATERIAL_V_MAT_SUBTYPE = "MATERIAL_V_MAT_SUBTYPE" ;
    
    /**
     *@tables 物料大类视图
     */
    public static final String MATERIAL_V_MAT_MAINTYPE = "MATERIAL_V_MAT_MAINTYPE" ;
    
    /**
     *@tables 物料单位视图
     */
    public static final String MATERIAL_V_MAT_UNIT = "MATERIAL_V_MAT_UNIT" ;
}
