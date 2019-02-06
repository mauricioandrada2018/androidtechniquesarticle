package mauricio.com.decoupledapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class ControllerInterface implements ServiceConnection, Handler.Callback {

    private Context context;
    private EventListener listener;
    private Messenger controllerMessenger; //provided by the Controller as part of binding
    private Messenger localMessgenger; //provided to the Controller for receiving messages

    ControllerInterface(@NonNull Context context) {
        this.context = context;
    }

    void registerComponent(@NonNull EventListener listener) {

        this.listener = listener;
        Handler handler = new Handler(this);
        localMessgenger = new Messenger(handler);
        Intent intent = new Intent();
        intent.setClass(context.getApplicationContext(), Controller.class);
        context.bindService(intent, this, Context.BIND_AUTO_CREATE);

    }

    void sendEvent(@NonNull Event event) {

        Message message = new Message();
        message.arg1 = 1;
        message.obj = event;

        try {
            controllerMessenger.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

        controllerMessenger = new Messenger(iBinder);
        Bundle bundle = new Bundle();
        bundle.putString("componentName", context.getClass().getName());
        bundle.putParcelable("messenger", localMessgenger);
        Message message = new Message();
        message.arg1 = 0;
        message.obj = bundle;

        try {
            controllerMessenger.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        listener.onControllerConnected();
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {

    }

    @Override
    public boolean handleMessage(Message message) {

        Event event = (Event) message.obj;

        listener.onEventReceived(event);

        return true;
    }
}
