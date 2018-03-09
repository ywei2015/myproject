package tw.business.service.material;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.business.entity.mat.Matdic;
import tw.business.entity.mat.MaterialVo;
import tw.sysbase.dao.GenericDao;
import tw.sysbase.dao.PaginationSupport;
import tw.sysbase.pub.Constants;

@Service
public class MaterialService {
    @Autowired
    private GenericDao genericDao;
    
    
    @Autowired
    private MaterialdicService materialdicService;
    /**
     * 物料分页查询
     * */
    public PaginationSupport listMatdic(MaterialVo materialVo) {
        String matcode=materialVo.getMatCode() == null ? null:"%"+materialVo.getMatCode()+"%";
        String matname=materialVo.getMatName() == null ? null:"%"+materialVo.getMatName()+"%";
        Object[] params=new Object[]{matcode,matname};
        PaginationSupport ps=null;
        try {
             ps=genericDao.getPageWithParams("MATERIAL.LISTMATDIC",params,materialVo);
             @SuppressWarnings("unchecked")
             List<Matdic> list = ps.getItems();
              list=list.stream().peek(p -> transformMatdic(p)).collect(Collectors.toList());
              ps.setItems(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ps;
    }
    
    private void transformMatdic(Matdic mat) {
        mat.setSub_type(materialdicService.getMaterialNameById(mat.getSub_type(), Constants.MATERIAL_V_MAT_SUBTYPE));
        mat.setType(materialdicService.getMaterialNameById(mat.getType(), Constants.MATERIAL_V_MAT_MAINTYPE));
        mat.setUnit(materialdicService.getMaterialNameById(mat.getUnit(), Constants.MATERIAL_V_MAT_UNIT));
    }
    /**
     * 物料对象保存
     * @param operator 
     * */
    @Transactional
    public Boolean saveMatdic(Matdic matdic, String operator) {
        try {
            if (!validateMatdic(matdic)) {
                return false;
            } else {
                Timestamp time = new Timestamp(System.currentTimeMillis());
                matdic.setPid(null);
                matdic.setSysFlag("1");
                matdic.setCreateTime(time);
                matdic.setCreator(operator);
                matdic.setLastModifiedTime(time);
                matdic.setLastModifier(operator);
                genericDao.save(matdic);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    /**
     * 物料对象逻辑删除
     * @param operator 
     * */
    @Transactional
    public Boolean deleteMatdic(String fpid, String operator) {
        String [] fpids=validatePid(fpid);
        if (fpids == null) {
            return false;
        } else {
            try {
                Timestamp time = new Timestamp(System.currentTimeMillis());
                for (int i = 0; i < fpids.length; i++) {
                    if (StringUtils.isBlank(fpids[i])) {
                        continue;
                    }
                    Matdic matdic=(Matdic) genericDao.getById(Matdic.class, fpids[i]);
                    if (matdic == null) {
                        return false;
                    } else {
                        matdic.setLastModifiedTime(time);
                        matdic.setLastModifier(operator);
                        matdic.setSysFlag("0");
                        genericDao.save(matdic);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    /**
     * 物料对象修改
     * @param operator 
     * */
    @Transactional
    public Boolean updateMatdic(Matdic matdic, String operator) {
        try {
            if (StringUtils.isBlank(matdic.getPid())) {
                return false;
            } else {
                Timestamp time = new Timestamp(System.currentTimeMillis());
                matdic.setLastModifiedTime(time);
                matdic.setLastModifier(operator);
                matdic.setSysFlag("1");
                genericDao.save(matdic);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    /**
     * 物料对象验证
     * */
    public  Boolean validateMatdic(Matdic matdic){
        if (StringUtils.isBlank(matdic.getMatCode())||StringUtils.isBlank(matdic.getMatName())) {
             return false;
        }
        return true;
    }
    
    public String[] validatePid(String pid){
        String [] pd=null;
        if (!StringUtils.isBlank(pid)) {
            pd = pid.split(",");
        }
        return pd;
    }
    
    /**
     * 基础物料
     * */
    public Matdic getMatdic(String id) {
        Matdic mat=null;
        try {
            mat=(Matdic) genericDao.getById(Matdic.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mat;
    }
}
