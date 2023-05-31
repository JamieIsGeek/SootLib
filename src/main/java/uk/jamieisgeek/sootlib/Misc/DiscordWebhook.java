package uk.jamieisgeek.sootlib.Misc;

import javax.net.ssl.HttpsURLConnection;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.List;
import java.util.*;

public class DiscordWebhook {
    private final String url;
    private String content;
    private String username;
    private String avatarUrl;
    private boolean tts;
    private List<EmbedObject> embeds = new ArrayList<>();

    /**
     * Constructs a new DiscordWebhook instance
     *
     * @param url The webhook URL obtained in Discord
     */
    public DiscordWebhook(String url) {
        this.url = url;
    }

    /**
     * @param content The message contents (up to 2000 characters)
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @param username The username to use for this message
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @param avatarUrl The avatar URL to use for this message
     */
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    /**
     * @param tts Whether or not this message should be read aloud
     */
    public void setTts(boolean tts) {
        this.tts = tts;
    }

    /**
     * @param embed The embeds to use for this message
     */
    public void addEmbed(EmbedObject embed) {
        this.embeds.add(embed);
    }

    /**
     * @throws IOException If the request was not successful
     */
    public void execute() throws IOException {
        if (this.content == null && this.embeds.isEmpty()) {
            throw new IllegalArgumentException("Set content or add at least one EmbedObject");
        }

        JSONObject json = new JSONObject();

        json.put("content", this.content);
        json.put("username", this.username);
        json.put("avatar_url", this.avatarUrl);
        json.put("tts", this.tts);

        if (!this.embeds.isEmpty()) {
            List<JSONObject> embedObjects = new ArrayList<>();

            for (EmbedObject embed : this.embeds) {
                JSONObject jsonEmbed = new JSONObject();

                jsonEmbed.put("title", embed.getTitle());
                jsonEmbed.put("description", embed.getDescription());
                jsonEmbed.put("url", embed.getUrl());

                if (embed.getColor() != null) {
                    Color color = embed.getColor();
                    int rgb = color.getRed();
                    rgb = (rgb << 8) + color.getGreen();
                    rgb = (rgb << 8) + color.getBlue();

                    jsonEmbed.put("color", rgb);
                }

                EmbedObject.Footer footer = embed.getFooter();
                EmbedObject.Image image = embed.getImage();
                EmbedObject.Thumbnail thumbnail = embed.getThumbnail();
                EmbedObject.Author author = embed.getAuthor();
                List<EmbedObject.Field> fields = embed.getFields();

                if (footer != null) {
                    JSONObject jsonFooter = new JSONObject();

                    jsonFooter.put("text", footer.getText());
                    jsonFooter.put("icon_url", footer.getIconUrl());
                    jsonEmbed.put("footer", jsonFooter);
                }

                if (image != null) {
                    JSONObject jsonImage = new JSONObject();

                    jsonImage.put("url", image.getUrl());
                    jsonEmbed.put("image", jsonImage);
                }

                if (thumbnail != null) {
                    JSONObject jsonThumbnail = new JSONObject();

                    jsonThumbnail.put("url", thumbnail.getUrl());
                    jsonEmbed.put("thumbnail", jsonThumbnail);
                }

                if (author != null) {
                    JSONObject jsonAuthor = new JSONObject();

                    jsonAuthor.put("name", author.getName());
                    jsonAuthor.put("url", author.getUrl());
                    jsonAuthor.put("icon_url", author.getIconUrl());
                    jsonEmbed.put("author", jsonAuthor);
                }

                List<JSONObject> jsonFields = new ArrayList<>();
                for (EmbedObject.Field field : fields) {
                    JSONObject jsonField = new JSONObject();

                    jsonField.put("name", field.getName());
                    jsonField.put("value", field.getValue());
                    jsonField.put("inline", field.isInline());

                    jsonFields.add(jsonField);
                }

                jsonEmbed.put("fields", jsonFields.toArray());
                embedObjects.add(jsonEmbed);
            }

            json.put("embeds", embedObjects.toArray());
        }

        URL url = new URL(this.url);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.addRequestProperty("Content-Type", "application/json");
        connection.addRequestProperty("User-Agent", "Java-DiscordWebhook-BY-Gelox_");
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");

        OutputStream stream = connection.getOutputStream();
        stream.write(json.toString().getBytes());
        stream.flush();
        stream.close();

        connection.getInputStream().close(); //I'm not sure why but it doesn't work without getting the InputStream
        connection.disconnect();
    }

    public static class EmbedObject {
        private String title;
        private String description;
        private String url;
        private Color color;

        private Footer footer;
        private Thumbnail thumbnail;
        private Image image;
        private Author author;
        private List<Field> fields = new ArrayList<>();

        /**
         * @return The title of the embed
         */
        public String getTitle() {
            return title;
        }

        /**
         * @return The description of the embed
         */
        public String getDescription() {
            return description;
        }

        /**
         * @return The URL of the embed
         */
        public String getUrl() {
            return url;
        }

        /**
         * @return The color of the embed
         */
        public Color getColor() {
            return color;
        }

        /**
         * @return The footer of the embed
         */
        public Footer getFooter() {
            return footer;
        }

        /**
         * @return The thumbnail of the embed
         */
        public Thumbnail getThumbnail() {
            return thumbnail;
        }

        /**
         * @return The image of the embed
         */
        public Image getImage() {
            return image;
        }

        /**
         * @return The author of the embed
         */
        public Author getAuthor() {
            return author;
        }

        /**
         * @return The fields of the embed
         */
        public List<Field> getFields() {
            return fields;
        }

