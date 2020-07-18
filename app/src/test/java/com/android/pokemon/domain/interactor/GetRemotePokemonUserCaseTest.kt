package com.android.pokemon.domain.interactor

import com.android.pokemon.data.db.PokemonEntity
import com.android.pokemon.domain.IDataRepository
import com.android.pokemon.domain.entity.GetPokemonRequest
import com.android.pokemon.domain.entity.GetPokemonsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class GetRemotePokemonUserCaseTest {

    @Mock
    lateinit var repository: IDataRepository

    private lateinit var userCase: GetRemotePokemonUserCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        userCase = GetRemotePokemonUserCase(repository = repository)
    }

    @Test
    fun prepareExecuteOnBackground() {
        //Given
        val list = ArrayList<PokemonEntity>()
        list.add(PokemonEntity(1, "Pikachu", "www.pokemon.cl", false))
        val response = GetPokemonsResponse(1, list)
        //When
        runBlocking(Dispatchers.Unconfined) {
            Mockito.`when`(repository.getPokemons()).thenReturn(response)
        }
        // then
        runBlocking(Dispatchers.Unconfined) {
            val result = userCase.prepareExecuteOnBackground(GetPokemonRequest())
            result.pokemon_species[0]?.let {
                assertEquals(1, it.id)
                assertEquals("Pikachu", it.name)
                assertEquals("www.pokemon.cl", it.url)
                assertEquals(false, it.favorite)
            }
        }
    }
}