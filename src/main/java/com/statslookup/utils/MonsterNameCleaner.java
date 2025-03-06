package com.statslookup.utils;

public class MonsterNameCleaner {
    /**
     * Cleans up the raw monster name by removing color tags and level information.
     * @param rawName The raw string containing formatting and additional info.
     * @return The cleaned monster name.
     */
    public static String clean(String rawName) {
        // Remove any <col=...> tags
        String cleaned = rawName.replaceAll("<col=[^>]*>", "");
        // Remove level info like "(level-3)"
        cleaned = cleaned.replaceAll("\\(level-\\d+\\)", "");
        return cleaned.trim();
    }
}
