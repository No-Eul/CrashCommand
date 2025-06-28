package dev.noeul.fabricmod.crashcommand;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.netty.util.internal.shaded.org.jctools.util.UnsafeAccess;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.client.util.GlfwUtil;
import net.minecraft.command.CommandSource;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.WinNativeModuleUtil;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;

import java.util.Locale;

public class CrashCommand implements ModInitializer, ClientModInitializer {
	@Override
	public void onInitialize() {
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
				CrashCommand.registerCrashCommand(dispatcher, EnvType.SERVER)
		);
	}

	@Override
	public void onInitializeClient() {
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
				CrashCommand.registerCrashCommand(dispatcher, EnvType.CLIENT)
		);
	}

	private static <S extends CommandSource> void registerCrashCommand(CommandDispatcher<S> dispatcher, EnvType envType) {
		dispatcher.register(LiteralArgumentBuilder.<S>literal("crash")
				.requires(source -> envType == EnvType.CLIENT || ((ServerCommandSource) source).hasPermissionLevel(4))
				.executes(ctx -> CrashCommand.makeGameCrash(envType))
				.then(LiteralArgumentBuilder.<S>literal(envType.name().toLowerCase(Locale.ROOT))
						.executes(ctx -> CrashCommand.makeGameCrash(envType))
						.then(LiteralArgumentBuilder.<S>literal("game").executes(ctx -> CrashCommand.makeGameCrash(envType)))
						.then(LiteralArgumentBuilder.<S>literal("jvm").executes(ctx -> CrashCommand.makeJvmCrash(envType)))
				)
		);
	}

	public static int makeGameCrash(EnvType envType) {
		String string = "Manually triggered debug crash";
		CrashReport crashReport = new CrashReport(string, new Throwable(string));
		CrashReportSection crashReportSection = crashReport.addElement("Manual crash details");
		if (envType == EnvType.CLIENT) WinNativeModuleUtil.addDetailTo(crashReportSection);
		throw new Error(new CrashException(crashReport));
	}

	public static int makeJvmCrash(EnvType envType) {
		switch (envType) {
			case SERVER -> UnsafeAccess.UNSAFE.putAddress(0, 0);
			case CLIENT -> GlfwUtil.makeJvmCrash();
		}
		throw new AssertionError("How did we get here? This should never be reached.");
	}
}
