/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.rmi.Remote;
import java.rmi.RemoteException;



/**
 *
 * @author ei05031
 */
public interface Tp04 extends Remote {
    String lookup(String str) throws RemoteException;
    String register(String str) throws RemoteException;

}
