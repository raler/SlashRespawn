package com.raler.SlashRespawn.command;

import org.bukkit.*;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.Random;

public class Respawn implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args){
        // Checks to see if the command comes from the console. If it does, let them know it can only be used in game
        if (!(sender instanceof Player) && args.length == 0) {
            sender.sendMessage("[SlashRespawn]: /respawn can only be used in game by a player in spectator mode.");
            return true;
        }

        //Otherwise, check to see if the user is a spectator and TRUE is the only argument
        Player player = (Player)sender;
        var mode = player.getGameMode();
        if(mode != null && mode == GameMode.SPECTATOR){
            if(args.length == 1 && Objects.equals(args[0], "TRUE")){
                player.sendMessage(String.format("[SlashRespawn]: Respawning... Please Wait"));

                World overworld = player.getWorld();
                var worlds = Bukkit.getServer().getWorlds();
                for( World w : worlds){
                    if(w.getEnvironment() == World.Environment.NORMAL){
                        overworld = w;
                    }
                }
                var block = GetRandomSpawnBlock(overworld);
                //Checks for lava and water
                while(block.isLiquid()){
                    block = GetRandomSpawnBlock(overworld);
                }

                player.teleport(new Location(overworld, block.getX(), block.getY() + 1, block.getZ() ));

                player.setGameMode(GameMode.SURVIVAL);
                player.setLevel(0);

                var playerLocation = player.getLocation();
                player.sendMessage(String.format("[SlashRespawn]: Respawned at %f, %f, %f", playerLocation.getX(), playerLocation.getY(), playerLocation.getZ()));
                Bukkit.getServer().getConsoleSender().sendMessage(String.format("[SlashRespawn]: Respawned player %s at %f, %f, %f", player.getName(), playerLocation.getX(), playerLocation.getY(), playerLocation.getZ()));
            }
            else{
                player.sendMessage(String.format("[SlashRespawn]: To respawn, you MUST type \"/respawn TRUE\""));
            }
        }
        else{
            sender.sendMessage("[SlashRespawn]: You must be a spectator to use this command.");
        }


        return true;
    }


    Block GetRandomSpawnBlock(World world){
        Random rand = new Random();
        var angle = rand.nextInt(360) * Math.PI / 180.0;

        var distance = 35355.0;

        var x = (int)(distance * Math.cos(angle));
        var z = (int)(distance * Math.sin(angle));

        return world.getHighestBlockAt(x,z);
    }
}
