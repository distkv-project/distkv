#ifndef DISTKV_STORAGE_ENGINE_H
#define DISTKV_STORAGE_ENGINE_H

class StorageEngine {


 public:
  void Put(const std::string &key, const std::string &value) {
    store_.put(key, value);
  }

  std::string Get(const std::string &key) {
    return store_[key];
  }

 private:
  std::unordered_map<std::string, std::string> store_;
};

#endif //DISTKV_STORAGE_ENGINE_H
