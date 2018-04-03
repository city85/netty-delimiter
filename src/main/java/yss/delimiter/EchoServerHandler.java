package yss.delimiter;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


public class EchoServerHandler extends ChannelInboundHandlerAdapter {

	int counter = 0;
	/**
	 * 复写ChannelInboundHandlerAdapter的channelRead方法，将接受到客户端的消息返回给客户端
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		String body = String.valueOf(msg);
		System.out.println("This is "+ ++counter +"times receive client:["+	body +"]");
		//由于设置的DelimitBasedFrameDecoder过滤掉分割符，所以返回给客户端的时候，需要加上
		body +="$_";
		ByteBuf echo = Unpooled.copiedBuffer(body.getBytes());
		ctx.writeAndFlush(echo);
	}


	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}
