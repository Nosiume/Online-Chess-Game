package network.packet;

import game.Launcher;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import network.Client;
import network.Packet;

public class Packet01PlayerList extends Packet {

	private List<String> names = new ArrayList<String>();
	
	public Packet01PlayerList(byte[] data) 
	{
		super(01);
		String str = new String(data).substring(2);
		for(String name : str.split(";"))
		{
			this.names.add(name);
		}
	}
	
	public Packet01PlayerList()
	{
		super(01);
	}
	
	public Packet01PlayerList(List<String> names)
	{
		super(01);
		this.names = names;
	}

	@Override
	public void onClientReceive(InetAddress addr) {
		Launcher.connectWindow.clearPlayerList();
		for(String name : this.names)
		{
			Launcher.connectWindow.addUser(name);
		}
	}

	@Override
	public void writeData(Client client) {
		client.sendToServer(this);
	}

	@Override
	public byte[] getData() {
		String names = "";
		for(String str : this.names)
		{
			names+=str + ";";
		}
		return ("01" + names).getBytes();
	}

}
