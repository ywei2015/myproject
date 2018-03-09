package spc.beans.base;

public final class StringEx {
	public static String toField(String str){
		if(str==null) return null;
		return String.format("[%s]", str);
	}
}
