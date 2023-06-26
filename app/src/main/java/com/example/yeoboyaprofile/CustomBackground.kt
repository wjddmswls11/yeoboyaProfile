package com.example.yeoboyaprofile

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.text.style.LineBackgroundSpan
import android.text.style.LineHeightSpan
import androidx.core.content.ContextCompat

class CustomBackground(
    private val colorId: Int
) : LineBackgroundSpan {
    override fun drawBackground(
        canvas: Canvas,//배경을 그리기 위해 사용되는 캔버스
        paint: Paint,//배경을 그리기 위해 사용되는 페인트
        left: Int,//줄의 왼쪽 끝 좌표
        right: Int,//줄의 오른쪽 끝 좌표
        top: Int,//줄의 맨 위 좌표
        baseline: Int,//줄의 기준선 좌표
        bottom: Int,//줄의 맨 아래 좌표
        text: CharSequence,//줄의 텍스트
        start: Int,//줄의 시작 위치
        end: Int,//줄의 끝 위치
        lnum: Int//줄의 번호
    ) {
        val originalColor = paint.color
        paint.color = colorId
        val halfHeight = (bottom - top) /2
        canvas.drawRect(Rect(left,bottom-halfHeight,right,bottom), paint)
        paint.color = originalColor
    }
}