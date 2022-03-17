package vladgad;

import vladgad.GUI.CheckAnswerGUI;
import vladgad.GUI.GenerationGUI;
import java.util.ArrayList;

public class App implements Generator.CallBack, CheckAnswer.CallBack, Storage.CallBack {

    private CallbackGenerate callbackGenerate;
    private CallbackCheckAnswer callbackCheckAnswer;
    private Generator generator;
    private CheckAnswer checkAnswer;
    private Storage storage;
    private GenerationGUI generationGUI;
    private CheckAnswerGUI checkAnswerGUI;



    public App() {


        generator = new Generator();
        generator.registerCallBack(this);
        initData();


        checkAnswer = new CheckAnswer();
        checkAnswer.registerCallBack(this);

        storage = new Storage();
        storage.registerCallBack(this);


        Images.init();

    }
    public void startGUI(){
        generationGUI = new GenerationGUI();
        generationGUI.createGUI(this);
        generationGUI.setVisible(true);


        checkAnswerGUI = new CheckAnswerGUI();
        checkAnswerGUI.createGUI(this);


    }
    public void startGenerationGUI(){
        generationGUI.setVisible(true);
        checkAnswerGUI.setVisible(false);
    }
    public void startCheckAnswerGUI(){
        generationGUI.setVisible(false);
        checkAnswerGUI.setVisible(true);
    }

    public void initData(){
        generator.initialization();
    }
    public void updateTask() {
        storage.updateTask();
    }

    public void generate() {
        generator.generatetask();
    }
    public void generateManyVariants(int size){
        generator.generateManyVariant(size);
    }

    public void checkAnswer(String taskId, String encrypt) {
//        //получитить задачу и закрытый ключ
        Task task = storage.getTask(taskId);
        checkAnswer.check(task, encrypt);
    }

    public void createQuestions() {
        generator.createQuestion();
    }



    public interface CallbackGenerate {
        void appCallbackGenerate(CallBackNotifications callBackNotifications, Object obj);
    }


    public void registerCallBackGenerate(CallbackGenerate callbackGenerate) {
        this.callbackGenerate = callbackGenerate;
    }


    public interface CallbackCheckAnswer {
        void appCallbackCheckAnswer(CallBackNotifications callBackNotifications, Object obj);
    }


    public void registerCallBackCheckAnswer(CallbackCheckAnswer callbackCheckAnswer) {
        this.callbackCheckAnswer = callbackCheckAnswer;
    }




    @Override
    public void generatorCallBack(CallBackNotifications callBackNotifications, Object obj) {
        switch (callBackNotifications){
            case CreateQuestions:{
                callbackCheckAnswer.appCallbackCheckAnswer(callBackNotifications, obj);
                break;
            }
            default:{
                callbackGenerate.appCallbackGenerate(callBackNotifications, obj);
                break;
            }
        }
    }



    @Override
    public void checkAnswerCallBack(CallBackNotifications callBackNotifications, Object obj) {
        switch (callBackNotifications){
            case DeleteTask:{
                storage.deleteFiles(obj.toString());
                break;
            }
            case DeleteVariant:{
                storage.deleteVariant(obj.toString());
                break;
            }

            default:{
                callbackCheckAnswer.appCallbackCheckAnswer(callBackNotifications, obj);
                break;
            }
        }

    }

    @Override
    public void storageCallBack(CallBackNotifications callBackNotifications, Object obj) {
        switch (callBackNotifications) {
            case UpdateOpenTasks: {
                ArrayList<Task> tasks = (ArrayList<Task>) obj;
                String[] idTasks = new String[tasks.size()];
                for (int i = 0; i < tasks.size(); i++) {
                    idTasks[i] = tasks.get(i).getId();
                }
                callbackCheckAnswer.appCallbackCheckAnswer(callBackNotifications, idTasks);
                break;
            }
            default: {
                callbackCheckAnswer.appCallbackCheckAnswer(callBackNotifications, obj);
                break;
            }
        }

    }
}
