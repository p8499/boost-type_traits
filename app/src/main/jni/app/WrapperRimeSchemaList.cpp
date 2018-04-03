#include "WrapperRimeSchemaList.hpp"
#include "WrapperRimeSchemaListItem.hpp"
#include "Utils.hpp"

namespace app {
    RimeSchemaList *WrapperRimeSchemaList::create1() {
        RimeSchemaList *cobj = new RimeSchemaList({0});
        return cobj;
    }

    void WrapperRimeSchemaList::destroy1(RimeSchemaList *cobj) {
        RimeFreeSchemaList(cobj);
        delete cobj;
    }

    jobjectArray WrapperRimeSchemaList::to(JNIEnv *env, RimeSchemaList *cobj) {
        return ConvertFromAnyArray<RimeSchemaListItem>(env, "com/p8499/lang/ime/rime/RimeSchemaListItem", cobj->list, cobj->size, WrapperRimeSchemaListItem::to);
    }
}