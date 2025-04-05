package com.example.news.data.util
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.news.domain.model.Article
import javax.inject.Inject

class ShareUtils @Inject constructor(
    private val context: Context
) {
    fun shareNews(article: Article) {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, article.title)
                putExtra(Intent.EXTRA_TEXT, "${article.title}\n\n${article.url}")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }

            context.startActivity(
                Intent.createChooser(shareIntent, "Share article").apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
            )
        } catch (e: Exception) {
            Log.e("ShareUtils", "Error sharing article", e)
        }
    }
}
