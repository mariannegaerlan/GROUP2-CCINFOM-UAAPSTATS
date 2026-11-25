package view;

import java.util.ArrayList; 
import javax.swing.*;
import java.util.Collections; 
import javax.swing.JPopupMenu;

import model.match;

// draft or placeholder lang 
public class MatchPanel extends Theme.BaseTablePanel {
    public MatchPanel() {
        super("Matches",
                new String[] {"Match ID", "Match Date", "Home Team", "Away Team", "Home Score",
                        "Away Score", "Top Scorer ID", "Venue", "Round", "Season"},
                new String[][] {
                        {"1", "2026-10-31", "ADMU", "DLSU", "73", "76", "13", "Smart Araneta Coliseum", "Round 2", "89"},
                        {"2", "2026-10-31", "NU", "UST", "75", "71", "30", "Smart Araneta Coliseum", "Round 2", "89"},
                        {"3", "2026-10-28", "ADU", "FEU", "66", "73", "25", "Smart Araneta Coliseum", "Round 2", "89"},
                        {"4", "2026-10-28", "NU", "ADMU", "65", "69", "1", "Smart Araneta Coliseum", "Round 2", "89"},
                        {"5", "2026-10-25", "UST", "DLSU", "80", "92", "1", "Smart Araneta Coliseum", "Round 2", "89"}
                });
    }

    protected void addCustomMenuItems(JPopupMenu menu){
        menu.addSeparator();

        JMenuItem matchVenue = new JMenuItem("MOA Matches"); 
        matchVenue.addActionListener(e->{
                int venueCol = getColumnIndex("Venue"); 
                setFilterRule(row->row[venueCol] != null && row[venueCol].toLowerCase().contains("mall of asia"));
        });
        menu.add(matchVenue);

        JMenuItem aranetaMatches = new JMenuItem("Araneta Matches");
        aranetaMatches.addActionListener(e->{
                int venueCol = getColumnIndex("Venue");
                setFilterRule(row->row[venueCol] != null && row[venueCol].toLowerCase().contains("araneta"));
        });
        menu.add(aranetaMatches);

        JMenuItem homeWins = new JMenuItem("Home Team Wins");
        homeWins.addActionListener(e->{
                int homeCol = getColumnIndex("Home Score");
                int awayCol = getColumnIndex("Away Score");
                setFilterRule(row->{
                        try{
                                int homeScore = Integer.parseInt(row[homeCol].trim());
                                int awayScore = Integer.parseInt(row[awayCol].trim());
                                return homeScore > awayScore;
                        }
                        catch(NumberFormatException ex){
                                return false;
                        }
                });
        });
        menu.add(homeWins);
    }
}
