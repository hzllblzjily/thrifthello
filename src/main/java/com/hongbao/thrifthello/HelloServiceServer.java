/**
 * 
 */
package com.hongbao.thrifthello;

import org.apache.thrift.TProcessor; 
import org.apache.thrift.protocol.TBinaryProtocol; 
import org.apache.thrift.protocol.TBinaryProtocol.Factory; 
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer; 
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer; 
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TServerSocket; 
import org.apache.thrift.transport.TTransportException; 
/**
 * @author hzllb
 *
 * 2016年2月7日
 */
public class HelloServiceServer {

	  public static void main(String[] args) { 
	        try { 
	            // 设置服务端口为 7911 
	            //TServerSocket serverTransport = new TServerSocket(7911); 
	        	
	        	//非阻塞式socket
	        	TNonblockingServerTransport serverTransport = new TNonblockingServerSocket(7911); 
	            // 设置协议工厂为 TBinaryProtocol.Factory 
	            Factory proFactory = new TBinaryProtocol.Factory(); 
	            // 关联处理器与 Hello 服务的实现
	            TProcessor processor = new Hello.Processor(new HelloServiceImpl()); 


	            //最简单的server，单线程阻塞顺序处理客户端请求
	            //TServer server = new TSimpleServer(new TServer.Args(serverTransport).processor(processor));
	            
	            //多线程服务器使用阻塞式io处理,一个连接accept开一个io线程处理
	            //TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor).protocolFactory(proFactory));
	            
	            //单线程服务器外加select非阻塞式io处理，先block在select上，accept后加入对应socket的read监听，read完成后加入write监听，write完成后再加入read监听
	            //TServer server = new TNonblockingServer(new TNonblockingServer.Args(serverTransport).processor(processor).protocolFactory(proFactory));
	      
	            //与nonblocking相比，完成数据读取之后，将业务处理过程交给特定的线程池处理
	            //TServer server = new THsHaServer(new THsHaServer.Args(serverTransport).processor(processor).protocolFactory(proFactory));
	            
	            //一个accept线程负责用select接受连接后从后端的selectorthread中选出一个thread并将socket注册到该thread对应的selector中，之后的所有读写socket操作都由该thread处理，但其业务操作要丢入专门的execute service线程池中处理
	            TServer server = new TThreadedSelectorServer(new TThreadedSelectorServer.Args(serverTransport).processor(processor).protocolFactory(proFactory));
	            
	            
	            
	            System.out.println("Start server on port 7911..."); 
	            server.serve(); 
	        } catch (TTransportException e) { 
	            e.printStackTrace(); 
	        } 
	    } 
}
