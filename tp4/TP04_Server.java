/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */




/**
 *
 * @author ei05031
 */
public class TP04_Server implements Tp04 {

	public TP04_Server() {}

	public static void main(String[] args) {

		try {
			TP04_Server server = new TP04_Server();

			Tp04 stub = (Tp04) UnicastRemoteObject.exportObject(server, 0);

			// Binds the remote object's stub in the regitry
			Registry registry = LocateRegistry.getRegistry();
			registry.bind("Tp04", stub);



		} catch (Exception e){
			e.printStackTrace();

		}

	}

}
