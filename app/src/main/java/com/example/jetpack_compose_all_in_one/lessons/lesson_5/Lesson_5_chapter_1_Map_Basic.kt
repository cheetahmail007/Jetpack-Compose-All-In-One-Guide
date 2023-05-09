package com.example.jetpack_compose_all_in_one.lessons.lesson_5

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack_compose_all_in_one.R
import com.example.jetpack_compose_all_in_one.ui.components.LessonHeader
import com.example.jetpack_compose_all_in_one.ui.theme.dp_100
import com.example.jetpack_compose_all_in_one.ui.theme.dp_50
import com.example.jetpack_compose_all_in_one.utils.Constants
import com.example.jetpack_compose_all_in_one.utils.LogicPager
import com.example.jetpack_compose_all_in_one.utils.bitmapDescriptorFromRes
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polygon
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState

@Preview()
@Composable
fun Lesson_5_Chapter_Map_Basic() {
    LessonContent()
}

@Composable
private fun LessonContent() {

    val ctx = LocalContext.current

    val cameraPositionState = rememberCameraPositionState{
        position = CameraPosition.fromLatLngZoom(Constants.MAP_POS_BIGBEN, 18.54f)
    }
    val currentPage = rememberSaveable{ mutableStateOf(0) }
    val lessonHeaderText by remember{ derivedStateOf {
        when (currentPage.value) {
            1 -> "Inflate Marker On Map"
            2 -> "Customize Marker On Map"
            3 -> "Create PolyLine on Map"
            4 -> "Create Polygon on Map"
            5 -> "Create Circle on Map"
            else -> "Simple Map"
        }
    } }

    var mapType by remember{ mutableStateOf(MapType.NORMAL) }

    LogicPager(
        pages = listOf(
            {},
            {},
            {},
            {},
            {},
            {}
        ),
        currentPage = currentPage
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            LessonHeader(
                lessonHeaderText,
                Modifier.fillMaxWidth().padding(16.dp),
                TextAlign.Center
            )

            GoogleMap(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                cameraPositionState = cameraPositionState,
                properties = MapProperties(mapType = mapType)
            ) {
                when (currentPage.value) {
                    0 -> {}
                    1 -> {
                        Marker(
                            state = MarkerState(position = Constants.MAP_POS_BIGBEN),
                            title = "Regular marker",
                            snippet = "This is a regular marker."
                        )
                    }
                    2 -> {
                        Marker(
                            state = MarkerState(position = Constants.MAP_POS_BIGBEN),
                            title = "Custom Marker",
                            snippet = "This is a marker with custom bitmap.",
                            icon = bitmapDescriptorFromRes(ctx, R.drawable.cannon, dp_100, dp_50)
                        )
                    }
                    3 -> {
                        Polyline(
                            points = listOf(
                                LatLng(
                                    Constants.MAP_POS_BIGBEN.latitude + 0.0002,
                                    Constants.MAP_POS_BIGBEN.longitude + 0.0003
                                ),
                                LatLng(
                                    Constants.MAP_POS_BIGBEN.latitude - 0.0002,
                                    Constants.MAP_POS_BIGBEN.longitude - 0.0003
                                )
                            )
                        )
                        Polyline(
                            points = listOf(
                                LatLng(
                                    Constants.MAP_POS_BIGBEN.latitude + 0.0002,
                                    Constants.MAP_POS_BIGBEN.longitude - 0.0003
                                ),
                                LatLng(
                                    Constants.MAP_POS_BIGBEN.latitude - 0.0002,
                                    Constants.MAP_POS_BIGBEN.longitude + 0.0003
                                )
                            )
                        )
                    }
                    4 -> {
                        Polygon(
                            points = listOf(
                                LatLng(
                                    Constants.MAP_POS_BIGBEN.latitude + 0.0002,
                                    Constants.MAP_POS_BIGBEN.longitude
                                ),
                                LatLng(
                                    Constants.MAP_POS_BIGBEN.latitude - 0.0002,
                                    Constants.MAP_POS_BIGBEN.longitude - 0.0003
                                ),
                                LatLng(
                                    Constants.MAP_POS_BIGBEN.latitude - 0.0002,
                                    Constants.MAP_POS_BIGBEN.longitude + 0.0003
                                )
                            ),
                            fillColor = Color.Transparent,
                            strokeColor = Color.Black,
                            zIndex = 1.0f
                        )
                    }
                    5 -> {
                        Circle(
                            center = Constants.MAP_POS_BIGBEN,
                            radius = 10.0
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MapSimpleUI() {

}

@Composable
fun InflateMarkerOnMap() {

}

@Composable
fun CustomMarkerOnMap() {

}


@Composable
fun CreatePolyLineOnMap() {

}

@Composable
fun CreatePolygonOnMap() {

}

@Composable
fun CreateCircleOnMap() {

}