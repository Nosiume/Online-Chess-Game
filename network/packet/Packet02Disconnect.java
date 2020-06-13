package network.packet;

import game.Launcher;

import java.net.InetAddress;

import network.Client;
import network.Packet;

public class Packet02Disconnect extends Packet {

	private String username;
	
	public Packet02Disconnect(byte[] data) 
	{
		super(02);
		this.username = readData(data);
	}
	
	public Packet02Disconnect(String username)
	{
		super(02);
		this.username = username;
	}
	
	@Override
	public void onClientReceive(InetAddress address) 
	{
		//When we receive it, that means the client has been removed from the
		//Online player's list on the server data
		//So we ask for the player list again to update
		Packet01PlayerList packet = new Packet01PlayerList();
		packet.writeData(Launcher.CLIENT);
	}
	
	@Override
	public void writeData(Client client) {
		client.sendToServer(this);
	}

	@Override
	public byte[] getData() {
		return ("02" + username).getBytes();
	}
	
	public String getUsername()
	{
		return this.username;
	}

}
