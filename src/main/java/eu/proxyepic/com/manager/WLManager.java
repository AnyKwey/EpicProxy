package eu.proxyepic.com.manager;

import eu.proxyepic.com.Proxy;

import java.util.List;

public class WLManager {

    public static void activeWhitelist(){
        Proxy.getInstance().getConfig("whitelist").set("whitelist", true);
    }

    public static void denyWhitelist(){
        Proxy.getInstance().getConfig("whitelist").set("whitelist", false);
    }

    public static String kickWhitelistMessage(){
        return "§cEpicraft \n§eVous n'êtes pas inscrit sur la liste-blanche.";
    }

    public static void add(String name){
        Proxy.getInstance().getConfig("whitelist").getStringList("players").add(name);
    }

    public static void remove(String name){
        Proxy.getInstance().getConfig("whitelist").getStringList("players").remove(name);
    }

    public static boolean isWhitelisted(String name){
       return Proxy.getInstance().getConfig("whitelist").getStringList("players").contains(name);
    }

    public static List<String> getList(){
        return Proxy.getInstance().getConfig("whitelist").getStringList("players");
    }
}
