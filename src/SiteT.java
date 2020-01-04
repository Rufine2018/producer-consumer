import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SiteT {
	
	public static void main(String[] args) {
		int consumerPort = 4030,
			producerPort = 4031;
		try{
		    int max = Integer.parseInt(args[0]);
		    Tampon tamp = new Tampon(max);
			ExecutorService executorService = Executors.newFixedThreadPool(2);
			executorService.execute(() -> new ProducerManager(tamp, producerPort).manage());
			executorService.execute(() -> new ConsumerManager(tamp, consumerPort).manage());
		} catch (Exception e) {
		    System.err.println("Error : " +e);
		}
	}
}
