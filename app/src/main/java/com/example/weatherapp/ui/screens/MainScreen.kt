package com.example.weatherapp.ui.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import coil.compose.rememberAsyncImagePainter
import com.example.weatherapp.R
import com.example.weatherapp.ui.components.loaders.WeatherLoader
import com.example.weatherapp.ui.components.textFields.SearchField
import com.example.weatherapp.ui.components.texts.ScreenBody
import com.example.weatherapp.ui.components.texts.ScreenSubTitle
import com.example.weatherapp.ui.components.texts.ScreenTitle
import com.example.weatherapp.ui.theme.*
import com.example.weatherapp.utils.ErrorResult
import com.example.weatherapp.utils.LoadingResult
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import java.util.*

@Composable
fun MainScreen(
    viewModel: MainViewModel
) {
    val currentWeather by viewModel.currentWeather.collectAsState()
    val hoursWeather by viewModel.hoursList.collectAsState()
    val daysWeather by viewModel.daysList.collectAsState()
    val cityPrefs = viewModel.cityPrefs.collectAsState()

    val isGetCurrent = remember { mutableStateOf(false) }
    val textState = remember { mutableStateOf(TextFieldValue("")) }

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    val isLoading = currentWeather is LoadingResult || hoursWeather is LoadingResult ||
            daysWeather is LoadingResult || currentWeather is ErrorResult ||
            hoursWeather is ErrorResult || daysWeather is ErrorResult

    fun changeIsGetCurrent(flag: Boolean) {
        isGetCurrent.value = flag
    }

    if (isGetCurrent.value) {
        GetCurrentCityInfo(
            context,
            viewModel::loadData,
            ::changeIsGetCurrent
        )
    }

    LaunchedEffect(Unit) {
        // We store the last city locally, so we load by default
        if (cityPrefs.value.isNotEmpty()) {
            viewModel.loadData()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
            .background(Blue50)
    ) {

        SearchField(
            textState = textState,
            getCurrentCityInfo = {
                focusManager.clearFocus()
                changeIsGetCurrent(true)
                textState.value = TextFieldValue("")
            },
            getSearchedCityInfo = {
                focusManager.clearFocus()
                if (textState.value.text.isNotEmpty()) {
                    viewModel.loadData(textState.value.text)
                    changeIsGetCurrent(false)
                    textState.value = TextFieldValue("")
                }
            }
        )

        if (cityPrefs.value.isNotEmpty()) {
            SwipeRefresh(
                state = rememberSwipeRefreshState(isGetCurrent.value),
                onRefresh = {
                    viewModel.loadData()
                }
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 20.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .padding(vertical = 15.dp)
                            .clip(Shapes.medium)
                            .background(Blue100)
                            .padding(vertical = 10.dp)
                    ) {
                        ScreenBody(
                            text = currentWeather.data?.city ?: stringResource(R.string.wait)
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                ScreenTitle(
                                    text = "${currentWeather.data?.currentTemp} °C"
                                )
                                ScreenSubTitle(
                                    text = currentWeather.data?.conditionText ?: stringResource(R.string.wait)
                                )
                            }
                            Image(
                                painter = rememberAsyncImagePainter("https:${currentWeather.data?.conditionIcon}"),
                                contentDescription = null,
                                modifier = Modifier.size(100.dp)
                            )
                        }
                        Text(
                            text = "${currentWeather.data?.minTemp} °C | ${currentWeather.data?.maxTemp} °C"
                        )

                        Box(
                            modifier = Modifier
                                .padding(vertical = 15.dp)
                                .fillMaxWidth()
                                .height(2.dp)
                                .background(Color.White)
                        )

                        // 2022-01-01 13:30 -> 13
                        val currHour = currentWeather.data?.time
                            ?.split(' ')?.get(1)
                            ?.split(':')?.get(0)?.toInt() ?: 0

                        LazyRow(
                            state = rememberLazyListState()
                        ) {
                            items(hoursWeather.data?.size ?: 0) { item ->
                                if (item >= currHour) {
                                    Column(
                                        modifier = Modifier
                                            .padding(horizontal = 5.dp)
                                            .padding(5.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            // 2022-01-01 13:30 -> 13:30
                                            text = hoursWeather.data?.get(item)?.time
                                                ?.split(' ')?.get(1) ?: stringResource(R.string.wait)
                                        )
                                        Image(
                                            painter = rememberAsyncImagePainter(
                                                "https:${
                                                    hoursWeather.data?.get(
                                                        item
                                                    )?.conditionIcon
                                                }"
                                            ),
                                            contentDescription = null,
                                            modifier = Modifier.size(60.dp)
                                        )
                                        Text(
                                            text = "${hoursWeather.data?.get(item)?.currentTemp} °C"
                                        )
                                    }
                                }
                            }
                        }
                    }
                    ScreenBody(
                        text = stringResource(R.string.week_forecast)
                    )
                    Column(
                        modifier = Modifier
                            .padding(bottom = 15.dp)
                            .clip(Shapes.medium)
                            .background(Blue100)
                            .padding(vertical = 10.dp)
                    ) {
                        daysWeather.data?.forEach { item ->

                            // 2022-01-01 13:30 -> ['2022', '01', '01']
                            val daysDate = item.time
                                .split(' ')[0].split('-')

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    modifier = Modifier.weight(2f),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "${daysDate[2]}.${daysDate[1]}"
                                    )
                                    ScreenSubTitle(
                                        text = item.conditionText
                                    )
                                }
                                Row(
                                    modifier = Modifier.weight(3f),
                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "${item.minTemp} ℃ | ${item.maxTemp} ℃"
                                    )
                                    Image(
                                        painter = rememberAsyncImagePainter(
                                            "https:${
                                                item.conditionIcon
                                            }"
                                        ),
                                        contentDescription = null,
                                        modifier = Modifier.size(60.dp)
                                    )
                                }
                            }

                            if (item != daysWeather.data?.last()) {
                                Box(
                                    modifier = Modifier
                                        .padding(vertical = 15.dp)
                                        .fillMaxWidth()
                                        .height(5.dp)
                                        .background(Color.White)
                                )

                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(5.dp))
                }
            }
        } else { // If the user opened the app for the first time
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 30.dp)
            ) {
                ScreenBody(
                    text = stringResource(R.string.welcome)
                )
            }
        }
    }

    if (isLoading || (currentWeather.data?.city == null && cityPrefs.value.isNotEmpty())) {
        WeatherLoader()
    }
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun GetCurrentCityInfo(
    context: Context,
    loadData: (String) -> Unit,
    changeIsGetCurrent: (Boolean) -> Unit
) {
    val openDialog = remember { mutableStateOf(false)  } // When to show permission dialog
    val onDismiss = remember { mutableStateOf(false)  } // If the user pressed dismiss the dialog should disappear

    val locationPermissionState = rememberPermissionState(
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    when (locationPermissionState.status) {
        PermissionStatus.Granted -> {
            openDialog.value = false

            val fusedLocationProviderClient: FusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(
                    context
                )

            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }

            fusedLocationProviderClient.getCurrentLocation(
                LocationRequest.PRIORITY_HIGH_ACCURACY,
                object : CancellationToken() {
                    override fun onCanceledRequested(p0: OnTokenCanceledListener) =
                        CancellationTokenSource().token

                override fun isCancellationRequested() = false
            })
                .addOnSuccessListener { location ->

                    if (location == null)
                        Toast.makeText(context, "Невозможно получить местоположение.", Toast.LENGTH_SHORT).show()
                    else {
                        val aLocale = Locale("en", "EN")

                        val geoCoder = Geocoder(context, aLocale)
                        val locationLat: Double = location.latitude
                        val locationLon: Double = location.longitude
                        val currentLocation = geoCoder.getFromLocation(
                            locationLat,
                            locationLon,
                            1
                        )
                        val city = currentLocation?.first()?.locality.toString() // Get name of the city from coords

                        loadData(city)
                        changeIsGetCurrent(false)
                    }
                }
        }
        is PermissionStatus.Denied -> {
            if (!onDismiss.value) {
                openDialog.value = true
            }
        }
    }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                changeIsGetCurrent(false)
                openDialog.value = false
                onDismiss.value = true
            },
            title = {
                Text(stringResource(R.string.permission_title))
            },
            text = {
                Text(
                    text = stringResource(R.string.permission_body)
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        locationPermissionState.launchPermissionRequest()
                        openDialog.value = false
                    }) {
                    Text(
                        text = stringResource(R.string.permission_confirm),
                        style = Typography.button
                    )
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        changeIsGetCurrent(false)
                        openDialog.value = false
                        onDismiss.value = true
                    }) {
                    Text(
                        text = stringResource(R.string.permission_dismiss),
                        style = Typography.button
                    )
                }
            }
        )
    }
}