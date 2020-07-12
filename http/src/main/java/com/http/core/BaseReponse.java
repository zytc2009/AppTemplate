package com.http.core;

/**
 * Created by whb on 2020/07/10.
 *
 * desc : 接口返回的实体基类
 */
public class BaseReponse<T> {
    private int code;                   //响应码
    private String message;             //提示信息
    private T result;                  //返回的具体数据

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

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "BaseReponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", result=" + result +
                '}';
    }
}
