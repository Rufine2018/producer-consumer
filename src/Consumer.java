import java.io.*;
import java.net.*;

public class Consumer {
	public static final String SYN = "SYN";
	public static final String ACK = "ACK";
	public static final String CONSUME = "CONSUME";

	public static void main (String[] args) {
		int port= 4030;
		InetAddress hote=null;
		Socket sc = null;
		String name = "";
		BufferedReader in = null;
		PrintWriter out = null;
		try{
			if (args.length>=3){
				hote= InetAddress.getByName(args[0]);
				port= Integer.parseInt(args[1]);
				name = args[2];

			}
			else{
				hote = InetAddress.getLocalHost();
			}
		}
		catch(UnknownHostException e){
			System.err.println("Machine inconnue :" +e);
		}
		try{
			sc = new Socket(hote,port);
			in = new BufferedReader(new InputStreamReader(sc.getInputStream()));
			out = new PrintWriter(sc.getOutputStream(),true);

			String str = name;//"consommateur"+ name +" est Prét à consommer";
			out.println(SYN);
			System.out.println("Le consommateur "+ name +"a envoyé: "+ SYN);
			String responseMsg = in.readLine();
			if (ACK.equalsIgnoreCase(responseMsg)) {
				System.out.println("Sending consume request...");
				out.println(CONSUME);
				responseMsg = in.readLine();
				System.out.println("Consume response: "+responseMsg);
			} else {
				System.out.println("Le consommateur "+ name + "a reçu "+ responseMsg);
				System.out.println("Le consommateur ne peut consommer.");
			}
		}
		catch(IOException e){
			System.err.println("Impossible de creer la socket du consommateur : " +e);
		}
		finally{
			try{
				sc.close();
				out.close();
				in.close();
			}
			catch (IOException e){}

		}
	}

}
