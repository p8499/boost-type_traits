#include "WrapperRimeContext.hpp"
#include "WrapperRimeComposition.hpp"
#include "WrapperRimeMenu.hpp"
#include "Utils.hpp"

namespace app {
    RimeContext *WrapperRimeContext::create1() {
        RimeContext *cobj = new RimeContext({0});
        RIME_STRUCT_INIT(RimeContext, *cobj);
        return cobj;
    }

    void WrapperRimeContext::destroy1(RimeContext *cobj) {
        RimeFreeContext(cobj);
        delete cobj;
    }

    jobject WrapperRimeContext::to(JNIEnv *env, RimeContext *cobj) {
        jclass cls_RimeContext = env->FindClass("com/p8499/lang/ime/rime/RimeContext");//need delete
        jmethodID cls_RimeContext_init = env->GetMethodID(cls_RimeContext, "<init>", "()V");
        jobject obj_RimeContext = env->NewObject(cls_RimeContext, cls_RimeContext_init);//return
        SetFieldInteger(env, obj_RimeContext, "dataSize", cobj->data_size);
        SetFieldAny<RimeComposition>(env, obj_RimeContext, "composition", "com/p8499/lang/ime/rime/RimeComposition", &cobj->composition, WrapperRimeComposition::to);
        SetFieldAny<RimeMenu>(env, obj_RimeContext, "menu", "com/p8499/lang/ime/rime/RimeMenu", &cobj->menu, WrapperRimeMenu::to);
        SetFieldString(env, obj_RimeContext, "commitTextPreview", cobj->commit_text_preview);
        SetFieldStringArray(env, obj_RimeContext, "selectLabels", cobj->select_labels);
        env->DeleteLocalRef(cls_RimeContext);
        return obj_RimeContext;
    }
}