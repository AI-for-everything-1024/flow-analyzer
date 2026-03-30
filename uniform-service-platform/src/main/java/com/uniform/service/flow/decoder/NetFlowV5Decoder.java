package com.uniform.service.flow.decoder;

import com.uniform.service.flow.model.FlowRecord;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * NetFlow V5 解码器实现
 * 迁移自 netflow-parser-java 技能
 */
public class NetFlowV5Decoder extends ByteToMessageDecoder {
    
    private static final int V5_HEADER_SIZE = 24;
    private static final int V5_RECORD_SIZE = 48;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        if (in.readableBytes() < V5_HEADER_SIZE) return;

        // 标记读取位置以便回滚
        in.markReaderIndex();

        int version = in.readUnsignedShort();
        if (version != 5) {
            // 非 V5 版本，交给其他解码器或记录错误
            in.resetReaderIndex();
            return;
        }

        int count = in.readUnsignedShort();
        in.skipBytes(20); // 跳过 Header 中其余字段 (uptime, unix_secs, etc.)

        for (int i = 0; i < count; i++) {
            if (in.readableBytes() < V5_RECORD_SIZE) break;
            
            FlowRecord record = new FlowRecord();
            record.setProtocolType("NETFLOW_V5");
            // 伪代码解析逻辑
            // record.setSrcIp(...);
            // ...
            out.add(record);
        }
    }
}
