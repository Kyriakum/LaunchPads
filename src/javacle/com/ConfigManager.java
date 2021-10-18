package javacle.com;

public class ConfigManager {

	private Main main;
	public ConfigManager(Main main) {
		this.main = main;
	}
	
	public void reload() {
		main.reloadConfig();
		main.getServer().getPluginManager().disablePlugin(main);
		main.getServer().getPluginManager().enablePlugin(main);
	}

	
	public boolean getBoolean(String path) {
		return main.getConfig().getBoolean(path);
	}
	
	public void setBoolean(String path, boolean bool) {
		main.getConfig().set(path, bool);
		main.saveConfig();
	}
	
	public String getString(String path) {
		return main.getConfig().getString(path);
	}
	
	public void setString(String path, String str) {
		main.getConfig().set(path, str);
		main.saveConfig();
	}
}
