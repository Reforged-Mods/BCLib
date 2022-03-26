package ru.bclib.items;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.level.material.Fluids;
import ru.bclib.interfaces.ItemModelProvider;

public class BaseBucketItem extends MobBucketItem implements ItemModelProvider {
	public BaseBucketItem(EntityType<?> type, Item.Properties settings) {
		super(type, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings.stacksTo(1));
	}
}
