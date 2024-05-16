package com.example.qrcodegenerator.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.qrcodegenerator.R
import com.example.qrcodegenerator.model.QRCodeScreen
import com.example.qrcodegenerator.ui.screens.HomeScreen
import com.example.qrcodegenerator.ui.screens.LoginScreen
import com.example.qrcodegenerator.ui.screens.QRCodeMainScreen
import com.example.qrcodegenerator.ui.screens.QRCodeParamsScreen
import com.example.qrcodegenerator.ui.screens.RegistrationScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QRCodeGeneratorApp(
    viewModel: QRCodeGeneratorViewModel = viewModel(factory = QRCodeGeneratorViewModel.Factory),
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = QRCodeScreen.valueOf(
        backStackEntry?.destination?.route ?: QRCodeScreen.HomeScreen.name
    )

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            QRCodeGeneratorTopAppBar(
                scrollBehavior = scrollBehavior,
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { paddingValues ->
        val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = QRCodeScreen.HomeScreen.name,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(route = QRCodeScreen.HomeScreen.name) {
                HomeScreen(
                    isLogged = uiState.isLogged,
                    onClickRegister = {
                        viewModel.resetRegistrationStatus()
                        viewModel.resetUsernameAndPassword()
                        navController.navigate(QRCodeScreen.RegistrationScreen.name)
                    },
                    onClickLogin = {
                        viewModel.resetRegistrationStatus()
                        viewModel.resetUsernameAndPassword()
                        navController.navigate(QRCodeScreen.LoginScreen.name)
                    },
                    onClickLogout = { viewModel.logout() },
                    onClickGenerate = { navController.navigate(QRCodeScreen.QRCodeParamsScreen.name) }
                )
            }

            composable(route = QRCodeScreen.RegistrationScreen.name) {
                RegistrationScreen(
                    inputUsername = viewModel.username,
                    inputPassword = viewModel.password,
                    onUsernameChange = { viewModel.updateUsername(it) },
                    onPasswordChange = { viewModel.updatePassword(it) },
                    onClickRegister = { viewModel.register() },
                    registrationStatus = uiState.registrationStatus,
                    onWrongRegistration = { viewModel.resetRegistrationStatus() },
                    onCompleteRegistration = {
                        viewModel.resetUsernameAndPassword()
                        viewModel.resetRegistrationStatus()
                        navController.navigateUp()
                    }
                )
            }

            composable(route = QRCodeScreen.LoginScreen.name) {
                LoginScreen(
                    inputUsername = viewModel.username,
                    inputPassword = viewModel.password,
                    onUsernameChange = { viewModel.updateUsername(it) },
                    onPasswordChange = { viewModel.updatePassword(it) },
                    onClickLogin = { viewModel.login() },
                    loginStatus = uiState.loginStatus,
                    onWrongLogin = { viewModel.resetLoginStatus() },
                    onCompleteLogin = {
                        viewModel.resetUsernameAndPassword()
                        viewModel.resetLoginStatus()
                        navController.navigateUp()
                    }
                )
            }

            composable(route = QRCodeScreen.QRCodeParamsScreen.name) {
                QRCodeParamsScreen(
                    onGenerateQRCode = {
                        viewModel.getQRCode()
                        navController.navigate(QRCodeScreen.QRCodeMainScreen.name)
                    },
                    encodedData = viewModel.encodedData,
                    onUpdateEncodedData = { viewModel.updateEncodedData(it) },
                    codeRed = viewModel.codeRed,
                    codeGreen = viewModel.codeGreen,
                    codeBlue = viewModel.codeBlue,
                    onUpdateRed = { viewModel.updateRed(it) },
                    onUpdateGreen = { viewModel.updateGreen(it) },
                    onUpdateBlue = { viewModel.updateBlue(it) },
                    textColor = viewModel.getQRCodeColor()
                )
            }

            composable(route = QRCodeScreen.QRCodeMainScreen.name) {
                QRCodeMainScreen(
                    isLoggedIn = uiState.isLogged,
                    fetchImageBitmapMethod = { viewModel.getQRCodeBitmap() },
                    generateCodeStatus = uiState.generateCodeStatus,
                    onSaveQRCode = { viewModel.saveQRCode() }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QRCodeGeneratorTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    currentScreen: QRCodeScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = stringResource(id = currentScreen.title),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
        },
        navigationIcon = {
            if (canNavigateBack) {
               IconButton(onClick = navigateUp) {
                   Icon(
                       imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                       contentDescription = stringResource(R.string.back_arrow)
                   )
               }
            }
        },
        modifier = modifier
    )
}
