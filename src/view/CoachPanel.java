package view;
// draft or placeholder lang 
public class CoachPanel extends Theme.BaseTablePanel {
    public CoachPanel() {
        super("Coaches",
                new String[] {"Coach ID", "School Code", "Coach Name", "Contract Start", "Contract End",
                        "Monthly Salary", "Career Championships", "Status"},
                "Status",
                new String[][] {
                        {"1", "ADU", "Nash Racela", "2021-12-01", "Ongoing", "₱50,000", "0", "Active"},
                        {"2", "ADMU", "Louie Alas", "2026-07-10", "Ongoing", "₱70,000", "0", "Active"},
                        {"3", "DLSU", "Topex Robinson", "2023-01-19", "Ongoing", "₱100,000", "2", "Active"},
                        {"4", "FEU", "Sean Chambers", "2024-03-18", "Ongoing", "₱60,000", "0", "Active"},
                        {"5", "NU", "Jeff Napa", "2020-10-09", "Ongoing", "₱80,000", "0", "Active"},
                        {"6", "UE", "Paulo Hubalde", "2026-09-29", "Ongoing", "₱40,000", "0", "Active"},
                        {"7", "UPD", "Goldwin Monteverde", "2021-08-13", "Ongoing", "₱70,000", "2", "Active"},
                        {"8", "UST", "Pido Jarencio", "2006-02-03", "Ongoing", "₱90,000", "1", "Active"},
                        {"9", "ADMU", "Thomas Baldwin", "2020-03-10", "Ongoing", "₱90,000", "2", "Suspended"},

                });
    }
}
