package com.statslookup.views;


import com.statslookup.views.WrapLayout;
import com.statslookup.models.MonsterInfobox;
import com.statslookup.utils.Constants;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MonsterInfoboxPanel extends JPanel {

    private final JLabel nameLabel = new JLabel();
    // Container for the section panels
    private final JPanel sectionsPanel = new JPanel();

    public MonsterInfoboxPanel() {
        setLayout(new BorderLayout());
        add(nameLabel, BorderLayout.NORTH);

        // vertical BoxLayout for sectionsPanel
        sectionsPanel.setLayout(new BoxLayout(sectionsPanel, BoxLayout.Y_AXIS));
        add(sectionsPanel, BorderLayout.CENTER);
    }

    public void setMonsterInfobox(MonsterInfobox infobox) {
        nameLabel.setText("Name: " + infobox.getCleanName());
        sectionsPanel.removeAll();

        // Combat Stats Section
        JPanel combatSection = createSectionPanel("Combat Stats");
        combatSection.add(createStatEntry(
                createResizedIcon(Constants.hitpointsIcon, 24, 24), "HP", infobox.getHitpoints()));
        combatSection.add(createStatEntry(
                createResizedIcon(Constants.attackIcon, 24, 24), "Attack", infobox.getAttack()));
        combatSection.add(createStatEntry(
                createResizedIcon(Constants.strengthIcon, 24, 24), "Strength", infobox.getStrength()));


        // Aggressive Stats Section
        JPanel aggressiveSection = createSectionPanel("Aggressive Stats");


        // Melee Defence Section
        JPanel meleeDefenceSection = createSectionPanel("Melee Defence");


        // Magic Defence Section
        JPanel magicDefenceSection = createSectionPanel("Magic Defence");


        // Ranged Defence Section
        JPanel rangedDefenceSection = createSectionPanel("Ranged Defence");
        combatSection.add(createStatEntry(
                createResizedIcon(Constants.lightIcon,24,28), "Light", infobox.getDlight()));
        combatSection.add(createStatEntry(
                createResizedIcon(Constants.standardIcon,24,28), "Standard", infobox.getDstandard()));
        combatSection.add(createStatEntry(
                createResizedIcon(Constants.heavyIcon,24,28), "Heavy", infobox.getDheavy()));


        // Add the sections to the main container
        sectionsPanel.add(combatSection);
        sectionsPanel.add(aggressiveSection);
        sectionsPanel.add(meleeDefenceSection);
        sectionsPanel.add(magicDefenceSection);
        sectionsPanel.add(rangedDefenceSection);

        revalidate();
        repaint();
    }
    private JPanel createSectionPanel(String title) {
        JPanel panel = new JPanel(new WrapLayout(FlowLayout.LEFT, 5, 5));
        TitledBorder border = BorderFactory.createTitledBorder(title);
        panel.setBorder(border);
        return panel;
    }

    /**
     * Helper method to create a stat entry component.
     * If icon is null, only text is shown.
     */
    private JPanel createStatEntry(Icon icon, String statName, String statValue) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        if (icon != null) {
            JLabel iconLabel = new JLabel(icon);
            panel.add(iconLabel);
        }
        JLabel valueLabel = new JLabel(statName + ": " + statValue);
        panel.add(valueLabel);
        return panel;
    }

    /**
     * Helper method to create a resized ImageIcon from a BufferedImage.
     */
    private ImageIcon createResizedIcon(BufferedImage image, int width, int height) {
        BufferedImage resized = net.runelite.client.util.ImageUtil.resizeImage(image, width, height);
        return new ImageIcon(resized);
    }
}
