package me.RyanStewart.Fullbright;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin{
	
	public void onEnable(){
		PluginManager pm = this.getServer().getPluginManager();
		Permission self = new Permission("fullbright.self");
		Permission others = new Permission("fullbright.others");
		pm.addPermission(self);
		pm.addPermission(others);
	}
	
	public void onDisable(){
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (cmd.getName().equalsIgnoreCase("fullbright") && sender instanceof Player){
			Player plr = (Player) sender;
			if (args.length == 0){
				//for self
				if (plr.hasPermission("fullbright.self")){
					if (!plr.hasPotionEffect(PotionEffectType.NIGHT_VISION)){
						PotionEffect vis = new PotionEffect(PotionEffectType.NIGHT_VISION, 99999999, 1000);
						plr.addPotionEffect(vis);
						plr.sendMessage(ChatColor.GREEN + "Fullbright enabled.");
					}else{
						plr.removePotionEffect(PotionEffectType.NIGHT_VISION);
						plr.sendMessage(ChatColor.RED + "Fullbright disabled.");
					}
				}else{
					plr.sendMessage(ChatColor.RED + "You do not have permission to do this!");
				}
			}else if (args.length == 1){
				//for other player
				if (plr.hasPermission("fullbright.others")){
					boolean plrFound = false;
					for (Player p : this.getServer().getOnlinePlayers()){
						if (args[0].equalsIgnoreCase(p.getName())){
							plrFound = true;
						}
					}
					if (plrFound == true){
						if (!this.getServer().getPlayer(args[0]).hasPotionEffect(PotionEffectType.NIGHT_VISION)){
							PotionEffect vis = new PotionEffect(PotionEffectType.NIGHT_VISION, 99999999, 1000);
							plr.addPotionEffect(vis);
							plr.sendMessage(ChatColor.GREEN + "Fullbright enabled for " + ChatColor.GOLD + args[0]);
							this.getServer().getPlayer(args[0]).sendMessage(ChatColor.GREEN + "Fullbright enabled by " + ChatColor.GOLD + args[0]);
						}else{
							plr.removePotionEffect(PotionEffectType.NIGHT_VISION);
							plr.sendMessage(ChatColor.RED + "Fullbright disabled for " + ChatColor.GOLD + args[0]);
							this.getServer().getPlayer(args[0]).sendMessage(ChatColor.RED + "Fullbright disabled by " + ChatColor.GOLD + args[0]);
						}
					}else{
						plr.sendMessage(ChatColor.RED + "Player \"" + args[0] + "\" not found.");
					}
				}else{
					plr.sendMessage(ChatColor.RED + "You do not have permission to do this!");
				}
			}else{
				plr.sendMessage(ChatColor.RED + "Too many arguments!");
			}
			return true;
		}
		return false;
	}
	
}
