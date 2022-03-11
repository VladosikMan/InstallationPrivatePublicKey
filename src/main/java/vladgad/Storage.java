package vladgad;

import java.io.*;

public class Storage {
    //class for store tasks
    public static void saveTask(Task task){
        String strTask = task.getId() + "\n" + task.getName() + "\n" + task.getDataKey() + "\n" +
                task.getProvider() + "\n" + task.getQuestion();
        System.out.println(strTask);
        String fpath  = Path.PATH_SAVE_TASKS + task.getId() + ".txt";
        try {
            saveFile(fpath, strTask);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void saveFile(String fpath, String data) throws IOException {
//        FileOutputStream outputStream = new FileOutputStream(fpath);
//        byte[] strToBytes = data.getBytes();
//        outputStream.write(strToBytes);
//        outputStream.close();

        File file = new File(fpath);

        // you want to output to file
        // BufferedWriter writer = new BufferedWriter(new FileWriter(file3, true));
        // but let's print to console while debugging
        //BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));

        String[] words = data.split("\n");
        for (String word: words) {
            writer.write(word);
            writer.newLine();
        }
        writer.close();

    }

}
