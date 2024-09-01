package eu.proxyepic.com.commands;

import eu.epicraft.com.MainBungee;
import eu.epicraft.com.data.yaml.PlayerInfos;
import eu.epicraft.com.data.yaml.PlayerSettings;
import eu.epicraft.com.data.yaml.RankUnit;
import eu.epicraft.com.manager.players.FriendManager;
import eu.proxyepic.com.Proxy;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.UUID;

/**
 * Created by AnyKwey
 */
public class ReplyCommand extends Command {

    public ReplyCommand() {
        super("reply", null, "r", "repondre");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer)){
            sender.sendMessage("§cErreor: You cannot do it.");
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) sender;

        if(args.length == 0){
            player.sendMessage("§cSyntaxe: /r <pseudo> <message>");
            return;
        }

        if(MainBungee.message.containsKey(player)){
            if(!MainBungee.message.get(player).isConnected()){
                player.sendMessage("§cErreur: Ce joueur est hors-ligne.");
            } else {
                ProxiedPlayer target = MainBungee.message.get(player);

                String msg = "";
                for (int i = 1; i < args.length; i++)
                    msg = msg + args[i] + " ";

                /**if(targetName == player.getName()){
                 player.sendMessage("§cErreur: Vous ne pouvez pas vous envoyez de message.");
                 return;
                 }**/

                UUID targetUUID = PlayerInfos.getUUID(target.getName());

                player.sendMessage("§6[§7Moi §6-> " + RankUnit.getRank(targetUUID).getPrefix() + target.getName() + "§6] §f" + msg);
                target.sendMessage("§6[" + RankUnit.getRank(player.getUniqueId()).getPrefix() + player.getName() + " §6-> §7Moi§6] §f" + msg);
            }
        } else {
            player.sendMessage("§cErreur: Vous n'avez personne à qui répondre.");
        }
    }
}
