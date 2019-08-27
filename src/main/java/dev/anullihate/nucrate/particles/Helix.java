package dev.anullihate.nucrate.particles;

import cn.nukkit.level.Level;
import cn.nukkit.level.particle.FlameParticle;
import cn.nukkit.math.Vector3;
import cn.nukkit.scheduler.Task;
import dev.anullihate.nucrate.NuCrate;

public class Helix extends Task {

    private NuCrate plugin;
    private Vector3 pos;

    public Helix(NuCrate plugin, Vector3 pos) {
        this.plugin = plugin;
        this.pos = pos;
    }

    @Override
    public void onRun(int tick) {
        Level level = this.plugin.getServer().getLevelByName(this.plugin.getConfig().getString("crateWorld"));
        Vector3 cpos = this.pos;
        int radio = 1;
        for (int y = 0; y < 2; y += 0.2) {
            double x = radio * Math.cos(y);
            double z = radio * Math.sin(y);
            level.addParticle(new FlameParticle(cpos.add(x, y, z)));
        }
        for (int y = 0; y < 2; y += 0.2) {
            double x = -radio * Math.cos(y);
            double z = -radio * Math.sin(y);
            level.addParticle(new FlameParticle(cpos.add(x, y, z)));
        }
    }
}
