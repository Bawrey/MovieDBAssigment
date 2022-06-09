package id.indocyber.moviedbassigment.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.indocyber.api_service.RetrofitClient
import id.indocyber.api_service.service.*
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @Singleton
    fun provideRetrofitClient(@ApplicationContext context: Context) =
        RetrofitClient.getRetrofitMovieClient(context)

    @Provides
    @Singleton
    fun provideGenreListService(retrofit: Retrofit) = retrofit.create(GenreListService::class.java)

    @Provides
    @Singleton
    fun provideMovieListService(retrofit: Retrofit) = retrofit.create(MovieListService::class.java)

    @Provides
    @Singleton
    fun provideMovieDetailService(retrofit: Retrofit) = retrofit.create(MovieDetailService::class.java)

    @Provides
    @Singleton
    fun provideMovieReviewService(retrofit: Retrofit) = retrofit.create(MovieReviewService::class.java)

    @Provides
    @Singleton
    fun provideMovieVideoService(retrofit: Retrofit) = retrofit.create(MovieVideoService::class.java)


}