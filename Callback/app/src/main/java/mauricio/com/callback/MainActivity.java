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

package mauricio.com.callback;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ServiceConnection, Handler.Callback {

    private IMyAidlInterface service;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler(this);
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(),MyService.class);
        bindService(intent,this,BIND_AUTO_CREATE);
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

        service = IMyAidlInterface.Stub.asInterface(iBinder);
        try {

            service.registerListener(new MyListener());
            service.getSomeData();

        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {

    }

    @Override
    public boolean handleMessage(Message message) {

        Toast.makeText(this, (CharSequence) message.obj, Toast.LENGTH_SHORT).show();
        return true;
    }

    private class MyListener extends IMyAidlListener.Stub {


        @Override
        public void setSomeData(String data) throws RemoteException {

            Message message = handler.obtainMessage();
            message.obj = data;
            message.sendToTarget();
        }
    }
}
