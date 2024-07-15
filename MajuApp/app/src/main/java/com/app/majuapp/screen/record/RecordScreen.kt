package com.app.majuapp.screen.record

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.majuapp.R
import com.app.majuapp.component.culture.CultureCard
import com.app.majuapp.component.fillMaxWidthSpacer
import com.app.majuapp.component.home.GrayBorderRoundedCard
import com.app.majuapp.component.record.CalendarWidget
import com.app.majuapp.component.record.RecordScreenCalendarColorInform
import com.app.majuapp.component.record.RecordScreenIconTextTitle
import com.app.majuapp.component.walk.WalkRecordingBox
import com.app.majuapp.domain.model.CultureLifeRecord
import com.app.majuapp.domain.model.RecordingDataModel
import com.app.majuapp.domain.model.RecordingWalkRecord
import com.app.majuapp.domain.model.walk.WalkDateHistoryDomainModel
import com.app.majuapp.ui.theme.BrightGray
import com.app.majuapp.ui.theme.GoldenPoppy
import com.app.majuapp.ui.theme.MajuAppTheme
import com.app.majuapp.ui.theme.SonicSilver
import com.app.majuapp.ui.theme.SpiroDiscoBall
import com.app.majuapp.ui.theme.White
import com.app.majuapp.ui.theme.defaultPadding
import com.app.majuapp.ui.theme.notoSansKoreanFontFamily
import com.app.majuapp.ui.theme.roundedCornerPadding
import com.app.majuapp.util.DateUtil
import com.app.majuapp.util.NetworkResult
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "RecordScreen_창영"

@Composable
fun RecordScreen(navController: NavController) {
    RecordScreenContent(navController, recordingScreenDummyData)
} // End of RecordScreen()


@Composable
fun RecordScreenContent(
    navController: NavController,
    recordingData: RecordingDataModel,
    recordViewModel: RecordViewModel = hiltViewModel(),
    calendarViewModel: RecordCalendarViewModel = hiltViewModel()
) {
    // Context
    val context = LocalContext.current

    // modifier
    val modifier = Modifier

    // Snackbar State
    val coroutineScope = rememberCoroutineScope()

    val cultureLikeDateEvents =
        calendarViewModel.cultureLikeDateEvents.collectAsStateWithLifecycle()

    val walkingHistoryDateEvents =
        calendarViewModel.walkingHistoryDateEvents.collectAsStateWithLifecycle()

    val culturePagerState = rememberPagerState(pageCount = {
        cultureLikeDateEvents.value.size
    })


    Surface(
        modifier = modifier.fillMaxSize(), color = White,
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                Column(
                    modifier = modifier.padding(
                        start = 30.dp,
                        end = 30.dp,
                        top = 30.dp,
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            context.getString(R.string.record_screen_recording_title),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = notoSansKoreanFontFamily
                        )
                        IconButton(modifier = Modifier, onClick = {}) {
                            Icon(
                                painter = painterResource(R.drawable.ic_default_user),
                                tint = SonicSilver,
                                contentDescription = null
                            )
                        }
                    }
                }
                RecordCalendar(recordViewModel = recordViewModel) // 달력
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(start = defaultPadding + 4.dp, top = defaultPadding / 2),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    RecordScreenCalendarColorInform(
                        text = context.getString(R.string.record_screen_walk_recording_content),
                        color = SpiroDiscoBall
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    RecordScreenCalendarColorInform(
                        text = context.getString(R.string.record_screen_culture_life_content),
                        color = GoldenPoppy
                    )
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = defaultPadding, bottom = defaultPadding)
                        .height(4.dp)
                        .background(BrightGray)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 30.dp, end = 30.dp)
                ) {
                    RecordScreenIconTextTitle(
                        painterResource(R.drawable.ic_walk_record_check),
                        SpiroDiscoBall,
                        context.getString(R.string.record_screen_walk_recording_content)
                    )

                    if (walkingHistoryDateEvents.value.isEmpty())
                        Text(
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            text = stringResource(id = R.string.record_screen_no_activity),
                            color = SonicSilver,
                            textAlign = TextAlign.Center,
                        )

                    LazyRow(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.spacedBy(28.dp)
                    ) {
                        // 산책 기록 LazyRow()
                        items(walkingHistoryDateEvents.value) { item ->
                            RecordScreenLazyItems(
                                modifier = Modifier.fillParentMaxWidth(),
                                walkingHistoryDateEvents = item
                            ) {}
                        }
                    }
                    fillMaxWidthSpacer(Modifier, 20.dp)
                    RecordScreenIconTextTitle(
                        painterResource(R.drawable.ic_culture_life_check),
                        GoldenPoppy,
                        context.getString(R.string.record_screen_culture_life_content)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    // CultureLife Pager
                    IconButton(modifier = Modifier.weight(1f), onClick = {
                        coroutineScope.launch {
                            if (culturePagerState.currentPage > 0) {
                                culturePagerState.animateScrollToPage(culturePagerState.currentPage - 1)
                            }
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowLeft, contentDescription = null
                        )
                    }

                    if (culturePagerState.pageCount < 1)
                        Text(
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            text = stringResource(id = R.string.record_screen_no_activity),
                            color = SonicSilver,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = defaultPadding)
                        )

                    HorizontalPager(
                        modifier = Modifier.weight(8f),
                        state = culturePagerState,
                        contentPadding = PaddingValues()
                    ) { page ->
                        CultureCard(
                            culture = cultureLikeDateEvents.value.get(page),
                            favoriteButtonFlag = false
                        )
                    }
                    IconButton(modifier = Modifier.weight(1f), onClick = {
                        coroutineScope.launch {
                            if (culturePagerState.currentPage < culturePagerState.pageCount - 1) {
                                culturePagerState.animateScrollToPage(culturePagerState.currentPage + 1)
                            }
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowRight, contentDescription = null
                        )
                    }
                }


            }
        } // End of LazyColumn()
    } // End of Surface()
} // End of RecordScreenContent()

