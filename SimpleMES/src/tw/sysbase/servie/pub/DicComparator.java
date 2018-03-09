package tw.sysbase.servie.pub;

import java.util.Comparator;

import tw.sysbase.entity.pub.Dic;

public class DicComparator implements Comparator<Dic> {

	public int compare(Dic o1, Dic o2) {
		if(isNull(o1) && !isNull(o2))
			return -1;
		if(!isNull(o1) && isNull(o2))
			return 1;
		if(isNull(o1) && isNull(o2))
			return 0;
		return o1.getNum1().compareTo(o2.getNum1());
	}
	
	private boolean isNull(Dic dic) {
		if(dic == null || dic.getNum1() == null)
			return true;
		else
			return false;
	}

}
