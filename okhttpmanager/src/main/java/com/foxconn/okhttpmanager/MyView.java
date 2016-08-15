package com.foxconn.okhttpmanager;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Bear on 2016/8/15.
 * 自定义控件的方式
 * 1.扩展自定义View继承自Android原生特定的View如：TextView，ImageView等等。
 * 我们通过重写onDrow()等回调方法对其进行扩展！使其实现我们想要的功能和样式。
 * 该方法实现的自定义View控件不需要自己支持wrap_content和padding。
 * 2.组合式自定义View继承自ViewGroup的子View如：LinearLayout，RelativieLayout等。
 * 当某种效果看起来像几种View组合在一起的时候，都可以使用这种方式实现。
 * 该方法实现的自定义View不粗要自己处理ViewGroup的测量和布局这两个过程。
 * 3.完全自定义View继承View(Android中所有控件的基类),通常实现一些不方便布局的组合方式
 * 来达到的，需要静态或动态地显示一些不规则的控件或图形。
 * 该方法实现的自定义View控件需要自己支持wrap_content和padding。
 *
 * 自定义View的基本流程
 * 1.重写构造方法
 * 2.重写OnMeasure
 * 3.重写onDraw
 * 4.在布局中使用
 */
public class MyView extends View {
    private Paint mPaint;
    /**
     * 在使用代码动态的添加我们自定义的View调用
     * @param context 上下文对象
     */
    public MyView(Context context) {
        super(context);
        initPaint();
    }

    /**
     * 使用xml+inflate的方法添加控件时会调用，里面多了一个AttributeSet类型的值
     * @param context 上下文对象
     * @param attrs AttributeSet属性
     */
    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    /**
     * 这个构造方法系统是不调用的，需要我们显示调用并给defStyleAttr传值，多了
     * 一个defStyleAttr参数，这是这个view引用style资源的属性参数，也就是我们
     * 可以在style中为自定义View定义一个默认的属性样式然后添加进来！
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画布
        canvas.drawCircle(60,60,60,mPaint);
        //绘制文本
        canvas.drawText("beacon",100,100,mPaint);
        //绘制直线
        canvas.drawLine(100,100,150,150,mPaint);
        //绘制路径
        Path path = new Path();
        path.moveTo(60,60);
        path.lineTo(60,60);
        path.lineTo(120,0);
        path.lineTo(180,120);
        path.moveTo(120,0);
        path.moveTo(180,120);

        path.close();
        canvas.drawPath(path,mPaint);
        //绘制点
        //绘制椭圆，扇形，环形
        //绘制Bitmap

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    private Paint initPaint(){
        //画笔
        mPaint = new Paint();
        //设置抗锯齿
        mPaint.setAntiAlias(true);
        //设置画笔的宽度
        mPaint.setStrokeWidth(5);
        //设置画笔的颜色
        mPaint.setColor(Color.BLUE);
        //设置画笔的样式
        mPaint.setStyle(Paint.Style.STROKE);
        //设置文本字体的大小
        mPaint.setTextSize(50);
        return mPaint;

    }

}
