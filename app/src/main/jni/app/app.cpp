#include <rime_api.h>
#include <rime/key_table.h>
#include "WrapperRimeTraits.hpp"
#include "WrapperRimeSchemaList.hpp"
#include "WrapperRimeStatus.hpp"
#include "WrapperRimeContext.hpp"
#include "WrapperRimeCommit.hpp"
#include "WrapperRimeCandidate.hpp"
#include "Utils.hpp"
#include "app.hpp"

using namespace std;
using namespace app;

RimeApi *rime;
RimeTraits *cTraits;

JNIEXPORT void JNICALL Java_com_p8499_lang_ime_JniWrapper_startup
        (JNIEnv *env, jclass cls, jobject traits) {
    rime = rime_get_api();
    cTraits = WrapperRimeTraits::create1(env, traits);
}

JNIEXPORT void JNICALL Java_com_p8499_lang_ime_JniWrapper_rimeSetup
        (JNIEnv *env, jclass cls) {
    rime->setup(cTraits);
}

JNIEXPORT void JNICALL Java_com_p8499_lang_ime_JniWrapper_rimeInitialize
        (JNIEnv *env, jclass cls) {
    rime->initialize(cTraits);
}

JNIEXPORT jobject JNICALL Java_com_p8499_lang_ime_JniWrapper_rimeStartMaintenance
        (JNIEnv *env, jclass cls, jobject fullCheck) {
    return ConvertFromInt(env, rime->start_maintenance(ConvertToInt(env, fullCheck)));
}

JNIEXPORT void JNICALL Java_com_p8499_lang_ime_JniWrapper_rimeJoinMaintenanceThread
        (JNIEnv *env, jclass cls) {
    rime->join_maintenance_thread();
}

JNIEXPORT jobjectArray JNICALL Java_com_p8499_lang_ime_JniWrapper_rimeGetSchemaList
        (JNIEnv *env, jclass cls) {
    RimeSchemaList *cSchemaList = WrapperRimeSchemaList::create1();;
    jobjectArray schemaList = rime->get_schema_list(cSchemaList) ? WrapperRimeSchemaList::to(env, cSchemaList) : NULL;
    WrapperRimeSchemaList::destroy1(cSchemaList);
    cSchemaList = NULL;
    return schemaList;
}

JNIEXPORT jobject JNICALL Java_com_p8499_lang_ime_JniWrapper_rimeSelectSchema
        (JNIEnv *env, jclass cls, jobject sessionId, jstring schemaId) {
    return ConvertFromInt(env, rime->select_schema(ConvertToLong(env, sessionId), ConvertToCharArray(env, schemaId)));
}

JNIEXPORT jobject JNICALL Java_com_p8499_lang_ime_JniWrapper_rimeGetModifierByName
        (JNIEnv *env, jclass cls, jstring name) {
    return ConvertFromInt(env, RimeGetModifierByName(ConvertToCharArray(env, name)));
}

JNIEXPORT jstring JNICALL Java_com_p8499_lang_ime_JniWrapper_rimeGetModifierName
        (JNIEnv *env, jclass cls, jobject modifier) {
    return ConvertFromCharArray(env, (char *) RimeGetModifierName(ConvertToInt(env, modifier)));
}

JNIEXPORT jobject JNICALL Java_com_p8499_lang_ime_JniWrapper_rimeGetKeycodeByName
        (JNIEnv *env, jclass cls, jstring name) {
    return ConvertFromInt(env, RimeGetKeycodeByName(ConvertToCharArray(env, name)));
}

JNIEXPORT jstring JNICALL Java_com_p8499_lang_ime_JniWrapper_rimeGetKeyName
        (JNIEnv *env, jclass cls, jobject keycode) {
    return ConvertFromCharArray(env, (char *) RimeGetKeyName(ConvertToInt(env, keycode)));
}

JNIEXPORT jobject JNICALL Java_com_p8499_lang_ime_JniWrapper_rimeCreateSession
        (JNIEnv *env, jclass cls) {
    return ConvertFromLong(env, (unsigned long) rime->create_session());
}

JNIEXPORT jobject JNICALL Java_com_p8499_lang_ime_JniWrapper_rimeGetStatus
        (JNIEnv *env, jclass cls, jobject sessionId) {
    RimeStatus *cStatus = WrapperRimeStatus::create1();
    jobject status = rime->get_status(ConvertToLong(env, sessionId), cStatus) ? WrapperRimeStatus::to(env, cStatus) : NULL;
    WrapperRimeStatus::destroy1(cStatus);
    cStatus = NULL;
    return status;
}

JNIEXPORT void JNICALL Java_com_p8499_lang_ime_JniWrapper_rimeSetOption
        (JNIEnv *env, jclass cls, jobject sessionId, jstring option, jobject value) {
    char *o = ConvertToCharArray(env, option);
    rime->set_option(ConvertToLong(env, sessionId), o, ConvertToInt(env, value));
    delete o;
    o = NULL;
}

JNIEXPORT jobject JNICALL Java_com_p8499_lang_ime_JniWrapper_rimeProcessKey
        (JNIEnv *env, jclass cls, jobject sessionId, jobject keycode, jobject mask) {
    return ConvertFromInt(env, rime->process_key(ConvertToLong(env, sessionId), ConvertToInt(env, keycode), ConvertToInt(env, mask)));
}

