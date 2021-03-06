package by.it.academy.homework4_2;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;
import java.util.Random;

public class CustomView extends View {

    interface OnCustomViewActionListener {
        void onActionDown(float x, float y, int color);
    }

    private int diameter;
    boolean defaultStateFlag = true;
    private int centerX;
    private int centerY;
    private int radius1;
    private int radius2;
    private final Region[] regions = {new Region(), new Region(), new Region(), new Region(), new Region()};
    private final Paint[] paints = {new Paint(), new Paint(), new Paint(), new Paint(), new Paint()};
    private final RectF oval = new RectF();
    private OnCustomViewActionListener onCustomViewActionListener;
    private final Random rand = new Random();

    private final int[] colors = {-16007990, -16766976, -15277667, -58998, -7798531, -10233776, -6765239, -48340, -15753874,
            -16766287, -16747109, -7798531, -11766015};


    public int getRandArrayElement() {
        return colors[rand.nextInt(colors.length)];
    }

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs) {
        try {
            TypedArray typedArray = getResources().obtainAttributes(attrs, R.styleable.CustomView);
            diameter = (int) typedArray.getDimension(R.styleable.CustomView_circleDefaultDiameter, 200);
            typedArray.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int viewHeight = MeasureSpec.getSize(heightMeasureSpec);
        int viewWidth = MeasureSpec.getSize(widthMeasureSpec);
        centerX = viewWidth / 2;
        centerY = viewHeight / 2;
        calculateCords();
    }

    public void calculateCords() {

        radius1 = diameter / 2;
        radius2 = diameter / 6;

        oval.set(centerX - radius1, centerY - radius1, centerX + radius1,
                centerY + radius1);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        regions[0] = new Region(centerX, centerY, centerX + radius1, centerY + radius1);
        regions[1] = new Region(centerX - radius1, centerY, centerX, centerY + radius1);
        regions[2] = new Region(centerX - radius1, centerY - radius1, centerX, centerY);
        regions[3] = new Region(centerX, centerY - radius1, centerX + radius1, centerY);
        regions[4] = new Region(centerX - radius2, centerY - radius2, centerX + radius2, centerY + radius2);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (defaultStateFlag) {
            paints[0].setColor(Color.YELLOW);
            paints[1].setColor(Color.BLUE);
            paints[2].setColor(Color.RED);
            paints[3].setColor(Color.GREEN);
            paints[4].setColor(Color.MAGENTA);
            defaultStateFlag = false;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawArc(oval, 0, 90, true, paints[0]);
        canvas.drawArc(oval, 90, 90, true, paints[1]);
        canvas.drawArc(oval, 180, 90, true, paints[2]);
        canvas.drawArc(oval, 0, -90, true, paints[3]);
        canvas.drawCircle(centerX, centerY, radius2, paints[4]);
        super.onDraw(canvas);
    }

    public void setOnCustomViewActionListener(OnCustomViewActionListener onCustomViewActionListener) {
        this.onCustomViewActionListener = onCustomViewActionListener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        performClick();
        float x = event.getX();
        float y = event.getY();

        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            if (onCustomViewActionListener != null) {
                for (int i = 0; i < regions.length; i++) {
                    if (regions[i].contains((int) x, (int) y)) {
                        if (i < 4) {
                            paints[i].setColor(getRandArrayElement());
                            onCustomViewActionListener.onActionDown(x, y, paints[i].getColor());
                            invalidate();
                        } else {
                            for (int j = 0; j < regions.length; j++) {
                                paints[j].setColor(getRandArrayElement());
                                onCustomViewActionListener.onActionDown(x, y, paints[j].getColor());
                                invalidate();
                            }
                        }
                    }
                }
            }
        }
        return super.onTouchEvent(event);
    }
    @Override
    public boolean performClick() {
        return super.performClick();
    }
}