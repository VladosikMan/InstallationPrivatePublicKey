import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class Generator implements Runnable{
    //class for generation variant task
    // 1 - Id task
    // 2 - Name cert
    // 3 - Key data
    // 4 - Mode ecnryption
    // 5 - Scheme supllement
    // 6? - Question for check knowledge student

    private static Thread generatorThread;
    private static ArrayList<Task> tasks = new ArrayList<>();
    private static ArrayList<String> names = new ArrayList<>();
    private static ArrayList<String> data = new ArrayList<>();
    private static ArrayList<String> que = new ArrayList<>();
    private static ArrayList<String> que2 = new ArrayList<>();
    private static ArrayList<String> que3 = new ArrayList<>();
    private static CallBack callBack;


    interface  CallBack{
        void generatorCallBack(CallBackNotifications callBackNotifications, Object obj);
    }

    public void registerCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public static void initialization() {
        names = getInformationFromFile(Path.PATH_NAMES);
        data = getInformationFromFile(Path.PATH_DATA);
        que = getInformationFromFile(Path.PATH_QUE_1);
        que2 = getInformationFromFile(Path.PATH_QUE_2);
        que3 = getInformationFromFile(Path.PATH_QUE_3);
    }

    public static Task generatetask() {
        Task task = new Task();
        task.setName(createNameCert());
        task.setId(createIdTask(task.getName()));
        task.setDataKey(createKeyData());
        task.setProvider(createProvide());
        task.setQuestion(createQuestion());
        return task;
    }

    private static ArrayList<String> getInformationFromFile(String path) {

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

    private static String createIdTask(String name) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        return name + "_" + timeStamp;
    }

    private static String createNameCert() {
        //name from file
        return names.get(0 + (int) (Math.random() * names.size()));
    }

    private static String createKeyData() {
        //data from file
        return data.get(0 + (int) (Math.random() * data.size()));
    }

    private static String createProvide() {
        Providers[] providers = Providers.values();
        return providers[0 + (int) (Math.random() * Providers.values().length)].getUrl();
    }

    private static String createQuestion() {
        return que.get(0 + (int) (Math.random() * que.size())) + "\n" + que2.get(0 + (int) (Math.random() * que2.size())) +
                "\n" + que3.get(0 + (int) (Math.random() * que3.size()));
    }

    private ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        Generator.initialization();
        Task task = Generator.generatetask();
        String que = task.getQuestion();
        System.out.println(que);

        byte bytes[] = que.getBytes("UTF-8");
        String value = new String(bytes, "UTF-8");
        System.out.println(value);
    }

    @Override
    public void run() {

    }
}
