package view;

import java.awt.*;
import javax.swing.*;

public class gui extends JFrame {
    private CardLayout cardLayout = new CardLayout();
    private JPanel pageCards = new JPanel(cardLayout);
    private JButton[] navButtons;
    private String[] pages = {"Players", "Coaches", "Matches", "Teams"};
    private String activePage = "Teams";

    public gui() {
        super("UAAP Stats | Records Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1050, 720));
        setSize(1280, 840);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(Theme.WHITE);
        root.add(buildHeader(), BorderLayout.NORTH);
        root.add(buildMainContent(), BorderLayout.CENTER);
        setContentPane(root);
    }

    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Theme.HEADER);
        header.setBorder(BorderFactory.createEmptyBorder(14, 42, 14, 42));
        header.setPreferredSize(new Dimension(0, 78));

        // Left: Logo and App Name
        JPanel brand = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 0));
        brand.setOpaque(false);

        Theme.CircleBadge logo = new Theme.CircleBadge("U", new Color(135, 135, 135), 48);
        JLabel appName = new JLabel("UAAP STATS");
        appName.setFont(Theme.font(17, Font.BOLD));
        appName.setForeground(Color.WHITE);

        JLabel subName = new JLabel("RECORDS MANAGEMENT");
        subName.setFont(Theme.font(9, Font.BOLD));
        subName.setForeground(new Color(210, 210, 210));

        JPanel brandText = new JPanel(new BorderLayout(0, 2));
        brandText.setOpaque(false);
        brandText.add(appName, BorderLayout.CENTER);
        brandText.add(subName, BorderLayout.SOUTH);

        brand.add(logo);
        brand.add(brandText);
        header.add(brand, BorderLayout.WEST);

        // Right: Profile badge and username
        JPanel profile = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        profile.setOpaque(false);

        JLabel name = new JLabel("Player");
        name.setFont(Theme.font(14, Font.BOLD));
        name.setForeground(Color.WHITE);

        profile.add(new Theme.CircleBadge(":>", new Color(135, 135, 135), 48));
        profile.add(name);
        header.add(profile, BorderLayout.EAST);

        return header;
    }

    private JPanel buildMainContent() {
        JPanel main = new JPanel(new BorderLayout(0, 22));
        main.setBackground(Theme.WHITE);
        main.setBorder(BorderFactory.createEmptyBorder(34, 64, 28, 64));

        JLabel welcome = new JLabel("Welcome Back, Player");
        welcome.setFont(Theme.font(42, Font.PLAIN));
        welcome.setForeground(Color.BLACK);
        main.add(welcome, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout(0, 22));
        center.setBackground(Theme.WHITE);
        center.add(buildNavigation(), BorderLayout.NORTH);

        pageCards.setBackground(Theme.WHITE);
        pageCards.add(new PlayerPanel(), "Players");
        pageCards.add(new CoachPanel(), "Coaches");
        pageCards.add(new MatchPanel(), "Matches");
        pageCards.add(new TeamPanel(), "Teams");

        cardLayout.show(pageCards, activePage);
        center.add(pageCards, BorderLayout.CENTER);

        main.add(center, BorderLayout.CENTER);
        return main;
    }

    private JPanel buildNavigation() {
        Theme.RoundedNavBar nav = new Theme.RoundedNavBar();
        nav.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        nav.setPreferredSize(new Dimension(500, 54));

        navButtons = new JButton[pages.length];

        for (int i = 0; i < pages.length; i++) {
            String page = pages[i];
            JButton button = new JButton(page);
            button.setFont(Theme.font(14, Font.PLAIN));
            button.setForeground(Color.WHITE);
            button.setContentAreaFilled(false);
            button.setFocusPainted(false);
            button.setPreferredSize(new Dimension(112, 54));
            button.addActionListener(e -> showPage(page));

            navButtons[i] = button;
            nav.add(button);
        }

        updateNavigationStyle();
        return nav;
    }

    private void showPage(String page) {
        activePage = page;
        cardLayout.show(pageCards, page);
        updateNavigationStyle();
    }

    private void updateNavigationStyle() {
        for (int i = 0; i < pages.length; i++) {
            JButton button = navButtons[i];
            boolean selected = pages[i].equals(activePage);
            button.setFont(Theme.font(14, selected ? Font.BOLD : Font.PLAIN));
            button.setForeground(selected ? Color.WHITE : new Color(245, 245, 245));
            button.setBorder(selected
                    ? BorderFactory.createMatteBorder(0, 0, 3, 0, new Color(65, 65, 65))
                    : BorderFactory.createEmptyBorder(0, 0, 3, 0));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new gui().setVisible(true);
        });
    }
}
