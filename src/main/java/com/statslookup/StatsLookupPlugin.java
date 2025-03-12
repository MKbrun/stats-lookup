package com.statslookup;

import com.google.inject.Provides;
import javax.inject.Inject;
import javax.swing.*;

import com.statslookup.utils.MonsterNameCleaner;
import com.statslookup.views.StatsLookupPanel;
import com.statslookup.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;

@Slf4j
@PluginDescriptor(name = "Example")
public class StatsLookupPlugin extends Plugin {
	@Inject
	private Client client;
	@Inject
	private StatsLookupConfig config;
	@Inject
	private ClientToolbar clientToolbar;

	private NavigationButton navButton;
	private StatsLookupPanel statsLookupPanel;
	private static final String STATS_OPTION = "Lookup Stats";

	@Override
	protected void startUp() throws Exception {
		statsLookupPanel = new StatsLookupPanel();

		if (Constants.NAV_BUTTON == null) {
			log.error("Failed");
		} else {
			log.info("Correct");
		}
		log.info("Example started!");
		navButton = NavigationButton.builder()
				.tooltip("Defence Stats")
				.icon(Constants.NAV_BUTTON)
				.panel(statsLookupPanel)
				.build();

		clientToolbar.addNavigation(navButton);
	}

	@Override
	protected void shutDown() throws Exception {
		clientToolbar.removeNavigation(navButton);
		statsLookupPanel = null;
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged) {
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN) {
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Example says ", null);
		}
	}

	@Subscribe
	public void onMenuEntryAdded(MenuEntryAdded event) {
		log.debug("Menu entry added: {}", event.getTarget());
		if (config.showStatsMenuOption() && event.getType() == MenuAction.NPC_SECOND_OPTION.getId()
				&& event.getTarget() != null) {
			client.createMenuEntry(client.getMenuEntries().length)
					.setOption(STATS_OPTION)
					.setTarget(event.getTarget())
					.setIdentifier(event.getIdentifier())
					.setType(MenuAction.RUNELITE)
					.setParam0(event.getActionParam0())
					.setParam1(event.getActionParam1())
					.onClick(
							evt -> {
								log.debug("Stats option clicked for: {}", event.getTarget());
								selectNavButton();
								// Use the utility class to clean up the monster name.
								String cleanedName = MonsterNameCleaner.clean(event.getTarget());
								log.debug("Cleaned monster name: {}", cleanedName);
								statsLookupPanel.lookupMonsterStats(cleanedName);
							});
		}
	}

	@Provides
	StatsLookupConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(StatsLookupConfig.class);
	}

	public void selectNavButton() {
		SwingUtilities.invokeLater(
				() -> {
					clientToolbar.openPanel(navButton);
				});
	}
}
