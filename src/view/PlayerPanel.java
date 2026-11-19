package view;
// draft or placeholder lang
import java.util.ArrayList; 
import javax.swing.*;
import java.util.Collections; 
import javax.swing.JPopupMenu;

public class PlayerPanel extends Theme.BaseTablePanel {
    public PlayerPanel() {
        super("Players",
                new String[] {"Player ID", "Jersey No.", "School Code", "Player Name", "Age",
                        "Points", "Rebounds", "Assists", "Blocks", "Steals", "Minutes Played", "Status"},
                new String[][] {
                        {"1", "9", "DLSU", "Luis Pablo", "25", "10", "2", "5", "3", "4", "00:34:10", "Active"},
                        {"2", "11", "DLSU", "Jacob Cortez", "24", "13", "4", "5", "1", "3", "00:28:39", "Active"}
                });
    }

    protected void addCustomMenuItems(JPopupMenu menu){
        menu.addSeparator();

        JMenuItem injuredPlayer = new JMenuItem("Injured Players");
        injuredPlayer.addActionListener(e->{
                int statusCol = getColumnIndex("Status"); 
                setFilterRule(row->"Injured".equalsIgnoreCase(row[statusCol]));
        });
        menu.add(injuredPlayer);

    }
}
