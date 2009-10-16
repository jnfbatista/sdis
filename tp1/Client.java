
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.ArrayList;

public class Client {

	private static DatagramSocket socket;
	private static DatagramPacket packet;

	private static ArrayList<String[]> registerList;

	private static String[] lookupList;

	private static InetAddress serverAddr;

	public static void main(String args[]) {
		checkInput(args);
		createMessage(args);
		sendPackets(args);
	}

	
	private static void sendPackets(String args[]) {
		try {
			socket = new DatagramSocket();
			InetAddress address = InetAddress.getByName(args[0]);
			if (args[2].equals("register")) {
				for (int i = 0; i < registerList.size(); i++) {
					String str = "REGISTER ".concat(registerList.get(i)[0]).concat(" ").concat(registerList.get(i)[1]);
					byte[] msg = str.getBytes();
					DatagramPacket p = new DatagramPacket(msg, msg.length, address, Integer.valueOf(args[1]));
					socket.send(p);
					byte[] buffer = new byte[256];
					DatagramPacket recPacket = new DatagramPacket(buffer, buffer.length);
					socket.receive(recPacket);

			String receivedPacket = new String(recPacket.getData(), 0, recPacket.getLength());
			System.out.println(receivedPacket);

				}
			} else if (args[2].equals("lookup")){
				String str = "LOOKUP ".concat(args[3]);
				System.out.println("Client "+str);
				byte[] msg = str.getBytes();

				DatagramPacket p = new DatagramPacket(msg, msg.length, address, Integer.valueOf(args[1]));
				socket.send(p);

				byte[] buffer = new byte[256];
				DatagramPacket recPacket = new DatagramPacket(buffer, buffer.length);
				socket.receive(recPacket);

			String receivedPacket = new String(recPacket.getData(), 0, recPacket.getLength());
			System.out.println(receivedPacket);

			}


		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	private static void createMessage(String data[]) {

		if( data[2].equals("register")) {
			registerList = new ArrayList<String[]>();

			for ( int i = 3; i < data.length; i++) {
				String[] pair = new String[2];
				pair[0] = data[i];
				i++;
				pair[1] = data [i];
				registerList.add(pair);
			}	
		} else if (data[2].equals("lookup")) {
			lookupList = new String[data.length - 3];
			int i = 0;
			while(i< lookupList.length) {
				lookupList[i] = data[i+3];
				i++;
			}
		}
	}

	private static boolean checkInput(String args[]) {
		int length = args.length;
		if (length > 4) {
			if(args[2].equals("register")) {
				return true;
			} else if (args[2].equals("lookup")) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	private static void usage() {
		System.out.println("FAIL!");
	}

}
