package vladgad.GUI;

import vladgad.App;
import vladgad.CallBackNotifications;
import vladgad.StatusCheck;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CheckAnswerGUI implements App.Callback {
    private JFrame mainFrame;
    private App app;
    private JLabel textEncrytLabel;
    private JTextArea textEncryptTextArea;
    private JLabel textQueLabel;
    private JLabel queLabel;

    private JLabel textComboLabel;
    private JComboBox<String> taskComboBox;
    private JLabel infoFuncLabel;
    private JLabel statusFuncLabel;
    private JButton checkAnswerButton;
    private JButton goGenerateButton;


    private int noZero(int x) {
        if (x != 0)
            return x;
        else
            return 0;
    }

    private float noZero(float x) {
        if (x != 0)
            return x;
        else
            return 0;
    }

    private JPanel fillCheckPanel(JPanel panel) {
        panel.setLayout(new GridBagLayout());
        panel.add(textEncrytLabel, setGridBagSettings(GridBagConstraints.NONE, 0, 0, 0.3f, 1f, new Insets(0, 10, 0, 0), GridBagConstraints.LINE_START));
        panel.add(textEncryptTextArea, setGridBagSettings(GridBagConstraints.NONE, 1, 0, 0.7f, 1, GridBagConstraints.LINE_START));
        panel.add(textQueLabel, setGridBagSettings(GridBagConstraints.NONE, 0, 1, 0.3f, 1f, new Insets(0, 10, 0, 0), GridBagConstraints.LINE_START));
        panel.add(queLabel, setGridBagSettings(GridBagConstraints.NONE, 1, 1, 0.7f, 1f, new Insets(0, 10, 0, 0), GridBagConstraints.LINE_START));
        return panel;
    }

    private JPanel fillFuncPanel(JPanel panel) {
        panel.setLayout(new GridBagLayout());
        app.initTasks();
        panel.add(textComboLabel, setGridBagSettings(GridBagConstraints.NONE, 0, 0, 1f, 0.1f));
        panel.add(taskComboBox, setGridBagSettings(GridBagConstraints.NONE, 0, 1, 1f, 0.2f));
        panel.add(infoFuncLabel, setGridBagSettings(GridBagConstraints.NONE, 0, 2, 1f, 0.2f));
        panel.add(statusFuncLabel, setGridBagSettings(GridBagConstraints.NONE, 0, 3, 1f, 0.1f));
        panel.add(checkAnswerButton, setGridBagSettings(GridBagConstraints.NONE, 0, 4, 1f, 0.3f));
        panel.add(goGenerateButton, setGridBagSettings(GridBagConstraints.NONE, 0, 5, 1f, 0.1f));
        return panel;
    }

    public void createGUI(App app) {
        this.app = app;
        app.registerCallBack(this);

        mainFrame = new JFrame("Проверка заданий");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(900, 600);
        mainFrame.setLocation(500, 50);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());

        JPanel checkPanel = new JPanel();
        checkPanel.setBackground(new Color(255, 0, 0));
        checkPanel = fillCheckPanel(checkPanel);


        mainPanel.add(checkPanel, setGridBagSettings(GridBagConstraints.BOTH, 0, 0, 0.6f, 1));

        JPanel funcPanel = new JPanel();
        funcPanel.setBackground(new Color(0, 0, 255));
        funcPanel = fillFuncPanel(funcPanel);

        mainPanel.add(funcPanel, setGridBagSettings(GridBagConstraints.BOTH, 1, 0, 0.4f, 1));

        buttonJob();
        mainFrame.setContentPane(mainPanel);
        mainFrame.setVisible(true);
    }

    private void buttonJob() {

        checkAnswerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.createQuestions();
//                if(!textEncryptTextArea.getText().isEmpty()){
//                    app.checkAnswer(taskComboBox.getItemAt(taskComboBox.getSelectedIndex()),textEncryptTextArea.getText());
//                }else{
//                    appCallback(CallBackNotifications.EmptyTextArea, null);
//                }
            }
        });
        goGenerateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // запустить процедуру генерации задачи
                mainFrame.dispose();
            }
        });

    }

    public CheckAnswerGUI() {
        initElements();
    }

    private void initElements(){
        textEncrytLabel = new JLabel("Зашифрованные данные в Base64");
        textEncryptTextArea = new JTextArea(5, 30);
        textEncryptTextArea.setLineWrap(true);
        textEncryptTextArea.setWrapStyleWord(true);
        textEncryptTextArea.setEditable(true);
        textQueLabel = new JLabel("Вопросы к защите");
        queLabel = new JLabel("<html>1) Crt формат <br> 2) CBC режим шифрования <br>3) Что такое SSL/TCL</html>");
        textComboLabel = new JLabel("Список вариантов");
        taskComboBox = new JComboBox<>();
        infoFuncLabel = new JLabel("<html>Строка1<br>Строка2<br>Строка 3</html>");
        statusFuncLabel = new JLabel();
        checkAnswerButton = new JButton("Проверить вариант");
        goGenerateButton = new JButton("Генерация задач");
    }
    private GridBagConstraints setGridBagSettings(int fill, int gridx, int gridy, float weightx, float weighty) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = fill;
        c.gridx = gridx;
        c.gridy = gridy;
        c.weightx = noZero(weightx);
        c.weighty = noZero(weighty);
        return c;
    }

    private GridBagConstraints setGridBagSettings(int fill, int gridx, int gridy, float weightx, float weighty, int anchor) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = fill;
        c.gridx = gridx;
        c.gridy = gridy;
        c.weightx = noZero(weightx);
        c.weighty = noZero(weighty);
        c.anchor = anchor;
        return c;
    }

    private GridBagConstraints setGridBagSettings(int fill, int gridx, int gridy, float weightx, float weighty, Insets insets, int anchor) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = fill;
        c.gridx = gridx;
        c.gridy = gridy;
        c.weightx = noZero(weightx);
        c.weighty = noZero(weighty);
        c.anchor = anchor;
        c.insets = insets;
        return c;
    }

    private GridBagConstraints setGridBagSettings(int fill, int gridx, int gridy, float weightx, float weighty, int gridwidth, int gridheight, Insets insets, int anchor) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = fill;
        c.gridx = gridx;
        c.gridy = gridy;
        c.weightx = noZero(weightx);
        c.weighty = noZero(weighty);
        c.anchor = anchor;
        c.insets = insets;
        c.gridwidth = gridwidth;
        c.gridheight = gridheight;
        return c;
    }

    private GridBagConstraints setGridBagSettings(int fill, int gridx, int gridy, float weightx, float weighty, Insets insets) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = fill;
        c.gridx = gridx;
        c.gridy = gridy;
        c.weightx = noZero(weightx);
        c.weighty = noZero(weighty);
        c.insets = insets;
        return c;
    }


    @Override
    public void appCallback(CallBackNotifications callBackNotifications, Object obj) {
        switch (callBackNotifications) {
            case InitOpenTasks: {
                String[] s = (String[]) obj;
                taskComboBox.setModel(new DefaultComboBoxModel<>(s));
                //taskComboBox = new JComboBox<>(s);
                break;
            }
            case ResultCheckAnswer: {
                switch ((StatusCheck) obj) {
                    case Success: {
                        statusFuncLabel.setText("Задание выполнено");
                        app.createQuestions();
                        //вывести вопросы

                        break;
                    }
                    case Fail: {
                        statusFuncLabel.setText("Соообщение не сошлось");
                        break;
                    }
                    case Error: {
                        statusFuncLabel.setText("Ошибка расшифровки");
                        break;
                    }

                }
                break;
            }
            case EmptyTextArea: {
                statusFuncLabel.setText("Не введены данные");
                break;
            }
            case FinishInitData: {
                System.out.println("Finish Init");
                break;
            }
            case CreateQuestions: {
                queLabel.setText(obj.toString());
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + callBackNotifications);
        }
    }
}
