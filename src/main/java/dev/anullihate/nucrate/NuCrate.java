package dev.anullihate.nucrate;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NuCrate extends PluginBase {

    private Map<String, Crate> crates = new HashMap<>();

    private Config blocksConfig;

    public void initCrates() {
        this.saveDefaultConfig();
        this.saveResource("crates.yml");
        this.saveResource("blocks.yml");

        blocksConfig = new Config(new File(getDataFolder(), "blocks.yml"), Config.YAML);
        Config cratesConfig = new Config(new File(getDataFolder(), "crates.yml"), Config.YAML);

        cratesConfig.getSections().forEach((crateType, crateSection) -> {
            this.crates.put(crateType, new Crate(this, (ConfigSection) crateSection, crateType));
        });
    }

    public void initParticles() {
        if (this.getConfig().getBoolean("showParticle")) {
            String crateWorld = this.getConfig().getString("crateWorld");
            if (!this.getServer().isLevelLoaded(crateWorld)) {
                this.getServer().loadLevel(crateWorld);
            }
            if (this.getServer().getLevelByName(crateWorld) != null) {
                // init particle show
                this.initParticleShow();
            } else {
                this.getServer().getLogger().critical("Please set the crateWorld in the config.yml. Or make sure that the world exists and is loaded.");
            }
        }
    }

    public void initParticleShow() {
        //
        String particleType = this.getConfig().getString("particleType");
        int particleTickRate = this.getConfig().getInt("particleTickRate");
    }

    public void initTextParticle() {
        crates.forEach((crateType, crate) -> {

        });
    }

    public Map<String, String> getCrateBlocks() {
        Map<String, String> crateBlocks = new HashMap<>();
        crates.forEach((crateType, crate) -> {
            crateBlocks.put(crate.getBlockData(), crateType);
        });
        return crateBlocks;
    }

    public boolean isCrateBlock(int id, int meta) {
        String blockDataString = String.format("%d:%d", id, meta);
        List<String> crateblocksAsList = new ArrayList<>();
        getCrateBlocks().forEach((crateBlockData, crateType) -> {
            crateblocksAsList.add(crateBlockData);
        });

        if (crateblocksAsList.contains(blockDataString)) {
            return true;
        } else {
            return false;
        }
    }

    public String getCrateType(int id, int meta) {
        String blockDataString = String.format("%d:%d", id, meta);
        return getCrateBlocks().get(blockDataString);
    }

    @Override
    public void onEnable() {
        initCrates();
        initParticles();
        initTextParticle();

        getServer().getPluginManager().registerEvents(new EventListeners(this), this);
    }

    public Config getBlocksConfig() {
        return blocksConfig;
    }
}
