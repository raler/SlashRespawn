package com.raler.SlashRespawn;

import com.raler.SlashRespawn.command.Respawn;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{
    @Override
    public void onEnable(){
        getServer().getPluginCommand("respawn").setExecutor(new Respawn());
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[SlashRespawn]: Plugin enabled");
    }

    @Override
    public void onDisable(){
        getServer().getConsoleSender().sendMessage(ChatColor.RED +"[SlashRespawn]: Plugin disabled");
    }
}
