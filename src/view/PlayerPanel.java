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

        JMenuItem activePlayer = new JMenuItem("Active Players");
        activePlayer.addActionListener(e->{
                int statusCol = getColumnIndex("Status");
                setFilterRule(row->"Active".equalsIgnoreCase(row[statusCol]));
        });
        menu.add(activePlayer);

        JMenuItem highScorers = new JMenuItem("High Scorers (10+ pts)");
        highScorers.addActionListener(e->{
                int pointsCol = getColumnIndex("Points");
                setFilterRule(row->{
                        try{
                                int points = Integer.parseInt(row[pointsCol].trim());
                                return points >= 10;
                        }
                        catch(NumberFormatException ex){
                                return false;
                        }
                });
        });
        menu.add(highScorers);

        JMenuItem topPlayers = new JMenuItem("MVP Leaderboard");
        topPlayers.addActionListener(e->{
                int[] statCols = {getColumnIndex("Points"), getColumnIndex("Rebounds"),
                        getColumnIndex("Assists"), getColumnIndex("Blocks")};

                setFilterRule(row->true, (row1, row2)->{
                        for(int col : statCols){
                                int stat1 = getStat(row1[col]);
                                int stat2 = getStat(row2[col]);
                                if(stat1 != stat2){
                                        return Integer.compare(stat2, stat1);
                                }
                        }
                        return 0;
                });
        });
        menu.add(topPlayers);
    }

    private int getStat(String value){
        try{
                return Integer.parseInt(value.trim());
        }
        catch(NumberFormatException ex){
                return 0;
        }
    }
}
