import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public  class Tampon {
	private final int max;
    private BlockingQueue<String> blockingQueue;
	private boolean poisonPill = false;

    public boolean isPoisonPill() {
        return poisonPill;
    }

    public void setPoisonPill(boolean poisonPill) {
        this.poisonPill = poisonPill;
    }

    public Tampon(int max){
        this.max = max;
        blockingQueue = new ArrayBlockingQueue<>(max);
    }

    boolean isFull () {
        return blockingQueue.remainingCapacity() == 0;
    }

    boolean isEmpty () {
        return blockingQueue.isEmpty();
    }

    void produce (String data) {
        blockingQueue.add(data);
    }

    String consume () throws InterruptedException {
        return blockingQueue.poll(2000, TimeUnit.MILLISECONDS);
    }
}
