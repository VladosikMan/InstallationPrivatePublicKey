package vladgad;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Generator implements Runnable {
    //class for generation variant task
    // 1 - Id task
    // 2 - Name cert
    // 3 - Key data
    // 4 - Mode ecnryption
    // 5 - Scheme supllement
    // 6? - Question for check knowledge student

    private Thread generatorThread;
    private ArrayList<Task> tasks = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> data = new ArrayList<>();
    private ArrayList<String> que = new ArrayList<>();
    private ArrayList<String> que2 = new ArrayList<>();
    private ArrayList<String> que3 = new ArrayList<>();
    private CallBack callBack;


    public interface CallBack {
        void generatorCallBack(CallBackNotifications callBackNotifications, Object obj);
    }

    public void registerCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public void initialization() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                names = getInformationFromFile(Path.PATH_NAMES);
                data = getInformationFromFile(Path.PATH_DATA);
                que = getInformationFromFile(Path.PATH_QUE_1);
                que2 = getInformationFromFile(Path.PATH_QUE_2);
                que3 = getInformationFromFile(Path.PATH_QUE_3);
                callBack.generatorCallBack(CallBackNotifications.FinishInitData, null);
            }
        }).start();
    }

    public void generatetask() {
        generatorThread = new Thread(this);
        generatorThread.start();
    }

    private ArrayList<String> getInformationFromFile(String path) {

        ArrayList<String> data = new ArrayList<>();
        try {
            BufferedReader reader = null;
            reader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = reader.readLine()) != null) {
                data.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;

    }

    private String createIdTask(String name) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        return name + "_" + timeStamp;
    }

    private String createNameCert() {
        //name from file
        return names.get(0 + (int) (Math.random() * names.size()));
    }

    private String createKeyData() {
        //data from file
        return data.get(0 + (int) (Math.random() * data.size()));
    }

    private String createProvide() {
        Providers[] providers = Providers.values();
        return providers[0 + (int) (Math.random() * Providers.values().length)].getUrl();
    }

    private String createQuestion() {
        return que.get(0 + (int) (Math.random() * que.size())) + "\n" + que2.get(0 + (int) (Math.random() * que2.size())) +
                "\n" + que3.get(0 + (int) (Math.random() * que3.size()));
    }

    private ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }


    @Override
    public void run() {
        Task task = new Task();
        String name = createNameCert();
        task.setName(name);
        System.out.println(name);
        callBack.generatorCallBack(CallBackNotifications.CreateNameTask, name);

        String id = createIdTask(task.getName());
        task.setId(createIdTask(id));

        String data = createKeyData();
        task.setDataKey(data);
        callBack.generatorCallBack(CallBackNotifications.CreateDataTask, data);


        String provider = createProvide();
        task.setProvider(provider);
        callBack.generatorCallBack(CallBackNotifications.CreateProviderTask, provider);

        String que = createQuestion();
        task.setQuestion(que);
    }
}