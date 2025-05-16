package info.partonetrain.thirstwasfixed.mixin.an;

import com.hollingsworth.arsnouveau.api.potion.IPotionProvider;
import com.hollingsworth.arsnouveau.api.registry.PotionProviderRegistry;
import com.hollingsworth.arsnouveau.common.items.PotionFlask;
import com.hollingsworth.arsnouveau.common.items.data.MultiPotionContents;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import com.hollingsworth.arsnouveau.setup.registry.DataComponentRegistry;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import info.partonetrain.thirstwasfixed.ArsNouveauHelper;
import info.partonetrain.thirstwasfixed.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PotionFlask.class)
public class PotionFlaskItemMixin extends Item {

    public PotionFlaskItemMixin(Properties properties) {
        super(properties);
    }

    //pick up water from cauldron
    @Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
    public void useOn(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        if(Config.AN_FLASK_PICKS_UP_WATER.get() && !context.isSecondaryUseActive()) {
            Level level = context.getLevel();
            BlockState state = level.getBlockState(context.getClickedPos());
            if (state.is(Blocks.WATER_CAULDRON)) {
                ItemStack itemstack = context.getItemInHand();
                MultiPotionContents multiPotionContents = itemstack.getComponents().get(DataComponentRegistry.MULTI_POTION.get());
                if (ArsNouveauHelper.isContentsEmptyOrWater(multiPotionContents)) {
                    if (multiPotionContents.usesRemaining(itemstack) < multiPotionContents.maxUses()) {
                        if (state.getValue(LayeredCauldronBlock.LEVEL) >= LayeredCauldronBlock.MIN_FILL_LEVEL) {
                            int nextValue = state.getValue(LayeredCauldronBlock.LEVEL) - 1;
                            if(nextValue == 0){
                                level.setBlock(context.getClickedPos(), Blocks.CAULDRON.defaultBlockState(), 3);
                            }else{
                                level.setBlock(context.getClickedPos(), state.setValue(LayeredCauldronBlock.LEVEL, state.getValue(LayeredCauldronBlock.LEVEL) - 1), 3);
                            }
                            level.playSound(context.getPlayer(), context.getClickedPos(), SoundEvents.BOTTLE_FILL, SoundSource.PLAYERS, 1.0F, 1.0F);
                            ItemStack newStack = itemstack.copy();

                            IPotionProvider data = PotionProviderRegistry.from(itemstack);
                            int usesRemaining = data.usesRemaining(itemstack);
                            int maxUses = data.maxUses(itemstack);

                            var newContents = new MultiPotionContents(usesRemaining + 1, new PotionContents(Potions.WATER), maxUses);
                            newStack.set(DataComponentRegistry.MULTI_POTION, newContents);
                            context.getPlayer().setItemInHand(context.getHand(), newStack);
                            cir.setReturnValue(InteractionResult.sidedSuccess(level.isClientSide()));
                            cir.cancel();
                        }
                    }
                }
            }
        }
    }

