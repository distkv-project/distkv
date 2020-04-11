#ifndef DISTKV_STORAGE_ENGINE_H
#define DISTKV_STORAGE_ENGINE_H

#include <string>
#include <unordered_map>

namespace distkv {


class StorageEngine {


 public:
  void Put(const std::string &key, const std::string &value) {
    store_[key] = value;
  }

  std::string Get(const std::string &key) {
    return store_[key];
  }

 private:
  std::unordered_map <std::string, std::string> store_;
};

} // namespace distkv

#endif //DISTKV_STORAGE_ENGINE_H
