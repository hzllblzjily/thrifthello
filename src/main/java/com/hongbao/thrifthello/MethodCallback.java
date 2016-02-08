/**
 * 
 */
package com.hongbao.thrifthello;

import org.apache.thrift.async.AsyncMethodCallback;

/**
 * @author hzllb
 *
 * 2016年2月8日
 */
public class MethodCallback implements AsyncMethodCallback<Object>{
    Object response = null; 

    public Object getResult() { 
        // 返回结果值
        return this.response; 
    } 

    // 处理服务返回的结果值
    public void onComplete(Object response) { 
        this.response = response; 
    } 
    // 处理调用服务过程中出现的异常
    public void onError(Exception throwable) { 

    }

}
