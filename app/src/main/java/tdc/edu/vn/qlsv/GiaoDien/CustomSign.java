package tdc.edu.vn.qlsv.GiaoDien;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.io.ByteArrayOutputStream;

public class CustomSign extends View {
    Paint paint;
    Path path;
    public CustomSign(Context context) {
        super(context);
        init();
    }

    public CustomSign(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomSign(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        path=new Path();
        paint=new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path,paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float xPos=event.getX();
        float yPos=event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                path.moveTo(xPos,yPos);
                return true;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(xPos,yPos);
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                return  false;
        }
        invalidate();
        return true;
    }

    public void reset() {
        this.init();
        invalidate();
    }
}
