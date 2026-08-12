package de.saschat.tfcc.mixin.tfc.blast;

import net.dries007.tfc.client.RenderHelpers;
import net.dries007.tfc.client.screen.BlastFurnaceScreen;
import net.dries007.tfc.client.screen.BlockEntityScreen;
import net.dries007.tfc.common.blockentities.BlastFurnaceBlockEntity;
import net.dries007.tfc.common.capabilities.heat.Heat;
import net.dries007.tfc.common.capabilities.heat.HeatCapability;
import net.dries007.tfc.common.container.BlastFurnaceContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mixin(BlastFurnaceScreen.class)
public class BlastFurnaceScreenMixin extends BlockEntityScreen<BlastFurnaceBlockEntity, BlastFurnaceContainer> {
    private BlastFurnaceScreenMixin(BlastFurnaceContainer container, Inventory playerInventory, Component name, ResourceLocation texture) {
        super(container, playerInventory, name, texture);
    }

    @Unique
    int tfcc$lastMaxX;
    @Unique
    int tfcc$lastMaxY;

    @Unique
    private static final int tfcc$offX = 2, tfcc$offY = 2;

    @Unique
    private void drawHeat(GuiGraphics graphics, int y, float[] heat, String[] text) {
        Font f = Minecraft.getInstance().font;
        int longest = 0;
        for (String s : text) {
            longest = Math.max(longest, f.width(s + ": "));
        }

        int lh = (int) (f.lineHeight * 1.5);

        for (int q = 0; q < text.length; q++) {
            graphics.drawString(f, text[q] + ":", tfcc$offX, y + q * lh, 0xE0E0E0);
        }

        int realLong = 0;
        for (int q = 0; q < heat.length; q++) {
            Heat h = Heat.getHeat(heat[q]);
            String str = String.format("%.1f", heat[q]);
            realLong = Math.max(realLong + longest, f.width(str));

            if (h != null && h.getColor().getColor() != null)
                graphics.drawString(f, str, 2 + longest, y + q * lh, h.getColor().getColor());
            else
                graphics.drawString(f, "None", tfcc$offX + longest, y + q * lh, 0xE0E0E0);
        }

        tfcc$lastMaxX = realLong;
        tfcc$lastMaxY = heat.length * lh;
    }

    @Inject(at = @At("HEAD"), method = "renderBg")
    protected void renderBg(GuiGraphics graphics, float partialTicks, int mouseX, int mouseY, CallbackInfo info) {
        List<ItemStack> input = ((BlastFurnaceBlockEntityAccessor) blockEntity).getInputStacks();
        float min = Integer.MAX_VALUE, avg = 0, max = 0;
        for (ItemStack itemStack : input) {
            float temp = HeatCapability.getTemperature(itemStack);
            min = Math.min(min, temp);
            max = Math.max(max, temp);
            avg += temp;
        }
        float heat = blockEntity.getTemperature();
        if (input.isEmpty()) return;

        avg /= input.size();

        drawHeat(graphics, tfcc$offY,
                new float[]{min, avg, max, heat},
                new String[]{"Min", "Avg", "Max", "Blk"}
        );
    }

    @Inject(at = @At("HEAD"), method = "renderTooltip")
    protected void tool(GuiGraphics graphics, int mouseX, int mouseY, CallbackInfo ci) {
        if (RenderHelpers.isInside(mouseX, mouseY, tfcc$offX, tfcc$offY, tfcc$lastMaxX - tfcc$offX, tfcc$lastMaxY - tfcc$offY)) {
            List<Component> c = new ArrayList<>();

            for (Heat value : Heat.values()) {
                if (value.getColor().getColor() != null) {
                    MutableComponent mutableComponent = value.getDisplayName().setStyle(Style.EMPTY.withColor(value.getColor().getColor()));
                    mutableComponent.append(String.format(
                            "%.1f - %.1f",
                            value.getMin(),
                            value.getMax()
                    ));
                    c.add(mutableComponent);
                }
            }

            graphics.renderTooltip(this.font, c, Optional.empty(), mouseX, mouseY);
        }
    }

}
