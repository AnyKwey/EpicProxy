package eu.proxyepic.com.commands;

import eu.epicraft.com.data.utils.TextUtil;
import eu.epicraft.com.data.yaml.PlayerInfos;
import eu.epicraft.com.data.yaml.RankUnit;
import eu.epicraft.com.manager.game.GameMessage;
import eu.epicraft.com.manager.moderation.BanManager;
import eu.epicraft.com.manager.moderation.MuteManager;
import eu.epicraft.com.manager.moderation.RecordManager;
import eu.proxyepic.com.Proxy;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.UUID;

/**
 * Created by AnyKwey
 */
public class MuteCommand extends Command {

    public MuteCommand() {
        super("mute", null);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage("§cErreor: You cannot do it.");
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) sender;

        if (RankUnit.getRank(player.getUniqueId()).getPower() < RankUnit.HELPER.getPower()) {
            player.sendMessage(TextUtil.sendNeedPerm(RankUnit.HELPER.getName()));
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
        if (MuteManager.isMute(targetUUID)) {
            player.sendMessage("§cErreur: Ce joueur est déjà réduit au silence.");
            return;
        }

        String reason = "";
        for (int i = 2; i < args.length; i++)
            reason = reason + args[i] + " ";

        if (args[1].equalsIgnoreCase("flood")) {
            MuteManager.mute(targetUUID, 1800, reason, RankUnit.getRank(player.getUniqueId()).getPrefix() + player.getName());
            player.sendMessage("§6[Sanction] §fla sanction a été appliquée.");
            RecordManager.addRecorder(targetUUID, reason, RankUnit.getRank(player.getUniqueId()).getPrefix() + player.getName());
            Proxy.getInstance().getProxy().getPlayer(targetUUID).sendMessage(MuteManager.muteMessage(targetUUID));
        } else if (args[1].equalsIgnoreCase("spam")) {
            MuteManager.mute(targetUUID, 1800, reason, RankUnit.getRank(player.getUniqueId()).getPrefix() + player.getName());
            player.sendMessage("§6[Sanction] §fla sanction a été appliquée.");
            RecordManager.addRecorder(targetUUID, reason, RankUnit.getRank(player.getUniqueId()).getPrefix() + player.getName());
            Proxy.getInstance().getProxy().getPlayer(targetUUID).sendMessage(MuteManager.muteMessage(targetUUID));
        } else if (args[1].equalsIgnoreCase("provocation")) {
            MuteManager.mute(targetUUID, 7200, reason, RankUnit.getRank(player.getUniqueId()).getPrefix() + player.getName());
            player.sendMessage("§6[Sanction] §fla sanction a été appliquée.");
            RecordManager.addRecorder(targetUUID, reason, RankUnit.getRank(player.getUniqueId()).getPrefix() + player.getName());
            Proxy.getInstance().getProxy().getPlayer(targetUUID).sendMessage(MuteManager.muteMessage(targetUUID));
        } else if (args[1].equalsIgnoreCase("mauvais_language")) {
            MuteManager.mute(targetUUID, 7200, reason, RankUnit.getRank(player.getUniqueId()).getPrefix() + player.getName());
            player.sendMessage("§6[Sanction] §fla sanction a été appliquée.");
            RecordManager.addRecorder(targetUUID, reason, RankUnit.getRank(player.getUniqueId()).getPrefix() + player.getName());
            Proxy.getInstance().getProxy().getPlayer(targetUUID).sendMessage(MuteManager.muteMessage(targetUUID));
        } else if (args[1].equalsIgnoreCase("fausse_information")) {
            RecordManager.addRecorder(targetUUID, reason, RankUnit.getRank(player.getUniqueId()).getPrefix() + player.getName());
            MuteManager.mute(targetUUID, 7200, reason, RankUnit.getRank(player.getUniqueId()).getPrefix() + player.getName());
            player.sendMessage("§6[Sanction] §fla sanction a été appliquée.");
            RecordManager.addRecorder(targetUUID, reason, RankUnit.getRank(player.getUniqueId()).getPrefix() + player.getName());
            Proxy.getInstance().getProxy().getPlayer(targetUUID).sendMessage(MuteManager.muteMessage(targetUUID));
        } else if (args[1].equalsIgnoreCase("incitation_a_l_infraction")) {
            RecordManager.addRecorder(targetUUID, reason, RankUnit.getRank(player.getUniqueId()).getPrefix() + player.getName());
            MuteManager.mute(targetUUID, 7200, reason, RankUnit.getRank(player.getUniqueId()).getPrefix() + player.getName());
            player.sendMessage("§6[Sanction] §fla sanction a été appliquée.");
            RecordManager.addRecorder(targetUUID, reason, RankUnit.getRank(player.getUniqueId()).getPrefix() + player.getName());
            Proxy.getInstance().getProxy().getPlayer(targetUUID).sendMessage(MuteManager.muteMessage(targetUUID));
        } else if (args[1].equalsIgnoreCase("message_interdit")) {
            RecordManager.addRecorder(targetUUID, reason, RankUnit.getRank(player.getUniqueId()).getPrefix() + player.getName());
            MuteManager.mute(targetUUID, 7200, reason, RankUnit.getRank(player.getUniqueId()).getPrefix() + player.getName());
            player.sendMessage("§6[Sanction] §fla sanction a été appliquée.");
            RecordManager.addRecorder(targetUUID, reason, RankUnit.getRank(player.getUniqueId()).getPrefix() + player.getName());
            Proxy.getInstance().getProxy().getPlayer(targetUUID).sendMessage(MuteManager.muteMessage(targetUUID));
        } else {
            player.sendMessage("§cLes différents catégories sont:");
            player.sendMessage(" §c- flood");
            player.sendMessage(" §c- spam");
            player.sendMessage(" §c- provocation");
            player.sendMessage(" §c- mauvais_language");
            player.sendMessage(" §c- fausse_information");
            player.sendMessage(" §c- incitation_a_l_infraction");
            player.sendMessage(" §c- message_interdit");
            player.sendMessage(" ");
        }
    }

    public void helpMessage(CommandSender sender) {
        sender.sendMessage("§cSyntaxe: /mute <pseudo> <catégorie> <raison>");
    }
}
