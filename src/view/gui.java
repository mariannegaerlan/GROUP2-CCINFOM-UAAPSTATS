package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/** Draft dashboard*/
public class gui extends JFrame {
    public gui() {
        setTitle("UAAP Stats");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 700);
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Theme.DARK);
        JLabel appName = new JLabel("   UAAP Stats");
        appName.setForeground(Theme.WHITE);
        appName.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18));
        JLabel user = new JLabel("User   ");
        user.setForeground(Theme.WHITE);
        header.add(appName, BorderLayout.WEST);
        header.add(user, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        JPanel body = new JPanel(new BorderLayout(10, 10));
        JLabel welcome = new JLabel("Welcome Back", SwingConstants.LEFT);
        welcome.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 36));
        welcome.setBorder(javax.swing.BorderFactory.createEmptyBorder(18, 20, 10, 20));
        body.add(welcome, BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Players", new PlayerPanel());
        tabs.addTab("Coaches", new CoachPanel());
        tabs.addTab("Matches", new MatchPanel());
        tabs.addTab("Teams", new TeamPanel());
        body.add(tabs, BorderLayout.CENTER);
        add(body, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new gui().setVisible(true));
    }
}
