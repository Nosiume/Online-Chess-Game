package network;

import java.net.InetAddress;

public class NetworkClient {

	private String username;
	private InetAddress address;
	private int port;
	
	public NetworkClient(String name, InetAddress address, int port)
	{
		this.username = name;
		this.address = address;
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public InetAddress getAddress() {
		return address;
	}

	public int getPort() {
		return port;
	}
	
}
