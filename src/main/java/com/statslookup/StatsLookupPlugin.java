package com.statslookup;

import com.google.inject.Provides;
import javax.inject.Inject;

import com.statslookup.views.StatsLookupPanel;
import com.statslookup.utils.Constants;
import com.statslookup.views.StatsLoopupOverlay;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.util.ImageUtil;

@Slf4j
@PluginDescriptor(name = "Example")
public class StatsLookupPlugin extends Plugin {
	@Inject
	private Client client;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private StatsLoopupOverlay statsLoopupOverlay;

	@Inject
	private StatsLookupConfig config;
	@Inject
	private ClientToolbar clientToolbar;

	private NavigationButton navButton;
	private StatsLookupPanel statsLookupPanel;

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

	@Provides
	StatsLookupConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(StatsLookupConfig.class);
	}
}
