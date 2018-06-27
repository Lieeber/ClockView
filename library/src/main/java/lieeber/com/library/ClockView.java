package lieeber.com.library;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import java.util.Calendar;

public class ClockView extends View {

    private Paint biaoPanPaint;
    private Paint hourPaint;
    private Paint minutePaint;
    private Paint hourZhenPiant;
    private Paint minuteZhenPaint;
    private Paint secondZhenPaint;
    private Paint aaaPint;
    private Handler handler;
    private int hours;
    private int minutes;
    private int seconds;

    public ClockView(Context context) {
        super(context);
        initView();
    }

    public ClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        biaoPanPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        biaoPanPaint.setStyle(Paint.Style.FILL);
        biaoPanPaint.setColor(Color.WHITE);

        hourPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        hourPaint.setStyle(Paint.Style.STROKE);
        hourPaint.setStrokeWidth(6);
        hourPaint.setColor(Color.BLACK);

        minutePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        minutePaint.setStyle(Paint.Style.STROKE);
        minutePaint.setStrokeWidth(3);
        minutePaint.setColor(Color.BLACK);

        hourZhenPiant = new Paint(Paint.ANTI_ALIAS_FLAG);
        hourZhenPiant.setStyle(Paint.Style.STROKE);
        hourZhenPiant.setStrokeWidth(20);
        hourZhenPiant.setStrokeCap(Paint.Cap.ROUND);
        hourZhenPiant.setColor(Color.BLACK);

        minuteZhenPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        minuteZhenPaint.setStyle(Paint.Style.STROKE);
        minuteZhenPaint.setStrokeWidth(15);
        minuteZhenPaint.setStrokeCap(Paint.Cap.ROUND);
        minuteZhenPaint.setColor(Color.BLACK);

        secondZhenPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        secondZhenPaint.setStyle(Paint.Style.STROKE);
        secondZhenPaint.setStrokeWidth(10);
        secondZhenPaint.setStrokeCap(Paint.Cap.ROUND);
        secondZhenPaint.setColor(Color.RED);

        aaaPint = new Paint(Paint.ANTI_ALIAS_FLAG);
        aaaPint.setStyle(Paint.Style.FILL);
        aaaPint.setColor(Color.RED);

        handler = new Handler();
        start();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(width, width);
    }

    private void start() {
        Calendar instance = Calendar.getInstance();
        hours = instance.get(Calendar.HOUR_OF_DAY);
        if (hours > 12) {
            hours -= 12;
        }
        minutes = instance.get(Calendar.MINUTE);
        seconds = instance.get(Calendar.SECOND);
        invalidate();
        handler.postDelayed(new Runnable() {
            @Override public void run() {
                start();
            }
        }, 1000);
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int radius = canvas.getWidth() / 2;
        canvas.translate(radius, radius);
        canvas.scale(1, -1);
        canvas.drawCircle(0, 0, radius, biaoPanPaint);

        canvas.save();
        int hourrotateDegree = 360 / 12;
        for (int i = 0; i < 12; i++) {
            canvas.drawLine(0, radius - 10, 0, radius - 60, hourPaint);
            canvas.rotate(hourrotateDegree);
        }
        canvas.restore();


        canvas.save();
        float minuteRotateDegree = 360 / 12.0f / 5;
        for (int i = 0; i < 12 * 5; i++) {
            canvas.drawLine(0, radius - 10, 0, radius - 40, minutePaint);
            canvas.rotate(minuteRotateDegree);
        }
        canvas.restore();
/*****************************************************************************/
        canvas.save();
        canvas.rotate(-360 / 12.0f * hours - 360 / 12.0f * (minutes + seconds / 60.0f) / 60.0f);
        canvas.drawLine(0, -40, 0, radius * 3 / 5, hourZhenPiant);
        canvas.restore();
/*****************************************************************************/
        canvas.save();
        canvas.rotate(-360 / 60.0f * (minutes + seconds / 60.0f));
        canvas.drawLine(0, -40, 0, radius * 4 / 5, minuteZhenPaint);
        canvas.restore();
/*****************************************************************************/
        canvas.save();
        canvas.rotate(-360 / 60.0f * seconds);
        canvas.drawLine(0, -50, 0, radius - 12, secondZhenPaint);
        canvas.restore();
/*****************************************************************************/
        canvas.drawCircle(0, 0, 20, aaaPint);
    }
}
