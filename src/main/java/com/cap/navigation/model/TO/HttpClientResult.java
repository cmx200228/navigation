package com.cap.navigation.model.TO;

import java.io.Serializable;

/**
 * @author 陈蒙欣
 * @date 2023/3/20 22:17
 */
public class HttpClientResult implements Serializable {
    private int code;
    private String content;

    public HttpClientResult(Integer code) {
        this.code = code;
    }

    public HttpClientResult(Integer statusCode, String content) {
        this.code = statusCode;
        this.content = content;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
