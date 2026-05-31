package de.saschat.tfcc.mixin.alekiships;

import com.alekiponi.alekiships.common.entity.vehiclehelper.compartment.EmptyCompartmentEntity;
import de.saschat.tfcc.integrations.alekiships.Main;
import de.saschat.tfcc.integrations.alekiships.steering.SteeringStrategy;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = EmptyCompartmentEntity.class, remap = false)
public class EmptyCompartmentEntityMixin {

    @ModifyVariable(method = "setInput", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    public boolean left(boolean arg2) {
        return Main.isEnabled ? SteeringStrategy.shouldSteerLeft() : arg2;
    }
    @ModifyVariable(method = "setInput", at = @At("HEAD"), ordinal = 1, argsOnly = true)
    public boolean right(boolean arg2) {
        return Main.isEnabled ? SteeringStrategy.shouldSteerRight() : arg2;
    }
    @ModifyVariable(method = "setInput", at = @At("HEAD"), ordinal = 2, argsOnly = true)
    public boolean up(boolean arg2) {
        return Main.isEnabled ? SteeringStrategy.shouldSteerForward() : arg2;
    }
    @ModifyVariable(method = "setInput", at = @At("HEAD"), ordinal = 3, argsOnly = true)
    public boolean down(boolean arg2) {
        return Main.isEnabled ? SteeringStrategy.shouldSteerBackward() : arg2;
    }
}
