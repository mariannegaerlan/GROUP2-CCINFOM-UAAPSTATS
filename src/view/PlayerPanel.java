package view;
// draft or placeholder lang 
public class PlayerPanel extends Theme.BaseTablePanel {
    public PlayerPanel() {
        super("Players",
                new String[] {"Player ID", "Jersey No.", "School Code", "Player Name", "Age",
                        "Points", "Rebounds", "Assists", "Blocks", "Steals", "Minutes Played", "Status"},
                "Status",
                new String[][] {
                        {"1", "9", "DLSU", "Luis Pablo", "25", "10", "2", "5", "3", "4", "00:34:10", "Active"},
                        {"2", "11", "DLSU", "Jacob Cortez", "24", "13", "4", "5", "1", "3", "00:28:39", "Active"}
                });
    }
}
