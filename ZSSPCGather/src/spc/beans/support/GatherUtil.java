package spc.beans.support;

import java.util.List;


/**
 * 数据处理方法 
 * @author songliming
 */
public class GatherUtil {
	
	private static boolean[] judgeState;
	private static boolean[] preState;
	private static String[] sampTime;
	
	public GatherUtil(){}
	
	
	/**
	 * 将数据列表转化为数组
	 * @param dataList
	 * @return double[]
	 */
	public static double[] changeListToArray(List<Object[]> dataList){
		int size = dataList.size() - 1;
	    double[] data = new double[size];
	    boolean[] jState = new boolean[size];
	    String[] time = new String[size];
	    boolean[] pState = new boolean[size];
	    int num = 0;
	    for (int i = 0; i < size; i++) {
	    	Object[] da = dataList.get(i);
			data[num] = Double.parseDouble(da[0].toString());
    		jState[num] = true;
    		pState[num] = true;
    		time[num] = da[1].toString();
    		num++;
	    }
	    if (num != size){
	    	double[] rdata = new double[num];
	    	judgeState = new boolean[num];
	    	sampTime = new String[num];
	    	preState = new boolean[num];
	    	for (int i = 0; i < num; i++) {
	    		rdata[i] = data[i];
	    		judgeState[i] = jState[i];
	    		sampTime[i] = time[i];
	    		preState[i] = pState[i];
	    	}
	    	return rdata;
	    }else {
	    	judgeState = jState;
	    	sampTime = time;
	    	preState = pState;
	    	return data;
	    }
	}
	
	public static boolean[] getJudgeState(){
		return judgeState;
	}

	public static boolean[] getPreState(){
		return preState;
	}

	public static String[] getSampTime(){
		return sampTime;
	}
}
