package com.austinevick.noteapp.ui.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.austinevick.noteapp.R
import com.austinevick.noteapp.core.color
import com.austinevick.noteapp.core.convertLongToDate
import com.austinevick.noteapp.data.NoteEntity
import com.austinevick.noteapp.theme.Green

@Composable
fun NoteCard(note: NoteEntity, onTap: () -> Unit) {


    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clickable { onTap() }
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .border(
                BorderStroke(
                    1.dp,
                    note.color.color.copy(alpha = 0.5f)
                ), RoundedCornerShape(12.dp)
            )
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
                if (note.title.isNotEmpty()) Text(text = note.title,
                    maxLines = 1, fontWeight = FontWeight.W600)

            if (note.content.isNotEmpty()) Text(text = note.content, maxLines = 3)
            Spacer(modifier = Modifier.height(6.dp))
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                  convertLongToDate(note.date),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.W600,
                    color = Color.Gray,
                    style = TextStyle(
                        platformStyle =
                            PlatformTextStyle(includeFontPadding = false)
                    )
                )
              if (note.isPinned)
                  Icon(painterResource(R.drawable.outline_push_pin),null,
                  modifier = Modifier.size(20.dp), tint = Green,)
            }
        }
    }

}