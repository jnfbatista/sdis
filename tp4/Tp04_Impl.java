/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tp04;

import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author ei05031
 */
public class Tp04_Impl extends UnicastRemoteObject implements Tp04 {

    public Tp04_Impl() throws RemoteException {

    }

    public String lookup(String str) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String register(String str) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }




}
