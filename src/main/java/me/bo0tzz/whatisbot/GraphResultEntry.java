package me.bo0tzz.whatisbot;

import java.util.Map;

/**
 * Created by boet on 7-2-2016.
 */
public class GraphResultEntry {
    private Map<String, Object> result;

    private String name;
    private String description;
    private Map<String, String> detailedDescription;
    private Map<String, String> image;

    public String getName() {
        if (name == null) {
            name = (String) result.get("name");
        }
        return name;
    }

    public String getDescription() {
        if (description == null) {
            description = (String) result.get("description");
        }
        return description;
    }

    public String getDetailedDescription() {
        if (detailedDescription == null) {
            Object map = result.get("detailedDescription");
            if (map instanceof Map) {
                detailedDescription = (Map) result.get("detailedDescription");
            } else {
                return null;
            }
        }
        return detailedDescription.get("articleBody");
    }

    public String getImage() {
        if (image == null) {
            Object map = result.get("image");
            if (map instanceof Map) {
                image = (Map) result.get("image");
            } else {
                return null;
            }
        }
        return image.get("contentUrl");
    }

}
