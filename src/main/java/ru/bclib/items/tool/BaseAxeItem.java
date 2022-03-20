package ru.bclib.items.tool;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Tier;
import ru.bclib.client.models.ModelsHelper;
import ru.bclib.interfaces.ItemModelProvider;

public class BaseAxeItem extends AxeItem implements ItemModelProvider {
	public BaseAxeItem(Tier material, float attackDamage, float attackSpeed, Properties settings) {
		super(material, attackDamage, attackSpeed, settings);
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public BlockModel getItemModel(ResourceLocation resourceLocation) {
		return ModelsHelper.createHandheldItem(resourceLocation);
	}
}
