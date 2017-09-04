package com.example.anadministrator.customcircleviewwithrotate;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

/**
 * Created by 张祺钒
 * on2017/9/4.
 */

//首先继承View  要实现的效果呢是一个圆上有一个箭头跟着转
    //首先要自定义attrs
public class Circle extends View {

    private float circleWidth;
    private int defaultCircleColor;

    private int currentCircleColor;
    private Paint paint;

    public Circle(Context context) {
        this(context,null);
    }

    public Circle(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Circle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //初始化一个画笔
        paint = new Paint();

        //首先要获得我们自定义的属性  得到一个类似于集合的东西
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Circle);
        //遍历并取出数据
        for (int i = 0; i < typedArray.getIndexCount(); i++) {
            //通过getIndex方法得到下标 索引
            int index = typedArray.getIndex(i);
            switch(index){
                case R.styleable.Circle_CircleWidth:
                    //得到宽度值  俩个参数分别是索引,和默认值 即:默认宽
                    circleWidth = typedArray.getDimension(index, 3);
                    break;
                case R.styleable.Circle_CircleColor:
                    defaultCircleColor = typedArray.getColor(index, Color.RED);
                    //将,默认的颜色赋予当前颜色
                    currentCircleColor=defaultCircleColor;
                    break;
                default:
                    break;
            }

        }

    }

    //定义圆的属性  圆心坐标  以及半径  当前角度
    private float circleX;
    private float circleY;
    private float radius=130;
    private float currentDegree=0;//默认为0

    //开始画

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //初始化画笔
        paint.setAntiAlias(true);//抗锯齿
        paint.setColor(currentCircleColor);
        paint.setStrokeWidth(circleWidth);
        paint.setStyle(Paint.Style.STROKE);//设置空心圆
        circleX=getWidth()/2;
        circleY=getHeight()/2;
        canvas.drawCircle(circleX,circleY,radius,paint);//画一个背景圆

        canvas.save();//用来在动画效果之后保存状态的   这里不写也可以

        //旋转画布 如果旋转的角度大的话,视觉上看着是旋转快的
        canvas.rotate(currentDegree,circleX,circleY);//三个参数 分别是旋转的角度和旋转的XY中心

        //画三角形
        //提供了一些api可以用来画线(画路径)
        Path path = new Path();
        //从哪开始画 从A开始画
        path.moveTo(circleX + radius, circleY);
        //从A点画一个直线到D点
        path.lineTo(circleX + radius - 20, circleY - 20);
        //从D点画一个直线到B点
        path.lineTo(circleX + radius, circleY + 20);
        //从B点画一个直线到C点
        path.lineTo(circleX + radius + 20, circleY - 20);
        //闭合  --  从C点画一个直线到A点
        path.close();

        //以上都是一个画三角形的操作

        paint.setStyle(Paint.Style.FILL);//重新设置画笔的样式 颜色  画出
        paint.setColor(Color.BLACK);
        canvas.drawPath(path, paint);
        canvas.restore();
        //旋转的角度一倍速的增加
        currentDegree+=1*currentSpeed;
        if (!isPause) {
            invalidate();
        }
    }
    //定义旋转速度
    private int currentSpeed=1;
    //是否暂停
    private boolean isPause=false;

    public void speed() {
        ++currentSpeed;
        if (currentSpeed >= 10) {
            currentSpeed = 10;
            Toast.makeText(getContext(), "我比闪电还快", Toast.LENGTH_SHORT).show();
        }
    }

    public void slowDown() {
        --currentSpeed;
        if (currentSpeed <=1) {
            currentSpeed = 1;
        }
    }
    //暂停
    public void pauseOrStart() {

        //如果是开始状态的话去重新绘制
        if (isPause) {
            isPause = !isPause;
            invalidate();
        } else {
            isPause = !isPause;
        }
    }

    public void setColor(int color) {
        if (currentCircleColor != color) {
            currentCircleColor = color;
        } else {
            currentCircleColor = defaultCircleColor;
        }
    }
}
