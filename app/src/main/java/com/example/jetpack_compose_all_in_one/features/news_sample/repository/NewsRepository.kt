package com.example.jetpack_compose_all_in_one.features.news_sample.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.jetpack_compose_all_in_one.di.NewsAPI
import com.example.jetpack_compose_all_in_one.features.news_sample.data.data.LatestNewsResponse
import com.example.jetpack_compose_all_in_one.features.news_sample.data.remote.NewsService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NewsRepository @Inject constructor(
    @NewsAPI private val newsService: NewsService
) : INewsRepository{

    override fun getLatestNews(): Single<LatestNewsResponse> {
        return newsService.getLatestNews()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override val latestNewsResponse = MutableLiveData<LatestNewsResponse>()
}