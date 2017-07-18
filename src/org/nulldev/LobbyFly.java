package org.nulldev.LobbyFly;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.nulldev.LobbyFly.CmdHandle;

public class Main extends JavaPlugin implements Listener {
	
    public Main plugin;
    public List<String> viewers = new ArrayList<String>();
    public Logger logger = Logger.getLogger("Minecraft");
    public static Main instance;
    public File f;
    public String prefix = null;
    public YamlConfiguration config;
    public Integer item;
    public static Main getInstance() { return instance; }
    
    @Override
    public void onEnable() {
    	this.checkConfig();
        instance = this;
    	Bukkit.getServer().getPluginManager().registerEvents(this, this);
        PluginDescriptionFile pdfFile = this.getDescription();
        this.logger.info("[" + String.valueOf(String.valueOf(pdfFile.getName())) + "] Version: " + 
        pdfFile.getVersion() + " by NullDev for EpticMC has been enabled!");
        this.getCommand("lfly").setExecutor(new CmdHandle());
    }
    
    //This works for 1.8.X
    public String colWrap(String txt){
        String txtDone = null;
        for(int i = 0; i < txt.length(); i++)
        {
            if(txt.charAt(i) == '&'){
            	//This might need an escape
                txtDone += '§';
            }
            else txtDone += txt.charAt(i);
        }
        return txtDone;
    }
    
    private void checkConfig() {
        File config = new File(this.getDataFolder(), "config.yml");
        if (!config.exists()) {
            this.saveDefaultConfig();
            this.getLogger().info("config.yml created.");
        }
        File denylist = new File(this.getDataFolder(), "denylist.yml");
        if (!denylist.exists()) {
        	saveResource("denylist.yml", false); //We do not want to overwrite (false)
            this.getLogger().info("denylist.yml created.");
        }
    }
    
    @EventHandler
    //getTypeId() is deprecated but still works fine. New method would be getType() which returns a Material
    @SuppressWarnings("deprecation")
    public void onItem(PlayerMoveEvent e){
        prefix = Main.getInstance().getConfig().getString("prefix");
    	Player p = e.getPlayer();
    	GameMode gm = p.getGameMode();
	//We don't want to disable fly for OP's or people in GM1
    	if(!p.isOp() && !gm.toString().toLowerCase().equals("creative") && !p.hasPermission("lobyfly.bypass")){
    	    item = Main.getInstance().getConfig().getInt("fly-item-id");
    	    //Check if it's a valid item
    	    if (Material.getMaterial(item) != null && item != null){
    	        if(p.getItemInHand().getTypeId() == item){
    		    if(p.getAllowFlight() == false){
    		        p.setAllowFlight(true);
    			p.setFlying(true);
    		    }
    		}
    		else{
    		    if(p.getAllowFlight() == true){
    		        p.setAllowFlight(false);
    			p.setFlying(false);
    		    }
    		}
    	    }
    	    //Use fallback item
    	    else{
    	        if(p.getItemInHand().getType() == Material.NETHER_STAR){
    		    if(p.getAllowFlight() == false){
    		        p.setAllowFlight(true);
    			p.setFlying(true);
    		    }
    		}
    		else{
    		    if(p.getAllowFlight() == true){
    		        p.setAllowFlight(false);
    			p.setFlying(false);
    		    }
    		}
    		//Telling all OP's (and players with permission lobbyfly.admin that the ID is invalid
    		for(Player op : Bukkit.getOnlinePlayers()){
    	            if(op.hasPermission("lobbyfly.admin") || op.isOp()){
    	                op.sendMessage(colWrap(prefix + " &c&lWARNING&r&f: "+
    	                "Item ID in config is invalid! Using 399 (NetherStar) instead!"));
    	            }
    	        }
    	    }
    	}
    }
    
    public void onDisable() {
        PluginDescriptionFile pdfFile = this.getDescription();
        this.logger.info(String.valueOf("[" + String.valueOf(pdfFile.getName())) + "] Version: " + 
        pdfFile.getVersion() + " by NullDev for EpticMC has been disabled!");
        instance = null;
    }
}
