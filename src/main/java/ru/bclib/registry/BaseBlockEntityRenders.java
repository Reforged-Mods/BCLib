package ru.bclib.registry;

import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import ru.bclib.client.render.BaseChestBlockEntityRenderer;
import ru.bclib.client.render.BaseSignBlockEntityRenderer;

@OnlyIn(Dist.CLIENT)
public class BaseBlockEntityRenders {
	public static void register() {
		BlockEntityRendererRegistry.register(BaseBlockEntities.CHEST, BaseChestBlockEntityRenderer::new);
		BlockEntityRendererRegistry.register(BaseBlockEntities.SIGN, BaseSignBlockEntityRenderer::new);
	}
}
