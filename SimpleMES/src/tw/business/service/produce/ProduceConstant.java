package tw.business.service.produce;

/**
 * 生产工单相关常量
 * @author CYJ
 *
 */
public class ProduceConstant {
	
	//工单状态(0-草稿,1-下达,10-执行中,20-执行完毕)
	/**
	 * 草稿
	 */
	public final static String PRODUCE_STATUS_INIT = "0";
	/**
	 * 下达
	 */
	public final static String PRODUCE_STATUS_ISSUED = "1";
	/**
	 * 执行中
	 */
	public final static String PRODUCE_STATUS_EXECUTING = "10";
	/**
	 * 执行完毕
	 */
	public final static String PRODUCE_STATUS_DONE = "20";
	
}