        /**
         * @param title The title of the embed
         * @return The current EmbedObject for chaining convenience
         */
        public EmbedObject setTitle(String title) {
            this.title = title;
            return this;
        }

        /**
         * @param description The description of the embed
         * @return The current EmbedObject for chaining convenience
         */
        public EmbedObject setDescription(String description) {
            this.description = description;
            return this;
        }

        /**
         * @param url The URL of the embed
         * @return The current EmbedObject for chaining convenience
         */
        public EmbedObject setUrl(String url) {
            this.url = url;
            return this;
        }

        /**
         * @param color The color of the embed
         * @return The current EmbedObject for chaining convenience
         */
        public EmbedObject setColor(Color color) {
            this.color = color;
            return this;
        }

        /**
         * @param text The text of the footer
         * @param icon The icon of the footer
         * @return The current EmbedObject for chaining convenience
         */
        public EmbedObject setFooter(String text, String icon) {
            this.footer = new Footer(text, icon);
            return this;
        }

        /**
         * @param url The URL of the thumbnail
         * @return The current EmbedObject for chaining convenience
         */
        public EmbedObject setThumbnail(String url) {
            this.thumbnail = new Thumbnail(url);
            return this;
        }

        /**
         * @param url The URL of the image
         * @return The current EmbedObject for chaining convenience
         */
        public EmbedObject setImage(String url) {
            this.image = new Image(url);
            return this;
        }

        /**
         * @param name The name of the author
         * @param url The URL of the author
         * @param icon The icon of the author
         * @return The current EmbedObject for chaining convenience
         */
        public EmbedObject setAuthor(String name, String url, String icon) {
            this.author = new Author(name, url, icon);
            return this;
        }

        /**
         * @param name The name of the field
         * @param value The value of the field
         * @param inline Whether the field is inline or not
         * @return The current EmbedObject for chaining convenience
         */
        public EmbedObject addField(String name, String value, boolean inline) {
            this.fields.add(new Field(name, value, inline));
            return this;
        }

        private class Footer {
            private String text;
            private String iconUrl;

            /**
             * @param text The text of the footer
             * @param iconUrl The icon of the footer
             */
            private Footer(String text, String iconUrl) {
                this.text = text;
                this.iconUrl = iconUrl;
            }

            /**
             * @return The text of the footer
             */
            private String getText() {
                return text;
            }

            /**
             * @return The icon of the footer
             */
            private String getIconUrl() {
                return iconUrl;
            }
        }

        private class Thumbnail {
            private String url;

            /**
             * @param url The URL of the thumbnail
             */
            private Thumbnail(String url) {
                this.url = url;
            }

            /**
             * @return The URL of the thumbnail
             */
            private String getUrl() {
                return url;
            }
        }

        private class Image {
            private String url;

            /**
             * @param url The URL of the image
             */
            private Image(String url) {
                this.url = url;
            }

            /**
             * @return The URL of the image
             */
            private String getUrl() {
                return url;
            }
        }

        private class Author {
            private String name;
            private String url;
            private String iconUrl;

            /**
             * @param name The name of the author
             * @param url The URL of the author
             * @param iconUrl The icon of the author
             */
            private Author(String name, String url, String iconUrl) {
                this.name = name;
                this.url = url;
                this.iconUrl = iconUrl;
            }

            /**
             * @return The name of the author
             */
            private String getName() {
                return name;
            }

            /**
             * @return The URL of the author
             */
            private String getUrl() {
                return url;
            }

            /**
             * @return The icon of the author
             */
            private String getIconUrl() {
                return iconUrl;
            }
        }

        private class Field {
            private String name;
            private String value;
            private boolean inline;

            /**
             * @param name The name of the field
             * @param value The value of the field
             * @param inline Whether the field is inline or not
             */
            private Field(String name, String value, boolean inline) {
                this.name = name;
                this.value = value;
                this.inline = inline;
            }

            /**
             * @return The name of the field
             */
            private String getName() {
                return name;
            }

            /**
             * @return The value of the field
             */
            private String getValue() {
                return value;
            }

            /**
             * @return Whether the field is inline or not
             */
            private boolean isInline() {
                return inline;
            }
        }
    }

    private class JSONObject {

        private final HashMap<String, Object> map = new HashMap<>();

        /**
         * @param key The key of the value
         * @param value The value
         */
        void put(String key, Object value) {
            if (value != null) {
                map.put(key, value);
            }
        }

        /**
         * @return The value
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            Set<Map.Entry<String, Object>> entrySet = map.entrySet();
            builder.append("{");

            int i = 0;
            for (Map.Entry<String, Object> entry : entrySet) {
                Object val = entry.getValue();
                builder.append(quote(entry.getKey())).append(":");

                if (val instanceof String) {
                    builder.append(quote(String.valueOf(val)));
                } else if (val instanceof Integer) {
                    builder.append(Integer.valueOf(String.valueOf(val)));
                } else if (val instanceof Boolean) {
                    builder.append(val);
                } else if (val instanceof JSONObject) {
                    builder.append(val.toString());
                } else if (val.getClass().isArray()) {
                    builder.append("[");
                    int len = Array.getLength(val);
                    for (int j = 0; j < len; j++) {
                        builder.append(Array.get(val, j).toString()).append(j != len - 1 ? "," : "");
                    }
                    builder.append("]");
                }

                builder.append(++i == entrySet.size() ? "}" : ",");
            }

            return builder.toString();
        }

        /**
         * @param string The string to quote
         * @return The quoted string
         */
        private String quote(String string) {
            return "\"" + string + "\"";
        }
    }
}
