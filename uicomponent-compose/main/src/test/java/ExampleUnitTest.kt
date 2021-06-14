import org.junit.Assert
import org.junit.Test

class ExampleUnitTest {
    /**
     * For fixing build error
     * * What went wrong:
     * Execution failed for task ':uicomponent-compose:main:transformDebugUnitTestClassesWithAsm'.
     * > java.nio.file.NoSuchFileException:
    /home/runner/work/conference-app-2021/conference-app-2021/uicomponent-compose/main/build/tmp/kotlin-classes/debugUnitTest
     */
    @Test
    fun addition_isCorrect() {
        Assert.assertEquals(4, (2 + 2).toLong())
    }
}
