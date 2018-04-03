#include "WrapperRimeCommit.hpp"
#include "Utils.hpp"

namespace app {
    RimeCommit *WrapperRimeCommit::create1() {
        RimeCommit *cobj = new RimeCommit({0});
        RIME_STRUCT_INIT(RimeCommit, *cobj);
        return cobj;
    }

    void WrapperRimeCommit::destroy1(RimeCommit *cobj) {
        RimeFreeCommit(cobj);
        delete cobj;
    }

    jobject WrapperRimeCommit::to(JNIEnv *env, RimeCommit *cobj) {
        jclass clz_RimeCommit = env->FindClass("com/p8499/lang/ime/rime/RimeCommit");//need delete
        jmethodID mid_RimeCommit_init = env->GetMethodID(clz_RimeCommit, "<init>", "()V");
        jobject obj_RimeCommit = env->NewObject(clz_RimeCommit, mid_RimeCommit_init);//return
        SetFieldInteger(env, obj_RimeCommit, "dataSize", cobj->data_size);
        SetFieldString(env, obj_RimeCommit, "text", cobj->text);
        env->DeleteLocalRef(clz_RimeCommit);
        return obj_RimeCommit;
    }
}