package org.example.emoji;

import java.util.*;

public class Emoji {
    private static final Map<String, String[]> EMOJI_CATEGORIES = new HashMap<>();
    private static final Map<String, String> emojiMap = new HashMap<>();

    static {
        EMOJI_CATEGORIES.put("Smileys & Emotion", new String[]{
                "😀", "😁", "😂", "🤣", "😊", "😇",
                "😍", "🥰", "😘", "😜", "😎", "😭",
                "😡", "😱", "🤔", "🤗", "🤩", "😅",
                "😓", "😢", "🥺", "😤", "😈", "💀"
        });

        EMOJI_CATEGORIES.put("People & Body", new String[]{
                "👍", "👎", "👏", "🙏", "🤝", "💪",
                "🙌", "🤲", "🤔", "🤗", "🧍", "🧎",
                "💃", "🕺", "🙅", "🙆", "🙋", "🤷",
                "🤦", "🖐️", "✌️", "🤘", "👌", "✋"
        });

        EMOJI_CATEGORIES.put("Animals & Nature", new String[]{
                "🐶", "🐱", "🦁", "🐭", "🐼", "🐦",
                "🐸", "🦄", "🐷", "🐮", "🐻", "🐤",
                "🐍", "🦧", "🐢", "🐬", "🐳", "🦋",
                "🌲", "🌴", "🌵", "🌻", "🌹", "🌈"
        });

        EMOJI_CATEGORIES.put("Food & Drink", new String[]{
                "🍎", "🍔", "☕", "🍩", "🍕", "🍇",
                "🍒", "🍉", "🍓", "🥝", "🌽", "🍗",
                "🥩", "🍤", "🍩", "🍪", "🍫", "🍷",
                "🍸", "🍹", "🥂", "🥤", "🍾", "🥞"
        });

        EMOJI_CATEGORIES.put("Activities", new String[]{
                "⚽", "🏀", "🎸", "🎮", "🎯", "🎳",
                "🎼", "🎵", "🎶", "🎤", "🎨", "🎭",
                "🎬", "🎲", "🎷", "🎺", "🎻", "🏓",
                "🏸", "🏊", "⛷️", "🏂", "🚴", "🚵"
        });

        EMOJI_CATEGORIES.put("Travel & Places", new String[]{
                "🚗", "🚕", "🚙", "🚌", "🏎️", "🚓",
                "✈️", "🚀", "🛸", "🚤", "⛵", "🏝️",
                "🏔️", "🗻", "🏕️", "🛶", "🏙️", "🏘️",
                "🏰", "🏯", "🗽", "🌉", "🌌", "🎢"
        });

        EMOJI_CATEGORIES.put("Objects", new String[]{
                "📱", "💻", "🖥️", "⌨️", "🖱️", "💡",
                "🔦", "📷", "📹", "🎥", "📞", "📺",
                "📻", "🕰️", "⏰", "📚", "✏️", "🖊️",
                "🖋️", "📝", "🔒", "🔑", "🔨", "⚙️"
        });

        EMOJI_CATEGORIES.put("Symbols", new String[]{
                "❤️", "💔", "💯", "✔️", "🔔", "🔕",
                "🎵", "♻️", "⚠️", "❌", "✅", "❓",
                "❗", "💢", "🔥", "✨", "🌟", "🎇",
                "💧", "🌊", "🌀", "💤", "🏁", "🚩"
        });

        EMOJI_CATEGORIES.put("Flags", new String[]{
                "🏳️", "🏴", "🏁", "🚩", "🇺🇸", "🇨🇦",
                "🇬🇧", "🇦🇺", "🇫🇷", "🇩🇪", "🇮🇳", "🇨🇳",
                "🇯🇵", "🇰🇷", "🇮🇹", "🇪🇸", "🇧🇷", "🇷🇺",
                "🇲🇽", "🇿🇦", "🇳🇿", "🇦🇷", "🇸🇪", "🇳🇴"
        });
        emojiMap.put("!:D", "😂");
        emojiMap.put("!;)", "\uD83D\uDE09");
        emojiMap.put("!:)", "\uD83D\uDE00");
        emojiMap.put("!:P", "\uD83D\uDE0B");
        emojiMap.put("!xD", "\uD83E\uDD23");
        emojiMap.put("!hearteyes", "\uD83D\uDE0D");
        emojiMap.put("!blowkiss", "\uD83D\uDE18");
        emojiMap.put("!<3", "❤️");
        emojiMap.put("!thinking", "\uD83E\uDD14");
        emojiMap.put("!sadface", "\uD83E\uDD7A");
        emojiMap.put("!clap", "\uD83D\uDC4F");
        emojiMap.put("!thumbsup", "\uD83D\uDC4D");
        emojiMap.put("!nice", "\uD83D\uDC4C");
        emojiMap.put("!thumbsdown", "\uD83D\uDC4E");
        emojiMap.put("!shrug", "\uD83E\uDD37");
        emojiMap.put("!facepalm", "\uD83E\uDD26");
    }

    public String emojiPicker(String message, Scanner terminalIn) {

        System.out.println("\n--- Emoji Picker ---");
        int index = 1;
        List<String> emojiList = new ArrayList<>();
        for (Map.Entry<String, String[]> category : EMOJI_CATEGORIES.entrySet()) {
            System.out.println(category.getKey() + ":");
            for (String emoji : category.getValue()) {
                System.out.printf("%d: %s  ", index, emoji);
                emojiList.add(emoji);
                index++;
            }
            System.out.println("\n");
        }

        System.out.println("Enter the number of the emoji you want to use (or 0 to skip): ");
        int choice = 0;
        try {
            choice = terminalIn.nextInt();
            terminalIn.nextLine();
        } catch (Exception e) {
            System.out.println("Input must be numeric!");
        }

        String emoji = (choice > 0 && choice <= emojiList.size()) ? emojiList.get(choice - 1) : "";

        if (!emoji.isEmpty()) {
            message = message.replace("!emoji", emoji);
        }
        return message;
    }

    public String getEmoji(String message){
        for (String key : emojiMap.keySet()){
            if (message.contains(key)){
                message = message.replace(key, emojiMap.get(key));
            }
        }
        return message;
    }

    public Map<String, String> getEmojiMap(){
        return emojiMap;
    }
}
