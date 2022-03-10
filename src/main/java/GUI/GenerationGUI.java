package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GenerationGUI {
    private static JFrame mainFrame;
    private static JLabel nameLabel;
    private static JLabel textNameLabel;
    private static JLabel dataLabel;
    private static JLabel providerLabel;
    private static JLabel textProviderLabel;
    private static JLabel infoLabel;
    private static JTextArea dataTextArea;
    private static JButton saveClipboardCrtButton;
    private static JButton generateTaskButton;


    private static int noZero(int x) {
        if (x != 0)
            return x;
        else
            return 0;
    }

    private static float noZero(float x) {
        if (x != 0)
            return x;
        else
            return 0;
    }


    private static GridBagConstraints setGridBagSettings(int fill, int gridx, int gridy, float weightx, float weighty) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = fill;
        c.gridx = gridx;
        c.gridy = gridy;
        c.weightx = noZero(weightx);
        c.weighty = noZero(weighty);
        return c;
    }

    private static GridBagConstraints setGridBagSettings(int fill, int gridx, int gridy, float weightx, float weighty, int anchor) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = fill;
        c.gridx = gridx;
        c.gridy = gridy;
        c.weightx = noZero(weightx);
        c.weighty = noZero(weighty);
        c.anchor = anchor;
        return c;
    }

    private static GridBagConstraints setGridBagSettings(int fill, int gridx, int gridy, float weightx, float weighty, Insets insets, int anchor) {
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

    private static GridBagConstraints setGridBagSettings(int fill, int gridx, int gridy, float weightx, float weighty, int gridwidth, int gridheight, Insets insets, int anchor) {
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

    private static GridBagConstraints setGridBagSettings(int fill, int gridx, int gridy, float weightx, float weighty, Insets insets) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = fill;
        c.gridx = gridx;
        c.gridy = gridy;
        c.weightx = noZero(weightx);
        c.weighty = noZero(weighty);
        c.insets = insets;
        return c;
    }

    private static JPanel fillTaskName(JPanel panel) {
        panel.setLayout(new GridBagLayout());
        nameLabel = new JLabel("Название: ");
        textNameLabel = new JLabel("Тут будет название варианта");

        panel.add(nameLabel, setGridBagSettings(GridBagConstraints.HORIZONTAL, 0, 0, 0.3f, 0f, new Insets(0, 50, 0, 0), GridBagConstraints.LINE_START));
        panel.add(textNameLabel, setGridBagSettings(GridBagConstraints.HORIZONTAL, 1, 0, 0.7f, 0f));
        return panel;
    }

    private static JPanel fillTaskDataKey(JPanel panel) {
        panel.setLayout(new GridBagLayout());
        dataLabel = new JLabel("Сертификат: ");
        dataTextArea = new JTextArea(5, 20);
        dataTextArea.setLineWrap(true);
        dataTextArea.setWrapStyleWord(true);
        dataTextArea.setEditable(false);
        dataTextArea.setText("dslkgfskdjfsdkfjksaldfjskldjflksdjfkjsdklfjskd");
        saveClipboardCrtButton = new JButton("Копировать сертификат");
        panel.add(dataLabel, setGridBagSettings(GridBagConstraints.HORIZONTAL, 0, 0, 0.3f, 1f, new Insets(0, 50, 0, 0)));
        panel.add(dataTextArea, setGridBagSettings(GridBagConstraints.NONE, 1, 0, 0.7f, 1f, GridBagConstraints.LINE_START));
        panel.add(saveClipboardCrtButton, setGridBagSettings(GridBagConstraints.NONE, 0, 1, 1f, 1f, 2, 1, new Insets(0, 0, 0, 20), GridBagConstraints.FIRST_LINE_END));
        return panel;
    }

    private static JPanel fillTaskProvider(JPanel panel) {
        panel.setLayout(new GridBagLayout());
        providerLabel = new JLabel("Провайдер: ");
        textProviderLabel = new JLabel("Тут будет название провайдера");

        panel.add(providerLabel, setGridBagSettings(GridBagConstraints.HORIZONTAL, 0, 0, 0.3f, 0f, new Insets(0, 50, 0, 0), GridBagConstraints.LINE_START));
        panel.add(textProviderLabel, setGridBagSettings(GridBagConstraints.HORIZONTAL, 1, 0, 0.7f, 0f));
        return panel;
    }

    private static JPanel fillTaskPanel(JPanel panel) {
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

    private static JPanel fillFuncPanel(JPanel panel) {
        panel.setLayout(new GridBagLayout());
        infoLabel = new JLabel("<html>Строка1<br>Строка2<br>Строка 3</html>");
        generateTaskButton = new JButton("Сгенерировать задание");

        panel.add(infoLabel, setGridBagSettings(GridBagConstraints.NONE, 0, 0, 1f, 0.8f));
        panel.add(generateTaskButton, setGridBagSettings(GridBagConstraints.NONE, 0, 1, 1f, 0.2f));

        return panel;
    }

    private static void buttonJob() {

        saveClipboardCrtButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveClipboardCrtButton.setText("1");
            }
        });
        generateTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateTaskButton.setText("2");
            }
        });

    }

    public static void createGUI() {
        mainFrame = new JFrame("Генератор заданий");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(700, 400);
        mainFrame.setLocation(500, 50);


        mainFrame.setResizable(false);


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
        mainFrame.setVisible(true);

    }
}
