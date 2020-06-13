package game.gui;

import game.Launcher;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import network.Client;
import network.Server;
import network.packet.Packet00Connect;
import network.packet.Packet01PlayerList;

/*
 * This window will ask if the user wants to : 
 * - Join a room (join server)
 * - Create a room (host server)
 * 
 * We use basic JFrame from java default libs because bothering us with
 * OpenGL is not required here.
 */
public class ConnectWindow extends JFrame {

	//Useful to take information easily and being understandable
	public enum UserTypeWaitingRoom
	{
		SERVER,
		CLIENT,
		NONE;
	}
	
	//Serial version ID by default for JFrame constructor
	private static final long serialVersionUID = 1L;
	
	//ConnectWindow's variables
	private JPanel panel;
	private JButton startBtn;
	private List<JLabel> userNames;
	private UserTypeWaitingRoom userType = UserTypeWaitingRoom.NONE;
	
	//Creates the empty window before adding elements with other functions
	public ConnectWindow(boolean fullscreen)
	{	
		//Create window
		super("Online Chess - Join a room");
		
		//Initialize
		userNames = new ArrayList<JLabel>();
		
		if(fullscreen)
		{
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			setSize(dim);
			setUndecorated(true);
			setLocation(0, 0);
		} else 
		{
			setSize(1280, 720);
			setLocationRelativeTo(null);
		}
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addWindowListener(new WaitingRoomWindowListener());
		setResizable(false);
		setIconImage(Launcher.ICON);
		setVisible(true);
		
		panel = new JPanel();
		panel.setSize(getWidth(), getHeight());
		panel.setLayout(null);
		panel.setBackground(Color.BLACK);
		add(panel);
	}

