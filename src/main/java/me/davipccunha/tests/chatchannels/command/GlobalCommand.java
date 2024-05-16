package me.davipccunha.tests.chatchannels.command;

import me.davipccunha.tests.chatchannels.util.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class GlobalCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§cUso: /global <mensagem>");
            return false;
        }

        final String message = String.join(" ", args);
        final boolean colorPermission = sender.hasPermission("chat.messages.color");

        final String formattedMessage = ChatUtils.formatGlobalMessage(sender, message, colorPermission);

        final boolean highlightPermission = sender.hasPermission("chat.messages.highlight");

        if (highlightPermission) Bukkit.getServer().broadcastMessage(" ");
        Bukkit.getServer().broadcastMessage(formattedMessage);
        if (highlightPermission) Bukkit.getServer().broadcastMessage(" ");

        return true;
    }
}
