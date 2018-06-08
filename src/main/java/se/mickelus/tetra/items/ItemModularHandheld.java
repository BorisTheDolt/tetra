package se.mickelus.tetra.items;

import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import se.mickelus.tetra.NBTHelper;
import se.mickelus.tetra.PotionBleeding;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class ItemModularHandheld extends ItemModular {

    private static final Set<Block> axeBlocks = Sets.newHashSet(Blocks.PLANKS, Blocks.BOOKSHELF, Blocks.LOG, Blocks.LOG2, Blocks.CHEST, Blocks.PUMPKIN, Blocks.LIT_PUMPKIN, Blocks.MELON_BLOCK, Blocks.LADDER, Blocks.WOODEN_BUTTON, Blocks.WOODEN_PRESSURE_PLATE);
    private static final Set<Material> axeMaterials = Sets.newHashSet(Material.WOOD, Material.PLANTS, Material.VINE);

    private static final Set<Block> pickaxeBlocks = Sets.newHashSet(Blocks.ACTIVATOR_RAIL, Blocks.COAL_ORE, Blocks.COBBLESTONE, Blocks.DETECTOR_RAIL, Blocks.DIAMOND_BLOCK, Blocks.DIAMOND_ORE, Blocks.DOUBLE_STONE_SLAB, Blocks.GOLDEN_RAIL, Blocks.GOLD_BLOCK, Blocks.GOLD_ORE, Blocks.ICE, Blocks.IRON_BLOCK, Blocks.IRON_ORE, Blocks.LAPIS_BLOCK, Blocks.LAPIS_ORE, Blocks.LIT_REDSTONE_ORE, Blocks.MOSSY_COBBLESTONE, Blocks.NETHERRACK, Blocks.PACKED_ICE, Blocks.RAIL, Blocks.REDSTONE_ORE, Blocks.SANDSTONE, Blocks.RED_SANDSTONE, Blocks.STONE, Blocks.STONE_SLAB, Blocks.STONE_BUTTON, Blocks.STONE_PRESSURE_PLATE);
    private static final Set<Material> pickaxeMaterials = Sets.newHashSet(Material.IRON, Material.ANVIL, Material.ROCK);


    @Override
    public boolean onBlockDestroyed(ItemStack itemStack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
        if (state.getBlockHardness(worldIn, pos) > 0) {
            applyDamage(itemStack, 2, entityLiving);
            tickProgression(itemStack, 1);
        }

        return true;
    }

    @Override
    public boolean hitEntity(ItemStack itemStack, EntityLivingBase target, EntityLivingBase attacker) {
        applyDamage(itemStack, 1, attacker);
        tickProgression(itemStack, 1);
        if (!isBroken(itemStack)) {
            getAllModules(itemStack).forEach(module -> module.hitEntity(itemStack, target, attacker));

            int fieryLevel = getEffectLevel(itemStack, ItemEffect.fiery);
            if (fieryLevel > 0) {
                target.setFire(fieryLevel * 4);
            }

            int knockbackLevel = getEffectLevel(itemStack, ItemEffect.knockback);
            if (knockbackLevel > 0) {
                target.knockBack(attacker, knockbackLevel * 0.5f,
                        MathHelper.sin(attacker.rotationYaw * 0.017453292F),
                        -MathHelper.cos(attacker.rotationYaw * 0.017453292F));
            }

            int sweepingLevel = getEffectLevel(itemStack, ItemEffect.sweeping);
            if (sweepingLevel > 0) {
                sweepAttack(itemStack, target, attacker, sweepingLevel, knockbackLevel);
            }

            int bleedingLevel = getEffectLevel(itemStack, ItemEffect.bleeding);
            if (bleedingLevel > 0) {
                if (!EnumCreatureAttribute.UNDEAD.equals(target.getCreatureAttribute()) && Math.random() > 0.7f) {
                    target.addPotionEffect(new PotionEffect(PotionBleeding.instance, 40, bleedingLevel));
                }
            }

            int arthropodLevel = getEffectLevel(itemStack, ItemEffect.arthropod);
            if (arthropodLevel > 0 && EnumCreatureAttribute.ARTHROPOD.equals(target.getCreatureAttribute())) {
                int ticks = 20 + attacker.getRNG().nextInt(10 * arthropodLevel);
                target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, ticks, 3));
            }
        }

        return true;
    }


    private void sweepAttack(ItemStack itemStack, EntityLivingBase target, EntityLivingBase attacker, int sweepingLevel, int knockbackLevel) {
        float cooldown = 1;
        if (attacker instanceof EntityPlayer) {
            cooldown = ItemModularHandheld.getCooledAttackStrength(itemStack);
        }

        if (cooldown > 0.9) {
            float damage = (float) Math.max((getDamageModifier(itemStack) + 1) * (sweepingLevel * 0.125f), 1);
            float knockback = sweepingLevel > 4 ? (knockbackLevel + 1) * 0.5f : 0.5f;

            attacker.world.getEntitiesWithinAABB(EntityLivingBase.class,
                    target.getEntityBoundingBox().expand(1.0d, 0.25d, 1.0d)).stream()
                    .filter(entity -> entity != attacker)
                    .filter(entity -> !attacker.isOnSameTeam(entity))
                    .filter(entity -> attacker.getDistanceSq(entity) < 9.0D)
                    .forEach(entity -> {
                        entity.knockBack(attacker, knockback,
                                MathHelper.sin(attacker.rotationYaw * 0.017453292f),
                                -MathHelper.cos(attacker.rotationYaw * 0.017453292f));
                        if (attacker instanceof EntityPlayer) {
                            entity.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) attacker), damage);
                        } else {
                            entity.attackEntityFrom(DamageSource.causeIndirectDamage(attacker, entity), damage);
                        }
                    });

            attacker.world.playSound(null, attacker.posX, attacker.posY, attacker.posZ,
                    SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, attacker.getSoundCategory(), 1.0F, 1.0F);
            spawnSweepParticles(attacker);
        }
    }

    private void spawnSweepParticles(EntityLivingBase attacker) {
        double d0 = (double)(-MathHelper.sin(attacker.rotationYaw * 0.017453292F));
        double d1 = (double)MathHelper.cos(attacker.rotationYaw * 0.017453292F);

        if (attacker.world instanceof WorldServer)
        {
            ((WorldServer)attacker.world).spawnParticle(EnumParticleTypes.SWEEP_ATTACK, attacker.posX + d0,
                    attacker.posY + attacker.height * 0.5D, attacker.posZ + d1, 0, d0,
                    0.0D, d1, 0.0D);
        }
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        setCooledAttackStrength(stack, player.getCooledAttackStrength(0.5f));
        return false;
    }

    public void setCooledAttackStrength(ItemStack itemStack, float strength) {
        NBTHelper.getTag(itemStack).setFloat(cooledStrengthKey, strength);
    }

    public static float getCooledAttackStrength(ItemStack itemStack) {
        return NBTHelper.getTag(itemStack).getFloat(cooledStrengthKey);
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack itemStack) {
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, itemStack);

        if (slot == EntityEquipmentSlot.MAINHAND) {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(),
                new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", getDamageModifier(itemStack), 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(),
                new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", getSpeedModifier(itemStack), 0));
        }

        return multimap;
    }

    public double getDamageModifier(ItemStack itemStack) {
        if (isBroken(itemStack)) {
            return 0;
        }

        double damageModifier = getAllModules(itemStack).stream()
            .map(itemModule -> itemModule.getDamageModifier(itemStack))
            .reduce(0d, Double::sum);

        damageModifier = Arrays.stream(getSynergyData(itemStack))
            .map(synergyData -> synergyData.damage)
            .reduce(damageModifier, Double::sum);

        return getAllModules(itemStack).stream()
            .map(itemModule -> itemModule.getDamageMultiplierModifier(itemStack))
            .reduce(damageModifier, (a, b) -> a*b);
    }

    public static double getDamageModifierStatic(ItemStack itemStack) {
        if (itemStack.getItem() instanceof ItemModularHandheld) {
            return ((ItemModularHandheld) itemStack.getItem()).getDamageModifier(itemStack);
        }
        return 0;
    }

    public double getSpeedModifier(ItemStack itemStack) {
//        if (isBroken(itemStack)) {
//            return 2;
//        }

        double speedModifier = getAllModules(itemStack).stream()
            .map(itemModule -> itemModule.getSpeedModifier(itemStack))
            .reduce(-2.4d, Double::sum);

        speedModifier = Arrays.stream(getSynergyData(itemStack))
            .map(synergyData -> synergyData.speed)
            .reduce(speedModifier, Double::sum);

        speedModifier = getAllModules(itemStack).stream()
            .map(itemModule -> itemModule.getSpeedMultiplierModifier(itemStack))
            .reduce(speedModifier, (a, b) -> a*b);

        if (speedModifier < -4) {
            speedModifier = -3.9d;
        }

        return speedModifier;
    }

    public static double getSpeedModifierStatic(ItemStack itemStack) {
        if (itemStack.getItem() instanceof ItemModularHandheld) {
            return ((ItemModularHandheld) itemStack.getItem()).getSpeedModifier(itemStack);
        }
        return 2;
    }

    @Override
    public Set<String> getToolClasses(ItemStack itemStack) {
        if (!isBroken(itemStack)) {
            return getCapabilities(itemStack).stream()
                .map(Enum::toString)
                .collect(Collectors.toSet());
        }
        return Collections.EMPTY_SET;
    }

    @Override
    public int getHarvestLevel(ItemStack itemStack, String toolClass, @Nullable EntityPlayer player, @Nullable IBlockState blockState) {
        if (!isBroken(itemStack)) {
            int capabilityLevel = getCapabilityLevel(itemStack, toolClass);
            if (capabilityLevel > 0) {
                return capabilityLevel - 1;
            }
        }
        return -1;
    }

    @Override
    public boolean canHarvestBlock(IBlockState blockState, ItemStack itemStack) {
        if (pickaxeMaterials.contains(blockState.getMaterial())) {
            return getHarvestLevel(itemStack, "pickaxe", null, null) >= 0;
        }
        return false;
    }

    @Override
    public float getDestroySpeed(ItemStack itemStack, IBlockState blockState) {
        if (!isBroken(itemStack)) {
            String tool = blockState.getBlock().getHarvestTool(blockState);

            if (tool == null) {
                if (axeMaterials.contains(blockState.getMaterial())) {
                    tool = "axe";
                } else if (pickaxeMaterials.contains(blockState.getMaterial())) {
                    tool = "pickaxe";
                } else if (axeBlocks.contains(blockState.getBlock())) {
                    tool = "axe";
                } else if (pickaxeBlocks.contains(blockState.getBlock())) {
                    tool = "pickaxe";
                }
            }

            float speed = (float) (4 + getSpeedModifier(itemStack)) * getCapabilityEfficiency(itemStack, tool);

            if (speed < 1) {
                return 1;
            }
            return speed;
        }
        return 1;
    }
}
