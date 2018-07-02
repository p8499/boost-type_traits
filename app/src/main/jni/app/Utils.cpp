
#include <cstring>
#include "Utils.hpp"

namespace app {
    char *ToSig(const char *clz) {
        char *sig = new char[strlen(clz) + 3]{0};//'L',clz,';','\0'
        strcat(sig, "L");
        strcat(sig, clz);
        strcat(sig, ";");
        return sig;
    }

    char *ToSigArray(const char *clz) {
        char *sig = new char[strlen(clz) + 4]{0};//'[','L',clz,';','\0'
        strcat(sig, "[L");
        strcat(sig, clz);
        strcat(sig, ";");
        return sig;
    }

/**
  * convert between bool and java/lang/Boolean
  */
    bool ConvertToBool(JNIEnv *env, jobject jobj) {
        jboolean result = (jboolean) NULL;
        if (jobj != NULL) {
            jclass cls_Boolean = env->FindClass("java/lang/Boolean");//need delete
            jmethodID mid_Boolean_booleanValue = env->GetMethodID(cls_Boolean, "booleanValue", "()Z");
            result = env->CallBooleanMethod(jobj, mid_Boolean_booleanValue);
            env->DeleteLocalRef(cls_Boolean);
        }
        return result;
    }

    jobject ConvertFromBool(JNIEnv *env, bool b) {
        jclass cls_Boolean = env->FindClass("java/lang/Boolean");//need delete
        jmethodID mid_Boolean_init = env->GetMethodID(cls_Boolean, "<init>", "(Z)V");
        jobject result = env->NewObject(cls_Boolean, mid_Boolean_init, b);//return
        env->DeleteLocalRef(cls_Boolean);
        return result;
    }

/**
 * convert between int and java/lang/Integer
 */
    int ConvertToInt(JNIEnv *env, jobject jobj) {
        jint result = (jint) NULL;
        if (jobj != NULL) {
            jclass cls_Integer = env->FindClass("java/lang/Integer");//need delete
            jmethodID mid_Integer_intValue = env->GetMethodID(cls_Integer, "intValue", "()I");
            result = env->CallIntMethod(jobj, mid_Integer_intValue);
            env->DeleteLocalRef(cls_Integer);
        }
        return result;
    }

    jobject ConvertFromInt(JNIEnv *env, int i) {
        jclass cls_Integer = env->FindClass("java/lang/Integer");//need delete
        jmethodID mid_Integer_init = env->GetMethodID(cls_Integer, "<init>", "(I)V");
        jobject result = env->NewObject(cls_Integer, mid_Integer_init, i);
        env->DeleteLocalRef(cls_Integer);
        return result;
    }

/**
 * convert between long and java/lang/Long
 */
    long ConvertToLong(JNIEnv *env, jobject jobj) {
        jlong result = (jlong) NULL;
        if (jobj != NULL) {
            jclass cls_Long = env->FindClass("java/lang/Long");//need delete
            jmethodID mid_Long_longValue = env->GetMethodID(cls_Long, "longValue", "()J");
            result = env->CallLongMethod(jobj, mid_Long_longValue);
            env->DeleteLocalRef(cls_Long);
        }
        return result;
    }

    jobject ConvertFromLong(JNIEnv *env, long l) {
        jclass cls_Long = env->FindClass("java/lang/Long");//need delete
        jmethodID mid_Long_init = env->GetMethodID(cls_Long, "<init>", "(J)V");
        jobject result = env->NewObject(cls_Long, mid_Long_init, l);
        env->DeleteLocalRef(cls_Long);
        return result;
    }

/**
 * convert between float and java/lang/Float
 */
    float ConvertToFloat(JNIEnv *env, jobject jobj) {
        jfloat result = (jfloat) NULL;
        if (jobj != NULL) {
            jclass cls_Float = env->FindClass("java/lang/Float");//need delete
            jmethodID mid_Float_floatValue = env->GetMethodID(cls_Float, "floatValue", "()F");
            result = env->CallLongMethod(jobj, mid_Float_floatValue);
            env->DeleteLocalRef(cls_Float);
        }
        return result;
    }

