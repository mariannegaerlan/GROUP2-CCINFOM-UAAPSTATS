package view;

import javax.swing.*;
import java.util.Collections;
import java.util.Arrays; 

// draft or placeholder lang 
public class TeamReport extends Theme.BaseTablePanel {
    public TeamReport() {
        super("Teams",
                new String[] {"Total Matches Played, Wins, Losses, Win Rate"},
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
      protected void addCustomMenuItems(JPopupMenu menu){
        menu.addSeparator();

        //view newest schools that entered the league after 1938(non founders)
        JMenuItem nonFounder = new JMenuItem("XD"); 
        nonFounder.addActionListener(e->{
                int yearCol = getColumnIndex("Year"); 
                setFilterRule(row->{
                    try{
                        int year = Integer.parseInt(row[yearCol].trim());
                        return year>1938;
                    }
                    catch(NumberFormatException ex){
                        return false; 
                    }
                });
        }); 
        menu.add(nonFounder);

        //view top 3 schools with the most championships 
        JMenuItem topChampion = new JMenuItem("Top 3 schools"); 
        topChampion.addActionListener(e->{
            int championCol = getColumnIndex("Team Championships"); 

            Integer champions[] = new Integer[rows.size()]; 

            for (int i = 0; i<rows.size(); i++)
            {
                try {
                champions[i] = Integer.parseInt(rows.get(i)[championCol].trim());
            } catch (NumberFormatException ex) {
                champions[i] = 0;
            }
            }

            Arrays.sort(champions, Collections.reverseOrder());

            String top1 = champions.length > 0 ? String.valueOf(champions[0]) : "";
            String top2 = champions.length > 1 ? String.valueOf(champions[1]) : "";
            String top3 = champions.length > 2 ? String.valueOf(champions[2]) : "";

            // 3. Compare row[yearCol] as a String against those three values
            setFilterRule(row -> {
                if (row[championCol] == null) return false;
                String cellYear = row[championCol].trim();
                return cellYear.equals(top1) || cellYear.equals(top2) || cellYear.equals(top3);
            });

        });
        menu.add(topChampion);
    }
}
