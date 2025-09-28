package fr.eletutour.commands;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class SimpleCommands {

    @ShellMethod(key = "hello", value = "Says hello")
    public String hello() {
        return "Hello, World!";
    }
}