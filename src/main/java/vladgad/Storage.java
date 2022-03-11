package vladgad;

import java.io.*;
import java.util.ArrayList;

public class Storage {
    //class for store tasks
    private static ArrayList<Task> tasks = new ArrayList<>();


    public static void saveTask(Task task) {
        String strTask = task.getId() + "&" + task.getName() + "&" + task.getDataKey() + "&" +
                task.getProvider() + "&" + task.getQuestion();
        String fpath = Path.PATH_SAVE_TASKS + task.getId() + ".txt";
        try {
            saveFile(fpath, strTask);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveFile(String fpath, String data) throws IOException {
        File file = new File(fpath);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
        String[] words = data.split("\n");
        for (String word : words) {
            writer.write(word);
            writer.newLine();
        }
        writer.close();
    }

    public static ArrayList<Task> getTasks() {
        for (final File fileEntry : new File(Path.PATH_SAVE_TASKS).listFiles())
            tasks.add(readTaskFile(fileEntry));
        return tasks;
    }

    private static Task readTaskFile(File file) {
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
        task.setQuestion(split[4]);
        return task;
    }

}
