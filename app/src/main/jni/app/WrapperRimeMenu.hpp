#ifndef APP_WRAPPER_RIMEMENU
#define APP_WRAPPER_RIMEMENU

#include <jni.h>
#include <rime_api.h>

namespace app {
    class WrapperRimeMenu {
    private:
    public:
        static jobject to(JNIEnv *env,RimeMenu *cobj);
    };
}
#endif