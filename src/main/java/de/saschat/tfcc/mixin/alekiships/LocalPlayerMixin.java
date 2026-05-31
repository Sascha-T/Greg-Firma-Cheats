package de.saschat.tfcc.mixin.alekiships;

import com.alekiponi.alekiships.common.entity.vehiclehelper.compartment.EmptyCompartmentEntity;
import com.mojang.authlib.GameProfile;
import de.saschat.tfcc.integrations.alekiships.steering.SteeringStrategy;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = LocalPlayer.class, priority = Integer.MIN_VALUE)
public class LocalPlayerMixin extends AbstractClientPlayer {

    public LocalPlayerMixin(ClientLevel p_250460_, GameProfile p_249912_) {
        super(p_250460_, p_249912_);
    }

    @Inject(at=@At("TAIL"), method = "rideTick")
    public void lateTail(CallbackInfo ci) {
        if(getVehicle() instanceof EmptyCompartmentEntity compartment)
            SteeringStrategy.update(compartment, (LocalPlayer) (Object) this);
    }
}