	/*
	 * Adds the two buttons for hosting or joining a game
	 * This is the first scene that the user will see when starting our program.
	 * It also adds username input to identify the users
	 */
	public void defaultElements()
	{
		Font font = Launcher.GLOBAL_FONT.deriveFont(76F);
		
		JTextField username = new JTextField(26);
		username.setText("Username...");
		username.setBounds(getWidth() / 2 - 500, getHeight() / 2 - 450, 1000, 200);
		username.setFont(font);
		username.setBackground(Color.DARK_GRAY);
		username.setForeground(Color.white);
		
		JButton host = new JButton("Host Game");
		host.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mouseExited(MouseEvent e) {
				host.setBackground(Color.BLACK);
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				host.setBackground(Color.GRAY);
			}
		});
		host.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Creates network components
				try {
					Launcher.SERVER = new Server();
					Launcher.CLIENT = new Client(InetAddress.getLocalHost());
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				}
				
				//Saving username
				Launcher.USERNAME = username.getText().trim();
				
				//Sends connection packet
				Packet00Connect packet = new Packet00Connect(Launcher.USERNAME);
				packet.writeData(Launcher.CLIENT);
				
				//Ask for player list (will be updated when sent back to client)
				Packet01PlayerList packetList = new Packet01PlayerList();
				packetList.writeData(Launcher.CLIENT);
				
				//Display update
				cleanScreen();
				waitingRoomServerSide(Launcher.USERNAME);
			}
		});
		host.setForeground(Color.white);
		host.setFont(font);
		host.setFocusPainted(false);
		host.setBounds(getWidth() / 2 - 500, getHeight() / 2 - 150, 1000, 200);
		host.setBackground(Color.BLACK);
		host.setBorderPainted(false);
		
		JButton join = new JButton("Join Game");
		join.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mouseExited(MouseEvent e) {
				join.setBackground(Color.BLACK);
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				join.setBackground(Color.GRAY);
			}
		});
		join.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Initializing server components
				try {
					String host = JOptionPane.showInputDialog("Please enter host IPv4");
					Launcher.CLIENT = new Client(InetAddress.getByName(host));
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				}
				
				//Saving username
				Launcher.USERNAME = username.getText().trim();
				
				//Join packet
				Packet00Connect packetJoin = new Packet00Connect(Launcher.USERNAME);
				packetJoin.writeData(Launcher.CLIENT);
				
				//Ask for player list (will be updated when sent back to client)
				Packet01PlayerList packetList = new Packet01PlayerList();
				packetList.writeData(Launcher.CLIENT);
				
				//Display update
				cleanScreen();
				waitingRoomClientSide(Launcher.USERNAME);
			}
		});
		join.setForeground(Color.white);
		join.setFont(font);
		join.setFocusPainted(false);
		join.setBounds(getWidth() / 2 - 500, getHeight() / 2 + 250, 1000, 200);
		join.setBackground(Color.BLACK);
		join.setBorderPainted(false);
		
		panel.add(host);
		panel.add(join);
		panel.add(username);
		panel.update(panel.getGraphics());
	}
	
	//Client side waiting room display
	private void waitingRoomClientSide(String ign)
	{
		this.userType = UserTypeWaitingRoom.CLIENT;
		Font font = Launcher.GLOBAL_FONT.deriveFont(66F);
		
		JLabel nameLabel = new JLabel("Connected as " + ign);
		nameLabel.setFont(font.deriveFont(50F));
		nameLabel.setForeground(Color.WHITE);
		nameLabel.setBounds(getWidth() / 6, getHeight() / 4, 600, 200);
		
		startBtn = new JButton("Waiting To Start Game");
		startBtn.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}

			@Override
			public void mouseEntered(MouseEvent e) {
				startBtn.setForeground(Color.WHITE);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				startBtn.setForeground(Color.CYAN);
			}
		});
		startBtn.setFont(font);
		startBtn.setBounds(getWidth() / 7, getHeight() / 2, 600, 200);
		startBtn.setBackground(Color.BLACK);
		startBtn.setForeground(Color.CYAN);
		startBtn.setFocusPainted(false);
		startBtn.setEnabled(false);
		
		JLabel playerList = new JLabel("Player List : ");
		playerList.setFont(font);
		playerList.setForeground(Color.WHITE);
		playerList.setBounds((int) (getWidth() / 1.5), getHeight() / 4, 600, 200);
		
		panel.add(nameLabel);
		panel.add(playerList);
		panel.add(startBtn);
		panel.update(panel.getGraphics());
	}
	
	//Server side waiting room display
	private void waitingRoomServerSide(String ign)
	{
		this.userType = UserTypeWaitingRoom.SERVER;
		Font font = Launcher.GLOBAL_FONT.deriveFont(66F);
		
		JLabel nameLabel = new JLabel("Connected as " + ign);
		nameLabel.setFont(font.deriveFont(50F));
		nameLabel.setForeground(Color.WHITE);
		nameLabel.setBounds(getWidth() / 6, getHeight() / 4, 600, 200);
		
		startBtn = new JButton("Start Game");
		startBtn.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}

			@Override
			public void mouseEntered(MouseEvent e) {
				startBtn.setForeground(Color.WHITE);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				startBtn.setForeground(Color.CYAN);
			}
		});
		startBtn.setFont(font);
		startBtn.setBounds(getWidth() / 7, getHeight() / 2, 600, 200);
		startBtn.setBackground(Color.BLACK);
		startBtn.setForeground(Color.CYAN);
		startBtn.setFocusPainted(false);
		startBtn.setEnabled(false);
		
		JLabel playerList = new JLabel("Player List : ");
		playerList.setFont(font);
		playerList.setForeground(Color.WHITE);
		playerList.setBounds((int) (getWidth() / 1.5), getHeight() / 4, 600, 200);
		
		panel.add(nameLabel);
		panel.add(playerList);
		panel.add(startBtn);
		panel.update(panel.getGraphics());
	}
	
	//Close waiting room
	public void close()
	{
		setVisible(false);
	}
	
	//Adds User to player list
	public void addUser(String ign)
	{
		Font font = Launcher.GLOBAL_FONT.deriveFont(66F);
		JLabel listIGN = new JLabel(ign);
		listIGN.setFont(font);
		listIGN.setForeground(Color.WHITE);
		listIGN.setBounds((int) (getWidth() / 1.75), (int) (getHeight() / 2.5) + userNames.size() * 120, 600, 120);
		panel.add(listIGN);
		this.userNames.add(listIGN);
		
		if(userNames.size() >= 2 && 
				userType != UserTypeWaitingRoom.CLIENT)
		{
			startBtn.setEnabled(true);
			startBtn.update(panel.getGraphics()); // To avoid graphic duplication of buttons
		}
		panel.update(panel.getGraphics());
	}
	
	//Removes all elements currently on the screen
	private void cleanScreen()
	{
		for(Component c : panel.getComponents())
		{
			panel.remove(c);
		}
	}

	//Clears the player list display
	public void clearPlayerList() 
	{
		for(JLabel label : this.userNames)
		{
			panel.remove(label);
		}
		this.userNames.clear();
		panel.update(panel.getGraphics());
	}
	
}
