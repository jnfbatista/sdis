import java.net.MulticastSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;


public class Client {


	public static void main(String args[]) {
		recieveAnnouncePacket(args[0], args[1]);
		

	}

	public static String[] recieveAnnouncePacket(String ip, String port){
		String[] address;
		try {
			MulticastSocket recieveSocket = new MulticastSocket(Integer.valueOf(port));

			byte[] buf = new byte[128];


			recieveSocket.joinGroup(InetAddress.getByName(ip));
			
			DatagramPacket p = new DatagramPacket(buf, 128);

			recieveSocket.receive(p);

			String packet = new String(p.getData(), 0, p.getLength());
			System.out.printf("%s\n", packet);

			address = packet.Split(":");


		} catch (Exception e) {
			e.printStackTrace();

		}

	}

}
