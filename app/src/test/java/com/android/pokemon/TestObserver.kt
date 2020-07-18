package com.android.pokemon

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList



class TestObserver<T> private constructor(private val eventCount: Int?) : Observer<T> {
    private val valueHistory = ArrayList<T>()
    private val valueLatch: CountDownLatch = CountDownLatch(eventCount!!)

    companion object {

        fun <T> test(liveData: LiveData<T>, eventCount: Int?): TestObserver<T> {
            val observer = TestObserver<T>(eventCount)
            liveData.observeForever(observer)
            return observer
        }
    }

    override fun onChanged(value: T?) {
        valueHistory.add(value!!)
        valueLatch.countDown()

    }

    private fun value(): T {
        assertHasValue()
        return valueHistory[valueHistory.size - 1]

    }

    private fun valueHistory(): List<T> {
        return Collections.unmodifiableList(valueHistory)
    }

    private fun assertHasValue(): TestObserver<T> {
        if (valueHistory.isEmpty()) {
            throw fail("Observer never received any value")
        }

        return this
    }

    private fun assertNoValue(): TestObserver<T> {
        if (valueHistory.isNotEmpty()) {
            throw fail("Expected no value, but received: " + value())
        }
        return this
    }

    private fun assertHistorySize(expectedSize: Int): TestObserver<T> {
        val size = valueHistory.size
        if (size != expectedSize) {
            throw fail("History size differ; Expected: $expectedSize, Actual: $size")
        }
        return this
    }

    @Throws(InterruptedException::class)
    fun awaitValues(timeout: Int?, timeUnit: TimeUnit): TestObserver<T> {
        valueLatch.await(timeout!!.toLong(), timeUnit)
        return this
    }

    fun verify(): List<T> {
        assertHasValue()
        assertHistorySize(eventCount!!)
        return valueHistory()
    }

    private fun fail(message: String): AssertionError {
        return AssertionError(message)
    }

}