package mauricio.com.decoupledapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity3 extends AppCompatActivity implements EventListener {

    private ControllerInterface controllerInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

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

        JSONObject content = new JSONObject();
        try {

            content.put("input",((EditText) findViewById(R.id.input)).getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Event event = new Event("OP3",content);
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
