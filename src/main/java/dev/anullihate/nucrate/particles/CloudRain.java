package dev.anullihate.nucrate.particles;

import cn.nukkit.level.Level;
import cn.nukkit.level.particle.ExplodeParticle;
import cn.nukkit.level.particle.WaterDripParticle;
import cn.nukkit.math.Vector3;
import cn.nukkit.scheduler.Task;
import dev.anullihate.nucrate.NuCrate;

public class CloudRain extends Task {

    private NuCrate plugin;
    private Vector3 pos;

    public CloudRain(NuCrate plugin, Vector3 pos) {
        this.plugin = plugin;
        this.pos = pos;
    }

    @Override
    public void onRun(int tick) {
        Level level = this.plugin.getServer().getLevelByName(this.plugin.getConfig().getString("crateWorld"));
        Vector3 cpos = this.pos;
        double time = 1;
        double pi = 3.14159;
        time = time + 0.1 / pi;

        for (int i = 0; i <= 2 * pi; i += pi / 8) {
            double x = time * Math.cos(i);
            double y = Math.exp(-0.1 * time * Math.sin(time) + 1.5);
            double z = time * Math.sin(i);
            level.addParticle(new ExplodeParticle(cpos.add(x, y, z)));
            level.addParticle(new WaterDripParticle(cpos.add(x, y, z)));
        }
    }
}
