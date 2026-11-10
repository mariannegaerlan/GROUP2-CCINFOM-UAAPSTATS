package view;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.*;

public class Theme {

    // Shared colors
    public static final Color WHITE = Color.WHITE;
    public static final Color HEADER = new Color(67, 67, 67);
    public static final Color TEXT = new Color(35, 38, 42);
    public static final Color MUTED = new Color(112, 119, 128);
    public static final Color LINE = new Color(225, 228, 232);
    public static final Color SOFT = new Color(239, 241, 243);
    public static final Color BUTTON = new Color(165, 165, 165);
    public static final Color DANGER = new Color(174, 70, 70);
    public static final Color TABLE_HEADER = new Color(242, 243, 245);
    public static final Color TABLE_ALT = new Color(250, 250, 251);

    public static Font font(float size, int style) {
        return new Font("Segoe UI", style, (int) size);
    }

    public static JButton button(String text, boolean primary) {
        JButton btn = new JButton(text);
        styleButton(btn, primary);
        return btn;
    }

    public static void styleButton(JButton btn, boolean primary) {
        btn.setFont(font(13, Font.PLAIN));
        btn.setForeground(primary ? WHITE : TEXT);
        btn.setBackground(primary ? BUTTON : SOFT);
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setBorder(BorderFactory.createEmptyBorder(9, 15, 9, 15));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    // Gray rounded navigation bar
    public static class RoundedNavBar extends JPanel {
        public RoundedNavBar() {
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(169, 169, 169));
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
            super.paintComponent(g);
        }
    }

    // Circle icon for logo and profile picture
    public static class CircleBadge extends JLabel {
        private Color fillColor;

        public CircleBadge(String text, Color fillColor, int size) {
            super(text, SwingConstants.CENTER);
            this.fillColor = fillColor;
            setFont(font(16, Font.BOLD));
            setForeground(WHITE);
            setPreferredSize(new Dimension(size, size));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(fillColor);
            g2.fillOval(0, 0, getWidth(), getHeight());
            super.paintComponent(g);
        }
    }

    // Reusable panel for Players, Coaches, Matches, and Teams tabs
    public static class BaseTablePanel extends JPanel {
        private static final int PAGE_SIZE = 10;

        private String entityName;
        private String[] columns;
        private String filterColumnName;

        private ArrayList<String[]> rows = new ArrayList<>();
        private ArrayList<String[]> visibleRows = new ArrayList<>();

        private JTextField searchField = new JTextField("Search bar");
        private JLabel countLabel = new JLabel();
        private JLabel pageLabel = new JLabel();
        private JButton previousButton = button("Previous", true);
        private JButton nextButton = button("Next", true);
        private JButton filterButton = button("Filter by  ▾", true);

        private DefaultTableModel tableModel;
        private JTable table;

        private String selectedFilter = "All";
        private int page = 0;

        public BaseTablePanel(String entityName, String[] columns, String filterColumnName, String[][] sampleRows) {
            this.entityName = entityName;
            this.columns = columns;
            this.filterColumnName = filterColumnName;

            for (int i = 0; i < sampleRows.length; i++) {
                this.rows.add(sampleRows[i]);
            }

            setLayout(new BorderLayout(0, 18));
            setBackground(WHITE);
            setBorder(BorderFactory.createEmptyBorder(28, 0, 8, 0));

            // 1. Top Toolbar
            add(buildToolbar(), BorderLayout.NORTH);

            // 2. Table Setup using DefaultTableModel
            String[] tableCols = new String[columns.length + 1];
            for (int i = 0; i < columns.length; i++) {
                tableCols[i] = columns[i];
            }
            tableCols[columns.length] = "Actions";

            tableModel = new DefaultTableModel(tableCols, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return column == columns.length; // Only Actions column is clickable
                }
            };

