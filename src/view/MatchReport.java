package view;

import java.util.ArrayList;
import javax.swing.*;
import java.util.Collections;

// draft or placeholder lang
public class MatchReport extends Theme.BaseTablePanel {
    public MatchReport() {
        super("Matches",
                new String[] {"Match Date", "Home Team", "Away Team", "Home Score", "Away Score", "Venue", "Round", "Number of Matches per Round"},
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

            setFilterRule(row->row[venueCol] != null &&
                row[venueCol].toLowerCase().contains("mall of asia"));
        });
        menu.add(matchVenue);

        JMenuItem commonPlayers = new JMenuItem("Common Top Scorers");
        commonPlayers.addActionListener(e->{
            int scorerCol = getColumnIndex("Top Scorer ID");

            setFilterRule(row->{
                String scorer = row[scorerCol].trim();

                if(scorer.isEmpty() || scorer.equals("0")){
                    return false;
                }

                int count = 0;

                for(String[] matchRow : rows){
                    if(scorer.equals(matchRow[scorerCol].trim())){
                        count++;
                    }
                }

                return count > 3;
            });
        });
        menu.add(commonPlayers);
    }
}
