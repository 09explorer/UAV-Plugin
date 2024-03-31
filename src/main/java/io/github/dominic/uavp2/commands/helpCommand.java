package io.github.dominic.uavp2.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class helpCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player){
            sender.sendMessage(ChatColor.YELLOW + "UAV Plugin: a plugin to track others with government drones");
            sender.sendMessage(ChatColor.YELLOW + "/uav: this command");
            sender.sendMessage(ChatColor.YELLOW + "/activate <player>: At a cost of 5 netherite ingots, you may download the coordinates of any online user");
            sender.sendMessage(ChatColor.YELLOW + "You will only get those coordinates once, and you may not use the command more than once every 24 hours");
        }


        return true;
    }
}
