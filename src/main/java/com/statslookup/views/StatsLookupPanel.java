package com.statslookup.views;

import com.statslookup.models.MonsterInfobox;
import com.statslookup.osrsdata.OsrsWikiMonster;
import com.statslookup.osrsdata.OsrsWikiScraper;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.components.IconTextField;

import javax.swing.*;
import java.awt.*;

public class StatsLookupPanel extends PluginPanel {

    private final IconTextField monsterSearchField = new IconTextField();
    // Panel to display monster info
    private final JPanel displayPanel = new JPanel(new BorderLayout());

    public StatsLookupPanel() {
        setLayout(new BorderLayout());

        buildSearchField();

        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.add(monsterSearchField, BorderLayout.CENTER);
        add(searchPanel, BorderLayout.NORTH);

        // Set an initial placeholder for the display panel
        displayPanel.add(new JLabel("Enter a monster name and hit enter"), BorderLayout.CENTER);
        add(displayPanel, BorderLayout.CENTER);

        monsterSearchField.addActionListener(evt -> performSearch());
        monsterSearchField.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                performSearch();
            }
        });
        // When the clear button is clicked, reset the display
        monsterSearchField.addClearListener(() -> resetDisplay());
    }

    private void buildSearchField() {
        monsterSearchField.setIcon(IconTextField.Icon.SEARCH);
        monsterSearchField.setPreferredSize(new Dimension(PluginPanel.PANEL_WIDTH - 20, 30));
        monsterSearchField.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        monsterSearchField.setHoverBackgroundColor(ColorScheme.DARK_GRAY_HOVER_COLOR);
        monsterSearchField.setMinimumSize(new Dimension(0, 30));
    }

    private void performSearch() {
        String monsterName = monsterSearchField.getText().trim();
        if (!monsterName.isEmpty()) {
            // Start a thread for scraping so the UI doesn't freeze
            new Thread(() -> {
                try {
                    OsrsWikiMonster monster = OsrsWikiScraper.scrapeMonsterInfo(monsterName);
                    MonsterInfobox infobox = new MonsterInfobox(monster);
                    SwingUtilities.invokeLater(() -> {
                        MonsterInfoboxPanel infoboxPanel = new MonsterInfoboxPanel();
                        infoboxPanel.setMonsterInfobox(infobox);
                        displayPanel.removeAll();
                        displayPanel.add(infoboxPanel, BorderLayout.CENTER);
                        displayPanel.revalidate();
                        displayPanel.repaint();
                    });
                } catch (Exception ex) {
                    SwingUtilities.invokeLater(() -> {
                        displayPanel.removeAll();
                        displayPanel.add(new JLabel("Error: " + ex.getMessage()), BorderLayout.CENTER);
                        displayPanel.revalidate();
                        displayPanel.repaint();
                    });
                }
            }).start();
        }
    }

    private void resetDisplay() {
        displayPanel.removeAll();
        displayPanel.add(new JLabel("Enter a monster name and hit enter"), BorderLayout.CENTER);
        displayPanel.revalidate();
        displayPanel.repaint();
    }

    public void lookupMonsterStats(String monsterName) {
        monsterSearchField.setText(monsterName);
        performSearch();
    }

}
