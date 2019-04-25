package chela.kotlinJS.history

import chela.kotlinJS.core.ChJS
import kotlin.browser.window

abstract class ChHistory(private val default:String){
    private val history = mutableListOf<String>()
    protected var hash:String = ""
    operator fun invoke(){window.onhashchange = {
        val v = ChJS.decodeURIComponent(window.location.hash)
        hash = if(v.isBlank() || hash == "#") default else v
        when{
            history.isNotEmpty() && history.last() == hash->same()
            history.size > 1 && history[history.size - 2] == hash->back()
            else->{
                val idx = history.lastIndexOf(hash)
                if(idx != -1) inHistory(idx, history.size)
                add(history.size - 1)
                history += hash
            }
        }
        changed()
    }}
    protected open fun same(){}
    protected open fun back(){}
    protected open fun inHistory(idx: Int, size: Int){}
    protected abstract fun add(idx: Int):Boolean
    protected open fun changed(){}
}
