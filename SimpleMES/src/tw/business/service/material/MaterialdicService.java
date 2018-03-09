package tw.business.service.material;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.sysbase.dao.GenericDao;
/**
 * material 物料基础服务类
 * */
@Service
public class MaterialdicService {
    
    @Autowired
    private GenericDao genericDao;
    
    public String getMaterialNameById(String id,String table) {
        @SuppressWarnings("unchecked")
        List <String >nameList= genericDao.getListWithNativeSql(table,new Object[]{id});
        if (nameList !=null && nameList.size() > 0) {
            return nameList.get(0);
        }
        return null;
    }
}
