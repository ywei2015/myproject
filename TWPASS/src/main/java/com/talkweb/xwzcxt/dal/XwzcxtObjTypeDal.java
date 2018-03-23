package com.talkweb.xwzcxt.dal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.talkweb.twdpe.dal.batis.spring.SessionDaoSupport;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.pojo.TWorkObjtype;

/**
 * TODO(描述这个类的作用)
 * 
 * @author: 2013-12-17，FLN，（描述修改内容）
 */
public class XwzcxtObjTypeDal extends SessionDaoSupport
{
    public List<TWorkObjtype> queryAllObjTypeTree()
    {
        return this.getSession().selectList("objType.queryTypeTree");

    }

    public List<TWorkObjtype> queryTypeNodes(TWorkObjtype tWorkObjtype,
            Pagination pagination)
    {
        return this.getSession().selectList("objType.queryTypeNodes",
                tWorkObjtype, pagination);

    }

    public void addObjType(TWorkObjtype tWorkObjtype)
    {
        this.getSession().insert("objType.insertObjType", tWorkObjtype);

    }

    public TWorkObjtype getTWorkObjtypeById(String id)
    {

        return (TWorkObjtype) this.getSession().selectOne(
                "objType.getTWorkObjtypeById", id);
    }

    public void removeObjTypeByKey(String c_objtype_id)
    {
        this.getSession().delete("objType.deleteObjTypeByKey", c_objtype_id);

    }

    public void updateObjType(TWorkObjtype tWorkObjtype)
    {
        this.getSession().update("objType.updateObjType", tWorkObjtype);

    }

    public void removeObjTypeByPks(List list)
    {
        this.getSession().delete("objType.batchRemoveObjTypeByPks", list);

    }
    
    public void deleteByIds(String[] ids)
    {
    	//作业对象类别删除之前，需要对应删除作业对象表中的字段
    	this.getSession().update("basic.deleteObjectByIds", ids);
        this.getSession().update("objType.batchDeleteByIds", ids);
    }

    public boolean isExistCode(Map param)
    {

        return ((Integer) this.getSession().selectOne("objType.isExistCode",
                param)) > 0;
    }
    public boolean hasChild(String id)
    {
        return ((Integer) this.getSession().selectOne("objType.hasChild",
                id)) > 0;
    }
}
