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

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity2 extends AppCompatActivity implements EventListener {

    private ControllerInterface controllerInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        controllerInterface = new ControllerInterface(this);
        controllerInterface.registerComponent(this);

        Event event = getIntent().getParcelableExtra("event");

        if (event != null) {

            JSONObject content = event.getContent();

            try {
                ((TextView) findViewById(R.id.display)).setText(content.getString("display"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void onClick(View v) {

        Log.v("MainActivity2","onClick()");

        JSONObject content = new JSONObject();

        try {

            content.put("input",((EditText) findViewById(R.id.input)).getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Event event = new Event("OP2",content);
        controllerInterface.sendEvent(event);

    }

    @Override
    public void onEventReceived(Event event) {

        Intent intent = new Intent();
        intent.setClass(this,this.getClass());
        intent.putExtra("event",event);

        //Doing this so I can bring the screen back to foreground
        startActivity(intent);
    }

    @Override
    public void onControllerConnected() {

        enableUiComponents();

    }

    private void enableUiComponents() {

        findViewById(R.id.display).setEnabled(true);
        findViewById(R.id.op).setEnabled(true);
        findViewById(R.id.input).setEnabled(true);
    }
}
