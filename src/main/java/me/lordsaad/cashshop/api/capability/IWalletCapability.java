package me.lordsaad.cashshop.api.capability;

import net.minecraft.nbt.NBTTagInt;
import net.minecraftforge.common.util.INBTSerializable;

/**
 * Created by Saad on 8/16/2016.
 */
public interface IWalletCapability extends INBTSerializable<NBTTagInt> {

	int getAmount();

	void setAmount(int money);
}
