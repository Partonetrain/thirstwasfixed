package info.partonetrain.thirstwasfixed.mixin.ae;

import alexthw.ars_elemental.common.blocks.EverfullUrnBlock;
import alexthw.ars_elemental.common.blocks.EverfullUrnTile;
import com.hollingsworth.arsnouveau.api.potion.IPotionProvider;
import com.hollingsworth.arsnouveau.api.registry.PotionProviderRegistry;
import com.hollingsworth.arsnouveau.api.util.SourceUtil;
import com.hollingsworth.arsnouveau.common.items.data.MultiPotionContents;
import com.hollingsworth.arsnouveau.setup.registry.DataComponentRegistry;
import dev.ghen.thirst.content.purity.WaterPurity;
import dev.ghen.thirst.foundation.common.capability.IThirst;
import dev.ghen.thirst.foundation.common.capability.ModAttachment;
import dev.ghen.thirst.foundation.config.CommonConfig;
import info.partonetrain.thirstwasfixed.ArsNouveauHelper;
import info.partonetrain.thirstwasfixed.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import static alexthw.ars_elemental.ConfigHandler.Common.WATER_URN_COST;

@Mixin(EverfullUrnBlock.class)
public class EverfullUrnBlockMixin extends Block {
    public EverfullUrnBlockMixin(Properties properties) {
        super(properties);
    }

    //drink from directly
    @Override
    @Unique
    protected @NotNull InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if(Config.AE_EVERFULL_DRINK_FROM.get() && player.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()){
            if(thirstwasfixed$requestSourceIfConfigNeeds(level, pos)){
                IThirst thirst = player.getData(ModAttachment.PLAYER_THIRST);
                if(thirst.getThirst() == 20){
                    return InteractionResult.FAIL;
                }

                thirst.drink(CommonConfig.HAND_DRINKING_HYDRATION.get().intValue(), CommonConfig.HAND_DRINKING_QUENCHED.get().intValue());
                WaterPurity.givePurityEffects(player, Config.AE_EVERFULL_PURITY.getAsInt());
                level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.GENERIC_DRINK, SoundSource.PLAYERS, 1.0F, 1.0F);

                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    //set bottle/bucket purity
    @Override
    @Unique
    protected @NotNull ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if(Config.AE_EVERFULL_FILL_FROM.get() && thirstwasfixed$requestSourceIfConfigNeeds(level, pos)){
            if(stack.is(Items.GLASS_BOTTLE)){
                player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
                ItemStack newStack = PotionContents.createItemStack(Items.POTION, Potions.WATER);
                WaterPurity.addPurity(newStack, Config.AE_EVERFULL_PURITY.getAsInt());
                stack.shrink(1);
                player.getInventory().add(newStack);
                level.playSound(player, pos, SoundEvents.BOTTLE_FILL, SoundSource.PLAYERS, 1.0F, 1.0F);
                return ItemInteractionResult.SUCCESS;
            }
            else if(stack.is(Items.BUCKET)){ //seems to have precedent over pickupBlock in the target method? but there may be an edge case
                player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
                ItemStack newStack = new ItemStack(Items.WATER_BUCKET);
                WaterPurity.addPurity(newStack, Config.AE_EVERFULL_PURITY.getAsInt());
                stack.shrink(1);
                player.getInventory().add(newStack);
                level.playSound(player, pos, SoundEvents.BUCKET_FILL, SoundSource.PLAYERS, 1.0F, 1.0F);
                return ItemInteractionResult.SUCCESS;
            }
            else if(Config.AE_EVERFULL_FLASK.get() && ArsNouveauHelper.isStackFlask(stack)){
                MultiPotionContents multiPotionContents = stack.getComponents().get(DataComponentRegistry.MULTI_POTION.get());
                 if(ArsNouveauHelper.isContentsEmptyOrWater(multiPotionContents)){
                    if(multiPotionContents.usesRemaining(stack) < multiPotionContents.maxUses()){

                        IPotionProvider data = PotionProviderRegistry.from(stack);
                        int usesRemaining = data.usesRemaining(stack);
                        int maxUses = data.maxUses(stack);

                        var newContents = new MultiPotionContents(usesRemaining + 1, new PotionContents(Potions.WATER), maxUses);
                        stack.set(DataComponentRegistry.MULTI_POTION, newContents);
                        return ItemInteractionResult.SUCCESS;
                    }
                }
            }
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Unique
    private boolean thirstwasfixed$requestSourceIfConfigNeeds(Level level, BlockPos pos) {
        if (!Config.AE_EVERFULL_REQUESTS_SOURCE.get()) {
            return true;
        } else {
            EverfullUrnTile blockEntity = (EverfullUrnTile) level.getBlockEntity(pos);
            if (blockEntity != null) {
                if (SourceUtil.hasSourceNearby(blockEntity.getBlockPos(), level, 6, WATER_URN_COST.get())){
                    SourceUtil.takeSourceMultiple(blockEntity.getBlockPos(), level, 6, WATER_URN_COST.get());
                    return true;
                }
                else
                {
                    return false;
                }
            }
            return false; //something has gone terribly wrong and there is no BE here
        }
    }
}