JNIEXPORT jobject JNICALL Java_com_p8499_lang_ime_JniWrapper_rimeGetContext
        (JNIEnv *env, jclass cls, jobject sessionId) {
    RimeContext *cContext = WrapperRimeContext::create1();
    jobject context = rime->get_context(ConvertToLong(env, sessionId), cContext) ? WrapperRimeContext::to(env, cContext) : NULL;
    WrapperRimeContext::destroy1(cContext);
    cContext = NULL;
    return context;
}

JNIEXPORT jstring JNICALL Java_com_p8499_lang_ime_JniWrapper_rimeGetInput
        (JNIEnv *env, jclass cls, jobject sessionId) {
    return ConvertFromCharArray(env, (char *) rime->get_input(ConvertToLong(env, sessionId)));
}

JNIEXPORT jobject JNICALL Java_com_p8499_lang_ime_JniWrapper_rimeGetCaretPos
        (JNIEnv *env, jclass cls, jobject sessionId) {
    return ConvertFromLong(env, rime->get_caret_pos(ConvertToLong(env, sessionId)));
}

JNIEXPORT void JNICALL Java_com_p8499_lang_ime_JniWrapper_rimeSetCaretPos
        (JNIEnv *env, jclass cls, jobject sessionId, jobject caretPos) {
    rime->set_caret_pos(ConvertToLong(env, sessionId), ConvertToLong(env, caretPos));
}

JNIEXPORT jobject JNICALL Java_com_p8499_lang_ime_JniWrapper_rimeSelectCandidate
        (JNIEnv *env, jclass clz, jobject sessionId, jobject index) {
    return ConvertFromInt(env, rime->select_candidate(ConvertToLong(env, sessionId), ConvertToInt(env, index)));
}

JNIEXPORT jobject JNICALL Java_com_p8499_lang_ime_JniWrapper_rimeGetAllCandidates
        (JNIEnv *env, jclass clz, jobject sessionId, jobject size) {
    jclass cls_ArrayList = env->FindClass("java/util/ArrayList");//need delete
    jmethodID mid_ArrayList_init = env->GetMethodID(cls_ArrayList, "<init>", "()V");
    jobject obj_ArrayList = env->NewObject(cls_ArrayList, mid_ArrayList_init);//return
    jmethodID mid_ArrayList_add = env->GetMethodID(cls_ArrayList, "add", "(Ljava/lang/Object;)Z");
    RimeCandidateListIterator iterator = {0};
    if (rime->candidate_list_begin(ConvertToLong(env, sessionId), &iterator)) {
        int cSize = ConvertToInt(env, size);
        while (rime->candidate_list_next(&iterator) && iterator.index < cSize) {
            jobject obj_RimeCandidate = WrapperRimeCandidate::to(env, &iterator.candidate);//need delete
            env->CallBooleanMethod(obj_ArrayList, mid_ArrayList_add, obj_RimeCandidate);
            env->DeleteLocalRef(obj_RimeCandidate);
        }
        rime->candidate_list_end(&iterator);
    }
    env->DeleteLocalRef(cls_ArrayList);
    return obj_ArrayList;
}

JNIEXPORT jobject JNICALL Java_com_p8499_lang_ime_JniWrapper_rimeSelectCandidateOnCurrentPage
        (JNIEnv *env, jclass clz, jobject sessionId, jobject index) {
    return ConvertFromInt(env, rime->select_candidate_on_current_page(ConvertToLong(env, sessionId), ConvertToInt(env, index)));
}

JNIEXPORT jobject JNICALL Java_com_p8499_lang_ime_JniWrapper_rimeGetCommit
        (JNIEnv *env, jclass cls, jobject sessionId) {
    RimeCommit *cCommit = WrapperRimeCommit::create1();
    jobject commit = rime->get_commit(ConvertToLong(env, sessionId), cCommit) ? WrapperRimeCommit::to(env, cCommit) : NULL;
    WrapperRimeCommit::destroy1(cCommit);
    cCommit = NULL;
    return commit;
}

JNIEXPORT jobject JNICALL Java_com_p8499_lang_ime_JniWrapper_rimeDestroySession
        (JNIEnv *env, jclass cls, jobject sessionId) {
    return ConvertFromInt(env, rime->destroy_session(ConvertToLong(env, sessionId)));
}

JNIEXPORT void JNICALL Java_com_p8499_lang_ime_JniWrapper_rimeCleanupStaleSessions
        (JNIEnv *env, jclass cls) {
    rime->cleanup_stale_sessions();
}

JNIEXPORT void JNICALL Java_com_p8499_lang_ime_JniWrapper_rimeCleanupAllSessions
        (JNIEnv *env, jclass cls) {
    rime->cleanup_all_sessions();
}

JNIEXPORT void JNICALL Java_com_p8499_lang_ime_JniWrapper_rimeFinalize
        (JNIEnv *env, jclass cls) {
    rime->finalize();
}

JNIEXPORT void JNICALL Java_com_p8499_lang_ime_JniWrapper_shutdown
        (JNIEnv *, jclass) {
    if (cTraits != NULL) {
        WrapperRimeTraits::destroy1(cTraits);
        cTraits = NULL;
    }
}