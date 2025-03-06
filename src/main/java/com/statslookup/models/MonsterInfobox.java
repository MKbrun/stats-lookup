package com.statslookup.models;

import com.statslookup.osrsdata.OsrsWikiMonster;
import com.statslookup.utils.MonsterNameCleaner;

public class MonsterInfobox {
    private final OsrsWikiMonster monster;

    public MonsterInfobox(OsrsWikiMonster monster) {
        this.monster = monster;
    }

    public String getCleanName() {
        return MonsterNameCleaner.clean(monster.getName());
    }

    public String getHitpoints() {
        return monster.getHitpoints();
    }

    public String getAttack() {
        return monster.getAtt();
    }

    public String getStrength() {
        return monster.getStr();
    }
    public String getDlight() {
        return monster.getDlight();
    }
    public String getDstandard() {
        return monster.getDstandard();
    }
    public String getDheavy() {
        return monster.getDheavy();
    }
}
