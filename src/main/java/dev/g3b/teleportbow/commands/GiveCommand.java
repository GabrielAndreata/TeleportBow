package dev.g3b.teleportbow.commands;

import dev.g3b.teleportbow.TeleportBow;
import dev.g3b.teleportbow.utils.BowUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class GiveCommand implements CommandExecutor {

    private final TeleportBow plugin;
    private final BowUtils bowUtils;

    public GiveCommand(TeleportBow plugin) {
        this.plugin = plugin;
        this.bowUtils = new BowUtils(plugin);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {

            if (player.hasPermission("tpbow.givebow")) {
                if (args.length == 0) {
                    boolean playerHasBow = (player.getInventory().contains(bowUtils.createBow()) && player
                            .getInventory().contains(new ItemStack(Material.ARROW))) || player
                            .getInventory().contains(bowUtils.createBow());

                    boolean playerHasArrow = player.getInventory().contains(new ItemStack(Material.ARROW));
                    if (!playerHasArrow && playerHasBow) {
                        player.getInventory().addItem(new ItemStack(Material.ARROW, 1));
                        return true;
                    }

                    if (!playerHasBow) {
                        ItemStack bow = bowUtils.createBow();
                        player.getInventory().addItem(bow);
                        player.getInventory().addItem(new ItemStack(Material.ARROW, 1));
                        player.sendMessage(ChatColor.GREEN + "You gave yourself a Teleport Bow!");
                    } else {
                        player.sendMessage(ChatColor.RED + "You already have a Teleport Bow!");
                        return true;
                    }
                }

                if (args.length == 1) {
                    Player target = Bukkit.getPlayerExact(args[0]);
                    if (target == null) {
                        player.sendMessage(ChatColor.RED + "This player does not exist.");
                        return true;
                    }
                    boolean targetHasBow = (target.getInventory().contains(bowUtils.createBow()) && target
                            .getInventory().contains(new ItemStack(Material.ARROW))) || target
                            .getInventory().contains(bowUtils.createBow());

                    boolean targetHasArrow = target.getInventory().contains(new ItemStack(Material.ARROW));
                    if (!targetHasArrow && targetHasBow) {
                        target.getInventory().addItem(new ItemStack(Material.ARROW, 1));
                        return true;
                    }

                    if (!targetHasBow) {
                        ItemStack bow = bowUtils.createBow();
                        target.getInventory().addItem(bow);
                        target.getInventory().addItem(new ItemStack(Material.ARROW, 1));
                        target.sendMessage(ChatColor.GREEN + "" + player.getName() + " has given you a Teleport Bow!");
                    } else {
                        player.sendMessage(ChatColor.RED + target.getName() + " already has a Teleport Bow!");
                    }
                }
            } else {
                player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
                return true;
            }
        }
        return true;
    }
}
