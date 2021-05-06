package hr.ferit.lloina.lv4_zad2.data.api

class ApiHelper(private val apiService: ApiService) {
    fun getUsers() = apiService.getUsers()
}