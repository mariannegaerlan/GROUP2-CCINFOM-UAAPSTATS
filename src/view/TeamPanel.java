package view;
// draft or placeholder lang 
public class TeamPanel extends Theme.BaseTablePanel {
    public TeamPanel() {
        super("Teams",
                new String[] {"School Code", "Team Name", "Year", "Coach ID", "Team Championships"},
                "School Code",
                new String[][] {
                        {"ADU", "Soaring Falcons", "1954", "1011", "1"},
                        {"ADMU", "Blue Eagles", "1974", "1012", "12"},
                        {"DLSU", "Green Archers", "1986", "1013", "11"},
                        {"FEU", "Tamaraws", "1938", "1014", "20"},
                        {"NU", "Bulldogs", "1938", "1015", "2"},
                        {"UE", "Red Warriors", "1954", "1016", "18"},
                        {"UPD", "Fighting Maroons", "1938", "1017", "4"},
                        {"UST", "Growling Tigers", "1938", "1018", "18"}
                });
    }
}
