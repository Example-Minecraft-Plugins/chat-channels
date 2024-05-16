package me.davipccunha.tests.chatchannels.command;

import lombok.RequiredArgsConstructor;
import me.davipccunha.tests.chatchannels.cache.LastTellCache;
import me.davipccunha.utils.messages.ErrorMessages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class ReplyCommand implements CommandExecutor {
    private final LastTellCache cache;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ErrorMessages.EXECUTOR_NOT_PLAYER.getMessage());
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage("§cUso: /responder <mensagem>");
            return false;
        }

        final String receiverName = this.cache.getLastTell(sender.getName());

        if (receiverName == null) {
            sender.sendMessage("§cVocê não recebeu nenhuma mensagem privada recentemente.");
            return true;
        }

        final Player receiver = Bukkit.getServer().getPlayer(receiverName);

        if (receiver == null) {
            sender.sendMessage(ErrorMessages.PLAYER_NOT_ONLINE.getMessage());
            this.cache.removeLastTell(sender.getName());
            return true;
        }

        final String message = String.join(" ", args);

        Bukkit.dispatchCommand(sender, "tell " + receiver.getName() + " " + message);

        return true;
    }
}
