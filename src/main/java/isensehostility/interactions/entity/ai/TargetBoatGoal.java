package isensehostility.interactions.entity.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;

public class TargetBoatGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
    public TargetBoatGoal(Mob attacker, Class<T> target, boolean hasToSee) {
        super(attacker, target, hasToSee);
    }

    @Override
    protected void findTarget() {
        this.target = this.mob.level.getNearestEntity(this.mob.level.getEntitiesOfClass(this.targetType, this.getTargetSearchArea(this.getFollowDistance()),
                entity -> entity instanceof Player && entity.getVehicle() instanceof Boat),
                this.targetConditions, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
    }
}
