package se.mickelus.tetra.gui.statbar;

import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantments;
import se.mickelus.tetra.gui.statbar.getter.*;
import se.mickelus.tetra.items.modular.impl.toolbelt.inventory.PotionsInventory;
import se.mickelus.tetra.items.modular.impl.toolbelt.inventory.QuickslotInventory;
import se.mickelus.tetra.items.modular.impl.toolbelt.inventory.QuiverInventory;
import se.mickelus.tetra.items.modular.impl.toolbelt.inventory.StorageInventory;
import se.mickelus.tetra.module.ItemEffect;

public class GuiStats {

    public static final int barLength = 59;

    public static final IStatGetter damageGetter = new StatGetterDamageMelee();
    public static final GuiStatBar damage = new GuiStatBar(0, 0,barLength, I18n.format("tetra.stats.damage"),
                0, 40, false, damageGetter, LabelGetterBasic.decimalLabel,
            new TooltipGetterDecimal("tetra.stats.damage.tooltip", damageGetter));

    public static final IStatGetter rangedDamageGetter = new StatGetterDamageRanged();
    public static final GuiStatBar rangedDamage = new GuiStatBar(0, 0,barLength, I18n.format("tetra.stats.ranged_damage"),
            0, 40, false, rangedDamageGetter, LabelGetterBasic.decimalLabel,
            new TooltipGetterDecimal("tetra.stats.ranged_damage.tooltip", rangedDamageGetter));

    public static final IStatGetter shieldDamageGetter = new StatGetterDamageShield();
    public static final GuiStatBar shieldDamage = new GuiStatBar(0, 0,barLength, I18n.format("tetra.stats.shield_damage"),
            0, 40, false, shieldDamageGetter, LabelGetterBasic.decimalLabel,
            new TooltipGetterDecimal("tetra.stats.shield_damage.tooltip", shieldDamageGetter));

    public static final IStatGetter speedGetter = new StatGetterSpeedMelee();
    public static final GuiStatBar speed = new GuiStatBar(0, 0, barLength, I18n.format("tetra.stats.speed"),
                0, 4, false, speedGetter, LabelGetterBasic.decimalLabel,
            new TooltipGetterSpeed());

    public static final IStatGetter speedGetterNormalized = new StatGetterSpeedNormalized();
    public static final GuiStatBar speedNormalized = new GuiStatBar(0, 0, barLength, I18n.format("tetra.stats.speed_normalized"),
            -3, 3, false, true, false, speedGetterNormalized, LabelGetterBasic.decimalLabel,
            new TooltipGetterDecimal("tetra.stats.speed_normalized.tooltip", speedGetterNormalized));

    public static final IStatGetter rangedSpeedGetter = new StatGetterSpeedRanged();
    public static final GuiStatBar rangedSpeed = new GuiStatBar(0, 0, barLength, I18n.format("tetra.stats.ranged_speed"),
            0, 10, false, false, true, rangedSpeedGetter, LabelGetterBasic.decimalLabelInverted,
            new TooltipGetterDecimal("tetra.stats.ranged_speed.tooltip", rangedSpeedGetter));

    public static final IStatGetter rangedSpeedGetterNormalized = new StatGetterSpeedRanged();
    public static final GuiStatBar rangedSpeedNormalized = new GuiStatBar(0, 0, barLength,
            I18n.format("tetra.stats.ranged_speed_normalized"), -4, 4, false, true, true,
            rangedSpeedGetterNormalized, LabelGetterBasic.decimalLabelInverted,
            new TooltipGetterDecimal("tetra.stats.ranged_speed_normalized.tooltip", rangedSpeedGetterNormalized));

    public static final IStatGetter shieldSpeedGetter = new StatGetterSpeedShield();
    public static final GuiStatBar shieldSpeed = new GuiStatBar(0, 0, barLength, I18n.format("tetra.stats.shield_speed"),
            0, 32, false, false, true, shieldSpeedGetter, LabelGetterBasic.decimalLabelInverted,
            new TooltipGetterDecimal("tetra.stats.shield_speed.tooltip", shieldSpeedGetter));

