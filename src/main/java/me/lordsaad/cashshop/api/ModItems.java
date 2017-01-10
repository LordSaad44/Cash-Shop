package me.lordsaad.cashshop.api;


import me.lordsaad.cashshop.common.item.ItemCurrency;
import me.lordsaad.cashshop.common.item.ItemMagicCoin;
import me.lordsaad.cashshop.common.item.ItemPurse;

/**
 * Created by LordSaad.
 */
public class ModItems {

    public static ItemCurrency CURRENCY;
    public static ItemPurse PURSE;
    public static ItemMagicCoin MAGIC_COIN;

    public static void init() {
        CURRENCY = new ItemCurrency();
        PURSE = new ItemPurse();
        MAGIC_COIN = new ItemMagicCoin();
    }
}
