package chela.kotlinJS.model

import chela.kotlinJS.i18n.ChI18n

object ChModel{
    internal val repo: MutableMap<String, Model> = HashMap()
    operator fun get(v:String):Any = get(v.split(".").map { it.trim() })
    operator fun get(v:List<String>):Any{
        if(v.isEmpty()) throw Exception("invalid list size == 0")
        if(v[0] == "i18n") return ChI18n.get(v)
        repo[v[0]]?.let { return find(v, it) } ?: run{


            throw Exception("invalid key:" + v[0])
        }
    }
    fun record(v: List<String>, record: Model): Any {
        if (v.isEmpty()) throw Exception("invalid list size == 0")
        return find(v, record)
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

