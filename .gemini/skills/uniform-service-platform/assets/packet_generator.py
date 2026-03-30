import socket
import struct
import time
import argparse

def generate_v5_packet(count=1):
    """生成一个简单的 NetFlow V5 模拟报文"""
    # Header: version=5, count, uptime, seconds, nanos, seq, engine_type, engine_id, sampling
    header = struct.pack('!HHIIIIBBH', 5, count, 1000, int(time.time()), 0, 1, 0, 0, 0)
    
    # Record: src, dst, nexthop, input, output, pkts, octets, first, last, srcport, dstport, pad, flags, prot, tos, srcas, dstas, srcmask, dstmask, pad
    # 示例 IP: 192.168.1.1 (0xC0A80101) -> 10.0.0.1 (0x0A000001)
    record = struct.pack('!IIIHHIIIIHHBBBBHHBBH', 
                         0xC0A80101, 0x0A000001, 0x00000000, 1, 2, 10, 1000, 100, 200, 1234, 80, 0, 0, 6, 0, 100, 200, 24, 24, 0)
    
    return header + (record * count)

def main():
    parser = argparse.ArgumentParser(description="NetFlow V5 Packet Generator")
    parser.add_argument("--host", default="127.0.0.1", help="Target UDP IP")
    parser.add_argument("--port", type=int, default=2055, help="Target UDP Port")
    parser.add_argument("--interval", type=float, default=1.0, help="Interval in seconds")
    args = parser.parse_args()

    sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    print(f"Sending NetFlow V5 packets to {args.host}:{args.port} every {args.interval}s...")

    try:
        while True:
            packet = generate_v5_packet()
            sock.sendto(packet, (args.host, args.port))
            time.sleep(args.interval)
    except KeyboardInterrupt:
        print("\nStopped.")

if __name__ == "__main__":
    main()
