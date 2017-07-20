package sail.beans.dao.iml;


import java.util.List;

/**
 * 定义一个分页对象
 * @author yww
 *
 */
public class Pager {
    
    private int page;//当前页码
    private int pageTotal;//总页码
    private int rowsTotal;//总条数
    private int rows;//每页显示条数
        private String hql;//分页语句    
    private List<?> list;//返回的数据集合
    

    public Pager() {
        super();
    }
    
    public String getHql() {
        return hql;
    }

    public void setHql(String hql) {
        this.hql = hql;
    }

    public int getPage() {
        return page;
    }
    public void setPage(int page) {
        this.page = page;
    }
    public int getPageTotal() {
        return pageTotal;
    }
    public void setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
    }
    public int getRowsTotal() {
        return rowsTotal;
    }
    public void setRowsTotal(int rowsTotal) {
        this.rowsTotal = rowsTotal;
    }
    public int getRows() {
        return rows;
    }
    public void setRows(int rows) {
        this.rows = rows;
    }
    public List<?> getList() {
        return list;
    }
    public void setList(List<?> list) {
        this.list = list;
    }


    @Override
    public String toString() {
        return "Pager [list=" + list + ", page=" + page + ", pageTotal="
                + pageTotal + ", rows=" + rows + ", rowsTotal=" + rowsTotal
                + "]";
    }
    

}