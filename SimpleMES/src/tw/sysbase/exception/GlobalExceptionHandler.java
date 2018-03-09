package tw.sysbase.exception ;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import tw.sysbase.pub.Constants;
import tw.sysbase.pub.DateBean;
import tw.sysbase.pub.ResultUtil;

@EnableWebMvc
@ControllerAdvice(basePackages="tw")
@ResponseBody
public class GlobalExceptionHandler {
	

    /**
     * 拦截web层异常，记录异常日志，并返回友好信息到前端
     * 目前只拦截Exception，是否要拦截Error需再做考虑
     *
     * @param e 异常对象
     * @return 异常提示
     */
    @ExceptionHandler(Exception.class)
    public Map<String,Object> handleException(Exception exception,HttpServletRequest request) {
        //不需要再记录ServiceException，因为在service异常切面中已经记录过
    	
//        System.out.println(exception.getClass());
    	String message = "";
        //按需重新封装需要返回的错误信息
        if (exception instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException e = (MethodArgumentNotValidException) exception;
            
            for (FieldError error : e.getBindingResult().getFieldErrors()) {
                message += error.getDefaultMessage() + "  ";
                message+="Field: "+error.getField()+"   错误信息: "+error.getDefaultMessage();

            }
        }
    /*    } else if (exception instanceof LuckDrawException) {//业务自定义异常
            LuckDrawException luckDrawExcetion = (LuckDrawException) exception;
            response = new AppResponseData(luckDrawExcetion.getAppCode().getCode(),luckDrawExcetion.getAppCode().getMessage()+luckDrawExcetion.getMessage());
        } else {
            response = new AppResponseData(new LuckDrawException().getAppCode());
        }*/

        String now = DateBean.getNow();
        String requestUrl = request.getRequestURI();
        String paramMapString = "";
        Map requestMap = (Map) request.getParameterMap();
        Iterator iter = requestMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            Object val = entry.getValue();
            paramMapString+=key.toString()+":"+val.toString()+"\n";
        }

      /*  StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);*/

            //logger.error("请求时间:{},请求URL:{},请求参数:{},错误信息:{},错误栈信息:{}", new Object[]{now, requestUrl,paramMapString,exception.getMessage(),errorStackTrace});
            message="请求时间:{"+now+"},请求URL:{"+requestUrl+"},请求参数:{"+paramMapString+"},错误信息:{"+exception.getMessage()+"},错误栈信息:{"+message+"}";
            Map<String,Object> result=ResultUtil.ErrorResult();
            result.put(Constants.SUCCESS_MSG, Constants.FAILURE_MSG);
            exception.printStackTrace();
			LogException.logEx(exception,this.getClass(),message);
		//	DebugOut
            return result;
       
    }
}
