package com.android.pokemon.domain.interactor

import com.android.pokemon.domain.IDataRepository
import com.android.pokemon.domain.entity.GetPokemonRequest
import com.android.pokemon.domain.entity.evolution.Chain
import com.android.pokemon.domain.entity.evolution.Evolution
import com.android.pokemon.domain.entity.evolution.EvolvesTo
import com.android.pokemon.domain.entity.evolution.Species
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class GetPokemonEvolutionUserCaseTest {

    @Mock
    lateinit var repository: IDataRepository
    private lateinit var userCase: GetPokemonEvolutionUserCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        userCase = GetPokemonEvolutionUserCase(repository = repository)
    }

    @Test
    fun prepareExecuteOnBackground() {
        //Given
        val pokemonName = "bulbasaur"
        val urlValue = "www.test.cl"

        val species = Species(pokemonName, urlValue)
        val evolvesTo = EvolvesTo(species)

        val evolvesToArrayList = ArrayList<EvolvesTo>()
        evolvesToArrayList.add(evolvesTo)

        val chain = Chain(evolvesToArrayList)
        val response = Evolution(chain, 0)

        //When
        runBlocking(context = Dispatchers.Unconfined) {
            Mockito.`when`(repository.getPokemonEvolution(pokemonName)).thenReturn(response)
        }

        // then
        runBlocking(Dispatchers.Unconfined) {
            val result = userCase.prepareExecuteOnBackground(pokemonName)
            assertEquals("bulbasaur", result?.chain?.evolves_to?.first()?.species?.name)

        }
    }
}