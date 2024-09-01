package eu.proxyepic.com.commands;

import eu.epicraft.com.data.utils.TextUtil;
import eu.epicraft.com.data.yaml.PlayerInfos;
import eu.epicraft.com.data.yaml.RankUnit;
import eu.epicraft.com.manager.game.GameMessage;
import eu.proxyepic.com.Proxy;
import eu.proxyepic.com.manager.WLManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/**
 * Created by AnyKwey
 */
public class WhitelistCommand extends Command {

    public WhitelistCommand() {
        super("whitelist", null);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer)){
            sender.sendMessage("§cErreor: You cannot do it.");
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) sender;

        if (RankUnit.getRank(player.getUniqueId()).getPower() < RankUnit.ADMIN.getPower()) {
            player.sendMessage(TextUtil.sendNeedPerm(RankUnit.ADMIN.getName()));
            return;
        }

        if(args.length == 0){
            player.sendMessage("§cSyntaxe: /whitelist <add/remove/list> (pseudo)");
            return;
        }

        if(args.length == 1){
            if(args[0].equalsIgnoreCase("list")){
                if(Proxy.getInstance().getConfig("whitelist").getStringList("players").isEmpty()) {
                    player.sendMessage("§cIl n'y a pas de joueur dans la liste-blanche.");
                    return;
                }
                player.sendMessage("§c" + WLManager.getList());
            }
        }

        if(args.length > 1){
            String targetName = args[1];

            if(args[0].equalsIgnoreCase("add")){
                if(!PlayerInfos.exist(targetName)){
                    player.sendMessage("§cErreur: Ce joueur ne s'est jamais connecté.");
                    return;
                }

                if(WLManager.isWhitelisted(targetName)){
                    player.sendMessage("§cErreur: Ce joueur est déjà dans la liste-blanche.");
                    return;
                }

                WLManager.add(targetName);
                player.sendMessage("§8[§a§l✔§8] §a" + targetName + " §fa été ajouté à la liste-blanche.");
            } else if(args[0].equalsIgnoreCase("remove")){
                if(!PlayerInfos.exist(targetName)){
                    player.sendMessage(GameMessage.PLAYER_NEVER_CONNECT.getMessage());
                    return;
                }

                if(!WLManager.isWhitelisted(targetName)){
                    player.sendMessage("§cErreur: Ce joueur n'est pas dans la liste-blanche.");
                    return;
                }

                WLManager.remove(targetName);
                player.sendMessage("§8[§a§l✔§8] §a" + targetName + " §fa été retiré de la liste-blanche.");
            } else {
                player.sendMessage("§cSyntaxe: /whitelist <add/remove/list> (pseudo)");
            }
        }
    }
}
