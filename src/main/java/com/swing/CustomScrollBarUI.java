package com.swing;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class CustomScrollBarUI extends BasicScrollBarUI {

    private static final int SCROLL_BAR_WIDTH = 30; // 设置滚动条宽度为 30
    private static final int SCROLL_BAR_HEIGHT = 30; // 设置滚动条高度为 30

    @Override
    protected void configureScrollBarColors() {
        this.thumbColor = new Color(70, 130, 180); // 深天蓝色 (SteelBlue)
        this.thumbHighlightColor = new Color(100, 149, 237); // 矢车菊蓝 (CornflowerBlue)
        this.thumbDarkShadowColor = new Color(25, 25, 112); // 深海蓝 (MidnightBlue)
        this.trackColor = new Color(230, 230, 250); // 淡蓝色 (Lavender)
        this.trackHighlightColor = new Color(176, 196, 222); // 浅钢蓝 (LightSteelBlue)
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return createZeroButton();
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return createZeroButton();
    }

    private JButton createZeroButton() {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(0, 0));
        button.setMinimumSize(new Dimension(0, 0));
        button.setMaximumSize(new Dimension(0, 0));
        return button;
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        g.setColor(trackColor);
        g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        if (thumbBounds.isEmpty() || !scrollbar.isEnabled()) {
            return;
        }

        int w = thumbBounds.width;
        int h = thumbBounds.height;

        g.translate(thumbBounds.x, thumbBounds.y);

        g.setColor(thumbColor);
        g.fillRect(0, 0, w - 1, h - 1);

        g.setColor(thumbHighlightColor);
        g.drawRect(0, 0, w - 1, h - 1);

        g.translate(-thumbBounds.x, -thumbBounds.y);
    }

    @Override
    public Dimension getPreferredSize(JComponent c) {
        // 修正滚动条的宽度和高度
        if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
            return new Dimension(SCROLL_BAR_WIDTH, Integer.MAX_VALUE);
        } else {
            return new Dimension(Integer.MAX_VALUE, SCROLL_BAR_HEIGHT);
        }
    }
}