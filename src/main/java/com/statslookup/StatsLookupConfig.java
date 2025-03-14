package com.statslookup;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import com.statslookup.views.ListViewOption;

@ConfigGroup("example")
public interface StatsLookupConfig extends Config {

	@ConfigItem(position = 0, keyName = "listViewOption", name = "List view option", description = "Toggle if you want compact or default list view")
	default ListViewOption listviewOption() {
		return ListViewOption.DEFAULT;
	}

	@ConfigItem(position = 1, keyName = "showStatsMenuOption", name = "Show Stats Menu Option", description = "Enable right-click 'Stats' option for NPCs")
	default boolean showStatsMenuOption() {
		return true;
	}

	@ConfigItem(position = 2, keyName = "showAvatar", name = "Monster Image", description = "Toggle to show or hide the monster's image")
	default boolean showAvatar() {
		return true;
	}

	// Combat Stats Toggle
	@ConfigItem(position = 3, keyName = "showCombatStats", name = "Show Combat Stats", description = "Toggle to show or hide combat stats (Attack, Strength, Defence, etc.)")
	default boolean showCombatStats() {
		return true;
	}

	// Aggressive Stats Toggle
	@ConfigItem(position = 4, keyName = "showAggressiveStats", name = "Show Aggressive Stats", description = "Toggle to show or hide aggressive stats (Attack bonus, Strength bonus, etc.)")
	default boolean showAggressiveStats() {
		return true;
	}

	// Melee Defence Toggle
	@ConfigItem(position = 5, keyName = "showMeleeDefence", name = "Show Melee Defence", description = "Toggle to show or hide melee defence stats (Stab, Slash, Crush)")
	default boolean showMeleeDefence() {
		return true;
	}

	// Magic Defence Toggle
	@ConfigItem(position = 6, keyName = "showMagicDefence", name = "Show Magic Defence", description = "Toggle to show or hide magic defence stats")
	default boolean showMagicDefence() {
		return true;
	}

	// Ranged Defence Toggle
	@ConfigItem(position = 7, keyName = "showRangedDefence", name = "Show Ranged Defence", description = "Toggle to show or hide ranged defence stats")
	default boolean showRangedDefence() {
		return true;
	}
}