    jobject ConvertFromFloat(JNIEnv *env, float f) {
        jclass cls_Float = env->FindClass("java/lang/Float");//need delete
        jmethodID mid_Float_init = env->GetMethodID(cls_Float, "<init>", "(F)V");
        jobject result = env->NewObject(cls_Float, mid_Float_init, f);
        env->DeleteLocalRef(cls_Float);
        return result;
    }

/**
 * convert between double and java/lang/Double
 */
    float ConvertToDouble(JNIEnv *env, jobject jobj) {
        jfloat result = (jdouble) NULL;
        if (jobj != NULL) {
            jclass cls_Double = env->FindClass("java/lang/Double");//need delete
            jmethodID mid_Double_doubleValue = env->GetMethodID(cls_Double, "doubleValue", "()D");
            result = env->CallDoubleMethod(jobj, mid_Double_doubleValue);
            env->DeleteLocalRef(cls_Double);
        }
        return result;
    }

    jobject ConvertFromDouble(JNIEnv *env, double d) {
        jclass cls_Double = env->FindClass("java/lang/Double");//need delete
        jmethodID mid_Double_init = env->GetMethodID(cls_Double, "<init>", "(D)V");
        jobject result = env->NewObject(cls_Double, mid_Double_init, d);
        env->DeleteLocalRef(cls_Double);
        return result;
    }


    /**
 * convert between char* and java/lang/String
 */
    char *ConvertToCharArray(JNIEnv *env, jstring jstr) {
        char *result = NULL;
        if (jstr != NULL) {
            const char *str = env->GetStringUTFChars(jstr, NULL);
            result = new char[strlen(str)];
            strcpy(result, str);
            env->ReleaseStringUTFChars(jstr, str);
        }
        return result;
    }

    jstring ConvertFromCharArray(JNIEnv *env, char *str) {
        jstring result = NULL;
        if (str != NULL) {
            result = env->NewStringUTF(str);//return
        }
        return result;
    }

/**
 * convert between char** and java/lang/String[]
 */
    char **ConvertToCharArray2(JNIEnv *env, jobjectArray jarr) {
        char **result = NULL;
        if (jarr != NULL) {
            jsize length = env->GetArrayLength(jarr);
            result = new char *[length];
            for (int i = 0; i < length; i++) {
                jstring jstr = (jstring) env->GetObjectArrayElement(jarr, i);//need delete
                if (jstr != NULL) {
                    result[i] = ConvertToCharArray(env, jstr);
                } else {
                    result[i] = NULL;
                }
                env->DeleteLocalRef(jstr);
            }
        }
        return result;
    }

    jobjectArray ConvertFromCharArray2(JNIEnv *env, char **arr) {
        jobjectArray result = NULL;
        if (arr != NULL) {
            int length = sizeof(arr) / sizeof(char *);
            jclass cls_String = env->FindClass("java/lang/String");//need delete
            result = env->NewObjectArray(length, cls_String, NULL);//return
            for (int i = 0; i < length; i++) {
                char *str = arr[i];
                if (str != NULL) {
                    jstring jstr = ConvertFromCharArray(env, str);//need delete
                    env->SetObjectArrayElement(result, i, jstr);
                    env->DeleteLocalRef(jstr);
                }
            }
            env->DeleteLocalRef(cls_String);
        }
        return result;
    }

/**
 * convert between void* and java/lang/Object[]
 */
//    template<typename T>
//    T *ConvertToAnyArray(JNIEnv *env, jobjectArray jarr, ConvertToAny<T> convert) {
//        T *result = NULL;
//        if (jarr != NULL) {
//            jsize length = env->GetArrayLength(jarr);
//            result = new T[length];
//            for (int i = 0; i < length; i++) {
//                jobject jobj = env->GetObjectArrayElement(jarr, i);
//                result[i] = jobj != NULL ? (*convert)(env, jobj) : NULL;
//            }
//        }
//        return result;
//    }

//    template<typename T>
//    jobjectArray ConvertFromAnyArray(JNIEnv *env, const char *clz, T *arr, ConvertFromAny<T> convert) {
//        jobjectArray result = NULL;
//        if (arr != NULL) {
//            int length = sizeof(arr) / sizeof(T);
//            result = env->NewObjectArray(length, env->FindClass(clz), NULL);
//            for (int i = 0; i < length; i++) {
//                T *obj = arr[i];
//                if (obj != NULL) {
//                    env->SetObjectArrayElement(result, i, (*convert)(env, obj));
//                }
//            }
//        }
//        return result;
//    }

/**
 * set or get bool (java.lang.Boolean) field
 */
    bool GetFieldBoolean(JNIEnv *env, jobject jobj, const char *name) {
        jclass cls = env->GetObjectClass(jobj);//need delete
        jfieldID fid = env->GetFieldID(cls, name, "Ljava/lang/Boolean;");
        jobject obj = env->GetObjectField(jobj, fid);//need delete
        bool result = ConvertToBool(env, obj);
        env->DeleteLocalRef(obj);
        env->DeleteLocalRef(cls);
        return result;
    }

