import java.io.PrintWriter;

public class ConsumerManager extends Manager {

    public ConsumerManager(Tampon tampon, int consumerPort) {
        this.tampon = tampon;
        this.portNumber = consumerPort;
    }

    @Override
    boolean canDoProcess() {
        return !tampon.isEmpty();
    }

    @Override
    String process(String message, PrintWriter socketBufferedReader) throws InterruptedException {
        String data = tampon.consume();
        socketBufferedReader.println(data);
        return data;
    }
}
