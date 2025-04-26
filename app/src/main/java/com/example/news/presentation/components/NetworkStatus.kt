// presentation/components/NetworkStatus.kt
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.example.news.R

@Composable
fun NetworkStatus(isOnline: Boolean) {
    val context = LocalContext.current

    LaunchedEffect(isOnline) {
        if (!isOnline) {
            Toast.makeText(
                context,
                context.getString(R.string.offline_mode_showing_cached_data),
                Toast.LENGTH_LONG
            ).show()
        }
    }
}