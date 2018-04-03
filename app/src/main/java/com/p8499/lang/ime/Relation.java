package com.p8499.lang.ime;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2/8/2018.
 */

public class Relation {
    /**
     * Map KeyEvent META to rime modifier
     */
    private static Map<Integer, Relation> mMetaRime;
    /**
     * Map KeyEvent KEYCODE to rime key
     */
    private static Map<Integer, Relation> mKeycodeRime;
    /**
     * Map Unicode UTF8 to rime key
     */
    private static Map<Integer, Relation> mUtf8Rime;
    private String mFromName;
    private Integer mFromValue;
    private String mThruName;
    private Integer mThruValue;

    public static void initialize(Resources resources) {
        if (mMetaRime != null && mKeycodeRime != null && mUtf8Rime != null)
            return;
        try {
            mMetaRime = parseXmlResource(resources, R.xml.relations_meta_rime);
            mKeycodeRime = parseXmlResource(resources, R.xml.relations_keycode_rime);
            mUtf8Rime = parseXmlResource(resources, R.xml.relations_utf8_rime);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Iterator<Map.Entry<Integer, Relation>> metaRimeIterator = mMetaRime.entrySet().iterator();
        while (metaRimeIterator.hasNext()) {
            Relation relation = metaRimeIterator.next().getValue();
            relation.mThruValue = JniWrapper.rimeGetModifierByName(relation.mThruName);
        }
        Iterator<Map.Entry<Integer, Relation>> keycodeRimeIterator = mKeycodeRime.entrySet().iterator();
        while (keycodeRimeIterator.hasNext()) {
            Relation relation = keycodeRimeIterator.next().getValue();
            relation.mThruValue = JniWrapper.rimeGetKeycodeByName(relation.mThruName);
        }
        Iterator<Map.Entry<Integer, Relation>> utf8RimeIterator = mUtf8Rime.entrySet().iterator();
        while (utf8RimeIterator.hasNext()) {
            Relation relation = utf8RimeIterator.next().getValue();
            relation.mThruValue = JniWrapper.rimeGetKeycodeByName(relation.mThruName);
        }
    }

    public static Relation lookupMetaRime(int fromValue) {
        return mMetaRime.get(fromValue);
    }

    public static Relation lookupKeycodeRime(int fromValue) {
        return mKeycodeRime.get(fromValue);
    }

    public static Relation lookupUtf8Rime(int fromValue) {
        return mUtf8Rime.get(fromValue);
    }

    public static int lookupMetaRimeCombination(int fromValue) {
        int rimeMask = 0;
        Iterator<Map.Entry<Integer, Relation>> metaIterator = mMetaRime.entrySet().iterator();
        while (metaIterator.hasNext()) {
            Relation m = metaIterator.next().getValue();
            rimeMask |= (fromValue & m.mFromValue) > 0 ? m.mThruValue : 0;
        }
        return rimeMask;
    }

    private static Map<Integer, Relation> parseXmlResource(Resources resources, int resId) throws XmlPullParserException, IOException {
        XmlResourceParser parser = resources.getXml(resId);
        Map<Integer, Relation> relations = null;
        Relation relation = null;
        do {
            parser.next();
            Log.d("xml", String.format("name=%s, eventType=%s", parser.getName(), parser.getEventType()));
            if ("relations".equals(parser.getName()) && parser.getEventType() == XmlPullParser.START_TAG) {
                relations = new HashMap<>();
            } else if ("relation".equals(parser.getName()) && parser.getEventType() == XmlPullParser.START_TAG) {
                relation = new Relation();
            } else if ("fromName".equals(parser.getName()) && parser.getEventType() == XmlPullParser.START_TAG) {
                relation.mFromName = parser.nextText();
            } else if ("fromValue".equals(parser.getName()) && parser.getEventType() == XmlPullParser.START_TAG) {
                relation.mFromValue = Integer.decode(parser.nextText());
            } else if ("thruName".equals(parser.getName()) && parser.getEventType() == XmlPullParser.START_TAG) {
                relation.mThruName = parser.nextText();
            } else if ("thruValue".equals(parser.getName()) && parser.getEventType() == XmlPullParser.START_TAG) {
                relation.mThruValue = Integer.decode(parser.nextText());
            } else if ("relation".equals(parser.getName()) && parser.getEventType() == XmlPullParser.END_TAG) {
                relations.put(relation.mFromValue, relation);
            }
        } while (parser.getEventType() != XmlResourceParser.END_DOCUMENT);
        return relations;
    }

    public String getFromName() {
        return mFromName;
    }

    public int getFromValue() {
        return mFromValue == null ? 0 : mFromValue.intValue();
    }

    public String getThruName() {
        return mThruName;
    }

    public int getThruValue() {
        return mThruValue == null ? 0 : mThruValue.intValue();
    }
}
