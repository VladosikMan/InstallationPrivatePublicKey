package vladgad.GUI;

import vladgad.App;
import vladgad.CallBackNotifications;
import vladgad.Generator;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GenerationGUI implements App.CallbackGenerate {
    private JFrame mainFrame;
    private JLabel dataLabel;
    private JTextField textDataLabel;
    private JLabel crtLabel;
    private JLabel providerLabel;
    private JLabel textProviderLabel;
    private JLabel infoLabel;
    private JTextArea crtTextArea;
    private JButton saveClipboardCrtButton;
    private JButton generateTaskButton;
    private JButton goCheckAnswerButton;
    private App app;


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

    private JPanel fillTaskName(JPanel panel) {
        panel.setLayout(new GridBagLayout());
        panel.add(dataLabel, setGridBagSettings(GridBagConstraints.HORIZONTAL, 0, 0, 0.1f, 0f, new Insets(0, 50, 0, 0), GridBagConstraints.LINE_START));
        panel.add(textDataLabel, setGridBagSettings(GridBagConstraints.HORIZONTAL, 1, 0, 0.9f, 0f, new Insets(0, 0, 0, 5), GridBagConstraints.LINE_START));
        return panel;
    }

    private JPanel fillTaskDataKey(JPanel panel) {
        panel.setLayout(new GridBagLayout());
        panel.add(crtLabel, setGridBagSettings(GridBagConstraints.HORIZONTAL, 0, 0, 0.3f, 1f, new Insets(0, 50, 0, 0)));
        panel.add(crtTextArea, setGridBagSettings(GridBagConstraints.HORIZONTAL, 1, 0, 0.7f, 1f, new Insets(0, 0, 0, 5), GridBagConstraints.LINE_START));
        panel.add(saveClipboardCrtButton, setGridBagSettings(GridBagConstraints.NONE, 0, 1, 1f, 1f, 2, 1, new Insets(0, 0, 0, 20), GridBagConstraints.FIRST_LINE_END));
        return panel;
    }

    private JPanel fillTaskProvider(JPanel panel) {
        panel.setLayout(new GridBagLayout());
        panel.add(providerLabel, setGridBagSettings(GridBagConstraints.HORIZONTAL, 0, 0, 0.3f, 0f, new Insets(0, 50, 0, 0), GridBagConstraints.LINE_START));
        panel.add(textProviderLabel, setGridBagSettings(GridBagConstraints.HORIZONTAL, 1, 0, 0.7f, 0f));
        return panel;
    }

    private JPanel fillTaskPanel(JPanel panel) {
        panel.setLayout(new GridBagLayout());
        JPanel j1 = new JPanel();
        j1.setBackground(new Color(255, 0, 255));
        j1 = fillTaskName(j1);

        JPanel j2 = new JPanel();
        j2.setBackground(new Color(255, 255, 0));
        j2 = fillTaskDataKey(j2);

        JPanel j3 = new JPanel();
        j3.setBackground(new Color(255, 255, 255));
        j3 = fillTaskProvider(j3);

        panel.add(j1, setGridBagSettings(GridBagConstraints.BOTH, 0, 0, 1, 0.25f));
        panel.add(j2, setGridBagSettings(GridBagConstraints.BOTH, 0, 1, 1f, 0.5f));
        panel.add(j3, setGridBagSettings(GridBagConstraints.BOTH, 0, 2, 1f, 0.25f));

        return panel;
    }

    private JPanel fillFuncPanel(JPanel panel) {
        panel.setLayout(new GridBagLayout());
        panel.add(infoLabel, setGridBagSettings(GridBagConstraints.NONE, 0, 0, 1f, 0.7f));
        panel.add(generateTaskButton, setGridBagSettings(GridBagConstraints.NONE, 0, 1, 1f, 0.2f));
        panel.add(goCheckAnswerButton, setGridBagSettings(GridBagConstraints.NONE, 0, 2, 1f, 0.1f));

        return panel;
    }

    private void buttonJob() {

        saveClipboardCrtButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!crtTextArea.getText().isEmpty()){
                    String myString = crtTextArea.getText();
                    StringSelection stringSelection = new StringSelection(myString);
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(stringSelection, null);
                }

            }
        });
        generateTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // запустить процедуру генерации задачи
                app.generate();
            }
        });
        goCheckAnswerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.startCheckAnswerGUI();
            }
        });

    }
    private void initElements(){
        dataLabel = new JLabel("Строка шифрования: ");
        textDataLabel = new JTextField(80);
        crtLabel = new JLabel("Сертификат: ");
        crtTextArea = new JTextArea(15, 50);
        crtTextArea.setLineWrap(true);
        crtTextArea.setWrapStyleWord(true);
        crtTextArea.setEditable(false);
        saveClipboardCrtButton = new JButton("Копировать сертификат");
        providerLabel = new JLabel("Провайдер: ");
        textProviderLabel = new JLabel("Тут будет название провайдера");
        infoLabel = new JLabel("<html>Строка1<br>Строка2<br>Строка 3</html>");
        generateTaskButton = new JButton("Сгенерировать задание");
        goCheckAnswerButton = new JButton("Проверить задание");
    }

    public GenerationGUI(){
        initElements();
    }
    public void createGUI(App app) {
        this.app = app;
        app.registerCallBackGenerate(this);

        mainFrame = new JFrame("Генератор заданий");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(900, 600);
        mainFrame.setLocation(500, 50);
        mainFrame.setResizable(true);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());

        JPanel taskPanel = new JPanel();
        taskPanel.setBackground(new Color(255, 0, 0));
        taskPanel = fillTaskPanel(taskPanel);

        mainPanel.add(taskPanel, setGridBagSettings(GridBagConstraints.BOTH, 0, 0, 0.6f, 1));


        JPanel funcPanel = new JPanel();
        funcPanel.setBackground(new Color(0, 0, 255));
        funcPanel = fillFuncPanel(funcPanel);

        mainPanel.add(funcPanel, setGridBagSettings(GridBagConstraints.BOTH, 1, 0, 0.4f, 1));
        buttonJob();
        mainFrame.setContentPane(mainPanel);

    }


    public void setVisible(boolean visible){
        mainFrame.setVisible(visible);
    }

    @Override
    public void appCallbackGenerate(CallBackNotifications callBackNotifications, Object obj) {
        System.out.println("Generate " + callBackNotifications);
        switch (callBackNotifications) {
            case CreateIdTask: {

                break;
            }
            case CreateNameTask: {

                break;
            }
            case CreateProviderTask: {
                textProviderLabel.setText(obj.toString());
                break;
            }
            case CreateDataTask: {
                textDataLabel.setText(obj.toString());
                break;
            }
            case FinishInitData:{
                System.out.println("Finish Init Data");
                break;
            }
            case CreateCrtTask:{
                crtTextArea.setText(obj.toString());
                break;
            }
        }
    }

}
