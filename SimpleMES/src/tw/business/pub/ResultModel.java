package tw.business.pub;

import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;


/**
 * 自定义返回结果
 * @author XieEnlong
 * @date 2015/7/14.
 */
public class ResultModel {

    /**
     * 返回码
     */
    private int code;

    /**
     * 返回结果描述
     */
    private String message;

    /**
     * 返回内容
     */
    private Object content;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Object getContent() {
        return content;
    }

    public ResultModel(int code, String message) {
        this.code = code;
        this.message = message;
        this.content = "";
    }

    public ResultModel(int code, String message, Object content) {
        this.code = code;
        this.message = message;
        this.content = content;
    }

    public ResultModel(ResultStatus status) {
        this.code = status.getCode();
        this.message = status.getMessage();
        this.content = "";
    }

    public ResultModel(List<ObjectError> ob) {
        this.code = 200;
        this.content = ob;
    }
    
    public ResultModel(ResultStatus status, Object content) {
        this.code = status.getCode();
        this.message = status.getMessage();
        this.content = content;
    }

    public static ResultModel ok(Object content) {
        return new ResultModel(ResultStatus.SUCCESS, content);
    }
    
    public static ResultModel vaildFailed(Object content) {
        return new ResultModel(ResultStatus.VAILD_FAILED, content);
    }
    
    public static ResultModel modifyOk(Object content) {
        return new ResultModel(ResultStatus.MODIFY_SUCCESS, content);
    }

    public static ResultModel ok() {
        return new ResultModel(ResultStatus.SUCCESS);
    }

    public static ResultModel error(ResultStatus error) {
        return new ResultModel(error);
    }
    
    public static ResultModel error(List<ObjectError> error) {
        return new ResultModel(error);
    }

    public static Object saveOk(Object content) {
        return new ResultModel(ResultStatus.MODIFY_SUCCESS, content);
    }
}
