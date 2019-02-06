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
