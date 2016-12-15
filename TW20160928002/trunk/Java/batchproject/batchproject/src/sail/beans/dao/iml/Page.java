package sail.beans.dao.iml;

 /**
 * <p>类说明：</p>
 *    分页对象. 包含当前页数据及分页信息如总记录数.  
 *    
 * <p>Copyright: Copyright (c) 2008</p>
 *    
 * @author mark
 * 2007-12-12
 *
 * @version 1.0
 */ 
 

import java.io.Serializable;
import java.util.ArrayList;


@SuppressWarnings("serial")
public class Page implements Serializable {

	private static int DEFAULT_PAGE_SIZE = 20;

	private int _pageSize = DEFAULT_PAGE_SIZE; // 每页的记录数

	private long _start; // 当前页第一条数据在List中的位置,从0开始

	private Object _data; // 当前页中存放的记录,类型一般为List

	private long _totalCount; // 总记录数

	/**
	 * 构造方法，只构造空页.
	 */
	public Page() {
		this(0, 0, DEFAULT_PAGE_SIZE, new ArrayList());
	}

	/**
	 * 默认构造方法.
	 *
	 * @param start	 本页数据在数据库中的起始位置
	 * @param totalSize 数据库中总记录条数
	 * @param _pageSize  本页容量
	 * @param data	  本页包含的数据
	 */
	public Page(long start, long totalSize, int _pageSize, Object data) {
		this._pageSize = _pageSize;
		this._start = start;
		this._totalCount = totalSize;
		this._data = data;
	}

	/**
	 * 取总记录数.
	 */
	public long getTotalCount() {
		return this._totalCount;
	}

	/**
	 * 取总页数.
	 */
	public long getTotalPageCount() {
		if (_totalCount % _pageSize == 0)
			return _totalCount / _pageSize;
		else
			return _totalCount / _pageSize + 1;
	}

	/**
	 * 取每页数据容量.
	 */
	public int get_pageSize() {
		return _pageSize;
	}

	/**
	 * 取当前页中的记录.
	 */
	public Object getResult() {
		return _data;
	}

	/**
	 * 取该页当前页码,页码从1开始.
	 */
	public long getCurrentPageNo() {
		return _start / _pageSize + 1;
	}

	/**
	 * 该页是否有下一页.
	 */
	public boolean hasNextPage() {
		return this.getCurrentPageNo() < this.getTotalPageCount() - 1;
	}

	/**
	 * 该页是否有上一页.
	 */
	public boolean hasPreviousPage() {
		return this.getCurrentPageNo() > 1;
	}

	/**
	 * 获取任一页第一条数据在数据集的位置，每页条数使用默认值.
	 *
	 * @see #getStartOfPage(int,int)
	 */
	protected static int getStartOfPage(int pageNo) {
		return getStartOfPage(pageNo, DEFAULT_PAGE_SIZE);
	}

	/**
	 * 获取任一页第一条数据在数据集的位置.
	 *
	 * @param pageNo   从1开始的页号
	 * @param _pageSize 每页记录条数
	 * @return 该页第一条数据
	 */
	public static int getStartOfPage(int pageNo, int _pageSize) {
		return (pageNo - 1) * _pageSize;
	}
}
