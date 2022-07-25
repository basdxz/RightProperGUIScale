package com.github.basdxz.rightproperguiscale;

import lombok.*;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.ClientCommandHandler;

import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
public class GUIScaleCommand extends CommandBase {
    protected static final GUIScaleCommand INSTANCE = new GUIScaleCommand();
    protected static final String GUI_SCALE_COMMAND_NAME = "guiscale";

    public String getCommandName() {
        return GUI_SCALE_COMMAND_NAME;
    }

    public String getCommandUsage(ICommandSender sender) {
        return "/" + GUI_SCALE_COMMAND_NAME + " <guiscale float>";
    }

    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    // Check args length
    // Turn args into double
    //
    public void processCommand(ICommandSender sender, String[] args) {
        if (argsNonNull(sender, args))
            return;

        if (args.length > 0) {
            try {
                GUIJiggler.setGUIScale(Float.parseFloat(args[0]));
                GUIJiggler.saveGUIScale();
            } catch (NullPointerException | NumberFormatException e) {
                sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Failed to parse a float."));
            }
        }
        sender.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Gui Scale: " + EnumChatFormatting.WHITE + GUIJiggler.guiScaleAsFloat()));
    }

    protected boolean argsNonNull(ICommandSender sender, String[] args) {
        return sender == null || args == null;
    }

    public static void register() {
        ClientCommandHandler.instance.registerCommand(GUIScaleCommand.INSTANCE);
    }
}