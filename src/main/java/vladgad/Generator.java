package vladgad;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

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
    private Storage storage;


    public interface CallBack {
        void generatorCallBack(CallBackNotifications callBackNotifications, Object obj);
    }

    public void registerCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public void initialization() {
        storage = new Storage();
        new Thread(new Runnable() {
            @Override
            public void run() {
                names = getInformationFromFile(Path.PATH_NAMES);
                data = getInformationFromFile(Path.PATH_DATA);
                que = getInformationFromFile(Path.PATH_QUE_1);
                que2 = getInformationFromFile(Path.PATH_QUE_2);
                que3 = getInformationFromFile(Path.PATH_QUE_3);
                //callBack.generatorCallBack(CallBackNotifications.FinishInitData, null);
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
        //String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        return name + "_" + generateRandomIdString(8);
    }

    private String createNameCert() {
        //name from file
        return names.get(0 + (int) (Math.random() * names.size()));
    }

    private String createNameVariant() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        return generateRandomIdString(5) + timeStamp;
    }

    private String createKeyData() {
        //data from file
        return data.get(0 + (int) (Math.random() * data.size()));
    }

    private String createProvide() {
        Providers[] providers = Providers.values();
        return providers[0 + (int) (Math.random() * Providers.values().length)].getUrl();
    }

    private ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }


    public void generateManyVariant(int size) {
        int percent = 0;
        new Thread(new Runnable() {
            @Override
            public void run() {
                String manyVariants = "Варианты " + size + "\n" + "\n";
                for (int i = 1; i <= size; i++) {

                    Task task = new Task();
                    String name = createNameCert();
                    String variant = createNameVariant();
                    task.setName(variant);
                    String id = createIdTask(name);
                    task.setId(id);
                    String data = createKeyData();
                    task.setDataKey(data);
                    String provider = createProvide();
                    task.setProvider(provider);


                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            storage.saveTask(task);
                        }
                    }).start();

                    String crt = Cryptography.generatePair(task);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            storage.saveVariant(task, crt);
                        }
                    }).start();

                    manyVariants += ("Вариант - " + i + "\n" + "Идентификатор варианта " + task.getName() + "\n" +
                            "Строка - " + task.getDataKey() + "\n" +
                            "Провайдер - " + task.getProvider() + "\n" +
                            "Сертификат - " + "\n" + crt + "\n" + "-----------------------------------------------------------");
                    callBack.generatorCallBack(CallBackNotifications.UpdateGenerateVariants, Math.round((100 / size)*i));
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


                String finalManyVariants = manyVariants;
                callBack.generatorCallBack(CallBackNotifications.FinishManyVariants, null);
                try {
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
                    String fpath = "Варианты - " + Integer.toString(size) + "_" + timeStamp + ".txt";

                    storage.saveFile(fpath, manyVariants);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }).start();


    }

    @Override
    public void run() {


        Task task = new Task();
        String name = createNameCert();
        String variant = createNameVariant();
        task.setName(variant);
        callBack.generatorCallBack(CallBackNotifications.CreateNameTask, variant);

        String id = createIdTask(name);
        task.setId(id);

        String data = createKeyData();
        task.setDataKey(data);
        callBack.generatorCallBack(CallBackNotifications.CreateDataTask, data);


        String provider = createProvide();
        task.setProvider(provider);
        callBack.generatorCallBack(CallBackNotifications.CreateProviderTask, provider);
        callBack.generatorCallBack(CallBackNotifications.CreateVariant, variant);


        storage.saveTask(task);


        String crt = Cryptography.generatePair(task);

        new Thread(new Runnable() {
            @Override
            public void run() {
                storage.saveVariant(task, crt);
            }
        }).start();
        callBack.generatorCallBack(CallBackNotifications.CreateCrtTask, crt);


    }

    public void createQuestion() {
        String s = "<html>" + que.get(0 + (int) (Math.random() * que.size())) + "<br>" + que2.get(0 + (int) (Math.random() * que2.size())) +
                "<br>" + que3.get(0 + (int) (Math.random() * que3.size())) + "</html>";
        callBack.generatorCallBack(CallBackNotifications.CreateQuestions, s);
    }

    private String generateRandomIdString(int targetStringLength) {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'

        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString;
    }


}
