/*
 * Copyright (C) 2019 Mauricio Andrada
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package mauricio.com.decoupledapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayDeque;

public class MyService extends Service implements EventListener {

    private ControllerInterface controllerInterface;
    private ArrayDeque<Event> eventArrayDeque;
    private WorkerThread workerThread;

    public MyService() {

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
                        Event outEvent = new Event("OP5",content);
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
