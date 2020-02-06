package info.u_team.useful_backpacks.crafting;

import java.util.List;

import com.google.common.collect.Lists;

import info.u_team.useful_backpacks.item.ItemBackPack;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class RecipesBackPackDyes extends net.minecraftforge.registries.IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
	
	/**
	 * TODO Just Copied {@link RecipesArmorDyes} -> Works well, but code is ugly
	 */
	
	public boolean matches(InventoryCrafting inv, World worldIn) {
		ItemStack itemstack = ItemStack.EMPTY;
		List<ItemStack> list = Lists.<ItemStack> newArrayList();
		
		for (int i = 0; i < inv.getSizeInventory(); ++i) {
			ItemStack itemstack1 = inv.getStackInSlot(i);
			
			if (!itemstack1.isEmpty()) {
				if (itemstack1.getItem() instanceof ItemBackPack) {
					itemstack = itemstack1;
				} else {
					if (!net.minecraftforge.oredict.DyeUtils.isDye(itemstack1)) {
						return false;
					}
					
					list.add(itemstack1);
				}
			}
		}
		
		return !itemstack.isEmpty() && !list.isEmpty();
	}
	
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		ItemStack itemstack = ItemStack.EMPTY;
		int[] aint = new int[3];
		int i = 0;
		int j = 0;
		ItemBackPack itemarmor = null;
		
		for (int k = 0; k < inv.getSizeInventory(); ++k) {
			ItemStack itemstack1 = inv.getStackInSlot(k);
			
			if (!itemstack1.isEmpty()) {
				if (itemstack1.getItem() instanceof ItemBackPack) {
					itemarmor = (ItemBackPack) itemstack1.getItem();
					
					itemstack = itemstack1.copy();
					itemstack.setCount(1);
					
					if (itemarmor.hasColor(itemstack1)) {
						int l = itemarmor.getColor(itemstack);
						float f = (float) (l >> 16 & 255) / 255.0F;
						float f1 = (float) (l >> 8 & 255) / 255.0F;
						float f2 = (float) (l & 255) / 255.0F;
						i = (int) ((float) i + Math.max(f, Math.max(f1, f2)) * 255.0F);
						aint[0] = (int) ((float) aint[0] + f * 255.0F);
						aint[1] = (int) ((float) aint[1] + f1 * 255.0F);
						aint[2] = (int) ((float) aint[2] + f2 * 255.0F);
						++j;
					}
				} else {
					if (!net.minecraftforge.oredict.DyeUtils.isDye(itemstack1)) {
						return ItemStack.EMPTY;
					}
					
					float[] afloat = net.minecraftforge.oredict.DyeUtils.colorFromStack(itemstack1).get().getColorComponentValues();
					int l1 = (int) (afloat[0] * 255.0F);
					int i2 = (int) (afloat[1] * 255.0F);
					int j2 = (int) (afloat[2] * 255.0F);
					i += Math.max(l1, Math.max(i2, j2));
					aint[0] += l1;
					aint[1] += i2;
					aint[2] += j2;
					++j;
				}
			}
		}
		
		if (itemarmor == null) {
			return ItemStack.EMPTY;
		} else {
			int i1 = aint[0] / j;
			int j1 = aint[1] / j;
			int k1 = aint[2] / j;
			float f3 = (float) i / (float) j;
			float f4 = (float) Math.max(i1, Math.max(j1, k1));
			i1 = (int) ((float) i1 * f3 / f4);
			j1 = (int) ((float) j1 * f3 / f4);
			k1 = (int) ((float) k1 * f3 / f4);
			int k2 = (i1 << 8) + j1;
			k2 = (k2 << 8) + k1;
			itemarmor.setColor(itemstack, k2);
			return itemstack;
		}
	}
	
	public ItemStack getRecipeOutput() {
		return ItemStack.EMPTY;
	}
	
	public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
		NonNullList<ItemStack> nonnulllist = NonNullList.<ItemStack> withSize(inv.getSizeInventory(), ItemStack.EMPTY);
		
		for (int i = 0; i < nonnulllist.size(); ++i) {
			ItemStack itemstack = inv.getStackInSlot(i);
			nonnulllist.set(i, net.minecraftforge.common.ForgeHooks.getContainerItem(itemstack));
		}
		
		return nonnulllist;
	}
	
	public boolean isDynamic() {
		return true;
	}
	
	public boolean canFit(int width, int height) {
		return width * height >= 2;
	}
}