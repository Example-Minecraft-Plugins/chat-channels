package me.davipccunha.tests.chatchannels.command;

import lombok.RequiredArgsConstructor;
import me.davipccunha.tests.chatchannels.ChatChannelsPlugin;
import me.davipccunha.tests.chatchannels.util.ChatUtils;
import me.davipccunha.utils.server.ServerUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class GlobalCommand implements CommandExecutor {
    final ChatChannelsPlugin plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§cUso: /global <mensagem>");
            return false;
        }

        final boolean isMuted = (sender instanceof Player) && ChatUtils.checkMute(this.plugin.getModerationAPI(), (Player) sender);
        if (isMuted) return true;

        final String message = String.join(" ", args);
        final boolean colorPermission = sender.hasPermission("chat.messages.color");

        final String formattedMessage = ChatUtils.formatGlobalMessage(sender, message, colorPermission);

        final boolean highlightPermission = sender.hasPermission("chat.messages.highlight");

        ServerUtils.messageEveryone(formattedMessage, highlightPermission);

        return true;
    }
}
