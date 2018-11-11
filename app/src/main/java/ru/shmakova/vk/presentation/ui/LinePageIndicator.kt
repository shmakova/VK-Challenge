package ru.shmakova.vk.presentation.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewConfigurationCompat
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewConfiguration
import ru.shmakova.vk.R


class LinePageIndicator
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = R.attr.vpiLinePageIndicatorStyle
) : View(context, attrs, defStyle) {

    private val mPaintUnselected = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mPaintSelected = Paint(Paint.ANTI_ALIAS_FLAG)

    private var mCurrentPage: Int = 0
    private var mCentered: Boolean = false
    private var mLineWidth: Float = 0.toFloat()
    private var mGapWidth: Float = 0.toFloat()
    private var mItemsCount: Int = 0
    private var mTouchSlop: Int = 0

    private lateinit var metrics: DisplayMetrics

    var isCentered: Boolean
        get() = mCentered
        set(centered) {
            mCentered = centered
            invalidate()
        }

    var unselectedColor: Int
        get() = mPaintUnselected.color
        set(unselectedColor) {
            mPaintUnselected.color = unselectedColor
            invalidate()
        }

    var selectedColor: Int
        get() = mPaintSelected.color
        set(selectedColor) {
            mPaintSelected.color = selectedColor
            invalidate()
        }

    var lineWidth: Float
        get() = mLineWidth
        set(lineWidth) {
            mLineWidth = lineWidth
            invalidate()
        }

    var strokeWidth: Float
        get() = mPaintSelected.strokeWidth
        set(lineHeight) {
            mPaintSelected.strokeWidth = lineHeight
            mPaintUnselected.strokeWidth = lineHeight
            invalidate()
        }

    var gapWidth: Float
        get() = mGapWidth
        set(gapWidth) {
            mGapWidth = gapWidth
            invalidate()
        }

    init {
        if (!isInEditMode) {
            val res = resources

            val defaultSelectedColor =
                ContextCompat.getColor(context, R.color.default_line_indicator_selected_color)
            val defaultUnselectedColor =
                ContextCompat.getColor(context, R.color.default_line_indicator_unselected_color)
            val defaultLineWidth = res.getDimension(R.dimen.default_line_indicator_line_width)
            val defaultGapWidth = res.getDimension(R.dimen.default_line_indicator_gap_width)
            val defaultStrokeWidth = res.getDimension(R.dimen.default_line_indicator_stroke_width)
            val defaultCentered = res.getBoolean(R.bool.default_line_indicator_centered)

            val a =
                context.obtainStyledAttributes(attrs, R.styleable.LinePageIndicator, defStyle, 0)

            mCentered = a.getBoolean(R.styleable.LinePageIndicator_centered, defaultCentered)
            mLineWidth = a.getDimension(R.styleable.LinePageIndicator_lineWidth, defaultLineWidth)
            mGapWidth = a.getDimension(R.styleable.LinePageIndicator_gapWidth, defaultGapWidth)
            strokeWidth =
                    a.getDimension(R.styleable.LinePageIndicator_strokeWidth, defaultStrokeWidth)
            mPaintUnselected.color = a.getColor(
                R.styleable.LinePageIndicator_unselectedColor,
                defaultUnselectedColor
            )
            mPaintSelected.color =
                    a.getColor(R.styleable.LinePageIndicator_selectedColor, defaultSelectedColor)

            val background = a.getDrawable(R.styleable.LinePageIndicator_android_background)
            if (background != null) {
                setBackground(background)
            }

            a.recycle()

            val configuration = ViewConfiguration.get(context)
            mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration)
            metrics = DisplayMetrics()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val count = mItemsCount

        if (count == 0) {
            return
        }

        if (mCurrentPage >= count) {
            mCurrentPage = count - 1
            return
        }
        mLineWidth = (width / count).toFloat() - mGapWidth
        val lineWidthAndGap = mLineWidth + mGapWidth
        val indicatorWidth = count * lineWidthAndGap
        val paddingTop = paddingTop
        val paddingLeft = paddingLeft
        val paddingRight = paddingRight

        val verticalOffset = paddingTop + (height - paddingTop - paddingBottom) / 2.0f
        var horizontalOffset = paddingLeft
        if (mCentered) {
            horizontalOffset += ((width.toFloat() - paddingLeft - paddingRight) / 2.0f - indicatorWidth / 2.0f).toInt()
        }

        for (i in 0 until count) {
            val dx1 = horizontalOffset + i * lineWidthAndGap
            val dx2 = dx1 + mLineWidth
            canvas.drawRoundRect(
                dx1,
                verticalOffset,
                dx2,
                verticalOffset + strokeWidth,
                12f,
                12f,
                if (i == mCurrentPage) mPaintSelected else mPaintUnselected
            )
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec))
    }

    private fun measureWidth(measureSpec: Int): Int {
        var result: Float
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize.toFloat()
        } else {
            val count = mItemsCount
            result = paddingLeft + paddingRight + count * mLineWidth + (count - 1) * mGapWidth
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize.toFloat())
            }
        }
        return Math.ceil(result.toDouble()).toInt()
    }

    private fun measureHeight(measureSpec: Int): Int {
        var result: Float
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize.toFloat()
        } else {
            result = mPaintSelected.strokeWidth + paddingTop + paddingBottom
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize.toFloat())
            }
        }
        return Math.ceil(result.toDouble()).toInt()
    }

    fun setItemsCount(count: Int) {
        mItemsCount = count
        invalidate()
    }

    fun onPageSelected(position: Int) {
        mCurrentPage = position
        invalidate()
    }
}