    public static final IStatGetter shieldSpeedGetterNormalized = new StatGetterSpeedShield();
    public static final GuiStatBar shieldSpeedNormalized = new GuiStatBar(0, 0, barLength,
            I18n.format("tetra.stats.shield_speed_normalized"), -16, 16, false, true, true,
            shieldSpeedGetterNormalized, LabelGetterBasic.decimalLabelInverted,
            new TooltipGetterDecimal("tetra.stats.shield_speed_normalized.tooltip", shieldSpeedGetterNormalized));

    public static final IStatGetter reachGetter = new StatGetterReach();
    public static final GuiStatBar reach = new GuiStatBar(0, 0, barLength, I18n.format("tetra.stats.reach"),
            0, 20, false, reachGetter, LabelGetterBasic.decimalLabel,
            new TooltipGetterDecimal("tetra.stats.reach.tooltip", reachGetter));

    public static final IStatGetter durabilityGetter = new StatGetterDurability();
    public static final GuiStatBar durability = new GuiStatBar(0, 0, barLength, I18n.format("tetra.stats.durability"),
                0, 2400, false, durabilityGetter, LabelGetterBasic.integerLabel,
            new TooltipGetterInteger("tetra.stats.durability.tooltip", durabilityGetter));

    public static final IStatGetter armorGetter = new StatGetterEffectLevel(ItemEffect.armor, 1d);
    public static final GuiStatBar armor = new GuiStatBar(0, 0, barLength, I18n.format("tetra.stats.armor"),
                0, 20, false, armorGetter, LabelGetterBasic.integerLabel,
            new TooltipGetterInteger("tetra.stats.armor.tooltip", armorGetter));

    public static final IStatGetter toughnessGetter = new StatGetterEffectLevel(ItemEffect.toughness, 1d);
    public static final GuiStatBar toughness = new GuiStatBar(0, 0, barLength, I18n.format("tetra.stats.toughness"),
            0, 20, false, toughnessGetter, LabelGetterBasic.integerLabel,
            new TooltipGetterInteger("tetra.stats.toughness.tooltip", toughnessGetter));

    public static final GuiStatBar blocking = new GuiStatBarBlockingDuration(0, 0, barLength);

    public static final IStatGetter bashingGetter = new StatGetterEffectLevel(ItemEffect.bashing, 1d);
    public static final GuiStatBar bashing = new GuiStatBar(0, 0, barLength, I18n.format("tetra.stats.bashing"),
            0, 16, false, bashingGetter, LabelGetterBasic.integerLabel,
            new TooltipGetterBashing());

    public static final IStatGetter throwableGetter = new StatGetterEffectEfficiency(ItemEffect.throwable, 1d);
    public static final GuiStatBar throwable = new GuiStatBar(0, 0, barLength, I18n.format("tetra.stats.throwable"),
            0, 3, false, throwableGetter, LabelGetterBasic.percentageLabel,
            new TooltipGetterPercentage("tetra.stats.throwable.tooltip", throwableGetter));

    public static final IStatGetter quickslotGetter = new StatGetterEffectLevel(ItemEffect.quickSlot, 1d);
    public static final GuiStatBar quickslot = new GuiStatBar(0, 0, barLength, I18n.format("tetra.stats.toolbelt.quickslot"),
                0, QuickslotInventory.maxSize, true, quickslotGetter, LabelGetterBasic.integerLabel,
            new TooltipGetterInteger("tetra.stats.toolbelt.quickslot.tooltip", quickslotGetter));

    public static final IStatGetter potionStorageGetter = new StatGetterEffectLevel(ItemEffect.potionSlot, 1d);
    public static final GuiStatBar potionStorage = new GuiStatBar(0, 0, barLength, I18n.format("tetra.stats.toolbelt.potion_storage"),
                0, PotionsInventory.maxSize, true, potionStorageGetter, LabelGetterBasic.integerLabel,
            new TooltipGetterInteger("tetra.stats.toolbelt.potion_storage.tooltip", potionStorageGetter));

