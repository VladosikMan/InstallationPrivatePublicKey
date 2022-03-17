package vladgad.GUI;

import vladgad.App;
import vladgad.CallBackNotifications;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

public class GenerationGUI implements App.CallbackGenerate {
    private JFrame mainFrame;
    private JLabel dataLabel;
    private JTextField textDataLabel;
    private JLabel crtLabel;
    private JLabel providerLabel;
    private JLabel textProviderLabel;
    private JLabel infoLabel;
    private JLabel funcLabel;

    private JTextArea crtTextArea;
    private JButton saveClipboardCrtButton;
    private JButton saveClipboardDataButton;
    private JButton generateTaskButton;
    private JButton goCheckAnswerButton;
    private App app;
    private JScrollPane sp;
    private JSlider queVariantsSlider;
    private JTextField queVariantsField;
    private JProgressBar progressBar;


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
        panel.add(dataLabel, setGridBagSettings(GridBagConstraints.NONE, 0, 0, 0.05f, 1f, new Insets(20, 10, 0, 0), GridBagConstraints.FIRST_LINE_START));
        panel.add(textDataLabel, setGridBagSettings(GridBagConstraints.HORIZONTAL, 1, 0, 0.95f, 1f, new Insets(20, 0, 0, 40), GridBagConstraints.FIRST_LINE_START));
        panel.add(saveClipboardDataButton, setGridBagSettings(GridBagConstraints.NONE, 1, 0, 1f, 1f, 2, 1, new Insets(10, 0, 5, 20), GridBagConstraints.LAST_LINE_END));
        return panel;
    }

    private JPanel fillTaskDataKey(JPanel panel) {
        panel.setLayout(new GridBagLayout());
        panel.add(crtLabel, setGridBagSettings(GridBagConstraints.HORIZONTAL, 0, 0, 0.1f, 1f, new Insets(20, 10, 0, 0), GridBagConstraints.FIRST_LINE_START));
        panel.add(sp, setGridBagSettings(GridBagConstraints.HORIZONTAL, 1, 0, 0.9f, 1f, new Insets(20, 0, 0, 20), GridBagConstraints.FIRST_LINE_START));
        panel.add(saveClipboardCrtButton, setGridBagSettings(GridBagConstraints.NONE, 0, 1, 1f, 0.3f, 2, 1, new Insets(0, 0, 0, 20), GridBagConstraints.FIRST_LINE_END));
        return panel;
    }

    private JPanel fillTaskProvider(JPanel panel) {
        panel.setLayout(new GridBagLayout());
        panel.add(providerLabel, setGridBagSettings(GridBagConstraints.HORIZONTAL, 0, 0, 0.3f, 0f, new Insets(0, 10, 0, 0), GridBagConstraints.LINE_START));
        panel.add(textProviderLabel, setGridBagSettings(GridBagConstraints.HORIZONTAL, 1, 0, 0.7f, 0f));
        return panel;
    }

    private JPanel fillTaskPanel(JPanel panel) {
        panel.setLayout(new GridBagLayout());

        Color color = new Color(48,0,12,204);
        JPanel j1 = new JPanel();
        j1.setBackground(color);
        j1 = fillTaskName(j1);

        JPanel j2 = new JPanel();
        j2.setBackground(color);
        j2 = fillTaskDataKey(j2);

        JPanel j3 = new JPanel();
        j3.setBackground(color);
        j3 = fillTaskProvider(j3);

        panel.add(j1, setGridBagSettings(GridBagConstraints.BOTH, 0, 0, 1f, 0.10f));
        panel.add(j2, setGridBagSettings(GridBagConstraints.BOTH, 0, 1, 1f, 0.8f));
        panel.add(j3, setGridBagSettings(GridBagConstraints.BOTH, 0, 2, 1f, 0.1f));

        return panel;
    }

    private JPanel fillFuncPanel(JPanel panel) {
        panel.setLayout(new GridBagLayout());
        Color colorFunc = new Color(57, 15, 173,183);

        panel.setBackground(colorFunc);
        panel.add(funcLabel, setGridBagSettings(GridBagConstraints.NONE, 0, 0, 1f, 0.1f));
        panel.add(infoLabel, setGridBagSettings(GridBagConstraints.HORIZONTAL, 0, 1, 1f, 0.4f, new Insets(0, 5, 0, 0), GridBagConstraints.LINE_START));
        panel.add(queVariantsSlider,setGridBagSettings(GridBagConstraints.HORIZONTAL, 0,2,0.7f,0.1f, new Insets(0,5,0,0), GridBagConstraints.LINE_START));
        panel.add(queVariantsField,setGridBagSettings(GridBagConstraints.HORIZONTAL, 1,2,0.3f,0.1f, new Insets(0,0,0,5), GridBagConstraints.LINE_START));

        panel.add(progressBar, setGridBagSettings(GridBagConstraints.HORIZONTAL, 0, 3, 1f, 0.1f,new Insets(0,10,0,0),  GridBagConstraints.CENTER));

        panel.add(generateTaskButton, setGridBagSettings(GridBagConstraints.NONE, 0, 4, 1f, 0.25f));
        panel.add(goCheckAnswerButton, setGridBagSettings(GridBagConstraints.NONE, 0, 5, 1f, 0.05f));
        return panel;
    }

    private void elementsJob() {

        saveClipboardCrtButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!crtTextArea.getText().isEmpty()) {
                    String myString = crtTextArea.getText();
                    StringSelection stringSelection = new StringSelection(myString);
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(stringSelection, null);
                }

            }
        });
        saveClipboardDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!textDataLabel.getText().isEmpty()) {
                    String myString = textDataLabel.getText();
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
                if(queVariantsSlider.getValue() == 1){
                    app.generate();
                }else{
                    app.generateManyVariants(queVariantsSlider.getValue());
                }

            }
        });
        goCheckAnswerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.startCheckAnswerGUI();
            }
        });
        queVariantsSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                try{
                    queVariantsField.setText(String.valueOf(queVariantsSlider.getValue()));
                }catch (NumberFormatException exception) {
                    exception.printStackTrace();
                    System.out.println("Error input");

                }

            }
        });
        queVariantsField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try{
                    queVariantsSlider.setValue(Integer.parseInt(queVariantsField.getText()));
                }catch (NumberFormatException exception) {
                    exception.printStackTrace();
                    System.out.println("Error input");
                    queVariantsField.setText(String.valueOf(queVariantsSlider.getValue()));
                }
            }
        });



    }


    private void initElements() {

        Color labelColor = new Color(223, 255,0, 215);
        Color areaColor = new Color(219, 210, 246, 150);
        Font font =  new Font("Courier", Font.ITALIC, 12);



        dataLabel = new JLabel("Строка шифрования: ");
        dataLabel.setForeground(labelColor);
        dataLabel.setFont(font);

        textDataLabel = new JTextField(80);
        textDataLabel.setEditable(false);
        textDataLabel.setBackground(areaColor);


        funcLabel = new JLabel();
        funcLabel.setFont(font);


        crtLabel = new JLabel("Сертификат: ");
        crtLabel.setForeground(labelColor);
        crtLabel.setFont(font);

        crtTextArea = new JTextArea(17, 30);
        crtTextArea.setLineWrap(true);
        crtTextArea.setWrapStyleWord(true);
        crtTextArea.setEditable(false);
        crtTextArea.setBackground(areaColor);

        sp = new JScrollPane(crtTextArea);
        sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        saveClipboardCrtButton = new JButton("Копировать сертификат");
        saveClipboardDataButton = new JButton("Копировать строку");

        providerLabel = new JLabel("Провайдер: ");
        providerLabel.setForeground(labelColor);
        providerLabel.setFont(font);

        textProviderLabel = new JLabel();
        textProviderLabel.setForeground(labelColor);
        textProviderLabel.setFont(font);
        infoLabel = new JLabel("<html>1) Сгенериуйте задание<br>2) Скопируйте строку шифрования, сертификат и параметры шифрования<br>" +
                "3) С помощью вашей программы закодируйте открытым ключом строку<br>4) Проверьте проделанную работу</html>");
        infoLabel.setFont(font);

        generateTaskButton = new JButton("Сгенерировать задание");
        goCheckAnswerButton = new JButton("Проверить задание");

        queVariantsSlider = new JSlider(1,31,1);
        queVariantsField = new JTextField(3);

        queVariantsSlider.setMajorTickSpacing(10);
        queVariantsSlider.setMinorTickSpacing(2);
        queVariantsSlider.setPaintTicks(true);

        //queVariantsSlider.setBackground(new Color(57, 15, 173,183));
        queVariantsSlider.setPaintLabels(true);
        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        progressBar.setValue(0);

    }

    public void clearElements() {
        textDataLabel.setText("");
        crtTextArea.setText("");
        textProviderLabel.setText("");
    }

    public GenerationGUI() {
        initElements();
    }

    public void createGUI(App app) {
        this.app = app;
        app.registerCallBackGenerate(this);

        mainFrame = new JFrame("Генератор заданий");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(820, 530);
        mainFrame.setLocation(300, 50);
        mainFrame.setResizable(true);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());

        JPanel taskPanel = new JPanel();
        taskPanel.setBackground(new Color(23, 4, 4));
        taskPanel = fillTaskPanel(taskPanel);

        mainPanel.add(taskPanel, setGridBagSettings(GridBagConstraints.BOTH, 0, 0, 0.8f, 1));


        JPanel funcPanel = new JPanel();
        funcPanel.setBackground(new Color(234, 234, 234, 200));
        funcPanel = fillFuncPanel(funcPanel);

        mainPanel.add(funcPanel, setGridBagSettings(GridBagConstraints.BOTH, 1, 0, 0.2f, 1));
        elementsJob();
        mainFrame.setContentPane(mainPanel);

    }


    public void setVisible(boolean visible) {
        mainFrame.setVisible(visible);
        clearElements();
    }


    @Override
    public void appCallbackGenerate(CallBackNotifications callBackNotifications, Object obj) {
        System.out.println("Generate " + callBackNotifications);
        switch (callBackNotifications) {
            case CreateIdTask: {
                break;
            }
            case CreateNameTask: {
                System.out.println("Ваш вариант" + obj.toString());
                break;
            }
            case CreateProviderTask: {
                textProviderLabel.setText(obj.toString());
                break;
            }
            case CreateDataTask: {
                textDataLabel.setText(obj.toString());
                funcLabel.setText("Задача сгенерирована");
                break;
            }
            case CreateVariant:{
                funcLabel.setText("<html>Задача сгенерированна" +"<br>"+ "Вариант - " + obj.toString()+ "</html>");
                break;
            }
            case FinishInitData: {
                funcLabel.setText("Данные готовы");
                System.out.println("Finish Init Data");
                break;
            }
            case CreateCrtTask: {
                crtTextArea.setText(obj.toString());
                break;
            }
            case FinishManyVariants:{
                System.out.println("Sgen");
                progressBar.setValue(0);
                break;
            }
            case UpdateGenerateVariants:{
                progressBar.setValue((Integer) obj);
                break;
            }
        }
    }
}
