package com.example.newsapp.data.repos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.newsapp.data.entities.UserEntity
import com.example.newsapp.data.room.UserDao
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class UserRepositoryTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var userDao: UserDao

    private lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        userRepository = UserRepository(userDao)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `given userDao, when login returns true, then repo returns true`() = runTest {

        // GIVEN
        val email = "test@test.com"
        val password = "testpassword"
        coEvery { userDao.login(email, password) } returns true

        // WHEN
        val result = userRepository.login(email, password)

        // THEN
        coVerify { userDao.login(email, password) }
        assertTrue(result)
    }

    @Test
    fun `given userDao, when login returns false, then repo returns false`() = runTest {

        // GIVEN
        val email = "test@test.com"
        val password = "testpassword"
        coEvery { userDao.login(email, password) } returns false

        // WHEN
        val result = userRepository.login(email, password)

        // THEN
        coVerify { userDao.login(email, password) }
        assertFalse(result)
    }

    @Test
    fun `given userDao, when register returns gt 0, then repo returns true`() = runTest {

        // GIVEN
        val user = createUserEntity()
        coEvery { userDao.insertUser(user) } returns 1

        // WHEN
        val result = userRepository.registerUser(user)

        // THEN
        coVerify { userDao.insertUser(user) }
        assertTrue(result)
    }

    @Test
    fun `given userDao, when register returns lt 0, then repo returns false`() = runTest {

        // GIVEN
        val user = createUserEntity()
        coEvery { userDao.insertUser(user) } returns -1

        // WHEN
        val result = userRepository.registerUser(user)

        // THEN
        coVerify { userDao.insertUser(user) }
        assertFalse(result)
    }

    private fun createUserEntity(
        email: String = "test@test.com",
        name: String = "testName",
        password: String = "123456789",
        phoneNumber: String = "01234567890"
    ): UserEntity {
        return UserEntity(email, name, password, phoneNumber)
    }
}
