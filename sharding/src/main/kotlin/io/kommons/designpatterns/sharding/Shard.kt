package io.kommons.designpatterns.sharding

enum class DataType {
    TYPE1, TYPE2, TYPE3
}

data class Data(var key: Int, var value: String, var type: DataType)

data class Shard(val id: Int) {

    private val dataStore = LinkedHashMap<Int, Data>()

    fun storeData(data: Data) {
        dataStore[data.key] = data
    }

    fun clearData() {
        dataStore.clear()
    }

    fun getDataById(id: Int): Data? {
        return dataStore[id]
    }
}