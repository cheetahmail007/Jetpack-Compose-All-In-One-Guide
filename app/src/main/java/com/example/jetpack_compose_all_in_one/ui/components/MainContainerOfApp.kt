package com.example.jetpack_compose_all_in_one.ui.components

import android.media.RingtoneManager
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetpack_compose_all_in_one.*
import com.example.jetpack_compose_all_in_one.R
import com.example.jetpack_compose_all_in_one.features.alarm.AlarmMainUI
import com.example.jetpack_compose_all_in_one.features.chatmodule.ChatViewModel
import com.example.jetpack_compose_all_in_one.features.download_manager.Download
import com.example.jetpack_compose_all_in_one.features.login_style_1.LoginPage
import com.example.jetpack_compose_all_in_one.features.login_style_2.LoginScreen2
import com.example.jetpack_compose_all_in_one.features.login_style_2.LoginStyle2ViewModel
import com.example.jetpack_compose_all_in_one.features.news_sample.NewsSample
import com.example.jetpack_compose_all_in_one.features.provideimages.ShowImages
import com.example.jetpack_compose_all_in_one.features.weather_sample.WeatherSample
import com.example.jetpack_compose_all_in_one.lessons.lesson_2.Lesson_2_Chapter_Shape
import com.example.jetpack_compose_all_in_one.lessons.lesson_2.Lesson_2_Screen
import com.example.jetpack_compose_all_in_one.ui.views.chat.DemoFullChat2
import com.example.jetpack_compose_all_in_one.ui.views.lessons.ComposeLayouts
import com.example.jetpack_compose_all_in_one.ui.views.quote_swipe.QuoteSwipe
import com.example.jetpack_compose_all_in_one.ui.views.tmdbapi.PopularMoviesPage
import com.example.jetpack_compose_all_in_one.utils.navigation.NavDes
import com.example.jetpack_compose_all_in_one.view.Quote
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContainerOfApp(
    isOffline: Boolean = false,
    playMusicFuncForeground: (Uri) -> Unit,
    stopMusicFuncForeground: () -> Unit,
    playMusicFuncBound: (Uri) -> Unit,
    stopMusicFuncBound: () -> Unit,
    pauseMusicFuncBound: (Long) -> Unit,
    resumeMusicFuncBound: () -> Unit,
    downloadFileObj: Download,
    setDownloadUrlFunc: (String) -> Unit
) {
    var inputUrl by remember { mutableStateOf("") }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val currentRoute: MutableState<NavDes> = remember { mutableStateOf(NavDes.startDestination) }
    val snackbarHostState = remember { SnackbarHostState() }

    NavigationDrawerMain(navController, currentRoute, drawerState,
        { scope.launch { drawerState.close() } }
    ) {
        Scaffold(
            topBar = {
                if (!NavDes.needCustomAppBar(currentRoute.value)) {
                    TopAppBar(
                        title = {
                            Text(
                                currentRoute.value.customAppBarStringId?.run {
                                    stringResource(id = this)
                                } ?: currentRoute.value.displayText()
                            )
                        },
                        navigationIcon = { DrawerButton(drawerState, scope) }
                    )
                }
            },
            snackbarHost = { SnackbarShow(snackbarHostState, isOffline) }
        ) {
            NavHost(navController, currentRoute.value.route(), Modifier.padding(it)) {
                composable(NavDes.Home.route()) {
                    Box {}
                }
                composable(NavDes.Internet.route()) {
                    if (isOffline) {
                        NetworkErrorDialog()
                    } else {
                        Text("Internet available")
                    }
                }
                composable(NavDes.ForegroundService.route()) {
                    Box(
                        Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Row {
                            SimpleIconButton(R.drawable.baseline_play_circle_outline_24) {
                                playMusicFuncForeground(
                                    RingtoneManager.getDefaultUri(
                                        RingtoneManager.TYPE_RINGTONE
                                    )
                                )
                            }
                            SimpleIconButton(R.drawable.outline_stop_circle_24) {
                                stopMusicFuncForeground()
                            }
                        }
                    }
                }
                composable(NavDes.BoundService.route()) {
                    Column(
                        Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        LazyVerticalGrid(
                            GridCells.Fixed(2),
                            Modifier
                                .background(Color(0.314f, 0.29f, 0.29f, 0.725f))
                                .padding(16.dp)
                        ) {
                            item {
                                SimpleTextButton("Start") {
                                    playMusicFuncBound(
                                        RingtoneManager.getDefaultUri(
                                            RingtoneManager.TYPE_RINGTONE
                                        )
                                    )
                                }
                            }
                            item {
                                SimpleTextButton("Stop") { stopMusicFuncBound() }
                            }
                            item {
                                SimpleTextButton("Pause (10s)") { pauseMusicFuncBound(10000L) }
                            }
                            item {
                                SimpleTextButton("Resume") { resumeMusicFuncBound() }
                            }
                        }
                    }
                }
                composable(NavDes.Download.route()) {
                    Column(
                        Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        TextField(
                            inputUrl,
                            onValueChange = { url ->
                                inputUrl = url
                                setDownloadUrlFunc(url)
                            },
                            placeholder = { Text("Enter URL here.") })
                        SimpleTextButton(buttonMessage = "Download") { downloadFileObj.start() }
                    }
                }
                composable(NavDes.AlarmManager.route()) {
                    AlarmMainUI { msg, lng ->
                        scope.launch {
                            snackbarHostState.showText(msg, lng)
                        }
                    }
                }

                composable(NavDes.Login1.route()) {
                    LoginPage(
                        drawerState,
                        onLogin = { _, _, _ -> },
                        onRegister = { _, _ -> }
                    )
                }

                composable(NavDes.Login2.route()) {
                    val vm = LoginStyle2ViewModel()
                    LoginScreen2(vm)
                }

                composable(NavDes.Tmdb.route()) {
                    PopularMoviesPage(hiltViewModel())
                }

                composable(NavDes.Quotes.route()) {
                    Quote()
                }
                composable(NavDes.ChatDemoUI.route()) {
                    val vm = hiltViewModel<ChatViewModel>()
                    DemoFullChat2(
                        vm.chatHistory.toList()
                    ) { data -> vm.sendMessage(data) }
                }

                composable(NavDes.ShowImages.route()) {
                    ShowImages(7)
                }

                composable(NavDes.L1Layouts.route()) {
                    ComposeLayouts()
                }

                composable(NavDes.QuoteSwipe.route()) {
                    QuoteSwipe(vm = hiltViewModel())
                }

                composable(NavDes.L2Chapter1.route()) {
                    Lesson_2_Chapter_Shape()
                }
                composable(NavDes.L2Chapter2.route()) {
                    Lesson_2_Screen()
                }

                composable(NavDes.NewsSample.route()) {
                    NewsSample()
                }
                composable(NavDes.WeatherSample.route()) {
                    WeatherSample()
                }
            }
        }
    }
}


@Composable
fun DrawerButton(
    drawerState: DrawerState,
    coroutineScope: CoroutineScope
) {
    SimpleIconButton(R.drawable.baseline_menu_24) {
        with(drawerState) {
            if (!isAnimationRunning) coroutineScope.launch { open() }
        }
    }
}

suspend fun SnackbarHostState.showText(message: String, isLong: Boolean) =
    showSnackbar(
        message,
        null,
        false,
        if (isLong) SnackbarDuration.Long else SnackbarDuration.Short
    )