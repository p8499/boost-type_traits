#ifndef APP_WRAPPER_RIMESCHEMALIST
#define APP_WRAPPER_RIMESCHEMALIST

#include <jni.h>
#include <rime_api.h>

namespace app {
    class WrapperRimeSchemaList {
    private:
    public:
        static RimeSchemaList *create1();

        static void destroy1(RimeSchemaList *cobj);

        static jobjectArray to(JNIEnv *env, RimeSchemaList *cobj);
    };
}
#endif