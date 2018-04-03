#ifndef APP_WRAPPER_RIMESTATUS
#define APP_WRAPPER_RIMESTATUS

#include <jni.h>
#include <rime_api.h>

namespace app {
    class WrapperRimeStatus {
    private:
    public:
        static RimeStatus *create1();

        static void destroy1(RimeStatus *cobj);

        static jobject to(JNIEnv *env, RimeStatus *cobj);
    };
}
#endif