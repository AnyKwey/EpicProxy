package eu.proxyepic.com.commands;

import eu.epicraft.com.data.utils.TextUtil;
import eu.epicraft.com.manager.moderation.StaffManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/**
 * Created by AnyKwey
 */
public class StaffCommand extends Command {

    public StaffCommand() {
        super("staff", null);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer)){
            sender.sendMessage("§cErreor: You cannot do it.");
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) sender;

        if(args.length == 0){
            player.sendMessage(TextUtil.setLineRed());
            player.sendMessage("");
            player.sendMessage(" §6[Staff] §fStaff connecté(s):");
            player.sendMessage("");
            player.sendMessage(StaffManager.getStaffs() + "§f");
            player.sendMessage("");
            player.sendMessage(TextUtil.setLineRed());
            return;
        }

        if(args.length > 0){
            player.sendMessage("§cErreur: L'argument '§f"+args[0]+"§c' est inconnu.");
        }
    }
}
