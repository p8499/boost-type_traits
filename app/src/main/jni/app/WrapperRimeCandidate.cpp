#include "WrapperRimeCandidate.hpp"
#include "Utils.hpp"

namespace app {
    jobject WrapperRimeCandidate::to(JNIEnv *env, RimeCandidate *cobj) {
        jclass cls_RimeCandidate = env->FindClass("com/p8499/lang/ime/rime/RimeCandidate");//need delete
        jmethodID mid_RimeCandidate_init = env->GetMethodID(cls_RimeCandidate, "<init>", "()V");
        jobject objRimeCandidate = env->NewObject(cls_RimeCandidate, mid_RimeCandidate_init);//return
        SetFieldString(env, objRimeCandidate, "text", cobj->text);
        SetFieldString(env, objRimeCandidate, "comment", cobj->comment);
        env->DeleteLocalRef(cls_RimeCandidate);
        return objRimeCandidate;
    }
}