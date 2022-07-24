package isensehostility.interactions.entity.ai;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Squid;

public class SquidRandomMoveGoal extends Goal {
    private final Squid squid;

    public SquidRandomMoveGoal(Squid p_30004_) {
        this.squid = p_30004_;
    }

    public boolean canUse() {
        return true;
    }

    public void tick() {
        int i = this.squid.getNoActionTime();
        if (i > 100) {
            this.squid.setMovementVector(0.0F, 0.0F, 0.0F);
        } else if (this.squid.getRandom().nextInt(reducedTickDelay(50)) == 0 || !this.squid.isInWater() || !this.squid.hasMovementVector()) {
            float f = this.squid.getRandom().nextFloat() * ((float)Math.PI * 2F);
            float f1 = Mth.cos(f) * 0.2F;
            float f2 = -0.1F + this.squid.getRandom().nextFloat() * 0.2F;
            float f3 = Mth.sin(f) * 0.2F;
            this.squid.setMovementVector(f1, f2, f3);
        }

    }
}
