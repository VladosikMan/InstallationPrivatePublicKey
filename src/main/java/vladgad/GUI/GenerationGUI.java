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
    private JLabel funcLabel;

    private JTextArea crtTextArea;
    private JButton saveClipboardCrtButton;
    private JButton saveClipboardDataButton;
    private JButton generateTaskButton;
    private JButton goCheckAnswerButton;
    private App app;
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


        JPanel j1 = new JPanel();
        j1.setBackground(new Color(255, 0, 255));
        j1 = fillTaskName(j1);

        JPanel j2 = new JPanel();
        j2.setBackground(new Color(255, 255, 0));
        j2 = fillTaskDataKey(j2);

        JPanel j3 = new JPanel();
        j3.setBackground(new Color(255, 255, 255));
        j3 = fillTaskProvider(j3);

        panel.add(j1, setGridBagSettings(GridBagConstraints.BOTH, 0, 0, 1f, 0.10f));
        panel.add(j2, setGridBagSettings(GridBagConstraints.BOTH, 0, 1, 1f, 0.8f));
        panel.add(j3, setGridBagSettings(GridBagConstraints.BOTH, 0, 2, 1f, 0.1f));

        return panel;
    }

    private JPanel fillFuncPanel(JPanel panel) {
        panel.setLayout(new GridBagLayout());
        panel.add(funcLabel, setGridBagSettings(GridBagConstraints.NONE, 0, 0, 1f, 0.1f));
        panel.add(infoLabel, setGridBagSettings(GridBagConstraints.HORIZONTAL, 0, 1, 1f, 0.6f, new Insets(0,5,0,0), GridBagConstraints.LINE_START));
        panel.add(generateTaskButton, setGridBagSettings(GridBagConstraints.NONE, 0, 2, 1f, 0.25f));
        panel.add(goCheckAnswerButton, setGridBagSettings(GridBagConstraints.NONE, 0, 3, 1f, 0.05f));
        return panel;
    }

    private void buttonJob() {

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
                // ��������� ��������� ��������� ������
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

    private void initElements() {
        dataLabel = new JLabel("������ ����������: ");
        textDataLabel = new JTextField(80);
        textDataLabel.setEditable(false);
        funcLabel = new JLabel();
        crtLabel = new JLabel("����������: ");
        crtTextArea = new JTextArea(17, 30);

        crtTextArea.setLineWrap(true);
        crtTextArea.setWrapStyleWord(true);
        crtTextArea.setEditable(false);
        sp = new JScrollPane(crtTextArea);
        sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        saveClipboardCrtButton = new JButton("���������� ����������");
        saveClipboardDataButton = new JButton("���������� ������");
        providerLabel = new JLabel("���������: ");
        textProviderLabel = new JLabel();
        infoLabel = new JLabel("<html>1) ����������� �������<br>2) ���������� ������ ����������, ���������� � ��������� ����������<br>" +
                "3) � ������� ����� ��������� ����������� �������� ������ ������<br>4) ��������� ����������� ������</html>");
        generateTaskButton = new JButton("������������� �������");
        goCheckAnswerButton = new JButton("��������� �������");
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

        mainFrame = new JFrame("��������� �������");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(820, 530);
        mainFrame.setLocation(300, 50);
        mainFrame.setResizable(true);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());

        JPanel taskPanel = new JPanel();
        //taskPanel.setBackground(new Color(255, 0, 0));
        taskPanel = fillTaskPanel(taskPanel);

        mainPanel.add(taskPanel, setGridBagSettings(GridBagConstraints.BOTH, 0, 0, 0.8f, 1));


        JPanel funcPanel = new JPanel();
        funcPanel = fillFuncPanel(funcPanel);

        mainPanel.add(funcPanel, setGridBagSettings(GridBagConstraints.BOTH, 1, 0, 0.2f, 1));
        buttonJob();
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

                break;
            }
            case CreateProviderTask: {
                textProviderLabel.setText(obj.toString());
                break;
            }
            case CreateDataTask: {
                textDataLabel.setText(obj.toString());
                funcLabel.setText("������ �������������");
                break;
            }
            case FinishInitData: {
                funcLabel.setText("������ ������");
                System.out.println("Finish Init Data");
                break;
            }
            case CreateCrtTask: {
                crtTextArea.setText(obj.toString());
                break;
            }
        }
    }
}
