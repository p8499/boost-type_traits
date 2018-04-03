#ifndef APP_WRAPPER_RIMECOMPOSITION
#define APP_WRAPPER_RIMECOMPOSITION

#include <jni.h>
#include <rime_api.h>

namespace app {
    class WrapperRimeComposition {
    private:
    public:
        static jobject to(JNIEnv *env, RimeComposition *cobj);
    };
}
#endif