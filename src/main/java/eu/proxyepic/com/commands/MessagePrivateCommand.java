package eu.proxyepic.com.commands;

import eu.epicraft.com.MainBungee;
import eu.epicraft.com.data.yaml.PlayerInfos;
import eu.epicraft.com.data.yaml.PlayerSettings;
import eu.epicraft.com.data.yaml.RankUnit;
import eu.epicraft.com.manager.game.GameMessage;
import eu.epicraft.com.manager.players.FriendManager;
import eu.proxyepic.com.Proxy;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.UUID;

/**
 * Created by AnyKwey
 */
public class MessagePrivateCommand extends Command {

    public MessagePrivateCommand() {
        super("mp", null, "msg", "m");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer)){
            sender.sendMessage("§cErreor: You cannot do it.");
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) sender;

        if(args.length == 0){
            player.sendMessage("§cSyntaxe: /msg <pseudo> <message>");
            return;
        }

        if(args.length > 1) {
            String targetName = args[0];

            if (!PlayerInfos.exist(targetName)) {
                player.sendMessage("§cErreur: Ce joueur ne s'est jamais connecté.");
                return;
            }

            if (PlayerInfos.exist(targetName) && !(Proxy.getInstance().getProxy().getPlayer(targetName).isConnected())) {
                player.sendMessage("§cErreur: Ce joueur est hors-ligne.");
                return;
            }

            String msg = "";
            for (int i = 1; i < args.length; i++)
                msg = msg + args[i] + " ";

            /**if(targetName == player.getName()){
             player.sendMessage("§cErreur: Vous ne pouvez pas vous envoyez de message.");
             return;
             }**/

            UUID targetUUID = PlayerInfos.getUUID(targetName);

            if (PlayerSettings.getStatusOf(targetUUID, "mp_private") == 0) {
                if(PlayerSettings.getStatusOf(player.getUniqueId(), "mp_private") > 0){
                    player.sendMessage("§bVous acceptez que ce joueur puisse vous répondre.");
                }
                player.sendMessage("§6[§7Moi §6-> " + RankUnit.getRank(targetUUID).getPrefix() + targetName + "§6] §f" + msg);
                Proxy.getInstance().getProxy().getPlayer(targetName).sendMessage("§6[" + RankUnit.getRank(player.getUniqueId()).getPrefix() + player.getName() + " §6-> §7Moi§6] §f" + msg);
                MainBungee.message.put(player, Proxy.getInstance().getProxy().getPlayer(targetName));
            } else if (PlayerSettings.getStatusOf(targetUUID, "mp_private") == 1 && FriendManager.isFrendsWith(targetName, player.getName())) {
                player.sendMessage("§6[§7Moi §6-> " + RankUnit.getRank(targetUUID).getPrefix() + targetName + "§6] §f" + msg);
                Proxy.getInstance().getProxy().getPlayer(targetName).sendMessage("§6[" + RankUnit.getRank(player.getUniqueId()).getPrefix() + player.getName() + " §6-> §7Moi§6] §f" + msg);
                MainBungee.message.put(player, Proxy.getInstance().getProxy().getPlayer(targetName));
            } else if (PlayerSettings.getStatusOf(targetUUID, "mp_private") == 1 && !(FriendManager.isFrendsWith(targetName, player.getName()))) {
                player.sendMessage("§cErreur: Ce joueur accepte uniquement les messages de ses amis.");
            } else if (PlayerSettings.getStatusOf(targetUUID, "mp_private") == 2) {
                player.sendMessage("§cErreur: Ce joueur n'accepte pas les messages privées.");
            }
        }
    }
}
