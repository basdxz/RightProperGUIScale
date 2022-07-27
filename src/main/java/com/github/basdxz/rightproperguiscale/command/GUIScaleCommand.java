package com.github.basdxz.rightproperguiscale.command;

import com.github.basdxz.rightproperguiscale.GUIScale;
import lombok.*;
import net.minecraft.command.*;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.ClientCommandHandler;

import java.util.Optional;

import static lombok.AccessLevel.PROTECTED;

/**
 * The client-side command for changing GUI Scale.
 * <p>
 * Example usage: {@code /guiscale 5} will set the GUI Scale to 5.
 */
@NoArgsConstructor(access = PROTECTED)
public class GUIScaleCommand extends CommandBase {
    protected static final GUIScaleCommand INSTANCE = new GUIScaleCommand();
    protected static final String GUI_SCALE_COMMAND_NAME = "guiscale";
    protected static final String GUI_SCALE_COMMAND_USE_UNLOCALIZED = "commands.guiscale.usage";
    protected static final String GUI_SCALE_COMMAND_SUCCESS_UNLOCALIZED = "commands.guiscale.success";
    protected static final String GUI_SCALE_COMMAND_NUMBER_INVALID = "commands.guiscale.number_invalid";
    protected static final int GUI_SCALE_ARGUMENT_INDEX = 0;
    protected static final int GUI_SCALE_COMMAND_ARGUMENT_COUNT = 1;

    /**
     * Registers the command into {@link ClientCommandHandler#instance}.
     */
    public static void register() {
        ClientCommandHandler.instance.registerCommand(GUIScaleCommand.INSTANCE);
    }

    /**
     * Provides the command name without a slash.
     * <p>
     * Invoked by the command handler when parsing command and for tab-completion.
     *
     * @return command name
     */
    public String getCommandName() {
        return GUI_SCALE_COMMAND_NAME;
    }

    /**
     * Provides the command name without a slash.
     *
     * @return unlocalized command usage string
     */
    public String getCommandUsage(ICommandSender sender) {
        return GUI_SCALE_COMMAND_USE_UNLOCALIZED;
    }

    /**
     * Check if the sender can run this command.
     * <p>
     * Always returns true since the command is client-side.
     *
     * @param sender command sender
     * @return can sender use command
     */
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    /**
     * Processes the command with provided arguments.
     * <p>
     * If the provided arguments are valid, updates the GUI scale.
     *
     * @param sender command sender
     * @param args   command arguments
     */
    public void processCommand(ICommandSender sender, String[] args) {
        if (argsNull(sender, args) || argsWrongCount(args))
            throw newWrongUsageException();
        val guiScale = parseGUIScale(args);
        if (!guiScale.isPresent())
            throw newNumberInvalidGUIScaleValueException(args);
        updateGUIScale(guiScale.get());
        sendSuccessMessage(sender);
    }

    /**
     * Checks if any of the provided arguments are null.
     *
     * @param sender command sender
     * @param args   command arguments
     * @return are any arguments null
     */
    protected boolean argsNull(ICommandSender sender, String[] args) {
        return sender == null || args == null;
    }

    /**
     * Checks the number of provided arguments.
     *
     * @param args command arguments
     * @return is the number of arguments unexpected
     */
    protected boolean argsWrongCount(@NonNull String[] args) {
        return args.length != GUI_SCALE_COMMAND_ARGUMENT_COUNT;
    }

    /**
     * Provides a new wrong usage exception.
     *
     * @return new wrong usage exception
     */
    protected CommandException newWrongUsageException() {
        return new WrongUsageException(GUI_SCALE_COMMAND_USE_UNLOCALIZED);
    }

    /**
     * Parses the GUI Scale argument at index {@value GUIScaleCommand#GUI_SCALE_ARGUMENT_INDEX},
     * providing the value on success or an empty optional on failure.
     *
     * @param args command arguments
     * @return optional containing the result of parsing
     */
    protected Optional<Float> parseGUIScale(@NonNull String[] args) {
        try {
            return Optional.of(Float.parseFloat(args[GUI_SCALE_ARGUMENT_INDEX]));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Provides a new number invalid exception for an invalid GUI Scale value.
     *
     * @param args command arguments
     * @return new number invalid exception
     */
    protected CommandException newNumberInvalidGUIScaleValueException(@NonNull String[] args) {
        return new NumberInvalidException(GUI_SCALE_COMMAND_NUMBER_INVALID, args[GUI_SCALE_ARGUMENT_INDEX]);
    }

    /**
     * Updates the {@link GUIScale} with a new GUI Scale value.
     *
     * @param guiScale new GUI Scale value
     */
    protected void updateGUIScale(float guiScale) {
        GUIScale.set(guiScale);
        GUIScale.save();
    }

    /**
     * Send the sender a success message.
     *
     * @param sender command sender
     */
    protected void sendSuccessMessage(@NonNull ICommandSender sender) {
        sender.addChatMessage(newSuccessMessage());
    }

    /**
     * Provides a new success message with the updated GUI Scale value.
     *
     * @return new success message
     */
    protected IChatComponent newSuccessMessage() {
        return new ChatComponentTranslation(GUI_SCALE_COMMAND_SUCCESS_UNLOCALIZED, GUIScale.value());
    }
}