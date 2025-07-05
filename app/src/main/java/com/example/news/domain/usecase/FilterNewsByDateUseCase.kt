//package com.example.news.domain.usecase
//
//import com.example.news.domain.model.Article
//import com.example.news.domain.repository.NewsRepository
//import java.text.SimpleDateFormat
//import java.util.*
//import javax.inject.Inject
//
//class FilterNewsByDateUseCase @Inject constructor(
//    private val repository: NewsRepository
//) {
//    suspend operator fun invoke(selectedDate: String) : List<Article> {
//        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
//        val date = dateFormat.parse(selectedDate)
//        return repository.getNewsByDate(date)
//    }
//}