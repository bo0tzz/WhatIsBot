package me.bo0tzz.whatisbot;

import org.json.JSONArray;
import org.json.JSONObject;
import pro.zackpollard.telegrambot.api.chat.inline.send.InlineQueryResponse;
import pro.zackpollard.telegrambot.api.chat.inline.send.content.InputTextMessageContent;
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
        JSONArray json = googleHook.query(query);

        if (json == null) {
            return;
        }

        for (int i = 0; i < 3; i++) {
            JSONObject result = json.getJSONObject(i).getJSONObject("result");
            String title = result.getString("name");
            String description = result.getJSONObject("detailedDescription").getString("articleBody");
            URL image = null;
            if (result.has("image")) {
                try {
                    image = new URL(result.getJSONObject("image").getString("contentUrl"));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }

            results.add(InlineQueryResultArticle.builder()
                    .title(title)
                    .inputMessageContent(InputTextMessageContent.builder()
                            .messageText(description)
                            .build())
                    .description(description)
                    .thumbUrl(image)
                    .build()
            );
        }

        event.getQuery().answer(bot.bot, InlineQueryResponse.builder().results(results).build());
    }
}