    public static final IStatGetter storageGetter = new StatGetterEffectLevel(ItemEffect.storageSlot, 1d);
    public static final GuiStatBar storage = new GuiStatBar(0, 0, barLength, I18n.format("tetra.stats.toolbelt.storage"),
                0, StorageInventory.maxSize, true, storageGetter, LabelGetterBasic.integerLabel,
            new TooltipGetterInteger("tetra.stats.toolbelt.storage.tooltip", storageGetter));

    public static final IStatGetter quiverGetter = new StatGetterEffectLevel(ItemEffect.quiverSlot, 1d);
    public static final GuiStatBar quiver = new GuiStatBar(0, 0, barLength, I18n.format("tetra.stats.toolbelt.quiver"),
                0, QuiverInventory.maxSize, true, quiverGetter, LabelGetterBasic.integerLabel,
            new TooltipGetterInteger("tetra.stats.toolbelt.quiver.tooltip", quiverGetter));

    public static final IStatGetter boosterGetter = new StatGetterEffectLevel(ItemEffect.booster, 1d);
    public static final GuiStatBar booster = new GuiStatBar(0, 0, barLength, I18n.format("tetra.stats.toolbelt.booster"),
                0, 3, true, boosterGetter, LabelGetterBasic.integerLabel,
            new TooltipGetterInteger("tetra.stats.toolbelt.booster.tooltip", boosterGetter));

    public static final IStatGetter sweepingGetter = new StatGetterEffectLevel(ItemEffect.sweeping, 12.5);
    public static final GuiStatBar sweeping = new GuiStatBar(0, 0, barLength, I18n.format("tetra.stats.sweeping"),
                0, 100, false, sweepingGetter, LabelGetterBasic.percentageLabelDecimal,
            new TooltipGetterPercentage("tetra.stats.sweeping.tooltip", sweepingGetter));

    public static final IStatGetter bleedingGetter = new StatGetterEffectLevel(ItemEffect.bleeding, 4);
    public static final GuiStatBar bleeding = new GuiStatBar(0, 0, barLength, I18n.format("tetra.stats.bleeding"),
                0, 12, false, bleedingGetter, LabelGetterBasic.integerLabel,
            new TooltipGetterInteger("tetra.stats.bleeding.tooltip", bleedingGetter));

    public static final IStatGetter backstabGetter = new StatGetterEffectLevel(ItemEffect.backstab, 25, 25);
    public static final GuiStatBar backstab = new GuiStatBar(0, 0, barLength, I18n.format("tetra.stats.backstab"),
                0, 200, false, backstabGetter, LabelGetterBasic.percentageLabelDecimal,
            new TooltipGetterPercentage("tetra.stats.backstab.tooltip", backstabGetter));

    public static final IStatGetter armorPenetrationGetter = new StatGetterEffectLevel(ItemEffect.armorPenetration, 1);
    public static final GuiStatBar armorPenetration = new GuiStatBar(0, 0, barLength, I18n.format("tetra.stats.armorPenetration"),
                0, 10, false, armorPenetrationGetter, LabelGetterBasic.integerLabel,
            new TooltipGetterInteger("tetra.stats.armorPenetration.tooltip", armorPenetrationGetter));

    public static final IStatGetter unarmoredDamageGetter = new StatGetterEffectLevel(ItemEffect.unarmoredDamage, 1);
    public static final GuiStatBar unarmoredDamage = new GuiStatBar(0, 0, barLength, I18n.format("tetra.stats.unarmoredDamage"),
                0, 10, false, unarmoredDamageGetter, LabelGetterBasic.integerLabel,
            new TooltipGetterInteger("tetra.stats.unarmoredDamage.tooltip", unarmoredDamageGetter));

