#ifndef APP_WRAPPER_RIMESCHEMALISTITEM
#define APP_WRAPPER_RIMESCHEMALISTITEM

#include <jni.h>
#include <rime_api.h>

namespace app {
    class WrapperRimeSchemaListItem {
    private:
    public:
        static jobject to(JNIEnv *env, RimeSchemaListItem *cobj);
    };
}
#endif