package mauricio.com.globalvariable;

import android.os.Bundle;
import android.util.Log;

public class GlobalVariableStaticWrapper implements GlobalVariableWrapper {

    private final static Bundle[] globalValues = new Bundle[1];

    @Override
    public void setValues(Bundle values) {

        synchronized (globalValues) {

            globalValues[0] = new Bundle(values);
        }


    }

    @Override
    public Bundle getValues() {

        synchronized (globalValues) {

            return globalValues[0];
        }
    }
}
