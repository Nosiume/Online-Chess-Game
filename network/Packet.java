package network;

import game.Launcher;

import java.net.DatagramPacket;
import java.net.InetAddress;

public abstract class Packet {
	
	public byte packetID;
	
	public Packet(int packetID)
	{
		this.packetID = (byte) packetID;
	}
	
	public abstract void onClientReceive(InetAddress addr);
	public abstract void writeData(Client client);
	//public abstract void writeData(Server server);
	public abstract byte[] getData();
	
	public String readData(byte[] data)
	{
		String message = new String(data).trim();
		return message.substring(2);
	}
	
	public void sendDataToClient(InetAddress address, int port)
	{
		byte[] data = getData();
		DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
		packet.setData(data);
		Launcher.SERVER.sendPacket(packet);
	}
	
	public static PacketType lookupPacket(String id)
	{
		try {
			int packetID = Integer.parseInt(id);
			return lookupPacket(packetID);
		} catch(NumberFormatException e)
		{
			return PacketType.INVALID;
		}
	}
	
	public static PacketType lookupPacket(int id)
	{
		for(PacketType p : PacketType.values())
		{
			if(p.getID() == id)
			{
				return p;
			}
		}
		return PacketType.INVALID;
	}
	
	
}
