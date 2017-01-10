package me.lordsaad.cashshop.common.item;

import com.teamwizardry.librarianlib.common.base.item.ItemMod;
import com.teamwizardry.librarianlib.common.util.ConfigPropertyInt;
import me.lordsaad.cashshop.Cashshop;
import me.lordsaad.cashshop.api.Constants;
import me.lordsaad.cashshop.common.entity.EntityNPC;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

/**
 * Created by LordSaad.
 */
public class ItemPurse extends ItemMod {

    @ConfigPropertyInt(modid = Constants.MOD_ID, category = "general", id = "started_amount", comment = "The initial amount of money a player starts with", defaultValue = 100)
    public static int starterAmount;

    public ItemPurse() {
        super("purse");
        setMaxStackSize(1);
    }

    @NotNull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@NotNull ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
        if (!playerIn.getEntityData().hasKey(EntityPlayer.PERSISTED_NBT_TAG)) {
            playerIn.getEntityData().setTag(EntityPlayer.PERSISTED_NBT_TAG, new NBTTagCompound());
            playerIn.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).setInteger("currency", starterAmount);
        }

        if (!playerIn.isSneaking() && !worldIn.isRemote)
            playerIn.openGui(Cashshop.instance, 0, worldIn, (int) playerIn.posX, (int) playerIn.posY, (int) playerIn.posZ);

        return new ActionResult(EnumActionResult.PASS, itemStackIn);
    }
}
