package dk.jrpe.monitor.service.command;

/**
 *
 * @author Jörgen Persson
 */
public abstract class Command {
    private String command;

    /**
     * Execute to command using data in supplied command handler.
     * @param cmdHandler
     */
    public abstract void execute(CommandHandler cmdHandler);

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
