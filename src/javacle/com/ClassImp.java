package javacle.com;

public abstract class ClassImp {

	private Main main;
	private ConfigManager manager;
	private BlockManager bm;

	
	public ClassImp(Main main) {
		this.main = main;
		setManager(new ConfigManager(main));
		setBm(new BlockManager(main));
	
	
	}
	public Main getMain() {
		return this.main;
	}
	
	
	public BlockManager getBm() {
		return bm;
	}
	public void setBm(BlockManager bm) {
		this.bm = bm;
	}
	public ConfigManager getManager() {
		return manager;
	}
	public void setManager(ConfigManager manager) {
		this.manager = manager;
	}
	
}
