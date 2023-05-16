package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;



public class ExpenseManager extends JFrame implements ActionListener {

    private JLabel amountLabel, categoryLabel, dateLabel, sumLabel;
    private JTextField amountField, categoryField, dateField, sumField;
    private JButton addButton, clearButton;
    private JTextArea expenseLog;
    private double totalExpenses = 0.0;
    private JComboBox<String> categoryComboBox;
//    private static final String FILE_NAME = "dane.xlsx";
//    private Workbook workbook;
//    private int rowNum = 0;

    public ExpenseManager() {
        super("Manager Wydatków");

        String[] categories = {"Jedzenie", "Transport", "Rachunki", "Rozrywka"};

        categoryComboBox = new JComboBox<>(categories);

        amountLabel = new JLabel("Kwota:");
        categoryLabel = new JLabel("Kategoria:");
        dateLabel = new JLabel("Data:");
        sumLabel = new JLabel("Suma wydatków");

        amountField = new JTextField(10);
        categoryField = new JTextField(10);
        dateField = new JTextField(10);
        sumField = new JTextField(10);
        sumField.setEditable(false);

        addButton = new JButton("Dodaj");
        addButton.addActionListener(this);

        clearButton = new JButton("Wyczyść");
        clearButton.addActionListener(this);

        expenseLog = new JTextArea(10, 30);
        expenseLog.setEditable(false);

        JPanel amountPanel = new JPanel(new FlowLayout());
        amountPanel.add(amountLabel);
        amountPanel.add(amountField);

        JPanel categoryPanel = new JPanel(new FlowLayout());
        categoryPanel.add(categoryLabel);
        categoryComboBox = new JComboBox<>(new String[]{"Jedzenie", "Transport", "Rachunki", "Rozrywka", "Inne"});
        categoryPanel.add(categoryComboBox);

        JPanel datePanel = new JPanel(new FlowLayout());
        datePanel.add(dateLabel);
        datePanel.add(dateField);

        JPanel sumPanel = new JPanel(new FlowLayout());
        sumPanel.add(sumLabel);
        sumPanel.add(sumField);


        JPanel inputPanel = new JPanel(new GridLayout(3, 1));
        inputPanel.add(amountPanel);
        inputPanel.add(categoryPanel);
        inputPanel.add(datePanel);
        inputPanel.add(sumPanel);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(clearButton);

        JPanel logPanel = new JPanel(new BorderLayout());
        logPanel.add(new JScrollPane(expenseLog), BorderLayout.CENTER);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(logPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        try {
            File file = new File("dane.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                expenseLog.append(line + "\n");
                double amount = Double.parseDouble(line.split(" ")[0].replace(",", "."));
                totalExpenses += amount;
            }
            scanner.close();
            sumField.setText(String.format("%.2f", totalExpenses));
        } catch (IOException e) {
            System.out.println("Błąd odczytu pliku");
            e.printStackTrace();
        }

    }


    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == addButton) {
            double amount = Double.parseDouble(amountField.getText());
            String date = dateField.getText();

            totalExpenses += amount;
            sumField.setText(String.format("%.2f", totalExpenses));

            String category = (String) categoryComboBox.getSelectedItem();

            expenseLog.append(String.format("%.2f zł - %s - %s\n", amount, category, date));
            amountField.setText("");
            categoryField.setText("");
            dateField.setText("");

            try {
                FileWriter writer = new FileWriter("dane.txt", true);
                writer.write(String.format("%.2f zł - %s - %s\n", amount, category, date));
                writer.close();


            } catch (IOException ex) {
                System.out.println("Błąd zapisu do pliku");
                ex.printStackTrace();
            }

            amountField.setText("");
            categoryField.setText("");
            dateField.setText("");

        } else if (e.getSource() == clearButton) {
            expenseLog.setText("");
        }

    }
    public static void main(String[] args) {
        new ExpenseManager();
    }
}











