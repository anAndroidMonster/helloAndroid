#include "hello.h"
#include "com_android_lmc_ipcservice_MainActivity.h"
 
JNIEXPORT jstring JNICALL Java_com_android_lmc_ipcservice_MainActivity_sayHello
  (JNIEnv *env, jobject){
    return env->NewStringUTF("hello ndk");
  }