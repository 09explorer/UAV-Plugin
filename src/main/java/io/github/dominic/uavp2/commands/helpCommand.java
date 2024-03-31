package io.github.dominic.uavp2.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class helpCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player){
            sender.sendMessage("UAV Plugin: a plugin to track others with government drones");
            sender.sendMessage("/uav: this command");
            sender.sendMessage("/activate <player>: At a cost of 5 netherite ingots, you may download the coordinates of any online user");
            sender.sendMessage("You will only get those coordinates once, and you may not ");
        }


        return true;
    }
}
