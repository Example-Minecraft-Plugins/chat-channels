package me.davipccunha.tests.chatchannels.command;

import lombok.RequiredArgsConstructor;
import me.davipccunha.tests.chatchannels.cache.LastTellCache;
import me.davipccunha.tests.chatchannels.util.ChatUtils;
import me.davipccunha.utils.messages.ErrorMessages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class TellCommand implements CommandExecutor {
    private final LastTellCache cache;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 2) {
            sender.sendMessage("§cUso: /tell <jogador> <mensagem>");
            return false;
        }

        final String targetName = args[0];
        final Player target = Bukkit.getServer().getPlayer(targetName);

        if (target == null) {
            sender.sendMessage(ErrorMessages.PLAYER_NOT_ONLINE.getMessage());
            return true;
        }

        final String message = String.join(" ", args).replaceFirst(targetName, "").trim();
        final String formattedSenderMessage = ChatUtils.formatTellMessage(sender, message, true);
        final String formattedTargetMessage = ChatUtils.formatTellMessage(sender, message, false);

        sender.sendMessage(formattedSenderMessage);
        target.sendMessage(formattedTargetMessage);

        this.cache.setLastTell(target.getName(), sender.getName());

        return true;
    }
}
