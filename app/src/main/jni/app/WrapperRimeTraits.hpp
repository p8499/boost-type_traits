#ifndef APP_WRAPPER_RIMETRAITS
#define APP_WRAPPER_RIMETRAITS

#include <jni.h>
#include <rime_api.h>

namespace app {
    class WrapperRimeTraits {
    private:
    public:

        static RimeTraits *create1(JNIEnv *env, jobject jobj);

        static void destroy1(RimeTraits *cobj);

        static jobject to(JNIEnv *env, RimeTraits *cobj);
    };
}
#endif