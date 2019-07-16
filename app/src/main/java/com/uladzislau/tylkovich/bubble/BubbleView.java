package com.uladzislau.tylkovich.bubble;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.text.AttributedCharacterIterator;
import java.util.ArrayList;


public class BubbleView extends ImageView  implements View.OnTouchListener{

private ArrayList<Bubble> bubbleList;
private final int Delay =16;
private Paint myPaint = new Paint();

private Handler h;

public BubbleView(Context context, AttributeSet attrs) {
    super(context, attrs);
    bubbleList = new ArrayList<Bubble>();
    myPaint.setColor(Color.WHITE);
    h = new Handler();


    this.setOnTouchListener(this);
    //
//    for(int x=0;x<100;x++)
//        bubbleList.add(new Bubble((int)(Math.random()*300),
//                (int)(Math.random()*400), 50));
}


private Runnable r =new Runnable() {
    @Override
    public void run() {
        //update all the bubles
        for(Bubble bubble:bubbleList)
            bubble.update();

        //redraw the screen
        invalidate();
    }
};

protected  void onDraw(Canvas c){

//    for(int x=0;x<100;x++)
//        bubbleList.add(new Bubble((int)(Math.random()*300),
//                (int)(Math.random()*400), 50));


    for (Bubble bubble:bubbleList){
        myPaint.setColor(bubble.color);
        c.drawOval(bubble.x -bubble.size/2,bubble.y-bubble.size/2,
                bubble.x+bubble.size/2,bubble.y +bubble.size/2,myPaint);

    }
    myPaint.setColor(Color.WHITE);
    c.drawText("Vount: " + bubbleList.size(),5,15,myPaint);
    h.postDelayed(r,Delay);
}

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
    //handle multitouchevent
        if(motionEvent.getPointerCount()>1)
        for(int n=0;n< motionEvent.getPointerCount();n++) {

            bubbleList.add(new Bubble((int) motionEvent.getX(n), (int) motionEvent.getY(n), (int) (Math.random() * 50 + 50)));
        }
        else{
            bubbleList.add(new Bubble((int) motionEvent.getX(n), (int) motionEvent.getY(n), (int) (Math.random() * 50 + 50)));
       if(bubbleList.size()>1)
       {

           bubbleList.get(bubbleList.size()-1).xspeed = bubbleList.get(bubbleList.size()-2).xspeed;
           bubbleList.get(bubbleList.size()-1).yspeed = bubbleList.get(bubbleList.size()-2).yspeed;
       }
        }
    return true;
    }


    private class Bubble{
    public int x;
    public int y;
    public int size;
    public int color;
    public int xspeed;
    public int yspeed;
    private final int Max_SPEED =15;

    public Bubble(int newX, int newY,int newSize){
        x=newX;
        y=newY;
        size = newSize;
        color = Color.argb( (int)(Math.random()*256),
                (int)(Math.random()*256),
                (int)(Math.random()*256),
                (int)(Math.random()*256) );
        xspeed = (int)(Math.random()*Max_SPEED*2-Max_SPEED);
        yspeed = (int)(Math.random()*Max_SPEED*2-Max_SPEED);

        if(xspeed==0 && yspeed==0){
            xspeed=1;
            yspeed=1;
        }
    }
    public void update(){
        x +=xspeed;
        y +=yspeed;
        if(x<=size/2 || x+size/2>=getWidth())
            xspeed = -1*yspeed;
        if(y<=size/2 || y+size/2>=getWidth())
            yspeed = -yspeed;

    }

}


}
