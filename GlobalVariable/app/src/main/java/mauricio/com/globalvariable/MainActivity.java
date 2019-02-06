package mauricio.com.globalvariable;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GlobalWrapperFactory globalWrapperFactory = new GlobalWrapperFactory();

        GlobalVariableServiceWrapper serviceWrapper = (GlobalVariableServiceWrapper) globalWrapperFactory.createWrapper(this, GlobalWrapperFactory.SERVICE_WRAPPER);
        GlobalVariableCPWrapper cpWrapper = (GlobalVariableCPWrapper) globalWrapperFactory.createWrapper(this,GlobalWrapperFactory.CP_WRAPPER);
        GlobalVariableStaticWrapper staticWrapper = (GlobalVariableStaticWrapper) globalWrapperFactory.createWrapper(this, "");

        Bundle bundle = new Bundle();
        bundle.putString("str1","val1");
        bundle.putString("str2","");
        bundle.putString("str3","");


        serviceWrapper.setValues(bundle);
        bundle = serviceWrapper.getValues();
        printValues(bundle);

        bundle.putString("str2","val2");
        cpWrapper.setValues(bundle);
        bundle = cpWrapper.getValues();
        printValues(bundle);

        bundle.putString("str3","val3");
        staticWrapper.setValues(bundle);
        bundle = staticWrapper.getValues();
        printValues(bundle);



    }

    private void printValues(Bundle bundle) {

        Log.v("GlobalVariable",bundle.getString("str1") + " " + bundle.getString("str2") + " " + bundle.getString("str3") );
    }
}
