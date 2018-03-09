package spc.beans.buffer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.locks.ReentrantLock;

import spc.beans.entity.spc.RealtimePointData;

public final class PointDataQueue {
	
	//private static PointDataQueue instance = null;
	
	//private static ArrayList<RealtimePointData> pointdatalist = (ArrayList<RealtimePointData>) Collections.synchronizedList(new ArrayList<RealtimePointData>());
	private static  ArrayList<RealtimePointData> pointdatalist = new ArrayList<RealtimePointData>();
	private static int size = 0;
	private static int gathertotalcount = 0;
	private static ReentrantLock locker=new ReentrantLock();
	
	/*public static PointDataQueue getInstance(){
		if(instance==null) 
			instance = new PointDataQueue();
		return instance;
	}*/

	public static long getGathertotalcount(){
		try
		{
			locker.lock();
			return gathertotalcount;
		}finally{
			locker.unlock();
		}
	}
	
	public static long getCount(){
		try
		{
			locker.lock();
			return pointdatalist.size();
		}finally{
			locker.unlock();
		}
	}
	
	public static boolean in(RealtimePointData pdata){ 
		try
		{
			locker.lock();
			pointdatalist.add(pdata);
			size++;
			gathertotalcount ++;
			return true;
		}finally{
			locker.unlock();
		}
	}
	public static RealtimePointData out(){
		try
		{
			locker.lock();
			if(pointdatalist.isEmpty()) return null;
			return pointdatalist.get(0);
		}finally{
			locker.unlock();
		}
	}
	public static boolean removeFirst(){ 
		try
		{
			locker.lock();
			if(pointdatalist.isEmpty()) return false;
			pointdatalist.remove(0);
			size--;
			if(size<0) size=0;
			return true;
		}finally{
			locker.unlock();
		}
	}
	public static boolean removeLast(){ 
		try
		{
			locker.lock();
			if(pointdatalist.isEmpty()) return false;
			pointdatalist.remove(pointdatalist.size()-1);
			size--;
			if(size<0) size=0;
			return true;
		}finally{
			locker.unlock();
		}
	}
	public static RealtimePointData outRemove(){ 
		try
		{
			locker.lock();
			if(pointdatalist.isEmpty()) return null;
			RealtimePointData data = pointdatalist.get(0);
			pointdatalist.remove(0);
			size--;
			if(size<0) size=0;
			return data;
		}finally{
			locker.unlock();
		}
	}

	public static int getSize() {
		return size;
	}
 
	
}
