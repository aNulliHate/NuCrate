package dev.anullihate.nucrate;

import cn.nukkit.utils.ConfigSection;

public class Crate {

    private NuCrate plugin;
    private ConfigSection crateSection;
    private String crateType;

    public Crate(NuCrate plugin, ConfigSection crateSection, String crateType) {
        this.plugin = plugin;
        this.crateSection = crateSection;
        this.crateType = crateType;
    }

    public String getBlockData() {
        return this.crateSection.getString("block");
    }

    public String getCrateType(int id, int meta) {
        String itemDataString = String.format("%d:%d", id, meta);
        if (this.crateSection.getString("block").equals(itemDataString)) {
            return this.crateType;
        } else {
            return "";
        }
    }
}
