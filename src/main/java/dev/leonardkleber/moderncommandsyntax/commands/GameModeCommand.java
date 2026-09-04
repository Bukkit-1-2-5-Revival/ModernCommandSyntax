package dev.leonardkleber.moderncommandsyntax.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.leonardkleber.ccc.api.CCCAPI;

public class GameModeCommand implements CommandExecutor {
    private static final String USAGE = "Usage: /gamemode <gamemode> [<target>]";
    private static final String NO_PERMISSION = "You don't have permission to use this command.";
    private static final String TOO_LITTLE_ARGS = "Too little arguments. " + USAGE;
    private static final String TOO_MANY_ARGS = "Too many arguments. " + USAGE;
    private static final String INVALID_MODE = "Invalid game mode. Choose from survival (s, 0), or creative (c, 1).";
    private static final String ONLY_PLAYERS = "This command can only be used by a player.";
    private static final String PLAYER_NOT_FOUND = "Player not found.";

    private final CCCAPI cccAPI;

    public GameModeCommand(CCCAPI cccAPI) {
        this.cccAPI = cccAPI;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) {
            sendError(sender, NO_PERMISSION);
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

        if (args.length == 1 && !(sender instanceof Player)) {
            sendError(sender, ONLY_PLAYERS);
            return true;
        }

        GameMode gameMode;

        gameMode = selectGameMode(args[0]);
        if (gameMode == null) {
            sendError(sender, INVALID_MODE);
            return true;
        }

        Player player;

        if (args.length == 1) {
            player = (Player) sender;
        } else {
            player = Bukkit.getPlayer(args[1]);

            if (player == null) {
                sendError(sender, PLAYER_NOT_FOUND);
                return true;
            }
        }

        player.setGameMode(gameMode);

        sendSuccess(sender, "Game mode of " + player.getName() + " changed to " + args[0] + ".");

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

    private GameMode selectGameMode(String input) {
        if (input.equalsIgnoreCase("survival")) return GameMode.SURVIVAL;
        if (input.equalsIgnoreCase("s")) return GameMode.SURVIVAL;
        if (input.equalsIgnoreCase("0")) return GameMode.SURVIVAL;

        if (input.equalsIgnoreCase("creative")) return GameMode.CREATIVE;
        if (input.equalsIgnoreCase("c")) return GameMode.CREATIVE;
        if (input.equalsIgnoreCase("1")) return GameMode.CREATIVE;

        return null;
    }
}
