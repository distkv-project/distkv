#include <jni.h>

#include "storage_engine/storage_engine.h"

#ifdef __cplusplus
extern "C" {
#endif


/// Convert a Java String to C++ std::string.
inline std::string JavaStringToNativeString(JNIEnv *env, jstring jstr) {
  const char *c_str = env->GetStringUTFChars(jstr, nullptr);
  std::string result(c_str);
  env->ReleaseStringUTFChars(static_cast<jstring>(jstr), c_str);
  return result;
}

/// A helper to covert a native string to java string.
jstring NativeStringToJavaString(JNIEnv * env, const std::string &native_string) {
  return env->NewStringUTF(native_string.c_str());
}

/*
 * Class:     com_distkv_storageengine_StorageEngine
 * Method:    nativeInit
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_distkv_storageengine_StorageEngine_nativeInit
    (JNIEnv *env, jobject o) {
  auto *native_storage_engine_pointer = new distkv::StorageEngine();
  return reinterpret_cast<jlong>(native_storage_engine_pointer);
}

/*
 * Class:     com_distkv_storageengine_StorageEngine
 * Method:    nativeDestory
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_distkv_storageengine_StorageEngine_nativeDestory
    (JNIEnv *env, jobject o, jlong nativePointer) {
  auto *native_storage_engine_pointer = reinterpret_cast<distkv::StorageEngine *>(nativePointer);
  delete native_storage_engine_pointer;
}

/*
 * Class:     com_distkv_storageengine_StorageEngine
 * Method:    nativePut
 * Signature: (JLjava/lang/String;Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_distkv_storageengine_StorageEngine_nativePut
    (JNIEnv *env, jobject o, jlong nativePointer, jstring key, jstring value) {
  auto *native_storage_engine_pointer = reinterpret_cast<distkv::StorageEngine *>(nativePointer);
  native_storage_engine_pointer->Put(JavaStringToNativeString(env, key), JavaStringToNativeString(env, value));
}

/*
 * Class:     com_distkv_storageengine_StorageEngine
 * Method:    nativeGet
 * Signature: (JLjava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_distkv_storageengine_StorageEngine_nativeGet
    (JNIEnv *env, jobject o, jlong nativePointer, jstring key) {
  // TODO(qwang): These JNI methods shouldn't provide string interface, provide bytes instead.
  auto *native_storage_engine_pointer = reinterpret_cast<distkv::StorageEngine *>(nativePointer);
  return NativeStringToJavaString(env, native_storage_engine_pointer->Get(JavaStringToNativeString(env, key)));
}

#ifdef __cplusplus
}
#endif
