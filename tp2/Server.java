import java.util.HashMap;
import java.net.MulticastSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Timer;
import java.util.TimerTask;

public class Server {

	private static final String notFound = "NOT_FOUND";
	private static final String SUCCESS = "0";
	private static final String ERROR = "1";
	private static HashMap<String, String> dnsTable;
	private static MulticastSocket socket = null;
	private static MulticastSocket anounceSocket = null;
	private static InetAddress group;
	private static int servicePort;
	private static int announcePort;

	private static final int announceDelay = 1000;

	private static Timer announceTimer;

	/**
	 * Server srvc_port mcast_address mcast_port
	 */
	public static void main(String args[]){
		dnsTable = new HashMap<String, String>();
		
		if (args.length != 3)
			usage();
		else {	
			try {
				//Initiating the socket for the service
				servicePort = Integer.valueOf(args[0]);
				socket = new MulticastSocket(servicePort);


				// Joining a multicast group
				group = InetAddress.getByName(args[1]);
				socket.joinGroup(group);
				
				// Initiating announce socket
				announcePort = Integer.valueOf(args[2]);
				anounceSocket = new MulticastSocket(announcePort);
				anounceSocket.joinGroup(group);

				announceTimer = new Timer(true);
				announceTimer.schedule( 
				new TimerTask() {
					public void run() {
						announceService();
					}
					
				}, 0, announceDelay);



				// Recieve packets
				waitForPackets();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * Announce service
	 */
	public static void announceService() {
		try { 
			String msg = group.getHostAddress() + ":"+ String.valueOf(servicePort);

			byte[] buffer = msg.getBytes();
			
			DatagramPacket announcePacket = new DatagramPacket(buffer, buffer.length, group, announcePort);

			anounceSocket.send(announcePacket);



		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * Main loop for listening for the socket
	 */
	private static void waitForPackets() {
		while(true) {
			try {
				byte[] buf = new byte[256];
				DatagramPacket p = new DatagramPacket(buf, buf.length);
				
				socket.receive(p);

				processPackets(p);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Process Incoming packets
	 */
	private static void processPackets(DatagramPacket p) {
		try{
			String packet = new String(p.getData(), 0, p.getLength());
			String[] data = packet.split(" ");
			if (data[0].equals("REGISTER")) {
				
				if (!dnsTable.containsKey(data[1])) {
					dnsTable.put(data[1], data[2]);
					System.out.printf("Register %s %s\n", data[1], data[2]);
				
					socket.send(new DatagramPacket(SUCCESS.getBytes(), SUCCESS.length(), p.getAddress(), p.getPort()));
				} else { 
					socket.send(new DatagramPacket(ERROR.getBytes(), ERROR.length(), p.getAddress(), p.getPort()));
				}
	
			} else if (data[0].equals("LOOKUP")) {
			String msg = "";
				if (dnsTable.containsKey(data[1])) {
					System.out.println("Found");
					msg = msg.concat(data[1]).concat(" ").concat(dnsTable.get(data[1]));
					byte[] buf = msg.getBytes();

					socket.send(new DatagramPacket(buf,buf.length, p.getAddress(), p.getPort()));
				} else {
					socket.send(new DatagramPacket(notFound.getBytes(), notFound.length(), p.getAddress(), p.getPort()));
				}

			} else {
				System.out.println("No go");
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
	}


	public static void register(String dnsAddr, String ip) {
		dnsTable.put(dnsAddr, ip);
	}

	/**
	 * Program usage...
	 */
	private static void usage() {
		System.out.println("Usage: java Server <port_number> <mcast_address> <mcast_port>");
	}

}
