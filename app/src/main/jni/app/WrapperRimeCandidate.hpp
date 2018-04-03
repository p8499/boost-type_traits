#ifndef APP_WRAPPER_RIMECANDIDATE
#define APP_WRAPPER_RIMECANDIDATE

#include <jni.h>
#include <rime_api.h>

namespace app {
    class WrapperRimeCandidate {
    private:
    public:
        static jobject to(JNIEnv *env, RimeCandidate *cobj);
    };
}
#endif