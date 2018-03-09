package tw.sysbase.pub;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.ComparatorUtils;
import org.apache.commons.collections.comparators.ComparableComparator;

import tw.sysbase.entity.pub.ObjBase;
public class SortUtil{
	  /**

     * @describe 依据某个字段对集合进行排序

     * @author zw

     * @param list

     *            待排序的集合

     * @param fieldName

     *            依据这个字段进行排序

     * @param asc

     *            如果为true，是正序；为false，为倒序

     */
	   @SuppressWarnings("unchecked")
	    public static <T> void sort(List<T> list, String fieldName, boolean asc) {
	        Comparator<?> mycmp = ComparableComparator.getInstance();
	        mycmp = ComparatorUtils.nullLowComparator(mycmp); // 允许null

	        if (!asc) {
	            mycmp = ComparatorUtils.reversedComparator(mycmp); // 逆序

	        }
	        Collections.sort(list, new BeanComparator(fieldName, mycmp));
	    }

	public static List setToList(Set set) {
		List list=new ArrayList<>();
		if(set!=null){
			for(Object object:set){
				list.add(object);
			}
			return list;
		}
		return null;
	}

}