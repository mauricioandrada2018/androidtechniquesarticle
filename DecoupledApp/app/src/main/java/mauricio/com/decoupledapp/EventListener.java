package mauricio.com.decoupledapp;

import org.json.JSONObject;

public interface EventListener {

    void onEventReceived(Event event);
    void onControllerConnected();

}
