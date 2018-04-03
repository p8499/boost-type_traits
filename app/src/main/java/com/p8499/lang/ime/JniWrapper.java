package com.p8499.lang.ime;

import com.p8499.lang.ime.rime.RimeCandidate;
import com.p8499.lang.ime.rime.RimeCommit;
import com.p8499.lang.ime.rime.RimeContext;
import com.p8499.lang.ime.rime.RimeSchemaListItem;
import com.p8499.lang.ime.rime.RimeTraits;

import java.util.List;

/**
 * Created by Administrator on 12/10/2017.
 */

public class JniWrapper {
    public static int KEY_VOIDSYMBOL;
    public static int KEY_PAGE_UP;
    public static int KEY_PAGE_DOWN;
    public static int KEY_UP;
    public static int KEY_DOWN;
    public static int META_RELEASE;
    public static int META_SHIFT;

    static {
        System.loadLibrary("app");
        KEY_VOIDSYMBOL = JniWrapper.rimeGetKeycodeByName("VoidSymbol");
        KEY_PAGE_UP = JniWrapper.rimeGetKeycodeByName("Page_Up");
        KEY_PAGE_DOWN = JniWrapper.rimeGetKeycodeByName("Page_Down");
        KEY_UP = JniWrapper.rimeGetKeycodeByName("Up");
        KEY_DOWN = JniWrapper.rimeGetKeycodeByName("Down");
        META_RELEASE = JniWrapper.rimeGetModifierByName("Release");
        META_SHIFT = JniWrapper.rimeGetModifierByName("Shift");
    }

    public static native void startup(RimeTraits traits);

    public static native void rimeSetup(/*RimeTraits traits from constants*/);

    public static native void rimeInitialize(/*RimeTraits traits from constants*/);

    public static native Integer rimeStartMaintenance(Integer fullCheck);

    public static native void rimeJoinMaintenanceThread();

    public static native RimeSchemaListItem[] rimeGetSchemaList();

    public static native Integer rimeGetModifierByName(String name);

    public static native String rimeGetModifierName(Integer modifier);

    public static native Integer rimeGetKeycodeByName(String name);

    public static native String rimeGetKeyName(Integer keycode);

    public static native Long rimeCreateSession();

    public static native Integer rimeGetStatus(Long sessionId);

    public static native void rimeSetOption(Long sessionId, String option, Integer value);

    public static native Integer rimeProcessKey(Long sessionId, Integer keycode, Integer mask);

    public static native RimeContext rimeGetContext(Long sessionId);

    public static native String rimeGetInput(Long sessionId);

    public static native Long rimeGetCaretPos(Long sessionId);

    public static native void rimeSetCaretPos(Long sessionId, Long caretPos);

    public static native List<RimeCandidate> rimeGetAllCandidates(Long sessionId, Integer size);

    public static native Integer rimeSelectCandidate(Long sessionId, Integer index);

    public static native Integer rimeSelectCandidateOnCurrentPage(Long sessionId, Integer index);

    public static native RimeCommit rimeGetCommit(Long sessionId);

    public static native Integer rimeDestroySession(Long sessionId);

    public static native void rimeCleanupStaleSessions();

    public static native void rimeCleanupAllSessions();

    public static native void rimeFinalize();

    public static native void shutdown();
}
