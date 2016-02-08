/**
 * 
 */
package com.hongbao.thrifthello;

import java.io.IOException;

import org.apache.thrift.TException;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TNonblockingSocket;
import org.apache.thrift.transport.TNonblockingTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

/**
 * @author hzllb
 *
 * 2016年2月7日
 */
public class HelloServiceClient {
    public static void main(String[] args) throws TException { 
    	//同步调用，先send再read response
//        try { 
//            // 设置调用的服务地址为本地，端口为 7911 
//            TTransport transport = new TSocket("localhost", 7911); 
//            transport.open(); 
//            // 设置传输协议为 TBinaryProtocol 
//            TProtocol protocol = new TBinaryProtocol(transport); 
//            Hello.Client client = new Hello.Client(protocol); 
//            // 调用服务的 helloVoid 方法
//            client.helloVoid(); 
//            System.out.println(client.helloString("123"));
//            transport.close(); 
//        } catch (TTransportException e) { 
//            e.printStackTrace(); 
//        } catch (TException e) { 
//            e.printStackTrace(); 
//        } 
        try { 
            TAsyncClientManager clientManager = new TAsyncClientManager(); 
            TNonblockingTransport transport = new TNonblockingSocket( 
                    "localhost", 7911); 
            TProtocolFactory protocol = new TBinaryProtocol.Factory(); 
            Hello.AsyncClient asyncClient = new Hello.AsyncClient(protocol, 
                    clientManager, transport); 
            System.out.println("Client calls ....."); 
            MethodCallback callBack = new MethodCallback(); 

            //asyncClient.helloVoid(callBack);
            asyncClient.helloString("123", callBack);
            //asyncClient.helloString("Hello World", callBack); 
            Object res = callBack.getResult(); 
            while (res == null) { 
                System.out.println("没有输出");
                res = callBack.getResult(); 
    
            } 
            System.out.println("有输出了");
            System.out.println(((Hello.AsyncClient.helloString_call) res) 
                    .getResult()); 
            

            
        } catch (IOException e) { 
            e.printStackTrace(); 
        } 
    } 
}
