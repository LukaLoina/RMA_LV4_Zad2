package hr.ferit.lloina.lv4_zad2.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import hr.ferit.lloina.lv4_zad2.data.api.ApiHelper
import hr.ferit.lloina.lv4_zad2.data.repository.MainRepository
import hr.ferit.lloina.lv4_zad2.ui.main.viewModel.MainViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            return  MainViewModel(MainRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name.")
    }

}