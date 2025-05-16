package info.partonetrain.thirstwasfixed.mixin;

import info.partonetrain.thirstwasfixed.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LayeredCauldronBlock.class)
public class LayeredCauldronBlockMixin extends Block {
    public LayeredCauldronBlockMixin(Properties properties) {
        super(properties);
    }

    protected @NotNull InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if(Config.DRINK_CAULDRONS.get()){

        }
        return InteractionResult.PASS;
    }
}
