package com.softvision.domain.interactor

import com.softvision.domain.model.TMDBItemDetails
import com.softvision.domain.repository.ResourcesRepository
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class ExplorerInteractor @Inject constructor(private val repository: ResourcesRepository<String, TMDBItemDetails, Int>): FetchInteractor {

    override fun invoke(type: String, page: Int): Single<List<TMDBItemDetails>> {

        // CALL ALL EXPLORER ENDPOINTS
        // RESOURCE REPOSITORY SHOULD CONTAIN THE LOGIC TO CALL ALL ENDPOINTS NECESSARY FOR EXPLORER FRAGMENT
        // AND SHOULD PROVIDE THE LOGIC FOR SAVING TMDB ITEMS (WITH THE CORRESPONDING CATEGORY = ENDPOINT ???)

//        when (type) {
//            is APIType.TRENDING_MOVIES -> {
//
//            }
//            is APIType.COMING_SOON_MOVIES -> {
//
//            }
//            is APIType.POPULAR_MOVIES -> {
//
//            }
//        }
        return repository.getData(type, page)
    }
}