package me.bo0tzz.whatisbot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import pro.zackpollard.telegrambot.api.chat.inline.send.InlineQueryResponse;
import pro.zackpollard.telegrambot.api.chat.inline.send.results.InlineQueryResult;
import pro.zackpollard.telegrambot.api.chat.inline.send.results.InlineQueryResultArticle;
import pro.zackpollard.telegrambot.api.event.Listener;
import pro.zackpollard.telegrambot.api.event.chat.inline.InlineQueryReceivedEvent;
import pro.zackpollard.telegrambot.api.event.chat.message.TextMessageReceivedEvent;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by boet on 5-2-2016.
 */
public class WhatIsBotListener implements Listener {
    private final WhatIsBot bot;
    private final GoogleHook googleHook;

    public WhatIsBotListener(WhatIsBot bot) {
        this.bot = bot;
        googleHook = new GoogleHook(bot);
    }

    @Override
    public void onInlineQueryReceived(InlineQueryReceivedEvent event) {
        List<InlineQueryResult> results = new ArrayList<>(3);

        String query = event.getQuery().getQuery();
        System.out.println(query);
        List<GraphResultEntry> graphResultEntries = googleHook.query(query).getGraphResultEntries();
        if (graphResultEntries.isEmpty()) {
            System.out.println("Result list is empty");
            return;
        }

        int limit = graphResultEntries.size();
        System.out.println("Result list size: " + limit);

        for (GraphResultEntry entry : graphResultEntries) {
            System.out.println("Entry: " + entry.getName() + " - " + entry.getDescription() + " - " + entry.getImage());

            try {
                results.add(InlineQueryResultArticle.builder()
                        .title(entry.getName())
                        .messageText(entry.getDetailedDescription())
                        .description(entry.getDescription())
                        .thumbUrl(new URL(entry.getImage()))
                        .build()
                );
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        event.getQuery().answer(bot.bot, InlineQueryResponse.builder().results(results).build());
    }
}
