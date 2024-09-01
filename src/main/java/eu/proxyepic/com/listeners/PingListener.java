package eu.proxyepic.com.listeners;

import eu.epicraft.com.data.tools.DefaultFontInfo;
import eu.proxyepic.com.Proxy;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.Favicon;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Created by AnyKwey
 */
public class PingListener implements Listener {

    private static final int CENTER_PX = 154;

    @EventHandler
    public void onJoin(ProxyPingEvent e){
        ServerPing serverPing = e.getResponse();

        serverPing.setDescriptionComponent(new TextComponent(getCenteredMessage(Proxy.getInstance().getConfig("config").getString("motd.header").replace("\n", "\n").replace("&", "§")) +
                "\n" + getCenteredMessage(Proxy.getInstance().getConfig("config").getString("motd.footer").replace("\n", "\n").replace("&", "§"))));

        serverPing.setPlayers(new ServerPing.Players(0, Proxy.getInstance().getProxy().getOnlineCount(), serverPing.getPlayers().getSample()));

        if(e.getConnection().getVersion() == 763){
            serverPing.setVersion(new ServerPing.Protocol(Proxy.getInstance().getConfig("config").getString("motd.description").replace("&", "§"), serverPing.getVersion().getProtocol()));
        } else if(e.getConnection().getVersion() < 763 || e.getConnection().getVersion() > 763) {
            serverPing.setVersion(new ServerPing.Protocol("§c" + Proxy.getInstance().getConfig("config").getString("version"), serverPing.getVersion().getProtocol()));
        }

        try {
            serverPing.setFavicon(Favicon.create(ImageIO.read(new File("icon.png"))));
        } catch (IOException e1){
            e1.printStackTrace();
        }

        e.setResponse(serverPing);
   }

    public static String getCenteredMessage(String message) {
        if (message == null || message.equals("")) return "";

        message = ChatColor.translateAlternateColorCodes('&', message);
        message = message.replace("<center>", "").replace("</center>", "");

        int messagePxSize = 0;
        boolean previousCode = false;
        boolean isBold = false;

        for (char c : message.toCharArray()) {
            if (c == '�') {
                previousCode = true;

            } else if (previousCode) {
                previousCode = false;
                if (c == 'l' || c == 'L') {
                    isBold = true;
                } else isBold = false;

            } else {
                DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
                messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                messagePxSize++;
            }
        }

        int halvedMessageSize = messagePxSize / 2;
        int toCompensate = CENTER_PX - halvedMessageSize;
        int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
        int compensated = 0;
        StringBuilder sb = new StringBuilder();
        while (compensated < toCompensate) {
            sb.append(" ");
            compensated += spaceLength;
        }

        return sb.toString() + message;

    }
}
