package isensehostility.interactions.entity.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;

public class SquidFleeGoal extends Goal {
    private int fleeTicks;
    private Squid squid;

    public SquidFleeGoal(Squid squid) {
        this.squid = squid;
    }

    public boolean canUse() {
        LivingEntity livingentity = squid.getLastHurtByMob();
        if (squid.isInWater() && livingentity != null) {
            return squid.distanceToSqr(livingentity) < 100.0D;
        } else {
            return false;
        }
    }

    public void start() {
        this.fleeTicks = 0;
    }

    public boolean requiresUpdateEveryTick() {
        return true;
    }

    public void tick() {
        ++this.fleeTicks;
        LivingEntity livingentity = squid.getLastHurtByMob();
        if (livingentity != null) {
            Vec3 vec3 = new Vec3(squid.getX() - livingentity.getX(), squid.getY() - livingentity.getY(), squid.getZ() - livingentity.getZ());
            BlockState blockstate = squid.level.getBlockState(new BlockPos(squid.getX() + vec3.x, squid.getY() + vec3.y, squid.getZ() + vec3.z));
            FluidState fluidstate = squid.level.getFluidState(new BlockPos(squid.getX() + vec3.x, squid.getY() + vec3.y, squid.getZ() + vec3.z));
            if (fluidstate.is(FluidTags.WATER) || blockstate.isAir()) {
                double d0 = vec3.length();
                if (d0 > 0.0D) {
                    vec3.normalize();
                    double d1 = 3.0D;
                    if (d0 > 5.0D) {
                        d1 -= (d0 - 5.0D) / 5.0D;
                    }

                    if (d1 > 0.0D) {
                        vec3 = vec3.scale(d1);
                    }
                }

                if (blockstate.isAir()) {
                    vec3 = vec3.subtract(0.0D, vec3.y, 0.0D);
                }

                squid.setMovementVector((float)vec3.x / 20.0F, (float)vec3.y / 20.0F, (float)vec3.z / 20.0F);
            }

            if (this.fleeTicks % 10 == 5) {
                squid.level.addParticle(ParticleTypes.BUBBLE, squid.getX(), squid.getY(), squid.getZ(), 0.0D, 0.0D, 0.0D);
            }

        }
    }
}
