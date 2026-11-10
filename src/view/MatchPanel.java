package view;
// draft or placeholder lang 
public class MatchPanel extends Theme.BaseTablePanel {
    public MatchPanel() {
        super("Matches",
                new String[] {"Match ID", "Match Date", "Home Team", "Away Team", "Home Score",
                        "Away Score", "Top Scorer ID", "Venue", "Round", "Season"},
                "Round",
                new String[][] {
                        {"1", "2026-10-31", "ADMU", "DLSU", "73", "76", "13", "Smart Araneta Coliseum", "Round 2", "89"},
                        {"2", "2026-10-31", "NU", "UST", "75", "71", "30", "Smart Araneta Coliseum", "Round 2", "89"},
                        {"3", "2026-10-28", "ADU", "FEU", "66", "73", "25", "Smart Araneta Coliseum", "Round 2", "89"},
                        {"4", "2026-10-28", "NU", "ADMU", "65", "69", "1", "Smart Araneta Coliseum", "Round 2", "89"},
                        {"5", "2026-10-25", "UST", "DLSU", "80", "92", "1", "Smart Araneta Coliseum", "Round 2", "89"}
                });
    }
}
