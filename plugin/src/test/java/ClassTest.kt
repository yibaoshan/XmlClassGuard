import com.xml.guard.model.MappingParser
import com.xml.guard.transform.MyClassWriter
import com.xml.guard.utils.to26Long
import org.junit.Test
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type
import java.io.File
import java.util.regex.Pattern
import kotlin.math.pow

/**
 * User: ljx
 * Date: 2023/7/18
 * Time: 15:33
 */
class ClassTest {

    private val UPPERCASE_PATTERN: Pattern = Pattern.compile("^[A-Z]+$")

    @Test
    fun test() {
        println("FF".to26Long())
    }

    fun String.to26Long(): Long {
        val regexMixedCaseAndDigits = "^[a-zA-Z0-9]+$" // 允许大小写混合和数字
        val isMixedCaseAndDigits = Pattern.matches(regexMixedCaseAndDigits, this)
        if (!isMixedCaseAndDigits) {
            throw IllegalArgumentException("string must consist of mixed upper and lowercase letters and digits, but it was $this")
        }

        var num = 0L
        for (i in indices) {
            val c = get(i)
            val charValue = when {
                c.isUpperCase() -> c - 'A'
                c.isLowerCase() -> c - 'a' + 26 // 使小写字母的值位于大写字母之后
                else -> c - '0' + 52 // 使数字的值位于字母之后
            }
            num += (charValue * 62.0.pow((length - 1 - i).toDouble())).toLong()
        }
        return num
    }

    @Test
    fun test1() {
        val myClassWriter = MyClassWriter()
        myClassWriter.generateClass()
        File("DynamicClass.class").writeBytes(myClassWriter.toByteArray())
    }
}