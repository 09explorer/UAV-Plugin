package io.github.dominic.uavp2.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import java.math.*;

import java.awt.*;
import java.util.HashMap;

public class activateCommand implements CommandExecutor {

    private final int PRICE_AMOUNT = 5;
    private final int COOLDOWN_TIME = 86400;
    public HashMap<String, Long> cooldowns = new HashMap<String, Long>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(sender instanceof Player){
            Plugin p = Bukkit.getPluginManager().getPlugin("UAV");
            final Player player = (Player) sender;

                    Inventory inv = player.getInventory();
            Material m = Material.NETHERITE_INGOT;
            ItemStack payment = new ItemStack(m, PRICE_AMOUNT);

            if(args.length == 0) {
                player.sendMessage(ChatColor.DARK_RED + "Please provide a target!");
                player.sendMessage(ChatColor.YELLOW + "Example: /activate <player>");
            }
            else{
                String targetedPlayerString = args[0];
                Player targetedPlayer = Bukkit.getServer().getPlayerExact(targetedPlayerString);
                if(targetedPlayer == null) player.sendMessage(ChatColor.DARK_RED + "This player is not online!");
               else {

                   //Remove the payment
                   if(hasItem(player, payment)){

                       if(cooldowns.containsKey(sender.getName())){
                           long secondsLeft = ((cooldowns.get(sender.getName())/1000)+COOLDOWN_TIME) - (System.currentTimeMillis()/1000);
                           long minutesLeft = secondsLeft / 60;
                           long hoursLeft = secondsLeft / 3600;
                           if(secondsLeft > 0){
                               //cooldown messages
                               if(hoursLeft >= 1) sender.sendMessage(ChatColor.DARK_RED + "You may not use this command for another " + hoursLeft + " hours!"); //hours
                               else if(minutesLeft >= 1 && minutesLeft < 60) sender.sendMessage(ChatColor.DARK_RED + "You may not use this command for another " + minutesLeft + " minutes!"); //minutes
                               else sender.sendMessage(ChatColor.DARK_RED + "You may not use this command for another " + secondsLeft + " seconds!"); // seconds
                               return true;
                           }
                       }

                       cooldowns.put(sender.getName(), System.currentTimeMillis());

                      inv.removeItem(payment);
                      player.sendMessage(ChatColor.DARK_GREEN + "Payment Successful!");


                      //Inform the stalked player
                       targetedPlayer.sendMessage(ChatColor.DARK_RED + "You are being tracked with a government UAV!");
                       int id = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(p, new Runnable() {
                           @Override
                           public void run() {
                               double targetX = targetedPlayer.getX();
                               double targetY = targetedPlayer.getY();
                               double targetZ = targetedPlayer.getZ();

                               //round to 2 decimal places
                               double roundedX = Math.round(targetX * 100.0) / 100.0;
                               double roundedY = Math.round(targetY * 100.0) / 100.0;
                               double roundedZ = Math.round(targetZ * 100.0) / 100.0;

                               player.sendMessage(ChatColor.AQUA + "[UAV Drone] "+ targetedPlayer.getName() + "'s coordinates are: " + roundedX + " " + roundedY + " " + roundedZ);
                           }
                       }, 0, 200);
                       Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(p, new Runnable() {
                           @Override
                           public void run() {
                               Bukkit.getScheduler().cancelTask(id);
                           }
                       }, 600);

                   }
                   else{
                       player.sendMessage(ChatColor.DARK_RED + "You do not have enough items for payment!");
                   }
                }
            }
        }
        return true;
    }

    public boolean hasItem(Player player, ItemStack item){
        for(ItemStack current : player.getInventory().getContents()){
            if(current == null) continue;
            if(current.isSimilar(item)){
                int currentSize = current.getAmount();
                if(currentSize >= PRICE_AMOUNT) return true;

            }
        }
        return false;
    }

    public void updatePos (Player player, Player targetedPlayer){
        double targetX = targetedPlayer.getX();
        double targetY = targetedPlayer.getY();
        double targetZ = targetedPlayer.getZ();

        //round to 2 decimal places
        double roundedX = Math.round(targetX * 100.0) / 100.0;
        double roundedY = Math.round(targetY * 100.0) / 100.0;
        double roundedZ = Math.round(targetZ * 100.0) / 100.0;

        player.sendMessage(ChatColor.AQUA + "[UAV Drone] "+ targetedPlayer.getName() + "'s coordinates are: " + roundedX + " " + roundedY + " " + roundedZ);
    }
}
