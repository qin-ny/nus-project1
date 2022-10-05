package nus.iss.team1.project1.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;


@AllArgsConstructor
@Data
public class ResponseResult<T> implements Serializable {
    private int code = 200;
    private String msg = "Success";
    private T data;

    @Getter
    public enum ResponseEnum {
        SUCCESS(200,"Success"),
        ERROR(403,"Error"),
        INTERNAL_ERROR(501, "Internal Error");

        private int resultCode;
        private String resultMsg;

        ResponseEnum(int resultCode, String resultMsg) {
            this.resultCode = resultCode;
            this.resultMsg = resultMsg;
        }
    }

    private ResponseResult() {
    }

    public static ResponseResult success() {
        return success(null);
    }

    public static ResponseResult success(Object data) {
        return success(ResponseEnum.SUCCESS.getResultCode(), ResponseEnum.SUCCESS.getResultMsg(), data);
    }

    public static ResponseResult success(String msg, Object data) {
        return success(ResponseEnum.SUCCESS.getResultCode(), msg, data);
    }

    public static ResponseResult success(int code, Object data) {
        return success(code, ResponseEnum.SUCCESS.getResultMsg(), data);
    }

    public static ResponseResult success(int code, String msg, Object data) {
        ResponseResult responseResult = new ResponseResult<>();
        responseResult.setCode(code);
        responseResult.setMsg(msg);
        responseResult.setData(data);
        return responseResult;
    }

    public static ResponseResult error() {
        return error(null);
    }

    public static ResponseResult error(Object data) {
        return error(ResponseEnum.ERROR.getResultCode(), ResponseEnum.ERROR.getResultMsg(), data);
    }

    public static ResponseResult error(String msg, Object data) {
        return error(ResponseEnum.ERROR.getResultCode(), msg, data);
    }

    public static ResponseResult error(int code, Object data) {
        return error(code, ResponseEnum.ERROR.getResultMsg(), data);
    }

    public static ResponseResult error(int code, String msg, Object data) {
        ResponseResult responseResult = new ResponseResult<>();
        responseResult.setCode(code);
        responseResult.setMsg(msg);
        responseResult.setData(data);

        return responseResult;
    }

    public static ResponseResult internalError() {
        return error(ResponseEnum.INTERNAL_ERROR.getResultCode(), ResponseEnum.INTERNAL_ERROR.getResultMsg(), null);
    }

    public ResponseResult data(T data) {
        this.setData(data);
        return this;
    }

    public ResponseResult code(int code) {
        this.setCode(code);
        return this;
    }

    public ResponseResult msg(String msg) {
        this.setMsg(msg);
        return this;
    }

    @Override
    public String toString() {
        return "RestResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
