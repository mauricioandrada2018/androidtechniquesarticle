package mauricio.com.decoupledapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayDeque;

public class MyService2 extends Service implements EventListener {

    private ControllerInterface controllerInterface;
    private ArrayDeque<Event> eventArrayDeque;
    private WorkerThread workerThread;

    public MyService2() {

        eventArrayDeque = new ArrayDeque<>();

    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (controllerInterface == null) {

            controllerInterface = new ControllerInterface(this);
            controllerInterface.registerComponent(this);

        }

        Event event = intent.getParcelableExtra("event");
        onEventReceived(event);

        return START_STICKY;
    }

    @Override
    public void onEventReceived(Event event) {

        eventArrayDeque.offer(event);

    }

    @Override
    public void onControllerConnected() {

        if (workerThread == null) {
            workerThread = new WorkerThread();
            workerThread.start();
        }
    }

    private class WorkerThread extends Thread {

        private boolean running;

        public void run() {

            running = true;

            while (running) {

                Event inEvent = eventArrayDeque.poll();

                if (inEvent != null) {
                    JSONObject content = inEvent.getContent();
                    try {
                        content.put("display",content.get("input"));
                        Event outEvent = new Event("OP6",content);
                        controllerInterface.sendEvent(outEvent);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public void setRunning(boolean running) {
            this.running = running;
        }
    }
}
