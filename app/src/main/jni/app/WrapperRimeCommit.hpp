#ifndef APP_WRAPPER_RIMECOMMIT
#define APP_WRAPPER_RIMECOMMIT

#include <jni.h>
#include <rime_api.h>

namespace app {
    class WrapperRimeCommit {
    private:
    public:
        static RimeCommit *create1();

        static void destroy1(RimeCommit *cobj);

        static jobject to(JNIEnv *env, RimeCommit *cobj);
    };
}
#endif