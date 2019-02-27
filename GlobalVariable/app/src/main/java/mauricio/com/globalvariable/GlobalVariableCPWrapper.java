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

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import java.util.Set;

public class GlobalVariableCPWrapper implements GlobalVariableWrapper {

    private final ContentResolver contentResolver;
    private Context context;

    public GlobalVariableCPWrapper(Context context) {
        this.context = context;

        contentResolver = context.getContentResolver();
    }

    @Override
    public void setValues(Bundle values) {

        ContentValues contentValues = new ContentValues();

        if (values != null) {

            Set<String> keySet = values.keySet();

            for (String key:keySet) {

                contentValues.put(key,values.getString(key));
            }

            contentResolver.insert(Uri.parse("content://globalwrapper"),contentValues);
        }

    }

    @Override
    public Bundle getValues() {

        Cursor cursor = contentResolver.query(Uri.parse("content://globalwrapper"),null,null,null,null);
        Bundle bundle = new Bundle();

        if (cursor != null) {

            int columnCount = cursor.getColumnCount();

            if (cursor.moveToFirst()) {

                while (!cursor.isAfterLast()) {

                    for (int i = 0; i < columnCount; i++) {

                        String key = cursor.getColumnName(i);
                        String value = cursor.getString(i);

                        bundle.putString(key,value);
                    }

                    cursor.moveToNext();
                }
            }

            cursor.close();
        }

        return bundle;
    }
}
