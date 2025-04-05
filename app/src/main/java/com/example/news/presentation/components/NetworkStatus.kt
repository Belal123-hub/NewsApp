// presentation/components/NetworkStatus.kt
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext

@Composable
fun NetworkStatus(isOnline: Boolean) {
    val context = LocalContext.current

    LaunchedEffect(isOnline) {
        if (!isOnline) {
            Toast.makeText(
                context,
                "Offline mode - showing cached data",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}