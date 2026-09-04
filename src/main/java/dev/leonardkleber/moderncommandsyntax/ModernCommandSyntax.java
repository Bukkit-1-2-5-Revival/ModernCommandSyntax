package dev.leonardkleber.moderncommandsyntax;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import dev.leonardkleber.moderncommandsyntax.commands.GameModeCommand;
import dev.leonardkleber.moderncommandsyntax.commands.TimeCommand;
import dev.leonardkleber.moderncommandsyntax.commands.WeatherCommand;
import dev.leonardkleber.ccc.CCC;
import dev.leonardkleber.ccc.api.CCCAPI;

public class ModernCommandSyntax extends JavaPlugin{
    private CCCAPI cccAPI;
    
    @Override
    public void onEnable() {
        Plugin plugin = getServer().getPluginManager().getPlugin("CCC");
        if (plugin instanceof CCC) cccAPI = ((CCC) plugin).getAPI();

        getCommand("time").setExecutor(new TimeCommand(cccAPI));
        getCommand("weather").setExecutor(new WeatherCommand(cccAPI));
        getCommand("gamemode").setExecutor(new GameModeCommand(cccAPI));
    }
}
