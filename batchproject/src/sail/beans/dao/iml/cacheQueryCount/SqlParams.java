package sail.beans.dao.iml.cacheQueryCount;

import java.util.ArrayList;

public class SqlParams {
	private String sql;
	private ArrayList<Object> params;
	private ArrayList<Object> values;
	private int _hashCode = -1;

	public SqlParams(String sql, final ArrayList<Object> params,
			final ArrayList<Object> values) { 
		this.sql = sql;
		this.params = params;
		this.values = values;
	}

	public SqlParams(String sql, final String[] params, final Object[] values) {
		this.sql = sql;

		this.params = new ArrayList<Object>();
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				this.params.add(params[i]);
			}
		}

		this.values = new ArrayList<Object>();
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				this.values.add(values[i]);
			}
		}
	}

	public int hashCode() {
		if (_hashCode == -1) {
			_hashCode = (sql + values.toString()).hashCode();
		}
		return _hashCode;
	}

	public boolean equals(Object obj) {
		boolean b = true;
		if (this == obj)
			return (this == obj);
		SqlParams t = (SqlParams) obj;
		if (sql.equals(t.getSql())) {
			if (params != null && !params.equals(t.getParams())) {
				b = false;
			}
			if (b) {
				if (values != null && !values.equals(t.getValues())) {
					b = false;
				}
			}
		} else {
			b = false;
		}
		return b;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public ArrayList<Object> getParams() {
		return params;
	}

	public void setParams(ArrayList<Object> params) {
		this.params = params;
	}

	public ArrayList<Object> getValues() {
		return values;
	}

	public void setValues(ArrayList<Object> values) {
		this.values = values;
	}
}
