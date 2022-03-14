package com.github.EthanCosta.odysseyus.game;

import fr.flowarg.flowupdater.versions.ForgeVersionBuilder;
import fr.flowarg.flowupdater.versions.VersionType;
import fr.flowarg.openlauncherlib.NewForgeVersionDiscriminator;
import fr.theshark34.openlauncherlib.minecraft.GameType;


public class MinecraftInfos {

    public static  final String SERVER_NAME = "Odysseyus";

    public static final String GAME_VERSION = "1.12.2";
    public static final VersionType VERSION_TYPE = VersionType.FORGE;
    public static final ForgeVersionBuilder.ForgeVersionType FORGE_VERSION_TYPE = ForgeVersionBuilder.ForgeVersionType.NEW;
    public static final String FORGE_VERSION = "1.12.2-14.23.5.2860";
    public static final String OPTIFINE_VERSION = "1.12.2_HD_U_G5";
    public static final String MODS_LIST_URL = "https://odysseyus.fr/mods.json";
    public static final String FILE_LIST_URL = "https://odysseyus.fr/files.json";
    public static final GameType OLL_GAME_TYPE = GameType.V1_8_HIGHER;

    public static final NewForgeVersionDiscriminator OLL_FORGE_DISCRIMINATOR = new NewForgeVersionDiscriminator(
            "14.23.5.2855",
            MinecraftInfos.GAME_VERSION,
            "20210115.111550"
    );

}
