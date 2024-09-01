package eu.proxyepic.com.commands;

import eu.proxyepic.com.Proxy;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/**
 * Created by AnyKwey
 */
public class LobbyCommand extends Command {

    public LobbyCommand() {
        super("lobby", null, "hub");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer)){
            sender.sendMessage("§cErreor: You cannot do it.");
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) sender;

        if(args.length == 0){
            ServerInfo serverInfo = Proxy.getInstance().getProxy().getServerInfo("lobby1");
            player.connect(serverInfo);
        }

        if(args.length > 0){
            player.sendMessage("§cErreur: L'argument '§f"+args[0]+"§c' est inconnu.");
            return;
        }
    }
}
