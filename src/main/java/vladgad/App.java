package vladgad;

public class App implements Generator.CallBack{

    private Callback callBack;
    private Generator generator;


    public App() {
        generator = new Generator();
        generator.registerCallBack(this);
        generator.initialization();
    }

    public void generate(){
        generator.generatetask();
    }
    public interface Callback {
        void appCallback(CallBackNotifications callBackNotifications, Object obj);
    }

    public void registerCallBack(Callback callBack) {
        this.callBack = callBack;
    }

    @Override
    public void generatorCallBack(CallBackNotifications callBackNotifications, Object obj) {
        callBack.appCallback(callBackNotifications, obj);
    }
}
