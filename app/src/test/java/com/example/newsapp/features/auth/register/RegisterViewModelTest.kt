@file: Suppress("NoWildcardImports")

package com.example.newsapp.features.auth.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.newsapp.data.entities.UserDto
import com.example.newsapp.data.repos.UserRepo
import com.example.newsapp.util.TestCoroutineRule
import com.example.newsapp.util.getOrAwaitValue
import com.example.newsapp.utils.isValidEmailFormat
import com.example.newsapp.utils.toEntity
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import kotlin.test.assertIs

@OptIn(ExperimentalCoroutinesApi::class)
class RegisterViewModelTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var registerViewModel: RegisterViewModel

    @MockK
    private lateinit var userRepo: UserRepo

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        registerViewModel = RegisterViewModel(userRepo)
        // https://stackoverflow.com/questions/44382540/mocking-extension-function-in-kotlin
        mockkStatic("com.example.newsapp.utils.ExtentionsUtilKt")
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    //region register tests
    @Test
    fun `given empty name, when register is called, InvalidData EmptyName is returned`() {
        // GIVEN
        val userDto = createUserDto(name = "")
        every { any<String>().isValidEmailFormat() } returns true

        // WHEN
        registerViewModel.register(userDto)

        // THEN
        val result = registerViewModel.registrationResultLiveData.getOrAwaitValue()
        assertIs<RegisterResult.InvalidData>(result)
        assertIs<ErrorType.EmptyName>(result.error)
    }

    @Test
    fun `given empty email, when register is called, is InvalidData EmptyEmail returned`() {
        // GIVEN
        val userDto = createUserDto(email = "")
        every { any<String>().isValidEmailFormat() } returns true

        // WHEN
        registerViewModel.register(userDto)

        // THEN
        val result = registerViewModel.registrationResultLiveData.getOrAwaitValue()
        assertIs<RegisterResult.InvalidData>(result)
        assertIs<ErrorType.EmptyEmail>(result.error)
    }

    @Test
    fun `given invalid email, when register is called, is InvalidData InvalidEmailFormat returned`() {
        // GIVEN
        val userDto = createUserDto(email = "test invalid email")
        every { any<String>().isValidEmailFormat() } returns false

        // WHEN
        registerViewModel.register(userDto)

        // THEN
        val result = registerViewModel.registrationResultLiveData.getOrAwaitValue()
        assertIs<RegisterResult.InvalidData>(result)
        assertIs<ErrorType.InvalidEmailFormat>(result.error)
    }

    @Test
    fun `given empty password, when register is called, is InvalidData EmptyPassword returned`() {
        // GIVEN
        val userDto = createUserDto(password = "")
        every { any<String>().isValidEmailFormat() } returns true

        // WHEN
        registerViewModel.register(userDto)

        // THEN
        val result = registerViewModel.registrationResultLiveData.getOrAwaitValue()
        assertIs<RegisterResult.InvalidData>(result)
        assertIs<ErrorType.EmptyPassword>(result.error)
    }

    @Test
    fun `given invalid password, when register is called, is InvalidData InvalidPasswordFormat returned`() {
        // GIVEN
        val userDto = createUserDto(password = "123")
        every { any<String>().isValidEmailFormat() } returns true

        // WHEN
        registerViewModel.register(userDto)

        // THEN
        val result = registerViewModel.registrationResultLiveData.getOrAwaitValue()
        assertIs<RegisterResult.InvalidData>(result)
        assertIs<ErrorType.InvalidPasswordFormat>(result.error)
    }

    @Test
    fun `given empty phone number, when register is called, is InvalidData EmptyPhoneNumber returned`() {
        // GIVEN
        val userDto = createUserDto(phoneNumber = "")
        every { any<String>().isValidEmailFormat() } returns true

        // WHEN
        registerViewModel.register(userDto)

        // THEN
        val result = registerViewModel.registrationResultLiveData.getOrAwaitValue()
        assertIs<RegisterResult.InvalidData>(result)
        assertIs<ErrorType.EmptyPhoneNumber>(result.error)
    }

    @Test
    fun `given invalid phone number, when register is called, is InvalidData InvalidPhoneNumberFormat returned`() {
        // GIVEN
        val userDto = createUserDto(phoneNumber = "1234")
        every { any<String>().isValidEmailFormat() } returns true

        // WHEN
        registerViewModel.register(userDto)

        // THEN
        val result = registerViewModel.registrationResultLiveData.getOrAwaitValue()
        assertIs<RegisterResult.InvalidData>(result)
        assertIs<ErrorType.InvalidPhoneNumberFormat>(result.error)
    }

    @Test
    fun `given successful register, when register is called, is InvalidData InvalidPhoneNumberFormat returned`() =
        runTest {
            // GIVEN
            val userDto = createUserDto()
            every { any<String>().isValidEmailFormat() } returns true
            coEvery { userRepo.registerUser(any()) } returns true

            // WHEN
            registerViewModel.register(userDto)

            // THEN
            coVerify { userRepo.registerUser(userDto.toEntity()) }
            val result = registerViewModel.registrationResultLiveData.getOrAwaitValue()
            assertIs<RegisterResult.RegisterSuccessful>(result)
        }

    @Test
    fun `given failed register, when register is called is InvalidData InvalidPhoneNumberFormat returned`() =
        runTest {
            // GIVEN
            val userDto = createUserDto()
            every { any<String>().isValidEmailFormat() } returns true
            coEvery { userRepo.registerUser(any()) } returns false

            // WHEN
            registerViewModel.register(userDto)

            // THEN
            coVerify { userRepo.registerUser(userDto.toEntity()) }
            val result = registerViewModel.registrationResultLiveData.getOrAwaitValue()
            assertIs<RegisterResult.RegisterError>(result)
        }
    //endregion

    //region navigation tests
    @Test
    fun `when successfulRegistration is called, NavigateToHome is emitted`() {
        with(registerViewModel) {
            successfulRegistration()
            val result = registerNavigationLiveData.getOrAwaitValue().getContentIfNotHandled()

            assertIs<RegisterNavigation.NavigateToHome>(result)
        }
    }

    @Test
    fun `when navigateToLogin is called, NavigateToLogin is emitted`() {
        with(registerViewModel) {
            successfulRegistration()
            val result = registerNavigationLiveData.getOrAwaitValue().getContentIfNotHandled()

            assertIs<RegisterNavigation.NavigateToHome>(result)
        }
    }
    //endregion

    private fun createUserDto(
        email: String = "test@test.com",
        name: String = "testName",
        password: String = "123456789",
        phoneNumber: String = "01234567890"
    ): UserDto {
        return UserDto(email, name, password, phoneNumber)
    }
}
