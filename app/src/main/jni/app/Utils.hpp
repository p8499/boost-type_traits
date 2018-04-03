#ifndef APP_UTILS
#define APP_UTILS

#include <jni.h>

namespace app {
    char *ToSig(const char *clz);

    char *ToSigArray(const char *clz);

/**
 * convert between bool and java/lang/Boolean
 */
    bool ConvertToBool(JNIEnv *env, jobject jobj);

    jobject ConvertFromBool(JNIEnv *env, bool b);

/**
 * convert between int and java/lang/Integer
 */
    int ConvertToInt(JNIEnv *env, jobject jobj);

    jobject ConvertFromInt(JNIEnv *env, int i);

/**
 * convert between long and java/lang/Long
 */
    long ConvertToLong(JNIEnv *env, jobject jobj);

    jobject ConvertFromLong(JNIEnv *env, long l);

/**
 * convert between char* and java/lang/String
 */
    char *ConvertToCharArray(JNIEnv *env, jstring jstr);

    jstring ConvertFromCharArray(JNIEnv *env, char *str);

/**
 * convert between char** and java/lang/String[]
 */
    char **ConvertToCharArray2(JNIEnv *env, jobjectArray jarr);

    jobjectArray ConvertFromCharArray2(JNIEnv *env, char **arr);

/**
 * convert between void* and java/lang/Object
 */
    template<typename T>
    //typedef T *(*ConvertToAny)(JNIEnv *env, jobject obj);
    using ConvertToAny=T *(*)(JNIEnv *env, jobject obj);

    template<typename T>
    //typedef jobject (*ConvertFromAny)(JNIEnv *env, T *obj);
    using ConvertFromAny=jobject (*)(JNIEnv *env, T *obj);

/**
 * convert between void* and java/lang/Object[]
 */
    template<typename T>
    T *ConvertToAnyArray(JNIEnv *env, jobjectArray jarr, ConvertToAny<T> convert) {
        T *result = NULL;
        if (jarr != NULL) {
            jsize length = env->GetArrayLength(jarr);
            result = new T[length];
            for (int i = 0; i < length; i++) {
                jobject jobj = env->GetObjectArrayElement(jarr, i);//need delete
                result[i] = jobj != NULL ? (*convert)(env, jobj) : NULL;
                env->DeleteLocalRef(jobj);
            }
        }
        return result;
    }

    template<typename T>
    jobjectArray ConvertFromAnyArray(JNIEnv *env, const char *clz, T *arr, int length, ConvertFromAny<T> convert) {
        jobjectArray result = NULL;
        if (arr != NULL) {
            jclass jcls = env->FindClass(clz);//need delete
            result = env->NewObjectArray(length, jcls, NULL);//return
            for (int i = 0; i < length; i++) {
                T obj = arr[i];
                jobject jobj = (*convert)(env, &obj);//need delete
                env->SetObjectArrayElement(result, i, jobj);
                env->DeleteLocalRef(jobj);
            }
            env->DeleteLocalRef(jcls);
        }
        return result;
    }

/**
 * set or get bool (java.lang.Boolean) field
 */
    bool GetFieldBoolean(JNIEnv *env, jobject jobj, const char *name);

    void SetFieldBoolean(JNIEnv *env, jobject jobj, const char *name, bool value);

/**
 * set or get int (java.lang.Integer) field
 */
    int GetFieldInteger(JNIEnv *env, jobject jobj, const char *name);

    void SetFieldInteger(JNIEnv *env, jobject jobj, const char *name, int value);

/**
 * set or get long (java.lang.Long) field
 */
    long GetFieldLong(JNIEnv *env, jobject jobj, const char *name);

    void SetFieldLong(JNIEnv *env, jobject jobj, const char *name, long value);

/**
 * set or get char* (java.lang.String) field
 */
    char *GetFieldString(JNIEnv *env, jobject jobj, const char *name);

    void SetFieldString(JNIEnv *env, jobject jobj, const char *name, char *value);

/**
 * set or get char** (java.lang.String[]) field
 */
    char **GetFieldStringArray(JNIEnv *env, jobject jobj, const char *name);

    void SetFieldStringArray(JNIEnv *env, jobject jobj, const char *name, char **value);

/**
 * set or get void* (java.lang.Object) field
 */
    template<typename T>
    T *GetFieldAny(JNIEnv *env, jobject jobj, const char *name, const char *clz, ConvertToAny<T> convert) {
        jclass jcls = env->GetObjectClass(jobj);//need delete
        jfieldID jfid = env->GetFieldID(jcls, name, ToSig(clz));
        jobject jf = env->GetObjectField(jobj, jfid);//need delete
        T result = (*convert)(env, jf);
        env->DeleteLocalRef(jf);
        env->DeleteLocalRef(jcls);
        return result;
    }

    template<typename T>
    void SetFieldAny(JNIEnv *env, jobject jobj, const char *name, const char *clz, T *value, ConvertFromAny<T> convert) {
        jclass jcls = env->GetObjectClass(jobj);//need delete
        jfieldID jfid = env->GetFieldID(jcls, name, ToSig(clz));
        jobject jf = (*convert)(env, value);//need delete
        env->SetObjectField(jobj, jfid, jf);
        env->DeleteLocalRef(jf);
        env->DeleteLocalRef(jcls);
    }

/**
 * set or get void* (java.lang.Object[]) field
 */
    template<typename T>
    T *GetFieldAnyArray(JNIEnv *env, jobject jobj, const char *name, const char *clz, ConvertToAny<T> convert) {
        jclass jcls = env->GetObjectClass(jobj);//need delete
        jfieldID jfid = env->GetFieldID(jcls, name, ToSigArray(clz));
        jobject jf = env->GetObjectField(jobj, jfid);//need delete
        T result = ConvertToAnyArray(env, (jobjectArray) jf, convert);
        env->DeleteLocalRef(jf);
        env->DeleteLocalRef(jcls);
        return result;
    }

    template<typename T>
    void SetFieldAnyArray(JNIEnv *env, jobject jobj, const char *name, const char *clz, T *value, int size, ConvertFromAny<T> convert) {
        jclass jcls = env->GetObjectClass(jobj);//need delete
        jfieldID jfid = env->GetFieldID(jcls, name, ToSigArray(clz));
        jobject jf = ConvertFromAnyArray(env, clz, value, size, convert);//need delete
        env->SetObjectField(jobj, jfid, jf);
        env->DeleteLocalRef(jf);
        env->DeleteLocalRef(jcls);
    }
}
#endif