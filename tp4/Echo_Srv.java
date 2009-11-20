import java.rmi.*;

public class Echo_Srv {

    public static void main(String[] args) {
	if(args.length != 1) {
	    System.out.println("Usage: java Echo_Srv object_name");
	    return;
	}
	try {
	    // First create remote object
	    Echo echo = new Echo_Impl();
	    // Now register the object in the rmiregistry
	    System.out.println("Binding object to name " + args[0]);
	    Naming.rebind(args[0], echo);
	    System.out.println("Echo object " + args[0] + " ready");
	    // Why doesn't the program end?
	} catch (Exception e) {
	    System.out.println("Echo_Srv exception: " + e.getMessage());
	    e.printStackTrace();
	}
    }
}
