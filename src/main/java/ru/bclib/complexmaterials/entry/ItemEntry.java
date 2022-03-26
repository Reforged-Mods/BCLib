package ru.bclib.complexmaterials.entry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import ru.bclib.api.tag.TagAPI;
import ru.bclib.api.tag.TagAPI.TagLocation;
import ru.bclib.complexmaterials.ComplexMaterial;
import ru.bclib.registry.ItemRegistry;

import java.util.function.BiFunction;

public class ItemEntry extends ComplexMaterialEntry {
	final BiFunction<ComplexMaterial, Item.Properties, Item> initFunction;
	
	TagLocation<Item>[] itemTags;
	
	public ItemEntry(String suffix, BiFunction<ComplexMaterial, Item.Properties, Item> initFunction) {
		super(suffix);
		this.initFunction = initFunction;
	}
	
	public ItemEntry setItemTags(TagLocation<Item>[] itemTags) {
		this.itemTags = itemTags;
		return this;
	}
	
	public Item init(ComplexMaterial material, Item.Properties itemSettings, ItemRegistry registry) {
		ResourceLocation location = getLocation(material.getModID(), material.getBaseName());
		Item item = initFunction.apply(material, itemSettings);
		registry.register(location, item);
		if (itemTags != null) {
			TagAPI.addItemTags(item, itemTags);
		}
		return item;
	}
}
