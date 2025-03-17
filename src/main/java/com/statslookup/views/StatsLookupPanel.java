package com.statslookup.views;

import com.statslookup.StatsLookupConfig;
import com.statslookup.models.MonsterInfobox;
import com.statslookup.osrsdata.OsrsWikiMonster;
import com.statslookup.osrsdata.OsrsWikiScraper;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.components.IconTextField;
import lombok.extern.slf4j.Slf4j;
import javax.inject.Inject;

import javax.swing.*;
import java.awt.*;

@Slf4j
public class StatsLookupPanel extends PluginPanel {

    private final IconTextField monsterSearchField = new IconTextField();
    // Panel to display monster info

    private final StatsLookupConfig config;

    private final JPanel displayPanel = new JPanel(new BorderLayout());



    public StatsLookupPanel(StatsLookupConfig config) {
        this.config = config;
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
        log.debug("Performing search for monster: {}", monsterName);
        if (!monsterName.isEmpty()) {
            // Start a thread for scraping so the UI doesn't freeze
            new Thread(() -> {
                try {
                    log.debug("Starting scrape for monster: {}", monsterName);
                    OsrsWikiMonster monster = OsrsWikiScraper.scrapeMonsterInfo(monsterName);
                    log.debug("Scrape successful for monster: {}", monsterName);
                    MonsterInfobox infobox = new MonsterInfobox(monster);
                    SwingUtilities.invokeLater(() -> {
                        log.debug("Updating UI with monster info: {}", monsterName);
                        MonsterInfoboxPanel infoboxPanel = new MonsterInfoboxPanel(config);
                        infoboxPanel.setMonsterInfobox(infobox);
                        displayPanel.removeAll();
                        displayPanel.add(infoboxPanel, BorderLayout.CENTER);
                        displayPanel.revalidate();
                        displayPanel.repaint();
                    });
                } catch (Exception ex) {
                    log.error("Error during monster search: {}", ex.getMessage(), ex);
                    SwingUtilities.invokeLater(() -> {
                        displayPanel.removeAll();
                        displayPanel.add(new JLabel("Error: " + ex.getMessage()), BorderLayout.CENTER);
                        displayPanel.revalidate();
                        displayPanel.repaint();
                    });
                }
            }).start();
        } else {
            log.debug("Monster name is empty, skipping search");
        }
    }

    private void resetDisplay() {
        displayPanel.removeAll();
        displayPanel.add(new JLabel("Enter a monster name and hit enter"), BorderLayout.CENTER);
        displayPanel.revalidate();
        displayPanel.repaint();
    }

    public void lookupMonsterStats(String monsterName) {
        log.debug("Looking up stats for monster: {}", monsterName);
        if (monsterName != null && !monsterName.isEmpty()) {
            log.debug("Setting text in monsterSearchField: {}", monsterName);
            try {
                SwingUtilities.invokeLater(() -> {
                    try {
                        monsterSearchField.setText(monsterName);
                        performSearch();
                    } catch (AssertionError e) {
                        log.error("AssertionError while setting text in monsterSearchField: {}", e.getMessage(), e);
                    }
                });
            } catch (Exception e) {
                log.error("Exception while setting text in monsterSearchField: {}", e.getMessage(), e);
            }
        } else {
            log.error("Monster name is null or empty");
        }
    }

}
