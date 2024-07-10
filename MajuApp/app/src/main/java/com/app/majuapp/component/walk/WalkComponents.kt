package com.app.majuapp.component.walk

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.app.majuapp.R
import com.app.majuapp.ui.theme.BrightGray
import com.app.majuapp.ui.theme.GoldenPoppy
import com.app.majuapp.ui.theme.MajuAppTheme
import com.app.majuapp.ui.theme.OuterSpace
import com.app.majuapp.ui.theme.SilverSand
import com.app.majuapp.ui.theme.SonicSilver
import com.app.majuapp.ui.theme.White
import com.app.majuapp.ui.theme.defaultPadding
import com.app.majuapp.ui.theme.dialogButtonRoundedCorner
import com.app.majuapp.ui.theme.dialogCornerPadding
import com.app.majuapp.ui.theme.dialogDefaultPadding
import com.app.majuapp.ui.theme.roundedCornerPadding
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalkScreenChooseStartDialog(
    title: String,
    content: String,
    onClickDismiss: () -> Unit,
    onClickConfirm: () -> Unit,
) {
    /*
        산책 기능 다이얼로그 화면
     */

    // Context
    val context = LocalContext.current

    var showAnimatedDialog by remember { mutableStateOf(false) }
    var graphicVisible = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { graphicVisible.value = true }

    var animateIn = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { animateIn.value = true }

    /* GoogleMap */
    val seoul = LatLng(37.5744, 126.9771)
    val cameraPositionState: CameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(seoul, 16f)
    }
    var uiSettings by remember { mutableStateOf(MapUiSettings()) }
    val properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.NORMAL))
    }

    AnimatedVisibility(
        visible = graphicVisible.value, enter = expandVertically(
            animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
            expandFrom = Alignment.CenterVertically,
        )
    ) {
        Dialog(
            onDismissRequest = { onClickDismiss() },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = false,
            ),
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.shadow(8.dp, shape = RoundedCornerShape(16.dp))
                    .background(Color.Black.copy(alpha = .9f))
                    .clip(RoundedCornerShape(dialogCornerPadding)).background(White).padding(
                        start = dialogDefaultPadding,
                        end = dialogDefaultPadding,
                        top = dialogDefaultPadding,
                        bottom = 16.dp
                    ),
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        modifier = Modifier,
                        text = title,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = content,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraLight,
                        color = SonicSilver,
                        modifier = Modifier.align(
                            Alignment.CenterHorizontally
                        ),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(18.dp))
                    Box(
                        modifier = Modifier.clip(RoundedCornerShape(roundedCornerPadding))
                            .fillMaxWidth().height(260.dp).border(
                                2.dp, BrightGray, shape = RoundedCornerShape(roundedCornerPadding)
                            ).padding(defaultPadding), contentAlignment = Alignment.BottomCenter
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "보라매공원 산책로",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.fillMaxWidth().height(18.dp))
                            Box(
                                modifier = Modifier.clip(RoundedCornerShape(roundedCornerPadding))
                                    .fillMaxWidth()
                                    .height(200.dp).background(
                                        OuterSpace, shape = RoundedCornerShape(roundedCornerPadding)
                                    ),
                                contentAlignment = Alignment.BottomCenter,
                            ) {
                                GoogleMap(
                                    modifier = Modifier.fillMaxSize(),
                                    cameraPositionState = cameraPositionState,
                                    properties = properties,
                                    uiSettings = uiSettings.copy(zoomControlsEnabled = false)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(18.dp))
                    Row(
                        modifier = Modifier.padding(start = defaultPadding, end = defaultPadding),
                        Arrangement.spacedBy(8.dp)
                    ) {
                        /*
                            산책로 결과에 따라 보여지는 버튼 Text 다름
                         */
//                        WalkComponentButton(
//                            buttonText = "돌아가기",
//                            GoldenPoppy,
//                            onClickConfirm,
//                            Modifier.weight(1f)
//                        )

                        WalkComponentButton(
                            buttonText = context.getString(R.string.walk_screen_dialog_start_dialog_start_button_content),
                            GoldenPoppy,
                            onClickConfirm,
                            Modifier.weight(1f)
                        )
                    }
                }
            }
        }

        DisposableEffect(Unit) {
            onDispose {
                showAnimatedDialog = false
            }
        }
    }
} // End of WalkScreenChooseStartDialogue()

@Composable
fun WalkRecordingBox(context: Context) {
    /*
        바텀 시트 내부
        이동 거리, 걸음 수가 보이는 회색 박스
     */

    Box(
        Modifier.clip(RoundedCornerShape(8.dp)).fillMaxWidth().height(92.dp)
            .background(color = BrightGray),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.matchParentSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                // 이동 거리 박스
                modifier = Modifier.fillMaxWidth().wrapContentHeight()
                    .weight(1f).align(
                        Alignment.CenterVertically,
                    ),
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        textAlign = TextAlign.Center,
                        color = SonicSilver,
                        text = context.getString(R.string.walk_screen_walking_bottom_sheet_box_distanced_traveled),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                    )
                    Row {
                        Text(
                            textAlign = TextAlign.Center,
                            text = "0.22",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            textAlign = TextAlign.Center,
                            text = context.getString(R.string.walk_screen_walking_bottom_sheet_box_distanced_traveled_unit),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium,
                        )
                    }
                }
            }
            Spacer(
                // 회색 박스 중간 구분선
                modifier = Modifier.width(1.dp).fillMaxHeight()
                    .padding(
                        top = defaultPadding + 8.dp,
                        bottom = defaultPadding + 8.dp
                    )
                    .background(SilverSand)
            )
            Box(
                // 걸음 수 박스
                modifier = Modifier.fillMaxWidth().wrapContentHeight()
                    .weight(1f).align(
                        Alignment.CenterVertically,
                    ),
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        color = SonicSilver,
                        textAlign = TextAlign.Center,
                        text = context.getString(R.string.walk_screen_walking_bottom_sheet_box_step_count_title),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                    )
                    Row() {
                        Text(
                            textAlign = TextAlign.Center,
                            text = "275",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            textAlign = TextAlign.Center,
                            text = context.getString(R.string.walk_screen_walking_bottom_sheet_box_step_count_unit),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium,
                        )
                    }
                }
            }
        }
    }
} // End of WalkRecordingBox


@Composable
fun WalkComponentButton(
    buttonText: String, buttonColor: Color, onClickConfirm: () -> Unit, modifier: Modifier
) {
    Button(shape = RoundedCornerShape(dialogButtonRoundedCorner),
        modifier = modifier.fillMaxWidth().wrapContentHeight(),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor,
        ),
        onClick = { onClickConfirm() }) {
        Text(buttonText, fontSize = 14.sp, fontWeight = FontWeight.Bold)
    }
} // End of WalkComponentButton()

@Preview
@Composable
private fun WalkScreenChooseStartDialogPreveiw() {
    MajuAppTheme {
        WalkScreenChooseStartDialog("출발지 선택", "지도 위에 현재 위치\n 선택해주세요!", {}, {})
    }
} // End of WalkScreenChooseStartDialogPreview()