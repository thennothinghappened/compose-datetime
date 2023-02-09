import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import com.wakaztahir.common.DialogDemos
import org.jetbrains.skiko.wasm.onWasmReady

fun main() {
    onWasmReady {
        BrowserViewportWindow("DateTime Demo") {
            MaterialTheme {
                Box(modifier = Modifier.fillMaxSize()) {
                    DialogDemos()
                }
            }
        }
    }
}


