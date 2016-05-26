package gr.nrallakis.tichu.server;

import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.minlog.Log;

import org.junit.After;
import org.junit.Before;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

abstract public class NetworkingTestCase {
    static public String host = "192.168.1.150";
    static public int tcpPort = 44444;

    private ArrayList<Thread> threads = new ArrayList<>();
    ArrayList<EndPoint> endPoints = new ArrayList<>();
    private Timer timer;
    boolean fail;

    public NetworkingTestCase() {
        // Log.set(Log.LEVEL_TRACE);
        Log.set(Log.LEVEL_DEBUG);
    }

    @Before
    public void setUp() throws Exception {
        System.out.println("---- " + getClass().getSimpleName());
        timer = new Timer();
    }

    @After
    public void tearDown() throws Exception {
        timer.cancel();
    }

    public void startEndPoint(EndPoint endPoint) {
        endPoints.add(endPoint);
        Thread thread = new Thread(endPoint);
        threads.add(thread);
        thread.start();
    }

    public void stopEndPoints() {
        stopEndPoints(0);
    }

    public void stopEndPoints(int stopAfterMillis) {
        timer.schedule(new TimerTask() {
            public void run() {
                for (EndPoint endPoint : endPoints)
                    endPoint.stop();
                endPoints.clear();
            }
        }, stopAfterMillis);
    }

    public void waitForThreads(int stopAfterMillis) {
        if (stopAfterMillis > 10000)
            throw new IllegalArgumentException("stopAfterMillis must be < 10000");
        stopEndPoints(stopAfterMillis);
        waitForThreads();
    }

    public void waitForThreads() {
        fail = false;
        TimerTask failTask = new TimerTask() {
            public void run() {
                stopEndPoints();
                fail = true;
            }
        };
        timer.schedule(failTask, 11000);
        while (true) {
            for (Iterator iter = threads.iterator(); iter.hasNext(); ) {
                Thread thread = (Thread) iter.next();
                if (!thread.isAlive()) iter.remove();
            }
            if (threads.isEmpty()) break;
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {
            }
        }
        failTask.cancel();
        if (fail) System.out.println("Test did not complete in a timely manner.");
        // Give sockets a chance to close before starting the next test.
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }
}
