import java.rmi.*;

public class Echo_Clnt {


    static void loop(Echo echo) {
	int i;
	String msg, rsp;

	// Just invoke the echo method once per second.
	for( i = 0; ; i ++) {
	    msg = "Client: sending message " + i;
	    System.out.println("Client: sending message:");
	    System.out.println("\t" + msg);
	    try {
		rsp = echo.echo(msg);
		System.out.println("Client: echo received:");
		System.out.println("\t" + rsp);
		Thread.sleep(1000);
	    } catch (Exception e) {
		System.out.println("Echo_Clnt loop() exception: " 
				   + e.getMessage());
		e.printStackTrace();
	    }
	}
    }

    public static void main(String[] args) {
	if(args.length != 2) {
	    System.out.println("Usage: java Echo_Client <srv_hostname> " 
			       + "<obj_name>");
	    return;
	}
	// Set Security Manager to allow stubs downloading
	System.setSecurityManager(new RMISecurityManager());
	try {
	    String name= "//" + args[0] + "/" + args[1];
	    Echo echo;

	    // Get reference to remote object
	    System.out.println("Lookingup name " + name);
	    echo = (Echo) Naming.lookup(name);
	    System.out.println("Got handle to Echo object");

	    // Execute endless loop
	    loop(echo);
	} catch (Exception e) {
	    System.out.println("Echo_Clnt main() exception: " 
			       + e.getMessage());
	    e.printStackTrace();
	}
    }
}
