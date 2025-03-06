package com.statslookup.osrsdata;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import okhttp3.*;

import java.io.IOException;

public class OsrsWikiScraper {

    private static final String BASE_URL = "https://oldschool.runescape.wiki/w/";

    // Scrape the wiki page for the given monster name
    public static OsrsWikiMonster scrapeMonsterInfo(String monsterName) throws IOException {
        // Format the name to match the URL format
        String formattedName = formatMonsterName(monsterName);

        // Send a request to the wiki page of the monster
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(BASE_URL + formattedName)
                .header("User-Agent", "OSRSWikiScraper/1.0")
                .header("Accept", "text/html")
                .build();
        Response response = client.newCall(request).execute();

        if (!response.isSuccessful()) {
            throw new IOException("Failed to fetch page: " + response.code());
        }

        String body = response.body().string();
        if (body.contains("There is currently no text in this page")) {
            throw new IOException("Monster not found: " + monsterName);
        }

        // Parse the HTML response using Jsoup
        Document doc = Jsoup.parse(body);

        // Find the infobox where all the monster data is stored
        Element infobox = doc.select(".infobox").first();

        if (infobox == null) {
            throw new IOException("Infobox not found for monster: " + monsterName);
        }

        // Create a new Monster object
        OsrsWikiMonster monster = new OsrsWikiMonster(monsterName);

        // Extract the data from the infobox and populate the Monster object
        monster.setName(monsterName);
        monster.setImage(extractData(infobox, "image"));
        monster.setRelease(extractData(infobox, "release"));
        monster.setUpdate(extractData(infobox, "update"));
        monster.setRemoval(extractData(infobox, "removal"));
        monster.setRemovalUpdate(extractData(infobox, "removalupdate"));
        monster.setAka(extractData(infobox, "aka"));
        monster.setMembers(extractData(infobox, "members"));
        monster.setCombat(extractData(infobox, "combat level"));
        monster.setSize(extractData(infobox, "size"));
        monster.setExamine(extractData(infobox, "examine"));
        monster.setAttributes(extractData(infobox, "attributes"));
        monster.setXpBonus(extractData(infobox, "xpbonus"));
        monster.setFlatArmour(extractData(infobox, "flatarmour"));
        monster.setMaxHit(extractData(infobox, "max hit"));
        monster.setAggressive(extractData(infobox, "aggressive"));
        monster.setPoisonous(extractData(infobox, "poisonous"));
        monster.setAttackStyle(extractData(infobox, "attack style"));
        monster.setAttackSpeed(extractData(infobox, "attack speed"));
        monster.setRespawn(extractData(infobox, "respawn"));
        monster.setSlayLvl(extractData(infobox, "slaylvl"));
        monster.setSlayXp(extractData(infobox, "slayxp"));
        monster.setCat(extractData(infobox, "cat"));
        monster.setAssignedBy(extractData(infobox, "assignedby"));
        monster.setHitpoints(extractData(infobox, "hitpoints"));
        monster.setAtt(extractData(infobox, "att"));
        monster.setStr(extractData(infobox, "str"));
        monster.setDef(extractData(infobox, "def"));
        monster.setMage(extractData(infobox, "mage"));
        monster.setRange(extractData(infobox, "range"));
        monster.setAttbns(extractData(infobox, "attbns"));
        monster.setStrbns(extractData(infobox, "strbns"));
        monster.setAmagic(extractData(infobox, "amagic"));
        monster.setMbns(extractData(infobox, "mbns"));
        monster.setArange(extractData(infobox, "arange"));
        monster.setRngbns(extractData(infobox, "rngbns"));
        monster.setDstab(extractData(infobox, "dstab"));
        monster.setDslash(extractData(infobox, "dslash"));
        monster.setDcrush(extractData(infobox, "dcrush"));
        monster.setDmagic(extractData(infobox, "dmagic"));
        monster.setDlight(extractData(infobox, "dlight"));
        monster.setDstandard(extractData(infobox, "dstandard"));
        monster.setDheavy(extractData(infobox, "dheavy"));
        monster.setDrange(extractData(infobox, "drange"));
        monster.setElementalWeaknessType(extractData(infobox, "elementalweaknesstype"));
        monster.setElementalWeaknessPercent(extractData(infobox, "elementalweaknesspercent"));
        monster.setImmunepoison(extractData(infobox, "immunepoison"));
        monster.setImmunevenom(extractData(infobox, "immunevenom"));
        monster.setImmunecannon(extractData(infobox, "immunecannon"));
        monster.setImmunethrall(extractData(infobox, "immunethrall"));
        monster.setImmuneburn(extractData(infobox, "immuneburn"));
        monster.setFreezeresistance(extractData(infobox, "freezeresistance"));
        monster.setId(extractData(infobox, "id"));
        monster.setDropversion(extractData(infobox, "dropversion"));

        return monster;
    }

