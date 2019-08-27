package dev.anullihate.nucrate;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import cn.nukkit.level.particle.ExplodeParticle;
import cn.nukkit.level.particle.LavaParticle;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.Config;

import java.util.ArrayList;
import java.util.List;

public class EventListeners implements Listener {

    private NuCrate plugin;

    private final List<Integer> CRATE_BLOCKS = new ArrayList<Integer>() {
        {
            add(Block.CHEST);
            add(Block.ENDER_CHEST);
            add(Block.TRAPPED_CHEST);
        }
    };

    public EventListeners(NuCrate plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Level level = this.plugin.getServer().getLevelByName(this.plugin.getConfig().getString("crateWorld"));
        if (!(player.hasPermission("nu.crates.destroy"))) {
            if (this.plugin.isCrateBlock(block.getId(), block.getDamage())) {
                if (CRATE_BLOCKS.contains(block.getLevel().getBlock(block.add(0, 1)).getId())) {
                    player.sendMessage("no perm destroy");
                    event.setCancelled();
                }
            } else if (CRATE_BLOCKS.contains(block.getId())) {
                Block typeBlock = block.getLevel().getBlock(block.subtract(0, 1));
                if (this.plugin.isCrateBlock(typeBlock.getId(), typeBlock.getDamage())) {
                    player.sendMessage("no perm destroy");
                    event.setCancelled();
                }
            }
        } else {
            if (CRATE_BLOCKS.contains(block.getId())) {
                if (player.getLevel() == level) {
                    Block typeBlock = block.getLevel().getBlock(block.subtract(0, 1));
                    Config blocksConfig = this.plugin.getBlocksConfig();
                    if (this.plugin.isCrateBlock(typeBlock.getId(), typeBlock.getDamage())) {
                        String type = this.plugin.getCrateType(typeBlock.getId(), typeBlock.getDamage());
                        if (!blocksConfig.getString(type).isEmpty()) {
                            blocksConfig.remove(type);
                            blocksConfig.save();
                            player.sendMessage("destroyed");
                        }
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Level level = this.plugin.getServer().getLevelByName(this.plugin.getConfig().getString("crateWorld"));
        if (!(player.hasPermission("nu.crates.create"))) {
            if (this.plugin.isCrateBlock(block.getId(), block.getDamage())) {
                if (CRATE_BLOCKS.contains(block.getLevel().getBlock(block.add(0, 1)).getId())) {
                    player.sendMessage("no perm create");
                    event.setCancelled();
                }
            } else if (CRATE_BLOCKS.contains(block.getId())) {
                Block typeBlock = block.getLevel().getBlock(block.subtract(0, 1));
                if (this.plugin.isCrateBlock(typeBlock.getId(), typeBlock.getDamage())) {
                    player.sendMessage("no perm create");
                    event.setCancelled();
                }
            }
        } else {
            if (CRATE_BLOCKS.contains(block.getId())) {
                if (player.getLevel() == level) {
                    Block typeBlock = block.getLevel().getBlock(block.subtract(0, 1));
                    Config blocksConfig = this.plugin.getBlocksConfig();
                    if (this.plugin.isCrateBlock(typeBlock.getId(), typeBlock.getDamage())) {
                        String type = this.plugin.getCrateType(typeBlock.getId(), typeBlock.getDamage());
                        double x = block.getX();
                        double y = block.getY();
                        double z = block.getZ();
                        if (blocksConfig.getString(type).isEmpty()) {
                            blocksConfig.set(type + ".x", x);
                            blocksConfig.set(type + ".y", y);
                            blocksConfig.set(type + ".z", z);
                            blocksConfig.save();
                            player.sendMessage("created");
                        }
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Level level = this.plugin.getServer().getLevelByName(this.plugin.getConfig().getString("crateWorld"));
        Block typeBlock = block.getLevel().getBlock(block.subtract(0, 1));
        Item item = event.getItem();
        if (player.getLevel().equals(level)) {
            if (CRATE_BLOCKS.contains(block.getId()) && this.plugin.isCrateBlock(typeBlock.getId(), typeBlock.getDamage())) {
                String type = this.plugin.getCrateType(typeBlock.getId(), typeBlock.getDamage());
                event.setCancelled();

                if (!player.hasPermission("nu.crates.use")) {
                    player.sendMessage("cant use");
                    return;
                } else {
                    if (player.isSneaking()) {
                        player.sendMessage("error sneaking");
                        return;
                    }

                    double cx = block.getX() + 0.5;
                    double cy = block.getY() + 1.2;
                    double cz = block.getZ() + 0.5;
                    int radius = 1;
                    for (int i = 0; i < 361; i += 1.1) {
                        double x = cx + (radius * Math.cos(i));
                        double z = cz + (radius * Math.sin(i));
                        Vector3 pos = new Vector3(x, cy, z);
                        block.level.addParticle(new LavaParticle(pos));
                    }
                }
            }
        }
    }
}