    void SetFieldBoolean(JNIEnv *env, jobject jobj, const char *name, bool value) {
        jclass cls = env->GetObjectClass(jobj);//need delete
        jfieldID fid = env->GetFieldID(cls, name, "Ljava/lang/Boolean;");
        jobject obj = ConvertFromBool(env, value);//need delete
        env->SetObjectField(jobj, fid, obj);
        env->DeleteLocalRef(obj);
        env->DeleteLocalRef(cls);
    }

/**
 * set or get int (java.lang.Integer) field
 */
    int GetFieldInteger(JNIEnv *env, jobject jobj, const char *name) {
        jclass cls = env->GetObjectClass(jobj);//need delete
        jfieldID fid = env->GetFieldID(cls, name, "Ljava/lang/Integer;");
        jobject obj = env->GetObjectField(jobj, fid);//need delete
        int result = ConvertToInt(env, obj);
        env->DeleteLocalRef(obj);
        env->DeleteLocalRef(cls);
        return result;
    }

    void SetFieldInteger(JNIEnv *env, jobject jobj, const char *name, int value) {
        jclass cls = env->GetObjectClass(jobj);//need delete
        jfieldID fid = env->GetFieldID(cls, name, "Ljava/lang/Integer;");
        jobject obj = ConvertFromInt(env, value);//need delete
        env->SetObjectField(jobj, fid, obj);
        env->DeleteLocalRef(obj);
        env->DeleteLocalRef(cls);
    }

/**
 * set or get long (java.lang.Long) field
 */
    long GetFieldLong(JNIEnv *env, jobject jobj, const char *name) {
        jclass cls = env->GetObjectClass(jobj);//need delete
        jfieldID fid = env->GetFieldID(cls, name, "Ljava/lang/Long;");
        jobject obj = env->GetObjectField(jobj, fid);//need delete
        long result = ConvertToLong(env, obj);
        env->DeleteLocalRef(obj);
        env->DeleteLocalRef(cls);
        return result;
    }

    void SetFieldLong(JNIEnv *env, jobject jobj, const char *name, long value) {
        jclass cls = env->GetObjectClass(jobj);//need delete
        jfieldID fid = env->GetFieldID(cls, name, "Ljava/lang/Long;");
        jobject obj = ConvertFromLong(env, value);//need delete
        env->SetObjectField(jobj, fid, obj);
        env->DeleteLocalRef(obj);
        env->DeleteLocalRef(cls);
    }

/**
 * set or get float (java.lang.Float) field
 */
    float GetFieldFloat(JNIEnv *env, jobject jobj, const char *name) {
        jclass cls = env->GetObjectClass(jobj);//need delete
        jfieldID fid = env->GetFieldID(cls, name, "Ljava/lang/Float;");
        jobject obj = env->GetObjectField(jobj, fid);//need delete
        float result = ConvertToFloat(env, obj);
        env->DeleteLocalRef(obj);
        env->DeleteLocalRef(cls);
        return result;
    }

