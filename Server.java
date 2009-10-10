
import java.util.HashMap;
import java.net.MulticastSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;

public class Server {

	private static final String notFound = "NOT_FOUND";
	private static final String SUCCESS = "0";
	private static final String ERROR = "1";
	private static HashMap<String, String> dnsTable;
	private static MulticastSocket socket = null;
	private static MulticastSocket anounceSocket = null;
	private static final InetAddress group;
	
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
				socket = new MulticastSocket(Integer.valueOf(args[0]));

				// Joining a multicast group
				group = new InetAddress.getByName(args[1]);
				socket.joinGroup(group);
				
				// Initiating announce socket
				anounceSocket = new MulticastSocket(Integer.valueOf(args[2]));

				// Recieve packets
				waitForPackets();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
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
