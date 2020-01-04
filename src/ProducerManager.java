import java.io.PrintWriter;

public class ProducerManager  extends Manager{

    public ProducerManager(Tampon tampon, int portNumber) {
        this.tampon = tampon;
        this.portNumber = portNumber;
    }

    @Override
    boolean canDoProcess() {
        return !tampon.isFull();
    }

    @Override
    String process(String message, PrintWriter socketBufferedReader) {
        tampon.produce(message);
        socketBufferedReader.println(FIN);
        return FIN;
    }


}
