#include <stdio.h>
#include <string.h>
#include <rime_api.h>
#include <rime/key_table.h>
#include <stdlib.h>
#include "app.hpp"
#include <glog/logging.h>

//#define  LOG    "JavaCallCDemoLog" // 这个是自定义的LOG的标识
//#define  LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,LOG,__VA_ARGS__) // 定义LOGD类型
//#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG,__VA_ARGS__) // 定义LOGI类型
//#define  LOGW(...)  __android_log_print(ANDROID_LOG_WARN,LOG,__VA_ARGS__) // 定义LOGW类型
//#define LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG,__VA_ARGS__) // 定义LOGE类型
//#define LOGF(...)  __android_log_print(ANDROID_LOG_FATAL,LOG,__VA_ARGS__) // 定义LOGF类型
void on_message(void *context_object,
                RimeSessionId session_id,
                const char *message_type,
                const char *message_value) {
    LOG(INFO)
    << "message: sessionId=" << session_id << ","
    << "messageType=" << message_value << ","
    << "messageValue=" << message_value << "\n";
}

JNIEXPORT void JNICALL Java_com_p8499_lang_ime_JniWrapper_test
        (JNIEnv *env, jclass obj) {
    RimeApi *rime = rime_get_api();
    RIME_STRUCT(RimeTraits, traits);
    traits.app_name = "rime.console";
    traits.user_data_dir = "/data/data/com.p8499.lang.ime/files";
    traits.shared_data_dir = "/data/data/com.p8499.lang.ime/files";
    rime->setup(&traits);
    rime->set_notification_handler(&on_message, NULL);
    rime->initialize(NULL);
    if (rime->start_maintenance(True)) {
        rime->join_maintenance_thread();
    }
    RimeSchemaList list;
    rime->get_schema_list(&list);
    rime->free_schema_list(&list);

    RimeSessionId sessionId = rime->create_session();

    RIME_STRUCT(RimeContext, context);
    Bool processKey1 = rime->process_key(sessionId, 110, 0);
    Bool getContext1 = rime->get_context(sessionId, &context);
    Bool processKey2 = rime->process_key(sessionId, 110, RimeGetModifierByName("Release"));
    Bool getContext2 = rime->get_context(sessionId, &context);

    rime->finalize();
}