package de.saschat.tfcc.mixin.tfc.anvil;

import de.saschat.tfcc.integrations.tfc.Main;
import net.dries007.tfc.client.screen.button.AnvilPlanSelectButton;
import net.dries007.tfc.common.recipes.AnvilRecipe;
import net.minecraft.client.gui.components.Button;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilPlanSelectButton.class)
public class AnvilPlanSelectButtonMixin {

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/CompoundTag;putString(Ljava/lang/String;Ljava/lang/String;)V"), method = "lambda$new$0")
    private static void setRecipe(AnvilRecipe recipe, Button button, CallbackInfo ci) {
        Main.LAST_RECIPE = recipe;
    }
}
