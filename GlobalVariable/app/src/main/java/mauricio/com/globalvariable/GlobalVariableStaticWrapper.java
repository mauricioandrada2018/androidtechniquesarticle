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
