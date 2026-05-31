package de.saschat.tfcc.integrations.alekiships.steering;

import com.alekiponi.alekiships.common.entity.vehiclehelper.compartment.EmptyCompartmentEntity;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.server.commands.data.DataCommands;

public class SteeringStrategy {
    public static final float Y_ROT_THRESHOLD = 5f; // degrees
    public static boolean needAdjustment = false;
    public static boolean turnLeft = false;
    public static boolean shouldSteerLeft() {
        return needAdjustment && turnLeft;
    }
    public static boolean shouldSteerRight() {
        return needAdjustment && !turnLeft;
    }
    public static boolean shouldSteerForward() {
        return true;
    }
    public static boolean shouldSteerBackward() {
        return false;
    }

    public static void update(EmptyCompartmentEntity compartment, LocalPlayer thiz) {
        float offset = compartment.getYRot() - thiz.getYRot();
        if(Math.abs(offset) > Y_ROT_THRESHOLD) {
            needAdjustment = true;

            turnLeft = offset > 0;
        } else needAdjustment = false;
    }
}
