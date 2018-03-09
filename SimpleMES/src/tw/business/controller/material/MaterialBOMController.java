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
import tw.business.entity.mat.MaterialVo;
import tw.business.entity.material.MaterialBOMDTO;
import tw.business.entity.material.MaterialBOMDetailDTO;
import tw.business.pub.ResultModel;
import tw.business.pub.ResultStatus;
import tw.business.service.material.MaterialBOMService;
import tw.sysbase.dao.PaginationSupport;
import tw.sysbase.entity.Pagination;
import tw.sysbase.pub.ResponseBase;

/**
 * @author yinweiwei
 * @date 2018-01-18
 * */
@Controller
@RequestMapping("/materialbom")
public class MaterialBOMController {
    
    @Autowired
    private MaterialBOMService materialbomService;
    
    /**
     * 基础物料bom列表
     * */
    @RequestMapping("/list_materialbom")
    @ResponseBody
    private Pagination listMatBOMdic(MaterialVo materialVo){
        PaginationSupport ps=materialbomService.listMatBOMdic(materialVo); 
        Pagination pt=Pagination.init(ps);
        return pt;
    }
    
    /**
     * 基础物料bom详情列表
     * */
    @RequestMapping("/list_materialbom_detail")
    @ResponseBody
    private Pagination listMatBOMdetail(MaterialVo materialVo){
        PaginationSupport ps=materialbomService.listMatBOMdetail(materialVo); 
        Pagination pt=Pagination.init(ps);
        return pt;
    }
    /**
     * 基础物料bom
     * */
    @RequestMapping("/materialbom")
    @ResponseBody
    private MaterialBOMDTO getMatBOM(String id){
        MaterialBOMDTO matdic=materialbomService.getMatBOM(id); 
        return matdic;
    }
    
    /**
     * 基础物料bomdetail获取
     * */
    @RequestMapping("/materialbom_detail")
    @ResponseBody
    private MaterialBOMDetailDTO getMatBOMDetail(String id){
        MaterialBOMDetailDTO matdic=materialbomService.getMatBOMDetail(id); 
        return matdic;
    }
    /**
     * 基础物料bom保存
     * */
    @RequestMapping("/savematerialbom")
    @ResponseBody
    @Authorization
    private ResponseEntity<?> saveMatBOM(@Valid MaterialBOMDTO matdic,BindingResult bindingResult,@CurrentUser String operator){
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(ResultModel.vaildFailed(bindingResult.getAllErrors()), HttpStatus.OK);
        }
        ResponseEntity<?> rs=new ResponseEntity<>(null,HttpStatus.OK);
        Boolean result=materialbomService.saveMatBOM(matdic,operator);
        if(result) {
            rs= new ResponseEntity<>(ResultModel.saveOk(ResultStatus.SAVE_SUCCESS), HttpStatus.OK);
        }else {
            rs=new ResponseEntity<>(ResultModel.error(ResultStatus.SAVE_ERROR), HttpStatus.NOT_ACCEPTABLE);
        }
        return rs;
    }
    
    /**
     * 基础物料bom详情保存
     * */
    @RequestMapping("/savematerialbom_detail")
    @ResponseBody
    @Authorization
    private ResponseEntity<?> saveMatBOMDetail(@Valid MaterialBOMDetailDTO matdic,BindingResult bindingResult,@CurrentUser String operator){
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(ResultModel.vaildFailed(bindingResult.getAllErrors()), HttpStatus.OK);
        }
        ResponseEntity<?> rs=new ResponseEntity<>(null,HttpStatus.OK);
        Boolean result=materialbomService.saveMatBOMDetail(matdic,operator);
        if(result) {
            rs= new ResponseEntity<>(ResultModel.saveOk(ResultStatus.SAVE_SUCCESS), HttpStatus.OK);
        }else {
            rs=new ResponseEntity<>(ResultModel.error(ResultStatus.SAVE_ERROR), HttpStatus.NOT_ACCEPTABLE);
        }
        return rs;
    }
    /**
     * 基础物料bom删除
     * */
    @RequestMapping("/deletematerialbom")
    @ResponseBody
    @Authorization
    private ResponseBase deleteMatBOM(String ids,@CurrentUser String operator){
        ResponseBase rb=new ResponseBase();
        Boolean result=materialbomService.deleteMatBOM(ids,operator);
        if(result) {
            rb.setResponseStatus(true,"200");
        }else {
            rb.setResponseStatus(false,"删除失败!");
        }
        return rb;
    }
    
    /**
     * 基础物料bom详情删除
     * */
    @RequestMapping("/deletematerialbom_detail")
    @ResponseBody
    @Authorization
    private ResponseBase deleteMatBOMDetail(String ids,@CurrentUser String operator){
        ResponseBase rb=new ResponseBase();
        Boolean result=materialbomService.deleteMatBOMDetail(ids,operator);
        if(result) {
            rb.setResponseStatus(true,"200");
        }else {
            rb.setResponseStatus(false,"删除失败!");
        }
        return rb;
    }
    
    /**
     * 基础物料bom修改
     * */
    @RequestMapping("/updatematerialbom")
    @ResponseBody
    @Authorization
    private ResponseEntity<?> updateMatBOM(@Valid MaterialBOMDTO matdic,BindingResult bindingResult,@CurrentUser String operator){
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(ResultModel.vaildFailed(bindingResult.getAllErrors()), HttpStatus.OK);
        }
        ResponseEntity<?> rs=new ResponseEntity<>(null,HttpStatus.OK);
        Boolean result=materialbomService.updateMatBOM(matdic,operator);
        if(result) {
            rs= new ResponseEntity<>(ResultModel.modifyOk(ResultStatus.MODIFY_SUCCESS), HttpStatus.OK);
        }else {
            rs=new ResponseEntity<>(ResultModel.error(ResultStatus.MODIFY_ERROR), HttpStatus.NOT_MODIFIED);
        }
        return rs;
    }
    
    /**
     * 基础物料bom详情修改
     * */
    @RequestMapping("/updatematerialbom_detail")
    @ResponseBody
    @Authorization
    private ResponseEntity<?> updateMatBOMDetail(@Valid MaterialBOMDetailDTO matdic,BindingResult bindingResult,@CurrentUser String operator){
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(ResultModel.vaildFailed(bindingResult.getAllErrors()), HttpStatus.OK);
        }
        ResponseEntity<?> rs=new ResponseEntity<>(null,HttpStatus.OK);
        Boolean result=materialbomService.updateMatBOMDetail(matdic,operator);
        if(result) {
            rs= new ResponseEntity<>(ResultModel.modifyOk(ResultStatus.MODIFY_SUCCESS), HttpStatus.OK);
        }else {
            rs=new ResponseEntity<>(ResultModel.error(ResultStatus.MODIFY_ERROR), HttpStatus.NOT_MODIFIED);
        }
        return rs;
    }
}
