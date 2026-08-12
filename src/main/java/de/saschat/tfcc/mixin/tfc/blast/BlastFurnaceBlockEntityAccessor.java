package de.saschat.tfcc.mixin.tfc.blast;

import net.dries007.tfc.common.blockentities.BlastFurnaceBlockEntity;
import net.dries007.tfc.common.capabilities.forge.ForgeStep;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(BlastFurnaceBlockEntity.class)
public interface BlastFurnaceBlockEntityAccessor {
    @Accessor("inputStacks")
    List<ItemStack> getInputStacks();
}
