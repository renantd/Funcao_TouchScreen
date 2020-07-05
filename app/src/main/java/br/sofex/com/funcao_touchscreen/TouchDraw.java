package br.sofex.com.funcao_touchscreen;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TouchDraw extends View {

    Context mContext;
    private static final int SIZE = 60;
    private AlertDialog alerta;

    private SparseArray<PointF> mActivePointers;
    private Paint mPaint;
    private int[] colors = { Color.BLUE, Color.GREEN, Color.MAGENTA,
            Color.BLACK, Color.CYAN, Color.GRAY, Color.RED, Color.DKGRAY,
            Color.LTGRAY, Color.YELLOW };

    private Paint textPaint;
    private List<Point> points = new ArrayList<Point>();
    private int cor;
    public int corSelecionada;
    public int cont;

    // setup initial color
    private final int paintColor = Color.BLACK;
    // defines paint and canvas
    private Paint paint;
    // stores next circle
    private Path path = new Path();

    List<Integer> pointX = new ArrayList<Integer>();
    List<Integer> pointY = new ArrayList<Integer>();

    public TouchDraw(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();
    }

    private void initView() {
        mActivePointers = new SparseArray<>();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // set painter color to a color you like
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(20);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        for (Point point : points) {
            canvas.drawCircle(point.x, point.y, 20, mPaint);
            invalidate();
        }
    }

    private float initialX = 0;
    private float initialY = 0;
    private float deltaX = 0;
    private float deltaY = 0;

    @Override
    public boolean onTouchEvent(MotionEvent m) {
        int pointerCount = m.getPointerCount();

        for (int i = 0; i < pointerCount; i++)
        {

            synchronized (m)
            {
                try
                {
                    //Waits 16ms.
                    m.wait(16);

                    //when user touches the screen
                    if(m.getAction() == MotionEvent.ACTION_DOWN)
                    {
                        //reset deltaX and deltaY
                        deltaX = deltaY = 0;

                        //get initial positions
                        initialX = m.getRawX();
                        initialY = m.getRawY();
                    }

                    //when screen is released
                    if(m.getAction() == MotionEvent.ACTION_UP)
                    {
                        deltaX = m.getRawX() - initialX;
                        deltaY = m.getRawY() - initialY;

                        //swipped up
                        if(deltaY > 0)
                        {
                        }
                        else
                        {
                            //make your object/character move left
                        }

                        return true;
                    }
                }

                catch (InterruptedException e)
                {
                    return true;
                }
            }

            int x = (int) m.getX(i);
            int y = (int) m.getY(i);

            int id = m.getPointerId(i);
            int action = m.getActionMasked();
            int actionIndex = m.getActionIndex();
            String actionString;


            switch (action)
            {

                case MotionEvent.ACTION_DOWN:

                    Point p = new Point();
                    p.x = (int) m.getX(i);
                    p.y = (int) m.getY(i);

                    pointX.add((int) m.getX(i));
                    pointY.add((int) m.getY(i));

                    points.add(p);
                    invalidate();

                    cont = cont + 1;
                    actionString = "DOWN";
                    break;
                case MotionEvent.ACTION_UP:

                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    actionString = "PNTR DOWN";
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    actionString = "PNTR UP";
                    break;
                case MotionEvent.ACTION_MOVE:
                    actionString = "MOVE";
                    break;
                default:
                    actionString = "";
            }

        }

        invalidate();
        return true;
    }


}
