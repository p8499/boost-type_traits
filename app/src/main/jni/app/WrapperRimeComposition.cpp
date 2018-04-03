#include <rime_api.h>
#include "WrapperRimeComposition.hpp"
#include "Utils.hpp"

namespace app {
    jobject WrapperRimeComposition::to(JNIEnv *env, RimeComposition *cobj) {
        jclass cls_RimeComposition = env->FindClass("com/p8499/lang/ime/rime/RimeComposition");//need delete
        jmethodID mid_RimeComposition = env->GetMethodID(cls_RimeComposition, "<init>", "()V");
        jobject obj_RimeComposition = env->NewObject(cls_RimeComposition, mid_RimeComposition);//return
        SetFieldInteger(env, obj_RimeComposition, "length", cobj->length);
        SetFieldInteger(env, obj_RimeComposition, "cursorPos", cobj->cursor_pos);
        SetFieldInteger(env, obj_RimeComposition, "selStart", cobj->sel_start);
        SetFieldInteger(env, obj_RimeComposition, "selEnd", cobj->sel_end);
        SetFieldString(env, obj_RimeComposition, "preedit", cobj->preedit);
        env->DeleteLocalRef(cls_RimeComposition);
        return obj_RimeComposition;
    }
}