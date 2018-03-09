package tw.sysbase.entity;

import java.util.List;

import tw.sysbase.dao.PaginationSupport;

public class Pagination {
		private List rows;
		private int total;
		private String hint="hint";
		private int status=200;
		public List getRows() {
			return rows;
		}
		public void setRows(List rows) {
			this.rows = rows;
		}
		public int getTotal() {
			return total;
		}
		public void setTotal(int total) {
			this.total = total;
		}
		public String getHint() {
			return hint;
		}
		public void setHint(String hint) {
			this.hint = hint;
		}
		public int getStatus() {
			return status;
		}
		public void setStatus(int status) {
			this.status = status;
		}
		
	public static Pagination init(PaginationSupport ps){
		Pagination pagination=new Pagination();
		pagination.setRows(ps.getItems());
		pagination.setTotal(ps.getTotalCount());
		return pagination;
	};
		
}
