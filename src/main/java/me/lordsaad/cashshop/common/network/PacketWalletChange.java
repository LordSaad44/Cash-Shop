package me.lordsaad.cashshop.common.network;

import com.teamwizardry.librarianlib.common.network.PacketBase;
import com.teamwizardry.librarianlib.common.util.saving.Save;
import me.lordsaad.cashshop.api.capability.CapabilityWallet;
import me.lordsaad.cashshop.api.capability.IWalletCapability;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by LordSaad.
 */
public class PacketWalletChange extends PacketBase {

	@Save
	private int newWallet;

	public PacketWalletChange() {
	}

	public PacketWalletChange(int newWallet) {
		this.newWallet = newWallet;
	}

	@Override
	public void handle(MessageContext messageContext) {
		EntityPlayerMP player = messageContext.getServerHandler().playerEntity;
		IWalletCapability walletCap = player.getCapability(CapabilityWallet.WALLET, null);
		walletCap.setAmount(newWallet);

		WalletPacketHandler.INSTANCE.sendToAll(new PacketWalletSync(newWallet));
	}
}
