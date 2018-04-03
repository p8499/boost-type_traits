#include "WrapperRimeSchemaListItem.hpp"
#include "Utils.hpp"

namespace app {
    jobject WrapperRimeSchemaListItem::to(JNIEnv *env, RimeSchemaListItem *cobj) {
        jclass cls_RimeSchemaListItem = env->FindClass("com/p8499/lang/ime/rime/RimeSchemaListItem");//need delete
        jmethodID mid_RimeSchemaListItem = env->GetMethodID(cls_RimeSchemaListItem, "<init>", "()V");
        jobject obj_RimeSchemaListItem = env->NewObject(cls_RimeSchemaListItem, mid_RimeSchemaListItem);//return
        SetFieldString(env, obj_RimeSchemaListItem, "schemaId", cobj->schema_id);
        SetFieldString(env, obj_RimeSchemaListItem, "name", cobj->name);
        env->DeleteLocalRef(cls_RimeSchemaListItem);
        return obj_RimeSchemaListItem;
    }
}