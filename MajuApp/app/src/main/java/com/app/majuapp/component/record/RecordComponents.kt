package com.app.majuapp.component.record

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.majuapp.ui.theme.notoSansKoreanFontFamily
import com.app.majuapp.ui.theme.roundedCornerPadding


@Composable
fun RecordScreenCalendarColorInform(text: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier.clip(RoundedCornerShape(roundedCornerPadding)).width(28.dp)
                .height(14.dp).background(color)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            fontWeight = FontWeight.Normal,
            text = text,
            fontSize = 12.sp,
            fontFamily = notoSansKoreanFontFamily,
        )
    }
} // End of RecordScreenCalendarColorInform()


