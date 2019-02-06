package mauricio.com.callback;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class MyService extends Service {
    private IMyAidlListener serviceListener;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {

        return new MyBinder();
    }

    private class MyBinder extends IMyAidlInterface.Stub {

        @Override
        public void registerListener(IMyAidlListener listener) throws RemoteException {

            serviceListener = listener;
        }

        @Override
        public void getSomeData() throws RemoteException {

            serviceListener.setSomeData("Here is the data");

        }
    }
}
