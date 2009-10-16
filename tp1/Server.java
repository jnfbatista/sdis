
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.util.HashMap;

public class Server {

	private static final String notFound = "NOT_FOUND";
	private static final String SUCCESS = "0";
	private static final String ERROR = "1";
	private static HashMap<String, String> dnsTable;
	private static DatagramSocket socket = null;

	public static void main(String args[]){
		dnsTable = new HashMap<String, String>();
		
		if (args.length != 1)
			usage();
		else {	
			try {
				socket = new DatagramSocket(Integer.valueOf(args[0]));
				recievePackets();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	
	}
	
	/**
	 * Main loop for listening for the socket
	 */
	private static void recievePackets() {
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
			System.out.println("LOOKUP");
			String msg = "";
				if (dnsTable.containsKey(data[1])) {
					System.out.println("Found");
					msg = msg.concat(data[1]).concat(" ").concat(dnsTable.get(data[1]));
					byte[] buf = msg.getBytes();

					socket.send(new DatagramPacket(buf,buf.length, p.getAddress(), p.getPort()));
				} else {
					System.out.println("Found NOT");
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

	public static String lookup(String dns_addr) {
		return notFound;
	}

	private static void usage() {
		System.out.println("Usage: java Server <port_number>");
	}

}
