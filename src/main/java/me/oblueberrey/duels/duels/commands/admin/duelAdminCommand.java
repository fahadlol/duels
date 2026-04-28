package me.oblueberrey.duels.duels.commands.admin;

import me.oblueberrey.duels.Duels;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class duelAdminCommand implements CommandExecutor, TabCompleter {

    private final Duels plugin;

    public duelAdminCommand(Duels plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players");
            return true;
        }

        if (!sender.hasPermission("duels.setspawn")) {
            plugin.getMessageManager().send(player, "permissions.no-perms");
            return true;
        }

        if (args.length == 0) {
            plugin.getMessageManager().send(player, "dueladmin.help-message");
            return true;
        }

        switch (args[0].toLowerCase()) {

            case "setspawn":
                setSpawn(player);
                break;

            case "arena":
                if (args.length < 2) {
                    plugin.getMessageManager().send(player, "dueladmin.arena-help-message");
                    return true;
                }

                switch (args[1].toLowerCase()) {

                    case "create":
                        if (args.length < 3) {
                            plugin.getMessageManager().send(player, "dueladmin.arena-create-usage");
                            return true;
                        }
                        createArena(args[2], player);
                        break;

                    case "pos":
                        if (args.length < 4) return true;

                        if (!args[2].equals("1") && !args[2].equals("2")) return true;

                        setArenaPos(Integer.parseInt(args[2]), args[3], player);
                        break;

                    case "wandcopy":
                        plugin.getMessageManager().send(player, "dueladmin.wandcopy-message");
                        break;

                    case "setspawn":
                        if (args.length < 4) return true;

                        if (!args[2].equals("1") && !args[2].equals("2")) return true;

                        setArenaSpawn(args[3], player, Integer.parseInt(args[2]));
                        break;

                    case "delete":
                        if (args.length < 3) return true;
                        deleteArena(args[2], player);
                        break;

                    case "enable":
                        if (args.length < 3) return true;
                        enableArena(args[2], player);
                        break;

                    case "disable":
                        if (args.length < 3) return true;
                        disableArena(args[2], player);
                        break;

                    case "list":
                        listArena(player);
                        break;

                    default:
                        plugin.getMessageManager().send(player, "dueladmin.arena-help-message");
                        break;
                }
                break;

            case "kit":
                if (args.length < 2) {
                    plugin.getMessageManager().send(player, "dueladmin.kit-help-message");
                    return true;
                }

                switch (args[1].toLowerCase()) {

                    case "create":
                        kitCreate(args[2], player);
                        break;
                    case "delete":
                        deleteKit(args[2], player);
                        break;
                    case "set":
                        setKit(args[2], player);
                        break;
                    case "load":
                        //duels kit load player
                        Player target = plugin.getServer().getPlayer(args[3]);
                        if(target == null) {
                            plugin.getMessageManager().send(player, "target-not-found");
                            return true;
                        } else if (!(target.isOnline())) {
                            plugin.getMessageManager().send(player, "target-not-online");
                            return true;
                        }
                        loadKit(args[2], target);
                        break;
                    case "list":
                        kitList(args[2], player);
                        break;

                    default:
                        plugin.getMessageManager().send(player, "dueladmin.kit-help-message");
                        break;
                }
                break;

            case "reload":
                reload(player);
                plugin.getMessageManager().send(player, "dueladmin.reload-message");
                break;

            default:
                plugin.getMessageManager().send(player, "dueladmin.help-message");
                break;
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        List<String> list = new ArrayList<>();

        if (args.length == 1) {
            list.add("setspawn");
            list.add("arena");
            list.add("kit");
            list.add("reload");
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("arena")) {
            list.add("create");
            list.add("pos");
            list.add("wandcopy");
            list.add("setspawn");
            list.add("delete");
            list.add("enable");
            list.add("disable");
            list.add("list");
        }

        return list;
    }

    public void setSpawn(Player player) {
        plugin.getDuelManager().setSpawn(player);
    }

    public void createArena(String arenaName, Player player) {
        if (plugin.getDuelManager().arenaExists(arenaName)) {
            plugin.getMessageManager().send(player, "dueladmin.arena-create-exists");
            return;
        }

        FileConfiguration arenas = plugin.getConfigManager().getConfig("arenas.yml");
        arenas.set("arenas." + arenaName + ".enabled", false);
        plugin.saveConfig();
    }

    public void setArenaSpawn(String arenaName, Player player, Integer pos) {

        if (!plugin.getDuelManager().arenaExists(arenaName)) {
            plugin.getMessageManager().send(player, "dueladmin.arena-not-found");
            return;
        }

        if (pos != 1 && pos != 2) {
            plugin.getMessageManager().send(player, "dueladmin.arena-invalid-pos");
            return;
        }

        FileConfiguration arenas = plugin.getConfigManager().getConfig("arenas.yml");
        Location loc = player.getLocation();

        arenas.set("arenas." + arenaName + ".spawn.pos" + pos, loc);
        plugin.saveConfig();

        plugin.getMessageManager().send(player, "dueladmin.arena-spawn-set");
    }

    public void setArenaPos(Integer pos, String arenaName, Player player) {

        if (!plugin.getDuelManager().arenaExists(arenaName)) {
            plugin.getMessageManager().send(player, "dueladmin.arena-not-found");
            return;
        }

        if (pos != 1 && pos != 2) {
            plugin.getMessageManager().send(player, "dueladmin.arena-invalid-pos");
            return;
        }

        FileConfiguration arenas = plugin.getConfigManager().getConfig("arenas.yml");
        Location loc = player.getLocation();

        arenas.set("arenas." + arenaName + ".pos.pos" + pos, loc);
        plugin.saveConfig();

        plugin.getMessageManager().send(player, "dueladmin.arena-pos-set");
    }

    public void enableArena(String arenaName, Player player) {

        if (!plugin.getDuelManager().arenaExists(arenaName)) {
            plugin.getMessageManager().send(player, "dueladmin.arena-not-found");
            return;
        }

        FileConfiguration arenas = plugin.getConfigManager().getConfig("arenas.yml");
        arenas.set("arenas." + arenaName + ".enabled", true);
        plugin.saveConfig();

        plugin.getMessageManager().send(player, "dueladmin.arena-enabled");
    }

    public void disableArena(String arenaName, Player player) {

        if (!plugin.getDuelManager().arenaExists(arenaName)) {
            plugin.getMessageManager().send(player, "dueladmin.arena-not-found");
            return;
        }

        FileConfiguration arenas = plugin.getConfigManager().getConfig("arenas.yml");
        arenas.set("arenas." + arenaName + ".enabled", false);
        plugin.saveConfig();

        plugin.getMessageManager().send(player, "dueladmin.arena-disabled");
    }

    public void deleteArena(String arenaName, Player player) {

        if (!plugin.getDuelManager().arenaExists(arenaName)) return;

        FileConfiguration arenas = plugin.getConfigManager().getConfig("arenas.yml");
        arenas.set("arenas." + arenaName, null);
        plugin.saveConfig();

        plugin.getMessageManager().send(player, "dueladmin.arena-deleted");
    }

    public void listArena(Player player) {

        FileConfiguration arenas = plugin.getConfigManager().getConfig("arenas.yml");
        ConfigurationSection section = arenas.getConfigurationSection("arenas");

        plugin.getMessageManager().send(player, "dueladmin.arena-list-header");

        if (section != null) {
            for (String arenaName : section.getKeys(false)) {
                player.sendMessage("§7- §a" + arenaName);
            }
        }
    }
    public void reload(Player player) {

        if (!player.hasPermission("duels.reload")) {
            plugin.getMessageManager().send(player, "permissions.no-perms");
            return;
        }

        plugin.getMessageManager().send(player, "dueladmin.reloading-message");

        try {
            // reload configs
            plugin.getConfigManager().reloadConfigs();

            // reload systems (if implemented)
            plugin.getDuelManager().reload();
            plugin.getMessageManager().reload();

            plugin.getMessageManager().send(player, "dueladmin.reload-success");

        } catch (Exception e) {
            plugin.getMessageManager().send(player, "dueladmin.reload-failed");
            e.printStackTrace();
        }
    }



    // KITS
    public void saveInventory(String kitName, Player player, FileConfiguration config) {
        String path = "kits." + kitName;

        config.set(path + ".contents", player.getInventory().getContents());
        config.set(path + ".armor", player.getInventory().getArmorContents());
        config.set(path + ".offhand", player.getInventory().getItemInOffHand());
    }
    public void loadInventory(Player player, FileConfiguration config) {
        String path = "inventories." + player.getUniqueId();

        if (!config.contains(path)) return;

        ItemStack[] contents = (ItemStack[]) config.get(path + ".contents");
        ItemStack[] armor = (ItemStack[]) config.get(path + ".armor");
        ItemStack offhand = config.getItemStack(path + ".offhand");

        if (contents != null) {
            player.getInventory().setContents(contents);
        }

        if (armor != null) {
            player.getInventory().setArmorContents(armor);
        }

        if (offhand != null) {
            player.getInventory().setItemInOffHand(offhand);
        }
    }
    public void kitCreate(String kitName, Player player) {

        if (plugin.getDuelManager().kitExists(kitName)) {
            plugin.getMessageManager().send(player, "dueladmin.kit-already-exists");
            return;
        }

        FileConfiguration kits = plugin.getConfigManager().getConfig("kits.yml");

        saveInventory(kitName, player, kits);

        plugin.getConfigManager().saveConfig("kits.yml");

        plugin.getMessageManager().send(player, "dueladmin.kit-created");
    }
    public void deleteKit(String kitName, Player player) {

        if (!plugin.getDuelManager().kitExists(kitName)) {
            plugin.getMessageManager().send(player, "dueladmin.kit-not-found");
            return;
        }

        FileConfiguration kits = plugin.getConfigManager().getConfig("kits.yml");

        kits.set("kits." + kitName, null);

        plugin.getConfigManager().saveConfig("kits.yml");

        plugin.getMessageManager().send(player, "dueladmin.kit-deleted");
    }
    public void setKit(String kitName, Player player) {

        if (!plugin.getDuelManager().kitExists(kitName)) {
            plugin.getMessageManager().send(player, "dueladmin.kit-not-found");
            return;
        }

        FileConfiguration kits = plugin.getConfigManager().getConfig("kits.yml");

        saveInventory(kitName, player, kits);

        plugin.getConfigManager().saveConfig("kits.yml");

        plugin.getMessageManager().send(player, "dueladmin.kit-set");
    }
    public void loadKit(String kitName, Player player) {

        if (!plugin.getDuelManager().kitExists(kitName)) {
            plugin.getMessageManager().send(player, "dueladmin.kit-not-found");
            return;
        }

        FileConfiguration kits = plugin.getConfigManager().getConfig("kits.yml");
        String path = "kits." + kitName;

        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.getInventory().setItemInOffHand(null);

        ItemStack[] contents = (ItemStack[]) kits.get(path + ".contents");
        ItemStack[] armor = (ItemStack[]) kits.get(path + ".armor");
        ItemStack offhand = kits.getItemStack(path + ".offhand");

        if (contents != null) player.getInventory().setContents(contents);
        if (armor != null) player.getInventory().setArmorContents(armor);
        if (offhand != null) player.getInventory().setItemInOffHand(offhand);

        plugin.getMessageManager().send(player, "dueladmin.kit-loaded");
    }
    public void kitList(String kitName, Player player) {
        FileConfiguration kits = plugin.getConfigManager().getConfig("kits.yml");
        ConfigurationSection section = kits.getConfigurationSection("kits");

        plugin.getMessageManager().send(player, "dueladmin.arena-list-header");

        if (section != null) {
            for (String kitsName : section.getKeys(false)) {
                player.sendMessage("§7- §a" + kitsName);
            }
        }
    }
}