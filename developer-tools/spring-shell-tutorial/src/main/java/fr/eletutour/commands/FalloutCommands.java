package fr.eletutour.commands;

import jakarta.annotation.PostConstruct;
import org.jline.terminal.Terminal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@ShellComponent("Fallout Terminal")
public class FalloutCommands {

    private final Terminal terminal;
    private final Map<String, String> files = new ConcurrentHashMap<>();

    // Game state
    private static final int HACK_ATTEMPTS = 4;
    private static final String[] PASSWORD_POOL = {"SYSTEM", "SECURE", "ACCESS", "VERIFY", "LOCKED", "ADMINS", "ONLINE", "SERVER"};
    private static final int PASSWORD_LENGTH = 6;
    private static final int CANDIDATE_COUNT = 5;

    private List<String> passwordCandidates;
    private String correctPassword;
    private int attemptsLeft;
    private boolean gameActive = false;

    @Autowired
    public FalloutCommands(@Lazy Terminal terminal) {
        this.terminal = terminal;
    }

    @PostConstruct
    public void setupFiles() {
        files.put("security_log_01.txt", "Log Entry 713\n\nUnauthorized access detected in Sector 3.\nSentries dispatched.\nConclusion: False alarm. Raccoon again.");
        files.put("personnel_notes.txt", "Note to self:\n\nRemember to change the admin password.\n'password123' is probably not secure enough.");
        files.put("reactor_status.log", "Coolant levels stable.\nEnergy output at 98%.\nScheduled maintenance next cycle.");
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

    @ShellMethod(key = "ls", value = "List available files.")
    public void listFiles() throws InterruptedException {
        PrintWriter writer = terminal.writer();
        String greenColor = "\u001B[32m";
        String resetColor = "\u001B[0m";

        for (String file : files.keySet()) {
            slowPrint(writer, greenColor + file + resetColor, 20);
            writer.println();
            TimeUnit.MILLISECONDS.sleep(100);
        }
    }

    @ShellMethod(key = "cat", value = "Display content of a file.")
    public void displayFile(@ShellOption(help = "The name of the file to display.") String filename) throws InterruptedException {
        PrintWriter writer = terminal.writer();
        String greenColor = "\u001B[32m";
        String resetColor = "\u001B[0m";

        String content = files.get(filename);

        if (content == null) {
            slowPrint(writer, greenColor + "ERROR: File not found: " + filename + resetColor, 50);
            writer.println();
        } else {
            slowPrint(writer, greenColor + content + resetColor, 40);
            writer.println();
        }
    }

    @ShellMethod(key = "system-status", value = "Display the system status.")
    public void systemStatus() throws InterruptedException {
        PrintWriter writer = terminal.writer();
        String greenColor = "\u001B[32m";
        String resetColor = "\u001B[0m";

        String status = """
                RIT.OS (V2.1) - SYSTEM STATUS
                ---------------------------------
                CORE_TEMP: 85°C (STABLE)
                REACTOR_OUTPUT: 102% (NOMINAL)
                MEMORY_USAGE: [██████░░░░░░░░░] 40%
                NETWORK: UPLINK ACTIVE - ROBCO-NET
                USERS_CONNECTED: 1
                """
;

        slowPrint(writer, greenColor + status + resetColor, 20);
        writer.println();
    }

    @ShellMethod(key = "hack", value = "Initiate a hacking sequence.")
    public void hack() throws InterruptedException {
        if (gameActive) {
            PrintWriter writer = terminal.writer();
            slowPrint(writer, "\u001B[32m" + "Hacking sequence already in progress." + "\u001B[0m", 40);
            writer.println();
            return;
        }
        startGame();
    }

    @ShellMethod(key = "try", value = "Attempt to guess the password.")
    public void tryPassword(@ShellOption(help = "The password to attempt.") String password) throws InterruptedException {
        if (!gameActive) {
            PrintWriter writer = terminal.writer();
            slowPrint(writer, "\u001B[32m" + "No hacking sequence in progress. Use 'hack' to start." + "\u001B[0m", 40);
            writer.println();
            return;
        }
        processGuess(password.toUpperCase());
    }

    private void startGame() throws InterruptedException {
        gameActive = true;
        attemptsLeft = HACK_ATTEMPTS;

        List<String> pool = new ArrayList<>(Arrays.asList(PASSWORD_POOL));
        Collections.shuffle(pool);
        passwordCandidates = pool.subList(0, CANDIDATE_COUNT);
        correctPassword = passwordCandidates.get(new Random().nextInt(passwordCandidates.size()));

        displayHackScreen();
    }

    private void processGuess(String guess) throws InterruptedException {
        PrintWriter writer = terminal.writer();
        String greenColor = "\u001B[32m";
        String resetColor = "\u001B[0m";

        if (guess.length() != PASSWORD_LENGTH || !passwordCandidates.contains(guess)) {
            slowPrint(writer, greenColor + "> Error: Invalid entry." + resetColor, 40);
            writer.println();
            return;
        }

        attemptsLeft--;

        if (guess.equals(correctPassword)) {
            slowPrint(writer, greenColor + "\n> Access Granted. \n> Welcome, Administrator." + resetColor, 50);
            writer.println();
            gameActive = false;
        } else {
            int likeness = calculateLikeness(guess);
            slowPrint(writer, greenColor + "> Entry denied. Likeness=" + likeness + "/" + PASSWORD_LENGTH + resetColor, 40);
            writer.println();

            if (attemptsLeft <= 0) {
                slowPrint(writer, greenColor + "\n> TERMINAL LOCKED. \n> PLEASE CONTACT AN ADMINISTRATOR." + resetColor, 50);
                writer.println();
                gameActive = false;
            }
        }
    }

    private void displayHackScreen() throws InterruptedException {
        PrintWriter writer = terminal.writer();
        String greenColor = "\u001B[32m";
        String resetColor = "\u001B[0m";
        Random random = new Random();
        String junk = "!@#$%^&*()[]{}<>?-_=+;:,./";

        clearScreen();
        slowPrint(writer, greenColor + "ROBCO INDUSTRIES (TM) TERMLINK - ATTEMPTS REMAINING: " + attemptsLeft + resetColor, 30);
        writer.println();
        writer.println();

        List<String> displayWords = new ArrayList<>(passwordCandidates);
        Collections.shuffle(displayWords);

        for (int i = 0; i < 14; i++) { // 14 lines of junk
            StringBuilder line = new StringBuilder();
            for (int j = 0; j < 34; j++) { // 34 chars per line
                line.append(junk.charAt(random.nextInt(junk.length())));
            }

            if (i < displayWords.size() * 2 && i % 2 != 0) {
                int index = i / 2;
                if (index < displayWords.size()) {
                    String password = displayWords.get(index);
                    int start = random.nextInt(line.length() - password.length() - 2) + 1; // Avoid edges
                    line.replace(start, start + password.length(), password);
                }
            }
            slowPrint(writer, greenColor + line.toString() + resetColor, 2);
            writer.println();
        }
        writer.println();
        slowPrint(writer, greenColor + "> Use 'try <password>' to make a guess." + resetColor, 40);
        writer.println();
    }

    private int calculateLikeness(String guess) {
        int likeness = 0;
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            if (guess.charAt(i) == correctPassword.charAt(i)) {
                likeness++;
            }
        }
        return likeness;
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
        int total = 25;
        writer.println();
        writer.print(color);

        for (int i = 0; i <= total; i++) {
            writer.print("\r[");
            for (int j = 0; j < total; j++) {
                if (j < i) {
                    writer.print("█");
                } else {
                    writer.print("░");
                }
            }
            writer.print("] " + (i * 100 / total) + "%");
            writer.flush();
            TimeUnit.MILLISECONDS.sleep(80);
        }
        writer.println(resetColor);
        writer.println();
    }

    private void clearScreen() {
        terminal.writer().print("\033[H\033[2J");
        terminal.writer().flush();
    }
}
