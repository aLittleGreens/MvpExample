package com.klaus.common.basebean;

import java.io.Serializable;

/**
 * des:封装服务器返回数据
 *
 */
public class BaseResponse<T> implements Serializable {
    public String code;
    public String msg;

    public T data;

    public boolean success() {
        return "200".equals(code)||"0".equals(code);
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
