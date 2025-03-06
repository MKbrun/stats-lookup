package com.statslookup;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import com.statslookup.views.ListViewOption;

import java.awt.*;

@ConfigGroup("example")
public interface StatsLookupConfig extends Config {
	@ConfigItem(
			position = 0,
			keyName = "listViewOption",
			name = "List view option",
			description = "Toggle if you want compact or default list view"
	)
	default ListViewOption listviewOption() {
		return ListViewOption.DEFAULT;
	}

	@ConfigItem(
			position = 1,
			keyName = "showStatsMenuOption",
			name = "Show Stats Menu Option",
			description = "Enable right-click 'Stats' option for NPCs"
	)
	default boolean showStatsMenuOption()
	{
		return true;
	}

	@ConfigItem(
			position = 2,
			keyName = "showAvatar",
			name = "Monster Image",
			description = "Toggle to show or hide image of the monster"

	)
	default boolean showAvatar() {
		return true;
	}
}
