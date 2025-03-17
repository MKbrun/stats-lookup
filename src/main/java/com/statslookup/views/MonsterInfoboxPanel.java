package com.statslookup.views;

import com.statslookup.models.MonsterInfobox;
import com.statslookup.utils.Constants;
import com.statslookup.StatsLookupConfig;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

public class MonsterInfoboxPanel extends JPanel {

        private final JLabel nameLabel = new JLabel("", SwingConstants.CENTER);
        private final JLabel imageLabel = new JLabel("", SwingConstants.CENTER);
        private final JPanel sectionsPanel = new JPanel(new GridBagLayout());
        private final StatsLookupConfig config;

        public MonsterInfoboxPanel(StatsLookupConfig config) {
                this.config = config;
                setLayout(new BorderLayout());
                setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                // Top Panel (Contains Name and Image)
                JPanel topPanel = new JPanel();
                topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
                topPanel.setOpaque(false);

                nameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                nameLabel.setForeground(Color.WHITE);
                nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                topPanel.add(nameLabel);
                topPanel.add(Box.createRigidArea(new Dimension(0, 8)));

                // Only add the image if enabled in config
                if (config.showAvatar()) {
                        topPanel.add(imageLabel);
                }

                topPanel.add(Box.createRigidArea(new Dimension(0, 15)));
                add(topPanel, BorderLayout.NORTH);

                JScrollPane scrollPane = new JScrollPane(sectionsPanel);
                scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
                scrollPane.setBorder(null);
                scrollPane.getViewport().setOpaque(false);
                scrollPane.setOpaque(false);

                add(scrollPane, BorderLayout.CENTER);
        }

        public void setMonsterInfobox(MonsterInfobox infobox) {
                nameLabel.setText("<html><b>" + infobox.getCleanName() + "</b></html>");
                sectionsPanel.removeAll();
                sectionsPanel.setOpaque(false);
                System.out.println("Setting monster info: " + infobox.getCleanName());


                // Load Image (Now centered and without background)
                if (config.showAvatar()) {
                        loadImageFromURL(infobox.getImage());
                }

                // Add sections based on config settings
                if (config.showCombatStats()) {
                        addSection("Combat Stats", new Object[][]{
                                {"HP", infobox.getHitpoints(), Constants.hitpointsIcon},
                                {"Attack", infobox.getAttack(), Constants.attackIcon},
                                {"Strength", infobox.getStrength(), Constants.strengthIcon},
                                {"Defence", infobox.getDef(), Constants.defenceIcon},
                                {"Mage", infobox.getMage(), Constants.magicIcon},
                                {"Range", infobox.getRange(), Constants.rangeIcon}
                        });
                }

                if (config.showAggressiveStats()) {
                        addSection("Aggressive Stats", new Object[][]{
                                {"Attack", infobox.getAttbns(), Constants.attackIcon},
                                {"Strength", infobox.getStrbns(), Constants.strengthIcon},
                                {"Mage", infobox.getAmagic(), Constants.magicIcon},
                                {"Magic Damage", infobox.getMbns(), Constants.magicIcon},
                                {"Range", infobox.getArange(), Constants.rangeIcon},
                                {"Ranged Strength", infobox.getRngbns(), Constants.rangeIcon}
                        });
                }

                if (config.showMeleeDefence()) {
                        addSection("Melee Defence", new Object[][]{
                                {"Stab", infobox.getDstab(), Constants.attackIcon},
                                {"Slash", infobox.getDslash(), Constants.attackIcon},
                                {"Crush", infobox.getDcrush(), Constants.attackIcon}
                        });
                }

                if (config.showMagicDefence()) {
                        addSection("Magic Defence", new Object[][]{
                                {"Magic Defence", infobox.getDmagic(), Constants.magicIcon}
                        });
                }

                if (config.showRangedDefence()) {
                        addSection("Ranged Defence", new Object[][]{
                                {"Light", infobox.getDlight(), Constants.lightIcon},
                                {"Standard", infobox.getDstandard(), Constants.standardIcon},
                                {"Heavy", infobox.getDheavy(), Constants.heavyIcon}
                        });
                }

                sectionsPanel.revalidate();
                sectionsPanel.repaint();
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

        private void addSection(String title, Object[][] stats) {
                JPanel sectionPanel = new JPanel(new GridLayout(0, 3, 10, 5));
                sectionPanel.setBorder(BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(80, 80, 80), 1, true),
                        title, TitledBorder.CENTER, TitledBorder.TOP,
                        new Font("Arial", Font.PLAIN, 12), Color.WHITE));
                sectionPanel.setOpaque(false);
                sectionPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

                for (Object[] stat : stats) {
                        BufferedImage iconImage = (BufferedImage) stat[2];
                        sectionPanel.add(createStatEntry(createResizedIcon(iconImage, 16, 16), (String) stat[0], (String) stat[1]));
                }

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.weightx = 1.0;
                gbc.gridx = 0;
                gbc.gridy = sectionsPanel.getComponentCount();
                gbc.insets = new Insets(15, 0, 15, 0);

                sectionsPanel.add(sectionPanel, gbc);
        }

        private JPanel createStatEntry(Icon icon, String statName, String statValue) {
                JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 2));
                panel.setOpaque(false);
                if (icon != null) {
                        panel.add(new JLabel(icon));
                }
                JLabel statLabel = new JLabel("<html><b style='color:white; font-weight:400'>" + statValue + "</b></html>");
                panel.add(statLabel);
                return panel;
        }

        private ImageIcon createResizedIcon(BufferedImage image, int width, int height) {
                BufferedImage resized = net.runelite.client.util.ImageUtil.resizeImage(image, width, height);
                return new ImageIcon(resized);
        }
}
