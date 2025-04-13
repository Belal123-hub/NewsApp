# NewsWave App 📰  
**Stay Updated Anywhere – Online or Offline!**  

<img src="https://github.com/user-attachments/assets/your-image-id" width="200" height="200" alt="NewsWave Icon" />  

## 📱 About NewsWave  
NewsWave is a modern news aggregator that delivers the latest headlines with offline support. Read, search, and filter news seamlessly, even without an internet connection. With dark mode, caching, and a sleek UI, NewsWave ensures you never miss a story.  

---

## 🚀 Features  
### Core Functionality:  
✅ **Latest News Feed**: View real-time headlines with source, image, description, and date.  
✅ **WebView Integration**: Open full articles in-app (no browser redirect).  
✅ **Offline Mode**: Cache news content and web pages for offline reading.  
✅ **Smart Search**: Find news by keywords (works offline).  
✅ **Date Filtering**: Filter news by calendar date (offline supported).  
✅ **Reading History**: Automatically save opened news for later access.  
✅ **Pagination**: Load data incrementally (online/offline).  
✅ **Network Awareness**: Detect offline mode and notify users.  
✅ **Share News**: Share articles with source, image, description, and URL.  
✅ **Theme Support**: Auto-switch between light/dark themes or manually override.  
✅ **Orientation Resilience**: Preserve state on screen rotation.  

---

## 🛠️ Tech Stack  
### Modern Android Development with Best Practices:  
- **API**: Ktor (with `buildConfigField` for secure API keys) + NewsAPI.org  
- **Database**: Room (for caching news, history, and web content)  
- **UI**: Jetpack Compose + Material Design 3  
- **Architecture**: Clean Architecture + MVVM/MVI  
- **DI**: Hilt/Koin  
- **State Management**: Kotlin Coroutines + Flow  
- **Navigation**: Jetpack Navigation Compose  
- **Preferences**: Jetpack DataStore (for theme settings)  
- **Patterns**: Repository, Singleton, Factory (via Refactoring.Guru)  

---

## 📸 Screenshots  
| **News Feed** | **WebView**
|--------------|-------------|
| ![News](https://github.com/user-attachments/assets/b3cd33f0-7779-4992-b0b4-8c66291cc909) | ![WebView](https://github.com/user-attachments/assets/5ef338ca-903d-49a6-a774-0a6441dbddac)

---

## 🧩 App Structure  
### Clean Architecture Modules:  
1. **Data Layer**:  
   - `api/` (Ktor network calls)  
   - `database/` (Room entities, DAOs)  
   - `repository/` (NewsRepository implementation)  
2. **Domain Layer**:  
   - `model/` (entities, use cases)  
3. **Presentation Layer**:  
   - `ui/` (Compose screens + ViewModels):  
     - `feed/` (News list + pagination)  
     - `detail/` (WebView + sharing)  
     - `search/` (Search + filters)  
     - `history/` (Read articles)  
     - `theme/` (Dark/light toggle)  

---

## 🔧 Installation  
1. Clone the repository:  
   ```bash  
   git clone https://github.com/Belal123-hub/NewsApp.git 
   ```  
   ```  
2. Build and run in Android Studio (or via Gradle).  

---

## 🤝 Contributing  
PRs welcome! Follow Git Flow:  
- `feature/` for new additions  
- `fix/` for bug repairs  

---

## 📜 License  
`MIT` – Free to use and modify.  
--- 
