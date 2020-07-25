package fr.skytasul.quests.options;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.skytasul.quests.api.options.OptionSet;
import fr.skytasul.quests.api.options.QuestOption;
import fr.skytasul.quests.editors.TextEditor;
import fr.skytasul.quests.editors.checkers.NumberParser;
import fr.skytasul.quests.gui.ItemUtils;
import fr.skytasul.quests.gui.creation.FinishGUI;
import fr.skytasul.quests.utils.Lang;
import fr.skytasul.quests.utils.XMaterial;

public class OptionTimer extends QuestOption<Integer> {
	
	public OptionTimer() {
		super(OptionRepeatable.class);
	}
	
	@Override
	public Object save() {
		return getValue();
	}
	
	@Override
	public void load(ConfigurationSection config, String key) {
		setValue(config.getInt(key));
	}
	
	@Override
	public Integer cloneValue() {
		return getValue();
	}
	
	@Override
	public boolean shouldDisplay(OptionSet options) {
		return options.getOption(OptionRepeatable.class).getValue();
	}
	
	@Override
	public ItemStack getItemStack() {
		return ItemUtils.item(XMaterial.CLOCK, Lang.timer.toString(), getLore());
	}
	
	private String[] getLore() {
		return new String[] { "§7" + getValue().toString() + " minutes" };
	}
	
	@Override
	public void click(FinishGUI gui, Player p, ItemStack item, int slot) {
		Lang.TIMER.send(p);
		new TextEditor(p, (obj) -> {
			setValue((int) obj);
			ItemUtils.lore(item, getLore());
			gui.reopen(p);
		}, new NumberParser(Integer.class, true), () -> {
			gui.reopen(p);
		}, () -> {
			resetValue();
			ItemUtils.lore(item, getLore());
			gui.reopen(p);
		}).enterOrLeave(p);
	}
	
}
