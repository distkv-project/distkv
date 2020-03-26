#include <jni.h>

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Class:     com_distkv_storageengine_StorageEngine
 * Method:    nativePut
 * Signature: (Ljava/lang/String;Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_distkv_storageengine_StorageEngine_nativePut
  (JNIEnv *, jobject, jstring, jstring) {

}

/*
 * Class:     com_distkv_storageengine_StorageEngine
 * Method:    nativeGet
 * Signature: (Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_distkv_storageengine_StorageEngine_nativeGet
  (JNIEnv *, jobject, jstring) {

}

#ifdef __cplusplus
}
#endif
