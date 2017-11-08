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
        String key = System.getenv("BOT_KEY");
        if (key == null || key.equals("")) {
            if (args.length < 1) {
                System.out.println("API key not specified!");
                System.exit(1);
            }
            key = args[0];
        }
        new WhatIsBot(key);
    }

    public WhatIsBot(String key) {
        System.out.println("Initialising bot");
        bot = TelegramBot.login(key);
        if (bot == null) {
            System.out.println("Failed to login! Faulty API key?");
            System.exit(1);
        }
        System.out.println("Registering events");
        bot.getEventsManager().register(new WhatIsBotListener(this));
        bot.startUpdates(false);
        System.out.println("Bot initialised!");
    }

    public String getKeys() {
        String key = System.getenv("GOOGLE_KEY");
        if (key == null || key.equals("")) {
            try {
                key = Files.lines(new File("key").toPath()).toString();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return key;
    }
}
