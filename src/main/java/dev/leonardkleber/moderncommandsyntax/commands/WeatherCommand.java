package dev.leonardkleber.moderncommandsyntax.commands;

import java.util.Random;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.leonardkleber.ccc.api.CCCAPI;

public class WeatherCommand implements CommandExecutor {
    private static final String USAGE = "Usage: /weather (clear|rain|thunder) [<duration>]";
    private static final String NO_PERMISSION = "You don't have permission to use this command.";
    private static final String ONLY_PLAYERS = "This command can only be used by a player.";
    private static final String TOO_LITTLE_ARGS = "Too little arguments. " + USAGE;
    private static final String TOO_MANY_ARGS = "Too many arguments. " + USAGE;
    private static final String INVALID_WEATHER = "Invalid weather. Choose from clear, rain, or thunder.";
    private static final String INVALID_DURATION = "Invalid duration. Duration must be an integer.";
    private static final String SET_CLEAR = "Weather set to clear. Duration: ";
    private static final String SET_RAIN = "Weather set to rain. Duration: ";
    private static final String SET_THUNDER = "Weather set to thunder. Duration: ";

    private static final int CLEAR_MIN = 12000;
    private static final int CLEAR_MAX = 180000;
    private static final int RAIN_MIN = 12000;
    private static final int RAIN_MAX = 24000;
    private static final int THUNDER_MIN = 3600;
    private static final int THUNDER_MAX = 15600;

    private final CCCAPI cccAPI;
    private final Random random = new Random();

    public WeatherCommand(CCCAPI cccAPI) {
        this.cccAPI = cccAPI;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) {
            sendError(sender, NO_PERMISSION);
            return true;
        }

        if (!(sender instanceof Player)) {
            sendError(sender, ONLY_PLAYERS);
            return true;
        }

        if (args.length < 1) {
            sendError(sender, TOO_LITTLE_ARGS);
            return true;
        }

        if (args.length > 2) {
            sendError(sender, TOO_MANY_ARGS);
            return true;
        }

        Player player = (Player) sender;
        String weather = args[0].toLowerCase();

        if (!weather.equalsIgnoreCase("clear") && !weather.equalsIgnoreCase("rain") && !weather.equalsIgnoreCase("thunder")) {
            sendError(sender, INVALID_WEATHER);
            return true;
        }

        int duration;

        if (args.length == 1) {
            duration = generateDuration(weather);
        } else {
            try {
                duration = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                sendError(sender, INVALID_DURATION);
                return true;
            }
        }

        if (weather.equalsIgnoreCase("clear")) {
            player.getWorld().setStorm(false);
            player.getWorld().setThundering(false);
            player.getWorld().setWeatherDuration(duration);

            sendSuccess(sender, SET_CLEAR + duration);
            return true;
        }

        if (weather.equalsIgnoreCase("rain")) {
            player.getWorld().setStorm(true);
            player.getWorld().setThundering(false);
            player.getWorld().setWeatherDuration(duration);

            sendSuccess(sender, SET_RAIN + duration);
            return true;
        }

        if (weather.equalsIgnoreCase("thunder")) {
            player.getWorld().setStorm(true);
            player.getWorld().setThundering(true);
            player.getWorld().setWeatherDuration(duration);

            sendSuccess(sender, SET_THUNDER + duration);
            return true;
        }

        return true;
    }

    private void sendError(CommandSender sender, String message) {
        if (cccAPI != null) sender.sendMessage(cccAPI.error(message));
        else sender.sendMessage(message);
    }

    private void sendSuccess(CommandSender sender, String message) {
        if (cccAPI != null) sender.sendMessage(cccAPI.success(message));
        else sender.sendMessage(message);
    }

    private int generateDuration(String arg) {
        if (arg.equalsIgnoreCase("rain")) return RAIN_MIN + random.nextInt(RAIN_MAX - RAIN_MIN + 1);
        if (arg.equalsIgnoreCase("clear")) return CLEAR_MIN + random.nextInt(CLEAR_MAX - CLEAR_MIN + 1);
        if (arg.equalsIgnoreCase("thunder")) return THUNDER_MIN + random.nextInt(THUNDER_MAX - THUNDER_MIN + 1);

        return 0;
    }
}