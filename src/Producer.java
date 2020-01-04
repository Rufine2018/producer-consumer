import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Producer {

	public static final String SYN = "SYN";
	public static final String ACK = "ACK";
	public static final String FIN = "FIN";
	public static void main (String[] args) {

		int port= 4030;
		InetAddress hote=null;
		Socket sc=null;
		String name = null;
		BufferedReader in;
		PrintWriter out;
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
		    
		    String str = name; //"le producteur"+ name +" est Prét à produire";
		    out.println(SYN);// synchronize request
		    System.out.println("Le producteur "+ name +"a envoyé : " +SYN);
		    String rep = in.readLine();
		    System.out.println("Le producteur "+ name + "a reçu "+ rep);
		    if(rep.equalsIgnoreCase(ACK)) {
		    	//int mess = (int) (Math.random() *100 + 1);
		    	out.println(str);
		    	System.out.println("Le producteur "+ name + " a produit: "+ name);
		    	rep = in.readLine();
		    	if (rep.equalsIgnoreCase(FIN)) {
					System.out.println("Message produced successfully");
				}
		    }else {
		    	System.out.println("Le producteur "+ name + " ne peut produire");
		    }
		    
		}
		catch(IOException e){
		    System.err.println("Impossible de creer la socket du producteur : " +e);
		}
		finally{
		    try{
			sc.close();
		    }
		    catch (IOException e){}

         }
}
}