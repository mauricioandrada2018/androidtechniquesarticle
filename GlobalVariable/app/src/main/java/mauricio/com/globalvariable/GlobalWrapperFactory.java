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
