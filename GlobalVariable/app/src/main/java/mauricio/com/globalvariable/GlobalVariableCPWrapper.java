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
