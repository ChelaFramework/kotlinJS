package chela.kotlinJS.model

import chela.kotlinJS.cdata.ChCdata

object ChModel{
    internal val repo = mutableMapOf<String, Model>()
    operator fun get(v:String):Any = get(v.split(".").map { it.trim() })
    operator fun get(v:List<String>):Any{
        if(v.isEmpty()) throw Exception("invalid list size == 0")
        else if(v[0] == "cdata") return ChCdata[v[1]] ?: "---"//"no data:${v[1]}"
        repo[v[0]]?.let { return find(v, it) } ?: run{
            throw Exception("invalid key:" + v[0])
        }
    }
    fun record(v: List<String>, record: dynamic, i: Int, size: Int): Any {
        if (v.isEmpty()) throw Exception("invalid list size == 0")
        if(v.size == 1 && v[0] == "") return record as Any
        var r = record
        v.all{
            when(it){
                ":index"->{r = i; false}
                ":size"->{r = size; false}
                ":record"->{r = record; true}
                else -> {
                    if(r[it] != undefined) {
                        r = r[it]
                        true
                    }else{
                        r = null
                        false
                    }
                }
            }
        }
        return r
    }
    private fun find(v: List<String>, it: Model): Any {
        var model: Model? = it
        var list:MutableList<Any>? = null
        var r: Any = 0
        for(idx in 1 until v.size) {
            var k = v[idx]
            if(k[0] == '@') k = k.substring(1)
            r = model?.get(k) ?: list?.get(k.toInt()) ?: throw Exception("invalid key:${k} in $v")
            when(r){
                is Model ->{
                    model = r
                    list = null
                }
                is List<*>->{
                    model = null
                    @Suppress("UNCHECKED_CAST")
                    list = r as MutableList<Any>
                }
            }
        }
        return r
    }
}

