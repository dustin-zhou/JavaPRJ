package com.swing;

import javax.swing.*;
import java.awt.*;

public class CustomScrollBar {

    public static void main(String[] args) {
        // 创建一个 JFrame
        JFrame frame = new JFrame("Custom ScrollBar Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        // 创建一个 JTextArea 并添加一些示例文本
        JTextArea textArea = new JTextArea(20, 30);
        for (int i = 0; i < 50; i++) {
            textArea.append("This is line " + i + "\n");
        }

        // 创建一个 JScrollPane 并将 JTextArea 添加到其中
        JScrollPane scrollPane = new JScrollPane(textArea);

        // 设置滚动条的宽度
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(50, 0));
        scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 20));

        // 添加 JScrollPane 到 JFrame 中
        frame.add(scrollPane);
        frame.setVisible(true);
    }
}