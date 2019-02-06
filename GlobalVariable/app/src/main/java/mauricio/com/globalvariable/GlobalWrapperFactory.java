package mauricio.com.globalvariable;

import android.content.Context;

public class GlobalWrapperFactory {

    public static final String CP_WRAPPER = "cp_type";
    public static final String SERVICE_WRAPPER = "serv_wrapper";

    public GlobalVariableWrapper createWrapper(Context context, String type) {

        if (type.equals(CP_WRAPPER)) {

            return new GlobalVariableCPWrapper(context);

        } else if (type.equals(SERVICE_WRAPPER)) {

            return new GlobalVariableServiceWrapper(context);

        }

        return new GlobalVariableStaticWrapper();
    }
}
