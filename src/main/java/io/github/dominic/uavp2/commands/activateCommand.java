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
import org.jetbrains.annotations.NotNull;
import java.math.*;

import java.awt.*;
import java.util.HashMap;

public class activateCommand implements CommandExecutor {

    private final int PRICE_AMOUNT = 5;
    public HashMap<String, Long> cooldowns = new HashMap<String, Long>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        int cooldownTime = PRICE_AMOUNT; // number of seconds for cooldown

        if(sender instanceof Player){

            if(cooldowns.containsKey(sender.getName())){
                long secondsLeft = ((cooldowns.get(sender.getName())/1000)+cooldownTime) - (System.currentTimeMillis()/1000);
                if(secondsLeft > 0){
                    //cooldown
                    sender.sendMessage(ChatColor.DARK_RED + "You may not use this command for another " + secondsLeft + " seconds!");
                    return true;
                }
            }

            cooldowns.put(sender.getName(), System.currentTimeMillis());

            final Player player = (Player) sender;
            Inventory inv = player.getInventory();
            Material m = Material.NETHERITE_INGOT;
            ItemStack payment = new ItemStack(m, 5);

            if(args.length == 0) {
                player.sendMessage("Please provide a target!");
                player.sendMessage("Example: /activate <player>");
            }
            else{
                String targetedPlayerString = args[0];
                Player targetedPlayer = Bukkit.getServer().getPlayerExact(targetedPlayerString);
                if(targetedPlayer == null) player.sendMessage(ChatColor.DARK_RED + "This player is not online!");
               else {
                   //Remove the payment
                   if(hasItem(player, payment)){
                      inv.removeItem(payment);
                      player.sendMessage(ChatColor.DARK_GREEN + "Payment Successful!");
                      //Inform the stalked player
                       targetedPlayer.sendMessage(ChatColor.DARK_RED + "You are being tracked with a government UAV!");

                       double targetX = targetedPlayer.getX();
                       double targetZ = targetedPlayer.getZ();

                       //round to 2 decimal places
                       double roundedX = Math.round(targetX * 100.0) / 100.0;
                       double roundedZ = Math.round(targetZ * 100.0) / 100.0;

                       player.sendMessage(ChatColor.AQUA + "[UAV Drone] "+ targetedPlayer.getName() + "'s coordinates are: " + roundedX + " " + roundedZ);
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
}
