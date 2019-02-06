package mauricio.com.globalvariable;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

public class GlobalVariableService extends Service implements Handler.Callback {

    private Bundle values;
    private Messenger localMessenger;
    private Handler handler;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Messenger remoteMessenger = intent.getParcelableExtra("messenger");
        handler = new Handler(this);
        localMessenger = new Messenger(handler);
        Message message = new Message();
        message.obj = localMessenger;
        message.arg1 = 0;
        try {
            remoteMessenger.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return START_STICKY;
    }

    @Override
    public boolean handleMessage(Message message) {


        switch (message.arg1) {

            case 2:

                Messenger remoteMessenger = (Messenger) message.obj;
                Message response = new Message();
                response.arg1 = 1;
                response.obj = values;
                try {
                    remoteMessenger.send(response);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                break;

            case 3:

                values = (Bundle) message.obj;

                break;
        }

        return true;
    }
}
