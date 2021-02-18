package com.waxym.ui.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.waxym.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CircularStatView extends View {
    private final List<Percent> mPercents = new ArrayList<>();
    private final Rect mTextBounds = new Rect(); // use by drawTextCentered to center text
    private final Paint mPercentPaint = new Paint();
    private final Paint mOutLinePaint = new Paint();
    private final Paint mPercentLabelPaint = new Paint();
    private float mOutlineStrokeWidth = 0f;
    private int mOutlineStrokeColor = Color.TRANSPARENT;
    private int mHeight, mWidth;
    private float mMaxDisplayedPercent = 1f;
    private float mMinDisplayedPercent = 0f;

    public CircularStatView(Context context) {
        super(context);
        init(context, null);
    }

    public CircularStatView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CircularStatView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            mWidth = right - left;
            mHeight = bottom - top;
        }
    }

    private void init(@NonNull final Context context, @Nullable final AttributeSet attrs) {
        if (attrs != null) {
            final TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CircularStatView, 0, 0);
            try {
                mOutlineStrokeColor = a.getColor(R.styleable.CircularStatView_stroke_color, Color.parseColor("#808080"));
                mOutlineStrokeWidth = a.getDimension(R.styleable.CircularStatView_stroke_width, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics()));
            } finally {
                a.recycle();
            }
        }

        mPercentLabelPaint.setColor(Color.WHITE);
        mPercentLabelPaint.setStyle(Paint.Style.FILL);
        mPercentLabelPaint.setAntiAlias(true);
        mPercentLabelPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
        mPercentLabelPaint.setShadowLayer(1.0f, 1.0f, 1.0f, Color.GRAY);

        mPercentPaint.setColor(Color.RED);
        mPercentPaint.setStrokeWidth(mOutlineStrokeWidth);
        mPercentPaint.setStyle(Paint.Style.FILL);
        mPercentPaint.setAntiAlias(false);

        mOutLinePaint.setColor(mOutlineStrokeColor);
        mOutLinePaint.setStrokeWidth(mOutlineStrokeWidth / 2);
        mOutLinePaint.setAntiAlias(true);
        mOutLinePaint.setStyle(Paint.Style.STROKE);
        mOutLinePaint.setStrokeCap(Paint.Cap.ROUND);
        mOutLinePaint.setStrokeJoin(Paint.Join.BEVEL);

        if (isInEditMode()) {
            mPercents.add(new Percent(0.05f, Color.parseColor("#39add1")));
            mPercents.add(new Percent(0.25f, Color.parseColor("#838cc7")));
            mPercents.add(new Percent(0.45f, Color.parseColor("#637a91")));
            mPercents.add(new Percent(0.15f, Color.parseColor("#c25975")));
            mPercents.add(new Percent(0.10f, Color.parseColor("#e15258")));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();

        float margin = mOutlineStrokeWidth / 2f;
        float minAngle = mMinDisplayedPercent * 360f;
        float maxAngle = mMaxDisplayedPercent * 360f;

        float startAngle = 0f;
        for (Percent percent : mPercents) {
            float sweepAngle = percent.percent * 360f;
            if (minAngle <= startAngle + sweepAngle && startAngle <= maxAngle) {
                drawPercent(canvas, Math.max(startAngle, minAngle), Math.min(sweepAngle, maxAngle - startAngle) - Math.max(minAngle - startAngle, 0f), percent.color);
                drawPercentSeparator(canvas, Math.max(startAngle, minAngle), mOutlineStrokeColor);
            }
            if (percent.getPercent() >= 0.05f) {
                drawPercentLabel(canvas, startAngle, sweepAngle, percent);
            }
            startAngle += sweepAngle;
        }
        if (mPercents.size() > 0) {
            drawPercentSeparator(canvas, Math.min(Math.max(minAngle, startAngle), maxAngle), mOutlineStrokeColor);
            canvas.drawArc(margin, margin, mWidth - margin, mHeight - margin, minAngle, Math.max(Math.min(startAngle - minAngle, (mMaxDisplayedPercent - mMinDisplayedPercent) * 360f), 0f), false, mOutLinePaint);
        }

        canvas.restore();
    }

    public void drawPercent(@NonNull final Canvas canvas, final float startAngle, final float sweepAngle, @ColorInt final int color) {
        mPercentPaint.setColor(color);
        float margin = mOutlineStrokeWidth / 2f;
        canvas.drawArc(margin, margin, mWidth - margin, mHeight - margin, startAngle, sweepAngle, true, mPercentPaint);
    }

    public void drawPercentSeparator(@NonNull final Canvas canvas, final float startAngle, @ColorInt final int color) {
        mOutLinePaint.setColor(color);
        float margin = mOutlineStrokeWidth / 2f;
        int width = mWidth / 2;
        int height = mHeight / 2;
        double magic = Math.toRadians(startAngle);
        float x = (float) (Math.cos(magic) * (width - margin));
        float y = (float) (Math.sin(magic) * (height - margin));
        canvas.drawLine(width, height, width + x, height + y, mOutLinePaint);
    }

    public void drawPercentLabel(@NonNull final Canvas canvas, final float startAngle, final float sweepAngle, @NonNull final Percent percent) {
        int width = mWidth / 2;
        int height = mHeight / 2;
        double magic = Math.toRadians(startAngle + sweepAngle / 2);
        float x = (float) (Math.cos(magic) * (width * 2 / 3));
        float y = (float) (Math.sin(magic) * (height * 2 / 3));
        drawTextCentered(canvas, String.format(Locale.getDefault(), "%,.2f%%", percent.getPercent() * 100f), width + x, height + y, mPercentLabelPaint);
    }

    public void drawTextCentered(Canvas canvas, String text, float cx, float cy, Paint paint) {
        paint.getTextBounds(text, 0, text.length(), mTextBounds);
        canvas.drawText(text, cx - mTextBounds.exactCenterX(), cy - mTextBounds.exactCenterY(), paint);
    }

    public void setPercents(@Nullable final List<Percent> percents) {
        mPercents.clear();
        if (percents != null) {
            mPercents.addAll(percents);
        }
    }

    @NonNull
    public List<Percent> getPercents() {
        return mPercents;
    }

    @FloatRange(from = 0f, to = 1f)
    public float getMaxDisplayedPercent() {
        return mMaxDisplayedPercent;
    }

    public void setMaxDisplayedPercent(@FloatRange(from = 0f, to = 1f) float maxDisplayedPercent) {
        mMaxDisplayedPercent = maxDisplayedPercent;
    }

    @FloatRange(from = 0f, to = 1f)
    public float getMinDisplayedPercent() {
        return mMinDisplayedPercent;
    }

    public void setMinDisplayedPercent(@FloatRange(from = 0f, to = 1f) float minDisplayedPercent) {
        mMinDisplayedPercent = minDisplayedPercent;
    }

    @NonNull
    public ValueAnimator animateMaxPercent() {
        final ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mMaxDisplayedPercent = animation.getAnimatedFraction();
                postInvalidate();
            }
        });
        return animator;
    }

    @NonNull
    public ValueAnimator animateMinPercent() {
        final ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mMinDisplayedPercent = animation.getAnimatedFraction();
                postInvalidate();
            }
        });
        return animator;
    }

    public static class Percent {
        @FloatRange(from = 0f, to = 1f)
        private final float percent;
        @ColorInt
        private final int color;

        public Percent(@FloatRange(from = 0f, to = 1f) float percent, @ColorInt int color) {
            this.percent = percent;
            this.color = color;
        }

        @FloatRange(from = 0f, to = 1f)
        public float getPercent() {
            return percent;
        }

        @ColorInt
        public int getColor() {
            return color;
        }
    }
}
