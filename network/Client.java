package network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import javax.swing.JOptionPane;

import network.packet.Packet00Connect;
import network.packet.Packet01PlayerList;
import network.packet.Packet02Disconnect;
import network.packet.Packet03Start;

public class Client implements Runnable {

	private DatagramSocket socket;
	private boolean running = true;
	private InetAddress serverAddress;
	private Thread clientThread;

	public Client(InetAddress serverAddress) {
		try {
			this.serverAddress = serverAddress;
			this.socket = new DatagramSocket();
			this.clientThread = new Thread(this);
			this.clientThread.start();
		} catch (SocketException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Can't create Client Socket",
					"UDP Protocol Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}

	@Override
	public void run() {
		while (running) {
			try {
				byte[] buffer = new byte[8192];
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length,
						this.serverAddress, Server.PORT);
				socket.receive(packet);
				String data = new String(packet.getData());

				parsePacket(data, packet.getAddress());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void stop()
	{
		socket.close();
		running = false;
	}

	private void parsePacket(String data, InetAddress address) {
		PacketType type = Packet.lookupPacket(data.substring(0, 2));
		switch (type) {
		case CONNECT:
			Packet00Connect pConnect = new Packet00Connect(data.getBytes());
			pConnect.onClientReceive(address);
			break;
		case DISCONNECT:
			Packet02Disconnect pDisconnect = new Packet02Disconnect(data.getBytes());
			pDisconnect.onClientReceive(address);
			break;
		case PLAYERLIST:
			Packet01PlayerList pPlayerList = new Packet01PlayerList(data.getBytes());
			pPlayerList.onClientReceive(address);
			break;
		case START:
			Packet03Start pStart = new Packet03Start();
			pStart.onClientReceive(address);
			break;
		default:
			System.err.println("Unknown packet on client side. [" + type.toString() + "]");
			break;
		}
	}

	public void sendToServer(Packet packet) {
		byte[] data = packet.getData();
		DatagramPacket _packet = new DatagramPacket(data, data.length,
				this.serverAddress, Server.PORT);
		_packet.setData(data);
		try {
			socket.send(_packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
