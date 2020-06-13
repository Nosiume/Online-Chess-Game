package game.waitingroom;

import game.Launcher;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import network.packet.Packet02Disconnect;

public class WaitingRoomWindowListener implements WindowListener {

	@Override
	public void windowOpened(WindowEvent e) {
		
	}

	@Override
	public void windowClosing(WindowEvent e) 
	{
		if(Launcher.USERNAME != null && Launcher.CLIENT != null)
		{
			Packet02Disconnect packet = new Packet02Disconnect(Launcher.USERNAME);
			packet.writeData(Launcher.CLIENT);
		}
		if(Launcher.SERVER != null) Launcher.SERVER.stop();
		if(Launcher.CLIENT != null) Launcher.CLIENT.stop();
	}

	@Override
	public void windowClosed(WindowEvent e) {
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		
	}

}
