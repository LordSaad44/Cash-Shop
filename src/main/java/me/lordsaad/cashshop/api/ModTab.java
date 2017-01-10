package me.lordsaad.cashshop.api;

import com.teamwizardry.librarianlib.common.base.ModCreativeTab;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Saad on 10/3/2016.
 */
public class ModTab extends ModCreativeTab {

	private static boolean isInitialized = false;

	private ModTab() {
		super();
	}

	public static void init() {
		if (isInitialized) return;
		new ModTab().registerDefaultTab();
		isInitialized = true;
	}

	@NotNull
	@Override
	public Item getTabIconItem() {
		return ModItems.CURRENCY;
	}

	@NotNull
	@Override
	public ItemStack getIconStack() {
		return new ItemStack(ModItems.CURRENCY);
	}
}
