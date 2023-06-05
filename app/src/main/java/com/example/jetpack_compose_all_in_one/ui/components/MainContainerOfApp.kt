package com.example.jetpack_compose_all_in_one.ui.components

import android.location.Location
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
import com.example.jetpack_compose_all_in_one.features.dog_api.view.RandomDogUI
import com.example.jetpack_compose_all_in_one.features.download_manager.Download
import com.example.jetpack_compose_all_in_one.features.internet.InternetViewModel
import com.example.jetpack_compose_all_in_one.features.login_style_1.LoginPage
import com.example.jetpack_compose_all_in_one.features.login_style_1.LoginStyle1ViewModel
import com.example.jetpack_compose_all_in_one.features.login_style_2.LoginScreen2
import com.example.jetpack_compose_all_in_one.features.play_with_maps.ComposeDemoApp
import com.example.jetpack_compose_all_in_one.features.provideimages.ShowImages
import com.example.jetpack_compose_all_in_one.features.qrcodescanner.PreviewViewComposable
import com.example.jetpack_compose_all_in_one.features.random_dog_api.view.NextRandomDog
import com.example.jetpack_compose_all_in_one.features.random_fox.view.RandomFoxUI
import com.example.jetpack_compose_all_in_one.features.weather_sample.view.WeatherSample
import com.example.jetpack_compose_all_in_one.lessons.lesson_1.Lesson_1_Display
import com.example.jetpack_compose_all_in_one.lessons.lesson_2.Lesson_2_Chapter_2_Screen
import com.example.jetpack_compose_all_in_one.lessons.lesson_2.Lesson_2_Chapter_4_Image
import com.example.jetpack_compose_all_in_one.lessons.lesson_2.Lesson_2_Chapter_5_Progressbar
import com.example.jetpack_compose_all_in_one.lessons.lesson_2.Lesson_2_Chapter_6_FloatingActionButton
import com.example.jetpack_compose_all_in_one.lessons.lesson_2.Lesson_2_Chapter_Shape
import com.example.jetpack_compose_all_in_one.lessons.lesson_2.Lesson_2_Screen
import com.example.jetpack_compose_all_in_one.lessons.lesson_3.Lesson_3_Chapter_ListTypes
import com.example.jetpack_compose_all_in_one.lessons.lesson_4.Lesson_4_Chapter_Dialogs
import com.example.jetpack_compose_all_in_one.lessons.lesson_5.Lesson_5_Chapter_2_Map_Type
import com.example.jetpack_compose_all_in_one.lessons.lesson_5.Lesson_5_Chapter_3_CurrentLocation_On_Map
import com.example.jetpack_compose_all_in_one.lessons.lesson_5.Lesson_5_Chapter_4_Map_Search
import com.example.jetpack_compose_all_in_one.lessons.lesson_5.Lesson_5_Chapter_Map_Basic
import com.example.jetpack_compose_all_in_one.lessons.lesson_6.Lesson_6_ThemeChange
import com.example.jetpack_compose_all_in_one.lessons.lesson_7.Lesson_7_Chapter_ConstraintLayout
import com.example.jetpack_compose_all_in_one.lessons.lesson_8.Lesson_8_Animations
import com.example.jetpack_compose_all_in_one.ui.views.chat.DemoFullChat2
import com.example.jetpack_compose_all_in_one.ui.views.domain_search.DomainSearch
import com.example.jetpack_compose_all_in_one.ui.views.internet.InternetDemo
import com.example.jetpack_compose_all_in_one.ui.views.news_ui.list.LatestNewsPage
import com.example.jetpack_compose_all_in_one.ui.views.quote_swipe.QuoteSwipe
import com.example.jetpack_compose_all_in_one.ui.views.quotes_ui.Quote
import com.example.jetpack_compose_all_in_one.ui.views.tmdbapi.PopularMoviesPage
import com.example.jetpack_compose_all_in_one.utils.navigation.NavDes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContainerOfApp(
    internetViewModel: InternetViewModel,
    getCurrentLocationFunc: ((Location?) -> Unit) -> Unit,
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

    val customAppBarState = remember {
        derivedStateOf {
            NavDes.needCustomAppBar(currentRoute.value)
        }
    }
    val noSwipeState = remember {
        derivedStateOf {
            NavDes.disableDrawerSwiping(currentRoute.value)
        }
    }

    NavigationDrawerMain(navController, currentRoute, drawerState, noSwipeState,
        { scope.launch { drawerState.close() } }
    ) {
        Scaffold(
            topBar = {
                if (!customAppBarState.value) {
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
            snackbarHost = { SnackbarShow(snackbarHostState, internetViewModel.networkState) }
        ) {
            NavHost(navController, currentRoute.value.route(), Modifier.padding(it)) {
                composable(NavDes.Home.route()) {
                    Box {}
                }
                composable(NavDes.Internet.route()) {
                    InternetDemo()
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
                    val vm = hiltViewModel<LoginStyle1ViewModel>()

                    LoginPage(
                        drawerState,
                        loginStateHolder = vm.loginDetail,
                        onLogin = { vm.login() },
                        onRegister = { _, _ -> }
                    )
                }

                composable(NavDes.Login2.route()) {
                    // val vm = LoginStyle2ViewModel()
                    LoginScreen2()
                }

                composable(NavDes.QrCodeScanner.route()) {
                    PreviewViewComposable()
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
                    Lesson_1_Display()
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

                composable(NavDes.L2Chapter3.route()) {
                    Lesson_2_Chapter_2_Screen()
                }

                composable(NavDes.L2Chapter4.route()) {
                    Lesson_2_Chapter_4_Image()
                }

                composable(NavDes.L2Chapter5.route()) {
                    Lesson_2_Chapter_5_Progressbar()
                }

                composable(NavDes.L2Chapter6.route()) {
                    Lesson_2_Chapter_6_FloatingActionButton()
                }

                composable(NavDes.L3LIST.route()) {
                    Lesson_3_Chapter_ListTypes()
                }

                composable(NavDes.L7Chapter1.route()) {
                    Lesson_7_Chapter_ConstraintLayout()
                }
                composable(NavDes.Lesson8Animation.route()) {
                    Lesson_8_Animations()
                }

                composable(NavDes.L6Chapter1.route()) {
                    Lesson_6_ThemeChange()
                }

                composable(NavDes.L4Chapter1.route()) {
                    Lesson_4_Chapter_Dialogs()
                }

                composable(NavDes.NewsSample.route()) {
                    LatestNewsPage(
                        searchViewModel = hiltViewModel(),
                        viewModel = hiltViewModel()
                    )
                }
                composable(NavDes.WeatherSample.route()) {
                    WeatherSample()
                }

                composable(NavDes.DomainSearch.route()) {
                    DomainSearch(vm = hiltViewModel())
                }

                composable(NavDes.DogApi.route()) {
                    RandomDogUI(hiltViewModel())
                }

                composable(NavDes.RandomDogApi.route()) {
                    NextRandomDog()
                }

                composable(NavDes.RandomFox.route()) {
                    RandomFoxUI()
                }

                composable(NavDes.Maps.route()) {
                    ComposeDemoApp(hiltViewModel(), getCurrentLocationFunc)
                }

                composable(NavDes.L5Chapter1.route()) {
                    Lesson_5_Chapter_Map_Basic()
                }

                composable(NavDes.L5Chapter2.route()) {
                    Lesson_5_Chapter_2_Map_Type()
                }

                composable(NavDes.L5Chapter3.route()) {
                    Lesson_5_Chapter_3_CurrentLocation_On_Map(getCurrentLocationFunc)
                }

                composable(NavDes.L5Chapter4.route()) {
                    Lesson_5_Chapter_4_Map_Search(hiltViewModel())
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