@Composable
private fun RecordScreenLazyItems(
    modifier: Modifier,
    walkingHistoryDateEvents: WalkDateHistoryDomainModel,
    composableContent: @Composable () -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .wrapContentHeight()
    ) {
        GrayBorderRoundedCard(
            // 홈 화면 알림 카드
            modifier = Modifier.border(
                width = 2.dp, color = BrightGray, shape = RoundedCornerShape(
                    roundedCornerPadding
                )
            ),
            color = arrayListOf(Color.Transparent, Color.Transparent),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 34.dp, end = 34.dp, top = 14.dp, bottom = 14.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                WalkRecordingBox(
                    context,
                    walkingHistoryDateEvents
                )
            }
        }
        composableContent() // 내부 컴포저블 함수
    }
} // End of RecordScreenLazyItems()


@Composable
private fun RecordCalendar(
    calendarViewModel: RecordCalendarViewModel = hiltViewModel(), recordViewModel: RecordViewModel
) {
    val calendarUiState by calendarViewModel.calendarUiState.collectAsStateWithLifecycle() // 달력 날짜 전체 데이터
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val monthEvents = calendarViewModel.monthEvents.collectAsStateWithLifecycle()
    val walkingHistoryMonthEvents =
        calendarViewModel.walkingHistoryMonthEventsNetworkResult.collectAsStateWithLifecycle()
    val cultureLikeMonthEvents =
        calendarViewModel.cultureLikeMonthEventsNetworkResult.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = cultureLikeMonthEvents.value, key2 = walkingHistoryMonthEvents.value) {
        if (walkingHistoryMonthEvents.value is NetworkResult.Success && cultureLikeMonthEvents.value is NetworkResult.Success) {
            calendarViewModel.getMonthEvents(calendarUiState.yearMonth.toString())
        }
    }

    SnackbarHost(hostState = snackbarHostState)

    LaunchedEffect(recordViewModel.snackbarFlow) {
        // recordViewModel의 snackbarFlow의 값이 변경되면 이를 감지하여 동작함
        // collectLatest를 사용하여 최신 이벤트만 처리할 수 있도록 하였음
        recordViewModel.snackbarFlow.collectLatest { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    Surface(
        // modifier = Modifier.wrapContentHeight().verticalScroll(rememberScrollState()), color = White
        modifier = Modifier.wrapContentHeight(), color = White
    ) {
        CalendarWidget(days = DateUtil.daysOfWeek,
            yearMonth = calendarUiState.yearMonth,
            dates = calendarUiState.dates,
            monthEvents = monthEvents.value,
            onPreviousMonthButtonClicked = { prevMonth ->
                calendarViewModel.toPreviousMonth(prevMonth)
                calendarViewModel.getCultureLikeMonthEvents(prevMonth.toString())
                calendarViewModel.getWalkingHistoryMonthEvents(prevMonth.toString())
            },
            onNextMonthButtonClicked = { nextMonth ->
                calendarViewModel.toNextMonth(nextMonth)
                calendarViewModel.getCultureLikeMonthEvents(nextMonth.toString())
                calendarViewModel.getWalkingHistoryMonthEvents(nextMonth.toString())
            },
            onDateClickListener = { yearMonthDay, day ->
                calendarViewModel.getCultureLikeDateEvents(yearMonthDay)
                calendarViewModel.toClickedDay(day)
            })
    }
} // End of RecordCalendar()


@Preview(showSystemUi = true)
@Composable
fun CalendarAppPreview() {
    MajuAppTheme() {
        RecordScreenContent(
            rememberNavController(), recordingScreenDummyData
        )
    }
} // End of CalendarAppPreview()

@Preview(showBackground = true)
@Composable
fun LazyRowPreview() {
    MajuAppTheme() {
        RecordScreenLazyItems(
            modifier = Modifier, WalkDateHistoryDomainModel(0, 0.22, 275)
        ) {

        }
    }
} // End of LazyRowPreview()

private val recordingScreenDummyData = RecordingDataModel(
    arrayListOf(
        RecordingWalkRecord(
            "보라매공원 산책로",
            0.22,
            275,
        ), RecordingWalkRecord(
            "보라매공원 산책로",
            0.22,
            275,
        ), RecordingWalkRecord(
            "보라매공원 산책로",
            0.22,
            275,
        ), RecordingWalkRecord(
            "보라매공원 산책로",
            0.22,
            275,
        )
    ), arrayListOf(
        CultureLifeRecord("", "", "", "", ""),
        CultureLifeRecord("", "", "", "", ""),
        CultureLifeRecord("", "", "", "", ""),
        CultureLifeRecord("", "", "", "", ""),
    )
)