    public static final IStatGetter knockbackGetter = new StatGetterEnchantmentLevel(Enchantments.KNOCKBACK, 0.5);
    public static final GuiStatBar knockback = new GuiStatBar(0, 0, barLength, I18n.format("tetra.stats.knockback"),
                0, 10, false, knockbackGetter, LabelGetterBasic.decimalLabel,
            new TooltipGetterDecimal("tetra.stats.knockback.tooltip", knockbackGetter));

    public static final IStatGetter lootingGetter = new StatGetterEnchantmentLevel(Enchantments.LOOTING, 1);
    public static final GuiStatBar looting = new GuiStatBar(0, 0, barLength, I18n.format("tetra.stats.looting"),
                0, 20, false, lootingGetter, LabelGetterBasic.integerLabel,
            new TooltipGetterInteger("tetra.stats.looting.tooltip", lootingGetter));

    public static final IStatGetter fieryGetter = new StatGetterEnchantmentLevel(Enchantments.FIRE_ASPECT, 4);
    public static final GuiStatBar fiery = new GuiStatBar(0, 0, barLength, I18n.format("tetra.stats.fiery"),
                0, 32, false, fieryGetter, LabelGetterBasic.integerLabel,
            new TooltipGetterInteger("tetra.stats.fiery.tooltip", fieryGetter));

    public static final IStatGetter smiteGetter = new StatGetterEnchantmentLevel(Enchantments.SMITE, 2.5);
    public static final GuiStatBar smite = new GuiStatBar(0, 0, barLength, I18n.format("tetra.stats.smite"),
                0, 25, false, smiteGetter, LabelGetterBasic.decimalLabel,
            new TooltipGetterDecimal("tetra.stats.smite.tooltip", smiteGetter));

    public static final IStatGetter arthropodGetter = new StatGetterEnchantmentLevel(Enchantments.BANE_OF_ARTHROPODS, 2.5);
    public static final GuiStatBar arthropod = new GuiStatBar(0, 0, barLength, I18n.format("tetra.stats.arthropod"),
                0, 25, false, arthropodGetter, LabelGetterBasic.decimalLabel,
            new TooltipGetterArthropod());

    public static final IStatGetter unbreakingGetter = new StatGetterEffectLevel(ItemEffect.unbreaking, 1);
    public static final GuiStatBar unbreaking = new GuiStatBar(0, 0, barLength, I18n.format("tetra.stats.unbreaking"),
                0, 20, true, unbreakingGetter, LabelGetterBasic.integerLabel,
            new TooltipGetterUnbreaking());

    public static final IStatGetter mendingGetter = new StatGetterEnchantmentLevel(Enchantments.MENDING, 2);
    public static final GuiStatBar mending = new GuiStatBar(0, 0, barLength, I18n.format("tetra.stats.mending"),
                0, 10, false, mendingGetter, LabelGetterBasic.integerLabel,
            new TooltipGetterInteger("tetra.stats.mending.tooltip", mendingGetter));

    public static final IStatGetter silkTouchGetter = new StatGetterEnchantmentLevel(Enchantments.SILK_TOUCH, 1);
    public static final GuiStatBar silkTouch = new GuiStatBar(0, 0, barLength, I18n.format("tetra.stats.silkTouch"),
                0, 1, false, silkTouchGetter, LabelGetterBasic.integerLabel,
            new TooltipGetterDecimal("tetra.stats.silkTouch.tooltip", silkTouchGetter));

    public static final IStatGetter fortuneGetter = new StatGetterEnchantmentLevel(Enchantments.FORTUNE, 1);
    public static final GuiStatBar fortune = new GuiStatBar(0, 0, barLength, I18n.format("tetra.stats.fortune"),
                0, 20, false, fortuneGetter, LabelGetterBasic.integerLabel,
            new TooltipGetterInteger("tetra.stats.fortune.tooltip", fortuneGetter));

