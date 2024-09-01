package eu.proxyepic.com.commands;

import eu.epicraft.com.data.utils.TextUtil;
import eu.epicraft.com.data.yaml.RankUnit;
import eu.proxyepic.com.Proxy;
import eu.proxyepic.com.manager.WLManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/**
 * Created by AnyKwey
 */
public class MaintenanceCommand extends Command {

    public MaintenanceCommand() {
        super("maintenance", null);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage("§cErreor: You cannot do it.");
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) sender;

        if (RankUnit.getRank(player.getUniqueId()).getPower() < RankUnit.ADMIN.getPower()) {
            player.sendMessage(TextUtil.sendNeedPerm(RankUnit.ADMIN.getName()));
            return;
        }

        if (args.length == 0) {
            player.sendMessage("§cSyntaxe: /maintenance <on/off>");
            return;
        }

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("off")) {
                if (Proxy.getInstance().getConfig("whitelist").getBoolean("whitelist")) {
                    WLManager.denyWhitelist();
                } else {
                    player.sendMessage("§cErreur: Il n'y a aucune maintenance.");
                }
            } else if (args[0].equalsIgnoreCase("on")) {
                if (!Proxy.getInstance().getConfig("whitelist").getBoolean("whitelist")) {
                    WLManager.activeWhitelist();
                    for(ProxiedPlayer players : ProxyServer.getInstance().getPlayers()) {
                        players.disconnect("§6§lEpicraft\n\n§cNous sommes désolé d'interrompre vos parties, nous nous devons de faire une maintenance pour résoudre des soucis.\n§7discord.gg/epicraft");
                    }
                } else {
                    player.sendMessage("§cErreur: Une maintenance est déjà mise en place.");
                }
            } else {
                player.sendMessage("§cSyntaxe: /maintenance <on/off>");
            }
        }
    }
}
