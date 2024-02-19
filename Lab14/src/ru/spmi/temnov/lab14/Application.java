package ru.spmi.temnov.lab14;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Application extends JFrame implements ActionListener {//класс приложения
    private boolean wasWritten = false;//был ли записан файл
    private final JButton countButton, printButton;//кнопки
    private final JRadioButton lambdaButton, refButton;//radiobutton
    private final JLabel countChoice;//надпись
    private final JComboBox comboBox;//список выбора
    private JCheckBox checkBox;//флажок
    private TV[] tv = new TV[5];
    private Fridge[] fridge = new Fridge[5];
    private static void countAll(Countable2 countable, String name){
        countable.count(name);
    }
    private void print(){//вывод в файл
        if (!wasWritten) {
            FileWriter fw = null;
            try {
                fw = new FileWriter(new File("appliances.txt"));//открытия файла на запись
                StringBuilder text = new StringBuilder();
                for (TV tvs : tv) {
                    text.append(tvs.show());
                }
                for (Fridge fr : fridge) {
                    text.append(fr.show());
                }
                fw.write(text.toString());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } finally {
                try {
                    fw.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            this.wasWritten = true;
        }
        if (checkBox.isSelected() && Desktop.isDesktopSupported()) {//открытие файла с результатами
            try {
                Desktop.getDesktop().open(new File("appliances.txt"));
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Ошибка открытия " + ex);
            }
        }
    }

    public Application() {
        setTitle("Подсчёт товаров");
        setVisible(true);
        setSize(350, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = getContentPane();
        container.setLayout(new FlowLayout(FlowLayout.CENTER));
        setLocationRelativeTo(null);

        countButton = new JButton("Посчитать");
        countButton.setPreferredSize(new Dimension(275, 30));
        countButton.addActionListener(this);


        countChoice = new JLabel("Способ подсчета");
        countChoice.setPreferredSize(new Dimension(275, 30));


        lambdaButton = new JRadioButton("Лямбда-функция");
        lambdaButton.setPreferredSize(new Dimension(150,30));
        lambdaButton.setSelected(true);


        refButton = new JRadioButton("Ссылка на метод");
        refButton.setPreferredSize(new Dimension(150,30));


        ButtonGroup bg = new ButtonGroup();
        bg.add(lambdaButton);
        bg.add(refButton);

        printButton = new JButton("Вывести список в файл");
        printButton.setPreferredSize(new Dimension(275, 30));
        printButton.addActionListener(this);

        checkBox = new JCheckBox("Открывать текстовый файл после записи");
        checkBox.setPreferredSize(new Dimension(275, 30));

        comboBox = new JComboBox<>(RandomGenerator.getAll());
        comboBox.setPreferredSize(new Dimension(150,30));


        container.add(printButton);
        container.add(checkBox);
        container.add(countButton);
        container.add(countChoice);
        container.add(lambdaButton);
        container.add(refButton);
        container.add(comboBox);

        for (int i = 0; i < 5; ++i) {
            tv[i] = new TV(RandomGenerator.getRandomComp(),RandomGenerator.getRandomScreen());
            fridge[i] = new Fridge(RandomGenerator.getRandomComp(), RandomGenerator.getRandomShelf());
        }
    }
    private void count(String needed, boolean option){//вычисление ВП
        String method = null;
        if (needed != null){
            if (option){
                method = "Лямбда-функция";
                Appliances.zeroize();
                Countable countable = (app) -> {if(app.getName().equals(needed)) Appliances.incrNum();};
                for (int i = 0; i < tv.length; ++i){
                    countable.count(tv[i]);
                    countable.count(fridge[i]);
                }
            }
            else{
                method = "Ссылка на метод";
                Appliances.zeroize();
                for (int i = 0; i < tv.length; ++i){
                    countAll(tv[i]::countApp, needed);
                    countAll(fridge[i]::countApp, needed);
                }
            }
        }
        JOptionPane.showMessageDialog(this, "Количество товаров выбранной фирмы =  " + Appliances.getNum() + "\nСпособ подсчета = " + method);
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == countButton) {
            count((String) comboBox.getSelectedItem(), lambdaButton.isSelected());
        } else if (e.getSource() == printButton) {
            print();
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Application();
            }
        });
    }
}
