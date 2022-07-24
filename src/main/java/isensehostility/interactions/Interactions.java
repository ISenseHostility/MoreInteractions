package isensehostility.interactions;

import isensehostility.interactions.config.InteractionsConfig;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.ForgeRegistries;

@Mod("more_interactions")
public class Interactions {

    public static final String MODID = "more_interactions";

    public Interactions() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, InteractionsConfig.config);
        InteractionsConfig.loadConfig(InteractionsConfig.config, FMLPaths.CONFIGDIR.get().resolve(MODID + "-config.toml").toString());
    }

    public static class Tags {
        public static TagKey<Item> GIVES_FOOD_POISONING = createItemTag("raw_meats");
        public static TagKey<Item> IS_SWORD = createItemTag("is_sword");
        public static TagKey<Item> IS_SHEARS = createItemTag("is_shears");
        public static TagKey<EntityType<?>> IS_CHICKEN = createEntityTag("is_chicken");
        public static TagKey<EntityType<?>> IS_SHEEP = createEntityTag("is_sheep");

        public static TagKey<Item> createItemTag(String name) {
            return ItemTags.create(location(name));
        }

        public static TagKey<EntityType<?>> createEntityTag(String name) {
            return TagKey.create(Registry.ENTITY_TYPE_REGISTRY, location(name));
        }

        public static boolean isEntityInTag(Entity entity, TagKey<EntityType<?>> tag) {
            return ForgeRegistries.ENTITIES.getHolder(entity.getType()).get().is(tag);
        }

        private static ResourceLocation location(String name) {
            return new ResourceLocation(MODID, name);
        }
    }
}
