package view;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;
import model.Client;

public class ClientsView extends JPanel {
    private final DefaultTableModel model;
    private final JTable table;

    public ClientsView() {
        // Use a clean BorderLayout with more padding
        setLayout(new BorderLayout(15, 15));

        // Modern gradient panel background
        setBackground(new Color(240, 242, 245));

        // Create a header panel with a gradient
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(new Color(60, 90, 153));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));

        JLabel titleLabel = new JLabel("Client Management");
        titleLabel.setFont(new Font("Montserrat", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        // Search field in header
        JTextField searchField = new JTextField(15);
        searchField.setBorder(new CompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(new Color(60, 90, 153));
        JLabel searchIcon = new JLabel("🔍");
        searchIcon.setForeground(Color.WHITE);
        searchPanel.add(searchIcon);
        searchPanel.add(searchField);
        headerPanel.add(searchPanel, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // Table setup with modern styling
        String[] columns = {"Name", "Last Name", "Date of Birth", "INN", "Phone", "Bank Account", "Balance"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };

        table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setRowHeight(35);
        table.setFont(new Font("Open Sans", Font.PLAIN, 13));
        table.setSelectionBackground(new Color(235, 245, 251));
        table.setSelectionForeground(new Color(20, 20, 20));
        table.setGridColor(new Color(230, 230, 230));
        table.setIntercellSpacing(new Dimension(10, 5));
        table.setShowVerticalLines(false);

        // Header styling
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Open Sans", Font.BOLD, 13));
        header.setBackground(new Color(240, 240, 240));
        header.setForeground(new Color(60, 60, 60));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(180, 180, 180)));
        header.setPreferredSize(new Dimension(header.getWidth(), 40));

        // Custom cell renderer
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? new Color(252, 252, 252) : new Color(245, 248, 250));
                }
                setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
                setHorizontalAlignment(column == 6 ? JLabel.RIGHT : JLabel.LEFT); // Align balance to the right
                return c;
            }
        });

        // Scroll pane with shadow border
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 15, 15, 15),
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                        BorderFactory.createEmptyBorder(0, 0, 0, 0)
                )
        ));
        scrollPane.getViewport().setBackground(Color.WHITE);

        // Add action buttons panel at the bottom
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionPanel.setBackground(new Color(240, 242, 245));

        JButton addButton = createButton("Add Client", new Color(92, 184, 92));
        JButton editButton = createButton("Edit", new Color(91, 192, 222));
        JButton deleteButton = createButton("Delete", new Color(217, 83, 79));

        actionPanel.add(addButton);
        actionPanel.add(editButton);
        actionPanel.add(deleteButton);

        add(scrollPane, BorderLayout.CENTER);
        add(actionPanel, BorderLayout.SOUTH);
    }

    private JButton createButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Open Sans", Font.BOLD, 12));
        button.setPreferredSize(new Dimension(110, 32));
        return button;
    }

    public void updateTable(List<Client> clients) {
        model.setRowCount(0);
        for (Client client : clients) {
            model.addRow(client.toArray());
        }
    }
}