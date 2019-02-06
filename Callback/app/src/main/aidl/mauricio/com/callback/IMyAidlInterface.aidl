// IMyAidlInterface.aidl
package mauricio.com.callback;

// Declare any non-default types here with import statements
import mauricio.com.callback.IMyAidlListener;

interface IMyAidlInterface {

    void registerListener(IMyAidlListener listener);
    void getSomeData();
}
