package net.mp3skater.getop.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class GetOPCommonConfigs {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Double> END_SCEPTRE_TP_LENGTH;
    public static final ForgeConfigSpec.ConfigValue<Double> END_SCEPTRE_AIR_DASH_SPEED;
    public static final ForgeConfigSpec.ConfigValue<Double> DEATH_SWORD_REACH_DISTANCE;
    public static final ForgeConfigSpec.ConfigValue<Double> HEROBRINE_SWORD_REACH_DISTANCE;
    public static final ForgeConfigSpec.ConfigValue<Double> ICE_SCYTHE_RADIUS;

    public static final ForgeConfigSpec.ConfigValue<Integer> PAINITE_ORE_VEIN_SIZE;

    static {
        BUILDER.push("Configs for the Get OP Mod");

        END_SCEPTRE_TP_LENGTH = BUILDER.comment("How long you can teleport when using the End Sceptre ability!")
                .defineInRange("Length", 5d, 0d, 15d);
        END_SCEPTRE_AIR_DASH_SPEED = BUILDER.comment("How much you fly with the air dash ability!")
                .defineInRange("Boost multiplier", 3d, 0d, 10d);
        DEATH_SWORD_REACH_DISTANCE = BUILDER.comment("How long the reach distance of death-ray ability!")
                .defineInRange("Reach Distance", 8d, 3d, 20d);
        ICE_SCYTHE_RADIUS = BUILDER.comment("How big the radius of freeze ability!")
                .defineInRange("Radius", 8d, 3d, 20d);
        HEROBRINE_SWORD_REACH_DISTANCE = BUILDER.comment("How long the reach distance of herobrine ability!")
                .defineInRange("Reach Distance", 20d, 5d, 50d);
        PAINITE_ORE_VEIN_SIZE = BUILDER.comment("How much Painite spawns in your world!")
                .defineInRange("Average veins per Chunk (1 is very rare!)", 4, 1, 15);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
