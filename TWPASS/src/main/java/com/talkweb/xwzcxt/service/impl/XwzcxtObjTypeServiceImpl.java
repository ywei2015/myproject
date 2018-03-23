package com.talkweb.xwzcxt.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.dal.XwzcxtObjTypeDal;
import com.talkweb.xwzcxt.pojo.TWorkObjtype;
import com.talkweb.xwzcxt.service.XwzcxtObjTypeService;

/**
 * TODO(描述这个类的作用)
 * 
 * @author: 2013-12-17，FLN，（描述修改内容）
 */
public class XwzcxtObjTypeServiceImpl implements XwzcxtObjTypeService
{
    @Autowired
    private XwzcxtObjTypeDal xwzcxtObjTypeDal;

    @Override
    public List<TWorkObjtype> queryAllObjTypeTree()
    {
        return xwzcxtObjTypeDal.queryAllObjTypeTree();
    }

    @Override
    public List<TWorkObjtype> queryTypeNodes(TWorkObjtype tWorkObjtype,Pagination pagination)
    {
        return xwzcxtObjTypeDal.queryTypeNodes(tWorkObjtype,pagination);
    }

    @Override
    public void addObjType(TWorkObjtype tWorkObjtype)
    {
        xwzcxtObjTypeDal.addObjType(tWorkObjtype);

    }

    @Override
    public void removeObjTypeByKey(String c_objtype_code)
    {
        xwzcxtObjTypeDal.removeObjTypeByKey(c_objtype_code);

    }

    @Override
    public void updateObjType(TWorkObjtype tWorkObjtype)
    {
        xwzcxtObjTypeDal.updateObjType(tWorkObjtype);

    }

    @Override
    public TWorkObjtype getTWorkObjtypeById(String id)
    {
        return xwzcxtObjTypeDal.getTWorkObjtypeById(id);
    }

    @Override
    public void removeObjTypeByPks(List list)
    {
        xwzcxtObjTypeDal.removeObjTypeByPks(list);

    }
    
    @Override
    public boolean isExistCode(Map param)
    {
        return xwzcxtObjTypeDal.isExistCode(param);
    }

    
    
    @Override
    public void deleteByIds(String[] ids)
    {
        xwzcxtObjTypeDal.deleteByIds(ids);
    }
    
    @Override
    public boolean hasChild(String id)
    {
        return xwzcxtObjTypeDal.hasChild(id);
    }

    public XwzcxtObjTypeDal getXwzcxtObjTypeDal()
    {
        return xwzcxtObjTypeDal;
    }

    public void setXwzcxtObjTypeDal(XwzcxtObjTypeDal xwzcxtObjTypeDal)
    {
        this.xwzcxtObjTypeDal = xwzcxtObjTypeDal;
    }

}
