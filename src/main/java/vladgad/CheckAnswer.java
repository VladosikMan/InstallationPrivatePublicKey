package vladgad;

import java.io.File;

public class CheckAnswer implements Runnable {

    private CallBack callBack;
    private Thread checkAnswerTh;
    private Task task;
    private String encrypt;

    public interface CallBack {
        void checkAnswerCallBack(CallBackNotifications callBackNotifications, Object obj);
    }

    public void registerCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public void CheckAnswer() {

    }

    public void check(Task task, String encrypt) {
        this.task = task;
        this.encrypt = encrypt;
        checkAnswerTh = new Thread(this);
        checkAnswerTh.start();
    }

    @Override
    public void run() {
        File file = new File(Path.PATH_PRIVATE_KEYS + task.getId() + ".pem");
        try {
            String decrypt = Cryptography.decrypt(Cryptography.PrivateKey(file), encrypt, task.getProvider());
            System.out.println(decrypt);
            if (decrypt.equals(task.getDataKey())) {
                callBack.checkAnswerCallBack(CallBackNotifications.ResultCheckAnswer, StatusCheck.Success);
                callBack.checkAnswerCallBack(CallBackNotifications.DeleteTask, task.getId());
                callBack.checkAnswerCallBack(CallBackNotifications.DeleteVariant, task.getName());
            } else {
                callBack.checkAnswerCallBack(CallBackNotifications.ResultCheckAnswer, StatusCheck.Fail);
            }
        } catch (Exception e) {
            callBack.checkAnswerCallBack(CallBackNotifications.ResultCheckAnswer, StatusCheck.Error);
            e.printStackTrace();
        }
    }
}