            table = new JTable(tableModel);
            styleTable();

            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBorder(BorderFactory.createLineBorder(LINE));
            scrollPane.getViewport().setBackground(WHITE);
            scrollPane.setPreferredSize(new Dimension(900, 360));
            add(scrollPane, BorderLayout.CENTER);

            // 3. Bottom Pagination Footer
            add(buildFooter(), BorderLayout.SOUTH);

            applyFilters();
        }

        private JPanel buildToolbar() {
            JPanel toolbar = new JPanel(new BorderLayout(18, 0));
            toolbar.setBackground(WHITE);

            // Left: Title + Add new button
            JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            left.setBackground(WHITE);

            JLabel title = new JLabel(entityName);
            title.setFont(font(25, Font.BOLD));
            title.setForeground(TEXT);
            left.add(title);

            JButton addButton = button("Add new", true);
            addButton.setFont(font(14, Font.PLAIN));
            addButton.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
            addButton.addActionListener(e -> addRow());

            left.add(Box.createHorizontalStrut(22));
            left.add(addButton);
            toolbar.add(left, BorderLayout.WEST);

            // Right: Search bar + Filter by button
            JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
            right.setBackground(WHITE);

            searchField.setFont(font(13, Font.PLAIN));
            searchField.setForeground(MUTED);
            searchField.setPreferredSize(new Dimension(300, 40));
            searchField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(LINE),
                    BorderFactory.createEmptyBorder(7, 12, 7, 12)
            ));

            // Placeholder behavior on click
            searchField.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (searchField.getText().equals("Search bar")) {
                        searchField.setText("");
                        searchField.setForeground(TEXT);
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (searchField.getText().trim().isEmpty()) {
                        searchField.setText("Search bar");
                        searchField.setForeground(MUTED);
                    }
                }
            });

            // Filter rows when typing
            searchField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    page = 0;
                    applyFilters();
                }
            });

            filterButton.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
            filterButton.addActionListener(e -> showFilterMenu());

            right.add(searchField);
            right.add(filterButton);
            toolbar.add(right, BorderLayout.EAST);

            return toolbar;
        }

        private JPanel buildFooter() {
            JPanel footer = new JPanel(new BorderLayout());
            footer.setBackground(WHITE);

            countLabel.setFont(font(13, Font.PLAIN));
            countLabel.setForeground(TEXT);
            footer.add(countLabel, BorderLayout.WEST);

            JPanel pagination = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
            pagination.setBackground(WHITE);

            pageLabel.setFont(font(12, Font.PLAIN));
            pageLabel.setForeground(MUTED);

            previousButton.setBorder(BorderFactory.createEmptyBorder(9, 16, 9, 16));
            nextButton.setBorder(BorderFactory.createEmptyBorder(9, 16, 9, 16));

            previousButton.addActionListener(e -> {
                if (page > 0) {
                    page--;
                    refreshTable();
                }
            });

            nextButton.addActionListener(e -> {
                if ((page + 1) * PAGE_SIZE < visibleRows.size()) {
                    page++;
                    refreshTable();
                }
            });

            pagination.add(pageLabel);
            pagination.add(previousButton);
            pagination.add(nextButton);
            footer.add(pagination, BorderLayout.EAST);

            return footer;
        }

        private void styleTable() {
            table.setFont(font(13, Font.PLAIN));
            table.setForeground(TEXT);
            table.setBackground(WHITE);
            table.setRowHeight(45);
            table.setFillsViewportHeight(true);
            table.setShowGrid(false);
            table.setIntercellSpacing(new Dimension(0, 0));
            table.setSelectionBackground(new Color(238, 241, 244));
            table.setSelectionForeground(TEXT);

            table.getTableHeader().setFont(font(13, Font.BOLD));
            table.getTableHeader().setForeground(new Color(90, 96, 103));
            table.getTableHeader().setBackground(TABLE_HEADER);
            table.getTableHeader().setPreferredSize(new Dimension(0, 46));
            table.getTableHeader().setReorderingAllowed(false);

            DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable t, Object value,
                                                               boolean selected, boolean focus, int row, int column) {
                    Component c = super.getTableCellRendererComponent(t, value, selected, focus, row, column);
                    setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 8));
                    if (!selected) {
                        c.setBackground(row % 2 == 0 ? WHITE : TABLE_ALT);
                    }
                    return c;
                }
            };

            for (int i = 0; i < columns.length; i++) {
                table.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
            }

            TableColumn actionsCol = table.getColumnModel().getColumn(columns.length);
            actionsCol.setPreferredWidth(120);
            actionsCol.setMaxWidth(140);
            actionsCol.setCellRenderer(new ActionRenderer());
            actionsCol.setCellEditor(new ActionEditor());
        }

        private void showFilterMenu() {
            JPopupMenu menu = new JPopupMenu();

            JMenuItem allItem = new JMenuItem("All");
            allItem.addActionListener(e -> {
                selectedFilter = "All";
                page = 0;
                applyFilters();
            });
            menu.add(allItem);

            // Find the column index for filtering
            int filterCol = -1;
            for (int i = 0; i < columns.length; i++) {
                if (columns[i].equalsIgnoreCase(filterColumnName)) {
                    filterCol = i;
                    break;
                }
            }

            if (filterCol >= 0) {
                ArrayList<String> uniqueValues = new ArrayList<>();
                for (int i = 0; i < rows.size(); i++) {
                    String val = rows.get(i)[filterCol];
                    if (val != null && !val.isEmpty() && !uniqueValues.contains(val)) {
                        uniqueValues.add(val);
                    }
                }

                if (!uniqueValues.isEmpty()) {
                    menu.addSeparator();
                }

                for (int i = 0; i < uniqueValues.size(); i++) {
                    String value = uniqueValues.get(i);
                    JMenuItem item = new JMenuItem(value);
                    item.addActionListener(e -> {
                        selectedFilter = value;
                        page = 0;
                        applyFilters();
                    });
                    menu.add(item);
                }
            }

            menu.show(filterButton, 0, filterButton.getHeight());
        }

        private void applyFilters() {
            visibleRows.clear();

            String query = searchField.getText().trim().toLowerCase();
            if (query.equals("search bar")) {
                query = "";
            }

            int filterCol = -1;
            for (int i = 0; i < columns.length; i++) {
                if (columns[i].equalsIgnoreCase(filterColumnName)) {
                    filterCol = i;
                    break;
                }
            }

            for (int i = 0; i < rows.size(); i++) {
                String[] row = rows.get(i);

                // Check search text
                boolean matchesSearch = query.isEmpty();
                if (!matchesSearch) {
                    for (int j = 0; j < row.length; j++) {
                        if (row[j] != null && row[j].toLowerCase().contains(query)) {
                            matchesSearch = true;
                            break;
                        }
                    }
                }

                // Check filter selection
                boolean matchesFilter = selectedFilter.equals("All");
                if (!matchesFilter && filterCol >= 0 && filterCol < row.length) {
                    matchesFilter = selectedFilter.equals(row[filterCol]);
                }

                if (matchesSearch && matchesFilter) {
                    visibleRows.add(row);
                }
            }

            refreshTable();
        }

        private void refreshTable() {
            tableModel.setRowCount(0);

            int start = page * PAGE_SIZE;
            int end = Math.min(start + PAGE_SIZE, visibleRows.size());

            for (int i = start; i < end; i++) {
                String[] data = visibleRows.get(i);
                Object[] rowWithActions = new Object[columns.length + 1];
                for (int j = 0; j < columns.length; j++) {
                    rowWithActions[j] = data[j];
                }
                rowWithActions[columns.length] = "";
                tableModel.addRow(rowWithActions);
            }

            if (visibleRows.isEmpty()) {
                countLabel.setText("Showing 0 results");
                pageLabel.setText("Page 1 of 1");
            } else {
                int totalPages = (visibleRows.size() + PAGE_SIZE - 1) / PAGE_SIZE;
                countLabel.setText("Showing " + (start + 1) + "-" + end + " out of " + visibleRows.size() + " entries");
                pageLabel.setText("Page " + (page + 1) + " of " + totalPages);
            }

            previousButton.setEnabled(page > 0);
            nextButton.setEnabled(end < visibleRows.size());
        }

        private void addRow() {
            String[] values = RecordDialog.show(this, "Add New " + getSingularName(), columns, null);
            if (values != null) {
                rows.add(values);
                applyFilters();
            }
        }

        private void editRow(int visibleIndex) {
            if (visibleIndex < 0 || visibleIndex >= visibleRows.size()) return;

            String[] current = visibleRows.get(visibleIndex);
            String[] updated = RecordDialog.show(this, "Edit " + getSingularName(), columns, current);

            if (updated != null) {
                int mainIndex = rows.indexOf(current);
                if (mainIndex >= 0) {
                    rows.set(mainIndex, updated);
                }
                applyFilters();
            }
        }

        private void deleteRow(int visibleIndex) {
            if (visibleIndex < 0 || visibleIndex >= visibleRows.size()) return;

            String[] target = visibleRows.get(visibleIndex);
            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Delete this " + getSingularName().toLowerCase() + " record?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {
                rows.remove(target);
                applyFilters();
            }
        }

        private String getSingularName() {
            if (entityName.equals("Players")) return "Player";
            if (entityName.equals("Coaches")) return "Coach";
            if (entityName.equals("Matches")) return "Match";
            if (entityName.equals("Teams")) return "Team";
            return entityName;
        }

        // Renders the Edit and Del buttons inside the table cell
        private class ActionRenderer extends JPanel implements TableCellRenderer {
            private JButton editBtn = new JButton("Edit");
            private JButton delBtn = new JButton("Del");

            public ActionRenderer() {
                setLayout(new FlowLayout(FlowLayout.CENTER, 5, 6));
                styleSmallButton(editBtn, false);
                styleSmallButton(delBtn, true);
                add(editBtn);
                add(delBtn);
            }

            @Override
            public Component getTableCellRendererComponent(JTable t, Object val,
                                                           boolean isSelected, boolean hasFocus, int row, int col) {
                setBackground(isSelected ? new Color(238, 241, 244) : (row % 2 == 0 ? WHITE : TABLE_ALT));
                return this;
            }
        }

        // Handles clicking Edit and Del inside the table cell
        private class ActionEditor extends AbstractCellEditor implements TableCellEditor {
            private JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 6));
            private JButton editBtn = new JButton("Edit");
            private JButton delBtn = new JButton("Del");
            private int currentRowIndex;

            public ActionEditor() {
                styleSmallButton(editBtn, false);
                styleSmallButton(delBtn, true);
                panel.add(editBtn);
                panel.add(delBtn);

                editBtn.addActionListener(e -> {
                    fireEditingStopped();
                    editRow(currentRowIndex);
                });

                delBtn.addActionListener(e -> {
                    fireEditingStopped();
                    deleteRow(currentRowIndex);
                });
            }

            @Override
            public Component getTableCellEditorComponent(JTable t, Object val,
                                                         boolean isSelected, int row, int col) {
                currentRowIndex = (page * PAGE_SIZE) + row;
                panel.setBackground(new Color(238, 241, 244));
                return panel;
            }

            @Override
            public Object getCellEditorValue() {
                return "";
            }
        }

        private void styleSmallButton(JButton btn, boolean isDelete) {
            btn.setFont(font(11, Font.BOLD));
            btn.setForeground(isDelete ? DANGER : TEXT);
            btn.setBackground(isDelete ? new Color(250, 239, 239) : SOFT);
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createEmptyBorder(5, 7, 5, 7));
            btn.setOpaque(true);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    }
}
