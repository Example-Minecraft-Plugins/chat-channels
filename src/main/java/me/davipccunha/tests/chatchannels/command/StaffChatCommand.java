package me.davipccunha.tests.chatchannels.command;

import me.davipccunha.tests.chatchannels.util.ChatUtils;
import me.davipccunha.utils.messages.ErrorMessages;
import me.davipccunha.utils.server.ServerUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class StaffChatCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("chat.admin.staffchat")) {
            sender.sendMessage(ErrorMessages.NO_PERMISSION.getMessage());
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage("§cUso: /staffchat <mensagem>");
            return false;
        }

        final String message = String.join(" ", args);

        final String formattedMessage = ChatUtils.formatStaffChatMessage(sender, message);
        final boolean highlightPermission = sender.hasPermission("chat.messages.highlight");

        ServerUtils.messageEveryoneWithPermission(formattedMessage, highlightPermission,"chat.admin.staffchat");

        return true;
    }
}
