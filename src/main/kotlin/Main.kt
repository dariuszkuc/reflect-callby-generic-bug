import kotlinx.coroutines.runBlocking
import kotlin.reflect.KParameter
import kotlin.reflect.full.instanceParameter
import kotlin.reflect.full.memberFunctions

fun main() = runBlocking {
    val instance = FooImpl()
    for (fnName in listOf("optionalString", "optionalDataClass", "optionalGeneric")) {
        val fn = FooImpl::class.memberFunctions.first { it.name == fnName }
        val args: Map<KParameter, Any?> = mapOf(fn.instanceParameter!! to instance)

        val result = fn.callBy(args)
        println("call $fnName done => $result")
    }
}

interface Foo<T: Bar> {
    fun optionalString(input: String? = null): String
    fun optionalDataClass(input: BarImpl? = null): String
    fun optionalGeneric(input: T? = null): String
}

class FooImpl : Foo<BarImpl> {
    override fun optionalString(input: String?): String = "string"
    override fun optionalDataClass(input: BarImpl?): String = "data class"
    override fun optionalGeneric(input: BarImpl?): String = "generic"
}

interface Bar {
    val bar: String
}

data class BarImpl(override val bar: String): Bar