package vladgad;

import java.io.*;
import java.util.ArrayList;

public class Storage implements Runnable {
    //class for store tasks
    private ArrayList<Task> tasks = new ArrayList<>();
    private Thread storageTh;
    private CallBack callBack;

    public interface CallBack {
        void storageCallBack(CallBackNotifications callBackNotifications, Object obj);
    }

    public void registerCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public void saveTask(Task task) {
        String strTask = task.getId() + "&" + task.getName() + "&" + task.getDataKey() + "&" +
                task.getProvider();
        String fpath = Path.PATH_SAVE_TASKS + task.getId() + ".txt";
        try {
            saveFile(fpath, strTask);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveFile(String fpath, String data) throws IOException {
        File file = new File(fpath);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
        String[] words = data.split("\n");
        for (String word : words) {
            writer.write(word);
            writer.newLine();
        }
        writer.close();
    }

    public void updateTask() {

        storageTh = new Thread(this);
        storageTh.start();

    }

    public Task getTask(String idTask) {
        Task taske = null;
        for (Task task : tasks) {
            if (idTask.equals(task.getId())) {
                return task;
            }
        }
        return taske;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    private Task readTaskFile(File file) {
        Task task = new Task();
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                contentBuilder.append(sCurrentLine).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] split = contentBuilder.toString().split("&");
        task.setId(split[0]);
        task.setName(split[1]);
        task.setDataKey(split[2]);
        task.setProvider(split[3]);
        return task;
    }

    @Override
    public void run() {
        for (final File fileEntry : new File(Path.PATH_SAVE_TASKS).listFiles())
            tasks.add(readTaskFile(fileEntry));
        callBack.storageCallBack(CallBackNotifications.UpdateOpenTasks, tasks);
    }

    public void deleteFiles(String idTask) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                File cert = new File(Path.PATH_CERT + idTask + ".crt");
                File privateKey = new File(Path.PATH_PRIVATE_KEYS + idTask + ".pem");
                File task = new File(Path.PATH_SAVE_TASKS + idTask + ".txt");
                if (cert.delete()) {
                    System.out.println("File deleted successfully");
                } else {
                    System.out.println("Failed to delete the file");
                }

                if (privateKey.delete()) {
                    System.out.println("File deleted successfully");
                } else {
                    System.out.println("Failed to delete the file");
                }

                if (task.delete()) {
                    System.out.println("File deleted successfully");
                } else {
                    System.out.println("Failed to delete the file");
                }
                callBack.storageCallBack(CallBackNotifications.FinishDeleteTask, null);
            }
        }).start();

    }
}
