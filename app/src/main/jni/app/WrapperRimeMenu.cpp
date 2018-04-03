#include <rime_api.h>
#include "WrapperRimeMenu.hpp"
#include "WrapperRimeCandidate.hpp"
#include "Utils.hpp"

namespace app {
    jobject WrapperRimeMenu::to(JNIEnv *env, RimeMenu *cobj) {
        jclass cls_RimeMenu = env->FindClass("com/p8499/lang/ime/rime/RimeMenu");//need delete
        jmethodID mid_RimeMenu_init = env->GetMethodID(cls_RimeMenu, "<init>", "()V");
        jobject obj_RimeMenu = env->NewObject(cls_RimeMenu, mid_RimeMenu_init);//return
        SetFieldInteger(env, obj_RimeMenu, "pageSize", cobj->page_size);
        SetFieldInteger(env, obj_RimeMenu, "pageNo", cobj->page_no);
        SetFieldInteger(env, obj_RimeMenu, "isLastPage", cobj->is_last_page);
        SetFieldInteger(env, obj_RimeMenu, "highlightedCandidateIndex", cobj->highlighted_candidate_index);
        SetFieldInteger(env, obj_RimeMenu, "numCandidates", cobj->num_candidates);
        SetFieldAnyArray<RimeCandidate>(env, obj_RimeMenu, "candidates", "com/p8499/lang/ime/rime/RimeCandidate", cobj->candidates, cobj->num_candidates, WrapperRimeCandidate::to);
        env->DeleteLocalRef(cls_RimeMenu);
        return obj_RimeMenu;
    }
}