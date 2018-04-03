#include <cstring>
#include "WrapperRimeTraits.hpp"
#include "Utils.hpp"

namespace app {

    RimeTraits *WrapperRimeTraits::create1(JNIEnv *env, jobject jobj) {
        RimeTraits *cobj = new RimeTraits({0});
        RIME_STRUCT_INIT(RimeTraits, *cobj);
        //will not copy cobj->data_size
        cobj->shared_data_dir = GetFieldString(env, jobj, "sharedDataDir");
        cobj->user_data_dir = GetFieldString(env, jobj, "userDataDir");
        cobj->distribution_name = GetFieldString(env, jobj, "distributionName");
        cobj->distribution_code_name = GetFieldString(env, jobj, "distributionCodeName");
        cobj->distribution_version = GetFieldString(env, jobj, "distributionVersion");
        cobj->app_name = GetFieldString(env, jobj, "appName");
        cobj->modules = (const char **) GetFieldStringArray(env, jobj, "modules");
        return cobj;
    }

    void WrapperRimeTraits::destroy1(RimeTraits *cobj) {
        delete cobj->shared_data_dir;
        cobj->shared_data_dir = NULL;
        delete cobj->user_data_dir;
        cobj->user_data_dir = NULL;
        delete cobj->distribution_name;
        cobj->distribution_name = NULL;
        delete cobj->distribution_code_name;
        cobj->distribution_code_name = NULL;
        delete cobj->distribution_version;
        cobj->distribution_version = NULL;
        delete cobj->app_name;
        cobj->app_name = NULL;
        if (cobj->modules != NULL) {
            for (int i = 0; i < sizeof(cobj->modules) / sizeof(char *); i++) {
                delete cobj->modules[i];
                cobj->modules[i] = NULL;
            }
        }
        delete cobj->modules;
        cobj->modules = NULL;
        RIME_STRUCT_CLEAR(*cobj);
        delete cobj;
    }

    jobject WrapperRimeTraits::to(JNIEnv *env, RimeTraits *cobj) {
        return NULL;
        //TODO
    }
}