    void SetFieldFloat(JNIEnv *env, jobject jobj, const char *name, float value) {
        jclass cls = env->GetObjectClass(jobj);//need delete
        jfieldID fid = env->GetFieldID(cls, name, "Ljava/lang/Float;");
        jobject obj = ConvertFromFloat(env, value);//need delete
        env->SetObjectField(jobj, fid, obj);
        env->DeleteLocalRef(obj);
        env->DeleteLocalRef(cls);
    }

/**
 * set or get double (java.lang.Double) field
 */
    double GetFieldDouble(JNIEnv *env, jobject jobj, const char *name) {
        jclass cls = env->GetObjectClass(jobj);//need delete
        jfieldID fid = env->GetFieldID(cls, name, "Ljava/lang/Double;");
        jobject obj = env->GetObjectField(jobj, fid);//need delete
        double result = ConvertToDouble(env, obj);
        env->DeleteLocalRef(obj);
        env->DeleteLocalRef(cls);
        return result;
    }

    void SetFieldDouble(JNIEnv *env, jobject jobj, const char *name, double value) {
        jclass cls = env->GetObjectClass(jobj);//need delete
        jfieldID fid = env->GetFieldID(cls, name, "Ljava/lang/Double;");
        jobject obj = ConvertFromDouble(env, value);//need delete
        env->SetObjectField(jobj, fid, obj);
        env->DeleteLocalRef(obj);
        env->DeleteLocalRef(cls);
    }

    /**
 * set or get char* (java.lang.String) field
 */
    char *GetFieldString(JNIEnv *env, jobject jobj, const char *name) {
        jclass cls = env->GetObjectClass(jobj);//need delete
        jfieldID fid = env->GetFieldID(cls, name, "Ljava/lang/String;");
        jobject obj = env->GetObjectField(jobj, fid);//need delete
        char *result = ConvertToCharArray(env, (jstring) obj);
        env->DeleteLocalRef(obj);
        env->DeleteLocalRef(cls);
        return result;
    }

    void SetFieldString(JNIEnv *env, jobject jobj, const char *name, char *value) {
        jclass cls = env->GetObjectClass(jobj);//need delete
        jfieldID fid = env->GetFieldID(cls, name, "Ljava/lang/String;");
        jobject obj = ConvertFromCharArray(env, value);//need delete
        env->SetObjectField(jobj, fid, obj);
        env->DeleteLocalRef(obj);
        env->DeleteLocalRef(cls);
    }

/**
 * set or get char** (java.lang.String[]) field
 */
    char **GetFieldStringArray(JNIEnv *env, jobject jobj, const char *name) {
        jclass cls = env->GetObjectClass(jobj);//need delete
        jfieldID fid = env->GetFieldID(cls, name, "[Ljava/lang/String;");
        jobject obj = env->GetObjectField(jobj, fid);//need delete
        char **result = ConvertToCharArray2(env, (jobjectArray) obj);
        env->DeleteLocalRef(obj);
        env->DeleteLocalRef(cls);
        return result;
    }