    public static final IStatGetter infinityGetter = new StatGetterEnchantmentLevel(Enchantments.INFINITY, 1);
    public static final GuiStatBar infinity = new GuiStatBar(0, 0, barLength, I18n.format("tetra.stats.infinity"),
            0, 1, false, infinityGetter, LabelGetterBasic.integerLabel,
            new TooltipGetterInteger("tetra.stats.infinity.tooltip", infinityGetter));

    public static final IStatGetter flameGetter = new StatGetterEnchantmentLevel(Enchantments.FLAME, 4);
    public static final GuiStatBar flame = new GuiStatBar(0, 0, barLength, I18n.format("tetra.stats.flame"),
            0, 2, false, flameGetter, LabelGetterBasic.integerLabel,
            new TooltipGetterInteger("tetra.stats.flame.tooltip", flameGetter));

    public static final IStatGetter punchGetter = new StatGetterEnchantmentLevel(Enchantments.PUNCH, 1);
    public static final GuiStatBar punch = new GuiStatBar(0, 0, barLength, I18n.format("tetra.stats.punch"),
            0, 4, false, punchGetter, LabelGetterBasic.integerLabel,
            new TooltipGetterInteger("tetra.stats.punch.tooltip", punchGetter));

    public static final IStatGetter quickStrikeGetter = new StatGetterEffectLevel(ItemEffect.quickStrike, 5, 20);
    public static final GuiStatBar quickStrike = new GuiStatBar(0, 0, barLength, I18n.format("tetra.stats.quickStrike"),
                0, 100, false, quickStrikeGetter, LabelGetterBasic.percentageLabelDecimal,
            new TooltipGetterPercentage("tetra.stats.quickStrike.tooltip", quickStrikeGetter));

    public static final IStatGetter softStrikeGetter = new StatGetterEffectLevel(ItemEffect.softStrike, 1);
    public static final GuiStatBar softStrike = new GuiStatBar(0, 0, barLength, I18n.format("tetra.stats.softStrike"),
                0, 1, false, softStrikeGetter, LabelGetterBasic.integerLabel,
            new TooltipGetterInteger("tetra.stats.softStrike.tooltip", softStrikeGetter));

    public static final IStatGetter fierySelfGetter = new StatGetterEffectEfficiency(ItemEffect.fierySelf, 100);
    public static final GuiStatBar fierySelf = new GuiStatBar(0, 0, barLength, I18n.format("tetra.stats.fierySelf"),
                0, 100, false, false, true, fierySelfGetter, LabelGetterBasic.percentageLabelDecimalInverted,
            new TooltipGetterFierySelf());

    public static final IStatGetter enderReverbGetter = new StatGetterEffectEfficiency(ItemEffect.enderReverb, 100);
    public static final GuiStatBar enderReverb = new GuiStatBar(0, 0, barLength, I18n.format("tetra.stats.enderReverb"),
                0, 100, false, false, true, enderReverbGetter, LabelGetterBasic.percentageLabelDecimalInverted,
            new TooltipGetterPercentage("tetra.stats.enderReverb.tooltip", enderReverbGetter));

    public static final IStatGetter criticalGetter = new StatGetterEffectLevel(ItemEffect.criticalStrike, 1);
    public static final GuiStatBar criticalStrike = new GuiStatBar(0, 0, barLength, I18n.format("tetra.stats.criticalStrike"),
            0, 100, false, criticalGetter, LabelGetterBasic.percentageLabel,
            new TooltipGetterCriticalStrike());

    public static final IStatGetter intuitGetter = new StatGetterEffectLevel(ItemEffect.intuit, 1);
    public static final GuiStatBar intuit = new GuiStatBar(0, 0, barLength, I18n.format("tetra.stats.intuit"),
            0, 8, false, intuitGetter, LabelGetterBasic.integerLabel,
            new TooltipGetterInteger("tetra.stats.intuit.tooltip", intuitGetter));

    public static final IStatGetter earthbindGetter = new StatGetterEffectLevel(ItemEffect.earthbind, 1);
    public static final GuiStatBar earthbind = new GuiStatBar(0, 0, barLength, I18n.format("tetra.stats.earthbind"),
            0, 16, false, earthbindGetter, LabelGetterBasic.integerLabel,
            new TooltipGetterInteger("tetra.stats.earthbind.tooltip", earthbindGetter));

