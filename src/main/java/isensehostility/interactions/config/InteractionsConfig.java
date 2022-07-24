package isensehostility.interactions.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import isensehostility.interactions.enums.Severity;
import net.minecraftforge.common.ForgeConfigSpec;

import java.io.File;

public class InteractionsConfig {

    public static ForgeConfigSpec.BooleanValue shearWithSwordEnabled;
    public static ForgeConfigSpec.DoubleValue shearWithSwordDamage;

    public static ForgeConfigSpec.BooleanValue pluckFeathersEnabled;
    public static ForgeConfigSpec.DoubleValue pluckFeathersDamage;

    public static ForgeConfigSpec.BooleanValue foodPoisoningEnabled;
    public static ForgeConfigSpec.DoubleValue foodPoisoningTime;
    public static ForgeConfigSpec.ConfigValue<Severity> foodPoisoningSeverity;
    public static ForgeConfigSpec.IntValue foodPoisoningChance;

    public static ForgeConfigSpec.BooleanValue squidTargetTurtleEnabled;
    public static ForgeConfigSpec.BooleanValue squidTargetBoatEnabled;
    public static ForgeConfigSpec.DoubleValue squidDamage;

    public static ForgeConfigSpec.BooleanValue touchFireEnabled;
    public static ForgeConfigSpec.DoubleValue touchFireTime;

    public static ForgeConfigSpec.BooleanValue touchCactusEnabled;
    public static ForgeConfigSpec.DoubleValue touchCactusDamage;

    public static ForgeConfigSpec.BooleanValue brokenToolDropsEnabled;

    private static final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec config;

    static {
        InteractionsConfig.init(builder);

        config = builder.build();
    }

    public static void loadConfig(ForgeConfigSpec config, String path) {
        final CommentedFileConfig file = CommentedFileConfig.builder(new File(path)).sync().autosave().writingMode(WritingMode.REPLACE).build();
        file.load();
        config.setConfig(file);
    }

    public static void init(ForgeConfigSpec.Builder config) {
        shearWithSwordEnabled = config
                .comment("Shear sheep by right clicking with a sword.")
                .define("enabled.shearWithSword", true);
        shearWithSwordDamage = config
                .comment("Determines how much damage you do to the sheep by shearing it, in half hearts.")
                .defineInRange("damage.shearWithSword", 1.0D, 0.0D, Double.MAX_VALUE);

        pluckFeathersEnabled = config
                .comment("Pluck a feather from a chicken by right clicking it with shears.")
                .define("enabled.pluckFeathers", true);
        pluckFeathersDamage = config
                .comment("Determines how much damage you do to the chicken by plucking a feather, in half hearts.")
                .defineInRange("damage.pluckFeathers", 1.0D, 0.0D, Double.MAX_VALUE);

        foodPoisoningEnabled = config
                .comment("Determines if you can get negative effects from eating raw meat.")
                .define("enabled.foodPoisoning", true);
        foodPoisoningTime = config
                .comment("Determines how long negative effects from food poisoning last, in seconds.")
                .defineInRange("time.foodPoisoning", 20.0D, 0.0D, Double.MAX_VALUE);
        foodPoisoningSeverity = config
                .comment("Determines how bad negative effects from food poisoning are, AUTO is based on difficulty.")
                .defineEnum("misc.foodPoisoningSeverity", Severity.AUTO);
        foodPoisoningChance = config
                .comment("Determines how often you contract food poisoning, as a percentage.")
                .defineInRange("misc.foodPoisoningChance", 50, 0, 100);

        squidTargetTurtleEnabled = config
                .comment("Squids target and attack baby turtles.")
                .define("enabled.squidTargetTurtle", true);
        squidTargetBoatEnabled = config
                .comment("Pluck a feather from a chicken by right clicking it with shears.")
                .define("enabled.squidTargetBoat", true);
        squidDamage = config
                .comment("Determines how much damage a squid does, in half hearts.")
                .defineInRange("damage.squid", 3.0D, 0.0D, Double.MAX_VALUE);

        touchFireEnabled = config
                .comment("Putting out a fire with you bare hands sets you on fire.")
                .define("enabled.touchFire", true);
        touchFireTime = config
                .comment("Determines how long the fire lasts after you touched it, in seconds.")
                .defineInRange("time.touchFire", 4.0D, 0.0D, Double.MAX_VALUE);

        touchCactusEnabled = config
                .comment("Hitting a cactus with bare hands hurts you.")
                .define("enabled.touchCactus", true);
        touchCactusDamage = config
                .comment("Determines how much damage you take by hitting a cactus, in half hearts.")
                .defineInRange("damage.touchCactus", 2.0D, 0.0D, Double.MAX_VALUE);

        brokenToolDropsEnabled = config
                .comment("When a tool is broken it will drop a relevant crafting material.")
                .define("enabled.brokenToolDrops", true);

    }
}
