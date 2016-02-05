package me.bo0tzz.whatisbot;

import pro.zackpollard.telegrambot.api.TelegramBot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Created by boet on 5-2-2016.
 */
public class WhatIsBot {
    public final TelegramBot bot;

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("API key not specified!");
            System.exit(1);
        }
        new WhatIsBot(args);
    }

    public WhatIsBot(String[] args) {
        System.out.println("Initialising bot");
        bot = TelegramBot.login(args[0]);
        if (bot == null) {
            System.out.println("Failed to login! Faulty API key?");
            System.exit(1);
        }
        System.out.println("Registering events");
        bot.getEventsManager().register(new WhatIsBotListener(this));
        bot.startUpdates(false);
        System.out.println("Bot initialised!");
    }

    public String[] getKeys() {
        try {
            return Files.lines(new File("key").toPath()).toArray(String[]::new);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
