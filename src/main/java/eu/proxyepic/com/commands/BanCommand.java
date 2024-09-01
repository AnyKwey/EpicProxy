package eu.proxyepic.com.commands;

import eu.epicraft.com.data.utils.TextUtil;
import eu.epicraft.com.data.yaml.PlayerInfos;
import eu.epicraft.com.data.yaml.RankUnit;
import eu.epicraft.com.manager.game.GameMessage;
import eu.epicraft.com.manager.moderation.BanManager;
import eu.epicraft.com.manager.moderation.RecordManager;
import eu.proxyepic.com.Proxy;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.UUID;

/**
 * Created by AnyKwey
 */
public class BanCommand extends Command {

    public BanCommand() {
        super("ban", null);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage("§cErreor: You cannot do it.");
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) sender;

        if (RankUnit.getRank(player.getUniqueId()).getPower() < RankUnit.MOD.getPower()) {
            player.sendMessage(TextUtil.sendNeedPerm(RankUnit.MOD.getName()));
            return;
        }

        if (args.length < 3) {
            helpMessage(sender);
            return;
        }

        String targetName = args[0];
        if (!PlayerInfos.exist(targetName)) {
            player.sendMessage("§cErreur: Ce joueur ne s'est jamais connecté.");
            return;
        }

        UUID targetUUID = PlayerInfos.getUUID(targetName);
        if (BanManager.isBanned(targetUUID)) {
            player.sendMessage("§cErreur: Ce joueur est déjà banni.");
            return;
        }

        String reason = "";
        for (int i = 2; i < args.length; i++)
            reason = reason + args[i] + " ";

        if (args[1].equalsIgnoreCase("perm")) {
            if (RankUnit.getRank(player.getUniqueId()).getPower() >= RankUnit.MANAGER.getPower()) {
                BanManager.ban(targetUUID, -1L, reason, RankUnit.getRank(player.getUniqueId()).getPrefix() + player.getName());
                player.sendMessage("§6[Sanction] §fla sanction a été appliquée.");
                Proxy.getInstance().getProxy().getPlayer(targetUUID).disconnect(BanManager.banPermMessage(targetUUID));
            } else {
                player.sendMessage("§cErreur: Cette catégorie est uniquement accessible pour les admins.");
                return;
            }
        } else if (args[1].equalsIgnoreCase("cheat")) {
            BanManager.ban(targetUUID, 2592000, reason, RankUnit.getRank(player.getUniqueId()).getPrefix() + player.getName());
            player.sendMessage("§6[Sanction] §fla sanction a été appliquée.");
            RecordManager.addRecorder(targetUUID, reason, RankUnit.getRank(player.getUniqueId()).getPrefix() + player.getName());
            Proxy.getInstance().getProxy().getPlayer(targetUUID).disconnect(BanManager.banMessage(targetUUID));
        } else if (args[1].equalsIgnoreCase("build_incorrect")) {
            BanManager.ban(targetUUID, 7200, reason, RankUnit.getRank(player.getUniqueId()).getPrefix() + player.getName());
            player.sendMessage("§6[Sanction] §fla sanction a été appliquée.");
            RecordManager.addRecorder(targetUUID, reason, RankUnit.getRank(player.getUniqueId()).getPrefix() + player.getName());
            Proxy.getInstance().getProxy().getPlayer(targetUUID).disconnect(BanManager.banMessage(targetUUID));
        } else if (args[1].equalsIgnoreCase("skin_incorrect")) {
            BanManager.ban(targetUUID, 7200, reason, RankUnit.getRank(player.getUniqueId()).getPrefix() + player.getName());
            player.sendMessage("§6[Sanction] §fla sanction a été appliquée.");
            RecordManager.addRecorder(targetUUID, reason, RankUnit.getRank(player.getUniqueId()).getPrefix() + player.getName());
            Proxy.getInstance().getProxy().getPlayer(targetUUID).disconnect(BanManager.banMessage(targetUUID));
        } else if (args[1].equalsIgnoreCase("pseudo_incorrect")) {
            BanManager.ban(targetUUID, 43200, reason, RankUnit.getRank(player.getUniqueId()).getPrefix() + player.getName());
            player.sendMessage("§6[Sanction] §fla sanction a été appliquée.");
            RecordManager.addRecorder(targetUUID, reason, RankUnit.getRank(player.getUniqueId()).getPrefix() + player.getName());
            Proxy.getInstance().getProxy().getPlayer(targetUUID).disconnect(BanManager.banMessage(targetUUID));
        } else if (args[1].equalsIgnoreCase("menace")) {
            BanManager.ban(targetUUID, 2592000, reason, RankUnit.getRank(player.getUniqueId()).getPrefix() + player.getName());
            player.sendMessage("§6[Sanction] §fla sanction a été appliquée.");
            RecordManager.addRecorder(targetUUID, reason, RankUnit.getRank(player.getUniqueId()).getPrefix() + player.getName());
            Proxy.getInstance().getProxy().getPlayer(targetUUID).disconnect(BanManager.banMessage(targetUUID));
        } else if (args[1].equalsIgnoreCase("alliance")) {
            BanManager.ban(targetUUID, 43200, reason, RankUnit.getRank(player.getUniqueId()).getPrefix() + player.getName());
            player.sendMessage("§6[Sanction] §fla sanction a été appliquée.");
            RecordManager.addRecorder(targetUUID, reason, RankUnit.getRank(player.getUniqueId()).getPrefix() + player.getName());
            Proxy.getInstance().getProxy().getPlayer(targetUUID).disconnect(BanManager.banMessage(targetUUID));
        } else {
            player.sendMessage("§cLes différents catégories sont:");
            player.sendMessage(" §c- cheat");
            player.sendMessage(" §c- build_incorrect");
            player.sendMessage(" §c- skin_incorrect");
            player.sendMessage(" §c- pseudo_incorrect");
            player.sendMessage(" §c- menace");
            player.sendMessage(" §c- alliance");
            player.sendMessage(" ");
        }
    }

    public void helpMessage(CommandSender sender) {
        sender.sendMessage("§cSyntaxe: /ban <pseudo> <catégorie> <raison>");
    }
}
