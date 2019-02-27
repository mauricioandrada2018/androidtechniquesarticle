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

package mauricio.com.activitycommunication;

import android.content.Intent;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class Main2Activity extends AppCompatActivity {

    private Messenger remoteMessenger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        remoteMessenger = getIntent().getParcelableExtra("messenger");

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        remoteMessenger = intent.getParcelableExtra("messenger");

    }

    public void onClick(View v) {

        String text = ((EditText) findViewById(R.id.input)).getText().toString();

        Log.v("CommApp","input = "+text);


        Message message = new Message();
        message.obj = text;

        try {
            remoteMessenger.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
}