    public static final IStatGetter releaseLatchGetter = new StatGetterEffectLevel(ItemEffect.releaseLatch, 1);
    public static final GuiStatBar releaseLatch = new GuiStatBar(0, 0, barLength, I18n.format("tetra.stats.bow.releaseLatch"),
            0, 1, false, releaseLatchGetter, LabelGetterBasic.integerLabel,
            new TooltipGetterInteger("tetra.stats.bow.releaseLatch.tooltip", releaseLatchGetter));

    public static final IStatGetter overbowedGetter = new StatGetterEffectLevel(ItemEffect.overbowed, 1);
    public static final GuiStatBar overbowed = new GuiStatBar(0, 0, barLength, I18n.format("tetra.stats.bow.overbowed"),
            0, 100, false, overbowedGetter, LabelGetterBasic.percentageLabel,
            new TooltipGetterOverbowed());

    public static final IStatGetter magicCapacityGetter = new StatGetterMagicCapacity();
    public static final GuiStatBar magicCapacity = new GuiStatBar(0, 0, barLength, I18n.format("tetra.stats.magicCapacity"),
            0, 150, false, magicCapacityGetter, LabelGetterBasic.integerLabel,
            new TooltipGetterInteger("tetra.stats.magicCapacity.tooltip", magicCapacityGetter));

    public static final IStatGetter holoSpeedGetter = new StatGetterSpeedHolo();
    public static final GuiStatBar holoSpeed = new GuiStatBar(0, 0, barLength, I18n.format("tetra.stats.holo_speed"),
            0, 3, false, false, true, holoSpeedGetter, LabelGetterBasic.decimalLabelInverted,
            new TooltipGetterDecimal("tetra.stats.holo_speed.tooltip", holoSpeedGetter));

    public static final IStatGetter scannerRangeGetter = new StatGetterEffectLevel(ItemEffect.scannerRange, 1);
    public static final GuiStatBar scannerRange = new GuiStatBar(0, 0, barLength, I18n.format("tetra.stats.holo.scannerRange"),
            0, 64, false, scannerRangeGetter, LabelGetterBasic.integerLabel,
            new TooltipGetterInteger("tetra.stats.holo.scannerRange.tooltip", scannerRangeGetter));

    public static final IStatGetter scannerHorizontalSpreadGetter = new StatGetterEffectLevel(ItemEffect.scannerHorizontalSpread, 4);
    public static final GuiStatBar scannerHorizontalSpread = new GuiStatBar(0, 0, barLength, I18n.format("tetra.stats.holo.scannerHorizontalSpread"),
            0, 128, false, scannerHorizontalSpreadGetter, LabelGetterBasic.integerLabel,
            new TooltipGetterScannerHorizontalRange(scannerHorizontalSpreadGetter));

    public static final IStatGetter scannerVerticalSpreadGetter = new StatGetterEffectLevel(ItemEffect.scannerVerticalSpread, 10, 40);
    public static final GuiStatBar scannerVerticalSpread = new GuiStatBar(0, 0, barLength, I18n.format("tetra.stats.holo.scannerVerticalSpread"),
            0, 180, false, scannerVerticalSpreadGetter, LabelGetterBasic.integerLabel,
            new TooltipGetterInteger("tetra.stats.holo.scannerVerticalSpread.tooltip", scannerVerticalSpreadGetter));

    public static final GuiStatBarIntegrity integrity = new GuiStatBarIntegrity(0, 0);



// todo: remaining effects
//        ItemEffect.strikingAxe
//        ItemEffect.strikingPickaxe
//        ItemEffect.strikingCut
//        ItemEffect.strikingShovel
//        ItemEffect.sweepingStrike
//        ItemEffect.flattening
//        ItemEffect.tilling
//        ItemEffect.counterweight
//        ItemEffect.denailing
}
