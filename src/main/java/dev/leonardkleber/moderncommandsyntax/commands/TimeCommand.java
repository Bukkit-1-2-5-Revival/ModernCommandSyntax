package dev.leonardkleber.moderncommandsyntax.commands;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.leonardkleber.ccc.api.CCCAPI;

public class TimeCommand implements CommandExecutor {
    private static final String USAGE = "Usage: /time [day|night|noon|midnight|set|add|<time>] [time]";
    private static final String NO_PERMISSION = "You don't have permission to use this command.";
    private static final String ONLY_PLAYERS = "This command can only be used by a player.";
    private static final String TOO_LITTLE_ARGS = "Too little arguments. " + USAGE;
    private static final String TOO_MANY_ARGS = "Too many arguments. " + USAGE;
    private static final String TIME_SET = "Time set to ";
    private static final String INVALID_TIME = "Invalid time. " + USAGE;
    private static final String TIME_ADD = "Time increased by ";
    private static final String INVALID_ACTION = "Invalid action. " + USAGE;

    private static final Long DAY = 1000L;
    private static final Long NOON = 6000L;
    private static final Long NIGHT = 13000L;
    private static final Long MIDNIGHT = 18000L;

    private final CCCAPI cccAPI;

    public TimeCommand(CCCAPI cccAPI) {
        this.cccAPI = cccAPI;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0 && sender instanceof Player) {
            Player player = (Player) sender;
            World world = player.getWorld();
            sendInfo(sender, "Current time: " + world.getTime());
            return true;
        }
        
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
        World world = player.getWorld();

        if (args.length == 1) {
            Long time = parseTime(args[0]);

            if (time == null) {
                sendError(sender, INVALID_TIME);
                return true;
            }

            world.setTime(time);
            sendSuccess(sender, TIME_SET + time + ".");
            return true;
        }

        String action = args[0].toLowerCase();
        Long time = parseTime(args[1]);

        if (time == null) {
            sendError(sender, INVALID_TIME);
            return true;
        }

        if (action.equalsIgnoreCase("set")) {
            world.setTime(time);
            sendSuccess(sender, TIME_SET + time + ".");
            return true;
        }

        if (action.equalsIgnoreCase("add")) {
            world.setTime(world.getTime() + time);
            sendSuccess(sender, TIME_ADD + time + ".");
            return true;
        }

        sendError(sender, INVALID_ACTION);
        return true;
    }

    private Long parseTime(String value) {
        if (value.equalsIgnoreCase("day")) return DAY;
        if (value.equalsIgnoreCase("noon")) return NOON;
        if (value.equalsIgnoreCase("night")) return NIGHT;
        if (value.equalsIgnoreCase("midnight")) return MIDNIGHT;

        if (value.equalsIgnoreCase("d")) return DAY;
        if (value.equalsIgnoreCase("n")) return NIGHT;

        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private void sendError(CommandSender sender, String message) {
        if (cccAPI != null) sender.sendMessage(cccAPI.error(message));
        else sender.sendMessage(message);
    }

    private void sendSuccess(CommandSender sender, String message) {
        if (cccAPI != null) sender.sendMessage(cccAPI.success(message));
        else sender.sendMessage(message);
    }

    private void sendInfo(CommandSender sender, String message) {
        if (cccAPI != null) sender.sendMessage(cccAPI.info(message));
        else sender.sendMessage(message);
    }
}
