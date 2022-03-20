package ru.bclib.items;

import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import ru.bclib.client.models.BasePatterns;
import ru.bclib.client.models.ModelsHelper;
import ru.bclib.client.models.PatternsHelper;
import ru.bclib.interfaces.ItemModelProvider;

import java.util.Optional;

public class BaseSpawnEggItem extends SpawnEggItem implements ItemModelProvider {
	public BaseSpawnEggItem(EntityType<? extends Mob> type, int primaryColor, int secondaryColor, Properties settings) {
		super(type, primaryColor, secondaryColor, settings);
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public BlockModel getItemModel(ResourceLocation resourceLocation) {
		Optional<String> pattern = PatternsHelper.createJson(BasePatterns.ITEM_SPAWN_EGG, resourceLocation);
		return ModelsHelper.fromPattern(pattern);
	}
}
