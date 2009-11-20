/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author ei05031
 */
public class Tp04_Impl extends UnicastRemoteObject implements Tp04 {
	public static final NOT_FOUND = "NOT FOUND";
	public static final REGISTERED = "REGISTERED";
	public static final ERROR = "ERROR";

	private HashMap<String, String> dnsMap = new HashMap<String, String>();


	public Tp04_Impl() throws RemoteException {

	}

	public String lookup(String str) throws RemoteException {
		if(dnsMap.hasKey(str)) {
			return dnsMap.get(str);
		} else {
			return NOT_FOUND;
		}
	}

	public String register(String str) throws RemoteException {
		String[] data = str.split(" ");

		if(data.length =! 2) {
			return ERROR;

		} else {
			dnsMap.put(data[0], data[1]);
			return REGISTERED;
		}

	}




}
