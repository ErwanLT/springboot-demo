package fr.eletutour.commands;

import org.jline.terminal.Terminal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

@ShellComponent("Fallout Terminal")
public class FalloutCommands {

    private final Terminal terminal;

    // We use lazy wiring to avoid a circular dependency
    @Autowired
    public FalloutCommands(@Lazy Terminal terminal) {
        this.terminal = terminal;
    }

    @ShellMethod(key = "fallout-terminal", value = "Emulates a Fallout-style terminal boot sequence.")
    public void falloutTerminal() throws InterruptedException {
        PrintWriter writer = terminal.writer();
        String greenColor = "\u001B[32m";
        String resetColor = "\u001B[0m";

        clearScreen();
        displayLoadingBar(writer, greenColor);

        slowPrint(writer, greenColor + "Welcome to ROBCO Industries (TM) Termlink" + resetColor, 50);
        writer.println();
        slowPrint(writer, greenColor + "Protocol: VP-101" + resetColor, 50);
        writer.println();
        writer.println();

        slowPrint(writer, greenColor + "LOADING OS..." + resetColor, 100);
        writer.println();
        TimeUnit.SECONDS.sleep(1);
        slowPrint(writer, greenColor + "RIT.OS (V2.1) LOADED" + resetColor, 50);
        writer.println();
        writer.println();
        slowPrint(writer, greenColor + "Type 'help' for a list of commands." + resetColor, 50);
        writer.println();
    }

    private void slowPrint(PrintWriter writer, String text, long delay) throws InterruptedException {
        for (char ch : text.toCharArray()) {
            writer.print(ch);
            writer.flush();
            try {
                TimeUnit.MILLISECONDS.sleep(delay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw e;
            }
        }
    }

    private void displayLoadingBar(PrintWriter writer, String color) throws InterruptedException {
        String resetColor = "\u001B[0m";
        int total = 25; // The width of the loading bar
        writer.println(); // New line before the bar
        writer.print(color);

        for (int i = 0; i <= total; i++) {
            writer.print("\r[");
            for (int j = 0; j < total; j++) {
                if (j < i) {
                    writer.print("█");
                } else {
                    writer.print("░"); // Using a light shade for the empty part
                }
            }
            writer.print("] " + (i * 100 / total) + "%");
            writer.flush();
            TimeUnit.MILLISECONDS.sleep(80);
        }
        writer.println(resetColor); // Reset color and move to next line
        writer.println();
    }

    private void clearScreen() {
        terminal.writer().print("\033[H\033[2J");
        terminal.writer().flush();
    }
}
