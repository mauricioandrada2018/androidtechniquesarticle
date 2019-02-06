package mauricio.com.globalvariable;

import android.content.Context;
import android.os.Bundle;

public interface GlobalVariableWrapper {

    void setValues(Bundle values);
    Bundle getValues();
}
