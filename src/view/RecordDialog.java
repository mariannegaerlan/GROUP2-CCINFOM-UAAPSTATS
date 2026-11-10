package view;

import java.awt.*;
import javax.swing.*;

public class RecordDialog extends JDialog {

    private JTextField[] textFields;
    private String[] savedValues = null;

    public RecordDialog(Window parent, String title, String[] columns, String[] existingValues) {
        super(parent, title, ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Form panel using simple GridLayout
        JPanel formPanel = new JPanel(new GridLayout(columns.length, 2, 12, 10));
        formPanel.setBackground(Theme.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 15, 25));

        textFields = new JTextField[columns.length];

        for (int i = 0; i < columns.length; i++) {
            JLabel label = new JLabel(columns[i]);
            label.setFont(Theme.font(13, Font.BOLD));
            label.setForeground(Theme.TEXT);

            String initialText = "";
            if (existingValues != null && i < existingValues.length && existingValues[i] != null) {
                initialText = existingValues[i];
            }

            JTextField field = new JTextField(initialText, 20);
            field.setFont(Theme.font(13, Font.PLAIN));
            field.setPreferredSize(new Dimension(240, 34));
            field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Theme.LINE),
                    BorderFactory.createEmptyBorder(5, 8, 5, 8)
            ));

            textFields[i] = field;
            formPanel.add(label);
            formPanel.add(field);
        }

        // Bottom buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 12));
        buttonPanel.setBackground(Theme.WHITE);

        JButton cancelButton = Theme.button("Cancel", false);
        JButton saveButton = Theme.button("Save", true);

        cancelButton.addActionListener(e -> dispose());

        saveButton.addActionListener(e -> {
            // Check if the first field (ID / Code) is empty
            if (textFields[0].getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Please fill in the " + columns[0] + " field.",
                        "Missing Information",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            savedValues = new String[columns.length];
            for (int i = 0; i < columns.length; i++) {
                savedValues[i] = textFields[i].getText().trim();
            }
            dispose();
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setMinimumSize(new Dimension(480, getHeight()));
        setLocationRelativeTo(parent);
    }

    public static String[] show(Component parent, String title, String[] columns, String[] existingValues) {
        Window window = SwingUtilities.getWindowAncestor(parent);
        RecordDialog dialog = new RecordDialog(window, title, columns, existingValues);
        dialog.setVisible(true);
        return dialog.savedValues;
    }
}
