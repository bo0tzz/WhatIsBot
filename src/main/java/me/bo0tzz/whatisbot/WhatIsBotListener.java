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
        GraphResult result = googleHook.query(query);
        if (result == null) return;
        List<GraphResultEntry> graphResultEntries = result.getGraphResultEntries();

        for (GraphResultEntry entry : graphResultEntries) {
            String name = entry.getName();
            String detailedDescription = entry.getDetailedDescription();
            String description = entry.getDescription();
            URL url;
            try {
                url = new URL(entry.getImage());
            } catch (MalformedURLException e) {
                url = null;
            }

            if (name == null || detailedDescription == null) continue;

            results.add(InlineQueryResultArticle.builder()
                    .title(name)
                    .messageText(detailedDescription)
                    .description(description)
                    .thumbUrl(url)
                    .build()
            );
        }

        event.getQuery().answer(bot.bot, InlineQueryResponse.builder().results(results).build());
    }
}
