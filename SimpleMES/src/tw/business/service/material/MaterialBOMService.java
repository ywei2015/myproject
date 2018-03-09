package tw.business.service.material;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import tw.sysbase.pub.Constants;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.business.entity.mat.Matdic;
import tw.business.entity.mat.MaterialVo;
import tw.business.entity.material.MaterialBOMDTO;
import tw.business.entity.material.MaterialBOMDetailDTO;
import tw.sysbase.dao.GenericDao;
import tw.sysbase.dao.PaginationSupport;

@Service
public class MaterialBOMService {
    @Autowired
    private GenericDao genericDao;
    
    @Autowired
    private MaterialdicService materialdicService;
    
    /**
     * 物料BOM列表分页查询 
     * */
    public PaginationSupport listMatBOMdic(MaterialVo materialVo) {
        String boomtype=materialVo.getBomType() == null ? null:materialVo.getBomType();
        String bommname=materialVo.getBomName() == null ? null:"%"+materialVo.getBomName()+"%";
        String status=materialVo.getStatus() == null ? null :materialVo.getStatus();
        Object[] params=new Object[]{boomtype,bommname,status};
        PaginationSupport ps=null;
        try {
             ps=genericDao.getPageWithParams("MATERIALBOM.LISTMAT",params,materialVo);
             @SuppressWarnings("unchecked")
            List<MaterialBOMDTO> matlist=ps.getItems();
             matlist=matlist.stream().peek(p -> matBOMTransform(p)).collect(Collectors.toList());
             ps.setItems(matlist);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ps;
    }
    
    private MaterialBOMDTO matBOMTransform(MaterialBOMDTO mat) {
        mat.setfBomType(materialdicService.getMaterialNameById(mat.getfBomType(), Constants.MATERIAL_V_MAT_BOMTYPE));
        mat.setfUnit(materialdicService.getMaterialNameById(mat.getfUnit(), Constants.MATERIAL_V_MAT_UNIT));
        mat.tranTimesamp();
        return mat;
    }
   
    /**
     * 物料BOM详情列表分页查询 
     * */
    public PaginationSupport listMatBOMdetail(MaterialVo materialVo) {
        String pid=materialVo == null ? null:materialVo.getFpid();
        Object[] params=new Object[]{pid};
        PaginationSupport ps=null;
        try {
             ps=genericDao.getPageWithParams("MATERIALBOM.DETAIL.LISTMAT",params,materialVo);
             @SuppressWarnings("unchecked")
            List<MaterialBOMDetailDTO> list = ps.getItems();
             list=list.stream().peek(p -> p.setfSubtype(materialdicService.getMaterialNameById(
                     p.getfSubtype(), Constants.MATERIAL_V_MAT_SUBTYPE))).collect(Collectors.toList());
             ps.setItems(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ps;
    }
    
    /**
     * 基础物料BOM获取
     * */
    public MaterialBOMDTO getMatBOM(String id) {
        MaterialBOMDTO mat=null;
        try {
            mat=(MaterialBOMDTO) genericDao.getById(MaterialBOMDTO.class, id);
            mat.tranTimesamp();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mat;
    }
    
    /**
     * 基础物料BOM详情获取
     * */
    public MaterialBOMDetailDTO getMatBOMDetail(String id) {
        MaterialBOMDetailDTO mat=null;
        try {
            mat=(MaterialBOMDetailDTO) genericDao.getById(MaterialBOMDetailDTO.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mat;
    }
   
    /**
     * 物料bom对象保存
     * @param operator 
     * */
    @Transactional
    public Boolean saveMatBOM(MaterialBOMDTO matdic, String operator) {
        try {
            Timestamp time = new Timestamp(System.currentTimeMillis());
            if(matdic.getfDisableDt_vo()!=null&&matdic.getfDisableDt_vo().length()>0) {
                matdic.setfDisableDt(Timestamp.valueOf(matdic.getfDisableDt_vo()));
            }
            if(matdic.getfEnableDt_vo()!=null&&matdic.getfEnableDt_vo().length()>0) {
                matdic.setfEnableDt(Timestamp.valueOf(matdic.getfEnableDt_vo()));
            }
            matdic.setfSysFlag("1");
            matdic.setfPid(null);
            matdic.setfCreateTime(time);
            matdic.setfCreator(operator);
            matdic.setfLastModifiedTime(time);
            matdic.setfLastModifier(operator);
            genericDao.save(matdic);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    /**
     * 物料bom详情对象保存
     * @param operator 
     * */
    @Transactional
    public Boolean saveMatBOMDetail(MaterialBOMDetailDTO matdic, String operator) {
        try {
            Timestamp time = new Timestamp(System.currentTimeMillis());
            matdic.setfSysFlag("1");
            matdic.setfPid(null);
            matdic.setfCreateTime(time);
            matdic.setfCreator(operator);
            matdic.setfLastModifiedTime(time);
            matdic.setfLastModifier(operator);
            genericDao.save(matdic);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    /**
     * 物料BOM对象逻辑删除
     * @param operator 
     * */
    @Transactional
    public Boolean deleteMatBOM(String fpid, String operator) {
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
                    MaterialBOMDTO matdic=(MaterialBOMDTO) genericDao.getById(MaterialBOMDTO.class, fpids[i]);
                    if (matdic == null) {
                        return false;
                    } else {
                        matdic.setfLastModifiedTime(time);
                        matdic.setfLastModifier(operator);
                        matdic.setfSysFlag("0");
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
     * 物料BOM详情对象逻辑删除
     * @param operator 
     * */
    @Transactional
    public Boolean deleteMatBOMDetail(String fpid, String operator) {
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
                    MaterialBOMDetailDTO matdic=(MaterialBOMDetailDTO) genericDao.getById(MaterialBOMDetailDTO.class, fpids[i]);
                    if (matdic == null) {
                        return false;
                    } else {
                        matdic.setfLastModifiedTime(time);
                        matdic.setfLastModifier(operator);
                        matdic.setfSysFlag("0");
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
     * 物料BOM详情对象逻辑删除，通过外键
     * @param operator 
     * @param fpid 逻辑外键
     * */
    @Transactional
    public Boolean deleteMatBOMDeailByFid(String fpid, String operator) {
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
                    MaterialBOMDetailDTO matdic=(MaterialBOMDetailDTO) genericDao.getById(MaterialBOMDetailDTO.class, fpids[i]);
                    if (matdic == null) {
                        return false;
                    } else {
                        matdic.setfLastModifiedTime(time);
                        matdic.setfLastModifier(operator);
                        matdic.setfSysFlag("0");
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
     * 物料BOM对象修改
     * @param operator 
     * */
    @Transactional
    public Boolean updateMatBOM(MaterialBOMDTO matdic, String operator) {
        try {
            if (StringUtils.isBlank(matdic.getfPid())) {
                return false;
            } else {
                Timestamp time = new Timestamp(System.currentTimeMillis());
                if(matdic.getfDisableDt_vo()!=null&&matdic.getfDisableDt_vo().length()>0) {
                    matdic.setfDisableDt(Timestamp.valueOf(matdic.getfDisableDt_vo().replace("/", "-")));
                }
                if(matdic.getfEnableDt_vo()!=null&&matdic.getfEnableDt_vo().length()>0) {
                    matdic.setfEnableDt(Timestamp.valueOf(matdic.getfEnableDt_vo().replace("/", "-")));
                }
                matdic.setfLastModifiedTime(time);
                matdic.setfLastModifier(operator);
                matdic.setfSysFlag("1");
                genericDao.save(matdic);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    /**
     * 物料BOM详情对象修改
     * @param operator 
     * */
    @Transactional
    public Boolean updateMatBOMDetail(MaterialBOMDetailDTO matdic, String operator) {
        try {
            if (StringUtils.isBlank(matdic.getfPid())) {
                return false;
            } else {
                Timestamp time = new Timestamp(System.currentTimeMillis());
                matdic.setfLastModifiedTime(time);
                matdic.setfLastModifier(operator);
                matdic.setfSysFlag("1");
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
