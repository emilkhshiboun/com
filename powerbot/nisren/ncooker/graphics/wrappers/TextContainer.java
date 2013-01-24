package com.powerbot.nisren.ncooker.graphics.wrappers;

import com.powerbot.nisren.ncooker.graphics.wrappers.util.Text;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Nisren
 * Date: 28/12/12
 * Time: 14:42
 */
public class TextContainer {
    List<Text> textList;

    public TextContainer(Text... texts) {
        textList = new LinkedList<>();
        add(texts);
    }

    public void add(Text... texts) {
        if (texts != null && texts.length > 0) {
            for (Text text : texts) {
                textList.add(text);
            }
        }
    }

    public void remove(Text... texts) {
        if (texts != null && texts.length > 0) {
            for (Text text : texts) {
                if (textList.contains(text)) textList.remove(text);
            }
        }
    }

    public Text[] get() {
        return textList.toArray(new Text[textList.size()]);
    }

    public Text get(int index) {
        return textList.get(index);
    }
}
