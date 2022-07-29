package dev.g3b.teleportbow.listeners;

import dev.g3b.teleportbow.TeleportBow;
import dev.g3b.teleportbow.utils.BowUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class BowListener implements Listener {

    private final TeleportBow plugin;
    private final BowUtils bowUtils;

    public BowListener(TeleportBow plugin) {
        this.plugin = plugin;
        this.bowUtils = new BowUtils(plugin);
    }

    @EventHandler
    public void onArrowHit(ProjectileHitEvent event) {
        if (event.getEntity().getShooter() instanceof Player player) {
            ItemStack itemInHand = player.getInventory().getItemInMainHand();
            if (itemInHand.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfig().getString("bow-name")))) {
                Location location = event.getEntity().getLocation();
                player.teleport(location);
                event.getEntity().remove();
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("shot-message")));
            }
        }
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (plugin.getConfig().getBoolean("give-on-join")) {
            Player player = event.getPlayer();
            player.getInventory().addItem(bowUtils.createBow());
            player.getInventory().addItem(new ItemStack(Material.ARROW, 1));
            player.sendMessage("hey here's a cool bow");
        }
    }


}
