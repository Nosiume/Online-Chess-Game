package network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import utils.NetworkUtils;
import network.packet.Packet00Connect;
import network.packet.Packet01PlayerList;
import network.packet.Packet02Disconnect;

public class Server implements Runnable {

	public static final int PORT = 9876;
	
	private DatagramSocket socket;
	private boolean running = true;
	private Thread serverThread;
	private List<NetworkClient> clients = new ArrayList<NetworkClient>();
	
	public Server()
	{
		try {
			this.socket = new DatagramSocket(PORT);
			this.serverThread = new Thread(this);
			this.serverThread.start();
		} catch (SocketException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Can't create Server Socket", "UDP Protocol Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}

	@Override
	public void run() {
		try {
			while(running)
			{	
				byte[] buffer = new byte[8192];
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				this.socket.receive(packet);
								
				parsePacket(new String(packet.getData()), packet.getAddress(), packet.getPort());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void stop()
	{
		socket.close();
		running = false;
	}
	
	private void parsePacket(String data, InetAddress address, int port)
	{
		PacketType type = Packet.lookupPacket(data.substring(0,2));
		switch(type)
		{
		case CONNECT:
			Packet00Connect pConnect = new Packet00Connect(data.getBytes());
			this.join(new NetworkClient(pConnect.getUsername(), address, port));
			sendDataToAllClients(pConnect);
			break;
		case DISCONNECT:
			Packet02Disconnect pDisconnect = new Packet02Disconnect(data.getBytes());
			this.leave(NetworkUtils.getClientByName(pDisconnect.getUsername(), this.clients));
			sendDataToAllClients(pDisconnect);
			break;
		case PLAYERLIST:
			List<String> playerList = new ArrayList<String>();
			for(NetworkClient c : clients)
				playerList.add(c.getUsername());
			Packet01PlayerList pPlayerList = new Packet01PlayerList(playerList);
			sendDataToAllClients(pPlayerList);
			break;
		default:
			sendRawDataToAllClients(data.getBytes());
			break;
		}
	}
	
	private void sendRawDataToAllClients(byte[] data)
	{
		for(NetworkClient c : clients)
		{
			DatagramPacket packet = new DatagramPacket(data, data.length, c.getAddress(), c.getPort());
			packet.setData(data);
			sendPacket(packet);
		}
	}
	
	public void sendDataToAllClients(Packet packet)
	{
		for(NetworkClient c : clients)
		{
			packet.sendDataToClient(c.getAddress(), c.getPort());
		}
	}
	
	public void sendPacket(DatagramPacket p)
	{
		try {
			socket.send(p);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void join(NetworkClient c) 
	{
		this.clients.add(c);
	}
	
	public void leave(NetworkClient c)
	{
		this.clients.remove(c);
	}
	
}