    // Helper function to extract data from the infobox
    private static String extractData(Element infobox, String fieldName) {
        // Special handling for combat stats which are in a specific format
        if (fieldName.equals("hitpoints") || fieldName.equals("att") ||
                fieldName.equals("str") || fieldName.equals("def") ||
                fieldName.equals("mage") || fieldName.equals("range")) {
            return extractCombatStat(infobox, fieldName);
        }

        // Special handling for defensive stats
        if (fieldName.equals("dstab") || fieldName.equals("dslash") ||
                fieldName.equals("dcrush") || fieldName.equals("dmagic") ||
                fieldName.equals("dlight") || fieldName.equals("dstandard") ||
                fieldName.equals("dheavy")) {
            return extractDefensiveStat(infobox, fieldName);
        }

        // Special handling for elemental weakness
        if (fieldName.equals("elementalweaknesstype") || fieldName.equals("elementalweaknesspercent")) {
            Elements rows = infobox.select("tr");
            for (Element row : rows) {
                String text = row.text().toLowerCase();
                if (text.contains("magic defence") && text.contains("weakness")) {
                    String[] parts = text.split("\\s+");
                    for (int i = 0; i < parts.length; i++) {
                        if (parts[i].contains("%")) {
                            if (fieldName.equals("elementalweaknesspercent")) {
                                return parts[i].replaceAll("[^0-9]", "");
                            } else if (fieldName.equals("elementalweaknesstype")) {
                                return "magic";
                            }
                        }
                    }
                }
            }
            return "N/A";
        }

        // Regular field extraction
        Elements rows = infobox.select("tr");
        for (Element row : rows) {
            String rowText = row.text().toLowerCase();
            String fieldNameCleaned = fieldName.toLowerCase().replaceAll("[^a-zA-Z0-9]", "");

            // Handle special cases for field names
            if (fieldName.equals("combat level") && rowText.matches(".*combat\\s+level\\s+\\d+.*")) {
                return rowText.replaceAll(".*combat\\s+level\\s+(\\d+).*", "$1");
            }

            if (fieldName.equals("max hit") && rowText.contains("max hit")) {
                return rowText.replaceAll(".*max hit\\s+(\\d+).*", "$1");
            }

            // General case
            if (rowText.contains(fieldNameCleaned)) {
                Element dataElement = row.select("td").first();
                if (dataElement != null) {
                    String value = dataElement.text().trim();
                    if (!value.isEmpty()) {
                        return value;
                    }
                }
            }
        }
        return "N/A";
    }

    private static String extractElementalWeakness(Element infobox) {
        Elements rows = infobox.select("tr");
        for (Element row : rows) {
            if (row.text().toLowerCase().contains("elemental weakness")) {
                Element dataElement = row.select("td").first();
                if (dataElement != null) {
                    return dataElement.text().trim();
                }
            }
        }
        return "N/A";
    }

    private static String extractCombatStat(Element infobox, String statName) {
        // Find the combat stats section
        Elements rows = infobox.select("tr");
        String combatStatsLine = null;

        // First find the Combat stats row
        for (Element row : rows) {
            if (row.text().contains("Combat stats")) {
                // Look for the next row that contains numbers
                Element current = row;
                while (current != null) {
                    current = current.nextElementSibling();
                    if (current != null && current.text().matches(".*\\d+.*")) {
                        combatStatsLine = current.text().trim();
                        break;
                    }
                }
                break;
            }
        }

        if (combatStatsLine != null) {
            System.out.println("Found combat stats line: " + combatStatsLine);
            String[] stats = combatStatsLine.split("\\s+");
            if (stats.length >= 6) {
                switch (statName) {
                    case "hitpoints":
                        return stats[0]; // Hitpoints
                    case "att":
                        return stats[1]; // Attack
                    case "str":
                        return stats[2]; // Strength
                    case "def":
                        return stats[3]; // Defence
                    case "mage":
                        return stats[4]; // Magic
                    case "range":
                        return stats[5]; // Range
                }
            }
        }
        return "N/A";
    }

