package me.lordsaad.cashshop.client.gui.purse;

import me.lordsaad.cashshop.api.ModItems;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

/**
 * Created by LordSaad.
 */
public class SlotPurse extends Slot {

    private final boolean isOutput;

    public SlotPurse(IInventory inventoryIn, int index, int xPosition, int yPosition, boolean isOutput) {
        super(inventoryIn, index, xPosition, yPosition);
        this.isOutput = isOutput;
    }

    @Override
    public boolean isItemValid(@Nullable ItemStack stack) {
        return isOutput || (stack != null && stack.getItem() != ModItems.CURRENCY);
    }

}
