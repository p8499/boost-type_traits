#ifndef APP_WRAPPER_RIMECONTEXT
#define APP_WRAPPER_RIMECONTEXT

#include <jni.h>
#include <rime_api.h>

namespace app {
    class WrapperRimeContext {
    private:
    public:
        static RimeContext *create1();

        static void destroy1(RimeContext *cobj);

        static jobject to(JNIEnv *env, RimeContext *cobj);
    };
}
#endif