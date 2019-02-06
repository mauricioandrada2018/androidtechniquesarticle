package mauricio.com.decoupledapp;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class Event implements Parcelable {

    private String name;
    private JSONObject content;


    Event(@NonNull String name, @NonNull JSONObject content) {
        this.name = name;
        this.content = content;
    }

    private Event(Parcel in) {

        name = in.readString();
        try {
            content = new JSONObject(in.readString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    String getName() {
        return name;
    }

    JSONObject getContent() {
        return content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(name);
        parcel.writeString(content.toString());
    }
}
