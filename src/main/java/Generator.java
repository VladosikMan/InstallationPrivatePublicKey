import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class Generator {
    //class for generation variant task
    // 1 - Id task
    // 2 - Name cert
    // 3 - Key data
    // 4 - Mode ecnryption
    // 5 - Scheme supllement
    // 6? - Question for check knowledge student

    private static ArrayList<Task> tasks = new ArrayList<>();
    private static ArrayList<String> names = new ArrayList<>();
    private static ArrayList<String> data = new ArrayList<>();

    public static void initialization() {
        names = getNames();
        data = getData();
    }

    public static Task generatetask() {
        Task task = new Task();
        task.setName(createNameCert());
        task.setId(createIdTask(task.getName()));
        task.setDataKey(createKeyData());
        task.setProvider(createProvide());
        return task;
    }

    private static ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<>();
        try {

            BufferedReader reader = null;
            reader = new BufferedReader(new FileReader(Path.PATH_NAMES));
            String line;
            while ((line = reader.readLine()) != null) {
                names.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < names.size(); i++)
            System.out.println(names.get(i));
        return names;
    }

    private static ArrayList<String> getData(){
        ArrayList<String> data = new ArrayList<>();
        try {

            BufferedReader reader = null;
            reader = new BufferedReader(new FileReader(Path.PATH_DATA));
            String line;
            while ((line = reader.readLine()) != null) {
                data.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < data.size(); i++)
            System.out.println(data.get(i));
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

        return null;
    }

    private ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public static void main(String[] args) {
        Generator.initialization();
        Task task = Generator.generatetask();
        System.out.println(task.getId());
        System.out.println(task.getName());
        System.out.println(task.getDataKey());
        System.out.println(task.getProvider());
    }
}
