package com.swing;

import javax.swing.*;

public class MainApp {

    public static void main(String[] args) {
        // 设置外观和感觉（可选）
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("App with Custom ScrollBar");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);

            JTextArea textArea = new JTextArea(10, 40);
            for (int i = 0; i < 100; i++) {
                textArea.append("This is line " + i + "\n");
            }

            JScrollPane scrollPane = new JScrollPane(textArea);

            JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
            verticalScrollBar.setUI(new CustomScrollBarUI());

            JScrollBar horizontalScrollBar = scrollPane.getHorizontalScrollBar();
            horizontalScrollBar.setUI(new CustomScrollBarUI());

            frame.add(scrollPane);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}