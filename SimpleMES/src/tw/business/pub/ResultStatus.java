package tw.business.pub;

/**
 * 自定义请求状态码
 * @author YWW
 * @date 2017/7/15.
 */
public enum ResultStatus {
    SUCCESS(100, "成功"),
    MODIFY_SUCCESS(200,"修改成功"),
    VAILD_FAILED(-1005,"验证失败"),
    MODIFY_ERROR(-1004,"修改失败"),
    SAVE_SUCCESS(200,"保存成功"),
    SAVE_ERROR(-1005,"保存失败"),
    USERNAME_OR_PASSWORD_ERROR(-1001, "用户名或密码错误"),
    USER_NOT_FOUND(-1002, "用户不存在"),
    USER_NOT_LOGIN(-1003, "用户未登录");
    /**
     * 返回码
     */
    private int code;

    /**
     * 返回结果描述
     */
    private String message;

    ResultStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
