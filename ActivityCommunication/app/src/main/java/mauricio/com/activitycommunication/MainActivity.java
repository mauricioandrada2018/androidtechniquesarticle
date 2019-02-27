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
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements Handler.Callback {

    private Messenger messenger;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler(this);
        messenger = new Messenger(handler);

    }


    public void onClick(View v) {

        Intent intent = new Intent();
        intent.putExtra("messenger",messenger);
        intent.setClass(getApplicationContext(), Main2Activity.class);
        startActivity(intent);

    }

    @Override
    public boolean handleMessage(Message message) {

        String someText = (String) message.obj;

        ((TextView) findViewById(R.id.display)).setText(someText);

        Log.v("CommApp","handler someText = "+someText);

        //here just to bring the activity to the front
        Intent intent = new Intent();
        intent.setClass(this,this.getClass());

        startActivity(intent);


        return true;
    }
}
