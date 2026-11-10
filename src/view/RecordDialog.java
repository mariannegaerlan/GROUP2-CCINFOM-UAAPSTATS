package view;

import java.awt.Component;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/** Starter Add/Edit dialog. */
public class RecordDialog {
    public static String[] showDialog(Component parent, String title, String[] labels) {
        JPanel panel = new JPanel(new GridLayout(labels.length, 2, 8, 8));
        JTextField[] fields = new JTextField[labels.length];

        for (int i = 0; i < labels.length; i++) {
            panel.add(new JLabel(labels[i]));
            fields[i] = new JTextField(16);
            panel.add(fields[i]);
        }

        int result = JOptionPane.showConfirmDialog(parent, panel, title,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION) return null;

        String[] values = new String[fields.length];
        for (int i = 0; i < fields.length; i++) values[i] = fields[i].getText().trim();
        return values;
    }
}
