package ltd.icecold.orangeengine;

import ltd.icecold.orangeengine.api.event.PluginVerifyPostEvent;
import ltd.icecold.orangeengine.mythicmobs.listener.MythicListener_v4;
import ltd.icecold.orangeengine.mythicmobs.listener.MythicListener_v5;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class OrangeMechanic extends JavaPlugin{
    @Override
    public void onEnable() {
        boolean isNewMM = false;
        try {
            Class.forName("io.lumine.mythic.api.skills.SkillResult");
            isNewMM = true;
        } catch (ClassNotFoundException var3) {
        }
        if (isNewMM) {
            Bukkit.getConsoleSender().sendMessage("§6[§eOrangeMechanic§6] > " + ChatColor.AQUA + "已检测到" + ChatColor.GREEN + "MythicMobs " + ChatColor.GOLD + "v5");
            Bukkit.getPluginManager().registerEvents(new MythicListener_v5(),this);
        } else {
            Bukkit.getConsoleSender().sendMessage("§6[§eOrangeMechanic§6] > " + ChatColor.AQUA + "已检测到" + ChatColor.GREEN + "MythicMobs " + ChatColor.GOLD + "v4");
            Bukkit.getPluginManager().registerEvents(new MythicListener_v4(),this);
        }
    }
}
