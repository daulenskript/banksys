package view;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;
import model.Bank;

public class BanksView extends JPanel {
    private final DefaultTableModel model;
    private final JTable table;

    public BanksView() {
        // Use a clean BorderLayout with more padding
        setLayout(new BorderLayout(15, 15));

        // Elegant panel background
        setBackground(new Color(240, 242, 245));

        // Create a header panel with a dark theme
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(new Color(51, 65, 92));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));

        JLabel titleLabel = new JLabel("Banking Institutions");
        titleLabel.setFont(new Font("Montserrat", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        // Filter dropdown in header
        JComboBox<String> filterBox = new JComboBox<>(new String[]{"All Banks", "Commercial", "Central", "Investment"});
        filterBox.setBackground(Color.WHITE);
        filterBox.setPreferredSize(new Dimension(150, 30));
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        filterPanel.setBackground(new Color(51, 65, 92));
        JLabel filterLabel = new JLabel("Filter: ");
        filterLabel.setForeground(Color.WHITE);
        filterPanel.add(filterLabel);
        filterPanel.add(filterBox);
        headerPanel.add(filterPanel, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // Table setup with modern styling
        String[] columns = {"Name", "Code", "Address", "Balance"};
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
        table.setSelectionBackground(new Color(230, 240, 250));
        table.setSelectionForeground(new Color(20, 20, 20));
        table.setGridColor(new Color(230, 230, 230));
        table.setIntercellSpacing(new Dimension(10, 5));
        table.setShowVerticalLines(false);

        // Header styling
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Open Sans", Font.BOLD, 13));
        header.setBackground(new Color(51, 65, 92));
        header.setForeground(Color.WHITE);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(41, 55, 82)));
        header.setPreferredSize(new Dimension(header.getWidth(), 40));

        // Custom cell renderer
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? new Color(252, 252, 252) : new Color(245, 246, 248));
                }
                setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
                // Right-align balance column
                setHorizontalAlignment(column == 3 ? JLabel.RIGHT : JLabel.LEFT);
                return c;
            }
        });

        // Scroll pane with modern border
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 15, 15, 15),
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                        BorderFactory.createEmptyBorder(0, 0, 0, 0)
                )
        ));
        scrollPane.getViewport().setBackground(Color.WHITE);

        // Statistics panel at the bottom
        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        statsPanel.setBackground(new Color(240, 242, 245));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        JLabel totalBanksLabel = new JLabel("Total Banks: 18");
        totalBanksLabel.setFont(new Font("Open Sans", Font.BOLD, 13));

        JLabel totalBalanceLabel = new JLabel("Combined Balance: $4,732,156,890");
        totalBalanceLabel.setFont(new Font("Open Sans", Font.BOLD, 13));

        statsPanel.add(totalBanksLabel);
        statsPanel.add(totalBalanceLabel);

        // Action buttons panel at the right bottom
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionPanel.setBackground(new Color(240, 242, 245));

        JButton detailsButton = createButton("View Details", new Color(70, 130, 180));
        JButton exportButton = createButton("Export Data", new Color(58, 83, 155));

        actionPanel.add(detailsButton);
        actionPanel.add(exportButton);

        // Combine stats and action panels
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(new Color(240, 242, 245));
        bottomPanel.add(statsPanel, BorderLayout.WEST);
        bottomPanel.add(actionPanel, BorderLayout.EAST);

        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JButton createButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Open Sans", Font.BOLD, 12));
        button.setPreferredSize(new Dimension(120, 32));
        return button;
    }

    public void updateTable(List<Bank> banks) {
        model.setRowCount(0);
        for (Bank bank : banks) {
            model.addRow(bank.toArray());
        }
    }
}