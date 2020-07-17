package com.android.pokemon.presentation.ui.main

import com.android.pokemon.BaseMockitoTest
import com.android.pokemon.data.db.PokemonEntity
import com.android.pokemon.domain.IDataRepository
import com.android.pokemon.domain.entity.GetPokemonsResponse
import com.android.pokemon.domain.interactor.GetLocalPokemonUserCase
import com.android.pokemon.domain.interactor.GetRemotePokemonUserCase
import com.android.pokemon.presentation.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.concurrent.TimeUnit

@RunWith(JUnit4::class)
class PokedexViewModelTest : BaseMockitoTest() {
    @InjectMocks
    lateinit var viewModel: PokedexViewModel


    @Mock
    lateinit var getRemotePokemonUserCase: GetRemotePokemonUserCase

    @Mock
    lateinit var getLocalPokemonUserCase: GetLocalPokemonUserCase

    @Mock
    lateinit var repository: IDataRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getRemotePokemonUserCase = Mockito.spy(GetRemotePokemonUserCase(repository))

        getLocalPokemonUserCase = Mockito.spy(GetLocalPokemonUserCase(repository))


        viewModel = PokedexViewModel(
            getRemotePokemonUserCase, getLocalPokemonUserCase
        )
    }

    @Test
    fun getRemotePokemon() = with(viewModel.liveGetPokemons.test(2)) {
        //Given
        val list = ArrayList<PokemonEntity>()
        list.add(PokemonEntity(1, "Pikachu", "www.pokemon.cl", false))
        val response = GetPokemonsResponse(1, list)
        //When
        runBlocking(Dispatchers.Main) {
            Mockito.`when`(repository.getPokemons()).thenReturn(response)
        }
        //Then
        viewModel.getRemotePokemon()
        awaitValues(2, TimeUnit.SECONDS)

        verify().forEach {
            when (it) {
                is Resource.Loading -> println("Loading State")
                is Resource.Success -> {
                    it.data?.get(0)?.let {
                        assertEquals(1, it.id)
                        assertEquals("Pikachu", it.name)
                        assertEquals("www.pokemon.cl", it.url)
                        assertEquals(false, it.favorite)
                    }
                }
            }
        }
    }

    @Test
    fun getLocalPokemon()  = with(viewModel.liveGetPokemons.test(1)) {
        //Given
        val response = ArrayList<PokemonEntity>()
        response.add(PokemonEntity(1, "Pikachu", "www.pokemon.cl", false))
        //When
        runBlocking(Dispatchers.Main) {
            Mockito.`when`(repository.getLocalPokemons()).thenReturn(response)
        }
        //Then
        viewModel.getLocalPokemon()
        awaitValues(1, TimeUnit.SECONDS)

        verify().forEach {
            when (it) {
                is Resource.Success -> {
                    it.data?.get(0)?.let {
                        assertEquals(1, it.id)
                        assertEquals("Pikachu", it.name)
                        assertEquals("www.pokemon.cl", it.url)
                        assertEquals(false, it.favorite)
                    }
                }
            }
        }
    }

    @Test
    fun validatePersistence() {
        viewModel.validatePersistence()
    }
}