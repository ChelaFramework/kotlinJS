package chela.kotlinJS.core

import kotlin.browser.document
import kotlin.js.Promise


object ChJS {
    val obj = js("Object")
    inline fun keys(v:dynamic, block:(String)->Unit) = obj.keys(v).unsafeCast<Array<String>>().forEach(block)
    inline fun <T> obj2map(v:dynamic, block:(String, dynamic)->T) = run {
        val r = mutableMapOf<String, T>()
        obj.keys(v).unsafeCast<Array<String>>().forEach{r[it] = block(it, v[it])}
        r
    }
    inline fun objForEach(v:dynamic, block:(String, dynamic)->Unit){
        obj.keys(v).unsafeCast<Array<String>>().forEach{block(it, v[it])}
    }
    inline fun obj(block:dynamic.()->Unit):dynamic{
        val o = js("{}")
        block(o)
        return o
    }
    inline fun obj(target:dynamic, block:dynamic.()->Unit):dynamic{
        block(target)
        return target
    }
    fun <R> then(p:dynamic, block:(dynamic)->R) = (p as? Promise<dynamic>)?.then(block)
    val enc = js("encodeURIComponent")
    @Suppress("UnsafeCastFromDynamic")
    fun encodeURIComponent(v:String):String = enc(v)
    val inF = js("function(k, t){return k in t;}")
    fun isIn(key:String, target:dynamic) = inF(key, target) as Boolean

    fun addJs(path:String) = Promise<dynamic>{res,_->
        val script = document.createElement("script")
        script.setAttribute("src", path)
        script.addEventListener("load", res)
        document.head?.appendChild(script)
    }
}

external fun delete(p: dynamic): Boolean = definedExternally
