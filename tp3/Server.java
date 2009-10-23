import java.net.ServerSocket;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;

public class Server {
	public static void main(String args[]) {
		try {
			ServerSocket socket = new ServerSocket(args[0]);

			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream));

			








		} catch (Exception e) {
			e.printStackTrace();

		}
	}

}
