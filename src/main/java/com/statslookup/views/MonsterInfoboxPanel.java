package com.statslookup.views;

import com.statslookup.models.MonsterInfobox;
import com.statslookup.utils.Constants;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

public class MonsterInfoboxPanel extends JPanel {

        private final JLabel nameLabel = new JLabel("", SwingConstants.CENTER);
        private final JLabel imageLabel = new JLabel("", SwingConstants.CENTER);
        private final JPanel sectionsPanel = new JPanel();

        public MonsterInfoboxPanel() {
                setLayout(new BorderLayout());
                setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add margin

                // Top Panel containing name and image
                JPanel topPanel = new JPanel();
                topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
                topPanel.add(nameLabel);
                topPanel.add(imageLabel);

                add(topPanel, BorderLayout.NORTH);

                sectionsPanel.setLayout(new BoxLayout(sectionsPanel, BoxLayout.Y_AXIS));
                add(sectionsPanel, BorderLayout.CENTER);
        }

        public void setMonsterInfobox(MonsterInfobox infobox) {
                nameLabel.setText("Name: " + infobox.getCleanName());
                sectionsPanel.removeAll();

                // Load Image
                loadImageFromURL(infobox.getImage());

                // Combat Stats Section
                JPanel combatSection = createSectionPanel("Combat Stats");
                combatSection.add(createStatEntry(
                                createResizedIcon(Constants.hitpointsIcon, 24, 24), "HP", infobox.getHitpoints()));
                combatSection.add(createStatEntry(
                                createResizedIcon(Constants.attackIcon, 24, 24), "Attack", infobox.getAttack()));
                combatSection.add(createStatEntry(
                                createResizedIcon(Constants.strengthIcon, 24, 24), "Strength", infobox.getStrength()));
                combatSection.add(createStatEntry(
                                createResizedIcon(Constants.defenceIcon, 24, 24), "Defence", infobox.getDef()));
                combatSection.add(createStatEntry(
                                createResizedIcon(Constants.magicIcon, 24, 24), "Mage", infobox.getMage()));
                combatSection.add(createStatEntry(
                                createResizedIcon(Constants.rangeIcon, 24, 24), "Range", infobox.getRange()));

                // Aggressive Stats Section
                JPanel aggressiveSection = createSectionPanel("Aggressive Stats");
                aggressiveSection.add(createStatEntry(
                                createResizedIcon(Constants.attackIcon, 16, 16), "Attack", infobox.getAttbns()));
                aggressiveSection.add(createStatEntry(
                                createResizedIcon(Constants.strengthIcon, 16, 16), "Strength", infobox.getStrbns()));
                aggressiveSection.add(createStatEntry(
                                createResizedIcon(Constants.magicIcon, 16, 16), "Mage", infobox.getAmagic()));
                aggressiveSection.add(createStatEntry(
                                createResizedIcon(Constants.magicIcon, 16, 16), "Magic Damage", infobox.getMbns()));
                aggressiveSection.add(createStatEntry(
                                createResizedIcon(Constants.rangeIcon, 16, 16), "Range", infobox.getArange()));
                aggressiveSection.add(createStatEntry(
                                createResizedIcon(Constants.rangeIcon, 16, 16), "Ranged Strength",
                                infobox.getRngbns()));

                // Melee Defence Section
                JPanel meleeDefenceSection = createSectionPanel("Melee Defence");
                meleeDefenceSection.add(createStatEntry(
                                createResizedIcon(Constants.attackIcon, 24, 24), "Stab", infobox.getDstab()));
                meleeDefenceSection.add(createStatEntry(
                                createResizedIcon(Constants.attackIcon, 24, 24), "Slash", infobox.getDslash()));
                meleeDefenceSection.add(createStatEntry(
                                createResizedIcon(Constants.attackIcon, 24, 24), "Crush", infobox.getDcrush()));

                // Magic Defence Section
                JPanel magicDefenceSection = createSectionPanel("Magic Defence");
                magicDefenceSection.add(createStatEntry(
                                createResizedIcon(Constants.magicIcon, 24, 24), "Magic Defence", infobox.getDmagic()));

                // Ranged Defence Section
                JPanel rangedDefenceSection = createSectionPanel("Ranged Defence");
                rangedDefenceSection.add(createStatEntry(
                                createResizedIcon(Constants.lightIcon, 24, 24), "Light", infobox.getDlight()));
                rangedDefenceSection.add(createStatEntry(
                                createResizedIcon(Constants.standardIcon, 24, 24), "Standard", infobox.getDstandard()));
                rangedDefenceSection.add(createStatEntry(
                                createResizedIcon(Constants.heavyIcon, 24, 24), "Heavy", infobox.getDheavy()));

                // Add the sections to the main container
                sectionsPanel.add(combatSection);
                sectionsPanel.add(aggressiveSection);
                sectionsPanel.add(meleeDefenceSection);
                sectionsPanel.add(magicDefenceSection);
                sectionsPanel.add(rangedDefenceSection);

                revalidate();
                repaint();
        }

        private void loadImageFromURL(String imageUrl) {
                if (imageUrl == null || imageUrl.isEmpty()) {
                        imageLabel.setIcon(null);
                        return;
                }

                try {
                        URL url = new URL(imageUrl);
                        ImageIcon icon = new ImageIcon(url);
                        Image image = icon.getImage().getScaledInstance(128, 128, Image.SCALE_SMOOTH);
                        imageLabel.setIcon(new ImageIcon(image));
                } catch (Exception e) {
                        System.err.println("Failed to load image: " + imageUrl);
                        imageLabel.setIcon(null);
                }
        }

        private JPanel createSectionPanel(String title) {
                JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5)); // Center align
                TitledBorder border = BorderFactory.createTitledBorder(title);
                border.setTitleJustification(TitledBorder.CENTER); // Center title
                panel.setBorder(border);
                panel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align
                return panel;
        }

        private JPanel createStatEntry(Icon icon, String statName, String statValue) {
                JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0)); // Center align
                panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Add padding
                if (icon != null) {
                        panel.add(new JLabel(icon));
                }
                panel.add(new JLabel(statName + ": " + statValue));
                return panel;
        }

        private ImageIcon createResizedIcon(BufferedImage image, int width, int height) {
                BufferedImage resized = net.runelite.client.util.ImageUtil.resizeImage(image, width, height);
                return new ImageIcon(resized);
        }
}
