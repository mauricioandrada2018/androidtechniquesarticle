package mauricio.com.decoupledapp;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import java.util.HashMap;

public class Controller extends Service implements Handler.Callback {

    private Messenger controllerMessenger;
    private HashMap<String,Messenger> messengerHashMap;
    private HashMap<String,String> eventToComponentmap;

    public Controller() {

        Handler controllerHandler = new Handler(this);
        controllerMessenger = new Messenger(controllerHandler);
        messengerHashMap = new HashMap<>();

        populateEventToComponent();

    }

    private void populateEventToComponent() {

        eventToComponentmap = new HashMap<>();

        eventToComponentmap.put("OP1",MyService.class.getName());
        eventToComponentmap.put("OP2",MyService2.class.getName());
        eventToComponentmap.put("OP3",MyService3.class.getName());

        eventToComponentmap.put("OP4",MainActivity.class.getName());
        eventToComponentmap.put("OP5",MainActivity2.class.getName());
        eventToComponentmap.put("OP6",MainActivity3.class.getName());

    }

    @Override
    public IBinder onBind(Intent intent) {

        return controllerMessenger.getBinder();
    }

    @Override
    public boolean handleMessage(Message message) {


        Messenger messenger;

        switch (message.arg1) {

            case 0: //component registration

                Bundle bundle = (Bundle) message.obj;

                String componentName = bundle.getString("componentName");
                messenger = bundle.getParcelable("messenger");
                messengerHashMap.put(componentName,messenger);

                break;

            case 1: //event received

                Event event = (Event) message.obj;

                String eventName = event.getName();

                String dest = eventToComponentmap.get(eventName);


                if (dest != null) {

                    messenger = messengerHashMap.get(dest);

                    if (messenger ==  null) {

                        Intent intent = new Intent();
                        intent.setClassName(getPackageName(),dest);
                        intent.putExtra("event",event);

                        if (dest.contains("Service")) {

                            startService(intent);

                        } else {

                            startActivity(intent);

                        }

                    } else {

                        try {

                            Message newMessage = new Message();
                            newMessage.obj = event;
                            messenger.send(newMessage);

                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }

                break;
        }

        return true;
    }
}
