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
    public String getImage() {
        return monster.getImage();
    }


    //Combat stats
    public String getHitpoints() {
        return monster.getHitpoints();
    }
    public String getAttack() {
        return monster.getAtt();
    }
    public String getStrength() {
        return monster.getStr();
    }
    public String getDef() {
        return monster.getDef();
    }
    public String getMage() {
        return monster.getMage();
    }
    public String getRange() {
        return monster.getRange();
    }

    //Aggressive stats
    public String getAttbns() {
        return monster.getAttbns();
    }
    public String getStrbns() {
        return monster.getStrbns();
    }
    public String getAmagic() {
        return monster.getAmagic();
    }
    public String getMbns() {
        return monster.getMbns();
    }
    public String getArange() {
        return monster.getArange();
    }
    public String getRngbns() {
        return monster.getRngbns();
    }

    //Melee defence
    public String getDstab() {
        return monster.getDstab();
    }
    public String getDslash() {
        return monster.getDslash();
    }
    public String getDcrush() {
        return monster.getDcrush();
    }

    //Magic defence
    public String getDmagic() {
        return monster.getDmagic();
    }

    //Ranged defence
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
