package me.bo0tzz.whatisbot;

import java.util.Map;

/**
 * Created by boet on 7-2-2016.
 */
public class GraphResultEntry {
    private String name;
    private String description;
    private Map<String, String> detailedDescription;
    private Map<String, String> image;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDetailedDescription() {
        return detailedDescription.get("articleBody");
    }

    public String getImage() {
        return image.get("contentUrl");
    }

}
