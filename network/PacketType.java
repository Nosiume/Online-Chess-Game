package network;

public enum PacketType {

	INVALID(-1),
	CONNECT(00),
	PLAYERLIST(01),
	DISCONNECT(02);
	
	private int id;
	
	PacketType(int id)
	{
		this.id = id;
	}
	
	public int getID()
	{
		return this.id;
	}
	
}
