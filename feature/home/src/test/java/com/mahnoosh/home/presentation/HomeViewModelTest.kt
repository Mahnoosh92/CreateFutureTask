package com.mahnoosh.home.presentation

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.mahnoosh.common.stringresolver.StringResolver
import com.mahnoosh.common.toRomanNumeralList
import com.mahnoosh.home.domain.model.Character
import com.mahnoosh.home.domain.usecase.FetchCharacterUseCase
import com.mahnoosh.unit.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class HomeViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val fetchCharacterUseCase = mockk<FetchCharacterUseCase>()
    private val stringResolver = mockk<StringResolver>()
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        viewModel = HomeViewModel(
            fetchCharacterUseCase = fetchCharacterUseCase,
            stringResolver = stringResolver
        )
    }

    @Test
    fun `homeViewModel initial state is idle`() = runTest {
        assertThat(HomeUiState.Idle).isEqualTo(viewModel.homeUiState.value)
    }

    @Test
    fun `homeViewModel onGetCharacters shows loading and success states`() = runTest {
        coEvery { fetchCharacterUseCase() } coAnswers {
            delay(1L)
            Result.success(emptyList())
        }

        viewModel.onEvent(HomeEvent.GetCharacters())

        viewModel.homeUiState.test {
            val loadingState = awaitItem()
            assertThat(loadingState).isInstanceOf(HomeUiState.Loading::class.java)

            val successState = awaitItem()
            assertThat(successState).isInstanceOf(HomeUiState.Success::class.java)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `homeViewModel onGetCharacters shows loading and error states`() = runTest {
        coEvery { fetchCharacterUseCase() } coAnswers {
            delay(1L)
            Result.failure<List<Character>>(Exception(""))
        }

        viewModel.onEvent(HomeEvent.GetCharacters())

        viewModel.homeUiState.test {
            val loadingState = awaitItem()
            assertThat(loadingState).isInstanceOf(HomeUiState.Loading::class.java)

            val successState = awaitItem()
            assertThat(successState).isInstanceOf(HomeUiState.Error::class.java)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `homeViewModel onConsumeErrorMessage shows error state with null message`() = runTest {
        viewModel.onEvent(HomeEvent.ConsumeErrorMessage)

        assertThat(HomeUiState.Error(null)).isEqualTo(viewModel.homeUiState.value)
    }

    @Test
    fun `homeViewModel onGetCharacters should not call api again when home ui state is already success`() =
        runTest {
            viewModel.setHomeUiStateToSuccess(characters = emptyList())

            viewModel.onEvent(HomeEvent.GetCharacters())

            viewModel.homeUiState.test {
                val expectedState = awaitItem()
                assertThat(expectedState).isNotInstanceOf(HomeUiState.Loading::class.java)
                assertThat(expectedState).isInstanceOf(HomeUiState.Success::class.java)

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `homeViewModel onGetCharacters should call api again when home ui state is already error`() =
        runTest {
            viewModel.setHomeUiStateToError("")
            coEvery { fetchCharacterUseCase() } coAnswers {
                delay(1L)
                Result.success(emptyList())
            }

            viewModel.onEvent(HomeEvent.GetCharacters())

            viewModel.homeUiState.test {
                val loadingState = awaitItem()
                assertThat(loadingState).isInstanceOf(HomeUiState.Loading::class.java)

                val successState = awaitItem()
                assertThat(successState).isInstanceOf(HomeUiState.Success::class.java)

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `homeViewModel onGetCharacters should call api again when home ui state is success and isRefreshing is true`() =
        runTest {
            viewModel.setHomeUiStateToSuccess(emptyList())
            coEvery { fetchCharacterUseCase() } coAnswers {
                delay(1L)
                Result.success(emptyList())
            }

            viewModel.onEvent(HomeEvent.GetCharacters(isRefreshing = true))

            viewModel.homeUiState.test {
                val refreshState = awaitItem()
                assertThat(refreshState).isInstanceOf(HomeUiState.Refreshing::class.java)
                assertThat(refreshState).isNotInstanceOf(HomeUiState.Loading::class.java)

                val successState = awaitItem()
                assertThat(successState).isInstanceOf(HomeUiState.Success::class.java)

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `homeViewModel on search a character which is in list should return filtered characters`() =
        runTest {
            viewModel.setHomeUiStateToSuccess(
                listOf(
                    Character(
                        name = "Arya Stark",
                        culture = "Northmen",
                        born = "In 263 AC, at Winterfell",
                        died = "In 299 AC, at Great Sept of Baelor in King's Landing",
                        aliases = emptyList(),
                        titles = emptyList(),
                        playedBy = emptyList(),
                        gender = "Male",
                        father = "",
                        mother = "",
                        tvSeries = listOf(
                            "Season 1",
                            "Season 2",
                            "Season 3",
                            "Season 4",
                            "Season 5",
                            "Season 6",
                            "Season 7",
                            "Season 8"
                        ).toRomanNumeralList()
                    )
                )
            )

            viewModel.onEvent(HomeEvent.Search("Arya"))

            viewModel.filteredCharacters.test {
                val item = awaitItem()
                assertThat(item).hasSize(1)

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `homeViewModel on search a character which is not in list should return empty list`() =
        runTest {
            viewModel.setHomeUiStateToSuccess(
                listOf(
                    Character(
                        name = "Arya Stark",
                        culture = "Northmen",
                        born = "In 263 AC, at Winterfell",
                        died = "In 299 AC, at Great Sept of Baelor in King's Landing",
                        aliases = emptyList(),
                        titles = emptyList(),
                        playedBy = emptyList(),
                        gender = "Male",
                        father = "",
                        mother = "",
                        tvSeries = listOf(
                            "Season 1",
                            "Season 2",
                            "Season 3",
                            "Season 4",
                            "Season 5",
                            "Season 6",
                            "Season 7",
                            "Season 8"
                        ).toRomanNumeralList()
                    )
                )
            )

            viewModel.onEvent(HomeEvent.Search("x"))

            viewModel.filteredCharacters.test {
                val item = awaitItem()
                assertThat(item).hasSize(0)

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `homeViewModel on search a character when api is failed should return empty list`() =
        runTest {
            viewModel.setHomeUiStateToError("")

            viewModel.onEvent(HomeEvent.Search("x"))

            viewModel.filteredCharacters.test {
                val item = awaitItem()
                assertThat(item).hasSize(0)

                cancelAndIgnoreRemainingEvents()
            }
        }
}