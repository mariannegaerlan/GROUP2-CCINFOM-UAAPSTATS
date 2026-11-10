package view;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

/** Basic shared styles. Add more styling here as the interface develops. */
public final class Theme {
    public static final Color DARK = new Color(75, 75, 75);
    public static final Color GRAY = new Color(165, 165, 165);
    public static final Color WHITE = Color.WHITE;
    public static final Font NORMAL = new Font("Arial", Font.PLAIN, 14);
    public static final Font TITLE = new Font("Arial", Font.BOLD, 23);

    private Theme() { }

    public static void styleButton(JButton button) {
        button.setFont(NORMAL);
        button.setFocusPainted(false);
    }

    public static void styleTable(JTable table) {
        table.setFont(NORMAL);
        table.setRowHeight(30);
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setBackground(new Color(225, 225, 225));
    }
}
