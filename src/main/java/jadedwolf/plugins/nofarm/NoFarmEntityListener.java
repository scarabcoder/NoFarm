package jadedwolf.plugins.nofarm;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class NoFarmEntityListener implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        if (entity == null || entity instanceof Player)
            return;

        if (entity.getLastDamageCause() == null)
            return;

        if (!NoFarm.getPlugin().getConfig().getBoolean("NonPlayerDamageDropsItems")) {
            if (entity.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
                EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) entity.getLastDamageCause();

                if (e.getDamager() instanceof Player)
                    return;

                if (e.getDamager() instanceof Projectile) {
                    Projectile proj = (Projectile) e.getDamager();
                    if (proj.getShooter() instanceof Player)
                        return;
                }

                event.getDrops().clear();
                event.setDroppedExp(0);
            }
        }
    }
}
