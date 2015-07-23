package net.lomeli.jss.core.helper;

import org.apache.commons.lang3.StringUtils;

import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

public class LocalizationHelper {
    private static String chatFormat = "&cf=";

    public static String translate(String unlocal, Object... args) {
        return String.format(translate(unlocal), args);
    }

    public static String translate(String unlocal) {
        String translation = StatCollector.translateToLocal(unlocal);
        if (translation.contains(chatFormat)) {
            int count = StringUtils.countMatches(translation, chatFormat);
            for (int j = 0; j < count; j++) {
                int i = translation.indexOf(chatFormat);
                char value = translation.charAt(i + chatFormat.length());
                translation = translation.replace(chatFormat + value, getFormatFromChar(value) + "");
            }
        }
        return translation;
    }

    private static EnumChatFormatting getFormatFromChar(char ch) {
        for (EnumChatFormatting format : EnumChatFormatting.values()) {
            if (format.getFormattingCode() == ch)
                return format;
        }
        return EnumChatFormatting.RESET;
    }
}
