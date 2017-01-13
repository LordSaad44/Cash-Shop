package me.lordsaad.cashshop.common.item;

import com.teamwizardry.librarianlib.common.base.item.ItemMod;
import me.lordsaad.cashshop.Cashshop;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

/**
 * Created by LordSaad.
 */
public class ItemPurse extends ItemMod {

    public ItemPurse() {
        super("purse");
        setMaxStackSize(1);
    }

    @NotNull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@NotNull ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
        if (!playerIn.isSneaking() && !worldIn.isRemote)
            playerIn.openGui(Cashshop.instance, 0, worldIn, (int) playerIn.posX, (int) playerIn.posY, (int) playerIn.posZ);


        return new ActionResult(EnumActionResult.PASS, itemStackIn);
    }
}