    private static String extractDefensiveStat(Element infobox, String statName) {
        Elements rows = infobox.select("tr");

        System.out.println("\nLooking for " + statName + "...");

        // First try to find the section containing defensive stats
        for (Element row : rows) {
            String text = row.text().toLowerCase();
            System.out.println("Checking row text: [" + text + "]");

            // Handle melee defence stats (stab, slash, crush)
            if (text.contains("melee defence")) {
                System.out.println("Found melee defence section!");
                Element nextRow = findNextRowWithNumbers(row);
                if (nextRow != null) {
                    String defenseText = nextRow.text().trim();
                    System.out.println("Found melee defense values: " + defenseText);
                    String[] values = defenseText.split("\\s+");
                    if (values.length >= 3) {
                        switch (statName) {
                            case "dstab":
                                return values[0].replaceAll("[^0-9\\-\\+]", "");
                            case "dslash":
                                return values[1].replaceAll("[^0-9\\-\\+]", "");
                            case "dcrush":
                                return values[2].replaceAll("[^0-9\\-\\+]", "");
                        }
                    }
                }
            }

            // Handle magic defence
            if (text.contains("magic defence")) {
                System.out.println("Found magic defence section!");
                Element nextRow = findNextRowWithNumbers(row);
                if (nextRow != null && statName.equals("dmagic")) {
                    String defenseText = nextRow.text().trim();
                    System.out.println("Found magic defense value: " + defenseText);
                    String[] parts = defenseText.split("\\s+");
                    if (parts.length >= 1) {
                        return parts[0].replaceAll("[^0-9\\-\\+]", "");
                    }
                }
            }

            // Handle ranged defence (light, standard, heavy)
            if (text.contains("ranged defence")) {
                System.out.println("Found ranged defence section!");
                Element nextRow = findNextRowWithNumbers(row);
                if (nextRow != null) {
                    String defenseText = nextRow.text().trim();
                    System.out.println("Found ranged defense values: " + defenseText);
                    String[] values = defenseText.split("\\s+");
                    if (values.length >= 3) {
                        switch (statName) {
                            case "dlight":
                                return values[0].replaceAll("[^0-9\\-\\+]", "");
                            case "dstandard":
                                return values[1].replaceAll("[^0-9\\-\\+]", "");
                            case "dheavy":
                                return values[2].replaceAll("[^0-9\\-\\+]", "");
                        }
                    }
                }
            }
        }

        return "N/A";
    }

    // Helper method to find the next row that contains numbers
    private static Element findNextRowWithNumbers(Element currentRow) {
        Element current = currentRow;
        while ((current = current.nextElementSibling()) != null) {
            String text = current.text().trim();
            if (text.matches(".*[\\+\\-]?\\d+.*")) {
                return current;
            }
        }
        return null;
    }

    // Helper function to format the monster name to match the URL format
    private static String formatMonsterName(String monsterName) {
        // Replace spaces with underscores and ensure proper case formatting
        return monsterName.trim().replaceAll(" ", "_"); // Removed toLowerCase() as wiki URLs seem to be very
                                                        // case-sensitive
    }

    public static void main(String[] args) {
        try {
            String monsterName = "Gargoyle";
            System.out.println("Attempting to scrape info for: " + monsterName);
            OsrsWikiMonster monster = OsrsWikiScraper.scrapeMonsterInfo(monsterName);

            // Print for testing right now
            System.out.println("\nMonster Information:");
            System.out.println("==================");
            System.out.println("Name: " + monster.getName());
            System.out.println("Combat Level: " + monster.getCombat());
            System.out.println("Slayer Level: " + monster.getSlayLvl());
            System.out.println("Max Hit: " + monster.getMaxHit());
            System.out.println("Aggressive: " + monster.getAggressive());
            System.out.println("Attack Style: " + monster.getAttackStyle());

            System.out.println("\nCombat Stats:");
            System.out.println("------------");
            System.out.println("Hitpoints: " + monster.getHitpoints());
            System.out.println("Attack: " + monster.getAtt());
            System.out.println("Strength: " + monster.getStr());
            System.out.println("Defence: " + monster.getDef());
            System.out.println("Magic: " + monster.getMage());
            System.out.println("Ranged: " + monster.getRange());

            System.out.println("\nDefensive Stats:");
            System.out.println("---------------");
            System.out.println("Stab: " + monster.getDstab());
            System.out.println("Slash: " + monster.getDslash());
            System.out.println("Crush: " + monster.getDcrush());
            System.out.println("Magic: " + monster.getDmagic());
            System.out.println("Ranged Light: " + monster.getDlight());
            System.out.println("Ranged Standard: " + monster.getDstandard());
            System.out.println("Ranged Heavy: " + monster.getDheavy());

            System.out.println("\nElemental Weakness:");
            System.out.println("-----------------");
            System.out.println("Type: " + monster.getElementalWeaknessType());
            System.out.println("Percent: " + monster.getElementalWeaknessPercent());

        } catch (IOException e) {
            System.out.println("Error occurred while scraping: " + e.getMessage());
            e.printStackTrace();
        }
    }
}