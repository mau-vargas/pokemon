package com.android.pokemon.presentation.ui.detail

import android.util.Log
import com.android.pokemon.BaseMockitoTest
import com.android.pokemon.data.db.PokemonEntity
import com.android.pokemon.domain.IDataRepository
import com.android.pokemon.domain.entity.Detail.*
import com.android.pokemon.domain.entity.GetPokemonsResponse
import com.android.pokemon.domain.entity.evolution.Evolution
import com.android.pokemon.domain.interactor.GetLocalPokemonUserCase
import com.android.pokemon.domain.interactor.GetPokemonDetailUserCase
import com.android.pokemon.domain.interactor.GetPokemonEvolutionUserCase
import com.android.pokemon.domain.interactor.GetRemotePokemonUserCase
import com.android.pokemon.presentation.ui.main.PokedexViewModel
import com.android.pokemon.presentation.util.Failure
import com.android.pokemon.presentation.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.concurrent.TimeUnit

class DetailViewModelTest : BaseMockitoTest() {

    @InjectMocks
    lateinit var viewModel: DetailViewModel

    @Mock
    lateinit var getPokemonDetailUserCase: GetPokemonDetailUserCase

    @Mock
    lateinit var getPokemonEvolutionUserCase: GetPokemonEvolutionUserCase

    @Mock
    lateinit var repository: IDataRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)


        getPokemonDetailUserCase = Mockito.spy(GetPokemonDetailUserCase(repository))

        getPokemonEvolutionUserCase = Mockito.spy(GetPokemonEvolutionUserCase(repository))


        viewModel = DetailViewModel(
            getPokemonDetailUserCase, getPokemonEvolutionUserCase
        )
    }

    @Test
    fun getPokemonDetailTest() = with(viewModel.liveGetPokemonDetail.test(2)) {
        //Given
        val pokemonName = "bulbasaur"

        val ability = Ability(AbilityX(pokemonName, "www.detail.com"))
        val abilities = ArrayList<Ability>()
        abilities.add(ability)
        val move = Move(MoveX("push", "www.moves.com"))
        val moves = ArrayList<Move>()
        moves.add(move)
        val types = ArrayList<Type>()
        types.add(Type(0, TypeX("glass", "www.Type.com")))
        val arrayListListAbility = ArrayList<Ability>()
        arrayListListAbility.add(ability)
        val location_area_encounters = ""
        val height = 4
        val weight = 5
        val id = 0

        val response = Detail(
            abilities,
            location_area_encounters, moves, types, height, weight, id, pokemonName
        )

        //When
        runBlocking(Dispatchers.Main) {
            Mockito.`when`(repository.getPokemonDetail(pokemonName)).thenReturn(response)
        }
        //Then
        viewModel.getPokemonDetail(pokemonName)
        awaitValues(2, TimeUnit.SECONDS)

        verify().forEach {
            when (it) {
                is Resource.Loading -> println("Loading State")
                is Resource.Success -> {
                    it.data
                    it.data?.abilities?.first()?.ability?.let {
                        assertEquals("bulbasaur",it.name)
                        assertEquals("www.detail.com",it.url)
                    }
                }
            }
        }
    }

    @Test
    fun getPokemonEvolutionTest(name: String)  = with(viewModel.liveGetPokemonEvolution.test(2)) {//= with(liveGetPokemonEvolution) {

        //Given
        val pokemonName = "bulbasaur"
        Evolution("","",0)
        val response = Detail(
            abilities,
            location_area_encounters, moves, types, height, weight, id, pokemonName
        )

        //When
        runBlocking(Dispatchers.Main) {
            Mockito.`when`(repository.getPokemonDetail(pokemonName)).thenReturn(response)
        }
        //Then
        viewModel.getPokemonEvolution(pokemonName)
        awaitValues(2, TimeUnit.SECONDS)

        verify().forEach {
            when (it) {
                is Resource.Loading -> println("Loading State")
                is Resource.Success -> {
                    it.data
               /*     it.data?.abilities?.first()?.ability?.let {
                        assertEquals("bulbasaur",it.name)
                        assertEquals("www.detail.com",it.url)
                    }*/
                }
            }
        }
    }


}