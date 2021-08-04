package com.lyv.security.modules;


import com.lyv.security.common.util.ResponseModel;

/**
 * controller基类
 * @author youzhian
 */
public class BaseController {

    protected ResponseModel success(){
        return success(null);
    }
    protected ResponseModel success(String msg){
        return success(msg,null);
    }
    protected ResponseModel success(Object data){
        return success(null,data);
    }
    protected ResponseModel success(String msg,Object data){
        return ResponseModel.success(msg,data);
    }

    protected ResponseModel error(){
        return error(null);
    }

    protected ResponseModel error(String msg){
        return error(msg,null);
    }
    protected ResponseModel error(String code,String msg){
        return error(code,msg,null);
    }
    protected ResponseModel error(String msg,Object data){
        return error(null,msg,data);
    }

    protected ResponseModel error(String code,String msg,Object data){
        return ResponseModel.error(code,msg,data);
    }
}
