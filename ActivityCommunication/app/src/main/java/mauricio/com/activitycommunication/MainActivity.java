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
