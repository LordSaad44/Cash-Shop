package me.lordsaad.cashshop.client.gui.purse;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

/**
 * Created by LordSaad.
 */
public class InventoryPurseInput implements IInventory {

    private final ItemStack[] stackResult = new ItemStack[3];

    @Override
    public int getSizeInventory() {
        return 3;
    }
    
    @Nullable
    @Override
    public ItemStack getStackInSlot(int index) {
        return this.stackResult[0];
    }

    @NotNull
    @Override
    public String getName() {
        return "Result";
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @NotNull
    @Override
    public ITextComponent getDisplayName() {
        return this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName(), new Object[0]);
    }

    @Nullable
    @Override
    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndRemove(this.stackResult, 0);
    }

    @Nullable
    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(this.stackResult, 0);
    }

    @Override
    public void setInventorySlotContents(int index, @Nullable ItemStack stack) {
        this.stackResult[0] = stack;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void markDirty() {
    }

    @Override
    public boolean isUsableByPlayer(@NotNull EntityPlayer player) {
        return true;
    }

    @Override
    public void openInventory(@NotNull EntityPlayer player) {
    }

    @Override
    public void closeInventory(@NotNull EntityPlayer player) {
    }

    @Override
    public boolean isItemValidForSlot(int index, @NotNull ItemStack stack) {
        return true;
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {
    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {
        for (int i = 0; i < this.stackResult.length; ++i)
            this.stackResult[i] = null;
    }
}
