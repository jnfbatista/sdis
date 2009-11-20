import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

public class Echo_Impl extends UnicastRemoteObject
		implements Echo 
{
  public Echo_Impl() throws RemoteException {
  }

  public String echo(String str) {
    return str;
  }
}
