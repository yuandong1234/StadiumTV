package com.kasai.stadium.tv.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.kasai.stadium.tv.utils.DensityUtil;

public class LineChartView extends View {
    private static String TAG = LineChartView.class.getSimpleName();

    private Paint linePaint;//线条画笔
    private TextPaint textPaint;//文字画笔
    private Paint dashPaint;//虚线画笔
    private Paint pathPaint;//折线画笔

    private String[] xTitles = new String[]{"00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00",
            "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00",
            "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00", "24:00"};
    private String[] yTitles = {"1000", "800", "600", "400", "200", "0"};//y轴做标标题

    private int padding;//view 边距
    private float leftTitleWidth;//左侧标题宽度
    private float bottomTitleHeight;//底部标题高度
    private float xOrigin, yOrigin;//O点坐标
    private float xWidth;//X轴宽度
    private float yHeight;//y轴高度
    private float width;//View宽度
    private float height;//View高度
    private int[] values;//数据源
    private int[] times;//时刻集合
    private int maxValue = 1000;//y轴最大值
    private int minValue = 0;//y轴最小值

    private PointF[] pointFs;//折线点
    private Path mPath = new Path();//折线path

    public LineChartView(Context context) {
        this(context, null);
    }

    public LineChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        init(context);
    }

    private void init(Context context) {
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setColor(Color.parseColor("#FCE3D2"));

        pathPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setStrokeJoin(Paint.Join.ROUND);
        pathPaint.setStrokeCap(Paint.Cap.ROUND);
        pathPaint.setStrokeWidth(DensityUtil.dip2px(context, 1));

        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(DensityUtil.sp2px(context, 10));
        textPaint.setStrokeWidth(1);
        textPaint.setColor(Color.parseColor("#666666"));

        dashPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        dashPaint.setStyle(Paint.Style.STROKE);
        dashPaint.setStrokeWidth(DensityUtil.dip2px(getContext(), 0.5f));
        dashPaint.setColor(Color.parseColor("#FE9C71"));
        DashPathEffect pathEffect = new DashPathEffect(new float[]{5, 5}, 0);
        dashPaint.setPathEffect(pathEffect);

        mPath = new Path();

        padding = DensityUtil.dip2px(context, 10);
        leftTitleWidth = textPaint.measureText("00000", 0, 5);

        Rect bounds = new Rect();// 矩形
        textPaint.getTextBounds("00:00", 0, 5, bounds);
        bottomTitleHeight = bounds.height();
        Log.e(TAG, "文字宽度：" + leftTitleWidth + " 文字高度：" + bottomTitleHeight);
        xOrigin = padding + leftTitleWidth;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);

        xWidth = width - 2f * padding - leftTitleWidth;
        yOrigin = height - padding - bottomTitleHeight - 0.5f * padding;
        //int height = (int) (yOrigin + leftTitleWidth / 2 + padding);
        //setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制坐标线
        linePaint.setStrokeWidth(DensityUtil.dip2px(getContext(), 1));
        canvas.drawLine(xOrigin, padding, xOrigin, yOrigin, linePaint);
        canvas.drawLine(xOrigin + 0.5f * padding, yOrigin, xOrigin + xWidth, yOrigin - 0.5f * padding, linePaint);

        //画间隔虚线
        float ySpaceHeight = (yOrigin - padding) / 5f;//间隔
        for (int i = 0; i < 5; i++) {
            float startX = xOrigin + padding;
            float startY = padding + i * ySpaceHeight;
            float stopX = xOrigin + xWidth;
            float stopY = startY;
            canvas.drawLine(startX, startY, stopX, stopY, dashPaint);
        }

        //画y轴坐标文字
        float xTextCenter = padding + leftTitleWidth / 2f;
        for (int i = 0; i < yTitles.length; i++) {
            float y = padding + i * ySpaceHeight;
            canvas.drawText(yTitles[i], xTextCenter, y, textPaint);
        }

        //画X坐标文字
        float xSpaceWidth = (xWidth - 2f * padding) / 24f;
        float x = xOrigin + padding;
        float y = yOrigin + bottomTitleHeight + 0.5f * padding;
        for (int i = 0; i < 24; i++) {
            canvas.drawText(xTitles[i], x + xSpaceWidth * i, y, textPaint);
        }

        //绘制折线
        if (values != null && values.length > 0 && times != null && times.length > 0) {
            drawLinePoint(canvas);
        }
    }

    private void drawLinePoint(Canvas canvas) {
        //清除折线路径
        mPath.rewind();
        pointFs = new PointF[values.length];
        float x = xOrigin + padding;
        float xSpaceWidth = (xWidth - padding * 2) / (24 * 60 * 60);
        for (int i = 0; i < values.length; i++) {
            pointFs[i] = computePoint(values[i], x + xSpaceWidth * times[i]);
        }

        for (int i = 0; i < pointFs.length; i++) {
            PointF point = pointFs[i];
            if (i == 0) {
                mPath.moveTo(point.x, point.y);
            } else {
                mPath.lineTo(point.x, point.y);
            }
        }

        LinearGradient linearGradient = new LinearGradient(pointFs[0].x, pointFs[0].y,
                pointFs[pointFs.length - 1].x, pointFs[pointFs.length - 1].y,
                Color.parseColor("#FEBF7C"), Color.parseColor("#FF4355"), Shader.TileMode.MIRROR);
        pathPaint.setShader(linearGradient);
        canvas.drawPath(mPath, pathPaint);
    }

    /**
     * 计算点坐标
     *
     * @param values
     * @return
     */
    private PointF computePoint(int values, float x) {
        final PointF point = new PointF();
        float height = yOrigin - padding;
        point.x = x;
        if (values > 0) {
            point.y = yOrigin - (values - minValue) * height / (maxValue - minValue);
        } else {
            point.y = yOrigin;
        }
        return point;
    }

    public void setData(int[] dataValues, int[] timeValues) {
        this.values = dataValues;
        this.times = timeValues;
        invalidate();
    }

    public void setLimitValue(int maxValue, int minValue) {
        this.maxValue = maxValue;
        this.minValue = minValue;
    }
}
