package com.talkweb.xwzcxt.service;

import java.util.List;
import java.util.Map;

import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.pojo.TWorkObjtype;

/**
 * TODO(描述这个类的作用)
 * 
 * @author: 2013-12-17，FLN，（描述修改内容）
 */
public interface XwzcxtObjTypeService
{
    public List<TWorkObjtype> queryAllObjTypeTree();

    public List<TWorkObjtype> queryTypeNodes(TWorkObjtype tWorkObjtype,
            Pagination pagination);

    public TWorkObjtype getTWorkObjtypeById(String id);

    public void addObjType(TWorkObjtype tWorkObjtype);

    public void removeObjTypeByKey(String c_objtype_code);

    public void updateObjType(TWorkObjtype tWorkObjtype);

    public void removeObjTypeByPks(List list);

    public boolean isExistCode(Map param);

    public void deleteByIds(String[] ids);
    
    public boolean hasChild(String id);
}
