package network.packet;

import java.net.InetAddress;

import network.Client;
import network.Packet;

public class Packet00Connect extends Packet {

	private String username;
	
	public Packet00Connect(byte[] data)
	{
		super(00);
		this.username = readData(data);
	}
	
	public Packet00Connect(String username)
	{
		super(00);
		this.username = username;
	}

	@Override
	public void onClientReceive(InetAddress address) {}
	
	@Override
	public void writeData(Client client) {
		client.sendToServer(this);
	}

	/*
	@Override
	public void writeData(Server server) {
		server.sendToAllClient(this);
	}
	*/

	@Override
	public byte[] getData() {
		return ("00" + username).getBytes();
	}
	
	public String getUsername()
	{
		return this.username;
	}
	
}
