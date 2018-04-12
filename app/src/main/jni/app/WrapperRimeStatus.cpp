#include "WrapperRimeStatus.hpp"
#include "Utils.hpp"

namespace app {
    RimeStatus *WrapperRimeStatus::create1() {
        RimeStatus *cobj = new RimeStatus({0});
        RIME_STRUCT_INIT(RimeStatus, *cobj);
        return cobj;
    }

    void WrapperRimeStatus::destroy1(RimeStatus *cobj) {
        RimeFreeStatus(cobj);
        delete cobj;
    }

    jobject WrapperRimeStatus::to(JNIEnv *env, RimeStatus *cobj) {
        jclass cls_RimeStatus = env->FindClass("com/p8499/lang/ime/rime/RimeStatus");//need delete
        jmethodID cls_RimeStatus_init = env->GetMethodID(cls_RimeStatus, "<init>", "()V");
        jobject obj_RimeStatus = env->NewObject(cls_RimeStatus, cls_RimeStatus_init);//return
        SetFieldInteger(env, obj_RimeStatus, "dataSize", cobj->data_size);
        SetFieldString(env, obj_RimeStatus, "schemaId", cobj->schema_id);
        SetFieldString(env, obj_RimeStatus, "schemaName", cobj->schema_name);
        SetFieldInteger(env, obj_RimeStatus, "isDisabled", cobj->is_disabled);
        SetFieldInteger(env, obj_RimeStatus, "isComposing", cobj->is_composing);
        SetFieldInteger(env, obj_RimeStatus, "isAsciiMode", cobj->is_ascii_mode);
        SetFieldInteger(env, obj_RimeStatus, "isFullShape", cobj->is_full_shape);
        SetFieldInteger(env, obj_RimeStatus, "isSimplified", cobj->is_simplified);
        SetFieldInteger(env, obj_RimeStatus, "isTraditional", cobj->is_traditional);
        SetFieldInteger(env, obj_RimeStatus, "isAsciiPunct", cobj->is_ascii_punct);
        env->DeleteLocalRef(cls_RimeStatus);
        return obj_RimeStatus;
    }
}