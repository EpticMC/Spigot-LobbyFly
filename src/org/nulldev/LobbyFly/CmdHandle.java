package org.nulldev.LobbyFly;
 
import java.io.File;
import java.util.ArrayList;
import java.util.List;
 
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.nulldev.LobbyFly.Main;

public class CmdHandle implements CommandExecutor {
 
    Main main = new Main();
    public List<?> denylist = new ArrayList<String>();
    public String prefix = Main.getInstance().getConfig().getString("prefix");
    public static CmdHandle inst;
    public CmdHandle getInstance() { return inst; }
    public File denyyml = new File(main.getDataFolder(), "denylist.yml");
    private FileConfiguration denys;
    public FileConfiguration getOtherConfig() { return this.denys; }
 
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] a) {
        inst = this;
        @SuppressWarnings("unused")
        Player p; //p = (Player)sender;
        //Need to find a better way to do the Colwrap without always writing Main()
        if (!sender.hasPermission("lobbyfly.admin") || !sender.isOp()) {
            sender.sendMessage(main.colWrap(prefix + " &cNo permission!"));
            return true;
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage(main.colWrap(prefix + " &cThis can only be executed ingame!"));
            return true;
        }
        if (a.length < 1 || !a[0].equals("deny") && a[0].equals("denylist") && !a[0].equals("undeny") || a[0].equalsIgnoreCase("help")) {
            sender.sendMessage(main.colWrap(prefix + " &fHelp Menu:"));
            sender.sendMessage(main.colWrap("&b----------&9============== &6&lHelp &9==============&b----------"));
            sender.sendMessage(main.colWrap("&b/lfly help               &f&l| &9Display this help menu"));
            sender.sendMessage(main.colWrap("&b/lfly deny (username)    &f&l| &9Deny a user from flying"));
            sender.sendMessage(main.colWrap("&b/lfly undeny (username)  &f&l| &9Allow a user to fly again"));
            sender.sendMessage(main.colWrap("&b/lfly denylist           &f&l| &9List all denied users"));
            sender.sendMessage(main.colWrap("&b/lfly status (username)  &f&l| &9See if a user is denied"));
            sender.sendMessage(main.colWrap("&b------------------------------------------------------"));
            sender.sendMessage(main.colWrap("&6       &l~ &b&lNOTE: &bYou can use &9/lobbyfly &bor &9/lfly ~"));
            sender.sendMessage(main.colWrap("This plugin was coded by &a&lNullDev &ffor &c&lEptic&4&lMC&f.com"));
            sender.sendMessage(main.colWrap("&b======================================================"));
            return true;
        }
        if (a[0].equalsIgnoreCase("deny")) {
            String toDeny = a[1];
            this.getOtherConfig().set("denied", toDeny); //No idea if that works as expected
            return true;
        }
        if (a[0].equalsIgnoreCase("denylist")) {
            denylist = this.getInstance().getOtherConfig().getList("denied");
            if (denylist != null && denylist.size() != 0){ //Size instead of length since its a list array
                sender.sendMessage(main.colWrap(prefix + " &fList of denied users:"));
                sender.sendMessage(main.colWrap(prefix + " &f" + denylist)); //this will probs be wrong formatted 
                //TODO: Format correctly
            }
            else sender.sendMessage(main.colWrap(prefix + " &fThere are no denied users."));
            return true; //Is this needed? 
        }
        if (a[0].equalsIgnoreCase("undeny")) { p = (Player)sender; }
    }
}
