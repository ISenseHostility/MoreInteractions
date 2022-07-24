package isensehostility.interactions.entity.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.phys.Vec3;

public class SquidMoveToTargetGoal extends Goal {
    private final Squid squid;

    public SquidMoveToTargetGoal(Squid p_30004_) {
        this.squid = p_30004_;
    }

    public boolean canUse() {
        return true;
    }

    public void tick() {
        if (this.squid.getRandom().nextInt(reducedTickDelay(50)) == 0 || !this.squid.isInWater() || !this.squid.hasMovementVector()) {
            LivingEntity target = squid.getTarget();
            if (target != null) {
                Vec3 difference = new Vec3(
                        target.getX() - squid.getX(),
                        target.getY() - squid.getY(),
                        target.getZ() - squid.getZ()
                );

                double scale = (difference.length() - 5.0D);
                difference.normalize();

                if (scale < 3) {
                    difference.scale(3);
                } else {
                    difference.scale(scale);
                }

                this.squid.setMovementVector((float) difference.x / 20, (float) difference.y / 20, (float) difference.z / 20);
            } else {
                this.squid.setMovementVector(0.0F, 0.0F, 0.0F);
                this.stop();
            }
        }
    }
}
