package me.lordsaad.cashshop.api.capability;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by Saad on 8/16/2016.
 */
public interface IWalletCapability {

	int getWallet();

	void setWallet(int money, EntityPlayer player);

	NBTTagCompound saveNBTData();

	void loadNBTData(NBTTagCompound compound);

	void dataChanged(EntityPlayer player);
}
