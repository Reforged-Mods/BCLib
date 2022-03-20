package ru.bclib.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import ru.bclib.client.models.BasePatterns;
import ru.bclib.client.models.ModelsHelper;
import ru.bclib.client.models.PatternsHelper;
import ru.bclib.interfaces.BlockModelProvider;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BaseStairsBlock extends StairBlock implements BlockModelProvider {
	private final Block parent;
	
	public BaseStairsBlock(Block source) {
		super(source.defaultBlockState(), FabricBlockSettings.copyOf(source));
		this.parent = source;
	}
	
	@Override
	@SuppressWarnings("deprecation")
	public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
		return Collections.singletonList(new ItemStack(this));
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public BlockModel getItemModel(ResourceLocation resourceLocation) {
		return getBlockModel(resourceLocation, defaultBlockState());
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public @Nullable BlockModel getBlockModel(ResourceLocation blockId, BlockState blockState) {
		ResourceLocation parentId = Registry.BLOCK.getKey(parent);
		Optional<String> pattern = Optional.empty();
		switch (blockState.getValue(SHAPE)) {
			case STRAIGHT:
				pattern = PatternsHelper.createJson(BasePatterns.BLOCK_STAIR, parentId);
				break;
			case INNER_LEFT:
			case INNER_RIGHT:
				pattern = PatternsHelper.createJson(BasePatterns.BLOCK_STAIR_INNER, parentId);
				break;
			case OUTER_LEFT:
			case OUTER_RIGHT:
				pattern = PatternsHelper.createJson(BasePatterns.BLOCK_STAIR_OUTER, parentId);
				break;
		}
		return ModelsHelper.fromPattern(pattern);
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public UnbakedModel getModelVariant(ResourceLocation stateId, BlockState blockState, Map<ResourceLocation, UnbakedModel> modelCache) {
		String state;
		StairsShape shape = blockState.getValue(SHAPE);
		switch (shape) {
			case INNER_LEFT:
			case INNER_RIGHT:
				state = "_inner";
				break;
			case OUTER_LEFT:
			case OUTER_RIGHT:
				state = "_outer";
				break;
			default:
				state = "";
		}
		ResourceLocation modelId = new ResourceLocation(stateId.getNamespace(), "block/" + stateId.getPath() + state);
		registerBlockModel(stateId, modelId, blockState, modelCache);
		
		boolean isTop = blockState.getValue(HALF) == Half.TOP;
		boolean isLeft = shape == StairsShape.INNER_LEFT || shape == StairsShape.OUTER_LEFT;
		boolean isRight = shape == StairsShape.INNER_RIGHT || shape == StairsShape.OUTER_RIGHT;
		int y = 0;
		int x = isTop ? 180 : 0;
		switch (blockState.getValue(FACING)) {
			case NORTH:
				if (isTop && !isRight) y = 270;
				else if (!isTop) y = isLeft ? 180 : 270;
				break;
			case EAST:
				if (isTop && isRight) y = 90;
				else if (!isTop && isLeft) y = 270;
				break;
			case SOUTH:
				if (isTop) y = isRight ? 180 : 90;
				else if (!isLeft) y = 90;
				break;
			case WEST:
			default:
				y = (isTop && isRight) ? 270 : (!isTop && isLeft) ? 90 : 180;
				break;
		}
		BlockModelRotation rotation = BlockModelRotation.by(x, y);
		return ModelsHelper.createMultiVariant(modelId, rotation.getRotation(), true);
	}
}
