package mauricio.com.globalvariable;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

public class GlobalVariableServiceWrapper implements GlobalVariableWrapper {

    private Context context;
    private Messenger localMessenger, remoteMessenger;
    private Handler handler;
    private SetupThread setupThread;
    private Bundle globalVars;
    private final Object lock = new Object();

    public GlobalVariableServiceWrapper(Context context) {

        this.context = context;

    }

    @Override
    public void setValues(Bundle values) {

        initializeService();

        if (remoteMessenger != null) {

            Message message = new Message();
            message.obj = values;

            message.arg1 = 3;

            try {
                remoteMessenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Bundle getValues() {

        initializeService();

        if (remoteMessenger != null) {

            Message message = new Message();
            message.arg1 = 2;
            message.obj = localMessenger;

            try {
                remoteMessenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        synchronized (lock) {

            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return globalVars;
    }

    private void initializeService() {

        if (setupThread == null) {

            setupThread = new SetupThread();
            setupThread.start();

            synchronized (lock) {

                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class SetupThread extends Thread implements Handler.Callback {


        public void run() {

            Looper.prepare();

            handler = new Handler(this);
            localMessenger = new Messenger(handler);

            Intent intent = new Intent();
            intent.setClass(context.getApplicationContext(),GlobalVariableService.class);
            intent.putExtra("messenger",localMessenger);
            context.startService(intent);

            Looper.loop();

            synchronized (this) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public boolean handleMessage(Message message) {

            switch (message.arg1) {

                case 0:

                    remoteMessenger = (Messenger) message.obj;

                    break;

                case 1:

                    globalVars = (Bundle) message.obj;

                    break;

            }

            synchronized (lock) {

                lock.notifyAll();
            }

            return true;
        }
    }
}
