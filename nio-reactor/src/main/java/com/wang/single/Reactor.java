package com.wang.single;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @Author: wangzhichao
 * @Date: 2020/5/28 9:24
 */

class Reactor implements Runnable
{
	final Selector selector;
	final ServerSocketChannel serverSocket;

	Reactor(int port) throws IOException
	{   //Reactor初始化
		selector = Selector.open();
		//打开ServerSocketChannel
		serverSocket = ServerSocketChannel.open();
		//绑定网卡地址
		serverSocket.socket().bind(new InetSocketAddress(port));
		//设置为非阻塞
		serverSocket.configureBlocking(false);

		//分步处理,第一步,接收accept事件
		SelectionKey sk =
				serverSocket.register(selector, SelectionKey.OP_ACCEPT);
		//将任务attach到key上
		sk.attach(new Acceptor());
	}

	@Override
	public void run()
	{
		//循环主线程不停的select 事件
		try
		{
			while (!Thread.interrupted())
			{
				selector.select();
				java.util.Set selected = selector.selectedKeys();
				Iterator it = selected.iterator();
				while (it.hasNext())
				{
					//Reactor负责dispatch收到的事件
					dispatch((SelectionKey) (it.next()));
				}
				selected.clear();
			}
		} catch (IOException ex)
		{ /* ... */ }
	}

	void dispatch(SelectionKey k)
	{
		Runnable r = (Runnable) (k.attachment());
		//调用之前注册的callback对象
		if (r != null)
		{
			r.run();
		}
	}

	// inner class
	class Acceptor implements Runnable
	{
		@Override
		public void run()
		{
			try
			{
				//建立一个客户端的SocketChannel
				SocketChannel channel = serverSocket.accept();
				//如果Channel不为空，则新建对应的Handler
				if (channel != null) {
					new Handler(selector, channel);
				}
			} catch (IOException ex)
			{ /* ... */ }
		}
	}
}
