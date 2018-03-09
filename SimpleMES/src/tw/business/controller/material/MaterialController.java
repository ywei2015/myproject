package tw.business.controller.material;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tw.authorization.annotation.Authorization;
import tw.authorization.annotation.CurrentUser;
import tw.business.entity.mat.Matdic;
import tw.business.entity.mat.MaterialVo;
import tw.business.pub.ResultModel;
import tw.business.pub.ResultStatus;
import tw.business.service.material.MaterialService;
import tw.sysbase.dao.PaginationSupport;
import tw.sysbase.entity.Pagination;
import tw.sysbase.pub.ResponseBase;

/**
 * @author yinweiwei
 * @date 2018-01-18
 * */
@Controller
@RequestMapping("/material")
public class MaterialController {
    
    @Autowired
    private MaterialService materialService;
    
    /**
     * 基础物料列表
     * */
    @RequestMapping("/materials")
    @ResponseBody
    private Pagination listMatdic(MaterialVo materialVo){
        PaginationSupport ps=materialService.listMatdic(materialVo); 
        Pagination pt=Pagination.init(ps);
        return pt;
    }
    
    /**
     * 基础物料
     * */
    @RequestMapping("/material")
    @ResponseBody
    private Matdic getMatdic(String id){
        Matdic matdic=materialService.getMatdic(id); 
        return matdic;
    }
    /**
     * 基础物料保存
     * */
    @RequestMapping("/savematerial")
    @ResponseBody
    @Authorization
    private ResponseEntity<?> saveMatdic(@Valid Matdic matdic,BindingResult bindingResult,@CurrentUser String operator){
        ResponseEntity<?> rs=new ResponseEntity<>(null,HttpStatus.OK);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(ResultModel.vaildFailed(bindingResult.getAllErrors()), HttpStatus.OK);
        }
        Boolean result=materialService.saveMatdic(matdic,operator);
        if(result) {
            rs= new ResponseEntity<>(ResultModel.saveOk(ResultStatus.SAVE_SUCCESS), HttpStatus.OK);
        }else {
            rs=new ResponseEntity<>(ResultModel.error(ResultStatus.SAVE_ERROR), HttpStatus.NOT_ACCEPTABLE);
        }
        return rs;
    }
    
    /**
     * 基础物料删除
     * */
    @RequestMapping("/deletematerial")
    @ResponseBody
    @Authorization
    private ResponseBase deleteMatdic(String ids,@CurrentUser String operator){
        ResponseBase rb=new ResponseBase();
        Boolean result=materialService.deleteMatdic(ids,operator);
        if(result) {
            rb.setResponseStatus(true,"200");
        }else {
            rb.setResponseStatus(false,"删除失败!");
        }
        return rb;
    }
    
    /**
     * 基础物料修改
     * */
    @RequestMapping("/updatematerial")
    @ResponseBody
    @Authorization
    private ResponseEntity<?> updateMatdic(@Valid Matdic matdic,BindingResult bindingResult,@CurrentUser String operator){
        ResponseEntity<?> rs=new ResponseEntity<>(null,HttpStatus.OK);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(ResultModel.vaildFailed(bindingResult.getAllErrors()), HttpStatus.OK);
        }
        Boolean result=materialService.updateMatdic(matdic,operator);
        if(result) {
            rs= new ResponseEntity<>(ResultModel.modifyOk(ResultStatus.MODIFY_SUCCESS), HttpStatus.OK);
        }else {
            rs=new ResponseEntity<>(ResultModel.error(ResultStatus.MODIFY_ERROR), HttpStatus.NOT_MODIFIED);
        }
        return rs;
    }
}
