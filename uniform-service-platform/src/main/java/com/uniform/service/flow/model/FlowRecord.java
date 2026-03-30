package com.uniform.service.flow.model;

import lombok.Data;

/**
 * 统一流量记录模型
 */
@Data
public class FlowRecord {
    private String protocolType; // "NETFLOW_V5", "NETFLOW_V9", "IPFIX", "SFLOW"
    private long timestamp;
    private String exporterIp;

    private String srcIp;
    private String dstIp;
    private int srcPort;
    private int dstPort;
    private int protocol;
    private int tos;

    private long bytes;
    private long packets;

    private int inputInterface;
    private int outputInterface;
    private int tcpFlags;
    
    private long samplingRate;
}
