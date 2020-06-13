package network.packet;

import game.Launcher;
import game.settings.GameStats;

import java.net.InetAddress;

import network.Client;
import network.Packet;

public class Packet03Start extends Packet {

	public Packet03Start() {
		super(03);
	}

	@Override
	public void onClientReceive(InetAddress addr) {
		Launcher.connectWindow.close();
		GameStats.started = true;
	}

	@Override
	public void writeData(Client client) {
		client.sendToServer(this);
	}

	@Override
	public byte[] getData() {
		return "03".getBytes();
	}

}
