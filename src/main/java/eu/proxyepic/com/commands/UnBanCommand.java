package eu.proxyepic.com.commands;

import eu.epicraft.com.data.utils.TextUtil;
import eu.epicraft.com.data.yaml.PlayerInfos;
import eu.epicraft.com.data.yaml.RankUnit;
import eu.epicraft.com.manager.game.GameMessage;
import eu.epicraft.com.manager.moderation.BanManager;
import eu.epicraft.com.manager.moderation.MuteManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.UUID;

/**
 * Created by AnyKwey
 */
public class UnBanCommand extends Command {

    public UnBanCommand() {
        super("unban", null);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage("§cErreor: You cannot do it.");
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) sender;

        if (RankUnit.getRank(player.getUniqueId()).getPower() < RankUnit.MANAGER.getPower()) {
            player.sendMessage(TextUtil.sendNeedPerm(RankUnit.MANAGER.getName()));
            return;
        }

        if (args.length != 1) {
            player.sendMessage("§cSyntaxe: /unban <pseudo>");
            return;
        }
        String targetName = args[0];
        if (!PlayerInfos.exist(targetName)) {
            player.sendMessage("§cErreur: Ce joueur ne s'est jamais connecté.");
            return;
        }
        UUID targetUUID = PlayerInfos.getUUID(targetName);
        if (!BanManager.isBanned(targetUUID)) {
            player.sendMessage("§cErreur: Ce joueur n'est pas banni.");
            return;
        }
        BanManager.unban(targetUUID);
        player.sendMessage("§8[§a§l✔§8] §aVous avez débanni " + targetName + ".");
    }
}
