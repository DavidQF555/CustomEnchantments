import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Silverfish;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Main extends JavaPlugin implements Listener {

	public static Main instance;

	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		instance = this;
	}

	public void onDisable() {}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player) sender;
		if(player instanceof Player) {
			String lowerCaseCommand = command.getName().toLowerCase();
			if(lowerCaseCommand.contentEquals("enchant")) {
				if(player.isOp()) {
					if(args.length == 2) {
						ItemStack item = player.getInventory().getItemInMainHand();
						ArrayList<String> lore = new ArrayList<String>();
						ItemMeta itemMeta = item.getItemMeta();
						if(itemMeta.hasLore()) {
							lore.addAll(itemMeta.getLore());
						}
						if(args[0].equalsIgnoreCase("lifesteal")) {
							if(item.getType().equals(Material.WOOD_SWORD) || item.getType().equals(Material.STONE_SWORD) || item.getType().equals(Material.IRON_SWORD) || item.getType().equals(Material.GOLD_SWORD) || item.getType().equals(Material.DIAMOND_SWORD)) {
								if(args[1].equals("1") || args[1].equals("2") || args[1].equals("3") || args[1].equals("4") || args[1].equals("5")) {
									int index = 0;
									if(itemMeta.hasLore()) {
										for(String enchants : lore) {
											if(enchants.contains("Lifesteal")) {
												lore.remove(index);
												break;
											} 
											index++;
										} 
									}
									if(args[1].equals("1")) {
										lore.add("Lifesteal I");
									}
									else if(args[1].equals("2")) {
										lore.add("Lifesteal II");
									}
									else if(args[1].equals("3")) {
										lore.add("Lifesteal III");
									}
									else if(args[1].equals("4")) {
										lore.add("Lifesteal IV");
									}
									else if(args[1].equals("5")) {
										lore.add("Lifesteal V");
									} 
									itemMeta.setLore(lore);
									item.setItemMeta(itemMeta);
									return true;
								} 
								player.sendMessage(ChatColor.DARK_RED + "Not a possible Lifesteal Level(1-5)");
							}
							else {
								player.sendMessage(ChatColor.DARK_RED + "Lifesteal only goes on swords");
								return true;
							} 
						}
						if(args[0].equalsIgnoreCase("infestation")) {
							if(item.getType().equals(Material.WOOD_SWORD) || item.getType().equals(Material.STONE_SWORD) || item.getType().equals(Material.IRON_SWORD) || item.getType().equals(Material.GOLD_SWORD) || item.getType().equals(Material.DIAMOND_SWORD)) {
								if(args[1].equals("1") || args[1].equals("2") || args[1].equals("3")) {
									int index = 0;
									if(itemMeta.hasLore()) {
										for(String enchants : lore) {
											if(enchants.contains("Infestation")) {
												lore.remove(index);
												break;
											} 
											index++;
										} 
									}
									if(args[1].equals("1")) {
										lore.add("Infestation I");
									}
									else if(args[1].equals("2")) {
										lore.add("Infestation II");
									}
									else if(args[1].equals("3")) {
										lore.add("Infestation III");
									} 
									itemMeta.setLore(lore);
									item.setItemMeta(itemMeta);
									return true;
								} 

								player.sendMessage(ChatColor.DARK_RED + "Not a possible Infestation Level(1-3)");
							}
							else {
								player.sendMessage(ChatColor.DARK_RED + "Infestation only goes on swords");
								return true;
							} 
						}
						if(args[0].equalsIgnoreCase("machinegun")) {
							if(item.getType().equals(Material.BOW)) {
								if(args[1].equals("1") || args[1].equals("2")) {
									int index = 0;
									if(itemMeta.hasLore()) {
										for(String enchants : lore) {
											if(enchants.contains("MachineGun")) {
												lore.remove(index);
												break;
											} 
											index++;
										} 
									}
									if(args[1].equals("1")) {
										lore.add("MachineGun I");
									}
									else if(args[1].equals("2")) {
										lore.add("MachineGun II");
									} 
									itemMeta.setLore(lore);
									item.setItemMeta(itemMeta);
									return true;
								} 

								player.sendMessage(ChatColor.DARK_RED + "Not a possible Machine Gun Level(1-2)");
								return true;
							} 
							player.sendMessage(ChatColor.DARK_RED + "Machine Gun only goes on bows");
							return true;
						}
					} 
					player.sendMessage(ChatColor.DARK_RED + "Usage: /enchant [Enchantment] [Level]");
					return true;
				} 
				player.sendMessage(ChatColor.DARK_RED + "You do not have permission to use this command");
				return true; }
		} 
		return true;
	}

	@EventHandler
	public void attack(EntityDamageByEntityEvent event) {
		LivingEntity damager = (LivingEntity) event.getDamager();
		LivingEntity entity = (LivingEntity) event.getEntity();
		ItemStack weapon = damager.getEquipment().getItemInMainHand();
		double damage = event.getDamage();
		double damagerHealth = damager.getHealth();
		ArrayList<String> lore = new ArrayList<String>();
		lore.addAll(weapon.getItemMeta().getLore());
		if(weapon.getItemMeta().hasLore()) {
			int random = (int) (Math.random() * 100.0D);
			for(String enchants : lore) {
				String[] level = enchants.split(" ");
				if(level[0].equals("Lifesteal")) {
					if(level[1].equals("I")) {
						damager.setHealth(damagerHealth + damage * 0.1D); 
					} 
					if(level[1].equals("II")) {
						damager.setHealth(damagerHealth + damage * 0.2D); 
					} 
					if(level[1].equals("III")) {
						damager.setHealth(damagerHealth + damage * 0.3D); 
					} 
					if(level[1].equals("IV")) {
						damager.setHealth(damagerHealth + damage * 0.4D); 
					} 
					if(level[1].equals("V"))
						damager.setHealth(damagerHealth + damage * 0.5D); 

				} 
				if(level[0].equals("Infestation")) {
					if(level[1].equals("I")) {
						if(random <= 5) {
							Silverfish infestationMob = (Silverfish)entity.getWorld().spawnEntity(entity.getLocation(), EntityType.SILVERFISH);
							infestationMob.setTarget(entity);
							infestationMob.setMetadata("owningEnchantment", new FixedMetadataValue(instance, "infestation"));
							infestationMob.setCustomName(String.valueOf(damager.getCustomName().toString()) + "'s Silverfish");
						}
					} 
					if(level[1].equals("II")) {
						if(random <= 10) {
							Silverfish infestationMob = (Silverfish)entity.getWorld().spawnEntity(entity.getLocation(), EntityType.SILVERFISH);
							infestationMob.setTarget(entity);
							infestationMob.setMetadata("owningEnchantment", new FixedMetadataValue(instance, "infestation"));
							infestationMob.setCustomName(String.valueOf(damager.getCustomName().toString()) + "'s Silverfish");
						}  
					} 
					if(level[1].equals("III") && random <= 15) {
						Silverfish infestationMob = (Silverfish)entity.getWorld().spawnEntity(entity.getLocation(), EntityType.SILVERFISH);
						infestationMob.setTarget(entity);
						infestationMob.setMetadata("owningEnchantment", new FixedMetadataValue(instance, "infestation"));
						infestationMob.setCustomName(String.valueOf(damager.getCustomName().toString()) + "'s Silverfish");
					} 
				} 
			} 
		} 
	}

	@EventHandler
	public void shoot(EntityShootBowEvent event) {
		final LivingEntity entity = event.getEntity();
		ItemStack bow = event.getBow();
		Entity arrow = event.getProjectile();
		final Vector lockedArrowVelocity = arrow.getVelocity();
		ArrayList<String> lore = new ArrayList<String>();
		lore.addAll(bow.getItemMeta().getLore());
		if(bow.getItemMeta().hasLore()) {
			int random = (int) (Math.random() * 100.0D);
			for(String enchants : lore) {
				String[] level = enchants.split(" ");
				if(level[0].equals("MachineGun")) {
					if(level[1].equals("I")) {
						if(random <= 50)
							(new BukkitRunnable() {
								int arrowAmount = (int)(Math.random() * 9.0D + 1.0D);
								int arrowsShot = 1;
								public void run() {
									Arrow machineGun = (Arrow)entity.getWorld().spawnEntity(entity.getEyeLocation(), EntityType.ARROW);
									machineGun.setShooter((ProjectileSource)entity);
									machineGun.setGravity(true);
									machineGun.setVelocity(lockedArrowVelocity);
									arrowsShot++;
									if(arrowsShot >= arrowAmount) {
										cancel();
									}
								}
							}).runTaskTimer(instance, 0L, 2L);
					} 
					if(level[1].equals("II") && random <= 50) {
						(new BukkitRunnable() {
							int arrowAmount = (int)(Math.random() * 10.0D + 5.0D);
							int arrowsShot = 1;
							public void run() {
								Arrow machineGun = (Arrow)entity.getWorld().spawnEntity(entity.getEyeLocation(), EntityType.ARROW);
								machineGun.setShooter((ProjectileSource)entity);
								machineGun.setGravity(true);
								machineGun.setVelocity(lockedArrowVelocity);
								arrowsShot++;
								if(arrowsShot >= arrowAmount) {
									cancel();

								}
							}
						}).runTaskTimer(instance, 0L, 2L); 
					} 
				} 
			} 
		}
	}
}