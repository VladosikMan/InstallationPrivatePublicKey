package vladgad.GUI;

import vladgad.App;
import vladgad.CallBackNotifications;
import vladgad.StatusCheck;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CheckAnswerGUI implements App.CallbackCheckAnswer {
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
    private JScrollPane sp;


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
        Color colorPane = new Color(23,4,4);
        panel.setBackground(colorPane);


        panel.add(textEncrytLabel, setGridBagSettings(GridBagConstraints.NONE, 0, 0, 0.3f, 1f, new Insets(0, 10, 0, 0), GridBagConstraints.LINE_START));
        panel.add(sp, setGridBagSettings(GridBagConstraints.NONE, 1, 0, 0.7f, 1, GridBagConstraints.LINE_START));
        panel.add(textQueLabel, setGridBagSettings(GridBagConstraints.NONE, 0, 1, 0.3f, 1f, new Insets(0, 10, 0, 0), GridBagConstraints.LINE_START));
        panel.add(queLabel, setGridBagSettings(GridBagConstraints.NONE, 1, 1, 0.7f, 1f, new Insets(0, 10, 0, 0), GridBagConstraints.LINE_START));
        return panel;
    }

    private JPanel fillFuncPanel(JPanel panel) {
        panel.setLayout(new GridBagLayout());
        Color colorPane =  new Color(57, 15, 173,183);
        panel.setBackground(colorPane);

        app.updateTask();
        panel.add(textComboLabel, setGridBagSettings(GridBagConstraints.NONE, 0, 0, 1f, 0.1f));
        panel.add(taskComboBox, setGridBagSettings(GridBagConstraints.NONE, 0, 1, 1f, 0.2f));
        panel.add(infoFuncLabel, setGridBagSettings(GridBagConstraints.HORIZONTAL, 0, 2, 1f, 0.6f, new Insets(0,5,0,0), GridBagConstraints.LINE_START));
        panel.add(statusFuncLabel, setGridBagSettings(GridBagConstraints.NONE, 0, 3, 1f, 0.1f));
        panel.add(checkAnswerButton, setGridBagSettings(GridBagConstraints.NONE, 0, 4, 1f, 0.3f));
        panel.add(goGenerateButton, setGridBagSettings(GridBagConstraints.NONE, 0, 5, 1f, 0.1f));
        return panel;
    }

    public void createGUI(App app) {
        this.app = app;
        app.registerCallBackCheckAnswer(this);

        mainFrame = new JFrame("Проверка заданий");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(900, 600);
        mainFrame.setLocation(500, 50);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());

        JPanel checkPanel = new JPanel();
        //checkPanel.setBackground(new Color(255, 0, 0));
        checkPanel = fillCheckPanel(checkPanel);


        mainPanel.add(checkPanel, setGridBagSettings(GridBagConstraints.BOTH, 0, 0, 0.6f, 1));

        JPanel funcPanel = new JPanel();
        funcPanel.setBackground(new Color(234,234,234, 200));
        funcPanel = fillFuncPanel(funcPanel);

        mainPanel.add(funcPanel, setGridBagSettings(GridBagConstraints.BOTH, 1, 0, 0.4f, 1));

        buttonJob();
        mainFrame.setContentPane(mainPanel);
    }

    public void setVisible(boolean visible) {
        mainFrame.setVisible(visible);
        app.updateTask();
        clearElements();
    }

    private void buttonJob() {

        checkAnswerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!textEncryptTextArea.getText().isEmpty()) {
                    app.checkAnswer(taskComboBox.getItemAt(taskComboBox.getSelectedIndex()), textEncryptTextArea.getText());
                } else {
                    appCallbackCheckAnswer(CallBackNotifications.EmptyTextArea, null);
                }
            }
        });
        goGenerateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // запустить процедуру генерации задачи
                app.startGenerationGUI();
            }
        });

    }

    public CheckAnswerGUI() {
        initElements();
    }

    private void initElements() {

        Color labelColor = new Color(223, 255,0, 215);
        Color areaColor = new Color(219, 210, 246, 150);
        Font font =  new Font("Courier", Font.ITALIC, 12);


        textEncrytLabel = new JLabel("Зашифрованные данные в Base64");
        textEncrytLabel.setForeground(labelColor);
        textEncrytLabel.setFont(font);

        textEncryptTextArea = new JTextArea(5, 30);
        textEncryptTextArea.setLineWrap(true);
        textEncryptTextArea.setWrapStyleWord(true);
        textEncryptTextArea.setEditable(true);
        textEncryptTextArea.setBackground(areaColor);



        sp = new JScrollPane(textEncryptTextArea);
        sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        textQueLabel = new JLabel("Вопросы к защите");
        textQueLabel.setForeground(labelColor);
        textQueLabel.setFont(font);


        queLabel = new JLabel();
        queLabel.setForeground(labelColor);
        queLabel.setFont(font);


        textComboLabel = new JLabel("Список вариантов");
        taskComboBox = new JComboBox<>();
        infoFuncLabel = new JLabel("<html>\n" +
                "1) Выберите из списка свой вариант<br>2) Внести в текстовое поле<br/>зашифрованную строку<br>" +
                "3) Дождитесь результата<br/>" +
                "4) Заберите вопросы</html>");
        statusFuncLabel = new JLabel();
        checkAnswerButton = new JButton("Проверить вариант");
        goGenerateButton = new JButton("Генерация задач");
    }

    public void clearElements() {
        textEncryptTextArea.setText("");
        queLabel.setText("");

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
    public void appCallbackCheckAnswer(CallBackNotifications callBackNotifications, Object obj) {
        System.out.println("Check " + callBackNotifications);
        switch (callBackNotifications) {
            case UpdateOpenTasks: {
                taskComboBox.removeAllItems();
                String[] s = (String[]) obj;
                taskComboBox.setModel(new DefaultComboBoxModel<>(s));
                break;
            }
            case ResultCheckAnswer: {
                switch ((StatusCheck) obj) {
                    case Success: {
                        statusFuncLabel.setText("Задание выполнено");
                        app.createQuestions();
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
            case FinishDeleteTask: {
                app.updateTask();
                break;
            }
        }
    }

}
