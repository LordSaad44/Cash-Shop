package me.lordsaad.cashshop.api;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

/**
 * Created by LordSaad.
 */
public class Utils {

	@Nullable
	public static ItemStack getStackFromString(String itemId) {
		int metadata = -1;
		String ID;

		if (itemId.contains(";")) {
			ID = itemId.split(";")[0];
			metadata = Integer.parseInt(itemId.split(";")[1]);
		} else ID = itemId;

		if (ID == null) return null;
		ResourceLocation location = new ResourceLocation(ID);
		ItemStack stack = null;

		if (ForgeRegistries.ITEMS.containsKey(location)) {
			Item item = ForgeRegistries.ITEMS.getValue(location);
			if (item != null) stack = new ItemStack(item);

		} else if (ForgeRegistries.BLOCKS.containsKey(location)) {
			Block block = ForgeRegistries.BLOCKS.getValue(location);
			if (block != null) stack = new ItemStack(block);
		}

		if (stack != null && metadata != -1) stack = new ItemStack(stack.getItem(), 1, metadata);
		return stack;
	}
}
