package me.lordsaad.cashshop.api.capability;

import me.lordsaad.cashshop.common.network.MessageUpdateCapabilities;
import me.lordsaad.cashshop.common.network.WalletPacketHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by Saad on 8/16/2016.
 */
public class DefaultWalletCapability implements IWalletCapability {

	int wallet = 10000;

	@Override
	public int getWallet() {
		return wallet;
	}

	@Override
	public void setWallet(int wallet, EntityPlayer player) {
		this.wallet = wallet;
		dataChanged(player);
	}

	@Override
	public NBTTagCompound saveNBTData() {
		return (NBTTagCompound) WalletCapabilityStorage.INSTANCE.writeNBT(WalletCapabilityProvider.walletCapability, this, null);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		WalletCapabilityStorage.INSTANCE.readNBT(WalletCapabilityProvider.walletCapability, this, null, compound);
	}

	@Override
	public void dataChanged(EntityPlayer player) {
		if ((player != null) && !player.getEntityWorld().isRemote)
			WalletPacketHandler.INSTANCE.sendTo(new MessageUpdateCapabilities(saveNBTData()), (EntityPlayerMP) player);
	}
}
