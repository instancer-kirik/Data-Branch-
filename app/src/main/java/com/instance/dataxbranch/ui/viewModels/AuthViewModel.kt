package com.instance.dataxbranch.ui.viewModels


    import androidx.lifecycle.ViewModel
    import com.instance.dataxbranch.domain.use_case.SaveGithubTokenUseCase
    import com.instance.dataxbranch.github.GithubRepository
    import dagger.hilt.android.lifecycle.HiltViewModel
    import kotlinx.coroutines.launch
    import javax.inject.Inject

    @HiltViewModel
    class AuthViewmodel @Inject constructor(

        private val saveGithubTokenUseCase: SaveGithubTokenUseCase
    ): ViewModel() {

        fun setToken(token: String) = saveGithubTokenUseCase.invoke(token)



}