package me.lordsaad.cashshop.client.gui.purse;

import com.teamwizardry.librarianlib.common.network.PacketHandler;
import me.lordsaad.cashshop.api.ConfigValues;
import me.lordsaad.cashshop.api.ModItems;
import me.lordsaad.cashshop.api.capability.CapabilityWallet;
import me.lordsaad.cashshop.api.capability.IWalletCapability;
import me.lordsaad.cashshop.common.network.PacketWalletChange;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
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

		if (isOutput) {
			if (getSlotIndex() == 1)
				this.inventory.setInventorySlotContents(getSlotIndex(), new ItemStack(ModItems.CURRENCY, 16, 1));
			else if (getSlotIndex() == 2)
				this.inventory.setInventorySlotContents(getSlotIndex(), new ItemStack(ModItems.CURRENCY, 16, 0));
			else if (getSlotIndex() == 3)
				this.inventory.setInventorySlotContents(getSlotIndex(), new ItemStack(ModItems.CURRENCY, 16, 2));
			this.inventory.markDirty();
		}
	}

	@Override
	public void putStack(@Nullable ItemStack stack) {
		super.putStack(stack);
		if (!isOutput && stack != null && stack.getItem() == ModItems.CURRENCY) {
			int worth = 0;
			if (stack.getMetadata() == 0)
				worth = ConfigValues.smallCurrencyAmount;
			else if (stack.getMetadata() == 1)
				worth = ConfigValues.normalCurrencyAmount;
			else if (stack.getMetadata() == 2)
				worth = ConfigValues.largeCurrencyAmount;

			IWalletCapability walletCap = Minecraft.getMinecraft().player.getCapability(CapabilityWallet.WALLET, null);
			PacketHandler.NETWORK.sendToServer(new PacketWalletChange(walletCap.getAmount() + (worth * stack.stackSize)));
			decrStackSize(stack.stackSize);
		}
	}

	@Override
	public void onPickupFromSlot(EntityPlayer playerIn, ItemStack stack) {
		super.onPickupFromSlot(playerIn, stack);
		if (isOutput) {
			int worth = 0;
			if (stack.getMetadata() == 0)
				worth = ConfigValues.smallCurrencyAmount;
			else if (stack.getMetadata() == 1)
				worth = ConfigValues.normalCurrencyAmount;
			else if (stack.getMetadata() == 2)
				worth = ConfigValues.largeCurrencyAmount;

			IWalletCapability walletCap = Minecraft.getMinecraft().player.getCapability(CapabilityWallet.WALLET, null);
			PacketHandler.NETWORK.sendToServer(new PacketWalletChange(walletCap.getAmount() - (worth * stack.stackSize)));

			if (getSlotIndex() == 1)
				this.inventory.setInventorySlotContents(getSlotIndex(), new ItemStack(ModItems.CURRENCY, 16, 1));
			else if (getSlotIndex() == 2)
				this.inventory.setInventorySlotContents(getSlotIndex(), new ItemStack(ModItems.CURRENCY, 16, 0));
			else if (getSlotIndex() == 3)
				this.inventory.setInventorySlotContents(getSlotIndex(), new ItemStack(ModItems.CURRENCY, 16, 2));
			this.inventory.markDirty();
		}
	}

	@Override
	public boolean isItemValid(@Nullable ItemStack stack) {
		return !isOutput && (stack != null && stack.getItem() == ModItems.CURRENCY);
	}

	@Override
	public boolean canTakeStack(EntityPlayer playerIn) {
		if (isOutput) {
			IWalletCapability walletCap = Minecraft.getMinecraft().player.getCapability(CapabilityWallet.WALLET, null);
			int wallet = walletCap.getAmount();
			int worth = 0;
			if (getSlotIndex() == 1)
				worth = ConfigValues.smallCurrencyAmount;
			else if (getSlotIndex() == 2)
				worth = ConfigValues.normalCurrencyAmount;
			else if (getSlotIndex() == 3)
				worth = ConfigValues.largeCurrencyAmount;

			return wallet >= worth * 16;
		} else return true;
	}
}
