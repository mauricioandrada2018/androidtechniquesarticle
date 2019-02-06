package mauricio.com.globalvariable;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;

public class GlobalVariableContentProvider extends ContentProvider {
    private SharedPreferences sharedPrefs;

    @Override
    public boolean onCreate() {

        sharedPrefs = getContext().getSharedPreferences("globalVar",Context.MODE_PRIVATE);

        return true;
    }

    
    @Nullable
    @Override
    public Cursor query( @NonNull Uri uri,  @Nullable String[] strings,  @Nullable String s,  @Nullable String[] strings1,  @Nullable String s1) {

        MatrixCursor cursor = null;

        if (sharedPrefs != null) {

            String[] keys = sharedPrefs.getAll().keySet().toArray(new String[]{});
            cursor = new MatrixCursor(keys);
            String[] values = new String[keys.length];

            for (int i = 0; i < keys.length; i++) {

                values[i] = sharedPrefs.getString(keys[i],null);
            }

            cursor.addRow(values);
        }

        return cursor;
    }

    
    @Nullable
    @Override
    public String getType( @NonNull Uri uri) {
        return null;
    }

    
    @Nullable
    @Override
    public Uri insert( @NonNull Uri uri,  @Nullable ContentValues contentValues) {

        if (sharedPrefs != null &&
                contentValues != null) {

            SharedPreferences.Editor editor = sharedPrefs.edit();

            String[] keys = contentValues.keySet().toArray(new String[]{});

            for (String key:keys) {

                editor.putString(key,contentValues.getAsString(key));
            }

            editor.apply();

        }

        return uri;
    }

    @Override
    public int delete( @NonNull Uri uri,  @Nullable String s,  @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update( @NonNull Uri uri,  @Nullable ContentValues contentValues,  @Nullable String s,  @Nullable String[] strings) {
        return 0;
    }
}
