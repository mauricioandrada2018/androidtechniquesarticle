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