    //pick up water from water source block (or waterlogged block)
    @Inject(method = "use", at = @At("TAIL"), cancellable = true)
    public void use(@NotNull Level level, Player player, @NotNull InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir){
            ItemStack itemstack = player.getItemInHand(hand);
            MultiPotionContents multiPotionContents = itemstack.getComponents().get(DataComponentRegistry.MULTI_POTION.get());

            if(Config.AN_FLASK_EMPTY.get() && multiPotionContents.usesRemaining(itemstack) > 0 && hand == InteractionHand.OFF_HAND
                    && player.getMainHandItem().isEmpty() && player.isSecondaryUseActive()) {
                var newContents = new MultiPotionContents(0, PotionContents.EMPTY, multiPotionContents.maxUses());
                ItemStack newStack = itemstack.copy();
                newStack.set(DataComponentRegistry.MULTI_POTION, newContents);
                PortUtil.sendMessageCenterScreen(player, Component.translatable("thirstwasfixed.flask.empty")); //AN doesn't use this method, hopefully it doesn't get removed!
                cir.setReturnValue(InteractionResultHolder.sidedSuccess(newStack, level.isClientSide()));
            }
            else if(Config.AN_FLASK_PICKS_UP_WATER.get() && ArsNouveauHelper.isContentsEmptyOrWater(multiPotionContents)){
                if(multiPotionContents.usesRemaining(itemstack) < multiPotionContents.maxUses()){
                    BlockHitResult blockhitresult = Item.getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
                    if (blockhitresult.getType() == HitResult.Type.BLOCK) {
                        BlockPos blockpos = blockhitresult.getBlockPos();
                        if (!level.mayInteract(player, blockpos)) {
                            cir.setReturnValue(InteractionResultHolder.pass(itemstack));
                        }

                        if (level.getFluidState(blockpos).is(FluidTags.WATER)) {
                            level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.PLAYERS, 1.0F, 1.0F);
                            ItemStack newStack = itemstack.copy();

                            IPotionProvider data = PotionProviderRegistry.from(itemstack);
                            int usesRemaining = data.usesRemaining(itemstack);
                            int maxUses = data.maxUses(itemstack);

                            var newContents = new MultiPotionContents(usesRemaining + 1, new PotionContents(Potions.WATER), maxUses);
                            newStack.set(DataComponentRegistry.MULTI_POTION, newContents);

                            cir.setReturnValue(InteractionResultHolder.sidedSuccess(newStack, level.isClientSide()));
                        }
                    }
                }
            }
    }

    //modify flask capacity
    @Unique
    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if(entity instanceof Player player && !level.isClientSide()) {
            if (player.getMainHandItem() == stack || player.getOffhandItem() == stack) { //update max capacity if flask is in hand
                MultiPotionContents multiPotionContents = stack.getComponents().get(DataComponentRegistry.MULTI_POTION.get());
                if (multiPotionContents.maxUses(stack) < Config.AN_FLASK_SIZE.getAsInt()) {
                    stack.set(DataComponentRegistry.MULTI_POTION, new MultiPotionContents(multiPotionContents.usesRemaining(stack), multiPotionContents.contents(), Config.AN_FLASK_SIZE.getAsInt()));
                }
            }
        }
    }

    @ModifyArg(method = "<init>()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item$Properties;durability(I)Lnet/minecraft/world/item/Item$Properties;"))
    private static int modifyDurability(int durability) {
        if(Config.AN_FLASK_SIZE.getAsInt() == Config.AN_FLASK_SIZE.getDefault()){
            return durability;
        }
        else{
            return Config.AN_FLASK_SIZE.get();
        }
    }
    @ModifyReturnValue(method = "getMaxCapacity", at = @At("RETURN"))
    private int modifyMaxCapacity(int maxCapacity) {
        if(Config.AN_FLASK_SIZE.getAsInt() == Config.AN_FLASK_SIZE.getDefault()){
            return maxCapacity;
        }
        else{
            return Config.AN_FLASK_SIZE.get();
        }
    }
    @ModifyArg(method="finishUsingItem", at= @At(value = "INVOKE", target = "Lcom/hollingsworth/arsnouveau/common/items/data/MultiPotionContents;<init>(ILnet/minecraft/world/item/alchemy/PotionContents;I)V"), index = 2)
    private int modifyMaxCapacityOnFinsihed(int maxCapacity) {
        if(Config.AN_FLASK_SIZE.getAsInt() == Config.AN_FLASK_SIZE.getDefault()){
            return maxCapacity;
        }
        else{
            return Config.AN_FLASK_SIZE.get();
        }
    }
    @ModifyArg(method="getDamage", at= @At(value = "INVOKE", target = "Lcom/hollingsworth/arsnouveau/common/items/data/MultiPotionContents;<init>(ILnet/minecraft/world/item/alchemy/PotionContents;I)V"), index = 2)
    private int modifyMaxCapacityOnDamage(int maxCapacity) {
        if(Config.AN_FLASK_SIZE.getAsInt() == Config.AN_FLASK_SIZE.getDefault()){
            return maxCapacity;
        }
        else{
            return Config.AN_FLASK_SIZE.get();
        }
    }
    @ModifyArg(method="use", at= @At(value = "INVOKE", target = "Lcom/hollingsworth/arsnouveau/common/items/data/MultiPotionContents;<init>(ILnet/minecraft/world/item/alchemy/PotionContents;I)V"), index = 2)
    private int modifyMaxCapacityUse(int maxCapacity) {
        if(Config.AN_FLASK_SIZE.getAsInt() == Config.AN_FLASK_SIZE.getDefault()){
            return maxCapacity;
        }
        else{
            return Config.AN_FLASK_SIZE.get();
        }
    }
    @ModifyArg(method="appendHoverText", at= @At(value = "INVOKE", target = "Lcom/hollingsworth/arsnouveau/common/items/data/MultiPotionContents;<init>(ILnet/minecraft/world/item/alchemy/PotionContents;I)V"), index = 2)
    private int modifyMaxCapacityHover(int maxCapacity) {
        if(Config.AN_FLASK_SIZE.getAsInt() == Config.AN_FLASK_SIZE.getDefault()){
            return maxCapacity;
        }
        else{
            return Config.AN_FLASK_SIZE.get();
        }
    }
}
