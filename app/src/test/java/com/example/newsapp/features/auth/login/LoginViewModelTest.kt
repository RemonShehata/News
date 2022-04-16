package com.example.newsapp.features.auth.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.newsapp.data.repos.UserRepository
import com.example.newsapp.util.TestCoroutineRule
import com.example.newsapp.util.getOrAwaitValue
import com.example.newsapp.utils.isValidEmailFormat
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkStatic
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import kotlin.test.assertIs

class LoginViewModelTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var loginViewModel: LoginViewModel

    @MockK
    private lateinit var userRepo: UserRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        loginViewModel = LoginViewModel(userRepo)
        mockkStatic("com.example.newsapp.utils.ExtentionsUtilKt")
    }

    @Test
    fun `given empty email and password, when login is called, EmptyEmail is returned`() {
        // GIVEN
        val email = ""
        val password = ""

        // WHEN
        loginViewModel.login(email, password)

        // THEN
        val result = loginViewModel.loginResultLiveData.getOrAwaitValue()

        assertIs<LoginResult.InvalidData>(result)
        assertIs<ErrorType.EmptyEmail>(result.error)
    }

    @Test
    fun `given invalid email and empty password, when login is called, InvalidEmailFormat is returned`() {
        // GIVEN
        val email = "test"
        val password = ""
        every { any<String>().isValidEmailFormat() } returns false

        // WHEN
        loginViewModel.login(email, password)

        // THEN
        val result = loginViewModel.loginResultLiveData.getOrAwaitValue()
        assertIs<LoginResult.InvalidData>(result)
        assertIs<ErrorType.InvalidEmailFormat>(result.error)
    }

    @Test
    fun `given valid email and empty password, when login is called, EmptyPassword is returned`() {
        // GIVEN
        val email = "test@test.com"
        val password = ""
        every { any<String>().isValidEmailFormat() } returns true

        // WHEN
        loginViewModel.login(email, password)

        // THEN
        val result = loginViewModel.loginResultLiveData.getOrAwaitValue()

        assertIs<LoginResult.InvalidData>(result)
        assertIs<ErrorType.EmptyPassword>(result.error)
    }
}
