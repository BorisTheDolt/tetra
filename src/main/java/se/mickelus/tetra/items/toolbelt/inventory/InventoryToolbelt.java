package se.mickelus.tetra.items.toolbelt.inventory;

import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.util.Constants;
import se.mickelus.tetra.NBTHelper;
import se.mickelus.tetra.data.DataHandler;
import se.mickelus.tetra.items.ItemPredicateComposite;
import se.mickelus.tetra.items.toolbelt.ItemToolbeltModular;
import se.mickelus.tetra.items.toolbelt.SlotType;
import se.mickelus.tetra.module.ItemEffect;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class InventoryToolbelt implements IInventory {
    protected static final String slotKey = "slot";

    protected ItemStack toolbeltItemStack;

    protected SlotType inventoryType;

    protected final String inventoryKey;
    protected NonNullList<ItemStack> inventoryContents;
    protected int numSlots = 0;
    protected int maxSize = 0;

    ItemPredicate predicate = ItemPredicate.ANY;
    public static ItemPredicate potionPredicate = ItemPredicate.ANY;
    public static ItemPredicate quickPredicate = ItemPredicate.ANY;
    public static ItemPredicate quiverPredicate = ItemPredicate.ANY;
    public static ItemPredicate storagePredicate = ItemPredicate.ANY;

    public InventoryToolbelt(String inventoryKey, ItemStack stack, int maxSize, SlotType inventoryType) {
        this.inventoryKey = inventoryKey;
        toolbeltItemStack = stack;

        this.inventoryType = inventoryType;

        this.maxSize = maxSize;
        inventoryContents = NonNullList.withSize(maxSize, ItemStack.EMPTY);
    }

    public static void initializePredicates() {
        potionPredicate = getPredicate("potion");
        quickPredicate = getPredicate("quick");
        quiverPredicate = getPredicate("quiver");
        storagePredicate = getPredicate("storage");
    }

    private static ItemPredicate getPredicate(String inventory) {
        ItemPredicate[] predicates = Arrays.stream(DataHandler.instance.getData(String.format("toolbelt/%s_predicates", inventory), ItemPredicate[].class))
                .filter(Objects::nonNull)
                .toArray(ItemPredicate[]::new);

        // todo: add debug log
        if (predicates.length > 0) {
            return new ItemPredicateComposite(predicates);
        }

        return ItemPredicate.ANY;
    }


    public void readFromNBT(NBTTagCompound compound) {
        NBTTagList items = compound.getTagList(inventoryKey, Constants.NBT.TAG_COMPOUND);

        for (int i = 0; i < items.tagCount(); i++) {
            NBTTagCompound itemTag = items.getCompoundTagAt(i);
            int slot = itemTag.getInteger(slotKey);

            if (0 <= slot && slot < maxSize) {
                inventoryContents.set(slot, new ItemStack(itemTag));
            }
        }
    }

    public void writeToNBT(NBTTagCompound tagcompound) {
        NBTTagList items = new NBTTagList();

        for (int i = 0; i < maxSize; i++) {
            if (getStackInSlot(i) != null) {
                NBTTagCompound item = new NBTTagCompound();
                item.setInteger(slotKey, i);
                getStackInSlot(i).writeToNBT(item);
                items.appendTag(item);
            }
        }

        tagcompound.setTag(inventoryKey, items);
    }

    @Override
    public int getSizeInventory() {
        return numSlots;
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < getSizeInventory(); i++) {
            if (!getStackInSlot(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return inventoryContents.get(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        ItemStack itemstack = ItemStackHelper.getAndSplit(this.inventoryContents, index, count);

        if (!itemstack.isEmpty()) {
            this.markDirty();
        }

        return itemstack;
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        ItemStack itemStack = this.inventoryContents.get(index);

        if (itemStack.isEmpty()) {
            return itemStack;
        } else {
            this.inventoryContents.set(index, ItemStack.EMPTY);
            return itemStack;
        }
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        this.inventoryContents.set(index, stack);

        if (!stack.isEmpty() && stack.getCount() > this.getInventoryStackLimit()) {
            stack.setCount(this.getInventoryStackLimit());
        }

        this.markDirty();
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void markDirty() {
        for (int i = 0; i < getSizeInventory(); ++i) {
            if (getStackInSlot(i).getCount() == 0) {
                inventoryContents.set(i, ItemStack.EMPTY);
            }
        }

        writeToNBT(NBTHelper.getTag(toolbeltItemStack));
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public void openInventory(EntityPlayer player) {}

    @Override
    public void closeInventory(EntityPlayer player) {}

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return isItemValid(stack);
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {}

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {
        inventoryContents.clear();
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public ITextComponent getDisplayName() {
        return null;
    }

    public ItemStack takeItemStack(int index) {
        ItemStack itemStack = getStackInSlot(index);
        setInventorySlotContents(index, ItemStack.EMPTY);
        return itemStack;
    }

    public void emptyOverflowSlots(EntityPlayer player) {
        for (int i = getSizeInventory(); i < maxSize; i++) {
            moveStackToPlayer(removeStackFromSlot(i), player);
        }
        this.markDirty();
    }

    protected void moveStackToPlayer(ItemStack itemStack, EntityPlayer player) {
        if (!itemStack.isEmpty()) {
            if (!player.inventory.addItemStackToInventory(itemStack)) {
                player.dropItem(itemStack, false);
            }
        }
    }

    public boolean isItemValid(ItemStack itemStack) {
        return predicate.test(itemStack);
    }

    public boolean storeItemInInventory(ItemStack itemStack) {
        if (!isItemValid(itemStack)) {
            return false;
        }

        // attempt to merge the itemstack with itemstacks in the toolbelt
        for (int i = 0; i < getSizeInventory(); i++) {
            ItemStack storedStack = getStackInSlot(i);
            if (storedStack.isItemEqual(itemStack)
                    && storedStack.getCount() < storedStack.getMaxStackSize()) {

                int moveCount = Math.min(itemStack.getCount(), storedStack.getMaxStackSize() - storedStack.getCount());
                storedStack.grow(moveCount);
                setInventorySlotContents(i, storedStack);
                itemStack.shrink(moveCount);

                if (itemStack.isEmpty()) {
                    return true;
                }
            }
        }

        // put item in the first empty slot
        for (int i = 0; i < getSizeInventory(); i++) {
            if (getStackInSlot(i).isEmpty()) {
                setInventorySlotContents(i, itemStack);
                return true;
            }
        }
        return false;
    }

    public int getFirstIndexForItem(Item item) {
        for (int i = 0; i < inventoryContents.size(); i++) {
            if (!inventoryContents.get(i).isEmpty() && inventoryContents.get(i).getItem().equals(item)) {
                return i;
            }
        }
        return -1;
    }

    public List<Collection<ItemEffect>> getSlotEffects() {
        return ItemToolbeltModular.instance.getSlotEffects(toolbeltItemStack, inventoryType);
    }

    public static void setPredicates(ItemPredicate[] predicates) {

    }
}
