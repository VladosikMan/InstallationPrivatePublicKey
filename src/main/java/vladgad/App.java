package vladgad;

import java.io.File;
import java.util.ArrayList;

public class App implements Generator.CallBack, CheckAnswer.CallBack, Storage.CallBack {

    private Callback callBack;
    private Generator generator;
    private CheckAnswer checkAnswer;
    private Storage storage;


    public App() {
        generator = new Generator();
        generator.registerCallBack(this);
        generator.initialization();

        checkAnswer = new CheckAnswer();
        checkAnswer.registerCallBack(this);
        storage = new Storage();
        storage.registerCallBack(this);

    }

    public void initTasks() {
        storage.initTasks();
    }

    public void generate() {
        generator.generatetask();
    }

    public void checkAnswer(String taskId, String encrypt) {
//        //получитить задачу и закрытый ключ
//        System.out.println(taskId);
//        System.out.println(encrypt);
        Task task = storage.getTask(taskId);
        checkAnswer.check(task, encrypt);
    }

    public void createQuestions() {
        generator.createQuestion();
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

    @Override
    public void checkAnswerCallBack(CallBackNotifications callBackNotifications, Object obj) {
        callBack.appCallback(callBackNotifications, obj);
    }

    @Override
    public void storageCallBack(CallBackNotifications callBackNotifications, Object obj) {
        switch (callBackNotifications) {
            case InitOpenTasks: {
                ArrayList<Task> tasks = (ArrayList<Task>) obj;
                String[] idTasks = new String[tasks.size()];
                for (int i = 0; i < tasks.size(); i++) {
                    idTasks[i] = tasks.get(i).getId();
                }
                callBack.appCallback(callBackNotifications, idTasks);
                break;
            }
            default: {
                callBack.appCallback(callBackNotifications, obj);
                break;
            }
        }

    }
}