    void SetFieldStringArray(JNIEnv *env, jobject jobj, const char *name, char **value) {
        jclass cls = env->GetObjectClass(jobj);//need delete
        jfieldID fid = env->GetFieldID(cls, name, "[Ljava/lang/String;");
        jobject obj = ConvertFromCharArray2(env, value);//need delete
        env->SetObjectField(jobj, fid, obj);
        env->DeleteLocalRef(obj);
        env->DeleteLocalRef(cls);
    }

/**
 * set or get void* (java.lang.Object) field
 */
//    template<typename T>
//    T *GetFieldAny(JNIEnv *env, jobject jobj, const char *name, const char *clz, ConvertToAny<T> convert) {
//        return (*convert)(env, env->GetObjectField(jobj, env->GetFieldID(env->GetObjectClass(jobj), name, ToSig(name))));
//    }

//    template<typename T>
//    void SetFieldAny(JNIEnv *env, jobject jobj, const char *name, const char *clz, T *value, ConvertFromAny<T> convert) {
//        env->SetObjectField(jobj, env->GetFieldID(env->GetObjectClass(jobj), name, ToSig(name)), (*convert)(env, value));
//    }

/**
 * set or get void* (java.lang.Object[]) field
 */
//    template<typename T>
//    T *GetFieldAnyArray(JNIEnv *env, jobject jobj, const char *name, const char *clz, ConvertToAny<T> convert) {
//        return ConvertToAnyArray(env, (jobjectArray) env->GetObjectField(jobj, env->GetFieldID(env->GetObjectClass(jobj), name, ToSigArray(name))), convert);
//    }

//    template<typename T>
//    void SetFieldAnyArray(JNIEnv *env, jobject jobj, const char *name, const char *clz, T *value, ConvertFromAny<T> convert) {
//        env->SetObjectField(jobj, env->GetFieldID(env->GetObjectClass(jobj), name, ToSigArray(name)), ConvertFromAnyArray(env, clz, value, convert));
//    }


///*set or get int (java.lang.Integer) field*/
//    jobject GetFieldInteger(JNIEnv *env, jobject jobj, const char *name) {
//        return env->GetObjectField(jobj, env->GetFieldID(env->GetObjectClass(jobj), name, "Ljava/lang/Integer;"));
//    }
//
//    void SetFieldInteger(JNIEnv *env, jobject jobj, const char *name, int value) {
//        env->SetObjectField(jobj, env->GetFieldID(env->GetObjectClass(jobj), name, "Ljava/lang/Integer;"), ConvertFromInt(env, value));
//    }
///*set or get char* (java.lang.String) field*/
//    jstring GetFieldString(JNIEnv *env, jobject jobj, const char *name) {
//        return (jstring) env->GetObjectField(jobj, env->GetFieldID(env->GetObjectClass(jobj), name, "Ljava/lang/String;"));
//    }
//
//    void SetFieldString(JNIEnv *env, jobject jobj, const char *name, char *value) {
//        env->SetObjectField(jobj, env->GetFieldID(env->GetObjectClass(jobj), name, "Ljava/lang/String;"), ConvertFromCharArray(env, value));
//    }
///*set or get char** (java.lang.String[]) field*/
//    jobjectArray GetFieldStringArray(JNIEnv *env, jobject jobj, const char *name) {
//        return (jobjectArray) env->GetObjectField(jobj, env->GetFieldID(env->GetObjectClass(jobj), name, "[Ljava/lang/String;"));
//    }
//
//    void SetFieldStringArray(JNIEnv *env, jobject jobj, const char *name, char **value) {
//        //TODO
//    }
//
//    jobject GetFieldObject(JNIEnv *env, jobject jobj, const char *name, const char *clz) {
//        char *sig = new char[strlen(name) + 3];//'L',clz,';','\0'
//        strcat(sig, "L");
//        strcat(sig, clz);
//        strcat(sig, ";");
//        jobject result = env->GetObjectField(jobj, env->GetFieldID(env->GetObjectClass(jobj), name, sig));
//        delete sig;
//        return result;
//    }
//
//    void SetFieldObject(JNIEnv *env, jobject jobj, const char *name, const char *clz, void *value, ConvertToObject convert) {
//        char *sig = new char[strlen(name) + 3];//'L',clz,';','\0'
//        strcat(sig, "L");
//        strcat(sig, clz);
//        strcat(sig, ";");
//        env->SetObjectField(jobj, env->GetFieldID(env->GetObjectClass(jobj), name, sig), (*convert)(env, value));
//        delete sig;
//    }
//
//    jobjectArray GetFieldObjectArray(JNIEnv *env, jobject jobj, const char *name, const char *clz) {
//        char *sig = new char[strlen(name) + 3];//'[','L',clz,';','\0'
//        strcat(sig, "[L");
//        strcat(sig, clz);
//        strcat(sig, ";");
//        jobjectArray result = (jobjectArray) env->GetObjectField(jobj, env->GetFieldID(env->GetObjectClass(jobj), name, sig));
//        delete sig;
//        return result;
//    }
//
//    void SetFieldObjectArray(JNIEnv *env, jobject jobj, const char *name, const char *clz, void **value, ConvertFromAny convert) {
//        char *sig = new char[strlen(name) + 3];//'[','L',clz,';','\0'
//        strcat(sig, "[L");
//        strcat(sig, clz);
//        strcat(sig, ";");
//        jobjectArray jarr = env->NewObjectArray(sizeof(value), env->FindClass(clz), NULL);
//        for (int i = 0; i < sizeof(value); i++) {
//            env->SetObjectArrayElement(jarr, i, (*convert)(env, value[i]));
//        }
//        env->SetObjectField(jobj, env->GetFieldID(env->GetObjectClass(jobj), name, sig), jarr);
//        delete sig;
//    }
//
///*Convert between bool and java/lang/Boolean*/
//    bool ConvertToBool(JNIEnv *env, jobject jobj) {
//        jboolean result = NULL;
//        if (jobj != NULL)
//            result = env->CallBooleanMethod(jobj, env->GetMethodID(env->FindClass("java/lang/Boolean"), "booleanValue", "()Z"));
//        return result;
//    }
//
//    jobject ConvertFromBool(JNIEnv *env, bool b) {
//        jclass clz = env->FindClass("java/lang/Boolean");
//        jobject result = env->NewObject(clz, env->GetMethodID(clz, "<init>", "(Z)V"), b);
//        return result;
//    }
//
///*Convert between int and java/lang/Integer*/
//    int ConvertToInt(JNIEnv *env, jobject jobj) {
//        jint result = NULL;
//        if (jobj != NULL)
//            result = env->CallIntMethod(jobj, env->GetMethodID(env->FindClass("java/lang/Integer"), "intValue", "()I"));
//        return result;
//    }
//
//    jobject ConvertFromInt(JNIEnv *env, int i) {
//        jclass clz = env->FindClass("java/lang/Integer");
//        jobject result = env->NewObject(clz, env->GetMethodID(clz, "<init>", "(I)V"), i);
//        return result;
//    }
//
//    /*Convert between long and java/lang/Long*/
//    long ConvertToLong(JNIEnv *env, jobject jobj) {
//        jlong result = NULL;
//        if (jobj != NULL) {
//            result = env->CallLongMethod(jobj, env->GetMethodID(env->FindClass("java/lang/Long"), "longValue", "()J"));
//        }
//        return result;
//    }
//
//    jobject ConvertFromLong(JNIEnv *env, long l) {
//        jclass clz = env->FindClass("java/lang/Long");
//        jobject result = env->NewObject(clz, env->GetMethodID(clz, "<init>", "(J)V"), l);
//        return result;
//    }
//
///*Convert between char* and java/lang/String*/
//    char *ConvertToCharArray(JNIEnv *env, jstring jstr) {
//        char *result = NULL;
//        if (jstr != NULL) {
//            const char *str = env->GetStringUTFChars(jstr, NULL);
//            result = new char[strlen(str)];
//            strcpy(result, str);
//            env->ReleaseStringUTFChars(jstr, str);
//        }
//        return result;
//    }
//
//    jstring ConvertFromCharArray(JNIEnv *env, char *str) {
//        jstring result = NULL;
//        if (str != NULL) {
//            result = env->NewStringUTF(str);
//        }
//        return result;
//    }
//
///*Convert between char** and java/lang/String[]*/
//    char **ConvertToCharArray2(JNIEnv *env, jobjectArray jarr) {
//        char **result = NULL;
//        if (jarr != NULL) {
//            jsize length = env->GetArrayLength(jarr);
//            result = new char *[length];
//            for (int i = 0; i < length; i++) {
//                jstring jstr = (jstring) env->GetObjectArrayElement(jarr, i);
//                if (jstr != NULL) {
//                    result[i] = ConvertToCharArray(env, jstr);
//                } else {
//                    result[i] = NULL;
//                }
//            }
//        }
//        return result;
//    }
//
//    jobjectArray ConvertFromCharArray2(JNIEnv *env, char **arr) {
//        //TODO
//        return NULL;
//    }
//
///*Convert between bool and java/lang/Boolean*/
//    typedef jobject (*ConvertFromAny)(JNIEnv *env, void *obj);//a type of convert function
}