package com.statslookup.osrsdata;

public class OsrsWikiMonster {
    private String name;
    private String image;
    private String release;
    private String update;
    private String removal;
    private String removalUpdate;
    private String aka;
    private String members;
    private String combat;
    private String size;
    private String examine;
    private String attributes;
    private String xpBonus;
    private String flatArmour;
    private String maxHit;
    private String aggressive;
    private String poisonous;
    private String attackStyle;
    private String attackSpeed;
    private String respawn;
    private String slayLvl;
    private String slayXp;
    private String cat;
    private String assignedBy;
    private String hitpoints;
    private String att;
    private String str;
    private String def;
    private String mage;
    private String range;
    private String attbns;
    private String strbns;
    private String amagic;
    private String mbns;
    private String arange;
    private String rngbns;
    private String dstab;
    private String dslash;
    private String dcrush;
    private String dmagic;
    private String dlight;
    private String dstandard;
    private String dheavy;
    private String elementalWeaknessType;
    private String elementalWeaknessPercent;
    private String immunepoison;
    private String immunevenom;
    private String immunecannon;
    private String immunethrall;
    private String immuneburn;
    private String freezeresistance;
    private String id;
    private String dropversion;
    private String drange;

    // Constructor
    public OsrsWikiMonster(String name) {
        this.name = name;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        if (image != null && !image.isEmpty()) {
            if (!image.startsWith("http")) {
                return "https://oldschool.runescape.wiki" + image;
            }
            return image;
        }
        return null;
    }


    public void setImage(String image) {
        this.image = image;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public String getRemoval() {
        return removal;
    }

    public void setRemoval(String removal) {
        this.removal = removal;
    }

    public String getRemovalUpdate() {
        return removalUpdate;
    }

    public void setRemovalUpdate(String removalUpdate) {
        this.removalUpdate = removalUpdate;
    }

    public String getAka() {
        return aka;
    }

    public void setAka(String aka) {
        this.aka = aka;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }

    public String getCombat() {
        return combat;
    }

    public void setCombat(String combat) {
        this.combat = combat;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getExamine() {
        return examine;
    }

    public void setExamine(String examine) {
        this.examine = examine;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public String getXpBonus() {
        return xpBonus;
    }

    public void setXpBonus(String xpBonus) {
        this.xpBonus = xpBonus;
    }

    public String getFlatArmour() {
        return flatArmour;
    }

    public void setFlatArmour(String flatArmour) {
        this.flatArmour = flatArmour;
    }

    public String getMaxHit() {
        return maxHit;
    }

    public void setMaxHit(String maxHit) {
        this.maxHit = maxHit;
    }

    public String getAggressive() {
        return aggressive;
    }

    public void setAggressive(String aggressive) {
        this.aggressive = aggressive;
    }

    public String getPoisonous() {
        return poisonous;
    }

    public void setPoisonous(String poisonous) {
        this.poisonous = poisonous;
    }

    public String getAttackStyle() {
        return attackStyle;
    }

    public void setAttackStyle(String attackStyle) {
        this.attackStyle = attackStyle;
    }

    public String getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(String attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public String getRespawn() {
        return respawn;
    }

    public void setRespawn(String respawn) {
        this.respawn = respawn;
    }

    public String getSlayLvl() {
        return slayLvl;
    }

    public void setSlayLvl(String slayLvl) {
        this.slayLvl = slayLvl;
    }

    public String getSlayXp() {
        return slayXp;
    }

    public void setSlayXp(String slayXp) {
        this.slayXp = slayXp;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(String assignedBy) {
        this.assignedBy = assignedBy;
    }

    public String getHitpoints() {
        return hitpoints;
    }

    public void setHitpoints(String hitpoints) {
        this.hitpoints = hitpoints;
    }

    public String getAtt() {
        return att;
    }

    public void setAtt(String att) {
        this.att = att;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public String getDef() {
        return def;
    }

    public void setDef(String def) {
        this.def = def;
    }

    public String getMage() {
        return mage;
    }

    public void setMage(String mage) {
        this.mage = mage;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getAttbns() {
        return attbns;
    }

    public void setAttbns(String attbns) {
        this.attbns = attbns;
    }

    public String getStrbns() {
        return strbns;
    }

    public void setStrbns(String strbns) {
        this.strbns = strbns;
    }

    public String getAmagic() {
        return amagic;
    }

    public void setAmagic(String amagic) {
        this.amagic = amagic;
    }

    public String getMbns() {
        return mbns;
    }

    public void setMbns(String mbns) {
        this.mbns = mbns;
    }

    public String getArange() {
        return arange;
    }

    public void setArange(String arange) {
        this.arange = arange;
    }

    public String getRngbns() {
        return rngbns;
    }

    public void setRngbns(String rngbns) {
        this.rngbns = rngbns;
    }

    public String getDstab() {
        return dstab;
    }

    public void setDstab(String dstab) {
        this.dstab = dstab;
    }

    public String getDslash() {
        return dslash;
    }

    public void setDslash(String dslash) {
        this.dslash = dslash;
    }

    public String getDcrush() {
        return dcrush;
    }

    public void setDcrush(String dcrush) {
        this.dcrush = dcrush;
    }

    public String getDmagic() {
        return dmagic;
    }

    public void setDmagic(String dmagic) {
        this.dmagic = dmagic;
    }

    public String getDlight() {
        return dlight;
    }

    public void setDlight(String dlight) {
        this.dlight = dlight;
    }

    public String getDstandard() {
        return dstandard;
    }

    public void setDstandard(String dstandard) {
        this.dstandard = dstandard;
    }

    public String getDheavy() {
        return dheavy;
    }

    public void setDheavy(String dheavy) {
        this.dheavy = dheavy;
    }

    public String getElementalWeaknessType() {
        return elementalWeaknessType;
    }

    public void setElementalWeaknessType(String elementalWeaknessType) {
        this.elementalWeaknessType = elementalWeaknessType;
    }

    public String getElementalWeaknessPercent() {
        return elementalWeaknessPercent;
    }

    public void setElementalWeaknessPercent(String elementalWeaknessPercent) {
        this.elementalWeaknessPercent = elementalWeaknessPercent;
    }

    public String getImmunepoison() {
        return immunepoison;
    }

    public void setImmunepoison(String immunepoison) {
        this.immunepoison = immunepoison;
    }

    public String getImmunevenom() {
        return immunevenom;
    }

    public void setImmunevenom(String immunevenom) {
        this.immunevenom = immunevenom;
    }

    public String getImmunecannon() {
        return immunecannon;
    }

    public void setImmunecannon(String immunecannon) {
        this.immunecannon = immunecannon;
    }

    public String getImmunethrall() {
        return immunethrall;
    }

    public void setImmunethrall(String immunethrall) {
        this.immunethrall = immunethrall;
    }

    public String getImmuneburn() {
        return immuneburn;
    }

    public void setImmuneburn(String immuneburn) {
        this.immuneburn = immuneburn;
    }

    public String getFreezeresistance() {
        return freezeresistance;
    }

    public void setFreezeresistance(String freezeresistance) {
        this.freezeresistance = freezeresistance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDropversion() {
        return dropversion;
    }

    public void setDropversion(String dropversion) {
        this.dropversion = dropversion;
    }

    public String getDrange() {
        return drange;
    }

    public void setDrange(String drange) {
        this.drange = drange;
    }
}